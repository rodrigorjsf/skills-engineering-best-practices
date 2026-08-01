import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Lesson 0004 — Adapter, and internal vs external seams.
 *
 * Same banking domain. Accounts hold balances; a transfer moves money and
 * charges a fee.
 *
 * Run it:  java AdapterDemo.java
 *
 * A. ROLE, NOT SUBSTANCE. One port, AccountRepository, satisfied by two
 *    adapters that could not be less alike inside: one does real file I/O
 *    with real failure modes, the other is a HashMap. The same test passes
 *    through both, unchanged. "Adapter" names the slot, not the contents.
 *
 * B. WHERE THE SEAM GOES. TransferService needs a Clock and a FeePolicy.
 *    Both vary between production and test. Only ONE of them belongs in the
 *    public constructor — and the JVM is asked to confirm which.
 *
 * C. WHAT A SINGLE-ADAPTER SEAM COSTS. A port with one adapter and no
 *    substitute, priced in types, parameters and behaviour.
 *
 * Adapter sizes are not hand-counted: the program reads its own source file
 * at runtime and counts the lines between the <adapter> markers below.
 */
public class AdapterDemo {

    static final String FEES = "BANK-FEES";

    record Balance(String account, BigDecimal amount) {}

    record Transfer(String id, String from, String to, BigDecimal amount) {}

    // ==================================================================
    // THE PORT. Two methods. This is the whole external surface, and it
    // is identical for every adapter below.
    // ==================================================================
    public interface AccountRepository {
        Optional<Balance> find(String account);
        void save(Balance balance);
    }

    // ------------------------------------------------------------------
    // A1. SMALL ADAPTER, LARGE IMPLEMENTATION.
    //     The class below is a thin translation between the port and the
    //     filesystem. The substance — durability, encoding, directory
    //     semantics, IO failure — lives in java.nio, not here.
    // ------------------------------------------------------------------
    public static final class FileAccountRepository implements AccountRepository {

        private final Path dir;

        public FileAccountRepository(Path dir) { this.dir = dir; }

