# Indirection, Abstraction and Dependency Inversion — What They Cost an LLM Coding Agent

Research note for the lesson on the **Dependency Inversion Principle**, with **YAGNI**
and **DRY** as companions. Supplies the *Paralelo IA* beat's raw material — and, more
importantly, the explicit list of what that beat is **not allowed to claim**.

All URLs fetched **2026-08-01** unless stated otherwise.

## Evidence markers used here

| Marker | Meaning |
| --- | --- |
| `[QUOTED]` | The page/paper was fetched in this session; the quote is verbatim; the URL is given. |
| `[METADATA VERIFIED]` | Title/authors/date taken from the publisher's or arXiv's own landing page. |
| `[unverified]` | Everything else. Stays visibly marked. |
| `[OUR ANALOGY]` | We are inferring it. No source states it. |

## ⚠️ Read this before writing a single line of the lesson

**The tempting thesis — "over-abstraction / excessive indirection makes LLM coding
agents worse" — has no primary source. Nothing found in this session measures
indirection depth as an independent variable against agent success.** It ships as
`[OUR ANALOGY]` or it does not ship. See §7 for the full prohibition list.

What *is* documented, and what this note is built on:

1. Context is a finite budget and every token spent is billed (already in `03-agent-engineering.md` §3).
2. **Localization — finding the right file — is a measured, separable failure mode
   with published rates.** (§3 here. This is new.)
3. **Giving an agent a symbol/structure index measurably improves both localization
   and resolve rate.** (§3 here. This is new, and it is the closest real evidence to
   the tempting thesis — but it varies the *retrieval tool*, not the *codebase*.)
4. The always-loaded bucket and the on-demand bucket have different cost curves, and
   Anthropic's own docs say so. (§4 here. Extends `03` §13.5 / line 840.)

## Relationship to `docs/research/03-agent-engineering.md`

`03` already covers, at high confidence and with verification notes — **do not
re-derive or re-fetch these**:

| Topic | Where in `03` |
| --- | --- |
| Context rot (Chroma, 14 Jul 2025), incl. the honest limitation that the curves are charts | §1 |
| "Building effective agents" (Schluntz & Zhang, 19 Dec 2024) — workflows vs agents, orchestrator-workers, the ACI framing | §3, §8.1 — **deliberately not re-fetched here** |
| Lost in the Middle (Liu et al., TACL 12, 2024, arXiv `2307.03172`), the 75.8/53.8/63.2 table, the 56.1% closed-book result | §2 |
| "Attention budget"; *"every new token introduced depletes this budget"* | §3 |
| Progressive disclosure; the three-level Skills loading table (~100 tok / <5k tok / 0) | §4, §13.1 |
| "Writing effective tools for agents"; the deep-module mapping and its caveat | §8 |
| Multi-agent: 90.2%, ~15× tokens, "spawning 50 subagents for simple queries" | §9 |
| Code execution with MCP: **150,000 → 2,000 tokens, 98.7%** | §8.3, §13.10 |
| CLAUDE.md / memory tiers; *"Bloated CLAUDE.md files cause Claude to ignore your actual instructions!"* | §13.5, §13.8, and the inverted-cost-model row at line 840 |
| Context editing + memory tool: 39% / 29% / 84% | §13.9 |

**This file adds only what is not there.**

---

## 1. Context as a budget — the additions

`03` §3 has the framing. The material below is **new**, from a Claude Code doc page
`03` did not read: **"Manage costs effectively"**,
<https://code.claude.com/docs/en/costs.md> (raw Markdown; living doc, no publication
date). All quotes `[QUOTED]`.

**Every turn re-sends everything:**

> "Long context: Claude Code sends your full conversation with every request, and each
> time Claude uses tools it sends another request carrying that batch of tool results.
> With prompt caching, Claude Code re-reads that history at the cached token rate, so a
> one-line question in a session that has been open all day still draws usage for the
> whole conversation."

**Compaction is itself expensive:**

> "Compaction: `/compact` reads the conversation it summarizes, so compacting a large
> context is itself a large request. When you want a fresh start instead of continuity,
> `/clear` costs nothing"

**Hooks and skills as context filters (the "push the filtering down" pattern):**

> "Instead of Claude reading a 10,000-line log file to find errors, a hook can grep for
> `ERROR` and return only matching lines, reducing context from tens of thousands of
> tokens to hundreds."

**Multiplied cost of parallelism** (a *newer* number than `03` §9's ~15×, and about a
different product — Claude Code agent teams, not the research system):

> "Agent teams use approximately 7x more tokens than standard sessions when teammates
> run in plan mode, because each teammate maintains its own context window and runs as
> a separate Claude instance."

> "Keep spawn prompts focused. Teammates load CLAUDE.md, MCP servers, and skills
> automatically, but everything in the spawn prompt adds to their context from the
> start."

---

## 2. Does anything measure *more files* vs *fewer, larger files*?

### ❌ Negative result — state it plainly

**No source found in this session measures agent performance as a function of file
count, module count, call-chain hop count, or indirection depth in a codebase the
agent must *read*.** Every measurement available is of **token volume** or of
**retrieval quality**, not of structure.

