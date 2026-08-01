# Engineering discipline as agent architecture

A course-in-progress studying [Matt Pocock's agent-skills repository](https://github.com/mattpocock/skills) and the software-engineering literature its skills apply — then tracing the correspondence to how AI agents actually behave.

**The thesis:** the same properties that make code tractable for a human maintainer make a codebase and a workflow tractable for an LLM. A small interface is a small context. A seam is a place to cut a task. A glossary is a shared prompt.

This repository exists to test that claim against primary sources rather than assert it.

> **Language note.** Lessons and reference documents are written in **Brazilian Portuguese**, with every technical term kept in English verbatim (`seam`, `deep module`, `vertical slice`, `tracer bullet`, `context rot`). Research, code, and operating documents are in English. Code examples are **Java**.

---

## Mission

Four goals drive every artifact here. If something serves none of them, it does not get built.

1. **Apply** these practices in day-to-day Java work.
2. **Teach and evangelise** them — which requires evidence that survives a hostile question, not slogans.
3. **Author skills** on the same principles.
4. **Hold the full intellectual genealogy** — Parnas, Feathers, Evans, Beck, Fowler, Nygard — and how it reconnects to agent architecture.

Full version: [`MISSION.md`](./MISSION.md).

## Evidence discipline

This is the part worth stealing even if you never read a lesson.

Every claim carries one of four tiers, and the tier is **visible to the reader**, not just to the author:

| Tier | Meaning |
|---|---|
| **Quoted** | Text was fetched; the quote is verbatim; a source is given |
| **Metadata verified** | Title/author/publisher/year/ISBN from a publisher page, DOI, or the author's own site |
| **Chapter located** | A real table of contents or excerpt was seen |
| **`[unverified]`** | Everything else — and it stays visibly unverified |

Three standing rules:

- **No guessed locators.** If a table of contents could not be fetched, the chapter number is omitted rather than invented.
- **No smoothing.** An unverified claim is never rewritten into confident prose.
- **Coinages are labelled as coinages,** and attributed to whoever actually coined them.

A fourth marker, `[our analogy]`, separates what a source says from what we are inferring by analogy. The research corpus currently carries **212 explicit `[unverified]` marks** across fifteen files — that count is a feature. It is the difference between a document that knows what it doesn't know and one that reads confidently everywhere.

### What that discipline has already caught

- The widely-circulated **"dumb zone begins at ~100K tokens / ~40% of the context window"** figure appears in no primary source. Matt Pocock's own written definition says *"around 125K-150K tokens — though this is debated"* and explicitly states the zones do **not** track a percentage of the window.
- **"Dumb zone was coined by Dex Horthy"** does not hold up — the phrase does not appear in HumanLayer's own document. What *is* his: the 40–60% utilization target and *intentional compaction*.
- **"Harness engineering" has no located coiner** — and this one caught *us*. It was recorded as *"traced: Hashimoto self-identifies as coining it"*, on a quote truncated one sentence early. The next sentence is *"I don't need to invent any new terms here; if another one exists, I'll jump on the bandwagon."* The compound is already in a talk published two months before his post, and two write-ups published two days apart name two different coiners. **Not Pocock's** was never what failed.
- The favourable **TDD** case study (Nagappan et al., "40–90% fewer defects") is four case studies, not experiments; its "15–35% slower" figure is explicitly *"subjectively estimated by management."* Fucci et al. (TSE 2017) found that **sequencing — test-first versus test-after — had no important influence**; granularity and uniformity carried the effect.
- **"Information hiding" is not coined in Parnas' famous 1972 paper.** He writes *The second decomposition was made using "information hiding" [4] as a criteria* — the quotation marks and the citation are his. Reference [4] is his own earlier CMU report, *"Information Distribution Aspects of Design Methodology"* (1971). The criterion is stated in the famous paper; the term arrives from a companion report.
- **The free "Fowler smells chapter" everyone links is not the 1st edition.** The PDF at `laputan.org` was downloaded and read: it carries **17 smells, not 22**, has no *Speculative Generality*, names the refactorings **Extract Component / Inline Component** (the published names are *Extract Class* / *Inline Class*, which appear zero times), and calls one smell *Case Statements*. It is a **pre-publication draft**. Our own corpus described it as the 1st-edition chapter, and that has been corrected in place.
- **"Depth as a ratio of implementation-lines to interface-lines (Ousterhout)"** — the rejection is sound, the attribution is not. Chapter 4 of *A Philosophy of Software Design* was read in full: the word `ratio` occurs **zero times in all 188 pages**, Figure 4.1 carries no numbers, axes or equation, and he rejects line count as a measure twice. The ratio he *does* write, in his lecture notes, is `functionality/(interface complexity)` — neither term a line count.

## What's inside

| Path | What it is |
|---|---|
| [`MISSION.md`](./MISSION.md) | Why this exists and what counts as success. Every lesson anchors here. |
| [`CLAUDE.md`](./CLAUDE.md) | Operating rules — the lesson contract, evidence tiers, markup contracts, delegation policy. |
| [`RESOURCES.md`](./RESOURCES.md) | Curated, verified sources with annotations, plus an explicit `Gaps` section. |
| [`docs/curriculum.md`](./docs/curriculum.md) | The full map: 4 tracks, 22 lessons, in order. |
| `lessons/` | Self-contained HTML lessons. **6 of 22 built.** |
| `checkpoints/` | One per closed track. Measures what landed; teaches nothing. **1 built** (Track I). |
| `reference/` | Compressed documents built to be revisited and printed. **3 built** — the glossary is binding, plus the stateful flow and the Java patterns. |
| `docs/research/` | The research corpus — 15 files, ~11,400 lines, cited. |
| `code/` | Java examples that compile and run. |
| `learning-records/` | What was actually learned, and what it changes. |

### Research corpus

Produced by delegated agents, every claim cited:

- **`01-skills-corpus.md`** — exhaustive harvest of the skills repo: **152 term entries, 667 `file:line` citations**, the stateful flow graph, a per-skill dossier, and a verbatim quote bank.
- **`02-se-literature.md`** — the software-engineering literature map, with the empirical record reported honestly including null and negative results.
- **`03-agent-engineering.md`** — context rot, lost-in-the-middle, progressive disclosure, harness, tooling — with the measured numbers.
- **`04-matt-pocock-sources.md`** — the author's own primary sources and a coinage ledger.
- **`05-ousterhout-depth.md`** — settles whether Ousterhout defines depth as a ratio, from the book itself. He does not, and his own text falsifies the line-count reading.
- **`06-walking-skeleton-and-harness-post.md`** — Cockburn's archived primary text, separated from `tracer bullet` and `vertical slice` in each author's own words.
- **`07-communities.md`** — four verified venues for testing this work outside the learning environment.
- **`08-ddd-evans.md`** — Evans on ubiquitous language and bounded context, chapters confirmed, including one hypothesis the agent disproved and reported.
- **`09-supplementary-sources.md`** — provenance checks on two supplementary sources; one was promoted, one demoted to a finding aid.
- **`10-dependency-inversion.md`** — Martin's article recovered from a pinned Wayback capture and read in full; DI ≠ DIP settled by grep over Fowler's own pages.
- **`11-yagni-dry-and-the-cost-of-abstraction.md`** — the exemption Fowler writes for himself, and the signed retraction in the DRY authors' 20th-anniversary text.
- **`12-indirection-and-agents.md`** — what is actually measured about fault localization, and sixteen numbered claims no source supports.
- **`13-library-sweep.md`** — all 61 URLs the finding-aid library references, tiered; then the gap-touching ones fetched. Resolves the origin of the "40%" figure and demotes the `harness engineering` coinage to no located coiner.
- **`14-martin-books-and-the-volatility-gate.md`** — Martin's own books read from the reader's copies. Closes who coined `SOLID` and whether he qualifies DIP, and dates the volatility gate to 2003 rather than 2018.
- **`15-shelf-provenance.md`** — every book on the shelf triaged before use. Two method corrections, one unauthorised retype refused, and what each book actually unlocks, by lesson.

### Curriculum

Four tracks, built one lesson at a time.

- **I — Structure**: seam · deep vs shallow module · interface beyond the signature · adapters and internal seams · the deletion test
- **II — Process**: dependency inversion · YAGNI, DRY and the wrong abstraction · vertical slices and tracer bullets · red-green · pre-agreed seams · test anti-patterns · diagnosis as a loop
- **III — Language**: ubiquitous language · bounded context · ADRs · grilling to shared understanding
- **IV — The AI parallel**: context rot · progressive disclosure · durable state over long context · harness engineering · the stateful flow end to end · writing your own skill

Every lesson follows the same six beats, which is how "explain it to a five-year-old, then to a PhD" is satisfied without splitting the audience: **Intuição → Mecanismo → Java → Fonte primária → Paralelo IA → Retrieval**.

## Using it

Lessons are plain, self-contained HTML — no build step, no dependencies.

```bash
git clone https://github.com/rodrigorjsf/skills-engineering-best-practices
cd skills-engineering-best-practices
xdg-open index.html          # or just open lessons/0001-seam.html
```

Java examples run without a compile step (Java 25, single-file source mode):

```bash
cd code/0001-seam && java SeamDemo.java
cd code/0002-deep-module && java DepthDemo.java
cd code/0003-interface && java InterfaceDemo.java
cd code/0004-adapter && java AdapterDemo.java
cd code/0005-deletion-test && java DeletionTestDemo.java
cd code/0006-dependency-inversion && java InversionDemo.java
```

## Attribution and licensing

The **MIT license applies to the material authored here** — lessons, reference documents, research write-ups, Java examples, stylesheets, and operating documents.

It does **not** extend to quoted material, which remains the property of its authors and is reproduced in short, attributed excerpts for study and commentary:

- [**mattpocock/skills**](https://github.com/mattpocock/skills) and [**mattpocock/dictionary-of-ai-coding**](https://github.com/mattpocock/dictionary-of-ai-coding) — Matt Pocock. Published docs at [aihero.dev](https://www.aihero.dev/skills). This repository studies that work; it is not affiliated with or endorsed by its author.
- Books and papers by Michael Feathers, David Parnas, John Ousterhout, Eric Evans, Kent Beck, Martin Fowler, Michael Nygard and others, cited with page numbers where verified.
- Engineering publications by Anthropic, Chroma Research, and other primary sources, cited with URLs.

No copyrighted book, paper, or vendored repository is redistributed here — see [`.gitignore`](./.gitignore). Full attribution and scope details: [`NOTICE.md`](./NOTICE.md).

## Status

Early and deliberately so. The research corpus is broad; the lessons are built **one at a time**, because working memory is the binding constraint and breadth belongs to the map rather than inside a lesson.

Open gaps are tracked honestly in [`RESOURCES.md`](./RESOURCES.md#gaps) rather than quietly omitted.

## License

[MIT](./LICENSE.md) © Rodrigo Jorge de Santana França
