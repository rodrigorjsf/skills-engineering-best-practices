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
| `Agile Software Development.pdf` | 557 | **none** — 0 chars over a 40-page sample, 1 image per page | **empty metadata**: no title, author, producer or creator | **Quotable via OCR + visual verification — see §3.** Identity and © 2003 confirmed from the copyright page |
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

> **⚠️ Dating retracted by §3.** The paragraphs below originally read the volatility gate as a **2018** self-correction, twenty-one years after the 1996 line. **It is already in Agile PPP, 2003, p. 129.** The convergence claim survives; the chronology does not. Read §3 before using any of this.

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

## 3 · *Agile PPP* — READ AFTER ALL, and it corrects §2

> **⚠️ This section replaces a wrong recommendation.** It first read *"do not OCR it, and do not cite it"*, on the grounds that OCR is not verbatim text and the book's identity was unconfirmed. **The reader pushed back and was right.** Both objections dissolved on contact with the evidence, and the chapter turns out to contain the one thing the 2018 book does not. Recorded rather than quietly rewritten.

### Method, and why the resulting quotes are not second-class

`tesseract 5.5.3` with `eng` is installed. Pages were rendered from the scan at **300 dpi** with PyMuPDF and OCR'd. Quality on body text is high; only display type on the cover garbles.

**But the quotes below are not trusted to OCR.** OCR was used only to *locate* the chapter — a cheap scan of the top 28% of 75 pages, matching a heading. Every sentence quoted here was then **read directly off the rendered page image** before being written down. The evidence tier is therefore **Quoted**, not a lesser sub-tier: the page was read, exactly as a page of a text-layer PDF is read.

Locators are solid. A page footer gives `130 Chapter 11 · DIP: The Dependency-Inversion Principle` at PDF page 154, fixing the offset at **printed = PDF − 24**.

### Identity and date, confirmed from inside the artifact

The Library of Congress page and the copyright line, read at 300 dpi:

> Martin, Robert C. **Agile software development: principles, patterns, and practices** / Robert Martin. … **ISBN 0-13-597444-5** … QA76.76.D47 M362 2002 … 005.1—dc21 2002070056
>
> **Prentice © 2003 by Pearson Education, Inc.**

**Metadata verified**, from the book rather than from a publisher's web page. This **independently confirms the © 2003 (not 2002)** correction already carried in `RESOURCES.md` — and shows where the wrong year comes from: the **CIP data is stamped 2002**, a year before publication. Anyone reading the catalogue block instead of the copyright line gets 2002.

### Finding 1 — the volatility gate is **not** a 2018 self-correction

This is the correction to §2 of this file. Printed **p. 129**, section *Depend On Abstractions* <span>QUOTED — read from the page image</span>:

> A somewhat more **naive**, yet still very powerful, interpretation of the DIP is the simple heuristic: "Depend on abstractions." Simply stated, this heuristic recommends that you should not depend on a concrete class—that all relationships in a program should terminate on an abstract class or an interface.
>
> Certainly this heuristic **is usually violated at least once in every program**. Somebody has to create the instances of the concrete classes, and whatever module does that will depend on them. Moreover, **there seems no reason to follow this heuristic for classes that are concrete but nonvolatile. If a concrete class is not going to change very much, and no other similar derivatives are going to be created, then it does very little harm to depend on it.**

**The exception is already there in 2003.** §2 of this file framed the volatility gate as Martin correcting his own 1996 unconditional scope twenty-one years later. That framing is wrong and is retracted. The actual arc:

| Year | What he writes | The gate |
|---|---|---|
| 1996, *The C++ Report* | *"can be applied wherever one class sends a message to another"* | none stated |
| **2003, Agile PPP p. 129** | *"no reason to follow this heuristic for classes that are concrete but nonvolatile"* | **present, as an exception** |
| 2018, *Clean Architecture* Ch. 11 | *"treating this idea as a rule is unrealistic"*; all four practices say **volatile** | promoted to the organizing idea |

So the gate was **seven years** behind the unconditional line, not twenty-one, and 2018 promotes an existing exception rather than inventing a qualification. The convergence claim in §2 survives intact — three independent statements of the same gate — but its **dating and its character were wrong**.

### Finding 2 — Martin calls the folk version "naive" himself

