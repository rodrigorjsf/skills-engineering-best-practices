# Software-Engineering Literature Map

A verified map from the concepts used in Matt Pocock's `agent-skills` repo back to their primary
literature. Built for someone who will learn these ideas **in Java** and wants to be able to *teach*
them, not just name-drop them.

> **Status:** complete. Every one of the 16 concepts, the Java section, the reading path and the
> bibliography are filled. Claims that could not be checked against a primary or reputable secondary
> source are marked **`[unverified]`** inline, and every such gap is triaged by cost-to-close in the
> appendix at the end of this file.

---

## How to read the evidence tiers

Every claim in this document carries one of four confidence levels. This is not decoration — the
whole point of the document is that you can tell what was checked from what was assumed.

| Tier | Meaning |
| --- | --- |
| **Quoted** | The text was fetched and the quote is verbatim from it. A URL is given. |
| **Metadata verified** | Title / author / publisher / year / ISBN came from a publisher page, DOI, or the author's own site. |
| **Chapter located** | A real table of contents or searchable excerpt was seen. Chapter numbers without this tag are *not* given. |
| **`[unverified]`** | Everything else. Believed true, not confirmed against a primary source. Treat with suspicion. |

Two standing rules:

1. **No guessed locators.** If a table of contents could not be fetched, the chapter number is
   omitted rather than invented. Page numbers for paywalled books are omitted by default.
2. **No smoothing.** An unverified claim stays visibly unverified. It is never rewritten into
   confident prose.

---

## 1. Seam

**Canonical source.** Michael C. Feathers, *Working Effectively with Legacy Code*.
**Publisher: Pearson**, in the **Robert C. Martin Series**. **Copyright year: 2005.** 1st edition,
**464 pages**. **ISBN-10: 0-13-117705-2. ISBN-13: 978-0-13-117705-5.**
**Metadata verified** from the publisher's own store page,
<https://www.informit.com/store/working-effectively-with-legacy-code-9780131177055>.

> **Note on the year.** This book is very commonly cited as **2004**. The publisher's page states a
> **2005** copyright. Both dates circulate; the 2004 date probably reflects first printing. Cite
> **2005** with the publisher page as evidence, or write "2004/2005" — but do not silently print
> 2004 as if verified.

**Chapter location — verified.** From the publisher's table of contents:

- **Part I: The Mechanics of Change** — 1. Changing Software · 2. Working with Feedback ·
  3. Sensing and Separation · **4. The Seam Model** · 5. Tools
- **Part II: Changing Software** — chapters 6–24, each titled as a complaint
  ("I Don't Have Much Time and I Have To Change It", "I Can't Get This Class into a Test Harness", …)
- **Part III: Dependency Breaking Techniques** — 25. Dependency Breaking Techniques
- Appendix: Refactoring · Glossary

**Chapter located** — so **"The Seam Model" is Chapter 4**, confirmed against a publisher TOC rather
than recollection.

**The definition, verbatim:**

> "A seam is a place where you can alter behavior in your program without editing in that place."

**Quoted.** (Already settled upstream of this document; corroborated here by a secondary source that
explicitly marks it as a direct quotation from the book,
<https://tarpitstories.blogspot.com/2017/05/legacy-code-concepts-ii-seam-model.html>, and by Martin
Fowler's bliki entry "LegacySeam", 4 January 2024.)

### Enabling point — resolved as far as the sources allow

Feathers pairs every seam with an **enabling point**: *the place where you can decide which of the
two behaviours is used* — the seam is where behaviour **can** change, the enabling point is where you
**choose**. Feathers' rule is that a seam without an enabling point is not usable as a seam.

**`[unverified]` as verbatim.** The wording most often reproduced is *"Every seam has an enabling
point, a place where you can make the decision to use one behavior or another."* I could not confirm
this as a direct quotation. **Where I looked:** the InformIT publisher page (TOC only, no body text);
the O'Reilly TOC (**HTTP 403** to automated fetch — this is the blocker the coordinator hit, and it
is still blocking); and two secondary write-ups, of which
<https://tarpitstories.blogspot.com/2017/05/legacy-code-concepts-ii-seam-model.html> **explicitly
marks its enabling-point wording as the blogger's paraphrase, not a quotation.**

**Recommendation for the published lesson:** the *concept* of the enabling point is safe to teach and
is consistently reported across independent sources — you can drop the `[unverified]` flag from the
**idea**. Keep the flag on any **sentence presented as Feathers' words**, or simply state it in your
own words and cite Chapter 4. That removes the flag without asserting a quotation you cannot back.

### The seam taxonomy

Feathers classifies seams by *when* the substitution happens — build time, link time, or run time.
**`[unverified]` for all three descriptions below**: they are reconstructed from consistent secondary
sources, not from the book's text, and the blog that carries them marks them as its own summary. The
*names* are Feathers'; the *phrasing* is not.

| Seam type | Where it lives | When behaviour is chosen |
| --- | --- | --- |
| **Preprocessing seam** | C/C++ only — `#define` / `#include` | **Build time**, before the compiler runs. A macro preprocessor rewrites the code, so a call can be redirected to different code without touching the call site. |
| **Link seam** | The linker / build assembly | **Link time** — swap one compiled library or object file for another when the binary is assembled. Also covers dynamic resolution by the runtime. |
| **Object seam** | Method calls in OO languages | **Run time** — the method that actually executes is changed via subclassing or an interface, without editing the caller. |

**For a Java reader, the object seam is the one that matters.** Java has no preprocessor, so
preprocessing seams do not exist; link seams exist but are awkward (classpath ordering, shading,
alternate jars). **Object seams are the Java default**, which is why the entire Java testing
ecosystem — dependency injection, interfaces, `java.time.Clock` — is built around them. This is worth
saying explicitly in a lesson: *two of Feathers' three seam types are essentially unavailable to you,
and that is why Java design pushes so hard on interfaces.*

### How the repo uses "seam" — and where it adds something

The repo attributes the term correctly:

> "**Seam** _(Michael Feathers)_ — a place where you can alter behaviour without editing in that
> place; the *location* at which a module's interface lives."

**Quoted** — `skills/engineering/codebase-design/SKILL.md`, upstream
<https://github.com/mattpocock/skills> at `2ab9580`.

Two genuine extensions beyond Feathers, both worth teaching as extensions rather than as Feathers:

1. **"The *location* at which a module's interface lives."** Feathers' seam is a *testability*
   concept — a place to substitute behaviour in code you did not design for testing. The repo
   promotes it to a *design* concept: where you **choose** to put the interface, up front. That is a
   real conceptual move and the repo's own.
2. **"One adapter means a hypothetical seam. Two adapters means a real one."** (**Quoted**, same
   file.) I found no precedent for this rule in Feathers. It is a crisp anti-speculation heuristic
   and it maps onto Fowler's *Speculative Generality* smell (§9). Treat it as **the repo's
   contribution**.

The repo's `tdd` skill uses "seam" in a third, narrower sense — "the public boundary you test at"
(**Quoted**) — and adds the rule "**No test is written at an unconfirmed seam**" (**Quoted**). Note
for teaching: **the repo uses one word for three related ideas** (Feathers' substitution point, the
design-time interface location, and the test observation point). That is not a defect, but a lesson
should name all three or the reader will conflate them.

## 2. Deep vs shallow modules, information hiding, complexity

This is the concept with the most tangled genealogy in the whole repo, and the one place where the
repo's own attribution needs correcting. Read 2a → 2b → 2c in order.

### 2a. Parnas 1972 — the foundational paper

