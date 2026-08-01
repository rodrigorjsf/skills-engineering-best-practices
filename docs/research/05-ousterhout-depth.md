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
| APOSD Chapter 4 full text | — | **not obtained** |
