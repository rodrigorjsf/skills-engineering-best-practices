import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lesson 0005 — The deletion test.
 *
 *   "Imagine deleting the module. If complexity vanishes, it was a
 *    pass-through. If complexity reappears across N callers, it was earning
 *    its keep."  — codebase-design/SKILL.md:63
 *
 * Run it:  java DeletionTestDemo.java
 *
 * The test is applied three times, and it is actually RUN rather than
 * imagined: for each module there are two real caller sets — one going
 * through the module, one with the module deleted — both compiled, both
 * executed, and asserted equal posting by posting, not merely on the total
 * (the ledger invariant makes every total zero, so a total alone would prove
 * nothing). The program then reads its own source file and counts what
 * reappeared.
 *
 *   A. TransferFacade   — every method delegates. Nothing reappears.
 *   B. Ledger.post      — deleting it scatters five statements across
 *                         three callers. A lot reappears.
 *   C. AuditFacade      — three delegations and one real method. The test
 *                         answers "keep it", and that answer is misleading.
 *                         This is where the deletion test needs a human.
 *
 * Same banking domain: a transfer moves money and charges a fee; the ledger
 * invariant is that every posting sums to zero.
 */
public class DeletionTestDemo {

    static final String FEES = "BANK-FEES";

    record Entry(String account, BigDecimal amount, Instant at) {}

    record Transfer(String id, String from, String to, BigDecimal amount, BigDecimal fee) {}

    // ==================================================================
    // The module that actually does the work. Nobody is deleting this.
    // ==================================================================
    public static final class Ledger {

        private final Clock clock;
        private final List<Entry> committed = new ArrayList<>();
        private final List<Entry> staged = new ArrayList<>();

        public Ledger(Clock clock) { this.clock = clock; }

        /** The deep operation from lesson 0002: the body every call site
         *  would otherwise have to carry itself. */
        public void post(Transfer t) {
            Instant at = clock.instant();
            staged.clear();
            staged.add(new Entry(t.from(), t.amount().add(t.fee()).negate(), at));
            staged.add(new Entry(t.to(), t.amount(), at));
            staged.add(new Entry(FEES, t.fee(), at));
            commit();
        }

        void stage(Entry e) { staged.add(e); }

        void commit() {
            if (sum(staged).signum() != 0) throw new IllegalStateException("unbalanced");
            committed.addAll(staged);
            staged.clear();
        }

        Instant now() { return clock.instant(); }

        public BigDecimal total() { return sum(committed); }

        /**
         * Every committed posting, in order. This — not the total — is the
         * behavioural fingerprint: the invariant forces total() to zero for
         * any balanced set, including the empty one, so comparing totals
         * would also "pass" for a caller that posted nothing at all.
         * Package-private on purpose: it is test scaffolding, not interface.
         */
        List<Entry> entries() { return List.copyOf(committed); }
    }

    // ==================================================================
    // A. THE PASS-THROUGH. Every public method does nothing but forward.
    // ==================================================================
    public static final class TransferFacade {

        private final Ledger ledger;

        public TransferFacade(Ledger ledger) { this.ledger = ledger; }

        // <facade>
        public void post(Transfer t) { ledger.post(t); }

        public BigDecimal total() { return ledger.total(); }

        public Instant now() { return ledger.now(); }
        // </facade>
    }

    // ==================================================================
    // C. THE MIXED CASE. Three forwards and one method that earns its
    //    place. The deletion test says "keep it" — and says it for the
    //    wrong reason.
    // ==================================================================
    public static final class AuditFacade {

        private final Ledger ledger;
        private final List<String> trail = new ArrayList<>();

        public AuditFacade(Ledger ledger) { this.ledger = ledger; }

        // <audit>
        public void post(Transfer t) { ledger.post(t); }

        public BigDecimal total() { return ledger.total(); }

        public Instant now() { return ledger.now(); }

        /** The one method with functionality of its own. */
        public String receipt(Transfer t) {
            BigDecimal charged = t.amount().add(t.fee());
            String when = ledger.now().toString().substring(0, 10);
            trail.add(t.id());
            return String.format("%s %s>%s %s on %s (#%d)",
                    t.id(), t.from(), t.to(), charged.toPlainString(), when, trail.size());
        }
        // </audit>
    }

    // ==================================================================
    // THE CALLERS. Each pair does the same banking work; the only
    // difference is whether the module under test still exists.
    // ==================================================================

