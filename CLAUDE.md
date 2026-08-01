# CLAUDE.md

Operating rules for this workspace. These are constraints on how work is done here, not a description of what is in it — for that, read [MISSION.md](./MISSION.md) and [docs/curriculum.md](./docs/curriculum.md).

## What this is

A **teaching workspace** (driven by the `teach` skill), not a software project. The subject under study is Matt Pocock's agent-skills repo and the software-engineering literature its skills apply. The deliverable is a course: lessons, reference documents, a research corpus, and runnable code.

The reader is one person — Rodrigo — with four stated goals: apply these practices in his own Java work, teach and evangelise them with real evidence, author his own skills on the same principles, and hold the full intellectual genealogy. Every artifact serves at least one of those. If it serves none, don't build it.

## Paths — read this before touching a file

| Path                                                         | What it is                                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------------------------ |
| `/home/rodrigo/Workspace/skills-engineering-best-practices/` | Workspace root. Everything we author lives here, and this is its own git repo. |
| `/home/rodrigo/Workspace/skills/` (i.e. `../skills`)         | **The live upstream repo — the only source of truth. Read from this one.**     |
| `../skills/docs/`                                            | **Never write here.** Matt's own published docs pages. Ours go to `./docs/`.   |

- **Read source material from `../skills`.** A vendored Windows copy used to sit at `./skills/`; it was deleted on 2026-07-31 after being confirmed identical to upstream (`2ab9580`). Do not re-create it — a Windows copy arrives with corrupted metadata (`AGENTS.md` symlink flattened, CRLF in `.sh`, hundreds of `Zone.Identifier` files). For a pinned snapshot, check out a tag in `../skills`. `.gitignore` lists `skills/` as a backstop; leave that entry in place.
- **Lesson and reference citations point at upstream URLs**, never at `/home/rodrigo/...` paths. A local path in a published lesson is a dead link within a month. Repo file references are fine as `path/to/file.md:LINE` *text* alongside the upstream URL, since line numbers are the evidence.
- **`docs/research/*.md` contains `./skills/...` strings inside quoted material** — verbatim excerpts of the upstream README's own links. They are quotes, not paths into this workspace. Never rewrite them; filter `docs/research/` out of any repo-wide path rewrite.

## Delegation

Broad research goes to subagents, run in parallel in a single message, `run_in_background: true`. Each writes a persisted file to `docs/research/` and returns a **short report** (under ~700 words) — never a file dump into the main context. The main thread does orientation and authoring; it does not re-run a search it delegated. Prompt requirements are in `.claude/rules/research.md`.

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
- **The running domain is banking** — `Account`, `Transfer`, `Balance`, `Ledger` — in every lesson, without exception.
- **Build lessons one at a time.** Research broadly, map the whole curriculum, ship one lesson. Scaling up is the reader's call, not a silent decision.

Detail lives in `.claude/rules/` and attaches on file match: `lessons.md` (banking domain, glossary binding, quiz rule, markup contracts, sidenote and width discipline), `diagrams.md` (Mermaid type picker and rendering contract), `research.md` (evidence tiers, delegation), `java.md`.

## Language

Set by the reader, 2026-07-31:

- **Lessons (`lessons/*.html`) and reference documents (`reference/*.html`): pt-BR prose.**
- **Technical terms stay English, verbatim, always.** `seam`, not "costura". `deep module`, `vertical slice`, `tracer bullet`, `ubiquitous language`, `bounded context`, `context rot`, `progressive disclosure`, `harness`, `red-green-refactor`. Wrap them in `<span class="en">` on first use in a paragraph where the reader might reach for a translation. Inventing a Portuguese translation for a term of art is a defect.
- **Everything durable is English by default**: `CLAUDE.md`, `MISSION.md`, `NOTES.md`, `RESOURCES.md`, `docs/**`, learning records, code, code comments, commit messages.
- Quoting an English source inside pt-BR prose: the quote stays English, unchanged. Do not paraphrase it into Portuguese.

