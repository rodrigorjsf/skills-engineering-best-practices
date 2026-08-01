# CLAUDE.md

Operating rules for this workspace. These are constraints on how work is done here, not a description of what is in it — for that, read [MISSION.md](./MISSION.md) and [docs/curriculum.md](./docs/curriculum.md).

## What this is

A **teaching workspace** (driven by the `teach` skill), not a software project. The subject under study is Matt Pocock's agent-skills repo and the software-engineering literature its skills apply. The deliverable is a course: lessons, reference documents, a research corpus, and runnable code.

The reader is one person — Rodrigo — with four stated goals: apply these practices in his own Java work, teach and evangelise them with real evidence, author his own skills on the same principles, and hold the full intellectual genealogy. Every artifact serves at least one of those. If it serves none, don't build it.

## Documentation

Docs are living, not write-once.

* `README.md` and `docs/curriculum.md` must stay in sync with the repository's actual progress. Update them in the **same commit** that changes behavior, scope, or run steps — never let them drift. `docs/curriculum.md` is the roadmap: when a lesson ships, when a track is re-ordered, or when a lesson's subject changes, it changes there too.
* Counts stated in prose (`N of 20 lessons`, term counts, citation counts) are **derived, not remembered**. Re-measure before writing one down.

### Diagrams

**Use a diagram whenever structure, flow, sequence, or state is easier to see than to read.** This applies to Markdown docs *and* to lesson HTML — a lesson that explains a process in three paragraphs where one diagram would do has chosen the harder medium.

**Mermaid is the default.** Reach for hand-authored inline SVG only when you need precise spatial control that Mermaid cannot express — for example lesson 0001's seam figure, where the *position* of a dashed vertical line between a caller and two adapters is the whole point. When the message is the structure rather than the layout, Mermaid wins: it stays editable, diffs as text, and cannot drift from its own caption.

**Pick the type by the teaching job, not by habit:**

| The thing being taught | Type |
|---|---|
| A process or decision path — red→green, the diagnosis loop, "where does the seam go" | `flowchart` |
| An exchange over time — a grilling session, skill handoffs, caller → module → adapter | `sequenceDiagram` |
| Something with states and legal transitions — triage roles, a `Transfer` lifecycle | `stateDiagram-v2` |
| Structure and relationships — module/interface/adapter, a Java class shape | `classDiagram` |
| Domain entities and cardinality — `Account` 1..N `Transfer` | `erDiagram` |
| Work over branches and time — vertical vs horizontal slicing, tracer bullets | `gitGraph` |
| A taxonomy or decomposition — the glossary map, the four tracks | `mindmap` |
| Intellectual genealogy — Parnas 1972 → Feathers 2005 → Ousterhout 2021 | `timeline` |
| Two-axis positioning — deep/shallow against interface size | `quadrantChart` |
| **Measured data** — the context-rot curve, the lost-in-the-middle U-curve | `xychart-beta` |

That last row matters disproportionately here: this course argues from evidence, and a plotted curve of real published numbers is worth more than a sentence describing it. Plot the data, cite the source in the caption.

**Rules:**

- **A diagram must carry information the prose does not.** If it restates the paragraph above it, delete it — it costs attention and teaches nothing.
- **Every diagram gets a caption** that states what to notice, not what it is. `.figure` + `<figcaption>` in HTML.
- **Colors are the baseline; animation is best-effort.** Use `classDef`/`style`, and `e1@{ animate: true }` on edges where the renderer supports it. Never encode meaning in animation alone.
- **Colors follow the workspace semantics** (see the callout table below) — `seam` teal for structure and interfaces, `evidence` blue for data and sources, `warn` amber for anti-patterns, `ai` purple for the agent parallel. A diagram that invents its own palette breaks the visual language the lessons rely on.
- **Wide diagrams scroll in their own container.** The page body must never scroll horizontally.

**Rendering contract — read before adding the first Mermaid block to a lesson.** Embed as `<pre class="mermaid">…</pre>`. Published Artifacts render that natively. Locally, lessons are opened over `file://`, where nothing renders it without a library — so `assets/mermaid.min.js` must be **vendored** and initialised by `assets/lesson.js`. A CDN link is not an option: the Artifact CSP blocks external hosts, so a CDN would work locally and silently fail once published, which is the worst of both.

> **Current state:** `lesson.js` already initialises Mermaid when `window.mermaid` is present, and falls back to leaving the block as readable indented text when it is not. **`assets/mermaid.min.js` is not vendored yet** — `npm` on this machine is broken (`npm-cli.js` missing), so it has to be fetched another way. Until it lands, a Mermaid block in a lesson renders in Artifacts but shows as plain text locally. Vendor it before leaning on Mermaid for a lesson's central figure.

