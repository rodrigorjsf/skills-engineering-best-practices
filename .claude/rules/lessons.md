---
paths:
  - "lessons/**/*.html"
  - "reference/**/*.html"
  - "checkpoints/**/*.html"
  - "index.html"
  - "assets/**"
---

# Authoring lessons, reference documents, and the shared assets

Root `CLAUDE.md` carries the six beats and the hard constraints — they must be
known before a lesson is composed, not after. This file is the detail.

**Checkpoints are not lessons.** A `checkpoints/NNNN-*.html` closes a track and
measures it: no six beats, no opening spaced-retrieval question, no primary-source
recommendation, no Java section. Everything else here binds — quiz word-count
uniformity, the sidenote contract, no hard-coded `px`, `.wide` as explicit opt-in,
`.ask-teacher`, `.nav`, and **the glossary**: a checkpoint links its terms by
anchor exactly as a lesson does. Two rules are specific to them:

- **Free recall comes before multiple choice.** Reading the options replaces
  production with recognition, and only production measures storage strength.
- **Every distractor is a real misreading a lesson was written to close** —
  depth-as-ratio, DI = DIP, the two-adapter rule as sufficient, "small adapter"
  measured in lines, the deletion test as a binary verdict. That is what makes the
  chosen option a diagnosis instead of a score, and it is why the return channel
  records `chosen`, not just `correct`.

## The running domain is banking

`Account`, `Transfer`, `Balance`, `Ledger`. Chosen for strong invariants — money
cannot be created or destroyed — which makes TDD and illegal-state examples land
hard. **Every lesson extends this same domain.** Do not invent a fresh domain per
lesson; the familiarity is what buys attention for the new concept.

## The glossary is binding

`reference/0001-glossary.html` is the anchor of the whole course. Once a term is
defined there:

- No lesson redefines it differently.
- Its `_Avoid_` list (synonyms the repo explicitly rejects) is honoured — don't
  reach for "component", "service", "API", or "boundary" where the repo says
  `module`, `interface`, `seam`.
- Build or update it **before** a lesson that introduces new terms, not after.

## Quiz rule

All answer options in a quiz must have **the same word count**, ideally the same
character count. No option may be distinguishable by shape, length, hedging, or
formatting. Java-code options make this easy to violate — count before shipping.

## Asset reuse

Reuse is the default. Read `assets/` before authoring anything.

- `assets/style.css` — the shared design system. Every lesson and reference doc
  links it. Tufte-flavoured; light/dark via `prefers-color-scheme` **and**
  `[data-theme]`; prints clean.
- `assets/lesson.js` — quiz behaviour, theme toggle, progress bar, Mermaid
  initialisation. Loaded with `defer`.
- `assets/mermaid.min.js` — vendored renderer, loaded with `defer` *before*
  `lesson.js`.

**Never inline CSS or JS a second document would duplicate.** New reusable
behaviour becomes a component in `assets/` and gets linked.

## Markup contracts

Quiz — `data-answer` is the zero-based index of the correct option:

```html
<div class="quiz" data-answer="1">
  <div class="quiz-head">Retrieval</div>
  <p class="quiz-q">…</p>
  <ul class="quiz-options">
    <li><button class="quiz-opt">…</button></li>
  </ul>
  <div class="quiz-feedback"><p>…</p></div>
</div>
```

Callouts — **the colour is the meaning**. Keep these consistent across every
lesson or the visual language stops teaching:

| Class               | Meaning                           |
| ------------------- | --------------------------------- |
| `.callout.child`    | The five-year-old pass            |
| `.callout.phd`      | The primary-source / deep pass    |
| `.callout.seam`     | Structure, interfaces, seams      |
| `.callout.evidence` | Data, citations, measured results |
| `.callout.trap`     | Anti-patterns                     |
| `.callout.ai`       | The agent/LLM parallel            |

Also available: `details.recall` (free-recall before reveal), `.code-block.good` /
`.code-block.bad` with `.code-label`, `.split` (side-by-side), `.figure` +
`.caption`, `.table-wrap` (all wide content scrolls in its own container — the
page body must never scroll horizontally).

Evidence tiers are visible to the reader through
`<span class="flag cited|coinage|unverified|ours">`.

**Sidenotes — the one contract that breaks the layout if you get it wrong.** A
`.sidenote` is a **sibling** of the prose blocks, a direct child of `.page`:

```html
<p>…prose…</p>
<p class="sidenote">…the note…</p>
```

Never nest it inside another `<p>`, and never give it a negative margin. It
floats against `.page`'s right edge, and `--page` is defined as
`--measure + --gutter + 3rem` so the 3rem channel between text and note is
already accounted for. A negative margin pushes the note *outside* `.page` —
which both opens a large dead gap and visually de-centres the whole page, since
`.page` is what `margin: 0 auto` centres. Under 62rem viewport it collapses
inline automatically.

**Width discipline.** Everything aligns to `--measure` by default — prose,
figures, tables, splits, nav. `.wide` is an explicit opt-in for content that
genuinely must overflow to `--page`. Prefer sizing an SVG's `viewBox` to the
measure over reaching for `.wide`; a diagram that ends on a different line than
the paragraph above it reads as a layout bug.

**Never hard-code a font size in px.** The root scales with viewport
(16 → 17 → 18 → 19px at 90/105/125rem), and every size in the stylesheet is in
`rem` so text and column grow together. That is what keeps the measure at ~70
characters on a large display instead of stretching the line.

Pages have no `<!DOCTYPE>`/`<html>`/`<head>`/`<body>` wrapper when published as
Artifacts, but files in `lessons/` and `reference/` are opened locally via
`file://` — so a bare `<title>`, `<link>`, `<script>` at the top followed by
content works in both.
