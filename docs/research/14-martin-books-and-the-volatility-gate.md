# 14 — Martin's two books, read from the reader's own copies

**Date:** 2026-08-01
**Trigger:** the reader added four PDFs to `docs/books/` and asked whether their contents close open gaps in `RESOURCES.md`.
**Answer, short:** **two gaps close outright, one book cannot be quoted at all, and one is not a source.** One finding also **corrects shipped work** — `lessons/0006-dependency-inversion.html`.

---

## 0 · Provenance first

`RESOURCES.md` records the standing hazard: *"A PDF in `docs/books/` is not automatically a book."* Metadata was checked before a single quote was taken.

| File | Pages | Text layer | Producer / metadata | Verdict |
|---|---|---|---|---|
| `Clean Architecture…pdf` | 444 | **yes**, 524,181 chars | calibre 3.7.0; `author: Robert C. Martin`; title matches | **Quotable.** Publisher front matter present |
| `Agile Software Development.pdf` | 557 | **none** — 0 chars over a 40-page sample, 1 image per page | **empty metadata**: no title, author, producer or creator | **Not quotable.** Image-only scan; identity unconfirmed |
| `System Design Interview_ Grokking the educative.pdf` | 179 | yes | `producer: Skia/PDF m76` — Chrome print-to-PDF | **Not a book.** A web capture of educative.io |
| `2018-John Ousterhout…`, `97-things…`, `DDD - Eric Evans`, `Walking_Skeleton…`, `Working Effectively with Legacy Code` | — | — | — | Already triaged in earlier passes; unchanged |

**Clean Architecture, publisher front matter, verbatim:**

> Copyright © 2018 Pearson Education, Inc.
> ISBN-13: 978-0-13-449416-6 · ISBN-10: 0-13-449416-4
> Library of Congress Control Number: 2017945537

**Metadata verified.** This **confirms** the correction already carried in `RESOURCES.md`: *Clean Architecture* is **© 2018**, not 2017. (The calibre `creationDate` of 2017-09-20 is the ebook conversion date and is not a publication date — do not cite it as one.)

### Locators

The calibre reflow carries **no page labels**, so PDF page ≠ printed page and arithmetic offsets drift. Printed page numbers below are taken from **the book's own back-of-book index**, not computed. Where the index does not supply one, none is asserted — per the no-guessed-locators rule.

| Claim | Printed page | How derived |
|---|---|---|
| Feathers / the SOLID acronym | **58** | index: `Feathers, Michael, 58` and `SOLID principles, 58` |
| "Stable Abstractions", volatility of interfaces | **88** | index: `volatility of interfaces, 88` |
| Ch. 11 opener, DIP | *not asserted* | index entry `Dependency Inversion Principle, 91` is a mention, not the opener |

---

## 1 · GAP CLOSED — who coined `SOLID`

`RESOURCES.md` had this as `[unverified]`: *"Widely attributed to **Michael Feathers**, and Martin's own words saying so were **not found**."* It noted why it mattered: Feathers also coined `seam`, so if it held it would be a real link between the two halves of this course.

**It holds. Martin says it himself, printed p. 58** <span>QUOTED</span>:

> The history of the SOLID principles is long. I began to assemble them in the late 1980s while debating software design principles with others on USENET (an early kind of Facebook). Over the years, the principles have shifted and changed. Some were deleted. Others were merged. Still others were added. The final grouping stabilized in the early 2000s, although I presented them in a different order.
>
> In 2004 or thereabouts, Michael Feathers sent me an email saying that if I rearranged the principles, their first words would spell the word SOLID—and thus the SOLID principles were born.

Three separable facts, and they should stay separable:

1. **Martin assembled the principles** — "beginning in the late 1980s", "stabilized in the early 2000s".
2. **Feathers supplied the acronym**, "in 2004 or thereabouts", by email.
3. **Feathers did not author the principles.** The retelling that says "Feathers coined SOLID" is true only of the *word*.

**The genealogy this unlocks:** Michael Feathers is the source of `seam` (*Working Effectively with Legacy Code*, 2005, p. 31 — already quoted in lesson 0001) **and** of the acronym that packages Martin's principles. That is a documented link between Track I's opening term and Track II's classical vocabulary, in both men's own words. It is a **naming** link, not an intellectual-lineage claim — do not inflate it into one.

---

## 2 · GAP CLOSED, and it corrects lesson 0006 — the volatility gate

`RESOURCES.md` had: *"whether Martin qualifies DIP there stays `[unverified]` and was **not paraphrased from memory**."*

**He qualifies it, explicitly, and the qualification is the whole second half of the chapter.** Ch. 11, *DIP: The Dependency Inversion Principle* <span>QUOTED</span>:

> Clearly, treating this idea as a rule is unrealistic, because software systems must depend on many concrete facilities. For example, the String class in Java is concrete, and it would be unrealistic to try to force it to be abstract. The source code dependency on the concrete `java.lang.string` cannot, and should not, be avoided.

> By comparison, the String class is very stable. Changes to that class are very rare and tightly controlled. Programmers and architects do not have to worry about frequent and capricious changes to String.

> For these reasons, we tend to ignore the stable background of operating system and platform facilities when it comes to DIP. We tolerate those concrete dependencies because we know we can rely on them not to change.

> **It is the volatile concrete elements of our system that we want to avoid depending on.** Those are the modules that we are actively developing, and that are undergoing frequent change.

And from *Stable Abstractions*, printed p. 88 <span>QUOTED</span>:

> Every change to an abstract interface corresponds to a change to its concrete implementations. Conversely, changes to concrete implementations do not always, or even usually, require changes to the interfaces that they implement. Therefore interfaces are less volatile than implementations.

Every one of the four coding practices that follows carries the qualifier <span>QUOTED</span>:

