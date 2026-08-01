# 05 — Does Ousterhout define module *depth* as a ratio?

**Question.** In *A Philosophy of Software Design* (2nd ed.), does John Ousterhout
define module **depth** as a *ratio* — specifically implementation-lines over
interface-lines, or any line-count measure — or is his treatment qualitative
(cost vs benefit, illustrated by the rectangle diagram)?

**Why it matters.** The `mattpocock/skills` repo lists a rejected framing and
attributes it to Ousterhout. Lesson 0002 must not retell that as "Ousterhout is
wrong and the repo corrects him" unless the attribution actually holds.

---

## 1. The accusation, verbatim

**Quoted** — read from the live upstream clone at `/home/rodrigo/Workspace/skills`.

`skills/engineering/codebase-design/SKILL.md:107`

> - **Depth as ratio of implementation-lines to interface-lines** (Ousterhout): rewards padding the implementation. We use depth-as-leverage instead.

The repo's own positive definition, `skills/engineering/codebase-design/SKILL.md:20`:

> **Depth** — leverage at the interface: the amount of behaviour a caller (or test) can exercise per unit of interface they have to learn. A module is **deep** when a large amount of behaviour sits behind a small interface, **shallow** when the interface is nearly as complex as the implementation.

The only other Ousterhout references upstream are `README.md:168` (book link) and
`skills/engineering/codebase-design/DESIGN-IT-TWICE.md:3` ("Design It Twice").
No upstream file quotes Ousterhout defining depth.

---

## 2. Verdict

**(b) CONFIRMED qualitative — no line-count ratio — with one qualification that
must be stated, not smoothed over.**

Three findings, strongest first.

### 2.1 Ousterhout's own text *contradicts* the line-count reading

**Quoted · Chapter located** — *A Philosophy of Software Design*, 2nd ed.,
Chapter 6 "General-Purpose Modules are Deeper", **p. 40**, from the official
extract hosted by the author on his Stanford page:
<https://web.stanford.edu/~ouster/cgi-bin/aposd2ndEdExtract.pdf>
(linked from <https://web.stanford.edu/~ouster/cgi-bin/book.php>; the extract is
a scanned/image PDF, read page-by-page as rendered images).

> What particularly surprised me is that general-purpose interfaces are simpler and deeper than special-purpose ones, and they result in less code in the implementation.

This is falsification, not absence of evidence. Under an
*implementation-lines / interface-lines* ratio, **less implementation code makes
a module shallower**. Ousterhout says the very same change makes it **deeper**.
The framing the repo rejects is not a position his text supports.

The same extract, p. 40, also gives the direction of travel:

> the module's functionality should reflect your current needs, but its interface should not

### 2.2 His own lecture notes say size is not the measure

**Quoted** — John Ousterhout, CS 190 Software Design Studio (Winter 2018) lecture
notes, "Modular Design", his own Stanford server:
<https://web.stanford.edu/~ouster/cgi-bin/cs190-winter18/lecture.php?topic=modularDesign>

These notes use the book's exact vocabulary ("Classes Should be Deep"), and are
the closest publicly-available text to Chapter 4:

> **Classes Should be Deep**
> Deep class: small interface, lots of functionality
> Lots of information hidden
> Example: Unix system calls for file I/O
> Shallow class
> Complex interface and/or not much functionality
> Invoking a method isn't much easier than just typing in the code of the method.
> Shallow classes don't hide much information

and, decisively:

> Size doesn't really matter that much
> Classes in the range of 200-2000 lines are fine
> The most important thing is depth: the power of the abstraction
> It's more important for a class to have a simple interface than a simple implementation

Lines of code appear only as a loose sanity range for class size, explicitly
demoted ("size doesn't really matter that much"), never as a term in a depth
formula.

### 2.3 The rectangle, in his own spoken words — and the numerator he cannot define

**Quoted** — Software Engineering Radio, episode 520, "John Ousterhout on A
Philosophy of Software Design", published transcript, timestamps as printed:
<https://se-radio.net/2022/07/episode-520-john-ousterhout-on-a-philosophy-of-software-design/>

