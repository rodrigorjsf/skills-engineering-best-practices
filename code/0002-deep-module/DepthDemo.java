import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lesson 0002 — Deep vs shallow module.
 *
 * One banking operation, posted to a double-entry ledger:
 *
 *   A transfer moves `amount` from one account to another and charges a `fee`.
 *   Three entries result: the payer is debited amount + fee, the payee is
 *   credited amount, and the bank's fee account is credited the fee.
 *   The ledger invariant is that every posting sums to zero — money is neither
 *   created nor destroyed.
 *
 * Run it:  java DepthDemo.java
 *
 * ShallowLedger and DeepLedger have BYTE-IDENTICAL method bodies. The only
 * differences are five `private` keywords and one added method, `post`. Nothing
 * was rewritten, optimised or extracted. What moved is the five-statement
 * sequence — out of the caller, into the module.
 *
 * That is the whole demonstration: depth is a property of the interface, not of
 * the implementation.
 */
public class DepthDemo {

    static final String FEES = "BANK-FEES";
    static final ZoneId ZONE = ZoneId.of("America/Recife");

    record Entry(String account, BigDecimal amount, Instant at) {}

    record Transfer(String id, String from, String to, BigDecimal amount, BigDecimal fee) {}

    // ==================================================================
    // A. SHALLOW — six public methods. The ordering rule and the balance
    //    invariant are not in any signature: they live in the caller's head.
    // ==================================================================
    public static final class ShallowLedger {

        private final Clock clock;
        private final List<Entry> staged = new ArrayList<>();
        private final List<Entry> committed = new ArrayList<>();
        private Instant stamp;

        public ShallowLedger(Clock clock) { this.clock = clock; }

        // <impl>
        public void open() { staged.clear(); stamp = clock.instant(); }

        public void debit(String account, BigDecimal amount) { staged.add(new Entry(account, amount.negate(), stamp)); }

        public void credit(String account, BigDecimal amount) { staged.add(new Entry(account, amount, stamp)); }

        public boolean balanced() { return sum(staged).signum() == 0; }

        public void commit() { committed.addAll(staged); staged.clear(); }
        // </impl>

        public BigDecimal total() { return sum(committed); }

        List<Entry> entries() { return committed; }   // not public: inspection for this demo only
    }

    // ==================================================================
    // B. DEEP — one public method to do the job. The five statements the
    //    caller used to write are now the body of `post`, unchanged.
    // ==================================================================
    public static final class DeepLedger {

        private final Clock clock;
        private final List<Entry> staged = new ArrayList<>();
        private final List<Entry> committed = new ArrayList<>();
        private Instant stamp;

        public DeepLedger(Clock clock) { this.clock = clock; }

        // <impl>
        public void post(Transfer t) {
            open();
            debit(t.from(), t.amount().add(t.fee()));
            credit(t.to(), t.amount());
            credit(FEES, t.fee());
            if (!balanced()) throw new IllegalStateException("unbalanced posting refused: " + t.id());
            commit();
        }

        private void open() { staged.clear(); stamp = clock.instant(); }

        private void debit(String account, BigDecimal amount) { staged.add(new Entry(account, amount.negate(), stamp)); }

        private void credit(String account, BigDecimal amount) { staged.add(new Entry(account, amount, stamp)); }

        private boolean balanced() { return sum(staged).signum() == 0; }

        private void commit() { committed.addAll(staged); staged.clear(); }
        // </impl>

        public BigDecimal total() { return sum(committed); }

        List<Entry> entries() { return committed; }
    }

    // ==================================================================
    // C. PADDED — the SAME public interface as DeepLedger and the SAME
    //    behaviour, written the long way round. Nothing here is useful.
    //    It exists to be measured by the metric the repo rejects.
    // ==================================================================
    public static final class PaddedLedger {

        private final Clock clock;
        private final List<Entry> staged = new ArrayList<>();
        private final List<Entry> committed = new ArrayList<>();
        private Instant stamp;

        public PaddedLedger(Clock clock) { this.clock = clock; }

