import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.*;

/**
 * Lesson 0006 — Dependency inversion: who owns the abstraction.
 *
 * Every number printed below is either DERIVED from the JVM by reflection over
 * the types in this file, or explicitly labelled `given`. Nothing measured is
 * asserted as a literal. Run with: java InversionDemo.java
 */
public class InversionDemo {

    // ---------------------------------------------------------------- domain

    record Money(BigDecimal amount) {
        static Money of(String s) { return new Money(new BigDecimal(s)); }
        public String toString() { return amount.toString(); }
    }

    record Receipt(String reference, Money charged) {}

    /**
     * The vendor's own namespace. Anything declared in here is a DETAIL —
     * and that is derivable, not asserted: a detail is a type whose enclosing
     * class is a vendor namespace rather than the domain itself.
     */
    public static final class Stripe {
        public record Response(String id, int httpStatus) {}
    }

    // ------------------------------------- A. injection, with and without DIP

    /**
     * The port. Declared HERE, next to the policy that needs it — not next to
     * the vendor that implements it. That placement IS the inversion.
     */
    public interface PaymentPort {
        Receipt charge(Money amount);
    }

    /**
     * The same idea, botched: the abstraction names a vendor type in its own
     * signature. Martin's part B fails — the abstraction depends on a detail.
     */
    public interface LeakyPaymentPort {
        Stripe.Response charge(Money amount);
    }

    /** A dependency nobody ever varies in production. */
    public interface FeeCalculator {
        Money feeFor(Money amount);
    }

    public static final class StripeGateway implements PaymentPort {
        public Receipt charge(Money amount) {
            return new Receipt("stripe-001", amount);
        }
    }

    public static final class InMemoryGateway implements PaymentPort {
        public Receipt charge(Money amount) {
            return new Receipt("fake-001", amount);
        }
    }

    public static final class LeakyStripeGateway implements LeakyPaymentPort {
        public Stripe.Response charge(Money amount) {
            return new Stripe.Response("stripe-001", 200);
        }
    }

    public static final class FlatFee implements FeeCalculator {
        public Money feeFor(Money amount) { return Money.of("2.50"); }
    }

    /**
     * Injection with NO inversion. The dependency is handed in — testable,
     * mockable, "accept dependencies, don't create them" satisfied — and the
     * caller still depends on a concrete vendor class.
     */
    public static final class NaiveTransferService {
        private final StripeGateway gateway;
        public NaiveTransferService(StripeGateway gateway) {
            this.gateway = gateway;
        }
        public Receipt transfer(Money amount) { return gateway.charge(amount); }
    }

    /** Injection AND inversion. One character of difference in the source. */
    public static final class TransferService {
        private final PaymentPort gateway;
        public TransferService(PaymentPort gateway) { this.gateway = gateway; }
        public Receipt transfer(Money amount) { return gateway.charge(amount); }
    }

    // ------------------------------------------------------------- reflection

    static Class<?>[] nested() { return InversionDemo.class.getDeclaredClasses(); }

    /** Derived: is the single constructor parameter an abstraction? */
    static boolean invertsItsDependency(Class<?> service) {
        Constructor<?> c = service.getDeclaredConstructors()[0];
        return Arrays.stream(c.getParameterTypes()).allMatch(Class::isInterface);
    }

    static String ctorParamName(Class<?> service) {
        return service.getDeclaredConstructors()[0].getParameterTypes()[0]
                .getSimpleName();
    }

    /** Derived: implementors of `port` among the types declared in this file. */
    static List<Class<?>> adaptersOf(Class<?> port) {
        return Arrays.stream(nested())
                .filter(port::isAssignableFrom)
                .filter(c -> !c.equals(port))
                .filter(c -> !c.isInterface())
                .collect(Collectors.toList());
    }

    /**
     * A DETAIL is a type that lives inside a vendor namespace — a type nested
     * one level deeper than the domain. Derived from the enclosing class, not
     * from a hand-written list of "bad" names.
     */
    static boolean isDetail(Class<?> t) {
        Class<?> enclosing = t.getEnclosingClass();
        return enclosing != null && !enclosing.equals(InversionDemo.class);
    }