Lesson 0006's whole first move is that *"use an interface"* is not inversion. **Martin says so in his own voice**, and the word he uses is *naive* — twice on facing pages. Printed p. 128, of the layering diagram: *"A naive interpretation of this statement might lead a designer to produce a structure similar to Figure 11-1."* Then p. 129, of the heuristic itself, quoted above.

This is a **primary source for a claim the lesson currently argues on its own**. It is also the sharper counter-datum: the lesson uses the 1996 unconditional line, and Dan North diagnosing the resulting shadow codebases. Martin naming the naive reading himself is stronger and more generous than both.

### Finding 3 — both parts are present in 2003, which brackets the 2018 silence

Printed **p. 127**, verbatim, <span>QUOTED — read from the page image</span>:

> **DIP: The Dependency-Inversion Principle**
> **a.** High-level modules should not depend on low-level modules. Both should depend on abstractions.
> **b.** Abstractions should not depend on details. Details should depend on abstractions.

Note the **hyphen** — *Dependency-Inversion* in 2003, unhyphenated in the 1996 article and in 2018.

§2 recorded that the two-part wording has **0 hits** in *Clean Architecture*. That observation now has a bracket rather than being a lone silence: **present 1996 → present 2003 → wording absent 2018.** The §2 restraint still applies in full — this dates a disappearance, it does **not** license saying he abandoned part B.

### Finding 4 — why the word "inversion", in his own words

Printed **p. 127** <span>QUOTED — read from the page image</span>:

> Over the years, many have questioned why I use the word "inversion" in the name of this principle. It is because more traditional software development methods, such as Structured Analysis and Design, tend to create software structures in which high-level modules depend on low-level modules, and in which policy depends on detail.

Lesson 0006's figure caption asserts *"É daí que vem a palavra 'inversion'"* and reasons it out from the diagram. **Martin explains it directly.** Cite him instead of reasoning it out.

Also on p. 129, the *Hollywood principle* — *"Don't call us, we'll call you"* — named as another framing of the same inversion of ownership.

## 4 · NOT A SOURCE — *System Design Interview*

Chrome print-to-PDF (`Skia/PDF m76`) of educative.io course pages. Measured over its 309,486 characters: `deep module` **0**, `dependency inversion` **0**, `SOLID` **0**, `YAGNI` **0**, `DRY` **0**, `walking skeleton` **0**, `vertical slice` **0**, `seam` **1** (incidental).

**Touches no gap and no lesson.** It is a study aid for interviews, not a primary source, and it should not enter `RESOURCES.md`.

---

## 5 · Net effect on the gap list

| Gap in `RESOURCES.md` | Status after this pass |
|---|---|
| `SOLID` — who coined the acronym? | **RESOLVED** — Martin's own words, printed p. 58, quoted |
| `Clean Architecture`'s stable/volatile qualification | **RESOLVED** — Ch. 11 + *Stable Abstractions*, quoted |
| *Agile PPP* Ch. 11 body | **RESOLVED** — read via OCR + visual verification. Four findings, one of which **retracts §2's dating** |
| Two arXiv paper bodies | untouched |
| `vertical slice` has no located coiner | untouched |
| Rule of Three attribution | untouched |
| No source measures indirection against agent success | untouched — standing negative result |

**Two new items for `RESOURCES.md`:**

- **`Clean Architecture` is now a first-class primary source**, not a chapter-located one: text layer verified, publisher front matter present, index usable for locators.
- **`Agile PPP` is too**, by a different route — see §3 for the method.
- **New hazard:** a calibre-converted ebook carries **no page labels**, so PDF page numbers are not printed page numbers and the offset drifts. **Use the book's own back-of-book index to resolve a printed page.** Do not compute one from an offset.
- **New method, and the standing rule that comes with it.** *"No text layer"* is **not** the same as *"not readable"*. `tesseract` is installed; render at 300 dpi and OCR. **But never quote OCR output.** Use OCR to *locate* — cheaply, by cropping page headers — then **read the rendered page image directly** before writing a quote down. That keeps the evidence tier at **Quoted**, because the page really was read. A scanned page footer also fixes the printed-page offset exactly, which a reflowed ebook cannot do.
- **Watch the CIP trap.** A Library of Congress cataloguing block is stamped up to a year **before** publication — *Agile PPP* reads `2002` in the CIP block and **`© 2003`** on the copyright line four lines below. That is the likely source of the wrong year in circulation. **Read the copyright line, not the catalogue block.**