        // <impl>
        public void post(Transfer t) {
            open();
            List<String> accounts = new ArrayList<>();
            List<BigDecimal> amounts = new ArrayList<>();
            accounts.add(t.from());
            amounts.add(negated(added(t.amount(), t.fee())));
            accounts.add(t.to());
            amounts.add(t.amount());
            accounts.add(FEES);
            amounts.add(t.fee());
            int index = 0;
            while (index < accounts.size()) {
                String account = accounts.get(index);
                BigDecimal amount = amounts.get(index);
                stage(account, amount);
                index = index + 1;
            }
            BigDecimal running = BigDecimal.ZERO;
            for (Entry entry : staged) {
                running = added(running, entry.amount());
            }
            boolean isBalanced = running.signum() == 0;
            if (!isBalanced) {
                throw new IllegalStateException("unbalanced posting refused: " + t.id());
            }
            commit();
        }

        private void open() { staged.clear(); stamp = clock.instant(); }

        private void stage(String account, BigDecimal amount) { staged.add(new Entry(account, amount, stamp)); }

        private void commit() { committed.addAll(staged); staged.clear(); }

        private BigDecimal added(BigDecimal a, BigDecimal b) { return a.add(b); }

        private BigDecimal negated(BigDecimal a) { return a.negate(); }
        // </impl>

        public BigDecimal total() { return sum(committed); }
    }