> - Don't refer to **volatile** concrete classes. Refer to abstract interfaces instead.
> - Don't derive from **volatile** concrete classes.
> - Don't override concrete functions.
> - Never mention the name of anything concrete and **volatile**. This is really just a restatement of the principle itself.

### Why this changes a shipped lesson

`lessons/0006-dependency-inversion.html` §4 opens with a counter-datum, and it is Martin's 1996 line:

> Dependency Inversion can be applied wherever one class sends a message to another.

The lesson then reads that unconditional scope as what produces Dan North's *"entire shadow codebases where each class is backed by exactly one interface."* North is presented as the corrective voice.

**That framing is now incomplete, and the missing piece is Martin himself.** Twenty-one years later he writes *"treating this idea as a rule is unrealistic"* and installs a **volatility gate**. The correction is the author's own, published, and in the reader's hands.

It also **strengthens a claim the lesson currently flags as ours.** Lesson 0006 argues that the repo's dependency-category taxonomy (`DEEPENING.md:5-25`) — not the two-adapter rule — is the actual brake, and flags the composition as our inference. Martin's stable/volatile split is **a third independent statement of the same gate**:

| Voice | The gate | Names DIP? |
|---|---|---|
| Martin 2018, Ch. 11 | stable vs **volatile** concretions | yes |
| `DEEPENING.md:5-25` | four dependency categories; only the last two earn a port | no |
| `tdd/mocking.md:3,10-14` | *"Mock at system boundaries only" · "Don't mock… Anything you control"* | no |

Three sources, three vocabularies, one line. **The convergence is now documented across three voices instead of two. Influence is still not documented** — none cites another, and this file does not invent a lineage.

### The second finding, from a silence

Searched across all 524,181 characters of the book:

| String | Hits |
|---|---|
| `depend upon details` | **0** |
| `depend on details` | **0** |
| `depend upon abstractions` | **0** |
| `high level modules` / `high-level modules` | **0** |
| `Abstractions should not` | **0** |

**Neither half of the 1996 two-part wording appears anywhere in *Clean Architecture*.** The 2018 chapter states DIP as *source-code dependencies pointing only at abstractions*, plus the volatility gate.

**State this lexically and stop there.** What is verified is a string absence across 524,181 characters. It is **not** verified that Martin *abandoned* part B — the 2018 formulation (*"source code dependencies refer only to abstractions, not to concretions"*) can be read as subsuming it, and *"Never mention the name of anything concrete and volatile"* is arguably part B restated for the volatile case. **Attributing an authorial choice to a man from a string search is precisely the move this project treats as its worst available failure.** The supportable sentence is: *the 1996 two-part wording appears nowhere in the 2018 book, which restates DIP as dependency direction plus a volatility gate.*

Lesson 0006 already makes the point that **part B is the half retellings drop**, and this is a legitimate second data point for that — a derived silence of the same kind the lesson uses for `injection.html`. It is **not** licence to say he dropped it.

---

## 3 · NOT CLOSED — *Agile PPP*, and why

`RESOURCES.md` wanted *Agile PPP* Ch. 11, *"DIP: The Dependency-Inversion Principle"* — **TOC confirmed, body not read**.

**The body still has not been read, and this PDF cannot read it.** 557 pages, one image per page, **zero extractable characters**, and no metadata of any kind — no title, no author, no producer. Two independent problems:

1. **It cannot be quoted** without an OCR pass, and OCR output is not verbatim text — it is a transcription with an error rate, and would need its own evidence sub-tier.
2. **Its identity is unconfirmed.** Nothing inside it has been read that says it is Martin's *Agile Software Development: Principles, Patterns, and Practices*. The filename is not evidence.

**Recommendation: do not OCR it, and do not cite it.** *Clean Architecture* Ch. 11 already supplies the qualification that was wanted, from the same author, verbatim, with publisher front matter and index-derived locators. *Agile PPP* would be corroboration of a point already made — the weakest possible return for the most expensive possible extraction. The gap note should be rewritten to say the question is **answered from the 2018 book**, with *Agile PPP* left explicitly unread rather than pending.

The **© 2003 (not 2002)** correction for *Agile PPP* was already verified against Pearson's own pages and is unaffected.

## 4 · NOT A SOURCE — *System Design Interview*

Chrome print-to-PDF (`Skia/PDF m76`) of educative.io course pages. Measured over its 309,486 characters: `deep module` **0**, `dependency inversion` **0**, `SOLID` **0**, `YAGNI` **0**, `DRY` **0**, `walking skeleton` **0**, `vertical slice` **0**, `seam` **1** (incidental).

**Touches no gap and no lesson.** It is a study aid for interviews, not a primary source, and it should not enter `RESOURCES.md`.

---

## 5 · Net effect on the gap list

| Gap in `RESOURCES.md` | Status after this pass |
|---|---|
| `SOLID` — who coined the acronym? | **RESOLVED** — Martin's own words, printed p. 58, quoted |
| `Clean Architecture`'s stable/volatile qualification | **RESOLVED** — Ch. 11 + *Stable Abstractions*, quoted |
| *Agile PPP* Ch. 11 body | **Closed as won't-do**, with reason. Not pending |
| Two arXiv paper bodies | untouched |
| `vertical slice` has no located coiner | untouched |
| Rule of Three attribution | untouched |
| No source measures indirection against agent success | untouched — standing negative result |

**Two new items for `RESOURCES.md`:**

- **`Clean Architecture` is now a first-class primary source**, not a chapter-located one: text layer verified, publisher front matter present, index usable for locators.
- **New hazard:** a calibre-converted ebook carries **no page labels**, so PDF page numbers are not printed page numbers and the offset drifts. **Use the book's own back-of-book index to resolve a printed page.** Do not compute one from an offset.