## Paths — read this before touching a file

| Path                                                         | What it is                                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------------------------ |
| `/home/rodrigo/Workspace/skills-engineering-best-practices/` | Workspace root. Everything we author lives here, and this is its own git repo. |
| `/home/rodrigo/Workspace/skills/` (i.e. `../skills`)         | **The live upstream repo — the only source of truth. Read from this one.**     |
| `../skills/docs/`                                            | **Never write here.** Matt's own published docs pages. Ours go to `./docs/`.   |
| `./docs/research/`                                           | Our research corpus, written by delegated agents.                              |

Three rules that follow:

- **Read source material from `../skills`.** A vendored Windows copy used to sit at `./skills/`; it was deleted on 2026-07-31 after being confirmed identical to upstream (`2ab9580`, no local commits, no stashes, no untracked work). Do not re-create it. If you need a pinned snapshot, check out a tag in `../skills` rather than copying the tree — the copy arrives with corrupted metadata (`AGENTS.md` symlink flattened to a zero-byte file, CRLF in `.sh`, hundreds of `Zone.Identifier` files).
- **`.gitignore` still lists `skills/`** as a backstop, so a re-created copy can never be committed by accident. Leave that entry in place.
- **Lesson and reference citations point at upstream URLs**, never at `/home/rodrigo/...` paths. A local path in a published lesson is a dead link within a month. Repo file references are fine as `path/to/file.md:LINE` *text* alongside the upstream URL, since line numbers are the evidence.

Note that `docs/research/*.md` contains strings like `./skills/engineering/...` **inside quoted material** — those are verbatim excerpts of the upstream repo's own README links. They are quotes, not paths into this workspace. Never rewrite them.

## Language

Set by the reader, 2026-07-31:

- **Lessons (`lessons/*.html`) and reference documents (`reference/*.html`): pt-BR prose.**
- **Technical terms stay English, verbatim, always.** `seam`, not "costura". `deep module`, `shallow module`, `vertical slice`, `tracer bullet`, `ubiquitous language`, `bounded context`, `context rot`, `progressive disclosure`, `harness`, `red-green-refactor`. Wrap them in `<span class="en">` on first use in a paragraph where the reader might reach for a translation. Inventing a Portuguese translation for a term of art is a defect.
- **Everything durable and English by default**: `CLAUDE.md`, `MISSION.md`, `NOTES.md`, `RESOURCES.md`, `docs/**`, learning records, code, code comments, commit messages.
- Quoting an English source inside pt-BR prose: the quote stays English, unchanged. Do not paraphrase it into Portuguese.

## The lesson contract

Every lesson is one self-contained HTML file at `lessons/NNNN-dash-case-name.html`, numbered sequentially from the highest existing.

**Six beats, in this order.** This is how "explain it to a five-year-old, then to a PhD" is satisfied without splitting the audience — both poles in every lesson, every time.

1. **Intuição** — one physical, non-code analogy. Zero jargon.
2. **Mecanismo** — the precise definition, quoted verbatim from the repo or the primary source.
3. **Java** — the same idea in code, in the banking domain, bad-then-good.
4. **Fonte primária** — who originated it, in what work, and what critique exists. Never a bare name-drop.
5. **Paralelo IA** — why the property that helps a human maintainer helps an LLM agent. Cited, or explicitly flagged as our own inference.
6. **Retrieval** — a quiz or free-recall prompt. Effortful retrieval, not re-reading.

**Hard constraints:**

- **One lesson teaches one thing** and is completable quickly. Working memory is the binding constraint. Breadth belongs to the curriculum map and the glossary, never inside a lesson.
- **Every lesson opens with an unannounced spaced-retrieval question about a prior lesson** (from 0002 onward). Storage strength over fluency.
- **Every lesson recommends one primary source** — the single highest-trust thing to read or watch next.
- **Every lesson carries the `.ask-teacher` block** reminding the reader to ask follow-up questions.
- **Every lesson links terms to `reference/0001-glossary.html`** by anchor, and links to adjacent lessons.
- **Build lessons one at a time.** Research broadly, map the whole curriculum, ship one lesson. Scaling up is the reader's call, not a silent decision.

### The running domain is banking

`Account`, `Transfer`, `Balance`, `Ledger`. Chosen for strong invariants — money cannot be created or destroyed — which makes TDD and illegal-state examples land hard. **Every lesson extends this same domain.** Do not invent a fresh domain per lesson; the familiarity is what buys attention for the new concept.

### Quiz rule

All answer options in a quiz must have **the same word count**, ideally the same character count. No option may be distinguishable by shape, length, hedging, or formatting. Java-code options make this easy to violate — count before shipping.