## Coinages — the highest-severity failure available here

**A confident fabricated definition of one of Matt Pocock's own terms is the single most visible failure available here — and mis-attributing someone else's term to him is the second.** Either quote the coiner defining it with a URL, or mark it `[unverified — not found in the author's public sources]`.

Settled verdicts (from `docs/research/03-agent-engineering.md` and `04-matt-pocock-sources.md`) — do not re-litigate these without new evidence:

| Term                                 | Whose                                                                    | Status                                                                                                                                                                                                                                         |
| ------------------------------------ | ------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `dumb zone` / `smart zone`           | **Defined by Pocock** in his *Dictionary of AI Coding*; coinage **open** | His figure is *"around 125K-150K tokens — though this is debated."* The circulating "~100K / 40% of window" figure is **not his**; the "coined by Dex Horthy" attribution **fails** — the phrase is absent from HumanLayer's own `ace-fca.md`. |
| `harness engineering`                | **No located coiner** — same shape as `vertical slice`                   | **Not Pocock's — do not attribute it to him.** He defines `harness` (which he did not coin either; Anthropic's usage is the vendor standard). **Hashimoto is not the coiner either:** *"I don't need to invent any new terms here"* — he adopts the term, and the compound is already in Horthy's talk published 2 Dec 2025. Two March-2026 write-ups name two different coiners; the Trivedy one fails (his post coins *Harness as a Service*). **Do not assign it a coiner.** |
| `grilling` (named practice)          | **Pocock's**, with ancestor acknowledged                                 | He cites *rubber ducking* as the pre-AI ancestor himself.                                                                                                                                                                                      |
| `AFK` / `HITL`                       | Acronyms **not his**; the workflow classification **is**                 | He explicitly rejects "background agent" as the alternative name.                                                                                                                                                                              |
| `wayfinder`, `deepening opportunity` | Likely his                                                               | Still to be confirmed against a definition he wrote.                                                                                                                                                                                           |

The four-tier evidence bar that produced these verdicts is in `.claude/rules/research.md`, and applies to lessons too.

## Documentation

Docs are living, not write-once.

- `README.md` and `docs/curriculum.md` must stay in sync with the repository's actual progress. Update them in the **same commit** that changes behavior, scope, or run steps — never let them drift. `docs/curriculum.md` is the roadmap: when a lesson ships, when a track is re-ordered, or when a lesson's subject changes, it changes there too.
- Counts stated in prose (`N of 20 lessons`, term counts, citation counts) are **derived, not remembered**. Re-measure before writing one down.

## File conventions

| Location            | Convention                                                                                                                                                                  |
| ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `lessons/`          | `NNNN-dash-case-name.html`, sequential                                                                                                                                      |
| `reference/`        | `NNNN-dash-case-name.html`, sequential                                                                                                                                      |
| `checkpoints/`      | `NNNN-track-name.html`, own numbering — **one per closed track.** Not a lesson: it measures, it does not teach. Contract in `.claude/rules/lessons.md`.                    |
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
- `npm` here is broken (`npm-cli.js` missing); fetch bundles another way.
- Screenshot lessons with `google-chrome --headless=new`; stub `matchMedia` to force dark.
- PIL is not installed; crop and slice PNGs with PyMuPDF (`fitz`).
- Keep captured program output under ~70 columns or it overflows the lesson measure.
- Force dark for a screenshot by stubbing `matchMedia`; `data-theme` alone is overwritten.
- A `.figure.wide` collides with a sidenote beside it; move the sidenote up a paragraph.
- `--virtual-time-budget` freezes CSS transitions mid-flight; set `data-theme` pre-paint.
- Scanned PDF: `tesseract` locates the page, then read the 300dpi image to quote.
- A book's CIP block predates its copyright line by a year; cite the copyright line.