Concretely:

- Chroma's cleanest structural result is **focused (~300 tokens) vs full (~113k
  tokens)** input for the same question — a *volume* manipulation (`03` §1).
- Anthropic's **150,000 → 2,000** is *tool definitions* loaded eagerly vs lazily — again
  volume, not structure (`03` §13.10).
- Liu et al. vary the *position* of the relevant document, not the *number of hops* to
  reach it (`03` §2).

**Say it in exactly these terms in the lesson: "no published source measures hops or
file count; every available measurement is of token volume or retrieval quality."**

### Searches that returned nothing usable (recorded so a future session does not redo them)

| Query | Outcome |
| --- | --- |
| `measured effect of number of files vs file size on LLM coding agent performance codebase structure` | Returned AGENTS.md studies (§4) and generation studies (§2.1) — nothing on reading structure |
| `SWE-bench agent failure analysis localization failure rate arXiv paper` | Productive, but for §3 — localization, not indirection |
| `Cursor codebase indexing embeddings how it works documentation` | Product/engineering blog only (§3.4) |
| `Sourcegraph code intelligence AI agent codebase context search blog` (domain-limited to sourcegraph.com) | **Marketing-grade only.** No measured numbers, no eval, no methodology. Closest to a claim: *"Agentic coding's productivity ceiling is set by context, not model quality"* — an unsupported vendor assertion. `[unverified]` **Do not cite Sourcegraph in the lesson.** |
| `GitHub Copilot research codebase structure agent navigation success rate measurement` (domain-limited to github.blog/githubnext) | **Productive, but for tool-surface size, not codebase structure** — see §3.5. Nothing found on codebase structure affecting navigation. |

### 2.1 The nearest adjacent finding — and why it does NOT transfer

`[METADATA VERIFIED]` + `[QUOTED abstract]` Francesco Dente, Dario Satriani, Paolo
Papotti, **"Constraint Decay: The Fragility of LLM Agents in Backend Code
Generation"**, arXiv `2605.06445`, submitted **7 May 2026**.
<https://arxiv.org/abs/2605.06445>

> "Our findings reveal a phenomenon of constraint decay: as structural requirements
> accumulate, agent performance exhibits a substantial decline. Capable configurations
> lose 30 points on average in assertion pass rates from baseline to fully specified
> tasks, while some weaker configurations approach zero. Framework sensitivity analysis
> exposes significant performance disparities: agents succeed in minimal, explicit
> frameworks (e.g., Flask) but perform substantially worse on average in
> convention-heavy environments (e.g., FastAPI, Django)."

**⚠️ Scope this precisely or it becomes a retraction.** This measures agents
**GENERATING** multi-file backend code under architectural constraints across 80
greenfield + 20 feature tasks. It does **not** measure an agent *reading* an existing
indirection-heavy codebase. "Explicit framework beats convention-heavy framework" is
tantalisingly close to "less magic is easier for an agent", but the experiment is on
the write side. **Usable in a lesson only with that scope stated in the same
sentence.**

---

## 3. Localization — the closest thing to real evidence

This is the section the lesson should lean on, because it is measured.

### 3.1 Half the agent's budget goes to *finding* the code

`[METADATA VERIFIED]` + `[QUOTED]` Hovhannes Tamoyan, Sean Narenthiran, Erik
Arakelyan, Mira Mezini, Boris Ginsburg, **"SHERLOC: Structured Diagnostic Localization
for Code Repair Agents"**, arXiv `2606.24820`, submitted **23 June 2026**.
<https://arxiv.org/abs/2606.24820>

Opening sentence of the abstract — **the single best line in this note**:

> "LLM agents solve repository-level coding tasks through multi-turn tool use, but
> utilize half their budget on locating faults before editing."

And from the same abstract:

> "SHERLOC reaches state-of-the-art localization across model scales: 84.33% accuracy@1
> on SWE-Bench Lite and 81.27% recall@1 on SWE-Bench Verified; at ~30B parameters, it
> matches or outperforms other agentic methods. Injecting our locations and diagnostic
> findings into repair agents yields, on average, +5.95 pp resolve rate on SWE-Bench
> Verified while cutting localization and total tokens by 36.7% and 23.1%."

**The failure taxonomy** `[QUOTED from the HTML full text]`,
<https://arxiv.org/html/2606.24820v1>. Analysis of **55 zero-recall instances** on
SWE-Bench Verified with Qwen3-235B-A22B-Thinking:

