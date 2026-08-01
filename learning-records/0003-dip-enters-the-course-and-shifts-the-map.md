# DIP enters the course — and the map shifts by two

Requested by the reader on 2026-08-01: research dependency inversion, YAGNI and
DRY, place them against everything the course already teaches, and retrofit the
existing lessons. This record captures what the research changed, because it
changed more than it added.

**This is still not a record of the reader's understanding.** No lesson has been
reported back and no quiz answer has been seen. It records a scope decision and
three findings that will bind future lessons.

## The finding that made the lesson

Measured over the 113 files of `../skills/skills` with `grep -rioF`, 2026-08-01:

| Term | Occurrences |
|---|---|
| `dependency inversion` | **0** |
| `SOLID` (the acronym) | **0** — both raw hits are the English word "solid" |
| `DRY` / `don't repeat yourself` | **0** |
| `dependency injection` | 1 |
| `YAGNI` | 1 |
| `seam` | 77 |
| `interface` | 160 |

The repo teaches the **mechanism** (injection) and never the **principle**
(inversion). That is not an oversight to be corrected — it is a position, and the
lesson teaches it as one. The vocabulary the repo invests in answers *where the
interface lives*; DIP answers *who owns it*, which the `seam` entry explicitly
calls a separate design decision and then never makes.

## The repo already answers "when not to invert" — under another name

`codebase-design/DEEPENING.md:5-25` is a four-category decision procedure.
Categories 1 and 2 (in-process, local-substitutable) get **no port at the
external interface**; 3 and 4 (remote-but-owned, true-external) do.
`tdd/mocking.md:3,10-14` draws the same boundary from the test side — *"Mock at
system boundaries only"*, *"Don't mock: … Anything you control."*

**Neither file cites the other. Neither uses the word "inversion." The
composition is ours and is flagged as ours in the lesson.**

## The two-adapter rule does not restrain DIP

This corrects a working assumption this workspace has carried since lesson 0001.

`DEEPENING.md:29` reads: *"Don't introduce a port unless at least two adapters
are justified **(typically production + test)**. A single-adapter seam is just
indirection."* The parenthetical **endorses a test double as the second
adapter** — so any dependency you are willing to write a fake for passes. That
is nearly all of them.

Lesson 0004 found this empirically without explaining it: `Clock` and `FeePolicy`
both passed, and only one belonged in the public constructor. The actual brake is
the **dependency-category taxonomy**; the rule is a filter on seams that already
reached it. Lesson 0006 demonstrates the gap by running both gates over the same
four dependencies: **4 pass the rule, 2 pass the gate.**

An addendum was written into lesson 0004 rather than leaving the empirical
finding unexplained.

## The contradiction the reader asked about does not exist as posed

The reader asked whether YAGNI argues against a seam-oriented discipline. Fowler
scopes himself out of it, verbatim: *"Yagni only applies to capabilities built
into the software to support a presumptive feature, it does not apply to effort
to make the software easier to modify"*, and names `SelfTestingCode` as an
exempt enabling practice. **A seam introduced to make a test possible is outside
YAGNI's scope on Fowler's own text.** The conflict is with folk-YAGNI.

The same shape repeats for DRY: the authors published a retraction of the folk
reading, and the folk reading — "don't duplicate lines" — is what *causes*
premature abstraction. Both are in
`docs/research/11-yagni-dry-and-the-cost-of-abstraction.md`.

## What the map did

Track II gained two entries at its head, and everything from the old 0006 down
**shifted by two**: vertical slice is now **0008**, and the final lesson is
**0022**. Renumbering the map is free; renumbering shipped lessons is not, which
is why the shift went into the plan rather than into the built lessons.

**0007 — YAGNI, DRY and the wrong abstraction** has its evidence gathered and is
**not built**. Building it is the reader's call, per the standing rule that
scaling up is not a silent decision.

## Rules this adds

- **The negative result is part of the deliverable.** `12-indirection-and-agents.md`
  ends with sixteen numbered prohibitions. The most important:
  *"over-abstraction hurts LLM coding agents"* has **no primary source** —
  nothing found varies indirection depth as an independent variable. Lesson 0006
  refuses it in visible text rather than in a footnote. What is measured is
  **localization**, which is a different claim.
- **Locators name the edition, still.** The canonical DRY sentence was read from
  the **2019/2020** edition; the 1999 wording was never fetched. Do not write
  "Hunt & Thomas, 1999" over it.
- **Dates come from inside the artifact when the web disagrees.** Martin's DIP
  article self-dates as *"the third of my Engineering Notebook columns"* after
  *"(Mar, 96)"*. The circulating "May 1996" appears in no artifact and stays
  `[unverified]`.

## Open, and deliberately so

- **Feathers coining `SOLID`** — widely repeated, and Martin's own words were not
  found. Stays `[unverified]`. It would be a real link to the `seam` entry if it
  held, which is exactly why it must not be asserted.
- **`Clean Architecture`'s stable/volatile qualification** — chapter and section
  title located, body text not fetched. Not paraphrased from memory.
- **The reader still has not answered a single quiz.** Lesson 0006's opening
  retrieval question was calibrated blind, like the three before it. See
  [[0002-four-attribution-corrections-and-what-they-changed]].