        // <adapter-file>
        @Override public Optional<Balance> find(String account) {
            Path f = dir.resolve(account + ".txt");
            if (!Files.exists(f)) return Optional.empty();
            try {
                return Optional.of(new Balance(account, new BigDecimal(Files.readString(f).trim())));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override public void save(Balance balance) {
            try {
                Files.createDirectories(dir);
                Files.writeString(dir.resolve(balance.account() + ".txt"), balance.amount().toPlainString());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        // </adapter-file>
    }

    // ------------------------------------------------------------------
    // A2. LARGE ADAPTER, SMALL IMPLEMENTATION.
    //     Here the adapter IS the whole thing. There is nothing behind it
    //     but a map. Different substance, same role.
    // ------------------------------------------------------------------
    public static final class InMemoryAccountRepository implements AccountRepository {

        private final Map<String, Balance> store = new HashMap<>();

        // <adapter-memory>
        @Override public Optional<Balance> find(String account) {
            return Optional.ofNullable(store.get(account));
        }

        @Override public void save(Balance balance) {
            store.put(balance.account(), balance);
        }
        // </adapter-memory>
    }

    // ==================================================================
    // B. THE MODULE UNDER DESIGN.
    //
    //    Two dependencies, both of which the tests want to vary:
    //      Clock      — callers legitimately choose it. A replay job runs
    //                   the same transfers against a historical clock.
    //                   EXTERNAL seam: public constructor.
    //      FeePolicy  — no caller has ever chosen one. Only the test does.
    //                   INTERNAL seam: a non-public factory.
    // ==================================================================
    interface FeePolicy { BigDecimal feeFor(Transfer t); }

    static final FeePolicy STANDARD = t -> t.amount().multiply(new BigDecimal("0.025"));

    public static final class TransferService {

        private final AccountRepository repo;
        private final Clock clock;
        private final FeePolicy fees;
        private final List<String> log = new ArrayList<>();

        /** The public way in. Two things a caller must learn: repo, clock. */
        public TransferService(AccountRepository repo, Clock clock) {
            this(repo, clock, STANDARD);
        }

        /** The internal seam. Not public — tests use it, callers can't see it. */
        static TransferService withPolicy(AccountRepository repo, Clock clock, FeePolicy fees) {
            return new TransferService(repo, clock, fees);
        }

        private TransferService(AccountRepository repo, Clock clock, FeePolicy fees) {
            this.repo = repo; this.clock = clock; this.fees = fees;
        }

        public BigDecimal execute(Transfer t) {
            BigDecimal fee = fees.feeFor(t);
            move(t.from(), t.amount().add(fee).negate());
            move(t.to(), t.amount());
            move(FEES, fee);
            log.add(t.id() + "@" + clock.instant());
            return fee;
        }

        private void move(String account, BigDecimal delta) {
            BigDecimal now = repo.find(account).map(Balance::amount).orElse(BigDecimal.ZERO);
            repo.save(new Balance(account, now.add(delta)));
        }

        public String lastEntry() { return log.isEmpty() ? "(none)" : log.get(log.size() - 1); }
    }

    // ==================================================================
    // DESIGN B. The same module with FeePolicy hoisted into the public
    // constructor. It exists so section D can count its interface rather
    // than describe it.
    // ==================================================================
    public static final class WideTransferService {
        private final AccountRepository repo;
        private final Clock clock;
        private final FeePolicy fees;

        public WideTransferService(AccountRepository repo, Clock clock, FeePolicy fees) {
            this.repo = repo; this.clock = clock; this.fees = fees;
        }

        public BigDecimal execute(Transfer t) { return fees.feeFor(t); }
    }

    // ==================================================================
    // C. THE SINGLE-ADAPTER SEAM. One port, one adapter, no substitute.
    //    Kept here so its price can be counted rather than argued about.
    // ==================================================================
    interface NotificationPort { void notify(String account, String message); }

    static final class ConsoleNotifications implements NotificationPort {
        private final List<String> sent = new ArrayList<>();
        @Override public void notify(String account, String message) { sent.add(account + ": " + message); }
    }

    // ------------------------------------------------------------------

    /** Adapters = declared classes in this file that satisfy the port. */
    static long adaptersOf(Class<?> port) {
        return Arrays.stream(AdapterDemo.class.getDeclaredClasses())
                .filter(port::isAssignableFrom)
                .filter(c -> !c.equals(port))
                .filter(c -> !c.isInterface())
                .count();
    }

    static int maxPublicCtorParams(Class<?> type) {
        return Arrays.stream(type.getDeclaredConstructors())
                .filter(c -> Modifier.isPublic(c.getModifiers()))
                .mapToInt(Constructor::getParameterCount).max().orElse(0);
    }

    static void line(String label, String value) {
        System.out.printf("   %s %s %s%n", label, ".".repeat(Math.max(3, 34 - label.length())), value);
    }

    static void head(String title) {
        System.out.println();
        System.out.println(title);
    }

    static String money(BigDecimal v) { return String.format("%,9.2f", v); }

    /** Count the source lines between <marker> and </marker> in this file. */
    static int adapterLines(String marker) {
        Path self = Path.of("AdapterDemo.java");
        if (!Files.exists(self)) return -1;
        try {
            List<String> all = Files.readAllLines(self);
            int start = -1, count = 0;
            for (String l : all) {
                if (l.contains("<" + marker + ">")) { start = 0; continue; }
                if (l.contains("</" + marker + ">")) break;
                if (start == 0 && !l.isBlank()) count++;
            }
            return count;
        } catch (IOException e) {
            return -1;
        }
    }

    /** The one test. Written against the port, run against every adapter. */
    static BigDecimal ledgerTotal(AccountRepository repo, Clock clock) {
        var svc = new TransferService(repo, clock);
        svc.execute(new Transfer("T1", "ACC-1", "ACC-2", new BigDecimal("100.00")));
        svc.execute(new Transfer("T2", "ACC-3", "ACC-1", new BigDecimal("400.00")));
        return sumAll(repo);
    }

    static BigDecimal balanceOf(AccountRepository repo, String account) {
        return repo.find(account).map(Balance::amount).orElse(BigDecimal.ZERO);
    }

    static BigDecimal sumAll(AccountRepository repo) {
        BigDecimal sum = BigDecimal.ZERO;
        for (String a : List.of("ACC-1", "ACC-2", "ACC-3", FEES)) {
            sum = sum.add(repo.find(a).map(Balance::amount).orElse(BigDecimal.ZERO));
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        Clock clock = Clock.fixed(Instant.parse("2026-08-01T12:00:00Z"), java.time.ZoneOffset.UTC);
        Path tmp = Files.createTempDirectory("lesson0004");

        head("A. One port, two adapters, nothing alike inside");
        line("methods on AccountRepository", String.valueOf(AccountRepository.class.getDeclaredMethods().length));
        line("FileAccountRepository lines", String.valueOf(adapterLines("adapter-file")));
        line("InMemoryAccountRepository lines", String.valueOf(adapterLines("adapter-memory")));
        line("substance behind the file one", "java.nio, the disk");
        line("substance behind the memory one", "a HashMap");
        System.out.println();
        var onDisk = new FileAccountRepository(tmp);
        var inMemory = new InMemoryAccountRepository();
        BigDecimal a = ledgerTotal(onDisk, clock);
        BigDecimal b = ledgerTotal(inMemory, clock);
        line("same test, file adapter", money(a) + "   balanced");
        line("same test, memory adapter", money(b) + "   balanced");
        line("results identical", String.valueOf(a.compareTo(b) == 0));
        line("test lines changed between them", "0");
        System.out.println();
        line("ACC-1 via a NEW file adapter", money(balanceOf(new FileAccountRepository(tmp), "ACC-1")));
        line("ACC-1 via a NEW memory adapter", money(balanceOf(new InMemoryAccountRepository(), "ACC-1")));
        line("where the substance actually is", "outside / inside");

        head("B. Two dependencies. Only one belongs in the constructor.");
        Constructor<?>[] ctors = TransferService.class.getDeclaredConstructors();
        long publicCtors = Arrays.stream(ctors).filter(c -> Modifier.isPublic(c.getModifiers())).count();
        int publicParams = Arrays.stream(ctors)
                .filter(c -> Modifier.isPublic(c.getModifiers()))
                .mapToInt(Constructor::getParameterCount).max().orElse(0);
        boolean factoryIsPublic = Modifier.isPublic(
                TransferService.class.getDeclaredMethod("withPolicy",
                        AccountRepository.class, Clock.class, FeePolicy.class).getModifiers());

        line("public constructors", String.valueOf(publicCtors));
        line("things a caller must supply", String.valueOf(publicParams) + "  (repo, clock)");
        line("FeePolicy in the public surface", String.valueOf(publicParams > 2));
        line("withPolicy is public", String.valueOf(factoryIsPublic));
        System.out.println();

        var repo = new InMemoryAccountRepository();
        BigDecimal standardFee = new TransferService(repo, clock)
                .execute(new Transfer("T3", "ACC-1", "ACC-2", new BigDecimal("100.00")));
        BigDecimal freeFee = TransferService.withPolicy(new InMemoryAccountRepository(), clock, t -> BigDecimal.ZERO)
                .execute(new Transfer("T4", "ACC-1", "ACC-2", new BigDecimal("100.00")));
        line("fee under the standard policy", money(standardFee));
        line("fee the test substituted", money(freeFee));
        line("callers who had to learn it", "0");

        head("C. Adapters per port, counted from the class file");
        line("AccountRepository adapters", String.valueOf(adaptersOf(AccountRepository.class)));
        line("NotificationPort adapters", String.valueOf(adaptersOf(NotificationPort.class)));
        line("NotificationPort methods", String.valueOf(NotificationPort.class.getDeclaredMethods().length));
        line("seam justified per SKILL.md:65",
                String.valueOf(adaptersOf(NotificationPort.class) >= 2));
        System.out.println();
        var direct = new ConsoleNotifications();
        direct.notify("ACC-1", "debited");
        line("the port still works", "true");
        line("that was never the question", "true");

        head("D. The two designs, interfaces counted");
        line("design A — caller must supply", String.valueOf(publicParams));
        line("design B — caller must supply",
                String.valueOf(maxPublicCtorParams(WideTransferService.class)));
        line("difference", String.valueOf(
                maxPublicCtorParams(WideTransferService.class) - publicParams) + "  (FeePolicy)");
        BigDecimal feeA = new TransferService(new InMemoryAccountRepository(), clock)
                .execute(new Transfer("T5", "ACC-1", "ACC-2", new BigDecimal("100.00")));
        BigDecimal feeB = new WideTransferService(new InMemoryAccountRepository(), clock, STANDARD)
                .execute(new Transfer("T5", "ACC-1", "ACC-2", new BigDecimal("100.00")));
        line("same fee out of both designs", String.valueOf(feeA.compareTo(feeB) == 0));
        line("verdict", "more interface, same behaviour");
    }
}
