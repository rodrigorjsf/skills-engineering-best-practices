import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Lesson 0001 — Seam.
 *
 * Two implementations of the same banking rule:
 *
 *   A high-value transfer (over 10,000) is refused during the night window
 *   (22:00–06:00) and must wait for the next business morning.
 *
 * The rule is identical in both. The only difference is whether the dependency
 * on "what time is it" sits behind a seam.
 *
 * Run it:  java SeamDemo.java
 *
 * The point this program makes is not that one version is prettier. It is that
 * the version without a seam can only ever observe the branch that the wall
 * clock happens to be in, while the version with a seam observes both branches
 * in a single run — with no change to the rule itself.
 */
public class SeamDemo {

    static final BigDecimal LIMIT = new BigDecimal("10000");
    static final ZoneId ZONE = ZoneId.of("America/Recife");

    // ------------------------------------------------------------------
    // A. No seam. The dependency is created inside, so there is nowhere to
    //    stand and alter behaviour without editing this class.
    // ------------------------------------------------------------------
    static final class NightGuardNoSeam {

        boolean allows(BigDecimal amount) {
            LocalTime now = LocalTime.now(ZONE);   // <-- hard-wired. No seam.
            return !(isNight(now) && amount.compareTo(LIMIT) > 0);
        }
    }

    // ------------------------------------------------------------------
    // B. Seam at the Clock. Same rule, same branch logic, same line count.
    //    The dependency is now accepted rather than created — which is all a
    //    seam is: a place where you can alter behaviour without editing here.
    // ------------------------------------------------------------------
    static final class NightGuard {

        private final Clock clock;

        NightGuard(Clock clock) {
            this.clock = clock;
        }

        boolean allows(BigDecimal amount) {
            LocalTime now = LocalTime.now(clock);  // <-- observed through the seam.
            return !(isNight(now) && amount.compareTo(LIMIT) > 0);
        }
    }

    static boolean isNight(LocalTime t) {
        return t.isAfter(LocalTime.of(22, 0)) || t.isBefore(LocalTime.of(6, 0));
    }

    // ------------------------------------------------------------------
    // A clock pinned to one instant. This is the adapter that sits at the seam.
    // Two adapters exist — the system clock in production, this one in tests —
    // which is what makes the seam real rather than hypothetical.
    // ------------------------------------------------------------------
    static Clock at(String isoLocalDateTime) {
        return Clock.fixed(
                Instant.parse(isoLocalDateTime),
                ZONE);
    }

    public static void main(String[] args) {
        BigDecimal high = new BigDecimal("25000");

        System.out.println("=".repeat(66));
        System.out.println(" LESSON 0001 — the same rule, with and without a seam");
        System.out.println("=".repeat(66));

        // --- A: try to verify BOTH branches without a seam ---------------
        LocalTime wall = LocalTime.now(ZONE);
        NightGuardNoSeam noSeam = new NightGuardNoSeam();
        boolean observedNoSeam = noSeam.allows(high);

        System.out.println();
        System.out.println("A. NightGuardNoSeam — dependency created inside");
        System.out.printf ("   wall clock right now .......... %s (%s)%n",
                wall.withNano(0), isNight(wall) ? "night" : "day");
        System.out.printf ("   allows(25000) ................. %s%n", observedNoSeam);
        System.out.println("   branch reachable in this run .. " + (isNight(wall) ? "night only" : "day only"));
        System.out.println("   BRANCHES COVERED .............. 1 of 2");
        System.out.println("   the other branch is unreachable until the wall clock moves.");

        // --- B: verify BOTH branches through the seam --------------------
        boolean atNight = new NightGuard(at("2026-08-01T02:30:00Z")).allows(high); // 23:30 local
        boolean atDay   = new NightGuard(at("2026-08-01T13:00:00Z")).allows(high); // 10:00 local

        System.out.println();
        System.out.println("B. NightGuard — dependency accepted at a seam");
        System.out.printf ("   allows(25000) at 23:30 local .. %s   (expected false)%n", atNight);
        System.out.printf ("   allows(25000) at 10:00 local .. %s    (expected true)%n", atDay);
        System.out.println("   BRANCHES COVERED .............. 2 of 2");

        // --- The claim, checked ------------------------------------------
        boolean claimHolds = (atNight == false) && (atDay == true);

        System.out.println();
        System.out.println("-".repeat(66));
        System.out.printf ("Rule verified end to end: %s%n", claimHolds ? "YES" : "NO");
        System.out.println("Rule source lines changed to get there: 0");
        System.out.println("What changed: where the dependency comes from, not what the rule says.");
        System.out.println("-".repeat(66));

        if (!claimHolds) {
            throw new AssertionError("the seamed version failed to prove both branches");
        }
    }
}