**Canonical source.** D. L. Parnas, "On the Criteria To Be Used in Decomposing Systems into
Modules", *Communications of the ACM*, Vol. 15, No. 12, December 1972, pp. 1053–1058.
DOI [10.1145/361598.361623](https://dl.acm.org/doi/10.1145/361598.361623). **Metadata verified**
(ACM Digital Library record).

**What was actually read.** The version fetched and quoted below is the earlier Carnegie-Mellon
University technical report of the same title, dated **August 1971**, prepared for the Air Force
Office of Scientific Research, mirrored at
<http://rule11.tech/papers/1971-decomposing-parnas.pdf>. The CACM 1972 paper is the published
version of this report. Quotes are therefore **Quoted** from the 1971 CMU report, not from the CACM
typesetting; wording between the two is believed identical but that was not diffed. Anyone citing a
page number should cite the CACM version and check it. **`[unverified]`** — I could not fetch the
CACM full text (cacm.acm.org returned HTTP 403 to automated fetch).

**The criterion, in Parnas's own words.** This is the sentence the entire discipline rests on:

> "Every module in the second decomposition is characterized by its knowledge of a design decision
> which it hides from all others. Its interface or definition was chosen to reveal as little as
> possible about its inner workings."

**Quoted** — 1971 CMU report, section "The Criteria".

Note what that sentence does *not* say. It says nothing about size, nothing about line counts,
nothing about method counts. The unit of modularity is **a design decision**, and the property that
matters is **what the interface refuses to reveal**.

**The term "information hiding" is Parnas citing himself, not coining it here.** In the section
"The Criteria" he writes:

> "The second decomposition was made using \"information hiding\" [4] as a criteria."

**Quoted**. Reference [4] in that paper is *Parnas, D. L., "Information Distribution Aspects of
Design Methodology", Technical Report, Department of Computer Science, Carnegie-Mellon University,
Pittsburgh, Pa., 1971. Also to be presented at the IFIP Congress 1971, Ljubeljana, Yugoslavia.*
**Quoted** (reference list). So the phrase pre-dates the famous paper and belongs to Parnas's own
earlier tech report. If you want to be precise when teaching: *the criterion is stated in the 1972
CACM paper; the term is introduced in a 1971 companion report.*

**The paper's design method, verbatim from the conclusion:**

> "We have tried to demonstrate by these examples that it is almost always incorrect to begin the
> decomposition of a system into modules on the basis of a flowchart. We propose instead that one
> begins with a list of difficult design decisions or design decisions which are likely to change.
> Each module is then designed to hide such a decision from the others. Since, in most cases,
> design decisions transcend time of execution, modules will not correspond to steps in the
> processing."

**Quoted** — 1971 CMU report, "CONCLUSION".

**The two modularizations.** Parnas builds a KWIC (Key Word In Context) index two ways.
*Modularization 1* makes each major processing step a module — "One might say that to get the first
decomposition one makes a flowchart" (**Quoted**). *Modularization 2* hides a design decision per
module. His demonstration of why the second wins:

> "In the first decomposition the format of the line storage in core must be used by all of the
> programs. In the second decomposition the story is entirely different. Knowledge of the exact way
> that the lines are stored is entirely hidden from all but module 1. Any change in the manner of
> storage can be confined to that module!"

**Quoted**.

**Why this matters for the skills repo.** The repo's **Interface** definition — "everything a
caller must know to use the module correctly: the type signature, but also invariants, ordering
constraints, error modes, required configuration, and performance characteristics" — is a direct
descendant of Parnas's "chosen to reveal as little as possible about its inner workings". Parnas
even anticipates the repo's strictness: he criticises his *own* circular-shifter interface for
leaking an ordering guarantee callers did not need —

> "While we have carefully hidden the method of storing or calculating the list of circular shifts,
> we have indicated an order to that list. […] By prescribing the order for the shifts we have
> given more information than necessary and so unnecessarily restricted the class of systems that
> we can build without changing the definitions."

**Quoted**. That is *exactly* the repo's claim that ordering constraints are part of the interface.
It is the strongest available evidence that the repo's unusually broad definition of "interface"
has a 1971 pedigree rather than being an invention.

**Evolution and critique.** Information hiding fed into abstract data types, then into
encapsulation in OO languages, and — via Parnas's own "On the Design and Development of Program
Families" (IEEE TSE, 1976) — into software product lines. **`[unverified]`** — I did not fetch the
1976 paper; title and venue are from memory and should be checked before citing. A standard
critique is that "hide the decisions likely to change" requires predicting change correctly, and
mispredicted change produces abstraction that costs without paying (this is the same objection
Fowler's *Speculative Generality* smell encodes). **`[unverified]`** — this critique is my
synthesis, not a quote from a named critic.

### 2b. Ousterhout — A Philosophy of Software Design

**Canonical source.** John Ousterhout, *A Philosophy of Software Design*. 2nd edition released
**July 2021**, ISBN **173210221X**. **Metadata verified** from the author's own book page,
<https://web.stanford.edu/~ouster/cgi-bin/book.php>. The publisher is commonly given as Yaknyam
Press and the 1st edition as 2018 — **`[unverified]`**, the author's own page states neither. Do
not print a publisher or a 1st-edition year without checking.

The 2nd edition adds a chapter titled **"Decide What Matters"** and expands the chapter on
general-purpose modules; the author's page states the 2nd edition also adds comparisons with Robert
Martin's *Clean Code*, noting "significant differences of opinion on topics such as the length of
methods and the role of comments" (**Quoted**, book page). Chapter *numbers* are **not** given here
— I could not fetch a real table of contents. **`[unverified]`**.

**Ousterhout's own definition of deep and shallow.** These are from text Ousterhout himself wrote
and published in the debate document (see 2c), so they are his words, not a summariser's:

> "The best methods are those that provide a lot of functionality but have a very simple interface:
> they replace a large cognitive load (reading the detailed implementation) with a much smaller
> cognitive load (learning the interface)."

> "I call these interfaces 'shallow': they don't help much in terms of reducing what the programmer
> needs to know."

**Quoted** — <https://github.com/johnousterhout/aposd-vs-clean-code>.

#### An open question: does Ousterhout actually frame depth as a ratio?

`skills/engineering/codebase-design/SKILL.md` lists under **Rejected framings**:

> "**Depth as ratio of implementation-lines to interface-lines** (Ousterhout): rewards padding the
> implementation. We use depth-as-leverage instead."

**Quoted** — the local repo, `skills/engineering/codebase-design/SKILL.md:107`, upstream
<https://github.com/mattpocock/skills> at commit `2ab9580`.

**This is flagged as an open question, not a finding.** Here is exactly what I have and what I do
not.

**What I have.** In the Ousterhout text I could fetch — the two definitions quoted above, which are
his own writing — depth is framed as a **cognitive-load cost/benefit judgement**: benefit is the
functionality a module provides, cost is what a caller must learn to use it, and a module is deep
when the first greatly exceeds the second. No formula appears, and no line counting.

**What I do not have.** The source those quotes come from is a debate document **about method
length**; it is not APOSD's chapter on module depth. It therefore **cannot settle** how the book
itself measures depth. A secondary characterisation in my fetched material stated that Ousterhout
"does not provide a formal mathematical formula" — but that is a **summarising model's wording, not
Ousterhout's**, and I am not treating it as evidence. **I could not read the book (paywalled).**

**Assessment, stated at the strength the evidence supports:** on the sources I could fetch, the
repo's "Rejected framings" entry targets a framing I **could not find in Ousterhout's own words**.
Whether APOSD states something closer to a ratio in a chapter I could not read is **unresolved**.
**`[unverified]`.**

What *can* be said firmly is the positive half: the repo's depth-as-leverage definition — "the
amount of behaviour a caller (or test) can exercise per unit of interface they have to learn" —
is **very close to the Ousterhout formulation quoted above**, arguably a restatement of it in
different vocabulary. On the idea itself, the repo and Ousterhout appear to agree.

One further caveat worth recording: Ousterhout is widely reported to illustrate depth with a
diagram of rectangles where **area** represents functionality and the **top edge** represents the
interface. If so, a reader could reasonably compress that picture into "ratio", which would make the
repo's characterisation a *plausible reading of a diagram* rather than an invention.
**`[unverified]`** — the diagram is from recollection; I did not see it.

**How to teach it.** Do not tell the reader the repo is wrong — the evidence does not support that
verdict. Tell them: *the repo and Ousterhout appear to want the same thing; the repo's rejection
targets a metric I could not find in Ousterhout's own words; and the interesting question is why a
line-ratio reading is so tempting.* The answer to that last question is the genuinely useful part —
a ratio is measurable and a cognitive load is not, and engineers reach for the measurable proxy
every time. That is a lesson worth having, and it does not depend on the open question resolving
either way.

**Partial close — corroborating secondary evidence.** Multiple independent write-ups of APOSD
**Chapter 4, "Modules Should Be Deep"** describe the framing consistently, and none describes a
line-count ratio:

- The cost/benefit pair is stated as **"the benefit provided by a module is its functionality and the
  cost of a module is its interface"** — a module is worth it when interface cost is much lower than
  functionality benefit.
- The rectangle diagram is real: **the top edge represents the interface and its length indicates
  interface complexity**, while the vertical dimension represents functionality provided.

**Chapter located** for "Modules Should Be Deep" = Chapter 4; **`[unverified]`** as verbatim — these
are **multiple independent secondary summaries**, not the book. What they establish is that the
*published, widely-read* account of APOSD's depth chapter is **qualitative cost/benefit plus a
diagram**, with no formula. That materially raises confidence that the repo's ratio characterisation
is a compression of the **diagram** rather than of a stated metric — but it still does not license
asserting that Ousterhout never quantifies anywhere in the book.

**To fully close it**, Ousterhout's own book page advertises a **free 2nd-edition extract PDF**, and
his **Stanford CS190** course materials are public. The discriminating question stays narrow: *does
APOSD's depth discussion quantify, or only illustrate?* Current best answer, on secondary evidence:
**it illustrates.**

### 2c. The Ousterhout vs Robert C. Martin debate

**This is real, primary, and quotable.** Canonical source:
<https://github.com/johnousterhout/aposd-vs-clean-code>, a repository maintained by Ousterhout
containing the edited exchange. Its own framing:

> "(This document is the result of a series of discussions, some online and some in person, held
> between Robert 'Uncle Bob' Martin and John Ousterhout between September 2024 and February 2025)"

**Quoted**.

**Method length.** Martin's position:

> "If I can *meaningfully* extract one method from another, then the original method did more than
> one thing."

Ousterhout's counter introduces **entanglement** as the cost of over-decomposition:

> "When decomposed methods are entangled, they are harder to read than if they were not decomposed,
> and this defeats the whole purpose of decomposition."

**Quoted** (both). Martin argues functions should be very small — on the order of two to four lines
— and relies on programmer judgement to prevent abuse; Ousterhout argues decomposition is only
justified when it actually reduces total cognitive load. **Paraphrase, not verbatim** for the "two
to four lines" figure.

**Comments.** Martin:

> "Comments are always failures. We must have them because we cannot always figure out how to
> express ourselves without them."

Ousterhout:

> "Missing comments cost far more time than incorrect comments...I waste enormous amounts of time
> wading through code."

**Quoted** (both). Ousterhout states he writes roughly 5–10× more comments than Martin.
**Paraphrase, not verbatim.**

**TDD.** Ousterhout's objection is that TDD forces thinking in units too small to design well — he
argues for writing a larger unit ("a few methods or a class") before testing, so that a complete
design problem can be addressed at once, and says he finds "serious problems without any
compensating advantages" in TDD's tiny-increment discipline. Martin's reply is that scale matters
but that a disciplined developer reaches equally good designs either way, because strategic design
thinking is independent of tactical methodology. **Quoted** for the phrase "serious problems
without any compensating advantages"; **paraphrase, not verbatim** for the rest — the TDD section
was truncated in the fetched rendering and should be re-read in full before quoting further.
**`[unverified]`** — the full TDD exchange.

**Where they agree** (per the document's own closing section): that modular decomposition matters
for managing complexity; that over-decomposition is possible (Martin concedes this in *Clean Code*
2nd ed.); that code readability for future readers is the goal; that unit testing is important
(the dispute is method, not value); and that design requires judgement beyond rules.
**Paraphrase, not verbatim.**

**Why this belongs in the course.** The repo sits inside this debate whether it says so or not. It
takes Ousterhout's side on depth, takes a *third* position on refactoring (see §3, where the repo
removes refactoring from the TDD loop entirely), and its "one lesson, one thing" test discipline is
closer to Ousterhout's skepticism of tiny increments than to Martin's. Teaching the debate gives
the reader the map they need to argue about any of it.

## 3. TDD and red-green-refactor

**Canonical source.** Kent Beck, *Test-Driven Development: By Example*, Addison-Wesley, 2002. An
ACM Digital Library "Guide books" record exists at <https://dl.acm.org/doi/10.5555/579193>
(**Metadata verified** — title, author, year from that record). ISBN and exact publisher imprint
**`[unverified]`** — I did not fetch a publisher page; do not print an ISBN for this book without
checking one.

Beck's loop is conventionally stated as **red → green → refactor**: write a failing test, write the
minimum code to pass it, then refactor. Beck also states two governing rules — roughly "write new
code only if an automated test has failed" and "eliminate duplication". **`[unverified]`** — the
exact wording of both rules and of the loop is from memory, not from fetched text. Quote the book
directly before putting these in a lesson as quotations; you own the book's ideas here, not its
sentences.

The most useful *independent* characterisation of the loop I could verify verbatim comes from a
peer-reviewed paper describing the technique:

> "Test-driven development (TDD) is a technique that repeats short coding cycles interleaved with
> testing. The developer first writes a unit test for the desired functionality, followed by the
> necessary production code, and refactors the code."

**Quoted** — Fucci et al., <https://arxiv.org/abs/1611.05994>. Useful precisely because it is not a
TDD advocate's framing.

### The repo's deviation: refactoring is removed from the loop

`skills/engineering/tdd/SKILL.md` states:

> "**Refactoring is not part of the loop.** It belongs to the review stage (see the `code-review`
> skill), not the red → green implementation cycle."

**Quoted** — local repo `skills/engineering/tdd/SKILL.md`, upstream
<https://github.com/mattpocock/skills> at `2ab9580`.

This is a **real and deliberate departure from Beck**, and it should be taught as one, not glossed
over. Three things to say about it honestly:

1. **It is a minority position.** Every canonical statement of TDD includes refactoring in the
   cycle. Removing it makes the loop red → green only.
2. **There is empirical cover for it, but not the cover you would expect.** Fucci et al. 2017 found
   refactoring effort **negatively** associated with both quality and productivity (see §3b) — the
   authors themselves flag this as unexpected and attribute it to "possible prevalence of mixed
   refactoring" (**Quoted**). That is *not* evidence that refactoring is bad; it is evidence that
   refactoring tangled up with behaviour change is bad. The repo's move — separate the refactor
   from the behaviour-change cycle and do it as a distinct reviewed step — is arguably a *response
   to exactly that failure mode*. Flag this as **our own inference**, not as the paper's claim.
3. **The agent-specific rationale is the repo's, and is unsourced.** The plausible motivation is
   that an LLM told to "refactor" mid-cycle will make unrequested sweeping edits. That reading is
   **`[unverified]`** — the repo does not state a reason.

Note also the repo's **horizontal slicing** anti-pattern — "writing all tests first, then all
implementation… Bulk tests verify _imagined_ behavior" (**Quoted**, same file) — and its
prescription of **vertical slices**, each test a **tracer bullet**. That vocabulary is traced in §6.

### 3a. Chicago (classicist) vs London (mockist)

**Canonical source for the distinction.** Martin Fowler, "Mocks Aren't Stubs",
<https://martinfowler.com/articles/mocksArentStubs.html>. Fowler distinguishes **classical** TDD
(use real objects where practical, substitute only where awkward; verify by inspecting **state**
after exercise) from **mockist** TDD (substitute collaborators with mocks; verify by asserting on
the **interactions**). Fowler states his own leaning is classical. **`[unverified]`** — I did not
fetch this article in this session; the characterisation above is from memory. Fetch it before
quoting. It is free and short, so this gap is cheap to close.

**GOOS.** Steve Freeman & Nat Pryce, *Growing Object-Oriented Software, Guided by Tests*,
Addison-Wesley, 2009 — the book-length statement of the London/mockist, outside-in approach, and
the source of the "listen to the tests" heuristic and the walking-skeleton-first practice (§6).
**`[unverified]`** — metadata from memory; not fetched.