(Row labels below are the paper's category names; the italic glosses are **ours**.)

| Failure mode | n | share |
| --- | --- | --- |
| Reasoning error — *saw the correct file, selected a different one* | 22 | 40% |
| Close miss — *correct directory, wrong file* | 15 | 27% |
| Wrong module entirely | 14 | 25% |
| Multi-file bug (3+ ground-truth files) | 2 | 4% |
| Insufficient exploration | 1 | 2% |
| Ambiguous problem description | 1 | 2% |

The two sentences, verbatim (Appendix C), **both confirmed character-for-character by a
second, differently-worded fetch**:

> "The dominant failure mode is _reasoning error_ (40%): the model explored the correct
> area, often viewing the ground-truth file, but ultimately selected a different file in
> its final answer."

> "Combined with close misses (27%, correct directory but wrong file), 67% of failures
> stem from picking the wrong file among nearby candidates rather than from failing to
> reach the right area."

**What this licenses and what it does not.** It licenses: *choosing among
near-identical neighbouring files is where agents fail, not reaching the neighbourhood.*
It does **not** license: *interfaces with one implementor cause this.* **The paper never
varies codebase structure**, and the inference from "many similar candidates" to "your
abstraction layers created them" is not one the paper supports. See §7 for the only
form in which this may appear in a lesson.

### 3.2 Localization is a separable phase with its own accuracy number

`[METADATA VERIFIED]` + `[QUOTED]` Chunqiu Steven Xia, Yinlin Deng, Soren Dunn,
Lingming Zhang, **"Agentless: Demystifying LLM-based Software Engineering Agents"**,
arXiv `2407.01489`. <https://arxiv.org/abs/2407.01489>

From the abstract: *"Agentless employs a simplistic three-phase process of
localization, repair, and patch validation"*, achieving *"the highest performance
(32.00%, 96 correct fixes) and low cost ($0.70)"*.

From the full text (<https://ar5iv.labs.arxiv.org/html/2407.01489>):

> "Agentless addresses this by using a simple three-step hierarchical localization
> process: 1) localize to selected files; 2) localize each selected files into relevant
> classes, functions, and variables; 3) localize to code edit locations."

> "The difficulty lies in the fact that there could be hundreds of files with thousands
> of lines of code each in a repository, whereas the correct locations to edit are only
> a few selected lines or functions."

> "Due to the large possible action space and feedback response, it can be extremely
> easy for autonomous agents to become confused and perform sub-optimal explorations."

> "We observe that Agentless is able to localize the ground truth file in 77.7% of
> cases"

`[unverified]` The per-level accuracy table (file → class/function → edit location) was
reported in the fetched text only for the file level (77.7%). **Do not quote a
class-level or line-level figure without re-reading the paper's Table.**

### 3.3 The controlled ablation — a structural index changes both localization and resolve

`[METADATA VERIFIED]` + `[QUOTED abstract]` Ishaan Bhola, Adithyan Krishnan, Sravanth
Kurmala, Mukunda NS, **"Code Isn't Memory: A Structural Codebase Index Inside a Coding
Agent"**, arXiv `2606.22417`, submitted **21 June 2026**.
<https://arxiv.org/abs/2606.22417>

> "Inside a fixed coding-agent harness on a fixed model, does adding a structural
> codebase index actually change cost or resolve? We ran three arms (the harness with
> the index, the same harness without it, and an agentic-grep comparator) on
> SWE-PolyBench Verified and SWE-bench Pro with Claude Opus 4.7 held fixed throughout,
> across three seeds, inside a leak-audited per-task sandbox. The within-harness
> ablation produces a large localization gain and a statistically separated resolve
> gain, with no cost penalty per cell and lower cost per solve."

Numbers `[QUOTED from <https://arxiv.org/html/2606.22417v1>]` — **91 instances (34 Go,
20 Java, 37 Python), three seeds, Claude Opus 4.7 fixed**:

| Metric | Index ON | Index OFF | Delta |
| --- | --- | --- | --- |
| Localization acc@5 | 84.5 | 44.3 | **+39.6 pp** (paired Wilcoxon p<0.0001) |
| Resolve rate (legit cells) | 50.4% | 41.9% | **+7.9 pp** (p=0.003) |
| $ per solved | $2.30 | (OpenCode agentic-grep: $2.92) | ~21% cheaper |

**This is the strongest controlled evidence in the note — and it must be read
precisely.** It varies the *retrieval mechanism available to the agent*, holding model,
harness and codebase fixed. It says: *an agent without a structural index localizes
badly (44.3 acc@5) and an agent with one localizes well (84.5).* It says **nothing**
about whether a more indirected codebase is harder. `[OUR ANALOGY]`: if navigation
without an index is worth ~40 points of localization accuracy, then structure the agent
must *infer* rather than *look up* is expensive — but that inference is ours.

### 3.4 The tools that exist *because* the model has no symbol index

These are existence arguments, not measurements. Treat them as such.

**(a) Anthropic's own docs describe the go-to-definition problem verbatim.**
`[QUOTED]` <https://code.claude.com/docs/en/costs.md>:

> "Code intelligence plugins give Claude precise symbol navigation instead of
> text-based search, reducing unnecessary file reads when exploring unfamiliar code. A
> single 'go to definition' call replaces what might otherwise be a grep followed by
> reading multiple candidate files. Installed language servers also report type errors
> automatically after edits, so Claude catches mistakes without running a compiler."

**This is the assignment's item 3 stated by Anthropic itself.** It is qualitative — no
number attached — but it is the vendor documenting that grep-plus-read-candidates is
the default and that symbol navigation is the fix.

**(b) Aider's repo map.** `[QUOTED]` <https://aider.chat/docs/repomap.html>:

> "The LLM can see classes, methods and function signatures from everywhere in the
> repo."

> "If it needs to see more code, the LLM can use the map to figure out which files it
> needs to look at."

> "It does this by analyzing the full repo map using a graph ranking algorithm,
> computed on a graph where each source file is a node and edges connect files which
> have dependencies."

> "The token budget is influenced by the `--map-tokens` switch, which defaults to 1k
> tokens."

And from the design post, **"Building a better repository map with tree sitter"**,
**22 October 2023**, <https://aider.chat/2023/10/22/repomap.html> `[QUOTED]`:

> "A simple solution is to send the entire codebase to GPT along with each change
> request...But this won't work for even moderately sized repos, because they won't fit
> into the context window."

> "GPT-4 is actually great at making the code changes (3), once you tell it which files
> need to be changed (1) and show it how they fit into the rest of the codebase (2)."

> "Aider optimizes the repo map by selecting the most important parts of the codebase
> which will fit into the token budget assigned by the user."

**Read it correctly:** the repo map is evidence that *navigation without an index costs
tokens*, and that the fix is a **1k-token compressed signature index** rather than
source. It is **not** evidence that indirection is costly.

**(c) Cursor on semantic search.** `[QUOTED]` Stefan Heule, Emily Jia, Naman Jain,
**"Improving agent with semantic search"**, Cursor blog, **6 November 2025**.
<https://cursor.com/blog/semsearch>

> "While you could rely exclusively on grep and similar command-line tools for search,
> we've found that semantic search significantly improves agent performance, especially
> over large codebases."

> "Achieving on average 12.5% higher accuracy in answering questions (6.5%–23.5%
> depending on the model)"

> "Agent code retention increases by 0.3% when semantic search is available. This effect
> increases to 2.6% on large codebases with 1,000 files or more"

> "2.2% increase in dissatisfied follow-up user requests when semantic search was not
> available"

**Tier honestly: this is a vendor engineering post about its own paid feature.**
`[PRACTITIONER / vendor]`. The 0.3% → 2.6% scaling with codebase size is the most
interesting datum — retrieval quality matters more as the repo grows — and it is the
only number found anywhere that indexes on **codebase size**. Do not present it as
independent research.

`[unverified]` Cursor's index architecture (a vector index of chunk embeddings + a
graph index of definitions and call edges + a lexical BM25 index) was reported by a
search summary, **not read from a Cursor page in this session**. Do not quote it.

**(d) Sourcegraph — a deliberate negative.** A domain-limited search of sourcegraph.com
returned **only marketing-grade material**: no eval, no methodology, no measured
numbers. Its sharpest line, *"Agentic coding's productivity ceiling is set by context,
not model quality"*, is an unsupported vendor assertion. **Do not cite Sourcegraph in
the lesson**, even though it is the company whose whole product is the missing symbol
index.

### 3.5 The one vendor number on *interface size* — GitHub, not codebases

`[METADATA VERIFIED]` + `[QUOTED]` Anisha Agarwal, Connor Peet, **"How we're making
GitHub Copilot smarter with fewer tools"**, The GitHub Blog, **19 November 2025**.
<https://github.blog/ai-and-ml/github-copilot/how-were-making-github-copilot-smarter-with-fewer-tools/>

They *"trimmed the default 40 built-in tools down to 13 core ones"*, and report that
*"these changes improve success rates by 2-5 percentage points"* across benchmarks
including *"SWE-Lancer and SWEbench-Verified with both GPT-5 and Sonnet 4.5"*. Their
metric: *"Tool Use Coverage, which measures how often the model already has the right
tool visible when it needs it."*

**Scope it.** This is **fewer, broader tools beating more, narrower tools** — the
deep-vs-shallow-module axis (`03` §8), independently reproduced by a second vendor with
a number. It is **not** about codebase structure. It belongs in the lesson as
*narrowing the interface surface helps the agent*, which is DIP-adjacent but is really
the deep-module argument again. `[PRACTITIONER / vendor]`.

---

## 4. The inverted cost model — extending `03` line 840

The claim `03` records is: *an always-loaded line is billed every turn, unlike code an
agent reads on demand.* Here is the documented inventory that supports it.

### 4.1 The always-loaded bucket

| What | Evidence |
| --- | --- |
| **CLAUDE.md** — every session | `[QUOTED]` costs.md: *"Your CLAUDE.md file is loaded into context at session start."* And `03` §13.8: *"CLAUDE.md is loaded every session, so only include things that apply broadly."* |
| **`@`-imported files** — expanded at launch, max 4 hops | `03` §13.5 `[QUOTED]`: *"Imported files are expanded and loaded into context at launch alongside the CLAUDE.md that references them."* |
| **Skill metadata** — every installed skill, at startup | `03` §13.1 `[QUOTED]`: *"~100 tokens per Skill"*; *"Claude loads this metadata at startup and includes it in the system prompt."* |
| **Tool definitions** | `03` §13.10 `[QUOTED]`: *"In cases where agents are connected to thousands of tools, they'll need to process hundreds of thousands of tokens before reading a request."* |
| **The whole conversation so far** | `[QUOTED]` costs.md: *"Claude Code sends your full conversation with every request"* |

