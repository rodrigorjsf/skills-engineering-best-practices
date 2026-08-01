---
paths:
  - "lessons/**/*.html"
  - "reference/**/*.html"
  - "docs/**/*.md"
  - "*.md"
---

# Diagrams

**Use a diagram whenever structure, flow, sequence, or state is easier to see
than to read.** This applies to Markdown docs *and* to lesson HTML — a lesson
that explains a process in three paragraphs where one diagram would do has
chosen the harder medium.

**Mermaid is the default.** Reach for hand-authored inline SVG only when you
need precise spatial control that Mermaid cannot express — for example lesson
0001's seam figure, where the *position* of a dashed vertical line between a
caller and two adapters is the whole point. When the message is the structure
rather than the layout, Mermaid wins: it stays editable, diffs as text, and
cannot drift from its own caption.

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

That last row matters disproportionately here: this course argues from evidence,
and a plotted curve of real published numbers is worth more than a sentence
describing it. Plot the data, cite the source in the caption.

**Rules:**

- **A diagram must carry information the prose does not.** If it restates the
  paragraph above it, delete it — it costs attention and teaches nothing.
- **Every diagram gets a caption** that states what to notice, not what it is.
  `.figure` + `<figcaption>` in HTML.
- **Colors are the baseline; animation is best-effort.** Use `classDef`/`style`,
  and `e1@{ animate: true }` on edges where the renderer supports it. Never
  encode meaning in animation alone.
- **Colors follow the workspace semantics** — `seam` teal for structure and
  interfaces, `evidence` blue for data and sources, `warn` amber for
  anti-patterns, `ai` purple for the agent parallel (see the callout table in
  `.claude/rules/lessons.md`). A diagram that invents its own palette breaks the
  visual language the lessons rely on.
- **Wide diagrams scroll in their own container.** The page body must never
  scroll horizontally.

## Rendering contract

Embed as `<pre class="mermaid">…</pre>`, always inside a `.figure`, always with a
caption that says what to notice:

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

**Never put an HTML tag inside the block — `<br>` included.** The browser parses
it as a real element while building `<pre>`, so `textContent` drops it and the
two lines arrive concatenated with no space (`código-fonteidêntico`). For a line
break use a Mermaid markdown string: a backtick-quoted label with a literal
newline, `A["` + backtick + `first line⏎second line` + backtick + `"]`. Markdown
strings also give `**bold**` inside labels, which HTML labels would not survive
under `securityLevel: "strict"`.

Published Artifacts render that natively. Locally, lessons are opened over
`file://`, where nothing renders it without a library — so `assets/mermaid.min.js`
is **vendored** (v11.16.0, MIT, a classic IIFE script, not ESM: Chrome blocks ES
modules over `file://`) and initialised by `assets/lesson.js`. A CDN link is not
an option: the Artifact CSP blocks external hosts, so a CDN would work locally
and silently fail once published, which is the worst of both.

`lesson.js` initialises Mermaid when `window.mermaid` exists, re-renders on theme
toggle (Mermaid bakes colours into the SVG, so a CSS variable swap alone would
leave the diagram in the old theme), and otherwise adds `.mermaid-unrendered` so
the source stays readable instead of showing an empty frame.

`npm` on this machine is broken (`npm-cli.js` missing) — re-vendoring the bundle
needs another route.