**Where the school names come from** is genuinely murky. "London school" and "Detroit/Chicago
school" circulated in the XP/mailing-list community before appearing in print, and I could not
locate a primary coinage. **`[unverified] — no primary source found.** Do not attribute the naming
to a specific person in a lesson.

**Relevance to the repo.** The repo lands firmly **classicist**: `mocking.md` says mock only at
system boundaries — "External APIs (payment, email, etc.)", "Time/randomness" — and "Don't mock:
Your own classes/modules, Internal collaborators, Anything you control" (**Quoted**, local repo
`skills/engineering/tdd/mocking.md`). Its **implementation-coupled** anti-pattern — "mocks internal
collaborators… The tell: the test breaks when you refactor but behavior hasn't changed"
(**Quoted**) — is precisely the classicist objection to mockist TDD. That is the cleanest
school-alignment claim you can make about the repo, and it is well supported by its own text.

### 3b. What the empirical evidence actually says

**Read this section before arguing for TDD in front of skeptics.** The honest summary is: *the
evidence is real but weaker and more equivocal than TDD advocacy suggests, and the single
best-designed dissection found that the "test-first" part specifically does not matter.*

#### The favourable industrial evidence

**Nagappan, Maximilien, Bhat & Williams, "Realizing quality improvement through test driven
development: results and experiences of four industrial teams", *Empirical Software Engineering*
13(3), 2008, pp. 289–302.** DOI [10.1007/s10664-008-9062-z](https://doi.org/10.1007/s10664-008-9062-z).
Author PDF: <https://www.microsoft.com/en-us/research/wp-content/uploads/2009/10/Realizing-Quality-Improvement-Through-Test-Driven-Development-Results-and-Experiences-of-Four-Industrial-Teams-nagappan_tdd.pdf>.
**Metadata verified** and **Quoted** from that PDF.

Abstract, verbatim:

> "Case studies were conducted with three development teams at Microsoft and one at IBM that have
> adopted TDD. The results of the case studies indicate that the pre-release defect density of the
> four products decreased between 40% and 90% relative to similar projects that did not use the TDD
> practice. Subjectively, the teams experienced a 15–35% increase in initial development time after
> adopting TDD."

This is the study everyone cites. Cite it *with* the following, all of which comes from the paper
itself:

- **Design: case studies, not controlled experiments.** The authors say so explicitly and call them
  "N = 1 experiments". They also concede: "there can never be an accurate equal comparison between
  two projects except in a controlled case study" (**Quoted**).
- **Sample: four teams.** Team sizes 9 (IBM), 6 (MS Windows), 5–8 (MS MSN), 7 (MS VS). Projects
  ranged 6 KLOC to 155.2 KLOC. **Quoted** from Tables 1–2.
- **The defect-density figures are ratios against anonymised baselines** (reported as 0.61W, 0.38X,
  0.24Y, 0.09Z against comparable non-TDD teams W/X/Y/Z), i.e. reductions of roughly 39%, 62%, 76%
  and 91%. **Quoted** from Table 3.
- **The development-time figure is not measured — it is management opinion.** Per-team: 15–20%,
  25–35%, 15%, 20–25%, described in the paper as "subjectively estimated by management"
  (**Quoted**). Anyone quoting "35% slower" as a measurement is over-reading it.
- **Threats the authors state themselves:** possible Hawthorne/motivation effect ("Developers using
  TDD might have been more motivated to produce higher quality code as they were trying out a new
  process"); and a serious confound — the TDD projects were greenfield ("written completely or
  mostly from scratch") while some comparison projects were enhancements to legacy systems, which
  the authors admit could push the comparison either way. **Quoted.**
- **The authors explicitly disclaim generalisation:** "we cannot assume a priori that the results of
  a study generalize beyond the specific environment in which it was conducted". **Quoted.**

#### The meta-analytic picture

**Rafique & Misic, "The Effects of Test-Driven Development on External Quality and Productivity: A
Meta-Analysis", *IEEE Transactions on Software Engineering* 39(6), 2013, pp. 835–856.** Pooled 27
studies. Finding: **a small positive effect on quality, and little to no discernible effect on
productivity**; both the quality gain and the productivity drop were substantially larger in
*industrial* studies than in *academic* ones. **Metadata verified** via search result summary and
the ResearchGate record; **`[unverified]`** for the exact pooled effect sizes — I did not fetch the
paper, so do not print a numeric effect size from this study.

**Munir, Moayyed & Petersen, "Considering rigor and relevance when evaluating test driven
development: A systematic review", *Information and Software Technology* 56(4), 2014, pp. 375–394.**
DOI [10.1016/j.infsof.2014.01.002](https://doi.org/10.1016/j.infsof.2014.01.002) **`[unverified]`**
(DOI from memory). Their result, as reported verbatim by Ghafari et al. 2020:

> "They found that studies with a high rigor and relevance scores show clear results for improvement
> in external quality at the price of degrading productivity."

**Quoted** (via Ghafari et al., see below — a secondary report of a primary finding; labelled as
such deliberately).

That is probably the fairest one-sentence summary available: **better external quality, slower.**

#### The result that most challenges TDD

**Fucci, Erdogmus, Turhan, Oivo & Juristo, "A Dissection of the Test-Driven Development Process:
Does It Really Matter to Test-First or to Test-Last?", *IEEE Transactions on Software Engineering*
43(7), July 2017, pp. 597–614.** Preprint: <https://arxiv.org/abs/1611.05994>. **Metadata verified**;
abstract **Quoted**:

> "We formulate four process characteristic: sequencing, granularity, uniformity, and refactoring
> effort. We investigate how these characteristics impact quality and productivity in TDD and
> related variations. Method: We analyzed 82 data points collected from 39 professionals… Result:
> Quality and productivity improvements were primarily positively associated with the granularity
> and uniformity. **Sequencing, the order in which test and production code are written, had no
> important influence.** Refactoring effort was negatively associated with both outcomes… Conclusion:
> **The claimed benefits of TDD may not be due to its distinctive test-first dynamic, but rather due
> to the fact that TDD-like processes encourage fine-grained, steady steps that improve focus and
> flow.**"

(Emphasis added; wording otherwise verbatim.)

Note the sample: **39 professionals, 82 data points** — professionals, not students, which makes
this stronger than most of the literature.

**This is the single most important finding in the section.** If it holds, the defensible claim is
not "write your tests first" but **"work in small, uniform increments"** — and test-first is one way
(not the only way) to force that. A course that teaches TDD honestly should teach *that* as the
mechanism.

A related result from the same group: **Fucci et al., "A Longitudinal Cohort Study on the Retainment
of Test-Driven Development"** — students over five months; as summarised by Ghafari et al., they
"showed that adoption of TDD only results in writing more tests; otherwise it has neither
statistically significant effect on the external quality of software products nor on the developers'
productivity" (**Quoted** via Ghafari et al.). Preprint: <https://arxiv.org/pdf/1807.02971>.
**`[unverified]`** — venue and year not confirmed.

#### Why the literature disagrees with itself

**Ghafari, Gross, Fucci & Felderer, "Why Research on Test-Driven Development is Inconclusive?",
ESEM '20 (ACM/IEEE International Symposium on Empirical Software Engineering and Measurement),
October 8–9 2020, Bari, Italy.** DOI
[10.1145/3382494.3410687](https://doi.org/10.1145/3382494.3410687). Preprint:
<https://arxiv.org/pdf/2007.09863>. **Metadata verified** and **Quoted** from the preprint.

Abstract, verbatim:

> "[Background] Recent investigations into the effects of Test-Driven Development (TDD) have been
> contradictory and inconclusive. This hinders development teams to use research results as the
> basis for deciding whether and how to apply TDD. [Aim] … we aim at identifying the reasons behind
> the inconclusive research results in TDD. [Method] We studied the state of the art in TDD research
> published in top venues in the past decade, and analyzed the way these studies were set up.
> [Results] We identified five categories of factors that directly impact the outcome of studies on
> TDD."

Their diagnosis, verbatim from the discussion:

> "we found that the exact definition of TDD that a study follows is not always clear; the
> participants of the studies are often newcomers to this technique and experiments with TDD
> proficient participants are in a minority; experiments mainly focus on code generation in
> greenfield projects, and the opportunities to adopt TDD in an existing codebase is not
> investigated; the baseline practice against which TDD is compared should share similar agile
> characteristics"

**Quoted.**

They also relay Causevic et al.'s seven factors limiting industrial adoption: "increased development
time, insufficient TDD experience/knowledge, lack of upfront design, domain and tool specific issues,
lack of developer skill in writing test cases, insufficient adherence to TDD protocol, and legacy
code" (**Quoted**).

#### How to state the evidence honestly

If you are going to argue this in front of skeptics, here is a defensible position that no cited
study contradicts:

1. **Quality: modest, real, better-supported than productivity.** Industrial case studies report
   large defect-density reductions; the pooled meta-analysis reports a *small* positive quality
   effect. Both point the same direction. The magnitude claim should be "small to moderate", not
   "40–90%".
2. **Productivity: probably a cost, not a benefit.** Meta-analysis: no discernible effect overall;
   high-rigour studies: quality improves "at the price of degrading productivity"; the Microsoft/IBM
   teams' own managers estimated 15–35% more initial development time.
3. **The test-*first* ordering specifically has weak support.** The best-designed dissection found
   sequencing had "no important influence"; granularity and uniformity carried the effect.
4. **Most studies use students on greenfield toy tasks with novice TDD practitioners**, and often
   compare TDD against an ill-specified baseline. This is the field's own self-criticism, not mine.
5. **Nobody has good evidence about TDD on legacy codebases** — explicitly named as a gap by Ghafari
   et al. This matters, because that is where most professional work happens.

The strongest honest sales pitch for the repo's TDD skill is therefore **not** "studies prove TDD
produces better code". It is: *small uniform increments with a fast pass/fail signal are what the
evidence supports, and test-first is a reliable forcing function for getting them.* That claim
survives contact with the literature. The stronger one does not.

## 4. Test doubles taxonomy

**Canonical source.** Gerard Meszaros, *xUnit Test Patterns: Refactoring Test Code*, Addison-Wesley,
2007. **`[unverified]`** — publisher/year/ISBN not fetched; Meszaros's companion site
<http://xunitpatterns.com> was **unreachable** (connection refused) during this session, so the
canonical definitions could not be taken from the author directly. **That is where I looked.**

**What I could verify.** Martin Fowler's bliki entry "TestDouble",
**published 17 January 2006**, <https://martinfowler.com/bliki/TestDouble.html>, in which Fowler
**explicitly attributes the taxonomy to Gerard Meszaros**. All of the following are **Quoted** from
Fowler:

> "Test Double is a generic term for any case where you replace a production object for testing
> purposes."

| Kind | Fowler's definition (verbatim) |
| --- | --- |
| **Dummy** | "objects are passed around but never actually used. Usually they are just used to fill parameter lists." |
| **Fake** | "objects actually have working implementations, but usually take some shortcut which makes them not suitable for production." |
| **Stub** | "provide canned answers to calls made during the test, usually not responding at all to anything outside what's programmed in for the test." |
| **Spy** | "are stubs that also record some information based on how they were called." |
| **Mock** | "are pre-programmed with expectations which form a specification of the calls they are expected to receive." |

**The distinction that actually matters** is the last one: a **mock** carries *expectations about
interactions* and can fail the test by itself; a **stub** only supplies data and the assertion lives
in the test body. This is the same axis as classicist-vs-mockist in §3a: mocks enable **behaviour
verification**, stubs support **state verification**.

**Why it matters for the repo.** The repo says "mock" in `mocking.md` but what it describes at
system boundaries is usually a **stub** or a **fake** in Meszaros's vocabulary; and its
`codebase-design/SKILL.md` explicitly names an **in-memory fake** ("a large adapter with a small
implementation (an in-memory fake)", **Quoted**). Its `DEEPENING.md` is more precise still, naming
local test stand-ins such as PGLite for Postgres — those are **fakes**. Teaching the five-term
taxonomy is therefore genuinely corrective: it lets the reader see that the repo's "don't mock your
own collaborators, do use an in-memory adapter" advice is really *"prefer fakes over mocks"*, which
is a well-established position, not an idiosyncrasy.

**Critique of the taxonomy** worth mentioning: the five terms are widely cited but inconsistently
used in practice (most developers say "mock" for all five, and mocking *libraries* are named after
the term that fits them least). Fowler's article exists precisely because of that confusion.

## 5. Ubiquitous Language, Bounded Context, Context Map, Domain Model

**Why this section carries extra weight.** The skills repo *uses* all four of these terms and
*defines* none of them. `skills/engineering/domain-modeling/SKILL.md` says "Use the format in
[CONTEXT-FORMAT.md]" and tells the agent to maintain a glossary, and
`skills/engineering/codebase-design/SKILL.md` warns that "boundary" is "overloaded with DDD's
bounded context" — but nowhere does the repo say what a bounded context *is*. **Evans has to supply
the definitions; the repo supplies only the file format.** That is the gap this section fills.

**Canonical source.** Eric Evans, *Domain-Driven Design: Tackling Complexity in the Heart of
Software*, Addison-Wesley, 2003. ISBN 0-321-12521-5 / 978-0-321-12521-7 — **Metadata verified** from
the Pearson media path for the publisher's own sample
(`ptgmedia.pearsoncmg.com/images/9780321125217/samplepages/0321125215.pdf`, where both the ISBN-13
and ISBN-10 appear in the URL structure). I could **not** extract readable text from that sample —
it uses subset font encodings — so **no chapter numbers are given below**. **`[unverified]`** for all
chapter-level locators. Ubiquitous Language is in the early model-driven-design material; Bounded
Context and Context Map are in the Strategic Design part (commonly cited as Part IV) — **stated
without a verified TOC, so do not print it as fact.**

**Evans's free companion.** Evans published a *Domain-Driven Design Reference* — a condensed
pattern-definition booklet — at
<https://www.domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf>. It is
released under a **Creative Commons Attribution** licence (the licence URL is embedded in the PDF).
**Metadata verified** by downloading the file. Its body text also uses a custom font encoding that
defeated automated extraction, so **it is not quoted here** — but it is the single best free primary
source for these definitions and is worth opening by hand. **`[unverified]`** for its exact contents.

### Ubiquitous Language

The most reliable *fetchable* statement, from Martin Fowler's bliki entry "UbiquitousLanguage",
**31 October 2006**, <https://martinfowler.com/bliki/UbiquitousLanguage.html> (**Quoted**):

> "Ubiquitous Language is the term Eric Evans uses in Domain Driven Design for the practice of
> building up a common, rigorous language between developers and users."

Fowler quotes Evans on the *obligation the practice puts on both sides*:

> "Domain experts should object to terms or structures that are awkward or inadequate to convey
> domain understanding; developers should watch for ambiguity or inconsistency that will trip up
> design."

**Quoted** (Evans, via Fowler). This is the sentence to teach, because it is the one the repo
operationalises: `domain-modeling/SKILL.md` instructs the agent to **"Challenge against the
glossary"** and **"Sharpen fuzzy language"** — "Your glossary defines 'cancellation' as X, but you
seem to mean Y — which is it?" and "You're saying 'account' — do you mean the Customer or the User?
Those are different things." (**Quoted**, local repo). That is Evans's "watch for ambiguity or
inconsistency" turned into an agent behaviour. The lineage is direct and defensible.

Note also the repo's `_Avoid_:` convention in `CONTEXT.md` — recording the *rejected* synonyms
alongside the chosen term ("_Avoid_: backlog manager, backlog backend, issue host", **Quoted** from
the repo's own `CONTEXT.md`). I found **no precedent for this in Evans**; it appears to be the
repo's own addition and a genuinely good one. Flag it as **the repo's contribution**, not DDD.

### Bounded Context

From Fowler's "BoundedContext", **15 January 2014**,
<https://martinfowler.com/bliki/BoundedContext.html> (**Quoted**):

> "Bounded Context is a central pattern in Domain-Driven Design. It is the focus of DDD's strategic
> design section which is all about dealing with large models and teams."

The motivating claim, quoted by Fowler from Evans (**Quoted**):

> "total unification of the domain model for a large system will not be feasible or cost-effective"

Fowler's key structural observation — that context boundaries tend to follow **human/organisational**
divisions, because the model *is* a language and languages change where the people change — is the
bridge to Conway's Law (§12) and to the repo's `CONTEXT-MAP.md`. **Paraphrase, not verbatim.**

Fowler also credits Evans with the **"bubble context"** technique for applying bounded contexts to
legacy systems. **Paraphrase, not verbatim.**

### Context Map

A Context Map documents the bounded contexts in play and the *relationships* between them. Evans's
relationship patterns are commonly listed as Shared Kernel, Customer/Supplier, Conformist,
Anticorruption Layer, Open Host Service, Published Language, and Separate Ways.
**`[unverified]`** — I could not verify this list against Evans's own text in this session. Do not
present the list as complete or as verbatim Evans without checking the DDD Reference PDF.

**The repo's form of it:** a root `CONTEXT-MAP.md` that "points to where each one lives", with
per-context `CONTEXT.md` files and per-context `docs/adr/` directories (**Quoted**, local repo
`skills/engineering/domain-modeling/SKILL.md`). Note this is a *directory map*, and captures
**where contexts live** but not **how they relate** — it has no equivalent of Evans's relationship
patterns. That is a real and teachable difference, not a criticism: the repo optimises for an agent
navigating a filesystem, Evans optimised for teams negotiating integration.

### Domain Model

The repo's constraint on the artifact is unusually strict and worth quoting because it is where the
repo departs from common practice:

> "`CONTEXT.md` should be totally devoid of implementation details. Do not treat `CONTEXT.md` as a
> spec, a scratch pad, or a repository for implementation decisions. It is a glossary and nothing
> else."

**Quoted** — local repo. Evans's *domain model* is a richer object than a glossary (it includes
entities, value objects, aggregates, services, and the code that expresses them). **The repo's
`CONTEXT.md` is deliberately only the language half.** Do not teach `CONTEXT.md` as "the domain
model" — teach it as *the ubiquitous language, written down, with implementation deliberately
excluded*.

### Vaughn Vernon

Vaughn Vernon, *Implementing Domain-Driven Design*, Addison-Wesley, 2013 — the practical companion,
notably on aggregate design rules. Vernon also published a free three-part "Effective Aggregate
Design" PDF series via dddcommunity.org. **`[unverified]`** — neither the book metadata nor the PDF
series was fetched in this session; both are from memory. Verify before citing.

### 5a. EventStorming

**Alberto Brandolini.** EventStorming is a collaborative workshop technique for discovering a domain
by mapping **domain events** on a long paper roll with sticky notes (conventionally orange for
domain events, blue for commands, and further colours for aggregates, policies, read models and
external systems). It feeds directly into ubiquitous language and bounded-context discovery, which
is why it belongs beside Evans rather than in its own track.

**`[unverified]` throughout this subsection.** I did not fetch <https://www.eventstorming.com>,
Brandolini's original blog post (circa 2013, ziobrando.blogspot.com), or the Leanpub page in this
session. Two specific things to verify before teaching it:

1. The **colour convention** above is from memory and varies between Brandolini's own versions.
2. *Introducing EventStorming* is **self-published on Leanpub and has never been finished.** Cite it
   as an unfinished Leanpub book, not as a completed Addison-Wesley title — presenting it as a
   finished book is a common and visible error. **`[unverified]`**, but flagged because the failure
   mode is worse than the gap.

## 6. Vertical slice vs tracer bullet vs walking skeleton

These three are routinely used as synonyms. **They are not synonyms.** They sit on three different
axes, and the whole value of this section is keeping them apart:

| Idea | Axis | Question it answers | Origin |
| --- | --- | --- | --- |
| **Tracer bullet** | Delivery / feedback technique | *How do I get real feedback fast without throwing the work away?* | Hunt & Thomas, 1999 |
| **Walking skeleton** | Initial architecture artifact | *What is the smallest end-to-end thing that proves the pieces connect?* | Cockburn |
| **Vertical slice architecture** | Code organisation pattern | *How do I lay out the source tree — by layer or by feature?* | Bogard, 2018 |

A tracer bullet is a *practice*. A walking skeleton is a *thing you build once, at the start*. A
vertical slice architecture is a *permanent structural choice about directories and coupling*. You
can have any one without the other two.

### Tracer bullets — Hunt & Thomas

**Canonical source.** Andrew Hunt & David Thomas, *The Pragmatic Programmer: From Journeyman to
Master*, Addison-Wesley, 1999; 20th Anniversary Edition, *The Pragmatic Programmer: Your Journey to
Mastery*, 2019. Note the **subtitle changed** between editions — cite the one you actually used.
**`[unverified]`** — publisher/ISBN/section number not fetched from a publisher page in this session.
The topic is titled "Tracer Bullets"; its topic *number* is **omitted deliberately** because I could
not fetch a real table of contents.

The metaphor: tracer rounds burn visibly so the gunner sees the actual trajectory and adjusts, in
the real conditions, immediately — rather than computing a firing solution in advance.

**The distinction that matters, and the one people get wrong.** Tracer code is **not** a prototype:

- **Prototype** — you explore one aspect, then **throw the code away** and recode properly using
  what you learned.
- **Tracer code** — **lean but complete**, production quality, checked and documented, and it
  **stays**: it becomes the skeleton of the final system.

**Paraphrase, not verbatim** — this characterisation comes from an Artima interview with Hunt and
Thomas (<https://www.artima.com/articles/tracer-bullets-and-prototypes>), which is *the authors
speaking in their own words about their book*, but is **an interview, not a book excerpt**. I could
not fetch a verbatim passage from the book itself. Do not present any of this as a quotation from
*The Pragmatic Programmer*. **`[unverified]`** at the level of exact wording.

Hunt and Thomas position prototyping as the reconnaissance that happens *before* you fire tracer
bullets. That ordering is worth teaching: prototype to answer a question, then tracer-bullet to
build.

**This is exactly where the repo's `prototype` skill lands**, and the alignment is precise:
"A prototype is **throwaway code that answers a question**" and "**Throwaway from day one, and
clearly marked as such**" (**Quoted**, local repo `skills/engineering/prototype/SKILL.md`). The repo
keeps Hunt & Thomas's distinction intact without citing it.

### Walking skeleton — Cockburn

**Canonical source.** Alistair Cockburn. The definition in universal circulation is:

> "A Walking Skeleton is a tiny implementation of the system that performs a small end-to-end
> function. It need not use the final architecture, but it should link together the main
> architectural components."

**`[unverified]` as a verbatim Cockburn quotation.** This wording is reproduced consistently across
many secondary sources, but I could **not** retrieve it from Cockburn's own site:
`http://alistair.cockburn.us/Walking+skeleton` returned HTTP 404 and
`https://alistair.cockburn.us/walking-skeleton/` returned HTTP 404; the O'Reilly *97 Things Every
Software Architect Should Know* chapter 60 ("Start with a Walking Skeleton") returned HTTP 403 to
automated fetch. **Those are the three places I looked.** Treat the wording as
*attributed-and-widely-reproduced*, not as verified. Secondary sources place its origin in
Cockburn's *Writing Effective Use Cases* (Addison-Wesley, 2000/2001) and/or his Crystal
methodology writing — **`[unverified]`**, and the two candidate origins conflict, so do not print a
book-and-year for it.