### 4.2 The on-demand bucket

| What | Evidence |
| --- | --- |
| **Skill bodies** (<5k tok when triggered) and bundled resources (**0 until read**) | `03` §13.1 three-level table |
| **Source files** — only what the agent chooses to read | Implicit throughout; no page states it as a rule |
| **MCP tool definitions — now deferred by default** | `[QUOTED]` costs.md: *"MCP tool definitions are deferred by default, so only tool names enter context until Claude uses a specific tool."* **This is new since `03` was written and is worth flagging** — the 150k-token scenario is now the un-deferred worst case, not the default. |
| **Script output** (the script's own code never enters context) | `03` §13.1 |

### 4.3 The two guidance lines that make the YAGNI point

`[QUOTED]` <https://code.claude.com/docs/en/costs.md> — **the strongest single passage
for the lesson's YAGNI beat**:

> "Your CLAUDE.md file is loaded into context at session start. If it contains detailed
> instructions for specific workflows (like PR reviews or database migrations), those
> tokens are present even when you're doing unrelated work. Skills load on-demand only
> when invoked, so moving specialized instructions into skills keeps your base context
> smaller. Aim to keep CLAUDE.md under 200 lines by including only essentials."

`[QUOTED]` <https://code.claude.com/docs/en/best-practices> (via `03` §13.8):

> "Keep it concise. For each line, ask: 'Would removing this cause Claude to make
> mistakes?' If not, cut it. Bloated CLAUDE.md files cause Claude to ignore your actual
> instructions!"

**`200 lines` is a hard, quotable number and it is new relative to `03`.**

### 4.4 The AGENTS.md study — speculative context *measurably* costs money without buying success

`[METADATA VERIFIED]` Thibaud Gloaguen, Niels Mündler, Mark Müller, Veselin Raychev,
Martin Vechev, **"Evaluating AGENTS.md: Are Repository-Level Context Files Helpful for
Coding Agents?"**, arXiv `2602.11988`, submitted **12 February 2026**, revised **23 June
2026**. <https://arxiv.org/abs/2602.11988>

`[QUOTED from the arXiv abstract page]`:

> "providing context files does not generally improve task success rates, while
> increasing inference cost by over 20% on average"

Also reported on that page: repository *overviews* — "widely recommended by model
providers" — did not improve performance, whereas *specific coding instructions* were
followed correctly. Models named in the PDF: Claude Sonnet 4.5, GPT-5 / Codex, Qwen 3
Coder; agents: SWE-agent, OpenHands, Cursor, Aider, Claude Code.

`[unverified]` The finer numbers circulating for this paper (developer-written files
+2.4% and +3.34 steps; LLM-generated −0.5% on SWE-bench and −2% on CTXBENCH) came from a
**search-result summary, not from the paper's own text** — the PDF's result tables could
not be extracted in this session. **Do not quote them.** The `>20% cost increase`
sentence is safe.

**This is the best empirical anchor for YAGNI in the agent setting**: speculative,
always-loaded documentation has a measured price and an unmeasurable benefit. It is
about `AGENTS.md`, not about code abstractions — say so.

---

## 5. Progressive disclosure — nothing new needed

`03` §4 and §13.1 already carry the strongest instance (the three-level Skills loading
table with `~100 tokens` / `<5k tokens` / `none until accessed`) from Anthropic's own
docs, plus the 150k→2k figure. **Cross-reference, do not re-fetch.**

The one addition, `[QUOTED]` costs.md, is that Anthropic now frames a skill explicitly
as *an abstraction that replaces exploration*:

> "A skill can give Claude domain knowledge so it doesn't have to explore. For example,
> a 'codebase-overview' skill could describe your project's architecture, key
> directories, and naming conventions. When Claude invokes the skill, it gets this
> context immediately instead of spending tokens reading multiple files to understand
> the structure."

⚠️ Note the tension with §4.4: Anthropic recommends exactly the artifact
(`codebase-overview`) that the AGENTS.md study found does **not** improve success rates
when it is a repository overview. **Present both; the disagreement is real and the
lesson is better for naming it.**

---

## 6. Sub-agents as indirection — the cost of the handoff itself

`03` §9 has the headline numbers. The assignment asks specifically about **the cost of
the handoff**: a sub-agent boundary, like an interface, requires the caller to specify a
contract and requires results to be summarised back.

**Documented, `[QUOTED]`** — Claude Code sub-agents docs (via `03` §13.2),
<https://code.claude.com/docs/en/sub-agents>:

> "Each subagent starts with a fresh, isolated context window. It doesn't see your
> conversation history, the skills you've already invoked, or the files Claude has
> already read. **Claude composes a delegation message that summarizes the task**, and
> the subagent works from there."

> "Use one when a side task would flood your main conversation with search results,
> logs, or file contents you won't reference again: the subagent does that work in its
> own context and returns only the summary."

**The lossiness of the return leg**, `[PRACTITIONER — Pocock, pinned SHA, via `03` §9]`:

> "The report is a secondary source: the parent gets the subagent's account of what it
> found, not the raw results, so anything the report leaves out is invisible to the
> parent."

**The re-loading cost of the boundary**, `[QUOTED]` costs.md:

> "Teammates load CLAUDE.md, MCP servers, and skills automatically, but everything in
> the spawn prompt adds to their context from the start."

**The failure mode when the contract is under-specified**, `[QUOTED]` via `03` §9,
Anthropic multi-agent post: sub-agents duplicated work from *"vague task
descriptions"*.

**Assessment.** Four documented facts — (1) the delegation message must summarise the
task, (2) the return is a summary and therefore lossy, (3) the child re-pays for
CLAUDE.md/MCP/skills on spawn, (4) vague contracts cause duplicated work — jointly
describe a real handoff cost. **No source calls it "the cost of the boundary" or draws
the interface analogy.** The framing *"a sub-agent boundary is an interface: you pay to
specify it and you pay to summarise across it"* is `[OUR ANALOGY]` built on four
verified components.

---

## 7. The mapping for the DIP / YAGNI / DRY lesson

| Principle | The agent parallel | Status | Anchor |
| --- | --- | --- | --- |
| **DIP** — depend on abstractions, not concretions | For a human with an IDE, "go to implementation" is one keystroke; for an agent it is grep-plus-read-candidates | `[OUR ANALOGY]` for the *cost*; the *mechanism* is `[QUOTED]` | costs.md code-intelligence paragraph (§3.4a) |
| **DIP** — an interface with exactly one implementor | Adds a near-identical candidate to the set the agent must choose among — the 67% failure shape. *Ours, in full:* a `PaymentService` / `PaymentServiceImpl` / `AbstractPaymentService` cluster presents the same shape SHERLOC measured (many similar candidates, one right answer). **No paper connects the two.** | `[OUR ANALOGY]` — the bridge and the Java example are ours; only the 67% is `[QUOTED]` | SHERLOC §3.1 |
| **DIP / deep modules** — narrow the interface surface | 40 tools → 13 tools bought **+2–5 pp** success on SWE-Lancer / SWEbench-Verified | `[QUOTED]`, vendor-reported; about *tools*, not code | GitHub §3.5 |
| **DIP** — navigation without an index | Structural index worth **+39.6 pp** localization acc@5, **+7.9 pp** resolve | `[QUOTED]`, but it varies the *tool*, not the *codebase* | Code Isn't Memory §3.3 |
| **YAGNI** — don't build for imagined futures | Speculative always-loaded context costs **>20% more inference** and does not raise success rates | `[QUOTED]` (about AGENTS.md files, not code) | §4.4 |
| **YAGNI** — cost curve depends on the bucket | Always-loaded is billed every turn; on-demand is billed once, if ever | `[OUR ANALOGY]` as a *curve*; both buckets are `[QUOTED]` | §4.1/§4.2 |
| **DRY** — a single source of truth | **No primary source found** for a DRY-specific agent effect | `[unverified] / no source` | — |
| **DRY** — the inverted case | An always-included header costs each caller a compile; an always-loaded CLAUDE.md line costs each *turn* a re-send | `[OUR ANALOGY]`; the re-send is `[QUOTED]` | costs.md "sends your full conversation with every request" |

---

## What is quotable in a lesson

The strongest verbatim lines, with URLs. **Items 1, 2, 4, 5 and 10 were each re-fetched
a second time with a "return the sentence character for character, do not paraphrase"
prompt and came back identical.** They are safe to place inside a `<blockquote>` in a
published lesson. Items 3, 7 and 9 come from raw Markdown with no summariser in the
chain. Items 6 and 8 are single-fetch — see the verification table.

1. **The half-the-budget line** — the best single sentence in the note.
   > "LLM agents solve repository-level coding tasks through multi-turn tool use, but
   > utilize half their budget on locating faults before editing."
   — SHERLOC, arXiv `2606.24820` abstract. <https://arxiv.org/abs/2606.24820>

2. **The wrong-neighbour result.**
   > "67% of failures stem from picking the wrong file among nearby candidates rather
   > than from failing to reach the right area."
   — SHERLOC, <https://arxiv.org/html/2606.24820v1> (55 zero-recall instances,
   SWE-Bench Verified, Qwen3-235B-A22B-Thinking).