    /** Derived test of Martin's part B: does the abstraction name a detail? */
    static List<String> detailsLeakedBy(Class<?> port) {
        List<String> found = new ArrayList<>();
        for (Method m : port.getDeclaredMethods()) {
            Stream.concat(Stream.of(m.getReturnType()),
                          Arrays.stream(m.getParameterTypes()))
                  .filter(InversionDemo::isDetail)
                  .map(t -> t.getEnclosingClass().getSimpleName()
                            + "." + t.getSimpleName())
                  .forEach(found::add);
        }
        return found;
    }

    // ------------------------------------------------------------- section D

    /** The repo's four categories. The category is `given`; the verdict is derived. */
    record Dependency(String name, String category, boolean hasTestDouble) {}

    static final List<Dependency> DEPENDENCIES = List.of(
        new Dependency("feeCalculator",  "in-process",          true),
        new Dependency("ledgerStore",    "local-substitutable", true),
        new Dependency("notifier",       "remote-but-owned",    true),
        new Dependency("paymentGateway", "true-external",       true));

    /** DEEPENING.md:29 — production + test is explicitly enough. */
    static boolean passesTwoAdapterRule(Dependency d) {
        return d.hasTestDouble();
    }

    /** DEEPENING.md:5-25 — only categories 3 and 4 get a port at the interface. */
    static boolean passesCategoryGate(Dependency d) {
        return d.category().equals("remote-but-owned")
            || d.category().equals("true-external");
    }

    // ------------------------------------------------------------------- main

    static void rule(String label, Object value) {
        String dots = ".".repeat(Math.max(1, 38 - label.length()));
        System.out.printf("   %s %s %s%n", label, dots, value);
    }

    public static void main(String[] args) {
        Money amount = Money.of("100.00");

        System.out.println();
        System.out.println("A. Both accept the dependency. Only one inverts it.");
        var naive    = new NaiveTransferService(new StripeGateway());
        var inverted = new TransferService(new StripeGateway());
        rule("naive result", naive.transfer(amount).charged());
        rule("inverted result", inverted.transfer(amount).charged());
        rule("same behaviour", naive.transfer(amount).charged()
                .equals(inverted.transfer(amount).charged()));
        System.out.println();
        for (var svc : List.of(NaiveTransferService.class, TransferService.class)) {
            rule(svc.getSimpleName() + " takes",  ctorParamName(svc));
            rule("  ...and that is an abstraction", invertsItsDependency(svc));
        }
        rule("swap the vendor without editing", invertsItsDependency(
                TransferService.class) ? "TransferService" : "neither");

        System.out.println();
        System.out.println("B. Part B: abstractions must not name details.");
        for (var port : List.of(PaymentPort.class, LeakyPaymentPort.class)) {
            var leaked = detailsLeakedBy(port);
            rule(port.getSimpleName() + " leaks",
                 leaked.isEmpty() ? "nothing" : String.join(", ", leaked));
        }

        System.out.println();
        System.out.println("C. The single-adapter detector.");
        for (var port : Arrays.stream(nested()).filter(Class::isInterface)
                              .sorted(Comparator.comparing(Class::getSimpleName))
                              .toList()) {
            int n = adaptersOf(port).size();
            rule(port.getSimpleName() + " adapters",
                 n + (n < 2 ? "   just indirection" : "   real seam"));
        }

        System.out.println();
        System.out.println("D. Two gates. They do not agree.");
        int byRule = 0, byBoth = 0;
        for (var d : DEPENDENCIES) {
            boolean r = passesTwoAdapterRule(d), c = passesCategoryGate(d);
            if (r) byRule++;
            if (r && c) byBoth++;
            rule(d.name() + " [" + d.category() + "]",
                 (r ? "rule yes" : "rule no ")
                 + (c ? "  gate yes" : "  gate no "));
        }
        rule("dependencies given", DEPENDENCIES.size() + "   given");
        rule("pass the two-adapter rule", byRule + "   derived");
        rule("pass rule AND category gate", byBoth + "   derived");
        rule("ports the rule alone invents", (byRule - byBoth) + "   derived");
        System.out.println();
    }
}