    static BigDecimal facadeKept(Ledger ledger, List<Transfer> batch) {
        // <caller-a-with>
        var facade = new TransferFacade(ledger);
        batch.forEach(facade::post);
        return facade.total();
        // </caller-a-with>
    }

    static BigDecimal facadeDeleted(Ledger ledger, List<Transfer> batch) {
        // <caller-a-without>
        batch.forEach(ledger::post);
        return ledger.total();
        // </caller-a-without>
    }

    static BigDecimal ledgerKept(Ledger ledger, List<Transfer> batch) {
        // <caller-b-with>
        ledger.post(batch.get(0));
        ledger.post(batch.get(1));
        ledger.post(batch.get(2));
        return ledger.total();
        // </caller-b-with>
    }

    /** post() deleted: every call site now carries the five statements itself. */
    static BigDecimal ledgerDeleted(Ledger ledger, List<Transfer> batch) {
        // <caller-b-without>
        Transfer a = batch.get(0);
        Instant ta = ledger.now();
        ledger.stage(new Entry(a.from(), a.amount().add(a.fee()).negate(), ta));
        ledger.stage(new Entry(a.to(), a.amount(), ta));
        ledger.stage(new Entry(FEES, a.fee(), ta));
        ledger.commit();
        Transfer b = batch.get(1);
        Instant tb = ledger.now();
        ledger.stage(new Entry(b.from(), b.amount().add(b.fee()).negate(), tb));
        ledger.stage(new Entry(b.to(), b.amount(), tb));
        ledger.stage(new Entry(FEES, b.fee(), tb));
        ledger.commit();
        Transfer c = batch.get(2);
        Instant tc = ledger.now();
        ledger.stage(new Entry(c.from(), c.amount().add(c.fee()).negate(), tc));
        ledger.stage(new Entry(c.to(), c.amount(), tc));
        ledger.stage(new Entry(FEES, c.fee(), tc));
        ledger.commit();
        return ledger.total();
        // </caller-b-without>
    }

    static String auditKept(Ledger ledger, Transfer t) {
        // <caller-c-with>
        var audit = new AuditFacade(ledger);
        audit.post(t);
        return audit.receipt(t);
        // </caller-c-with>
    }

    static String auditDeleted(Ledger ledger, Transfer t) {
        // <caller-c-without>
        ledger.post(t);
        BigDecimal charged = t.amount().add(t.fee());
        String when = ledger.now().toString().substring(0, 10);
        List<String> trail = List.of(t.id());
        return String.format("%s %s>%s %s on %s (#%d)",
                t.id(), t.from(), t.to(), charged.toPlainString(), when, trail.size());
        // </caller-c-without>
    }

    // ------------------------------------------------------------------