3. **Go-to-definition, from Anthropic's own docs.**
   > "A single 'go to definition' call replaces what might otherwise be a grep followed
   > by reading multiple candidate files."
   — <https://code.claude.com/docs/en/costs.md>

4. **The haystack sentence.**
   > "The difficulty lies in the fact that there could be hundreds of files with
   > thousands of lines of code each in a repository, whereas the correct locations to
   > edit are only a few selected lines or functions."
   — Agentless, arXiv `2407.01489`. <https://ar5iv.labs.arxiv.org/html/2407.01489>

5. **The exploration-confusion sentence.**
   > "Due to the large possible action space and feedback response, it can be extremely
   > easy for autonomous agents to become confused and perform sub-optimal
   > explorations."
   — Agentless, ibid.

6. **The ablation numbers (for a chart).** Localization acc@5 **84.5 with index vs 44.3
   without**; resolve **50.4% vs 41.9%**; **$2.30 vs $2.92 per solve**; 91 instances,
   3 seeds, Claude Opus 4.7 fixed. — "Code Isn't Memory", arXiv `2606.22417`.
   <https://arxiv.org/abs/2606.22417>

7. **The YAGNI line.**
   > "If it contains detailed instructions for specific workflows (like PR reviews or
   > database migrations), those tokens are present even when you're doing unrelated
   > work. […] Aim to keep CLAUDE.md under 200 lines by including only essentials."
   — <https://code.claude.com/docs/en/costs.md>

