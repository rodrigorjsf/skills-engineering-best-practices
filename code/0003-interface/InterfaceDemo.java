import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.spi.ToolProvider;

/**
 * Lesson 0003 — Interface is more than the signature.
 *
 * Same banking domain as lesson 0002. A transfer moves `amount` between two
 * accounts and charges a `fee`. Three entries result: the payer is debited
 * amount + fee, the payee is credited amount, the bank's fee account is
 * credited the fee. The ledger invariant: every posting sums to zero.
 *
 * Run it:  java InterfaceDemo.java
 *
 * A. StrictLedger and EagerLedger implement the SAME Java interface, with
 *    byte-identical signatures. One of them requires reconcile() before
 *    total(); the other does not. No signature says so. The same caller
 *    produces balanced books against one and destroys money against the other.
 *
 * B. The fix changes exactly ONE thing: the ordering constraint moves from
 *    prose into the type. total() is not declared on the un-reconciled type.
 *
 * C. That claim is not asserted — javac is invoked at runtime on a caller that
 *    gets the order wrong, and its real diagnostic is printed.
 *
 * D. Reflection then audits which of the interface's six categories the type
 *    system actually checks. The answer is not six.
 */
public class InterfaceDemo {

    static final String FEES = "BANK-FEES";
    static final ZoneId ZONE = ZoneId.of("America/Recife");

    record Entry(String account, BigDecimal amount, Instant at) {}

    record Transfer(String id, String from, String to, BigDecimal amount, BigDecimal fee) {}

    /**
     * The type-level surface. Everything a compiler can see about a ledger.
     * Both implementations below satisfy it. Neither is described by it.
     */
    public interface Ledger {
        void post(Transfer t);
        void reconcile();
        BigDecimal total();
    }

    // ==================================================================
    // A1. STRICT — fees accrue on post and are swept on reconcile.
    //     Ordering constraint: reconcile() before total(), or the books
    //     are short by the accrued fees. Nowhere in any signature.
    // ==================================================================
    public static final class StrictLedger implements Ledger {

        private final Clock clock;
        private final List<Entry> book = new ArrayList<>();
        private BigDecimal accrued = BigDecimal.ZERO;

        public StrictLedger(Clock clock) { this.clock = clock; }

        @Override public void post(Transfer t) {
            Instant at = clock.instant();
            book.add(new Entry(t.from(), t.amount().add(t.fee()).negate(), at));
            book.add(new Entry(t.to(), t.amount(), at));
            accrued = accrued.add(t.fee());
        }

        @Override public void reconcile() {
            book.add(new Entry(FEES, accrued, clock.instant()));
            accrued = BigDecimal.ZERO;
        }

        @Override public BigDecimal total() { return sum(book); }
    }

    // ==================================================================
    // A2. EAGER — the fee lands on the fee account inside post().
    //     reconcile() exists and does nothing. Order-free.
    // ==================================================================
    public static final class EagerLedger implements Ledger {

        private final Clock clock;
        private final List<Entry> book = new ArrayList<>();

        public EagerLedger(Clock clock) { this.clock = clock; }

        @Override public void post(Transfer t) {
            Instant at = clock.instant();
            book.add(new Entry(t.from(), t.amount().add(t.fee()).negate(), at));
            book.add(new Entry(t.to(), t.amount(), at));
            book.add(new Entry(FEES, t.fee(), at));
        }

        @Override public void reconcile() { /* nothing to sweep */ }

        @Override public BigDecimal total() { return sum(book); }
    }

    // ==================================================================
    // B. THE ONE CHANGE — the ordering constraint moves into the type.
    //    Same accrual behaviour as StrictLedger. The only edit: total()
    //    is declared on SettledBooks, and reconcile() is the only way to
    //    obtain one. Prose becomes a type.
    // ==================================================================
    public static final class OpenBooks {

        private final Clock clock;
        private final List<Entry> book = new ArrayList<>();
        private BigDecimal accrued = BigDecimal.ZERO;

        public OpenBooks(Clock clock) { this.clock = clock; }