**The load-bearing clause is "it need not use the final architecture, but it should link together
the main architectural components."** That is what makes it different from a tracer bullet: a
walking skeleton's job is to prove *connection*, and it is allowed to be architecturally throwaway
in a way tracer code is not.

**GOOS's contribution.** Freeman & Pryce's *Growing Object-Oriented Software, Guided by Tests* (2009)
starts a project with a walking skeleton specifically to force the **build, deploy and end-to-end
test automation** into existence on day one — the skeleton is as much about the *pipeline* as about
the code. **`[unverified]`** — not fetched in this session; from memory. This is the version of the
idea most relevant to agent work, because it is the "make a feedback loop exist before you do
anything else" move, and it rhymes exactly with the repo's `diagnosing-bugs` Phase 1 (§13).

### Vertical slice architecture — Bogard

**Canonical source.** Jimmy Bogard, "Vertical Slice Architecture",
<https://www.jimmybogard.com/vertical-slice-architecture/>, **19 April 2018**. **Metadata verified**
(date from the page) and **Quoted**:

> "In this style, my architecture is built around distinct requests, encapsulating and grouping all
> concerns from front-end to back."

> "Minimize coupling between slices, and maximize coupling in a slice."

This is a **structural** claim about how to organise code: group by *request/feature* rather than by
*technical layer* (controllers/ services/ repositories). It says nothing about the order in which
you build things.

### The fourth thing: the agile "vertical slice" of a story

Separately from Bogard's architecture pattern, agile practice uses "vertical slice" to mean *a thin
increment of user-visible functionality cutting through every layer*, as opposed to a "horizontal"
slice that completes one layer across all features. **`[unverified]` — no primary coinage found.**
This usage predates Bogard and is not the same idea; Bogard's is about the resting shape of the
code, this one is about how work is divided.

### What the repo means by these words

`skills/engineering/tdd/SKILL.md` uses both terms, and it means the **agile/feedback** senses, not
Bogard's:

> "**Horizontal slicing** — writing all tests first, then all implementation. Bulk tests verify
> _imagined_ behavior… Work in **vertical slices** instead — one test → one implementation → repeat,
> each test a **tracer bullet** that responds to what the last cycle taught."

**Quoted** — local repo, upstream <https://github.com/mattpocock/skills> at `2ab9580`.

Two observations worth teaching:

1. The repo's "vertical slice" is the **agile work-division** sense (thin end-to-end increment), not
   Bogard's directory-layout sense. A reader who learns "vertical slice" from Bogard's blog and then
   reads this skill will mis-parse it.
2. The repo's "tracer bullet" usage is **faithful to Hunt & Thomas** — the emphasis on *responding
   to what the last cycle taught* is the adjust-your-aim half of the metaphor, which is the half
   most people drop. This is one of the repo's more precise borrowings.

## 7. Shared understanding, Three Amigos, Specification by Example, BDD

### BDD — Dan North

**Canonical source.** Dan North, "Introducing BDD", originally published in **Better Software
magazine, March 2006**; canonical copy at <https://dannorth.net/introducing-bdd/>.
**Metadata verified** and **Quoted** below.

The origin is a *vocabulary* change, not a tooling change:

> "I started using the word 'behaviour' in place of 'test' in my dealings with TDD and found that
> not only did it seem to fit but also that a whole category of coaching questions magically
> dissolved."

The trigger was a tool, `agiledox`, by his colleague Chris Stevenson, which generated documentation
from test names:

> "The word 'test' is stripped from both the class name and the method names, and the camel-case
> method name is converted into regular text."

From this North derives the **"should" sentence template** — *"The class **should** do something"* —
whose value is that when you cannot finish the sentence about *this* class, the behaviour belongs
somewhere else. (**Quoted**.)

**Given/When/Then** came out of work with business analyst Chris Matts:

> "Given some initial context (the givens), When an event occurs, Then ensure some outcomes."

**Quoted.**

**Direct relevance to the repo.** The `tdd` skill's rule that "A good test reads like a
specification — 'user can checkout with valid cart' tells you exactly what capability exists"
(**Quoted**, local repo) **is North's agiledox insight**, arrived at independently or inherited
silently. This is one of the clearest unattributed lineages in the repo and a strong lesson beat.

### Specification by Example — Gojko Adzic

Gojko Adzic, *Specification by Example: How Successful Teams Deliver the Right Software*, Manning,
2011; and the earlier *Bridging the Communication Gap* (2009). Key ideas: deriving scope from goals,
**key examples**, and **living documentation** — the examples become the executable specification and
therefore cannot rot silently. **`[unverified]`** — metadata and concepts from memory; neither book
nor gojko.net was fetched in this session.

### Three Amigos

Commonly credited to **George Dinwiddie**, not to Adzic: the practice of having a **business
representative, a developer and a tester** examine each story together before development, so that
three different failure-modes of understanding get caught at once. **`[unverified]`** — I did not
fetch Dinwiddie's original post (blog.gdinwiddie.com) in this session. **Do not attribute this to
Adzic**, which is the common error.

### "Shared understanding" attributed to Ward Cunningham — unsupported

The repo's `grilling` skill says "Interview me relentlessly about every aspect of this until we
reach a **shared understanding**" (**Quoted**). It is tempting to give that phrase a pedigree.

**I could not find a primary Ward Cunningham source for "shared understanding" as a named concept,
and I did not search for one in this session beyond noting the risk.** Cunningham's well-attested
contributions are the **wiki**, the **technical debt metaphor**, "**the simplest thing that could
possibly work**", and co-authorship of the Agile Manifesto. **`[unverified]` — treat the Cunningham
attribution as unsourced until someone produces the primary text.** Do not manufacture it.

What *is* solidly available instead:

- The **Agile Manifesto** value "**Individuals and interactions over processes and tools**" and the
  principle that "The most efficient and effective method of conveying information to and within a
  development team is face-to-face conversation." **`[unverified]` as verbatim** — I did not fetch
  <https://agilemanifesto.org/principles.html> this session, though it is free and trivially
  checkable.
- North's BDD and Adzic's SbE, both of which *are* explicitly about closing the understanding gap
  between business and developers, and both of which are properly sourced above.

Use those, and say plainly that "shared understanding" as used in the repo is ordinary English doing
useful work, not a term of art with a citation.

## 8. Architecture Decision Records

**Canonical source.** Michael Nygard, "Documenting Architecture Decisions", **15 November 2011**,
<https://www.cognitect.com/blog/2011/11/15/documenting-architecture-decisions> (originally published
on the Think Relevance blog). **Metadata verified**, and all quotes below **Quoted** from it.

**Nygard's rationale.** Architecture on agile projects "has to be described and defined differently"
because the decisions are not all made up front. He is careful that this is not anti-documentation —
agile methods oppose only "valueless documentation" — and his structural argument is that small,
modular documents have "at least a chance at being updated" whereas large ones never are.

The problem ADRs solve is stated sharply: a new team member facing an undocumented decision has only
two bad options — **blindly accept it** or **blindly reverse it**. Neither is engineering. The ADR
supplies the missing third option by making the *rationale* visible.

**The template**, with Nygard's own descriptions:

| Section | Nygard's description |
| --- | --- |
| **Title** | "short noun phrases" — e.g. "ADR 1: Deployment on Ruby on Rails 3.0.10" |
| **Context** | "describes the forces at play, including technological, political, social, and project local", in "value-neutral" language |
| **Decision** | "describes our response to these forces", in "full sentences, with active voice", beginning "We will…" |
| **Status** | "proposed", "accepted", "deprecated", or "superseded", with references to replacements |
| **Consequences** | "resulting context, after applying the decision" — "positive, negative, and neutral" — affecting "the team and project in the future" |