8. **The speculative-context price.**
   > "providing context files does not generally improve task success rates, while
   > increasing inference cost by over 20% on average"
   — Gloaguen et al., arXiv `2602.11988`. <https://arxiv.org/abs/2602.11988>

9. **The always-resent context.**
   > "Claude Code sends your full conversation with every request"
   — <https://code.claude.com/docs/en/costs.md>

10. **Why the repo map exists.**
    > "GPT-4 is actually great at making the code changes (3), once you tell it which
    > files need to be changed (1) and show it how they fit into the rest of the
    > codebase (2)."
    — Aider, 22 Oct 2023. <https://aider.chat/2023/10/22/repomap.html>
    Plus the **1k-token default `--map-tokens` budget**,
    <https://aider.chat/docs/repomap.html>

---

## What we may NOT claim

Each of these is tempting, plausible, and **unsupported**. If the lesson needs one, it
must carry `[nossa inferência]` in the visible text — not a footnote.

1. **"Excessive indirection / over-abstraction makes coding agents worse."**
   No source. Nothing found varies abstraction depth as an independent variable.
   *This is the assignment's named trap. Do not let §3 promote it.*

2. **"An interface with a single implementor costs an agent measurably more than a
   concrete class."** No source. Not measured by anyone found.

3. **"Agents perform better on fewer, larger files."** No source. Everything measured
   is token volume or retrieval quality. See §2.

4. **"Each layer of indirection costs N tokens / N tool calls."** No source publishes a
   per-hop cost. Do not invent an arithmetic.

5. **"SHERLOC / Agentless / SWE-bench show that abstraction hurts agents."** They show
   localization is hard and separable. They never manipulate abstraction. Conflating the
   two is the retraction-shaped move.

6. **"Aider's repo map exists because indirection is expensive."** It exists because
   repos do not fit in a context window and models have no symbol index. Different
   claim.

7. **"Cursor's numbers are independent research."** They are a vendor engineering post
   about a paid feature. Cite as `[PRACTITIONER / vendor]` or not at all.

8. **"Cursor's index is a vector + graph + BM25 hybrid."** `[unverified]` — from a
   search summary, not read from a Cursor page in this session.

9. **"DRY violations cost an agent extra context."** **No primary source found at all
   for a DRY-specific agent effect.** Say so plainly rather than stretching the
   CLAUDE.md-bloat quote to cover it.

10. **"Constraint decay proves agents struggle with abstract codebases."** It measures
    *generation* under structural constraints, not *reading*. The Flask-vs-Django
    finding is real but must be scoped in the same sentence.

11. **"Agentless localizes classes/functions at X%."** Only the **file-level 77.7%**
    was read in this session. Any deeper level is `[unverified]`.

12. **"Developer-written AGENTS.md files improve success by 2.4%."** `[unverified]` —
    from a search summary, not the paper. Only the *">20% cost, no general success
    improvement"* sentence is safe.

13. **"Anthropic says abstraction is bad for agents."** It does not. `03` §8.2 records
    the verified negative: `deep`, `shallow`, `module`, `information hiding`,
    `abstraction`, `encapsulat`, `Ousterhout`, `Parnas` are **all absent** from "Writing
    effective tools for agents". And "Writing effective tools" explicitly *distances*
    tool design from ordinary API design.

14. **"A sub-agent boundary is an interface with a specification cost."**
    `[OUR ANALOGY]`. The four component facts are verified (§6); the framing is ours.

15. **"Sourcegraph's research shows context beats model quality."** There is no
    research. A domain-limited search of sourcegraph.com found marketing copy only.
    **Do not cite Sourcegraph.**

16. **"GitHub proved that fewer abstractions help agents."** GitHub measured **fewer
    tools** (40 → 13, +2–5 pp) — a *tool-surface* result, not a *codebase-structure*
    result. It supports the deep-module argument, not the indirection one.

---

## Sources fetched in this session

**Peer-reviewed / preprint**

1. Tamoyan, H., Narenthiran, S., Arakelyan, E., Mezini, M., Ginsburg, B. (23 Jun 2026).
   "SHERLOC: Structured Diagnostic Localization for Code Repair Agents." arXiv
   `2606.24820`. <https://arxiv.org/abs/2606.24820> · full text
   <https://arxiv.org/html/2606.24820v1>