        public OpenBooks post(Transfer t) {
            Instant at = clock.instant();
            book.add(new Entry(t.from(), t.amount().add(t.fee()).negate(), at));
            book.add(new Entry(t.to(), t.amount(), at));
            accrued = accrued.add(t.fee());
            return this;
        }

        public SettledBooks reconcile() {
            book.add(new Entry(FEES, accrued, clock.instant()));
            accrued = BigDecimal.ZERO;
            return new SettledBooks(List.copyOf(book));
        }
    }

    public static final class SettledBooks {
        private final List<Entry> book;
        SettledBooks(List<Entry> book) { this.book = book; }
        public BigDecimal total() { return sum(book); }
    }

    // ------------------------------------------------------------------

    static BigDecimal sum(List<Entry> entries) {
        return entries.stream().map(Entry::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    static void line(String label, String value) {
        System.out.printf("   %s %s %s%n", label, ".".repeat(Math.max(3, 34 - label.length())), value);
    }

    static void head(String title) {
        System.out.println();
        System.out.println(title);
    }

    /** The one caller. Written against the interface, run against both. */
    static BigDecimal naiveCaller(Ledger ledger, List<Transfer> batch) {
        batch.forEach(ledger::post);
        return ledger.total();
    }

    /** The same caller, with the undocumented rule obeyed. */
    static BigDecimal informedCaller(Ledger ledger, List<Transfer> batch) {
        batch.forEach(ledger::post);
        ledger.reconcile();
        return ledger.total();
    }

    public static void main(String[] args) throws Exception {
        Clock clock = Clock.fixed(Instant.parse("2026-08-01T12:00:00Z"), ZONE);
        List<Transfer> batch = List.of(
                new Transfer("T1", "ACC-1", "ACC-2", new BigDecimal("100.00"), new BigDecimal("2.50")),
                new Transfer("T2", "ACC-3", "ACC-1", new BigDecimal("400.00"), new BigDecimal("2.50")));

        head("A. Same interface, same signatures, different interface");
        line("methods declared on Ledger", String.valueOf(Ledger.class.getDeclaredMethods().length));
        line("signatures that differ", "none");
        System.out.println();
        System.out.printf("   naive caller  post, post, total()%n");
        line("against EagerLedger", money(naiveCaller(new EagerLedger(clock), batch)) + "   OK");
        line("against StrictLedger", money(naiveCaller(new StrictLedger(clock), batch)) + "   MONEY DESTROYED");
        System.out.println();
        System.out.printf("   informed caller  post, post, reconcile, total()%n");
        line("against EagerLedger", money(informedCaller(new EagerLedger(clock), batch)) + "   OK");
        line("against StrictLedger", money(informedCaller(new StrictLedger(clock), batch)) + "   OK");
        System.out.println();
        line("rule that decides which is safe", "not in the type");

        head("B. The one change — ordering moved into the type");
        BigDecimal typed = new OpenBooks(clock).post(batch.get(0)).post(batch.get(1)).reconcile().total();
        line("post, post, reconcile, total()", money(typed) + "   OK");
        line("total() declared on OpenBooks", String.valueOf(hasMethod(OpenBooks.class, "total")));
        line("post() declared on SettledBooks", String.valueOf(hasMethod(SettledBooks.class, "post")));
        line("only producer of SettledBooks", "OpenBooks.reconcile()");

        head("C. Does the wrong order compile? Ask javac.");
        compileWrongOrder();

        head("D. Which of the six categories does the type check?");
        auditCategories();
    }

    static String money(BigDecimal v) { return String.format("%,9.2f", v); }

    static boolean hasMethod(Class<?> type, String name) {
        return Arrays.stream(type.getDeclaredMethods()).anyMatch(m -> m.getName().equals(name));
    }

    // ==================================================================
    // C. Run the real compiler on a caller that gets the order wrong.
    //    This file is compiled to a temp directory first, so the snippet
    //    is checked against THESE types, not against a reduced copy.
    // ==================================================================
    static void compileWrongOrder() throws Exception {
        Path self = Path.of("InterfaceDemo.java");
        var javac = ToolProvider.findFirst("javac");
        if (javac.isEmpty() || !Files.exists(self)) {
            line("skipped", "run from code/0003-interface/");
            return;
        }
        Path tmp = Files.createTempDirectory("lesson0003");
        Path classes = Files.createDirectories(tmp.resolve("classes"));

        StringWriter sink = new StringWriter();
        int selfRc = javac.get().run(new PrintWriter(sink), new PrintWriter(sink),
                "-d", classes.toString(), self.toString());
        if (selfRc != 0) {
            line("could not compile this file", "skipped");
            return;
        }

        Path wrong = tmp.resolve("WrongOrder.java");
        Files.writeString(wrong, """
                import java.time.Clock;
                import java.math.BigDecimal;

                class WrongOrder {
                    static BigDecimal broken(InterfaceDemo.OpenBooks books) {
                        return books.total();
                    }
                }
                """);

        StringWriter err = new StringWriter();
        int rc = javac.get().run(new PrintWriter(new StringWriter()), new PrintWriter(err),
                "-cp", classes.toString(), "-d", classes.toString(), wrong.toString());

        line("caller asks total() too early", rc == 0 ? "compiled" : "REJECTED");
        System.out.println();
        err.toString().lines()
                .map(l -> l.replace(wrong.toString(), "WrongOrder.java"))
                .filter(l -> !l.isBlank())
                .forEach(l -> System.out.println("   " + trim(l, 66)));
    }

    static String trim(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }

    // ==================================================================
    // D. The audit. The repo's interface has six categories. Ask the JVM
    //    how many of them survive into the type — for both designs.
    //
    //    Three of the six are DERIVED here: ordering, error modes and
    //    required configuration. The other three are GIVEN and labelled as
    //    such, because Java offers no construct to reflect over: a type
    //    signature always exists, and neither invariants nor performance
    //    characteristics are expressible at all. Nothing in this block is
    //    a number typed in by hand.
    // ==================================================================

    /** Ordering is in the type when the later phase's method is absent
     *  from the earlier phase's type, and present on a type only the
     *  earlier one can produce. */
    static boolean orderingInType(Class<?> before, Class<?> after) {
        return !hasMethod(before, "total") && hasMethod(after, "total");
    }

    static int declaredExceptions(Class<?> type) throws Exception {
        return type.getDeclaredMethod("post", Transfer.class).getExceptionTypes().length;
    }

    static int ctorParams(Class<?> type) {
        return Arrays.stream(type.getDeclaredConstructors())
                .filter(c -> Modifier.isPublic(c.getModifiers()))
                .mapToInt(java.lang.reflect.Constructor::getParameterCount)
                .max().orElse(0);
    }

    static int score(Class<?> before, Class<?> after) throws Exception {
        int n = 0;
        n += 1;                                              // 1 signature — given
        n += orderingInType(before, after) ? 1 : 0;          // 2 ordering  — derived
        n += declaredExceptions(before) > 0 ? 1 : 0;         // 3 errors    — derived
        n += ctorParams(before) > 0 ? 1 : 0;                 // 4 config    — derived
        n += 0;                                              // 5 invariants — given
        n += 0;                                              // 6 performance — given
        return n;
    }

    static String yn(boolean b) { return b ? "yes" : "no"; }

    static void auditCategories() throws Exception {
        line("1 type signature", "given    " + yn(true));
        line("3 error modes", "derived  " + yn(declaredExceptions(StrictLedger.class) > 0));
        line("4 required configuration", "derived  " + yn(ctorParams(StrictLedger.class) > 0));
        line("5 invariants", "given    " + yn(false));
        line("6 performance characteristics", "given    " + yn(false));
        System.out.println();
        line("2 ordering, StrictLedger", "derived  "
                + yn(orderingInType(StrictLedger.class, StrictLedger.class)));
        line("2 ordering, Open/SettledBooks", "derived  "
                + yn(orderingInType(OpenBooks.class, SettledBooks.class)));
        System.out.println();
        line("checked exceptions on post", String.valueOf(declaredExceptions(StrictLedger.class)));
        line("public constructor parameters", String.valueOf(ctorParams(StrictLedger.class)));
        System.out.println();
        line("score before section B", score(StrictLedger.class, StrictLedger.class) + " of 6");
        line("score after section B", score(OpenBooks.class, SettledBooks.class) + " of 6");
    }
}