    static BigDecimal sum(List<Entry> entries) {
        return entries.stream().map(Entry::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** The public interface, counted by the JVM rather than by us. */
    static long publicMethods(Class<?> type) {
        return Arrays.stream(type.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .filter(m -> !m.isSynthetic())
                .count();
    }

    static boolean anyNullTimestamp(List<Entry> entries) {
        return entries.stream().anyMatch(e -> e.at() == null);
    }

    static String money(BigDecimal v) { return String.format("%,10.2f", v); }

    // Implementation lines inside each `// <impl> ... // </impl>` block, ignoring
    // blank and comment-only lines. Counted in-session, not estimated:
    //   python3 -c "import re;print([len([l for l in b.split(chr(10)) if l.strip()
    //     and not l.strip().startswith('//')]) for b in
    //     re.findall(r'// <impl>.(.*?)\s*// </impl>', open('DepthDemo.java').read(), re.S)])"
    // Update these if the bodies change.
    static final int SHALLOW_IMPL_LINES = 5;
    static final int DEEP_IMPL_LINES = 13;
    static final int PADDED_IMPL_LINES = 32;

    public static void main(String[] args) {
        Clock clock = Clock.fixed(Instant.parse("2026-08-01T13:00:00Z"), ZONE);
        Transfer t = new Transfer("T-1", "ACC-1", "ACC-2",
                new BigDecimal("25000.00"), new BigDecimal("50.00"));

        System.out.println("=".repeat(62));
        System.out.println(" LESSON 0002 — the same implementation, two interfaces");
        System.out.println("=".repeat(62));

        // ---- A. the shallow interface, used four ways -------------------
        System.out.println();
        System.out.println("A. ShallowLedger — sequence and invariant are the caller's job");
        line("public methods to learn", String.valueOf(publicMethods(ShallowLedger.class)));
        line("calls to post one transfer", "5, in a required order");
        line("ordering rules absent from types", "1");
        line("invariants the caller maintains", "1");
        System.out.println();

        // A1 — correct caller.
        ShallowLedger a1 = new ShallowLedger(clock);
        a1.open();
        a1.debit(t.from(), t.amount().add(t.fee()));
        a1.credit(t.to(), t.amount());
        a1.credit(FEES, t.fee());
        if (!a1.balanced()) throw new IllegalStateException("unbalanced");
        a1.commit();
        report("caller 1  everything right", a1.total(), a1.entries());

        // A2 — the fee is credited but never debited, and balanced() is never called.
        ShallowLedger a2 = new ShallowLedger(clock);
        a2.open();
        a2.debit(t.from(), t.amount());          // forgot .add(t.fee())
        a2.credit(t.to(), t.amount());
        a2.credit(FEES, t.fee());
        a2.commit();                              // no invariant check at all
        report("caller 2  fee not debited", a2.total(), a2.entries());

        // A3 — copy-paste: credit where debit belonged, and the check runs AFTER
        // commit(), which has already emptied the staging area. It passes vacuously.
        ShallowLedger a3 = new ShallowLedger(clock);
        a3.open();
        a3.credit(t.from(), t.amount().add(t.fee()));   // should have been debit
        a3.credit(t.to(), t.amount());
        a3.credit(FEES, t.fee());
        a3.commit();
        boolean lateCheck = a3.balanced();               // true: staged is empty
        report("caller 3  check after commit", a3.total(), a3.entries());
        line("          balanced() said", lateCheck + "  (staging was emptied)");

        // A4 — open() forgotten. The money balances; the audit trail does not exist.
        ShallowLedger a4 = new ShallowLedger(clock);
        a4.debit(t.from(), t.amount().add(t.fee()));
        a4.credit(t.to(), t.amount());
        a4.credit(FEES, t.fee());
        if (!a4.balanced()) throw new IllegalStateException("unbalanced");
        a4.commit();
        report("caller 4  open() forgotten", a4.total(), a4.entries());

        System.out.println();
        line("wrong sequences that compile", "3 of 4");

        // ---- B. the deep interface --------------------------------------
        DeepLedger b = new DeepLedger(clock);
        b.post(t);

        System.out.println();
        System.out.println("B. DeepLedger — the same five statements, moved inside");
        line("public methods to learn", publicMethods(DeepLedger.class) + "  (post, total)");
        line("calls to post one transfer", "1");
        line("ordering rules absent from types", "0");
        line("invariants the caller maintains", "0  (enforced inside)");
        report("post(transfer)", b.total(), b.entries());
        line("wrong sequences that compile", "0 of 4  (five are private)");

        // ---- C. what the rejected metric scores -------------------------
        PaddedLedger c = new PaddedLedger(clock);
        c.post(t);
        boolean sameBehaviour = c.total().compareTo(b.total()) == 0;

        System.out.println();
        System.out.println("C. What \"impl-lines / interface-lines\" would score");
        line("ShallowLedger", String.format("%2d impl / %d public = %4.1f",
                SHALLOW_IMPL_LINES, publicMethods(ShallowLedger.class),
                SHALLOW_IMPL_LINES / (double) publicMethods(ShallowLedger.class)));
        line("DeepLedger", String.format("%2d impl / %d public = %4.1f",
                DEEP_IMPL_LINES, publicMethods(DeepLedger.class),
                DEEP_IMPL_LINES / (double) publicMethods(DeepLedger.class)));
        line("PaddedLedger", String.format("%2d impl / %d public = %4.1f",
                PADDED_IMPL_LINES, publicMethods(PaddedLedger.class),
                PADDED_IMPL_LINES / (double) publicMethods(PaddedLedger.class)));
        line("metric ranks Deep above Shallow", String.format("%s   (it gets this right)",
                DEEP_IMPL_LINES / (double) publicMethods(DeepLedger.class)
                        > SHALLOW_IMPL_LINES / (double) publicMethods(ShallowLedger.class)));
        line("PaddedLedger scored", String.format("%.1fx deeper than Deep",
                PADDED_IMPL_LINES / (double) DEEP_IMPL_LINES));
        line("same public interface as Deep",
                String.valueOf(publicMethods(PaddedLedger.class) == publicMethods(DeepLedger.class)));
        line("same behaviour (totals equal)", String.valueOf(sameBehaviour));
        line("behaviour gained by the padding", "none");

        System.out.println();
        System.out.println("-".repeat(62));
        System.out.println("Method bodies of ShallowLedger and DeepLedger: byte-identical.");
        System.out.println("Delta between them: 5 `private` keywords, plus post().");
        System.out.println("What changed: which side of the interface the sequence sits on.");
        System.out.println("-".repeat(62));

        boolean claimHolds = a1.total().signum() == 0
                && a2.total().signum() != 0
                && a3.total().signum() != 0
                && anyNullTimestamp(a4.entries())
                && b.total().signum() == 0
                && sameBehaviour;
        if (!claimHolds) {
            throw new AssertionError("the demonstration did not hold");
        }
    }

    static void report(String label, BigDecimal total, List<Entry> entries) {
        String verdict;
        if (total.signum() != 0) verdict = "MONEY CREATED";
        else if (anyNullTimestamp(entries)) verdict = "NO TIMESTAMP";
        else verdict = "OK";
        System.out.printf("   %-29s total %s   %s%n", label, money(total), verdict);
    }

    /** A dotted leader, so every measurement lands in the same column. */
    static void line(String label, String value) {
        System.out.printf("   %s %s %s%n", label, ".".repeat(Math.max(3, 36 - label.length())), value);
    }
}