2. Xia, C. S., Deng, Y., Dunn, S., Zhang, L. (Jul 2024). "Agentless: Demystifying
   LLM-based Software Engineering Agents." arXiv `2407.01489`.
   <https://arxiv.org/abs/2407.01489> · full text
   <https://ar5iv.labs.arxiv.org/html/2407.01489>
   `[unverified]` Exact submission day and any venue were not read off the abs page;
   the `2407` prefix fixes the month as July 2024.
3. Bhola, I., Krishnan, A., Kurmala, S., NS, M. (21 Jun 2026). "Code Isn't Memory: A
   Structural Codebase Index Inside a Coding Agent." arXiv `2606.22417`.
   <https://arxiv.org/abs/2606.22417> · full text <https://arxiv.org/html/2606.22417v1>
4. Gloaguen, T., Mündler, N., Müller, M., Raychev, V., Vechev, M. (12 Feb 2026; rev. 23
   Jun 2026). "Evaluating AGENTS.md: Are Repository-Level Context Files Helpful for
   Coding Agents?" arXiv `2602.11988`. <https://arxiv.org/abs/2602.11988>
5. Dente, F., Satriani, D., Papotti, P. (7 May 2026). "Constraint Decay: The Fragility
   of LLM Agents in Backend Code Generation." arXiv `2605.06445`.
   <https://arxiv.org/abs/2605.06445>

**Official documentation**

6. Claude Code — "Manage costs effectively." <https://code.claude.com/docs/en/costs.md>
   (raw Markdown; living doc, undated)

**Practitioner / vendor (primary but not peer-reviewed)**

7. Aider — "Repository map." <https://aider.chat/docs/repomap.html>
8. Aider — "Building a better repository map with tree sitter" (22 Oct 2023).
   <https://aider.chat/2023/10/22/repomap.html>
9. Heule, S., Jia, E., Jain, N. (6 Nov 2025). "Improving agent with semantic search."
   Cursor. <https://cursor.com/blog/semsearch>
10. Agarwal, A., Peet, C. (19 Nov 2025). "How we're making GitHub Copilot smarter with
    fewer tools." The GitHub Blog.
    <https://github.blog/ai-and-ml/github-copilot/how-were-making-github-copilot-smarter-with-fewer-tools/>

**Searched and rejected**

11. Sourcegraph blog (domain-limited search). Marketing-grade; no eval, no methodology,
    no numbers. <https://sourcegraph.com/blog/agentic-coding> and neighbours.
    **Not cited.**

**Cited from `03-agent-engineering.md`, not re-fetched here**

Anthropic "Effective context engineering" · "Writing effective tools for agents" ·
"Code execution with MCP" · "How we built our multi-agent research system" · Claude Code
sub-agents / memory / skills docs · Chroma context rot · Liu et al. "Lost in the
Middle" · Pocock's *Dictionary of AI Coding*.

## Verification notes

| Claim | How obtained | Confidence |
| --- | --- | --- |
| SHERLOC abstract sentences, 84.33% / 81.27% / +5.95 pp / 36.7% / 23.1% | arXiv abs page, direct fetch | High |
| SHERLOC "half their budget" + "67% … nearby candidates" + "dominant failure mode is reasoning error (40%)" | arXiv HTML, **two independent fetches with differently-worded prompts, character-identical** | **Verbatim-confirmed** |
| SHERLOC failure taxonomy counts (22/15/14/2/1/1, n=55) | arXiv HTML full text, single fetch | High |
| Agentless "hundreds of files…" + "sub-optimal explorations" | ar5iv HTML, **two independent fetches, character-identical** | **Verbatim-confirmed** |
| Aider "GPT-4 is actually great at making the code changes…" | aider.chat, **two independent fetches, character-identical** | **Verbatim-confirmed** |
| Agentless three-phase quote, 77.7% file localization, 32.00% / $0.70 | abs page + ar5iv HTML, single fetch each | High |
| GitHub Copilot 40→13 tools, +2–5 pp, "Tool Use Coverage" | github.blog, single fetch | High as *vendor-reported* |
| Sourcegraph as a usable source | Domain-limited search | **Rejected — marketing-grade** |
| Agentless class/line-level localization accuracy | **Not extracted** | Not claimed |
| "Code Isn't Memory" abstract | arXiv abs page | High |
| "Code Isn't Memory" 84.5/44.3, 50.4/41.9, $2.30/$2.92, 91 instances | arXiv HTML full text | High — single fetch; re-check before external publication |
| costs.md quotes | Raw Markdown (`.md` suffix), no summariser in the chain | Very high |
| Aider repo-map quotes and 1k default | Two direct fetches of aider.chat | High |
| Cursor 12.5% / 0.3% / 2.6% / 2.2% | Direct fetch of cursor.com/blog/semsearch | High as *vendor-reported*; not independently replicated |
| AGENTS.md ">20% cost" sentence | arXiv abs page | High |
| AGENTS.md +2.4% / +3.34 steps / −0.5% / −2% | **Search summary only; PDF tables not extractable** | **Low — do not quote** |
| Cursor vector+graph+BM25 index architecture | **Search summary only** | **Low — do not quote** |
| "No source measures hops or file count" | Three targeted searches (§2), all negative | Moderate — a negative result over a bounded search |
