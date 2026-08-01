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

## Palette — use these `classDef`s, don't invent new ones

The page has a light and a dark theme and `lesson.js` re-renders on toggle, so a
diagram cannot know which theme it is in. **Always set `fill` AND `color`
together** — a node that inherits either one from the Mermaid theme flips to
invisible on the other side of the toggle. That is exactly how the first draft
of lesson 0001's pipeline lost its `link seam` label.

```
classDef stage    fill:#f2efe8,stroke:#57534a,stroke-width:1.5px,color:#26241f
classDef seam     fill:#1f6f6b,stroke:#0f4d4a,stroke-width:2px,color:#ffffff
classDef evidence fill:#1e4f7a,stroke:#12314b,stroke-width:2px,color:#ffffff
classDef trap     fill:#8a5a12,stroke:#5c3c0c,stroke-width:2px,color:#ffffff
classDef ai       fill:#5b3a8c,stroke:#3b255c,stroke-width:2px,color:#ffffff
```

**`**bold**` inside a label ignores the `classDef` colour.** A markdown-string
label renders its bold run as a nested `<tspan>` that takes the *Mermaid theme's*
text colour, not the `color:` you set. On a `stage` node — pale fill, dark
`color:` — the bold run turns light-on-pale and **disappears in dark theme while
looking fine in light**. Lesson 0003's category numbers were invisible exactly
this way. Either drop the bold, or only bold inside nodes whose fill is dark
enough for the theme's light text. Checking one theme is not checking it.

`stage` is the neutral, unemphasised step. The other four carry the same meaning
as the matching `.callout` class. **The emphasised node is the solid one** — a
pale tint with dark text reads as disabled next to the neutral boxes, which is
the opposite of the intent.

Style the edges too, or the dashed "this is where the seam is" line renders in
the theme's default grey and the emphasis stops at the box:
`linkStyle 4,5,6 stroke:#1f6f6b,stroke-width:2px` (indices count every edge in
declaration order, from 0).

## Orientation and size

`flowchart TB` by default. `LR` renders wide and short, and the SVG is then
scaled down to the column — which shrinks the labels until they are unreadable.
`TB` grows into the vertical space, which is free.

`lesson.js` initialises Mermaid with `fontSize: 17` and generous
`nodeSpacing`/`rankSpacing` so the diagram's *natural* size is large enough that
`useMaxWidth` scales it up, not down. A figure that still feels cramped gets
`class="figure wide"` — the explicit opt-in to `--page` width.

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

**The backticks go inside the quotes, wrapping the whole label — not at the
line break.** `A["` + backtick + `line one⏎line two` + backtick + `"]` renders two
lines; putting a backtick at the end of line one and the start of line two
renders the backticks **literally**, as visible characters in the node. Lesson
0006's first draft did exactly that and shipped `TransferService\`` on screen.

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