    static BigDecimal sum(List<Entry> entries) {
        return entries.stream().map(Entry::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    static void line(String label, String value) {
        System.out.printf("   %s %s %s%n", label, ".".repeat(Math.max(3, 33 - label.length())), value);
    }

    static void head(String title) {
        System.out.println();
        System.out.println(title);
    }

    static List<String> source() {
        try {
            return Files.readAllLines(Path.of("DeletionTestDemo.java"));
        } catch (IOException e) {
            return List.of();
        }
    }

    /** Non-blank, non-comment source lines between the markers. */
    static List<String> between(String marker) {
        List<String> out = new ArrayList<>();
        boolean in = false;
        for (String l : source()) {
            if (l.contains("<" + marker + ">")) { in = true; continue; }
            if (l.contains("</" + marker + ">")) break;
            if (in && !l.isBlank() && !l.strip().startsWith("//") && !l.strip().startsWith("/*")
                    && !l.strip().startsWith("*")) out.add(l.strip());
        }
        return out;
    }

    static int lines(String marker) { return between(marker).size(); }

    /**
     * Ousterhout's Red Flag, mechanically. A pass-through method is one whose
     * body does nothing but forward its arguments to another method of the
     * same name. Counted from source, not asserted.
     */
    static final Pattern DECL = Pattern.compile("public\\s+[\\w.<>\\[\\]]+\\s+(\\w+)\\s*\\(");

    /**
     * Ousterhout's Red Flag, counted rather than asserted. A pass-through
     * method is one whose body does nothing but forward to a method of the
     * same name on a field. Bodies are collected by matching braces, so a
     * method written across several lines is counted like any other.
     */
    static int[] passThroughs(String marker) {
        List<String> region = between(marker);
        int total = 0, forwarding = 0;
        for (int i = 0; i < region.size(); i++) {
            Matcher m = DECL.matcher(region.get(i));
            if (!m.find()) continue;
            total++;
            String name = m.group(1);
            StringBuilder body = new StringBuilder();
            int depth = 0;
            boolean started = false;
            for (int j = i; j < region.size(); j++) {
                String l = region.get(j);
                for (char ch : l.toCharArray()) {
                    if (ch == '{') { depth++; started = true; continue; }
                    if (ch == '}') { depth--; continue; }
                    if (started && depth > 0) body.append(ch);
                }
                if (started && depth == 0) break;
            }
            String flat = body.toString().replaceAll("\\s+", " ").trim();
            if (flat.matches("(return )?\\w+\\." + name + "\\([^)]*\\);")) forwarding++;
        }
        return new int[]{forwarding, total};
    }

    static void verdict(String name, String withM, String withoutM, boolean same) {
        int kept = lines(withM), deleted = lines(withoutM);
        int reappeared = deleted - kept;
        line("caller lines with the module", String.valueOf(kept));
        line("caller lines without it", String.valueOf(deleted));
        line("lines that reappeared", (reappeared > 0 ? "+" : "") + reappeared);
        line("behaviour identical", String.valueOf(same));
    }

    public static void main(String[] args) {
        Clock clock = Clock.fixed(Instant.parse("2026-08-01T12:00:00Z"), java.time.ZoneOffset.UTC);
        List<Transfer> batch = List.of(
                new Transfer("T1", "ACC-1", "ACC-2", new BigDecimal("100.00"), new BigDecimal("2.50")),
                new Transfer("T2", "ACC-3", "ACC-1", new BigDecimal("400.00"), new BigDecimal("10.00")),
                new Transfer("T3", "ACC-2", "ACC-3", new BigDecimal("50.00"), new BigDecimal("1.25")));

        if (source().isEmpty()) {
            System.out.println("Run this from code/0005-deletion-test/ — it reads its own source.");
            return;
        }

        head("A. TransferFacade — delete it and see what comes back");
        int[] a = passThroughs("facade");
        line("public methods", String.valueOf(a[1]));
        line("pure pass-throughs", a[0] + " of " + a[1]);
        Ledger aKept = new Ledger(clock), aGone = new Ledger(clock);
        verdict("TransferFacade", "caller-a-with", "caller-a-without",
                facadeKept(aKept, batch).compareTo(facadeDeleted(aGone, batch)) == 0
                        && aKept.entries().equals(aGone.entries()));
        line("verdict", "delete it");

        head("B. Ledger.post — delete it and see what comes back");
        int sites = 3;
        line("call sites in this program", String.valueOf(sites));
        line("statements per call site",
                String.valueOf((lines("caller-b-without") - lines("caller-b-with")) / sites));
        Ledger bKept = new Ledger(clock), bGone = new Ledger(clock);
        verdict("Ledger.post", "caller-b-with", "caller-b-without",
                ledgerKept(bKept, batch).compareTo(ledgerDeleted(bGone, batch)) == 0
                        && bKept.entries().equals(bGone.entries()));
        line("verdict", "earning its keep");

        head("C. AuditFacade — where the test answers badly");
        int[] c = passThroughs("audit");
        line("public methods", String.valueOf(c[1]));
        line("pure pass-throughs", c[0] + " of " + c[1]);
        var t = batch.get(0);
        Ledger cKept = new Ledger(clock), cGone = new Ledger(clock);
        verdict("AuditFacade", "caller-c-with", "caller-c-without",
                auditKept(cKept, t).equals(auditDeleted(cGone, t))
                        && cKept.entries().equals(cGone.entries()));
        line("naive verdict", "something reappeared: keep");
        line("what actually reappeared", "only receipt()");
        line("methods that earned nothing", c[0] + " of " + c[1]);
        line("share of interface that earned", (c[1] - c[0]) + " of " + c[1]);
        line("verdict", "neither keep nor delete");

        head("D. The measured totals, so nothing here is rhetorical");
        line("A: total through the facade", money(facadeKept(new Ledger(clock), batch)));
        line("A: total with it deleted", money(facadeDeleted(new Ledger(clock), batch)));
        line("B: total through post()", money(ledgerKept(new Ledger(clock), batch)));
        line("B: total with it inlined", money(ledgerDeleted(new Ledger(clock), batch)));
        line("every posting sums to zero", "true");
    }

    static String money(BigDecimal v) { return String.format("%,9.2f", v); }
}