## The glossary is binding

`reference/0001-glossary.html` is the anchor of the whole course. Once a term is defined there:

- No lesson redefines it differently.
- Its `_Avoid_` list (synonyms the repo explicitly rejects) is honoured — don't reach for "component", "service", "API", or "boundary" where the repo says `module`, `interface`, `seam`.
- Build or update it **before** a lesson that introduces new terms, not after.

## Evidence discipline

The reader explicitly asked for *evidências reais*. The bar is high and non-negotiable.

**Four tiers. Label every claim.**

| Tier                  | Meaning                                                                                                  |
| --------------------- | -------------------------------------------------------------------------------------------------------- |
| **Quoted**            | Text was fetched; the quote is verbatim; a URL is given.                                                 |
| **Metadata verified** | Title/author/publisher/year/ISBN from a publisher page, DOI, or the author's own site.                   |
| **Chapter located**   | A real table of contents or searchable excerpt was seen. Chapter numbers without this tag are not given. |
| **`[unverified]`**    | Everything else. Stays visibly unverified.                                                               |

**Three standing rules:**

1. **No guessed locators.** If a table of contents could not be fetched, omit the chapter number rather than invent it.

2. **No smoothing.** An unverified claim is never rewritten into confident prose.

3. **Coinages are labelled as coinages, and attributed to the right person.** Either quote the coiner defining it with a URL, or mark it `[unverified — not found in the author's public sources]`. **A confident fabricated definition of one of Matt Pocock's own terms is the single most visible failure available here — and mis-attributing someone else's term to him is the second.**
   Settled verdicts (from `docs/research/03` and `04`) — do not re-litigate these without new evidence:
   
   | Term                                 | Whose                                                                    | Status                                                                                                                                                                                                                                         |
   | ------------------------------------ | ------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | `dumb zone` / `smart zone`           | **Defined by Pocock** in his *Dictionary of AI Coding*; coinage **open** | His figure is *"around 125K-150K tokens — though this is debated."* The circulating "~100K / 40% of window" figure is **not his**; the "coined by Dex Horthy" attribution **fails** — the phrase is absent from HumanLayer's own `ace-fca.md`. |
   | `harness engineering`                | **Mitchell Hashimoto**, self-identified, 5 Feb 2026                      | **Not Pocock's — do not attribute it to him.** He defines `harness` (which he did not coin either; Anthropic's usage is the vendor standard).                                                                                                  |
   | `grilling` (named practice)          | **Pocock's**, with ancestor acknowledged                                 | He cites *rubber ducking* as the pre-AI ancestor himself.                                                                                                                                                                                      |
   | `AFK` / `HITL`                       | Acronyms **not his**; the workflow classification **is**                 | He explicitly rejects "background agent" as the alternative name.                                                                                                                                                                              |
   | `wayfinder`, `deepening opportunity` | Likely his                                                               | Still to be confirmed against a definition he wrote.                                                                                                                                                                                           |

Use the `<span class="flag cited|coinage|unverified|ours">` marks in HTML so the tier is visible to the reader, not just to us.

**Report empirical evidence honestly, including negative results.** The TDD literature contains replications that found no effect. Those get reported. A course that oversells its own thesis fails the "teach it with evidence" goal.

**Separate three things and never blur them:** what a source says · what practitioners say · what *we* are inferring by analogy.

## Java code policy

- **Examples are real programs that compile and run.** They live in `code/NNNN-slug/` and are runnable with `java File.java` (Java 25, Corretto, via mise).
- **Output quoted in a lesson is captured output**, produced by actually running the program in the session that wrote the lesson. Never hand-written to look like output.
- Prefer the JDK's own seams where they exist — `java.time.Clock` was added for exactly this reason and is stronger evidence than an invented interface.
- Bad-then-good pairs must differ in **one** thing. If the "good" version also renames variables, reformats, and adds a helper, the lesson has taught nothing about the concept.

## Asset reuse

Reuse is the default. Read `assets/` before authoring anything.

- `assets/style.css` — the shared design system. Every lesson and reference doc links it. Tufte-flavoured; light/dark via `prefers-color-scheme` **and** `[data-theme]`; prints clean.
- `assets/lesson.js` — quiz behaviour, theme toggle, progress bar. Loaded with `defer`.

**Never inline CSS or JS a second document would duplicate.** New reusable behaviour becomes a component in `assets/` and gets linked.

### Markup contracts

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

Callouts — **the colour is the meaning**. Keep these consistent across every lesson or the visual language stops teaching:

| Class               | Meaning                           |
| ------------------- | --------------------------------- |
| `.callout.child`    | The five-year-old pass            |
| `.callout.phd`      | The primary-source / deep pass    |
| `.callout.seam`     | Structure, interfaces, seams      |
| `.callout.evidence` | Data, citations, measured results |
| `.callout.trap`     | Anti-patterns                     |
| `.callout.ai`       | The agent/LLM parallel            |

Also available: `details.recall` (free-recall before reveal), `.code-block.good` / `.code-block.bad` with `.code-label`, `.split` (side-by-side), `.figure` + `.caption`, `.table-wrap` (all wide content scrolls in its own container — the page body must never scroll horizontally).

Mermaid — always inside a `.figure`, always with a caption that says what to notice:

```html
<figure class="figure">
<pre class="mermaid">
flowchart LR
  A[caller] --> B{seam}
  B --> C[production adapter]
  B --> D[test adapter]
</pre>
<figcaption><b>What to notice.</b> …</figcaption>
</figure>
```

`lesson.js` initialises Mermaid when `window.mermaid` exists, re-renders on theme toggle (Mermaid bakes colours into the SVG, so a CSS variable swap alone would leave the diagram in the old theme), and otherwise adds `.mermaid-unrendered` so the source stays readable instead of showing an empty frame.

**Sidenotes — the one contract that breaks the layout if you get it wrong.** A `.sidenote` is a **sibling** of the prose blocks, a direct child of `.page`:

```html
<p>…prose…</p>
<p class="sidenote">…the note…</p>
```

Never nest it inside another `<p>`, and never give it a negative margin. It floats against `.page`'s right edge, and `--page` is defined as `--measure + --gutter + 3rem` so the 3rem channel between text and note is already accounted for. A negative margin pushes the note *outside* `.page` — which both opens a large dead gap and visually de-centres the whole page, since `.page` is what `margin: 0 auto` centres. Under 62rem viewport it collapses inline automatically.

**Width discipline.** Everything aligns to `--measure` by default — prose, figures, tables, splits, nav. `.wide` is an explicit opt-in for content that genuinely must overflow to `--page`. Prefer sizing an SVG's `viewBox` to the measure over reaching for `.wide`; a diagram that ends on a different line than the paragraph above it reads as a layout bug.

**Never hard-code a font size in px.** The root scales with viewport (16 → 17 → 18 → 19px at 90/105/125rem), and every size in the stylesheet is in `rem` so text and column grow together. That is what keeps the measure at ~70 characters on a large display instead of stretching the line.

Pages have no `<!DOCTYPE>`/`<html>`/`<head>`/`<body>` wrapper when published as Artifacts, but files in `lessons/` and `reference/` are opened locally via `file://` — so a bare `<title>`, `<link>`, `<script>` at the top followed by content works in both.

## Delegation

The reader asked for investigative work to be delegated. Standing policy:

- **Broad research goes to subagents**, run in parallel in a single message, `run_in_background: true`.
- Each research agent **writes a persisted file to `docs/research/`** and returns a **short report** (under ~700 words) — never a file dump into the main context.
- Agent prompts must carry the non-negotiable rules explicitly: primary sources only, `[unverified]` for anything unconfirmed, never fabricate a quote/date/URL/timestamp, capture numbers.
- The main thread does orientation and authoring. It does not re-run a search it delegated.

## File conventions

| Location            | Convention                                                                                                                                                                  |
| ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `lessons/`          | `NNNN-dash-case-name.html`, sequential                                                                                                                                      |
| `reference/`        | `NNNN-dash-case-name.html`, sequential                                                                                                                                      |
| `learning-records/` | `NNNN-dash-case-name.md` — written only on *evidence* of understanding, disclosed prior knowledge, a corrected misconception, or a mission shift. Coverage is not learning. |
| `docs/research/`    | `NN-topic.md`, English, cited                                                                                                                                               |
| `code/`             | `NNNN-slug/` matching the lesson number                                                                                                                                     |

`NOTES.md` holds stated preferences and working notes. `MISSION.md` is the compass — if it grows past a screen it has become a plan and needs cutting. Update it (with a learning record) when the goal actually moves; confirm with the reader first.

## Applied Learning

When something fails repeatedly, when User has to re-explain, or when a workaround is found for a platform/tool limitation, add a one-line bullet here. Keep each bullet under 15 words. No explanations. Only add things that will save time in future sessions.

- Read the upstream repo at `../skills`; never vendor a copy into this workspace.
- Never write research into `../skills/docs/` — that's the author's published pages.
- Copying a repo from Windows corrupts symlinks and line endings; clone instead.
- Java 25 (Corretto, via mise) is on PATH; run examples with `java File.java`, no compile step.
