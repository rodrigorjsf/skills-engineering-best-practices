# Four attribution corrections — and the rule they produced

Track I is built (lessons 0001–0005). This record exists because the evidence bar
set in [[0001-mission-and-teaching-frame-established]] stopped being a policy and
started producing results — including one correction against this workspace's own
research corpus.

**This is not a record of the reader's understanding.** No lesson has been
reported back yet. It records corrected misconceptions in the *course material*,
which the `learning-records/` convention lists as a trigger in its own right.

## The four

| Lesson | Claim in circulation | What the primary text says |
|---|---|---|
| 0002 | "Depth as a ratio of implementation-lines to interface-lines **(Ousterhout)**" | `ratio` occurs **zero times** in all 188 pages of the 1st ed. Figure 4.1 has no numbers, axes or equation. He rejects line count twice. The rejected framing is a real folk simplification — **the parenthetical attribution is what fails.** |
| 0003 | "Parnas coined *information hiding* in the 1972 CACM paper" | He writes it **in quotation marks with citation [4]** — his own earlier CMU report, *"Information Distribution Aspects of Design Methodology"* (1971). The criterion is in the famous paper; the term arrives from a companion report. |
| 0004 | *(our own corpus)* the two-adapter rule "would forbid many of the ports a strict hexagonal design would create" | Cockburn's own document expects **several adapters per port** and names the mock database as one. His ports pass the rule rather than failing it. **Withdrawn.** |
| 0005 | *(our own corpus)* the laputan.org PDF is "a free copy of the 1st-edition chapter" | It is a **pre-publication draft**: 16 smells not 22 (this line first said *17*; re-counted 2026-08-01 from the PDF's own section headings), no *Speculative Generality*, "Extract Component"/"Inline Component" where the book says *Extract Class*/*Inline Class*, "Case Statements" for *Switch Statements*. **Corrected in place.** |

## The rule that produced them

All four came from the same move, and it is cheap: **download the artifact and
read it, rather than citing the corpus entry that cites it.** Every one of the
four had already passed through a research pass that marked it verified.

Two of them are corrections to *us*. That is the part worth keeping: a corpus
written under an evidence bar still accumulates inference that reads like
citation once it is a few weeks old. The bar catches other people's errors on the
first pass and its own on the second.

## What changed as a result

- **Locators now name the artifact, not the work.** Parnas quotes are labelled
  *1971 CMU report, p. 17* — never CACM 1972 page numbers, because the CACM text
  was never fetched. Ousterhout is *1st ed. ebook p. N*, because ebook pages are
  not print pages. Cockburn gets a section name and no page, because the document
  is unpaginated. Fowler is *pre-publication draft, p. 92*.
- **A retraction is a first-class entry in `docs/research/`,** written where the
  wrong claim was, not appended at the end.
- **The counter-datum goes first.** Every lesson in Track I opens its
  primary-source beat with the evidence *against* its own framing, in full, then
  answers it. In 0003 that is Parnas defining interface as what you *choose* to
  reveal — which is not the repo's *everything a caller must know*. In 0005 it is
  Ousterhout's Figure 7.1 giving three remedies, only one of which is deletion.

## Open, and deliberately so

`vertical slice` still has **no located coiner** and must not be assigned one in
lesson 0008 (renumbered from 0006 on 2026-08-01; see record 0003). See `docs/research/06-walking-skeleton-and-harness-post.md`.