At 00:27:48:

> In the book I use the word Deep to describe modules like that, thinking I use the analog of a rectangle where the area of the rectangle is the functionality of a module and the length of its upper edge is the complexity of the interface. And so the ideal modules those would have very interfaces so it's a very tall skinny rectangle. Small interface and a lot of functionality. Shallow modules are those, that have a lot of interface and not much functionality.

(The transcript is machine-produced and has evident glitches — "would have very
interfaces" — quoted here unchanged.)

At 00:34:57, defining interface, then failing to quantify functionality:

> The interface is everything that anyone needs to know in order to use the module. And to be clear, that's not just the signatures of the methods. Yes, those are part of the interface, but there's lots more, you know, side effects or expectations or dependencies. […] Functionality is harder to define. That's just what it does. Maybe it's the right way to think about a system with a lot of functionality, maybe it's that it can be used in many, many different situations to perform different tasks. Maybe that's the right way to think about it. I don't have as good a definition.

A quantity the author says he has no good definition for is not a metric. The
rectangle is an *illustration of cost vs benefit*, not a formula.

Note also that his notion of *interface* is explicitly wider than the type
signature — which is exactly the repo's own glossary entry. The repo and
Ousterhout agree here.

### 2.4 The qualification — a ratio *does* appear, but not that one

**Quoted** — CS 190 (Spring 2016) lecture notes, "Modular Design", same server:
<https://web.stanford.edu/~ouster/cgi-bin/cs190-spring16/lecture.php?topic=modularDesign>

> Goal for interface design: maximize functionality/(interface complexity) (a sweet interface or module)

and, in the same lecture:

> Rule of thumb: 200-2000 lines is a good size for classes
> […]
> However, size itself isn't the most important metric: it's functionality/(interface complexity)

So Ousterhout *does* write a ratio, in his own teaching materials, on his own
site. It must not be suppressed. But:

- Its numerator is **functionality** (the benefit), not implementation lines.
- Its denominator is **interface complexity** (the cost), not interface lines.
- Neither term is numeric; §2.3 has him saying he cannot define the numerator.
- The *same* lecture demotes line count explicitly.

The repo rejects a ratio *of implementation-lines to interface-lines*. That is a
different ratio over different quantities, and one §2.1 shows his text
contradicts.

---

## 3. What this means for the repo's "Rejected framings" entry

The repo's stated rejection is sound as a *warning* — "depth = more
implementation lines behind fewer interface lines" is a real folk simplification
of APOSD that circulates in blog summaries, and it does reward padding. What does
not hold is the parenthetical attribution **"(Ousterhout)"**: no primary source
found states it, and his Chapter 6 text states the opposite.

Equally, the repo's own `depth-as-leverage` is not a *correction* of Ousterhout —
"behaviour a caller can exercise per unit of interface they have to learn" is a
restatement of "maximize functionality/(interface complexity)" in different
words.

---

## 4. Negative results — reported honestly

- **Chapter 4's literal text was not obtained.** No legitimate source found
  reproduces it. Consequently **no Chapter 4 page number, and no verbatim Figure
  4.1 caption, appears in this document.**
  **Superseded 2026-08-01 — see §6.** Chapter 4 was read in full from a
  legitimate 1st-edition copy. The bullet is kept unedited as the record of what
  was true before that.
- The official author-hosted extract
  (`aposd2ndEdExtract.pdf`, 20 pages, 2nd ed., ISBN 978-1-7321022-2-4, printing
  history "July 2021: Second Edition (v2.0)" — **Metadata verified** from its own
  copyright page) contains only **Chapter 6** (from p. 39) and **Chapter 21
  "Decide What Matters"** (to p. 174), plus the Clean Code comparisons. It does
  **not** contain Chapter 4.
- A Figure 4.1 caption wording ("the area of each rectangle is proportional to the
  functionality…") surfaced only in a **WebSearch result summary**, never in a
  page that was fetched. It is **`[unverified]`** and is deliberately not used as
  evidence above.
  **Superseded 2026-08-01 — see §6.3.** That string is now **Quoted** from the
  1st edition, with one correction: it is a sentence of the **body text**, not
  the figure caption. The caption is a different string, also now quoted.
- **Pirated full-text PDFs** appeared in search results (`milkov.tech/assets/psd.pdf`,
  `pdfcoffee.com` mirrors). They were **not** downloaded or read.
- CS 190 Winter 2019 / 2020 / 2021 have **no** modular-design lecture (their
  lecture lists cover intro, unix, primes, raft, messages, bookReview, wrapup).
  Winter 2018 and Spring 2016 do; those are the two used above.
- `https://web.stanford.edu/~ouster/cgi-bin/cs190-winter25/index.php` returns
  **HTTP 404**.
- `github.com/johnousterhout/aposd-vs-clean-code` (cloned; single `README.md`)
  discusses deep/shallow methods at length and contains **no** ratio, formula, or
  line-count measure of depth. Representative — **Quoted**,
  <https://raw.githubusercontent.com/johnousterhout/aposd-vs-clean-code/main/README.md>:

  > The best methods are those that provide a lot of functionality but have a very simple interface: they replace a large cognitive load (reading the detailed implementation) with a much smaller cognitive load (learning the interface). I call these methods "deep".

  and

  > One of the reasons I use the deep/shallow characterization is that it captures both sides of the tradeoff; it will tell you when a decomposition is good and also when decomposition makes things worse.

- `https://web.stanford.edu/~ouster/cgi-bin/faq.php` and `sayings.php` contain
  nothing on depth.

---

## 5. Sources

| Source | URL | Tier |
|---|---|---|
| `mattpocock/skills`, `skills/engineering/codebase-design/SKILL.md:107` | <https://github.com/mattpocock/skills/blob/main/skills/engineering/codebase-design/SKILL.md> | Quoted (from local clone of upstream) |
| APOSD 2nd ed. extract — Ch. 6 p. 40, Ch. 21 | <https://web.stanford.edu/~ouster/cgi-bin/aposd2ndEdExtract.pdf> | Quoted · Chapter located · Metadata verified |
| Book page (author's own) | <https://web.stanford.edu/~ouster/cgi-bin/book.php> | Metadata verified |
| CS 190 Winter 2018, "Modular Design" | <https://web.stanford.edu/~ouster/cgi-bin/cs190-winter18/lecture.php?topic=modularDesign> | Quoted |
| CS 190 Spring 2016, "Modular Design" | <https://web.stanford.edu/~ouster/cgi-bin/cs190-spring16/lecture.php?topic=modularDesign> | Quoted |
| SE Radio 520 transcript | <https://se-radio.net/2022/07/episode-520-john-ousterhout-on-a-philosophy-of-software-design/> | Quoted |
| `aposd-vs-clean-code` | <https://github.com/johnousterhout/aposd-vs-clean-code> | Quoted |
| APOSD Chapter 4 full text | — | **not obtained** (superseded — see §6) |
| APOSD **1st ed., April 2018, ISBN 978-1-7321022-0-0**, full text | local copy, `docs/books/2018-John Ousterhout-A Philosophy of Software Design.pdf` | Quoted · Chapter located · Metadata verified |

---

## 6. Amendment, 2026-08-01 — Chapter 4 read directly, from the 1st edition

The gap §4 recorded is now closed. The reader supplied a legitimate copy of the
book and Chapter 4 was read in full, along with Chapter 6 and the back matter.

## 6.0 Which edition, and how page numbers are cited here

**Metadata verified** — from the book's **own copyright page**, 1st ed. ebook
p. 3, read directly (**Quoted**):

> Copyright © 2018 John K. Ousterhout. […] Published by Yaknyam Press, Palo Alto, CA.
> Printing History:
> April 2018: First Edition (v1.0)
> November 2018: First Edition (v1.01)
> ISBN 978-1-7321022-0-0
> Digital book(s) (epub and mobi) produced by Booknook.biz.

So the edition statement comes from the book, not from the filename: **First
Edition, ISBN 978-1-7321022-0-0**, distinct from the 2nd ed.'s
978-1-7321022-2-4 recorded in §4. The file is 188 pages, an EPUB→PDF conversion
(PDF `creator`/`producer`: `calibre 3.14.0`), read with PyMuPDF. Path:
`docs/books/2018-John Ousterhout-A Philosophy of Software Design.pdf`. Its PDF
`creationDate` is `D:20190131` — that is the **conversion** timestamp, not a
publication date, and carries no edition information.

**This is the 1st edition. Everything in §§1–5 above that cites a page is the
2nd edition (2021). The two must never be mixed.** Chapter 6 carries the same
number and title in both editions ("General-Purpose Modules are Deeper" —
confirmed against the 2nd-ed. extract in §4); Chapter 4's 2nd-ed. number and
title are `[unverified]` here, since the extract does not contain it. Page
numbers do not transfer between editions in either case.

**Two page numberings, kept apart:**

- **ebook page N/188** — the position in *this* converted file. It is the
  locator used below, written `1st ed. ebook p. N`. The converted Contents
  carries no page numbers at all, and the reflow means this pagination is **not**
  the print edition's.
- **1st-ed. print page** — cited **only where the book's own back matter literally
  prints one** for the term in question. Those entries are real.

Proof the two differ: at ebook p. 39 the text reads "(see the formula on page 6)",
pointing at the complexity formula in §2.1, which sits at ebook p. 18. The
observed ebook−print offset is roughly 11–12 but is **not stable across the
book**, so **no print page below is derived by arithmetic**. Locators without a
literal printed source stay ebook-only.

Print-page locators that *are* literal — **Quoted**, "Summary of Design
Principles", 1st ed. ebook p. 185:

> 4. Modules should be deep (see p. 22)
> 7. General-purpose modules are deeper (see p. 39).
> 6. It's more important for a module to have a simple interface than a simple implementation (see pp. 55, 71).

and the Index, 1st ed. ebook pp. 179 and 183: `deep module, 22` ·
`shallow module, 25`; and "Summary of Red Flags", ebook p. 186:
`Shallow Module: the interface for a class or method isn't much simpler than its implementation (see pp. 25, 110)`.

So **deep module is defined on 1st-ed. print p. 22**, by the book's own index.

## 6.1 The definition of a deep module, in his own words

**Quoted** — 1st ed., §4.4 "Deep modules", ebook p. 34. (The book's own index
lists the term at **1st-ed. print p. 22**; §4.4 spans ebook pp. 34–36, so that
print page is not equated with a single ebook page here.)

> The best modules are those that provide powerful functionality yet have simple interfaces. I use the term deep to describe such modules.

and, closing the same paragraph:

> The best modules are deep: they have a lot of functionality hidden behind a simple interface. A deep module is a good abstraction because only a small fraction of its internal complexity is visible to its users.

**Shallow**, §4.5, ebook p. 36:

> On the other hand, a shallow module is one whose interface is relatively complex in comparison to the functionality that it provides.

and the Red Flag box, §4.5, ebook p. 37 — the Red Flags summary indexes
"Shallow Module" at **1st-ed. print pp. 25, 110**:

> A shallow module is one whose interface is complicated relative to the functionality it provides. Shallow modules don't help much in the battle against complexity, because the benefit they provide (not having to learn about how they work internally) is negated by the cost of learning and using their interfaces. Small modules tend to be shallow.

## 6.2 The rectangle — the book's own wording

**Quoted** — 1st ed., §4.4, ebook p. 34, the sentences that set up Figure 4.1:

> To visualize the notion of depth, imagine that each module is represented by a rectangle, as shown in Figure 4.1. The area of each rectangle is proportional to the functionality implemented by the module. The top edge of a rectangle represents the module's interface; the length of that edge indicates the complexity of the interface.

The **caption itself** is a different string — **Quoted**, ebook p. 35:

> Figure 4.1: Deep and shallow modules. The best modules are deep: they allow a lot of functionality to be accessed through a simple interface. A shallow module is one with a relatively complex interface, but not much functionality: it doesn't hide much complexity.

(The figure is a single raster image on ebook p. 35 with no text layer of its
own; the caption above is the surrounding text. The labels *inside* the image,
read by rendering the page: `Interface (cost: less is better)`,
`Functionality (benefit: more is better)`, `Deep Module`, `Shallow Module`.)

And immediately after the caption, ebook p. 35 — the sentence that settles what
the rectangle *is*:

> Module depth is a way of thinking about cost versus benefit. The benefit provided by a module is its functionality. The cost of a module (in terms of system complexity) is its interface. […] The best modules are those with the greatest benefit and the least cost. Interfaces are good, but more, or larger, interfaces are not necessarily better!

This confirms §2.3 above from the primary text rather than from a machine
transcript: "a way of thinking about cost versus benefit", in the book, in his
own printed words.

## 6.3 Item 3 — is there a formula, ratio, metric or line-count? **No.**

**Confirmed absence, read from the primary text.**

A whole-word regex sweep over **all 188 pages** for
`\bratio\b`, `\bformula`, `\bmetric`, `lines of code`, `number of lines`,
`\bLOC\b`, `divided by`, `per unit`, `\bproportional`, `\bmeasure`:

- **`ratio` occurs zero times in the entire book.** (Beware: a naive substring
  search for `ratio` matches inside `operations`, `traversing`… — the earlier
  scan produced exactly those false positives. The whole-word form returns
  nothing.)
- **`formula` occurs once in the entire book**, at ebook p. 39, and it is a
  cross-reference to the *complexity* formula in §2.1 — not a depth formula:
  > interfaces should be designed to make the common case as simple as possible (see the formula on page 6)
- **No `divided by`, no `per unit`, no `LOC`, no depth metric of any kind
  anywhere in Chapter 4.**
- `proportional` occurs once, in the rectangle sentence quoted in §6.2 — and its
  subject is *functionality*, the same quantity §2.3 has him admitting he cannot
  define.

**A text sweep alone would not have been sufficient, and this file proves it.**
The book's one real equation — the complexity formula of §2.1,
*C = Σ<sub>p</sub> c<sub>p</sub>t<sub>p</sub>* — is a **raster image** with no
text layer (ebook p. 19, a 79×33px `DeviceRGB` image; read by rendering the page
and looking at it). A regex could never have found it. So the sweep was
completed with an image census.

**Image census — all 188 pages, `page.get_images(full=True)`:** 30 images in the
whole book. Inside Chapter 4 (ebook pp. 31–39) there are exactly **three image
placements on two pages**, all identified by rendering and viewing them:

- ebook p. 35 — **Figure 4.1**, 251×159px, the deep/shallow rectangle diagram.
  Its only in-figure labels are `Interface (cost: less is better)`,
  `Functionality (benefit: more is better)`, `Deep Module`, `Shallow Module`.
  **No numbers, no axes, no equation** — it is a labelled sketch of cost vs
  benefit, exactly as the body text says.
- ebook p. 37 — two identical 28×28px images, the decorative red-flag icons
  flanking the "Red Flag: Shallow Module" heading. Not content.

**Verdict on item 3 — CONFIRMED ABSENCE.** No formula, no ratio, no metric and
no line-count measure of depth exists anywhere in Chapter 4, in either its text
or its figure. Across the rest of the 1st edition the text-layer sweep likewise
returns nothing metric-shaped for depth; the only equation in the book is the
§2.1 complexity formula, whose subject is complexity weighted by developer time,
not depth.

## 6.4 Interface size vs implementation size — including the datum that cuts the other way

**No smoothing.** There is one passage in Chapter 4 that a proponent of the
line-count reading would reach for, and it is reported first.

**Quoted**, §4.4, ebook p. 36:

> A modern implementation of the Unix I/O interface requires hundreds of thousands of lines of code

That is Ousterhout citing implementation *size* while arguing that Unix I/O is
deep. Taken alone it is compatible with "more implementation lines = deeper".

It does not survive contact with the rest of the book.

**Quoted**, §2.2, ebook p. 20 — he rejects line count as a measure outright:

> System designers sometimes assume that complexity can be measured by lines of code. They assume that if one implementation is shorter than another, then it must be simpler […] However, this view ignores the costs associated with cognitive load. […] Sometimes an approach that requires more lines of code is actually simpler, because it reduces cognitive load.

**Quoted**, §9.8 "Splitting and joining methods", ebook p. 80 — deep is decided
by the interface, with implementation length explicitly neither necessary nor
disqualifying:

> Methods containing hundreds of lines of code are fine if they have a simple signature and are easy to read. These methods are deep (lots of functionality, simple interface), which is good.

**Quoted**, §4.1, ebook p. 32 — the comparison he actually makes is
*interface complexity* vs *implementation complexity*, not lines vs lines:

> The best modules are those whose interfaces are much simpler than their implementations.

and §4.5, ebook p. 36, the shallow case stated the same way:

> The complexity of a linked list interface is nearly as great as the complexity of its implementation.

So: implementation size appears **once, as illustration in one example**;
line count is **explicitly rejected as a metric** in Chapter 2 and again in
Chapter 9. The repo's `depth as ratio of implementation-lines to
interface-lines` has no textual basis. It is not a paraphrase of §4.4 — §4.4
compares *complexities*, and §4.4 is where the term is defined.

## 6.5 Chapter 6 in the 1st edition — the falsifying sentence, checked

§2.1 above leans on a 2nd-ed. sentence at p. 40:
*"What particularly surprised me is that general-purpose interfaces are simpler
and deeper than special-purpose ones, and they result in less code in the
implementation."*

**That exact sentence does not appear in the 1st edition.** Reported plainly,
not smoothed. What the 1st edition has is the same claim split across two
passages of the same chapter — both **Quoted**:

- ebook p. 51 (Ch. 6 intro, §6.1's follow-on):
  > The most important (and perhaps surprising) benefit of the general-purpose approach is that it results in simpler and deeper interfaces than a special-purpose approach.
- ebook p. 53, §6.3:
  > Furthermore, the general-purpose approach has less code overall than the specialized approach, since it replaces a large number of special-purpose methods in the text class with a smaller number of general-purpose ones.

Both halves are present; only the single-sentence 2nd-ed. formulation is not.
**The falsification in §2.1 therefore holds in both editions, and is now stronger
for it:** *deeper* and *less code* are asserted of the same change, three years
apart, in two editions. Under an implementation-lines/interface-lines ratio,
less implementation code makes a module **shallower**.

Also confirmed verbatim in the 1st edition, §6.1, ebook p. 50 — the sentence
§2.1 quotes from the 2nd-ed. extract is word-for-word identical here:

> the module's functionality should reflect your current needs, but its interface should not

## 6.6 Effect on the verdict

**The verdict of §2 is CONFIRMED, and strengthened — not complicated.**

The one open item was "Chapter 4's own text has not been read". It has now been
read, and it says what the lecture notes, the SE Radio interview and the
`aposd-vs-clean-code` README said it would: depth is *functionality behind a
simple interface*, framed as **cost versus benefit**, with **no formula and no
line count**. The §2.4 qualification stands unchanged — the
`functionality/(interface complexity)` ratio appears in his *lecture notes*, and
the book confirms it is a way of thinking, not a metric.

The one genuinely new nuance is §6.4's Unix passage. It is a *rhetorical* use of
implementation size, in the same book that rejects line count as a measure
twice. It does not support the repo's attribution; it explains how someone
reading only that paragraph could arrive at it.