**Ecosystem.** Nat Pryce's `adr-tools`, the `adr` GitHub organisation, and Joel Parker Henderson's
`architecture-decision-record` collection are the usual tooling references; ThoughtWorks Technology
Radar moved "Lightweight Architecture Decision Records" to **Adopt** (commonly cited as Vol. 17,
November 2017). **`[unverified]`** — none of these were fetched this session; do not print the radar
volume as fact.

**The repo's contribution — a strict three-part gate.** `domain-modeling/SKILL.md` will only create
an ADR when **all three** hold:

> "1. **Hard to reverse** — the cost of changing your mind later is meaningful
> 2. **Surprising without context** — a future reader will wonder 'why did they do it this way?'
> 3. **The result of a real trade-off** — there were genuine alternatives and you picked one for
> specific reasons"

**Quoted.** Nygard imposes no such gate. This is **the repo's own addition**, and a good one: it
directly attacks the standard ADR failure mode of recording so many trivial decisions that nobody
reads any of them. Teach it as an extension, and note that Nygard's "Consequences" section — the one
people skip — is what the repo's criterion 3 is really enforcing.

## 9. Code smells and refactoring

**Canonical sources — two editions, and they differ in ways that matter.**

- **1st edition:** Martin Fowler (with contributions from Kent Beck, John Brant, William Opdyke and
  Don Roberts), *Refactoring: Improving the Design of Existing Code*, Addison-Wesley, **1999**.
  Examples in **Java**. **`[unverified]`** — ISBN not fetched (the Pearson sample path
  `9780201485677` corroborates the ISBN-13 978-0-201-48567-7). Chapter 3, "Bad Smells in Code", is
  co-authored with **Kent Beck** — a free copy of the 1st-edition chapter is at
  <http://www.laputan.org/pub/patterns/fowler/smells.pdf>.
- **2nd edition:** Martin Fowler, *Refactoring: Improving the Design of Existing Code*,
  Addison-Wesley, **2018/2019**. Examples in **JavaScript**. ISBN-13 978-0-13-475759-9 /
  978-0-13-475770-4 appear in publisher paths. **`[unverified]`** for exact ISBN and year — the 2nd
  edition is variously dated 2018 (copyright) and 2019 (release). Companion site:
  <https://refactoring.com>.

**Always state the edition.** "Fowler's smells" without an edition is ambiguous, and the 2nd edition
is in JavaScript — which surprises Java readers who expect the 1999 book.

**The 2nd-edition Chapter 3 smell list** (Mysterious Name, Duplicated Code, Long Function, Long
Parameter List, Global Data, Mutable Data, Divergent Change, Shotgun Surgery, Feature Envy, Data
Clumps, Primitive Obsession, Repeated Switches, Loops, Lazy Element, Speculative Generality,
Temporary Field, Message Chains, Middle Man, Insider Trading, Large Class, Alternative Classes with
Different Interfaces, Data Class, Refused Bequest, Comments). **Metadata verified** via search
consensus across the O'Reilly TOC listing and independent chapter summaries; **`[unverified]`** for
completeness and ordering — I did not fetch a publisher TOC directly.

**Which edition does the repo use? The 2nd — and this is checkable.** The `code-review` skill cites
"a fixed set of Fowler code smells (_Refactoring_, ch.3)" (**Quoted**) and its baseline includes
**Mysterious Name**. *Mysterious Name* is a **2nd-edition-only** entry, as are *Global Data* and
*Mutable Data*. So the repo's baseline is drawn from the **2nd edition**, and the chapter number
"ch.3" is correct for both editions. **This is a solid, verifiable finding** and exactly the kind of
detail that makes a citation trustworthy.

> **Do not use *Repeated Switches* as evidence for this.** It is a **rename**, not a new smell — the
> 1st edition carried the same idea as "Switch Statements". Saying it is "not in the 1999 list" is
> true of the *name* and misleading about the *smell*. Lean on *Mysterious Name* alone; the
> conclusion is unaffected.

Note also what the repo **left out**: *Long Function*, *Long Parameter List*, *Large Class* and
*Comments* — the four most size-oriented smells. Given §2c, that is interesting: the repo declines to
adopt the smells Uncle Bob would emphasise most and Ousterhout would resist. Whether that is
deliberate is **`[unverified]`**, but it is a genuinely good discussion prompt.

**Fowler's definition of refactoring** — a change to the internal structure of software that makes it
easier to understand and cheaper to modify **without changing its observable behaviour** — and his
advice to "make each refactoring step as small as possible, so that you can always see the program
working" (the latter **Quoted** as it appears in the repo's own deprecated
`request-refactor-plan/SKILL.md`, which attributes it to Fowler). **`[unverified]`** that the repo's
rendering is verbatim Fowler.

## 10. Legacy code and characterization tests

Same book as §1: Michael C. Feathers, *Working Effectively with Legacy Code*, Pearson (Robert C.
Martin Series), 2005, ISBN-13 978-0-13-117705-5. **Metadata verified** (see §1).

**Feathers' definition of legacy code** is the book's most-quoted line: *legacy code is simply code
without tests.* **`[unverified]` as verbatim** — the idea is universally attributed to Feathers and
is the premise of the whole book, but I did not fetch the sentence. Teach the idea; do not print it
inside quotation marks without checking.

What makes the definition useful is that it is **not about age**. Code written this morning without
tests is legacy; twenty-year-old code with a good suite is not. It reframes "legacy" from a property
you inherit to a property you create.

**Characterization tests.** The technique: when you must change code you do not understand and
cannot trust, you do **not** first write tests asserting what the code *should* do. You write tests
that **document what it currently does** — including its bugs — so that any change you make produces
a visible diff in behaviour. The test characterises actual behaviour; it is a vice, not a
specification.

The loop is roughly: write a test asserting something you *know is wrong*; run it; let the failure
message tell you the actual value; change the assertion to that actual value; repeat until the
behaviour you need to touch is pinned.

**Chapter location.** From the publisher's TOC (**Chapter located**, see §1), the relevant chapter is
**Chapter 13, "I Need To Make a Change but I Don't Know What Tests To Write"** — this is where
characterization tests are introduced. **`[unverified]`** that the term is *defined* there rather
than merely used; the chapter title matches the technique's purpose exactly, but I did not see the
body text.

**Relationship to golden master / approval testing.** A characterization test asserting on a large
captured output (rather than a single value) is what the approval-testing community calls a
**golden master** or **approval test**; the ApprovalTests family of libraries (associated with
Llewellyn Falco) automates exactly this. **`[unverified]`** — relationship and attribution not
verified against a primary source in this session.

**Where it connects to the repo.** The `diagnosing-bugs` skill's Phase 2 (**reproduce + minimise**)
is characterization-test thinking applied to a bug rather than to a module: pin the *actual* observed
behaviour first, shrink it, and only then reason about cause. And its Phase 5 rule — write the
regression test before the fix, *but only if a correct seam exists*, and "**If no correct seam
exists, that itself is the finding**" (**Quoted**, local repo) — is a direct descendant of Feathers'
central problem: you cannot test what you cannot get under a harness, and noticing that is
information about the architecture.

## 11. Coupling and cohesion

**Where the idea actually originates.** The coupling/cohesion taxonomy is usually credited to the
1979 book, but the **primary source is a journal paper five years earlier**:

- **W. P. Stevens, G. J. Myers & L. L. Constantine, "Structured Design", *IBM Systems Journal*,
  Vol. 13, No. 2, 1974.** **`[unverified]`** — I did not fetch this paper in this session. It is the
  usually-cited origin of the taxonomy and the correct primary citation if it checks out. Verify
  before printing.
- **E. Yourdon & L. L. Constantine, *Structured Design: Fundamentals of a Discipline of Computer
  Program and Systems Design*** — Yourdon Press 1975 / Prentice-Hall 1979. **`[unverified]`** —
  edition history and publisher not verified; the two dates correspond to different editions and are
  frequently conflated.

**The taxonomies**, in the conventional worst→best ordering:

- **Coupling:** content · common · external · control · stamp · data
- **Cohesion:** coincidental · logical · temporal · procedural · communicational · sequential ·
  functional

**`[unverified]`** — both lists are from memory, not from the primary text. The *shape* of the claim
(coupling should be low and cohesion high; both are ordinal scales, not booleans) is uncontroversial;
the *level names* should be checked against the 1974 paper before they appear in a lesson.

**Relationship to Parnas (§2a).** These are two answers to the same question asked in the same era,
and they are **not** the same answer. Parnas asks *what should a module hide?* and answers "a design
decision likely to change". Structured Design asks *how tightly are modules connected, and how well
does each one hang together?* and answers with graded scales. Parnas's criterion is **generative** —
it tells you how to choose the decomposition. Coupling/cohesion is **evaluative** — it tells you how
to score one you already have. A course should teach them as complementary, and should note that
Parnas's is the deeper idea because information hiding *produces* low coupling as a side effect,
while measuring coupling never tells you what to hide.

**Connection to the repo.** The repo has no coupling/cohesion vocabulary at all — deliberately, given
its insistence on a small fixed glossary. Its **deletion test** ("Imagine deleting the module. If
complexity vanishes, it was a pass-through. If complexity reappears across N callers, it was earning
its keep", **Quoted**) is a cohesion test in disguise, and Bogard's "minimize coupling between
slices, and maximize coupling in a slice" (§6) is literally the 1974 pair restated for feature
folders.

## 12. Conway's Law

**Canonical source.** Melvin E. Conway, "How Do Committees Invent?", ***Datamation*, April 1968**.
The author hosts the paper himself at
<https://www.melconway.com/Home/Committees_Paper.html>. **Metadata verified** (venue, month, year,
and F. D. Thompson Publications copyright, from Conway's own page).
**`[unverified]`**: volume 14, number 4, pages 28–31 — the commonly cited locators did not appear in
the fetched page, so they are **omitted from the bibliography** rather than guessed.

**A precision point most write-ups get wrong.** There are **two different sentences**, and they are
not from the same moment:

1. **The famous formulation**, from an author's note **Conway added 42 years after publication** —
   not from the 1968 text:

   > "Any organization that designs a system (defined more broadly here than just information
   > systems) will inevitably produce a design whose structure is a copy of the organization's
   > communication structure."

2. **The 1968 paper's own conclusion**:

   > "organizations which design systems (in the broad sense used here) are constrained to produce
   > designs which are copies of the communication structures of these organizations"

**Quoted** (both, from Conway's page). If a lesson attributes formulation 1 to the 1968 paper, it is
wrong. Use formulation 2 for the paper and note that Conway himself later restated it as 1. **This is
a genuinely valuable detail** — it is the kind of thing that establishes that you actually read the
source.

**Who named it "Conway's Law"?** Conway's own page does **not** say. The name is usually credited to
Fred Brooks in *The Mythical Man-Month*. **`[unverified]`** — not checked in this session.

**The empirical follow-up.** MacCormack, Rusnak & Baldwin, "Exploring the Duality Between Product and
Organizational Architectures", testing the **"mirroring hypothesis"** — that loosely-coupled
organisations produce more modular products. **`[unverified]`** — venue, year and findings not
verified. The **"Inverse Conway Maneuver"** (reshape teams to get the architecture you want) is
usually credited to the ThoughtWorks Technology Radar. **`[unverified]`**.

**Why it belongs in this course.** The repo's seams and bounded contexts are, in practice, where
teams hand off to each other — and (per Fowler in §5) context boundaries tend to follow human
divisions. In agent work the same law reappears in a new form: **the decomposition you give to
sub-agents becomes the decomposition of the artifact they produce.** Flag that last sentence as
**our own inference** — it is an analogy, not a finding.

## 13. Debugging methodology

The repo's `diagnosing-bugs` loop is **reproduce → minimise → hypothesise → instrument → fix**. Each
step has a real ancestor, and they come from two different books.

### David Agans — the nine rules

**Canonical source.** David J. Agans, *Debugging: The 9 Indispensable Rules for Finding Even the Most
Elusive Software and Hardware Problems*, **AMACOM** (American Management Association), **2002**.
ISBN-10 0-8144-7457-8 / ISBN-13 978-0-8144-7457-0. **Metadata verified** via search consensus
including the Internet Archive record <https://archive.org/details/debugging9indisp0000agan>.
The author's site is <https://debuggingrules.com> — note it is a **promotional landing page and does
not list the rules**, which is where I looked first.

The nine rules, in the order they are conventionally given:

1. Understand the System
2. Make It Fail
3. Quit Thinking and Look
4. Divide and Conquer
5. Change One Thing at a Time
6. Keep an Audit Trail
7. Check the Plug
8. Get a Fresh View
9. If You Didn't Fix It, It Ain't Fixed

**`[unverified]` for the full list and its ordering.** Rules 1, 2, 3 and 7 are **confirmed** to be
chapter titles in the book via search results; the remaining five are from memory and consistent
secondary reporting. Verify the complete list against the book or a TOC before printing it as
Agans's.

**Mapping to the repo, which is close enough to be worth teaching:**

| Agans | Repo's `diagnosing-bugs` |
| --- | --- |
| Make It Fail | Phase 1 (build a red-capable feedback loop) + Phase 2 (reproduce) |
| Quit Thinking and Look | Phase 1's hard gate: "If you catch yourself reading code to build a theory before this command exists, **stop**" (**Quoted**) |
| Divide and Conquer | Phase 2's minimise step |
| Change One Thing at a Time | Phase 4: "**Change one variable at a time**" (**Quoted**) — near-verbatim Agans |
| Keep an Audit Trail | Phase 4's `[DEBUG-a4f2]` tagging convention |
| If You Didn't Fix It, It Ain't Fixed | Phase 6: "Original repro no longer reproduces (re-run the Phase 1 loop)" (**Quoted**) |

That is six of nine rules reproduced, one of them almost word for word. Whether the repo drew on
Agans directly is **`[unverified]`** — it cites no source.

### Andreas Zeller — delta debugging and the scientific method

**Canonical sources.**

- **Andreas Zeller & Ralf Hildebrandt, "Simplifying and Isolating Failure-Inducing Input", *IEEE
  Transactions on Software Engineering* 28(2), February 2002.** This is the **ddmin** algorithm
  paper. **`[unverified]`** — not fetched; venue/volume/year from memory.
- **Andreas Zeller, *Why Programs Fail: A Guide to Systematic Debugging*, Morgan Kaufmann**, 1st ed.
  2005, 2nd ed. 2009. **`[unverified]`**.
- **Zeller's free, CC-licensed online book, *The Debugging Book*, <https://www.debuggingbook.org>.**
  **`[unverified]`** — not fetched, but this is the highest-value free resource in this section and
  worth opening first.

**Delta debugging** is the formal ancestor of the repo's **minimise** step: given a failing input,
systematically and automatically reduce it to a **1-minimal** failing input — one where removing any
remaining element makes the failure disappear.

**Compare the repo, which states the same stopping condition in plain English:**

> "Done when **every remaining element is load-bearing** — removing any one of them makes the loop go
> green."

**Quoted**, local repo. That *is* 1-minimality. The repo has re-derived (or silently inherited)
delta debugging's termination criterion. **This is the strongest single "the repo is doing real
computer science without saying so" example in the whole document** and makes an excellent lesson
beat.

Zeller's other contribution is framing debugging explicitly as **the scientific method** — hypothesis,
prediction, experiment, refinement — which is exactly the repo's Phase 3 requirement that every
hypothesis be **falsifiable**: "If you cannot state the prediction, the hypothesis is a vibe —
discard or sharpen it" (**Quoted**). Zeller's earlier paper "Yesterday, my program worked. Today, it
does not. Why?" (ESEC/FSE 1999) is the origin of much of this. **`[unverified]`**.

## 14. Working memory and cognitive load

> **Read this warning before using anything in this section.** The brief asked to "connect to why
> small interfaces matter". **That connection is an extrapolation, not a finding.** Miller and Sweller
> are not about software interfaces. Nothing in the cognitive-science literature below establishes
> that smaller interfaces reduce cognitive load in programming. Presenting it as if it does is
> exactly the kind of borrowed authority this document exists to prevent. Label the leap every time.

### Miller 1956 — and why you should not cite "seven" as current science

George A. Miller, "The Magical Number Seven, Plus or Minus Two: Some Limits on Our Capacity for
Processing Information", *Psychological Review* 63(2), 1956, pp. 81–97. **`[unverified]`** — not
fetched this session; metadata from memory.

Two corrections that matter:

1. Miller's paper is about **chunks** in absolute judgment and immediate memory — the whole point is
   that a "chunk" is elastic and recoding is what buys capacity. It is not a claim that any list
   longer than seven is bad.
2. **Nelson Cowan, "The magical number 4 in short-term memory: A reconsideration of mental storage
   capacity", *Behavioral and Brain Sciences* 24(1), 2001**, revised the working estimate down to
   roughly **four**. **`[unverified]`** — not fetched. Citing Miller's 7±2 as the current figure is a
   well-known error; if you cite Miller at all, cite Cowan alongside.

### Sweller — Cognitive Load Theory

John Sweller, "Cognitive load during problem solving: Effects on learning", *Cognitive Science*
12(2), 1988; and the intrinsic/extraneous/germane taxonomy in Sweller, van Merriënboer & Paas,
*Educational Psychology Review*, 1998. **`[unverified]`** — neither fetched.

Two honest caveats:

- CLT is a theory of **instructional design** — how to teach — not a theory of software structure.
- **Germane load has been contested and revised**, including by Sweller himself in later work. Do not
  present the three-way taxonomy as settled. **`[unverified]`**.

### What there actually is empirical evidence for

**Janet Siegmund, Christian Kästner, Sven Apel, Chris Parnin, Anja Bethmann, Thomas Leich, Gunter
Saake & André Brechmann, "Understanding understanding source code with functional magnetic resonance
imaging", ICSE 2014 (36th International Conference on Software Engineering).** DOI
[10.1145/2568225.2568252](https://dl.acm.org/doi/10.1145/2568225.2568252). Author PDF:
<https://www.cs.cmu.edu/~ckaestne/pdf/icse14_fmri.pdf>. **Metadata verified**.

Design: **17 participants** in an fMRI scanner comprehending short source-code snippets, contrasted
against locating syntax errors. Finding: a distinct activation pattern across **five brain regions
associated with working memory, attention, and language processing**. **Metadata verified** via the
ACM record and consistent secondary reporting; **`[unverified]`** for exact region labels.

**What this does and does not license you to say.** It supports: *program comprehension recruits
working memory and language systems* — i.e. reading code is more like reading than like arithmetic.
It does **not** support any claim about interface size, module depth, or the number of methods a
class should have. The sample is 17 people reading snippets.

**Felienne Hermans, *The Programmer's Brain: What Every Programmer Needs to Know about Cognition*,
Manning, 2021.** **`[unverified]`** — metadata from memory. This is the book that actually bridges
cognitive science and programming, organised around long-term memory, short-term memory and working
memory. Treat it as a **well-sourced practitioner synthesis** — it is the right thing to hand a
reader, and the right thing to cite as "a synthesis", not as primary evidence.

### The honest version of the argument

**There is no direct empirical evidence that smaller interfaces reduce cognitive load in software.**
What you can defensibly say is this, clearly marked as inference:

> Program comprehension demonstrably consumes working memory (Siegmund et al. 2014), and working
> memory is sharply capacity-limited (Miller 1956; Cowan 2001). An interface is the set of facts a
> caller must hold in mind to use a module correctly (the repo's definition). It therefore *follows
> by argument, not by measurement*, that a smaller interface costs a caller less. **This is our own
> inference.**

That is a good argument. It is not a citation. Keep the two apart — and note that this is precisely
where the AI parallel is *stronger* than the human one, because for an LLM the interface really is
measurable: it is tokens in the context window. That asymmetry is worth a lesson on its own.

## 15. Desirable difficulty, retrieval practice, spacing

This is the literature that grounds **the course's own teaching method** — the retrieval quizzes, the
unannounced spaced questions opening each lesson, the free-recall blocks.

**Storage strength vs retrieval strength.** Robert A. Bjork & Elizabeth L. Bjork, "A new theory of
disuse and an old theory of stimulus fluctuation", in Healy, Kosslyn & Shiffrin (eds.), *From
Learning Processes to Cognitive Processes: Essays in Honor of William K. Estes*, Erlbaum, **1992**.
**Metadata verified** via search consensus; **`[unverified]`** for exact volume/page details.

The theory posits **two independent strengths** per memory item:

- **Storage strength** — how well learned the item is. It only ever increases.
- **Retrieval strength** — how accessible it is right now. It fluctuates with recency and context.

**The pedagogically explosive consequence:** conditions that raise *retrieval* strength fastest
(re-reading, massed practice, fluency) often raise *storage* strength least. Feeling fluent is not
learning. This is the entire justification for making a lesson harder than it needs to be.

**"Desirable difficulties"** was coined by **Robert A. Bjork in 1994**, in "Memory and metamemory
considerations in the training of human beings", in Metcalfe & Shimamura (eds.), *Metacognition:
Knowing about Knowing*, MIT Press. **Metadata verified** via search consensus; **`[unverified]`** for
page range.

The accessible summary, hosted free by the authors' institutions: **Bjork & Bjork, "Making things
hard on yourself, but in a good way: Creating desirable difficulties to enhance learning"** — copies
at <https://www.unh.edu/teaching-learning-resource-hub/sites/default/files/media/2023-06/itow-introducing-desirable-difficulties-into-practice-and-instruction-bjork-and-bjork.pdf>
and <https://bjorklab.psych.ucla.edu/research/>. **Metadata verified** (URLs live).
**This is the single best free starting point in this section.**

**Retrieval practice / the testing effect.** Roediger & Karpicke, "Test-Enhanced Learning: Taking
Memory Tests Improves Long-Term Retention", *Psychological Science* 17(3), 2006. **`[unverified]`** —
not fetched. The classic finding: testing beats restudying at long delays, even though learners
predict the opposite.

**Spacing.** Cepeda, Pashler, Vul, Wixted & Rohrer, "Distributed practice in verbal recall tasks: A
review and quantitative synthesis", *Psychological Bulletin* 132(3), 2006. **`[unverified]`** — not
fetched.

**Report the boundary conditions honestly.** The testing and spacing effects are among the most
robust findings in cognitive psychology **for factual and verbal material**. Their **transfer to
complex, higher-order learning** — which is what this course is actually attempting — is genuinely
debated and much less settled. **`[unverified]`**, and stated as a limitation rather than smoothed
over. A course that cites Bjork to justify quizzes should admit that the evidence is strongest for
exactly the kind of material the course cares least about.

## 16. The "grilling" / Socratic interview practice

**Verdict up front: "grilling" as a named engineering practice is a practitioner coinage with no
academic literature behind it. It appears to be Matt Pocock's own.** Say that plainly. Do not build
it a pedigree.

**The evidence for calling it a coinage.** The repo's README presents it as the author's own fix,
in the author's own voice, with **no attribution**: the problem is "The Agent Didn't Do What I Want",
and "The fix for this is a **grilling session** - getting the agent to ask you detailed questions
about what you're building." (**Quoted**, `README.md`, upstream
<https://github.com/mattpocock/skills> at `2ab9580`). The skill itself is four short paragraphs of
imperative instruction with no citations (**Quoted**, `skills/productivity/grilling/SKILL.md`).
Nowhere in the repo is the term traced to anyone.

**What the practice actually consists of**, from the skill text (**Quoted**):

- "Interview me relentlessly about every aspect of this until we reach a shared understanding."
- "Walk down each branch of the decision tree, resolving dependencies between decisions one-by-one."
- "For each question, provide your recommended answer."
- "Ask the questions one at a time, waiting for feedback on each question before continuing. Asking
  multiple questions at once is bewildering."
- "If a *fact* can be found by exploring the environment (filesystem, tools, etc.), look it up rather
  than asking me. The *decisions*, though, are mine."
- "Do not act on it until I confirm we have reached a shared understanding."

That last pair — **facts are looked up, decisions are escalated; and nothing is executed until
understanding is confirmed** — is the substantive content, and it is a genuine contribution to
human-agent protocol design regardless of its lack of pedigree.

### Legitimate neighbours (not ancestors)

These are real literatures that address adjacent problems. **None of them is the source of
"grilling".** Present them as neighbours or the reader will hear a lineage that does not exist.

- **Socratic questioning.** Richard Paul & Linda Elder, *The Thinker's Guide to Socratic
  Questioning*, Foundation for Critical Thinking (criticalthinking.org). **`[unverified]`** — not
  fetched. Nearest match for the *one question at a time, follow the branch* form.
- **Toyota's "5 Whys".** Taiichi Ohno, *Toyota Production System: Beyond Large-Scale Production*,
  Productivity Press (English edition 1988). **`[unverified]`**. Nearest match for *iterative
  interrogation to reach a root*. **Report the critique too**: 5 Whys is criticised for producing
  single-cause explanations of multi-cause failures — e.g. Alan J. Card, "The problem with '5
  whys'", *BMJ Quality & Safety*, 2017. **`[unverified]`** — not fetched, and worth checking because
  the critique is the more interesting half.
- **Formal design/code inspection.** Michael E. Fagan, "Design and code inspections to reduce errors
  in program development", *IBM Systems Journal* 15(3), 1976. **`[unverified]`**. The origin of
  structured review with defined roles, and one of the earliest bodies of *measured* evidence that
  structured questioning finds defects.
- **ATAM (Architecture Tradeoff Analysis Method)**, Kazman, Klein & Clements, SEI technical report.
  **`[unverified]`**. Nearest match for *stress-testing a design decision against stated quality
  attributes before committing*.
- **Three Amigos** (§7) — the closest *practice* analogue, and it has an attributable originator
  (Dinwiddie), which grilling does not.

### How to teach it

The honest framing is the strongest one: *this is an undocumented practitioner technique that
rediscovers, in a human-agent context, something Socratic questioning and design review have long
done between humans — and its one genuinely novel move is the fact/decision split, which only makes
sense when your interlocutor can read your filesystem.* That is a more interesting claim than a
fabricated citation would be.

**Related likely coinages in the same repo** — flag each as `<span class="flag coinage">` and do not
define them from imagination: `deepening opportunity`, `wayfinder`, `dumb zone`, `harness
engineering`, and the repo's specific senses of `leverage` and `locality`. **`[unverified]` — none
found in academic literature.** The repo's `codebase-design/SKILL.md` does define **Leverage** and
**Locality** itself (**Quoted** in §1's source), so for those two you can quote the repo as the
primary source for its own terms — which is the correct move for a coinage.

---

## The Java expression of these ideas

The reader will learn this in Java, in a banking domain. This section maps each concept onto its
canonical Java-ecosystem expression, verified against official documentation wherever possible.

### Ports and adapters — the named pattern behind "adapter at a seam"

**Canonical source.** Alistair Cockburn, "Hexagonal Architecture" (Ports and Adapters), dated
**4 September 2005**, version 0.9, formally **"HaT Technical Report 2005.02"**, at
<https://alistair.cockburn.us/hexagonal-architecture/>. **Metadata verified.**

**Intent, verbatim (Quoted):**

> "Allow an application to equally be driven by users, programs, automated test or batch scripts, and
> to be developed and tested in isolation from its eventual run-time devices and databases."

**Confirmed:** Cockburn himself uses **both** term pairs — **primary/driving** ports and
**secondary/driven** ports — and derives the distinction from use-case analysis: primary actors
initiate conversations with the application; secondary actors are driven by it. He notes FIT fixtures
as test adapters for primary ports and mock objects as substitutes for secondary actors such as
databases. **Paraphrase, not verbatim**, except the intent statement above.

**How "adapter at a seam" maps onto it — and where the mapping breaks.** The repo's **Adapter** is
"a concrete thing that satisfies an interface at a seam… Describes *role* (what slot it fills), not
substance" (**Quoted**). That is essentially Cockburn's adapter. The repo's `DEEPENING.md` goes
further and explicitly reaches for the pattern by name for its dependency category 3: "Define a
**port** (interface) at the seam… Tests use an in-memory adapter. Production uses an
HTTP/gRPC/queue adapter." (**Quoted**).

**Two places the mapping is imperfect**, both worth teaching:

1. **Cockburn's ports are about the application's outer boundary**; the repo's seams are
   **scale-agnostic** and explicitly include *internal* seams "private to its implementation, used by
   its own tests" (**Quoted**). Hexagonal architecture has no notion of an internal port.
2. **Cockburn's symmetry claim** — that all ports are conceptually alike and the hexagon has no
   inherent top or bottom — is *stronger* than the repo's. The repo's "one adapter means a
   hypothetical seam, two means a real one" would forbid many of the ports a strict hexagonal design
   would create.

Related-but-distinct: Robert C. Martin's **Clean Architecture** (Prentice Hall, 2017) and Jeffrey
Palermo's **Onion Architecture** (2008 blog series). Convergent, not identical.
**`[unverified]`** — neither fetched.

### JUnit 5 — the test seam surface

JUnit 5 is architecturally three pieces: the **JUnit Platform** (the launcher/engine API),
**JUnit Jupiter** (the modern programming and extension model), and **JUnit Vintage** (a JUnit 3/4
engine). **`[unverified]`** — not fetched from junit.org this session; this decomposition is
well-established but should be confirmed against the current User Guide, as should the current
version number.

For this course the idiomatic combination is **`@Nested` + `@DisplayName`**, which is how Java
expresses Dan North's "test names are sentences" (§7) — a nested class per scenario with a
human-readable display name reads as a specification rather than as a method-name pun. Pair with
**AssertJ** (<https://assertj.github.io>) for fluent assertions, or `Assertions.assertAll` for
grouped assertions. **`[unverified]`** — API names from memory.

In the banking domain, this is where "a good test reads like a specification" becomes concrete:
`@DisplayName("transferência rejeitada quando saldo insuficiente")` on a nested class, with the seam
under test being `Ledger` or `TransferService`.

### Mockito — and its own warning against over-mocking

**Verified primary source.** The Mockito project wiki page "How to write good tests",
<https://github.com/mockito/mockito/wiki/How-to-write-good-tests>. **Quoted:**

> "Don't mock a type you don't own!"

And the reasoning (**Quoted**):

> "TDD is just as much about design as it is about test, when mocking an external API the test cannot
> be used to drive the design, the API belongs to someone else ; this third party can and will change
> the signature and behaviour of the API."

The page's three stated risks: mocked third-party libraries mask real behaviour changes after
upgrades (green tests, broken production); heavy mocking of external APIs signals insufficient
decoupling; and complex third-party mocks need so much fixture setup that readability and coverage
suffer. Its recommendation is to **wrap the external library in your own abstraction and write
integration tests** rather than mock the foreign type. **Paraphrase, not verbatim.**

**Attribution note, verified:** the Mockito wiki **does not credit an originator** for the principle
— it cites four secondary discussions (Dave Squared 2011, Mark Needham 2009, 8th Light, and a Stack
Overflow thread). The principle is widely associated with Freeman & Pryce / GOOS, but
**`[unverified]`** — do not state that Mockito attributes it to them, because it does not.

**An important refinement of the repo's mocking policy.** `mocking.md` says "Mock at **system
boundaries** only: External APIs (payment, email, etc.)" (**Quoted**). Read naively, that conflicts
with "don't mock a type you don't own". It is reconciled by the repo's own `DEEPENING.md`, which for
true external dependencies says the module "takes the external dependency as an injected **port**;
tests provide a mock **adapter**" (**Quoted**) — i.e. you mock **your own port**, not Stripe's client
class. **Teach the reconciliation explicitly**; it is exactly the distinction that separates a
brittle suite from a durable one.

### Testcontainers — real dependencies instead of doubles

Testcontainers (<https://testcontainers.com>, Java module `org.testcontainers`) runs real
dependencies — Postgres, Kafka, a message broker — in Docker containers for the duration of a test,
with JUnit 5 integration via `@Testcontainers` and `@Container`. **`[unverified]`** — not fetched
this session; annotation names and the AtomicJar/Docker acquisition are from memory.

**Why it belongs in this course.** It is the strongest available answer to the repo's dependency
category 2 ("Local-substitutable"). For the banking domain, a `Ledger` backed by a real Postgres in a
container tests the invariant *money is neither created nor destroyed* against the real transaction
semantics — which a fake cannot do, because the fake shares the developer's misconceptions about
isolation levels. That is the honest argument for Testcontainers over mocks, and it is stronger than
"mocks are bad".

### ArchUnit — mechanically enforcing a seam

ArchUnit (<https://www.archunit.org>) lets you write architecture rules **as JUnit tests**: e.g.
`layeredArchitecture()`, `onionArchitecture()`, and
`noClasses().that().resideInAPackage(...).should().dependOnClassesThat()...`. **`[unverified]`** —
API names from memory, not fetched from the user guide.

**This is the Java answer to a question the repo raises but does not solve.** The repo defines seams
and insists tests live at them — but nothing stops a developer from importing across a seam.
ArchUnit turns "the seam is here" from a convention into a **failing build**. For a banking example:
assert that nothing in `domain` depends on `infrastructure`. Make this connection explicit in the
Java lesson; it is one of the highest-value transfers in the whole course.

### Records, sealed interfaces, and illegal states

**The phrase "make illegal states unrepresentable" is not Java-native.** It is strongly associated
with **Yaron Minsky** (Jane Street) writing about OCaml, in the "Effective ML" material.
**`[unverified]`** — primary source not fetched. Attribute it to Minsky/the ML community, not to
Java.

**Verified JEPs** (all **Metadata verified** from openjdk.org search results):

| Feature | JEP | Standard in |
| --- | --- | --- |
| **Records** | [JEP 395](https://openjdk.org/jeps/395) | **Java 16** (previewed in 14, 15) |
| **Sealed Classes** | [JEP 409](https://openjdk.org/jeps/409) | **Java 17** (previewed in 15, 16) |
| **Pattern Matching for switch** | [JEP 441](https://openjdk.org/jeps/441) | **Java 21** (previewed 17–20) |
| **Record Patterns** | [JEP 440](https://openjdk.org/jeps/440) | **Java 21** |

Together these give Java **algebraic data types**: records are product types, sealed interfaces are
sum types, and pattern-matching `switch` over a sealed type is checked for **exhaustiveness** — JEP
441 states that when the selector's type is a sealed class, the compiler can use the `permits` clause
to determine exhaustiveness and thereby remove the need for a `default` clause. **Paraphrase, not
verbatim.**

For the banking domain this is the sharpest possible illustration: a `sealed interface
TransferResult permits Completed, Rejected, Pending` makes "a transfer that is both completed and
rejected" **unrepresentable**, and the compiler forces every caller to handle every case. That is the
type system doing what a test would otherwise have to do — and it is the best possible bad-then-good
pair for a lesson on illegal states.

### Effective Java

Joshua Bloch, *Effective Java*, **3rd edition**, Addison-Wesley, **2018** (covers Java 7/8/9).
**`[unverified]`** — publisher page not fetched; ISBN not verified.

**Item numbers are deliberately omitted.** They shifted between the 2nd and 3rd editions, and I could
not fetch a real 3rd-edition table of contents. The relevant items by **title** are: *"Minimize the
accessibility of classes and members"*, *"Prefer interfaces to abstract classes"*, *"Design and
document for inheritance or else prohibit it"*, *"Minimize mutability"*, *"Favor composition over
inheritance"*, and *"Prefer dependency injection to hardwiring resources"*. **`[unverified]`** for
exact titles. **Do not print an item number without checking a 3rd-edition TOC** — a wrong item
number is the most visible citation error available in Java writing.

*"Minimize the accessibility of classes and members"* is Parnas's information hiding (§2a) rendered
as a language-level rule, and *"Prefer dependency injection to hardwiring resources"* is precisely
the repo's "Accept dependencies, don't create them" (**Quoted**). Those two connections are worth
making explicitly — they let the reader see that the repo's advice is not novel, which paradoxically
makes it more trustworthy.

### Clock — the JDK's own seam

Prefer `java.time.Clock` over an invented `TimeProvider` interface. It was added to the JDK
specifically so that time-dependent code could be tested (`Clock.fixed`, `Clock.offset`), which makes
it **stronger evidence than any interface we could invent** that seams are a mainstream design
concern rather than a testing hack. **`[unverified]`** — the design rationale is not quoted from a
JDK document here, though `Clock.fixed` and `Clock.systemUTC` are standard API.

---

## Reading path

Opinionated and ordered, for someone whose goal is to **understand these skills deeply enough to
teach them**. The ordering principle: *read the thing that makes the next thing legible.* Free
sources are marked **(free)** — the first four items cost nothing and can be done in an afternoon.

### Tier 1 — Read these first, in this order (all free, ~4 hours total)

1. **Parnas, "On the Criteria To Be Used in Decomposing Systems into Modules" (1972)** — **(free)**
   Six pages, and everything else in this document is downstream of it. Read it first because it
   makes the repo's whole vocabulary legible at once: *interface*, *hiding*, *what a module is for*.
   If you read only one thing, read this. <http://rule11.tech/papers/1971-decomposing-parnas.pdf>

2. **Ousterhout & Martin, `aposd-vs-clean-code` (2024–25)** — **(free)**
   Read second, immediately after Parnas, because it shows two serious practitioners disagreeing
   about how to *apply* Parnas — and it is the context the repo's "Rejected framings" section sits
   in. Also the fastest way to acquire Ousterhout's actual position without buying the book.
   <https://github.com/johnousterhout/aposd-vs-clean-code>

3. **Fowler, "TestDouble" + "UbiquitousLanguage" + "BoundedContext" bliki entries** — **(free)**
   Twenty minutes, and they fix three vocabularies the repo uses but never defines. Read the test
   double taxonomy *before* touching the repo's `mocking.md` or you will misread it.

4. **Nygard, "Documenting Architecture Decisions" (2011)** — **(free)**
   Five minutes. The shortest complete idea in this document, and you can apply it the same day.
   <https://www.cognitect.com/blog/2011/11/15/documenting-architecture-decisions>

### Tier 2 — The two books that do the most work

5. **Feathers, *Working Effectively with Legacy Code* (2005)** — read **Chapter 4, "The Seam Model"**
   first, out of order. It is the single highest-leverage chapter for this course: it defines the
   term the repo builds its whole design vocabulary on, and Chapter 13 gives you characterization
   tests. Read the rest as a reference when you hit the problem in its title.

6. **Ousterhout, *A Philosophy of Software Design*, 2nd ed. (2021)** — short (under 200 pages),
   and after Tier 1 item 2 you will read it critically rather than devotionally. This is where
   "deep module" actually comes from.

### Tier 3 — Depth in the areas you will teach

7. **Evans, *Domain-Driven Design* (2003)** — but start with the **free DDD Reference PDF**, not the
   book. Read Part IV (Strategic Design) before Part I if your goal is to understand `CONTEXT.md`
   and `CONTEXT-MAP.md`; the tactical patterns matter less for this course.

8. **Beck, *Test-Driven Development: By Example* (2002)** — read it **with** §3b of this document
   open. Beck's book is a demonstration, not an evidence review, and reading it alongside the Fucci
   findings is what turns you from an advocate into someone who can survive a hostile question.

9. **Fucci et al., "A Dissection of the Test-Driven Development Process" (2017)** — **(free)**
   The most intellectually honest thing in the TDD literature. If you intend to teach TDD to
   skeptics, this is non-optional. <https://arxiv.org/abs/1611.05994>

10. **Hermans, *The Programmer's Brain* (2021)** — the bridge from cognitive science to code, and the
    right source to lean on instead of over-claiming from Miller and Sweller directly.

### Tier 4 — Reference, read as needed

11. Hunt & Thomas, *The Pragmatic Programmer* (tracer bullets); Freeman & Pryce, *GOOS* (walking
    skeleton, London school); Meszaros, *xUnit Test Patterns* (the full doubles catalogue);
    Fowler, *Refactoring*, **2nd ed.** (the smell catalogue the repo actually uses); Bloch,
    *Effective Java*, 3rd ed.; Zeller, *The Debugging Book* **(free)**;
    Bjork & Bjork, "Making things hard on yourself, but in a good way" **(free)**.

### Top 5, if you only do five

**Parnas 1972 → the Ousterhout/Martin debate → Feathers ch. 4 → Fowler's three bliki entries →
Fucci et al. 2017.** All five are free, they total maybe six hours, and between them they cover the
origin of the module concept, the live argument about it, the definition of *seam*, the vocabulary
the repo borrows silently, and the honest state of the TDD evidence. Everything else in this document
is elaboration on those five.

---

## Bibliography

Stable URLs. Entries are marked with the highest evidence tier reached in this document.

### Fetched and quoted in this session

- **Parnas, D. L.** "On the Criteria To Be Used in Decomposing Systems into Modules."
  *Communications of the ACM* 15(12), December 1972, pp. 1053–1058.
  DOI: <https://dl.acm.org/doi/10.1145/361598.361623>.
  Text quoted here is the Carnegie-Mellon technical report of the same title, **August 1971**:
  <http://rule11.tech/papers/1971-decomposing-parnas.pdf> — **Quoted**.
- **Ousterhout, John, and Robert C. Martin.** *A Philosophy of Software Design vs. Clean Code*
  (discussion record, September 2024 – February 2025).
  <https://github.com/johnousterhout/aposd-vs-clean-code> — **Quoted**.
- **Ousterhout, John.** *A Philosophy of Software Design*, 2nd ed., July 2021, ISBN 173210221X.
  Author's book page: <https://web.stanford.edu/~ouster/cgi-bin/book.php> — **Metadata verified**.
- **Feathers, Michael C.** *Working Effectively with Legacy Code*. Pearson (Robert C. Martin Series),
  2005. ISBN-10 0-13-117705-2, ISBN-13 978-0-13-117705-5, 464 pp.
  <https://www.informit.com/store/working-effectively-with-legacy-code-9780131177055> —
  **Metadata verified**, **Chapter located**.
- **Nagappan, N., E. M. Maximilien, T. Bhat & L. Williams.** "Realizing quality improvement through
  test driven development: results and experiences of four industrial teams." *Empirical Software
  Engineering* 13(3), 2008, pp. 289–302. DOI: <https://doi.org/10.1007/s10664-008-9062-z>.
  Author PDF: <https://www.microsoft.com/en-us/research/wp-content/uploads/2009/10/Realizing-Quality-Improvement-Through-Test-Driven-Development-Results-and-Experiences-of-Four-Industrial-Teams-nagappan_tdd.pdf>
  — **Quoted**.
- **Fucci, D., H. Erdogmus, B. Turhan, M. Oivo & N. Juristo.** "A Dissection of the Test-Driven
  Development Process: Does It Really Matter to Test-First or to Test-Last?" *IEEE Transactions on
  Software Engineering* 43(7), July 2017, pp. 597–614.
  Preprint: <https://arxiv.org/abs/1611.05994> — **Quoted**.
- **Ghafari, M., T. Gross, D. Fucci & M. Felderer.** "Why Research on Test-Driven Development is
  Inconclusive?" ESEM '20, 8–9 October 2020, Bari, Italy.
  DOI: <https://doi.org/10.1145/3382494.3410687>. Preprint: <https://arxiv.org/pdf/2007.09863> —
  **Quoted**.
- **Fowler, Martin.** "TestDouble", 17 January 2006.
  <https://martinfowler.com/bliki/TestDouble.html> — **Quoted**.
- **Fowler, Martin.** "UbiquitousLanguage", 31 October 2006.
  <https://martinfowler.com/bliki/UbiquitousLanguage.html> — **Quoted**.
- **Fowler, Martin.** "BoundedContext", 15 January 2014.
  <https://martinfowler.com/bliki/BoundedContext.html> — **Quoted**.
- **Nygard, Michael.** "Documenting Architecture Decisions", 15 November 2011.
  <https://www.cognitect.com/blog/2011/11/15/documenting-architecture-decisions> — **Quoted**.
- **North, Dan.** "Introducing BDD." *Better Software* magazine, March 2006.
  <https://dannorth.net/introducing-bdd/> — **Quoted**.
- **Conway, Melvin E.** "How Do Committees Invent?" *Datamation*, April 1968.
  <https://www.melconway.com/Home/Committees_Paper.html> — **Quoted**.
- **Cockburn, Alistair.** "Hexagonal Architecture" (Ports and Adapters), HaT Technical Report
  2005.02, v0.9, 4 September 2005.
  <https://alistair.cockburn.us/hexagonal-architecture/> — **Quoted**.
- **Bogard, Jimmy.** "Vertical Slice Architecture", 19 April 2018.
  <https://www.jimmybogard.com/vertical-slice-architecture/> — **Quoted**.
- **Mockito project.** "How to write good tests" (project wiki).
  <https://github.com/mockito/mockito/wiki/How-to-write-good-tests> — **Quoted**.
- **Siegmund, J., C. Kästner, S. Apel, C. Parnin, A. Bethmann, T. Leich, G. Saake & A. Brechmann.**
  "Understanding understanding source code with functional magnetic resonance imaging." ICSE 2014.
  DOI: <https://dl.acm.org/doi/10.1145/2568225.2568252>.
  Author PDF: <https://www.cs.cmu.edu/~ckaestne/pdf/icse14_fmri.pdf> — **Metadata verified**.
- **OpenJDK.** JEP 395 (Records) <https://openjdk.org/jeps/395> · JEP 409 (Sealed Classes)
  <https://openjdk.org/jeps/409> · JEP 440 (Record Patterns) <https://openjdk.org/jeps/440> ·
  JEP 441 (Pattern Matching for switch) <https://openjdk.org/jeps/441> — **Metadata verified**.
- **Evans, Eric.** *Domain-Driven Design Reference* (Creative Commons Attribution).
  <https://www.domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf> —
  **Metadata verified** (file retrieved; body text not machine-readable).
- **Pocock, Matt.** `mattpocock/skills`, commit `2ab9580`.
  <https://github.com/mattpocock/skills> — **Quoted** throughout.

### Cited but not verified in this session — check before quoting

- **Evans, Eric.** *Domain-Driven Design: Tackling Complexity in the Heart of Software.*
  Addison-Wesley, 2003. ISBN 978-0-321-12521-7. — chapter numbers **`[unverified]`**.
- **Beck, Kent.** *Test-Driven Development: By Example.* Addison-Wesley, 2002.
  <https://dl.acm.org/doi/10.5555/579193> — ISBN **`[unverified]`**.
- **Beck, Kent.** *Extreme Programming Explained: Embrace Change.* 1999 / 2nd ed. 2004. **`[unverified]`**.
- **Meszaros, Gerard.** *xUnit Test Patterns: Refactoring Test Code.* Addison-Wesley, 2007.
  Companion site <http://xunitpatterns.com> — **unreachable this session**. **`[unverified]`**.
- **Freeman, Steve & Nat Pryce.** *Growing Object-Oriented Software, Guided by Tests.*
  Addison-Wesley, 2009. **`[unverified]`**.
- **Fowler, Martin.** *Refactoring: Improving the Design of Existing Code.* 1st ed. (Java) 1999;
  2nd ed. (JavaScript) 2018/2019. <https://refactoring.com>. 1st-ed. Chapter 3 free copy:
  <http://www.laputan.org/pub/patterns/fowler/smells.pdf> — ISBNs/years **`[unverified]`**.
- **Fowler, Martin.** "Mocks Aren't Stubs."
  <https://martinfowler.com/articles/mocksArentStubs.html> — **`[unverified]`**, not fetched.
- **Hunt, Andrew & David Thomas.** *The Pragmatic Programmer: From Journeyman to Master*, 1999;
  20th Anniversary Edition (*Your Journey to Mastery*), 2019. Authors' interview on tracer bullets:
  <https://www.artima.com/articles/tracer-bullets-and-prototypes> — **`[unverified]`**.
- **Cockburn, Alistair.** "Walking Skeleton" — definition **`[unverified]`**; his own page returned
  HTTP 404 at two URLs and the O'Reilly *97 Things* chapter returned HTTP 403.
- **Vernon, Vaughn.** *Implementing Domain-Driven Design.* Addison-Wesley, 2013. **`[unverified]`**.
- **Brandolini, Alberto.** *Introducing EventStorming* (Leanpub, unfinished);
  <https://www.eventstorming.com> — **`[unverified]`**.
- **Adzic, Gojko.** *Specification by Example.* Manning, 2011; *Bridging the Communication Gap*,
  2009. **`[unverified]`**.
- **Dinwiddie, George.** "Three Amigos" — **`[unverified]`**, original post not fetched.
- **Stevens, W. P., G. J. Myers & L. L. Constantine.** "Structured Design." *IBM Systems Journal*
  13(2), 1974. — **`[unverified]`**.
- **Yourdon, E. & L. L. Constantine.** *Structured Design.* Yourdon Press 1975 / Prentice-Hall 1979.
  — **`[unverified]`**.
- **Agans, David J.** *Debugging: The 9 Indispensable Rules…* AMACOM, 2002.
  ISBN-13 978-0-8144-7457-0. <https://archive.org/details/debugging9indisp0000agan>;
  author site <https://debuggingrules.com> (does not list the rules) — full rule list
  **`[unverified]`**.
- **Zeller, Andreas & Ralf Hildebrandt.** "Simplifying and Isolating Failure-Inducing Input."
  *IEEE TSE* 28(2), 2002. — **`[unverified]`**.
- **Zeller, Andreas.** *Why Programs Fail*, Morgan Kaufmann, 2005/2009; *The Debugging Book*
  <https://www.debuggingbook.org> — **`[unverified]`**.
- **Miller, George A.** "The Magical Number Seven, Plus or Minus Two." *Psychological Review* 63(2),
  1956. — **`[unverified]`**.
- **Cowan, Nelson.** "The magical number 4 in short-term memory." *Behavioral and Brain Sciences*
  24(1), 2001. — **`[unverified]`**.
- **Sweller, John.** "Cognitive load during problem solving." *Cognitive Science* 12(2), 1988; and
  Sweller, van Merriënboer & Paas, *Educational Psychology Review*, 1998. — **`[unverified]`**.
- **Hermans, Felienne.** *The Programmer's Brain.* Manning, 2021. — **`[unverified]`**.
- **Bjork, R. A. & E. L. Bjork.** "A new theory of disuse and an old theory of stimulus fluctuation"
  (1992); Bjork, R. A. (1994) "Memory and metamemory considerations in the training of human beings";
  Bjork & Bjork, "Making things hard on yourself, but in a good way."
  <https://bjorklab.psych.ucla.edu/research/> — **Metadata verified** (lab page live), details
  **`[unverified]`**.
- **Roediger, H. L. & J. D. Karpicke.** "Test-Enhanced Learning." *Psychological Science* 17(3),
  2006. — **`[unverified]`**.
- **Cepeda, N. J. et al.** "Distributed practice in verbal recall tasks." *Psychological Bulletin*
  132(3), 2006. — **`[unverified]`**.
- **Bloch, Joshua.** *Effective Java*, 3rd ed., Addison-Wesley, 2018 — item numbers deliberately
  **omitted**; **`[unverified]`**.
- **Rafique, Y. & V. B. Misic.** "The Effects of Test-Driven Development on External Quality and
  Productivity: A Meta-Analysis." *IEEE TSE* 39(6), 2013, pp. 835–856. — effect sizes
  **`[unverified]`**.
- **Munir, H., M. Moayyed & K. Petersen.** "Considering rigor and relevance when evaluating test
  driven development: A systematic review." *Information and Software Technology* 56(4), 2014,
  pp. 375–394. — **`[unverified]`** (finding quoted second-hand via Ghafari et al. 2020).
- **Shull, F. et al.** "What Do We Know about Test-Driven Development?" *IEEE Software* 27(6), 2010.
  — **`[unverified]`**.
- **Fagan, Michael E.** "Design and code inspections to reduce errors in program development."
  *IBM Systems Journal* 15(3), 1976. — **`[unverified]`**.
- **Ohno, Taiichi.** *Toyota Production System.* Productivity Press, English ed. 1988; and
  Card, A. J. "The problem with '5 whys'", *BMJ Quality & Safety*, 2017. — **`[unverified]`**.
- **Paul, Richard & Linda Elder.** *The Thinker's Guide to Socratic Questioning.* — **`[unverified]`**.
- **Minsky, Yaron.** "Make illegal states unrepresentable" (Jane Street / OCaml). — **`[unverified]`**.
- **Martin, Robert C.** *Clean Architecture.* Prentice Hall, 2017; **Palermo, Jeffrey**, "Onion
  Architecture" (2008). — **`[unverified]`**.
- **JUnit 5** <https://junit.org/junit5/> · **AssertJ** <https://assertj.github.io> ·
  **Testcontainers** <https://testcontainers.com> · **ArchUnit** <https://www.archunit.org> —
  **`[unverified]`**, official docs not fetched this session.

---

## Appendix — everything currently marked `[unverified]`

Grouped by how much it would cost to close the gap, so the next session can triage.

**Cheap to close (free, fetchable, <10 min each):**
Fowler "Mocks Aren't Stubs" · Agile Manifesto principles · Dinwiddie's Three Amigos post ·
`xunitpatterns.com` (retry — was connection-refused, not blocked) · JUnit 5 User Guide ·
ArchUnit user guide · Testcontainers docs · Zeller's *The Debugging Book* · Brandolini's
eventstorming.com · Palermo's Onion Architecture posts · Minsky on illegal states.

**Moderate (paywalled abstract or TOC needed):**
Beck *TDD by Example* ISBN · Evans DDD chapter numbers (open the free DDD Reference PDF by hand —
it downloads fine, it just is not machine-readable) · Fowler *Refactoring* 2nd-ed. year/ISBN ·
Bloch *Effective Java* 3rd-ed. item numbers · Agans' full 9-rule list · Stevens/Myers/Constantine
1974 · Rafique & Misic effect sizes · Roediger & Karpicke · Cepeda et al. · Cowan 2001 · Miller 1956.

**Hard or blocked:**
Feathers' "enabling point" as a **verbatim** quotation, and the three seam-type descriptions in
Feathers' own words — the O'Reilly TOC returns **HTTP 403** to automated fetch and the publisher page
carries no body text. Likely needs a physical or licensed copy. *(The seam definition itself and the
Chapter 4 location are both settled — only the verbatim wording of the taxonomy and enabling point
remain open.)*
Cockburn's own "Walking Skeleton" page — **HTTP 404** at both known URLs; try the Wayback Machine.
APOSD's internal treatment of depth (to fully close the §2b straw-man question) — needs the book.
