# AI-Agent / Context-Engineering Concepts — Verified Primary-Source Map

**Purpose.** The AI half of a parallel between classic software architecture (deep modules, information hiding, TDD, ADRs) and AI-agent architecture. Every definition below is traced to a primary source or explicitly flagged as unverified.

**Access date for all URLs: 2026-07-31.**

## Evidence markers

Three markers are used throughout. They are not decorative — they are the contract of this document.

| Marker | Meaning |
| --- | --- |
| `[VERIFIED]` | Definition and/or number comes from a primary source (peer-reviewed paper, vendor engineering blog, official docs, the author's own repo). Source URL given. |
| `[PRACTITIONER]` | Practitioner coinage / folklore. It has a traceable author and wording, but no peer review and usually no controlled experiment behind the specific numbers. |
| `[OUR ANALOGY]` | An inference *we* are drawing between the software-architecture literature and the agent literature. The sources do **not** state it. |
| `[UNVERIFIED]` | Could not be traced to a primary source. What was searched is recorded inline. |

A fourth situation appears often and is called out where it occurs: **a claim that circulates widely in aggregator blogs but is absent from the named primary source.** Those are treated as `[UNVERIFIED]`, not as facts.

## Executive summary — the verdicts that matter

- **Strongest empirical evidence:** Liu et al. (TACL 2024) — GPT-3.5-Turbo drops **75.8% → 53.8%** by moving the answer to the middle of 20 documents, **below its 56.1% closed-book baseline**. Peer-reviewed. §2.
- **Strongest agent-economics number:** Anthropic — loading tool definitions on demand instead of up front cuts **150,000 tokens to 2,000 (98.7%)**. §13.10.
- **Chroma context rot:** real, 18 models, six experiment families — but **its accuracy curves are charts, not stated numbers.** The prose-level findings are the distractor, shuffled-haystack, and LongMemEval results. §1.
- **"Dumb zone":** defined by **Matt Pocock** in his own dictionary (pinned SHA), *"around 125K-150K tokens — though this is debated."* **The widely-quoted "100K / 40% of window" figure is not his, and "coined by Dex Horthy" is unverified** — the phrase does not appear in HumanLayer's own document. §6.
- **"Harness engineering":** coined by **Mitchell Hashimoto, 5 Feb 2026**, who explicitly says no industry term existed yet. **"Harness"** itself is established Anthropic vocabulary. §7.
- **Deep tools ↔ deep modules:** **Anthropic does NOT make this analogy.** It compares tools to *"the functions of a well-designed codebase"* and invokes *"separation of concerns"*, but never uses "deep", "shallow", "module", "information hiding", "Ousterhout" or "Parnas" — and explicitly warns against designing tools the way you'd design APIs for developers. §8.2.
- **Documentation drift is a live hazard.** The April 2025 Claude Code best-practices post — the one containing Anthropic's explicit **TDD** workflow — no longer exists at its original URL; it redirects to a rewritten living doc where the TDD section is gone. The original is only available via Wayback. §13.8.

---

## 1. Context rot

`[VERIFIED — empirical]`

**Definition.** The degradation of LLM performance as input length grows, *even well within the model's stated context window*. Performance is non-uniform across input length; the model does not treat token 100,000 the way it treats token 1,000.

**Origin of the term.** `[PRACTITIONER — traced]` The phrase appears to originate in a **Hacker News comment by user "Workaccount2" on 18 June 2025**, captured the same day by Simon Willison (who wrote that Workaccount2 "just coined the term"):

> "They poison their own context. Maybe you can call it **context rot**, where as context grows and especially if it grows with lots of distractions and dead ends, the output quality falls off rapidly."

Source: <https://news.ycombinator.com/item?id=44308711#44310054> · documented at <https://simonwillison.net/2025/Jun/18/context-rot/> (18 Jun 2025).

The Chroma technical report (26 days later) supplied the **empirical backing** and made the term standard. Anthropic subsequently adopted it in its own engineering blog (see §3), defining it as: *"as the number of tokens in the context window increases, the model's ability to accurately recall information from that context decreases."* Note the coinage is an anonymous forum comment, not a paper — cite Chroma for evidence, Workaccount2/Willison only for etymology.

**Primary source.** Kelly Hong, Anton Troynikov, Jeff Huber, **"Context Rot: How Increasing Input Tokens Impacts LLM Performance"**, Chroma technical report, **14 July 2025**.
<https://research.trychroma.com/context-rot> (301-redirects to <https://www.trychroma.com/research/context-rot>)
Replication code: <https://github.com/chroma-core/context-rot> (MIT).

### Experimental setup (verbatim structure)

**18 models** across four labs:

- Anthropic (5): Claude Opus 4, Claude Sonnet 4, Claude Sonnet 3.7, Claude Sonnet 3.5, Claude Haiku 3.5
- OpenAI (7): o3, GPT-4.1, GPT-4.1 mini, GPT-4.1 nano, GPT-4o, GPT-4 Turbo, GPT-3.5 Turbo
- Google (3): Gemini 2.5 Pro, Gemini 2.5 Flash, Gemini 2.0 Flash
- Alibaba (3): Qwen3-235B-A22B, Qwen3-32B, Qwen3-8B

**Six experiment families:**

1. **Needle–question similarity.** Needles varied by cosine similarity to the question across five embedding models. PG-essay needles spanned similarity **0.445–0.775**; arXiv needles spanned **0.521–0.829** (each with **<0.1 standard deviation** across embedding models).
2. **Distractors.** Three conditions — baseline (needle only), one distractor, four distractors. Distractors are topically related but non-answering content.
3. **Needle–haystack similarity.** Two haystacks (Paul Graham essays; arXiv papers) crossed with two needle sources. Measured average similarity: PG haystack + PG needles **0.529**; PG haystack + arXiv needles **0.368**; arXiv haystack + arXiv needles **0.654**; arXiv haystack + PG needles **0.394**.
4. **Haystack structure.** Original logically-ordered haystack vs. a version with sentences randomly shuffled.
5. **LongMemEval.** Conversational QA benchmark, **306 prompts**. Two conditions: **focused** input (~**300 tokens**, only the relevant conversation turns) vs. **full** input (~**113k tokens**, the whole chat history).
6. **Repeated words.** The model replicates a sequence of repeated words with a single unique word inserted at a varying position. Input lengths **25, 50, 75, 100, 250, 500, 750, 1000, 2500, 5000, 7500, 10000 words**; **1,090** (context-length × position) combinations.

### Results — the numbers that exist

> "LLMs do not maintain consistent performance across input lengths. Even on tasks as simple as non-lexical retrieval or text replication, we see increasing non-uniformity in performance as input length grows."

- **Distractors:** *"Even a single distractor reduces performance relative to the baseline (needle only), and adding four distractors compounds this degradation further."* Distractors 2 and 3 appeared most frequently in hallucinated responses; **Claude models had the lowest hallucination rates, GPT models the highest** — Claude models tended to abstain when uncertain.
- **Needle–question similarity:** *"performance degrades more quickly in input length with lower similarity needle-question pairs."* I.e. the further you get from literal lexical match, the faster long context hurts you.
- **Haystack structure (counterintuitive, and the most quotable finding):** *"Across all 18 models and needle-haystack configurations, we observe a consistent pattern that models perform better on shuffled haystacks than on logically structured ones."* And: *"Shuffling the haystack and removing local coherence consistently improves performance."*
- **LongMemEval:** *"Across all models, we see significantly higher performance on focused prompts compared to full prompts."* The focused/full gap is **~300 tokens vs ~113k tokens of input for the same question** — the cleanest available demonstration that *input length alone*, holding the answer constant, moves accuracy.
- **Repeated words:** *"Sonnet 3.5 … outperforms the newer Claude models up to its maximum output token count of 8192. Opus 4 … while exhibiting the slowest degradation rate, is also the only model in this family to refuse the task (2.89% of attempts)."* GPT-4.1 refusal rate **2.55%**; Qwen3-8B non-attempt rate **4.21%**. Accuracy was highest when the unique word sat **near the beginning** of the sequence.

**⚠️ Honest limitation of this citation.** The report's headline *accuracy-vs-input-length curves are published as charts, not as stated numbers in prose.* There is no sentence in the report of the form "accuracy fell from X% at 1k tokens to Y% at 100k tokens." Any blog quoting such a pairing as a Chroma finding is interpolating from the figures. The prose-level numbers are the ones listed above. To get per-point accuracies you must run the repo. **Do not quote a Chroma accuracy percentage you have not read off the figures yourself.**

**Stated conclusion / mitigation.**

> "Whether relevant information is present in a model's context is not all that matters; what matters more is how that information is presented. We demonstrate that even the most capable models are sensitive to this, making effective context engineering essential for reliable performance."

---

## 2. Lost in the middle

`[VERIFIED — peer-reviewed]` — the highest-value citation in this document.

**Definition.** Language models use long input contexts non-uniformly with respect to *position*: accuracy is highest when the relevant information is at the **beginning** or the **end** of the context and degrades significantly when it sits in the **middle**. The resulting accuracy-vs-position plot is **U-shaped**.

**Primary source.** Nelson F. Liu, Kevin Lin, John Hewitt, Ashwin Paranjape, Michele Bevilacqua, Fabio Petroni, Percy Liang, **"Lost in the Middle: How Language Models Use Long Contexts"**, *Transactions of the Association for Computational Linguistics*, **Vol. 12 (2024), pp. 157–173**, DOI `10.1162/tacl_a_00638`. arXiv preprint `2307.03172`, first posted 6 July 2023.
<https://aclanthology.org/2024.tacl-1.9/> · <https://arxiv.org/abs/2307.03172>

**Note on the year:** arXiv v1 is 2023; the TACL volume is **12 (2024)**. Cite it as TACL 2024.

### Abstract (verbatim)

> "While recent language models have the ability to take long contexts as input, relatively little is known about how well they use longer context. We analyze the performance of language models on two tasks that require identifying relevant information in their input contexts: multi-document question answering and key-value retrieval. We find that performance can degrade significantly when changing the position of relevant information, indicating that current language models do not robustly make use of information in long input contexts. In particular, we observe that performance is often highest when relevant information occurs at the beginning or end of the input context, and significantly degrades when models must access relevant information in the middle of long contexts, even for explicitly long-context models. Our analysis provides a better understanding of how language models use their input context and provides new evaluation protocols for future long-context language models."

### Setup

- **Multi-document QA:** the answering document is placed among **10, 20, or 30** total documents; its position is swept.
- **Models:** MPT-30B-Instruct, LongChat-13B (16K), GPT-3.5-Turbo, GPT-3.5-Turbo (16K), Claude-1.3, Claude-1.3 (100K).
- **Key-value retrieval:** synthetic JSON dicts with **75, 140, and 300** key-value pairs, **500 examples each**.

### Numbers

**20-document setting, accuracy by position of the answering document:**

| Model | First | Middle | Last |
| --- | --- | --- | --- |
| GPT-3.5-Turbo | **75.8%** | **53.8%** | **63.2%** |
| Claude-1.3 | 59.9% | 56.8% | 60.1% |
| MPT-30B-Instruct | 53.7% | 52.2% | 56.3% |

- **Largest observed positional swing: ~22 percentage points** (GPT-3.5-Turbo, first → middle).
- **The killer result:** GPT-3.5-Turbo's **closed-book** accuracy (no documents at all, answering from parametric knowledge) is **56.1%**. Its **middle-position** accuracy in the 20-document setting is **53.8%** — i.e. **giving the model the correct document, buried in the middle, made it worse than giving it nothing.** Oracle (only the answering document in context) is **88.3%**.
- **Key-value retrieval:** GPT-3.5-Turbo, GPT-3.5-Turbo (16K) and MPT-30B-Instruct show the U-shape; Claude-1.3 and Claude-1.3 (100K) are near-perfect on this synthetic task. (So the U-curve is model-dependent, not universal — worth stating honestly.)
- **On bigger windows not being the fix:** *"When the input context fits in the context window of both a model and its extended-context counterpart, we see that performance between them is nearly identical"* — the paper concludes *"extended-context models are not necessarily better than their non-extended counterparts at using their input context."*

**Mitigation implied by the paper.** Put the load-bearing material at the **head or tail** of the context; re-rank retrieved documents so the best one is first; and do not assume a larger window buys you better use of the window.

---

## 3. Context engineering

`[VERIFIED]`

**Definition (Anthropic, verbatim).**

> Context engineering: *"strategies for curating and maintaining the optimal set of tokens (information) during LLM inference."*
> Prompt engineering: *"methods for writing and organizing LLM instructions for optimal outcomes."*

Context engineering is framed as the superset — it manages the whole context state (system instructions, tool definitions, external data, message history), not just the instruction string.

**The guiding principle (verbatim).**

> "find the *smallest* *possible* set of high-signal tokens that maximize the likelihood of some desired outcome."

**Primary source.** Prithvi Rajasekaran, Ethan Dixon, Carly Ryan, Jeremy Hadfield (with Rafi Ayub, Hannah Moran, Cal Rueb, Connor Jennings), **"Effective context engineering for AI agents"**, Anthropic Engineering, **29 September 2025**.
<https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents>

### The techniques, verbatim

- **Attention budget.** The LLM has a finite capacity for parsing large context; *"every new token introduced depletes this budget."*
- **Context rot.** Cited directly: *"as the number of tokens in the context window increases, the model's ability to accurately recall information from that context decreases."* (Anthropic pointing at the Chroma finding.)
- **Just-in-time retrieval / progressive disclosure.** Agents *"maintain lightweight identifiers (file paths, stored queries, web links, etc.) and use these references to dynamically load data into context at runtime using tools"*, enabling *"incremental discovery of relevant context through exploration."*
- **Compaction.** *"Taking a conversation nearing the context window limit, summarizing its contents, and reinitiating a new context window with the summary."*
- **Structured note-taking.** *"Agent regularly writes notes persisted to memory outside of the context window. These notes get pulled back into the context window at later times."*
- **Sub-agent architectures.** Specialised agents work in clean context windows and return condensed summaries to a coordinating agent — *"separation of concerns."*
- **Tool design.** Tools should be *"self-contained, robust to error, and extremely clear with respect to their intended use."* The named failure mode: *"bloated tool sets that cover too much functionality or lead to ambiguous decision points."*

### The earlier foundation

**"Building effective agents"**, Erik Schluntz and Barry Zhang, Anthropic Engineering, **19 December 2024**.
<https://www.anthropic.com/engineering/building-effective-agents>

- **Workflows vs agents (verbatim):** *"Workflows are systems where LLMs and tools are orchestrated through predefined code paths."* / Agents *"are systems where LLMs dynamically direct their own processes and tool usage."*
- **The central design claim:** *"the most successful implementations weren't using complex frameworks or specialized libraries. Instead, they were building with simple, composable patterns."*
- **Orchestrator-workers:** *"A central LLM dynamically breaks down tasks, delegates them to worker LLMs, and synthesizes their results."*
- **Evaluator-optimizer:** *"One LLM call generates a response while another provides evaluation and feedback in a loop."*
- **Agent-Computer Interface (ACI).** The appendix argues for investing in tool-interface design *comparable to* the effort put into human-computer interface design: "put yourself in the model's shoes," test extensively, poka-yoke the arguments so the model cannot easily misuse them. **This is the closest Anthropic gets to an explicit interface-design analogy — see §8.**

---

## 4. Progressive disclosure

Two lineages. Keep them separate.

### (a) HCI / UX origin

`[VERIFIED — definition]` / `[UNVERIFIED — coinage attribution]`

**Canonical definition.** Jakob Nielsen, **"Progressive Disclosure"**, Nielsen Norman Group, **3 December 2006**. <https://www.nngroup.com/articles/progressive-disclosure/>

Two steps:

1. *"Initially, show users only a few of the most important options."*
2. *"Offer a larger set of specialized options upon request."*

Nielsen states that progressive disclosure *"has long been one of application design's primary guidelines"* and that progressive and staged disclosure are *"more than 30 years old"* (writing in 2006 — so roughly mid-1970s roots).

**⚠️ Coinage attribution is `[UNVERIFIED]`.** Nielsen's article **does not credit anyone** with originating the term. Searched: NN/g article body, NN/g related articles (`split-interfaces-progressive-disclosure` → 404), and web search for JoAnn Hackos / Kurt Schmucker / IBM attributions. **No primary source found** tying the coinage to Hackos, Schmucker, or IBM. Secondary sources (Wikipedia, IxDF) variously credit **Carroll & Rosson's "training wheels" work (1983–84)** and **Kristina Hooper Woolsey (Apple Human Interface Group, 1985)** — those are plausible antecedents but are *secondary attributions*, not primary. Cite NN/g for the definition; do not assert a coiner.

### (b) Adoption in agent / skill design

`[VERIFIED]`

**Primary source.** Barry Zhang, Keith Lazuka, Mahesh Murag, **"Equipping agents for the real world with Agent Skills"**, Anthropic Engineering, **16 October 2025**.
<https://www.anthropic.com/engineering/equipping-agents-for-the-real-world-with-agent-skills>

Anthropic uses the HCI term explicitly and makes it the load-bearing design principle:

> "Progressive disclosure is the core design principle that makes Agent Skills flexible and scalable."

**The three levels (verbatim):**

1. **Metadata** (name + description from the YAML frontmatter): *"it provides just enough information for Claude to know when each skill should be used without loading all of it into context"*
2. **SKILL.md body:** *"If Claude thinks the skill is relevant to the current task, it will load the skill by reading its full `SKILL.md` into context"*
3. **Bundled files:** *"Claude can choose to navigate and discover only as needed"*

**The analogy Anthropic itself draws:**

> "Like a well-organized manual that starts with a table of contents, then specific chapters, and finally a detailed appendix, skills let Claude load information only as needed"

> "Building a skill for an agent is like putting together an onboarding guide for a new hire."

Also: skills are *"composable resources"*; Anthropic *"published Agent Skills as an open standard for cross-platform portability"*; supported across Claude.ai, Claude Code, the Claude Agent SDK, and the Claude Developer Platform.

> `[OUR ANALOGY]` Progressive disclosure is **information hiding (Parnas 1972) applied to the context window instead of to the compiler.** The public interface is the skill's `description`; the implementation is the body and bundled files; the caller (the model) binds to the interface and pays the token cost of the implementation only on invocation. Anthropic makes the *manual/table-of-contents* analogy, not the *module/information-hiding* one — the module framing is ours.

---

## 5. Attention and long-context degradation mechanics

`[VERIFIED]` for each cited paper. **Caution:** none of these papers *is* "context rot"; they are the mechanistic and benchmarking neighbourhood around it.

### 5.1 Quadratic attention

Self-attention cost is **O(n²)** in sequence length — every token attends to every other token. This is the standard transformer result (Vaswani et al., "Attention Is All You Need", NeurIPS 2017, arXiv `1706.03762`).

**⚠️ Anti-fabrication note.** Several popular summaries say quadratic attention makes cost grow *"exponentially."* That is **wrong** — quadratic is not exponential. More importantly, **the quadratic cost is a compute/memory cost, not a demonstrated cause of accuracy degradation.** The accuracy degradation is empirically established (§1, §2); the causal mechanism is *not* settled, and "quadratic attention causes context rot" is an inference, not a cited finding. Do not present it as one.

### 5.2 Attention sinks (StreamingLLM)

Guangxuan Xiao, Yuandong Tian, Beidi Chen, Song Han, Mike Lewis, **"Efficient Streaming Language Models with Attention Sinks"**, **ICLR 2024**, arXiv `2309.17453` (29 Sep 2023; rev. 7 Apr 2024). <https://arxiv.org/abs/2309.17453>

- **Finding:** models allocate *"strong attention scores towards initial tokens as a 'sink' even if they are not semantically important."*
- **Consequence:** *"keeping the KV of initial tokens will largely recover the performance of window attention."*
- **Numbers:** enables modelling *"up to 4 million tokens and more"*; **up to 22.2× speedup** vs. the sliding-window-with-recomputation baseline.
- **Why it matters here:** it is direct evidence that attention allocation is *positionally biased in a way that has nothing to do with relevance* — the mechanistic cousin of "lost in the middle."

### 5.3 RULER — effective vs. claimed context length

Cheng-Ping Hsieh, Simeng Sun, Samuel Kriman, Shantanu Acharya, Dima Rekesh, Fei Jia, Yang Zhang, Boris Ginsburg (NVIDIA), **"RULER: What's the Real Context Size of Your Long-Context Language Models?"**, **COLM 2024**, arXiv `2404.06654`. <https://arxiv.org/abs/2404.06654>

- **Motivation, verbatim:** needle-in-a-haystack *"is indicative of only a superficial form of long-context understanding."* RULER adds multi-needle, multi-hop tracing, and aggregation categories.
- **Numbers:** **17 long-context models evaluated.** *"Almost all models exhibit large performance drops as the context length increases."* Despite claiming 32K+ support, **only about half maintain satisfactory performance at 32K.**
- **This is the citation for "effective context length ≪ advertised context length."**

### 5.4 Needle in a Haystack (NIAH)

`[PRACTITIONER — but foundational]` Greg Kamradt, **"Pressure Testing GPT-4 & Claude 2.1 Long Context"**, November 2023.
<https://github.com/gkamradt/LLMTest_NeedleInAHaystack> · <https://mail.gregkamradt.com/posts/pressure-testing-gpt-4-claude-2-1-long-context>

- A single out-of-place sentence (*"The best thing to do in San Francisco is eat a sandwich and sit in Dolores Park on a sunny day"*) is inserted at varying depths in Paul Graham essays of varying length; the model is asked to retrieve it. Results are rendered as a (context length × needle depth) heatmap.
- **It is a blog post and a repo, not a peer-reviewed paper.** Cite it as the origin of the *methodology*, not as evidence.
- **Known weaknesses**, and both are attested by primary sources: RULER (§5.3) says it tests only *"a superficial form of long-context understanding"*; Chroma (§1) shows NIAH is easy precisely because the needle is usually lexically similar to the question and sits in an incoherent haystack — both of which flatter the model relative to real tasks.

---

## 6. "Dumb zone" (and "smart zone")

`[PRACTITIONER — verified authorship, verified wording]`

**Verdict: VERIFIED as a documented practitioner term with a primary source in Matt Pocock's own repository. NOT verified as *coined* by him.**

**Primary source.** Matt Pocock, *Dictionary of AI Coding*, entry `dictionary/Smart zone.md`.
Repo: <https://github.com/mattpocock/dictionary-of-ai-coding> (created **2026-05-01**; no license declared)
Pinned commit: **`251fec7ec3b08059e4203863024e6123090a54e3`** (HEAD as of 2026-07-31)
Permalink: <https://github.com/mattpocock/dictionary-of-ai-coding/blob/251fec7ec3b08059e4203863024e6123090a54e3/dictionary/Smart%20zone.md>

**Note the headword.** The entry is filed under **"Smart zone"**, with `Dumb zone` and `Smart zone / Dumb zone` as declared frontmatter **aliases**. "Dumb zone" is defined as the opposite pole *inside* that entry, not as a standalone headword.

### Verbatim definition

> "Early in a session the agent is in a "smart zone" — sharp, focused, recall is good. As the session grows it drifts into a "dumb zone": sloppier, forgetful, more mistakes — and more faithfulness hallucinations. Same model, same harness — just more context. The felt effect of attention degradation. **On frontier models, the dumb zone commonly begins around 125K-150K tokens — though this is debated.** Clear or compact when the session bloats; don't push through."

Further verbatim from the same entry:

> "The decline is gradual, which makes it easy to miss. There's no error message and no visible boundary… Because the slide is smooth, the usual response is to push through and re-explain — which adds more context and makes the problem worse."

> "The zones don't track the context window limit. A session can be deep in the dumb zone with most of the window still free: the limit is where the harness refuses to continue, but quality falls off long before that. Plan around the smart zone, not the window."

> "The smart zone is a budget, and unrelated work spends it. Every task done in a session uses up tokens, so starting a second task in the same session means starting it closer to the dumb zone. Doing one task per session gives each task the sharpest part of the session."

### ⚠️ Three corrections to what circulates online

1. **The "~100K tokens / ~40% of the context window" figure is NOT in Pocock's dictionary.** `[UNVERIFIED]` That pairing appears only in **aggregator write-ups of a talk** (explainx.ai, biggo, agenticonsult, agentpatterns.ai, dev.to). Pocock's own written source says **"around 125K-150K tokens — though this is debated,"** and explicitly says the zones **do not track a percentage of the window**. Searched: the dictionary repo (all 70+ entries + git history back to the initial commit `71b89e931`, 2026-05-01), `mattpocock/dictionary-of-ai-coding` commit log, web search on aihero.dev / youtube / X. **The percentage framing could not be traced to any Pocock primary source.**
2. **"Coined by Dex Horthy (HumanLayer)" is `[UNVERIFIED]`.** That attribution is repeated by agentpatterns.ai and several dev.to posts. I fetched HumanLayer's own primary document — `advanced-context-engineering-for-coding-agents/ace-fca.md` (<https://github.com/humanlayer/advanced-context-engineering-for-coding-agents>) — and **the phrase "dumb zone" does not appear in it.** What that document *does* say verbatim is: *"designing your ENTIRE WORKFLOW around context management, and keeping utilization in the 40%-60% range (depends on complexity of the problem)"*, and it introduces *"intentional compaction"* and the **Research → Plan → Implement (RPI)** workflow. So the **40–60% number is Horthy's; the phrase "dumb zone" is not attested in his written source.** The claim that he "analyzed 100,000 developer sessions" was **not found in any primary source** and should not be repeated.
3. **Coinage is therefore open.** The defensible claim: *"'Dumb zone' is defined by Matt Pocock in his Dictionary of AI Coding; the entry dates to 2026-05-01 in that repo. Who first used the phrase is `[unverified]`."*

### Verifiable neighbours (use these when you need evidence, not vocabulary)

- **Context rot** (§1) — the measured phenomenon, 18 models.
- **Lost in the middle** (§2) — the peer-reviewed positional version.
- **RULER** (§5.3) — effective context length ≪ advertised context length, 17 models.
- **`/compact` and `/clear` before quality drops** — the mitigation, documented in Claude Code's own docs (§13) and in Anthropic's compaction guidance (§3, §10).

### Pocock's own mechanism vocabulary (same repo, same pinned SHA)

`[PRACTITIONER]` — this is his mental model, **not** established transformer science. Anthropic independently uses "attention budget" too (§3), so it is shared practitioner vocabulary, but the cited *mechanistic* work is §5.

- **Attention relationship:** *"When predicting each token, the model factors in every other token in the context — some heavily, others barely at all. The pairing between two tokens is an attention relationship."*
- **Attention budget:** *"Each token has a finite amount of influence to distribute across the rest of the context… The budget is per-token and doesn't grow when the context does, which is why long sessions dilute."* The signal/noise framing: *"An instruction that was the loudest thing at 10k tokens of context is background hum at 150k. This is the mechanism behind attention degradation: the model doesn't forget; the signal gets lost in the noise."*
- **Attention degradation:** *"As a session grows, each token's attention budget is spread across more competitors… You recover by removing context, not adding more."*

---

## 7. "Harness" and "harness engineering"

Two separate questions with two different answers.

### 7.1 "Harness" — `[VERIFIED as established vendor vocabulary]`

**Anthropic's own definition**, from **"Demystifying evals for AI agents"** (Mikaela Grace, Jeremy Hadfield, Rodrigo Olivares, Jiri De Jonghe; Anthropic Engineering, **9 January 2026**), <https://www.anthropic.com/engineering/demystifying-evals-for-ai-agents>:

> "An agent harness (or scaffold) is the system that enables a model to act as an agent: it processes inputs, orchestrates tool calls, and returns results."

The same post distinguishes it from the **eval harness**:

> "An evaluation harness is the infrastructure that runs evals end-to-end. It provides instructions and tools, runs tasks concurrently, records all the steps, grades outputs, and aggregates results."

**Anthropic's harness-focused engineering posts:**

- Justin Young et al., **"Effective harnesses for long-running agents"**, Anthropic Engineering, **26 November 2025**. <https://www.anthropic.com/engineering/effective-harnesses-for-long-running-agents> — describes the Claude Agent SDK as *"a powerful, general-purpose agent harness adept at coding."* Key patterns quoted verbatim: *"Agents need a way to bridge the gap between coding sessions"*; the first session writes an `init.sh`, a `claude-progress.txt` log, and an initial git commit; *"Self-verify all features. Only mark features as 'passing' after careful testing."*; *"Providing Claude with these kinds of testing tools dramatically improved performance, as the agent was able to identify and fix bugs."* Scale referenced: *"over 200 features"* in the claude.ai-clone example, running over *"hours, or even days."* **No token counts or eval scores are given in that post.**
- Prithvi Rajasekaran, **"Harness design for long-running application development"**, Anthropic Engineering, **24 March 2026**. <https://www.anthropic.com/engineering/harness-design-long-running-apps> — *"Harness design has a substantial impact on the effectiveness of long running agentic coding."* Design rule: *"Find the simplest solution possible, and only increase complexity when needed."* On resets: *"A reset provides a clean slate, at the cost of the handoff artifact having enough state for the next agent to pick up the work cleanly."* On evaluation: *"Separating the agent doing the work from the agent judging it proves to be a strong lever"*, and *"Tuning a standalone evaluator to be skeptical turns out to be far more tractable than making a generator critical of its own work."* Reported costs: retro-game-maker solo run **$9 / 20 min** vs. full harness **$200 / 6 hr** (**~20× cost**); a DAW build at **3 hr 50 min / $124.70**; frontend design **5–15 iterations per generation**, up to **4 hours per run**.

**Matt Pocock's definition** (same repo, same pinned SHA — <https://github.com/mattpocock/dictionary-of-ai-coding/blob/251fec7ec3b08059e4203863024e6123090a54e3/dictionary/Harness.md>):

> "Everything around the model that turns it into an agent: tools, system prompt, context-window management, permissions, hooks. **Claude.ai** and **Claude Code** run on the same model but behave differently because their harnesses differ."

And the diagnostic consequence, verbatim:

> "When behaviour differs between two products, or between yesterday and today, the model is often not the variable — the harness is."

> "the harness is where most of your configuration lives: AGENTS.md files, permission settings, and hooks are all instructions to the harness, not the model."

### 7.2 "Harness engineering" — `[PRACTITIONER — coinage traced to a primary source]`

**Verdict: traced. Mitchell Hashimoto self-identifies as coining it, and explicitly says no industry term existed yet.**

**Primary source.** Mitchell Hashimoto, **"My AI Adoption Journey"**, **5 February 2026**. <https://mitchellh.com/writing/my-ai-adoption-journey>

> "I don't know if there is a broad industry-accepted term for this yet, but I've grown to calling this 'harness engineering.'"

The practice he describes: whenever an agent makes a mistake, engineer a permanent fix into the agent's environment — better documentation, a new tool, a check — so it cannot make that mistake again, *"usually paired with an AGENTS.md change to let it know about this existing."*

**Downstream adoption.** OpenAI published **"Harness engineering: leveraging Codex in an agent-first world"** (<https://openai.com/index/harness-engineering/>) shortly afterwards. **⚠️ `[UNVERIFIED CONTENT]`** — this URL returned **HTTP 403** to automated fetch on 2026-07-31, so **no quote or number from it is reproduced here.** Widely-repeated figures (a three-person team, five months, ~1M lines of production code, ~1,500 merged PRs) come only from secondary summaries and are **not verified**. Fetch it manually before citing.

### 7.3 Relationship to "test harness"

`[OUR ANALOGY — with a caveat]`

"Test harness" is long-established software-engineering vocabulary: the scaffolding that sets up, invokes, and tears down a system under test. **No primary source found states that the AI usage is a deliberate borrowing** — Hashimoto does not say so, Anthropic does not say so, Pocock does not say so. `[UNVERIFIED]` as to intent.

What *is* verifiable is that the two meanings converge structurally: both are the non-product scaffolding that makes an otherwise inert artefact runnable and observable. And Anthropic's evals post uses **both senses in the same document** (`agent harness` and `eval harness`), which is at minimum evidence that the vocabulary is felt to be the same word. Treat the etymological claim as ours, not theirs.

---

## 8. Agent tooling design — the deep-modules parallel

### 8.1 What Anthropic actually says `[VERIFIED]`

**Primary source.** **"Writing effective tools for agents"**, Anthropic Engineering, **11 September 2025**. <https://www.anthropic.com/engineering/writing-tools-for-agents>

Verbatim:

- *"More tools don't always lead to better outcomes. A common error we've observed is tools that merely wrap existing software functionality."*
- Build *"a few thoughtful tools targeting specific high-impact workflows"* rather than comprehensive coverage.
- *"Tools can consolidate functionality, handling potentially multiple discrete operations (or API calls) under the hood."* Worked example: collapse `list_users` + `list_events` + `create_event` into a single `schedule_event`.
- Token efficiency: *"We suggest implementing some combination of pagination, range selection, filtering, and/or truncation with sensible default parameter values."*
- Namespacing: group by service and resource (`asana_search`, `asana_projects_search`) to help tool selection.
- Return *"only high signal information back to agents"*; prioritise *"contextual relevance over flexibility."*
- A `ResponseFormat` enum letting the agent request `concise` vs `detailed` responses cut token consumption by **roughly two-thirds** in their example.
- Development method: **evaluation-driven** — measure tool performance systematically before and after changes.

From **"Building effective agents"** (§3), the **ACI** framing: invest in the agent-computer interface with effort *comparable to* human-computer interface design; "put yourself in the model's shoes"; design tool arguments so mistakes are hard to make.

### 8.2 Does Anthropic make the deep-modules analogy explicitly?

**No. `[OUR ANALOGY]`**

I ran a targeted term check over the full text of "Writing effective tools for agents" for `deep`, `shallow`, `module`, `information hiding`, `abstraction`, `encapsulat`, `Ousterhout`, `Parnas`. **All eight: not present.** The nearest things Anthropic says are (a) the **ACI/HCI** analogy in "Building effective agents," and (b) the **consolidation** guidance (fewer, thicker tools).

**What Anthropic *does* say (verified, and closer than you'd expect).** A term check over "Effective context engineering for AI agents" returns NO for `deep module`, `shallow`, `information hiding`, `abstraction`, `Ousterhout`, `Parnas` — but YES for two things:

> "Similar to the functions of a well-designed codebase, tools should be self-contained, robust to error, and extremely clear with respect to their intended use."

> "This approach achieves a clear separation of concerns—the detailed search context remains isolated within sub-agents"

So Anthropic **does** draw an explicit software-design analogy — at the level of **well-designed functions** and **separation of concerns**. It does **not** reach for module *depth*, information hiding, or the Ousterhout/Parnas literature. The gap between "self-contained functions" and "deep modules" is exactly the gap our analogy fills.

**And there is a counter-signal you must not suppress.** "Writing effective tools for agents" explicitly *distances* tool design from ordinary API/library design:

> "instead of writing tools and MCP servers the way we'd write functions and APIs for other developers or systems, we need to design them for agents"

Anthropic's argument is that a tool is a contract between a *deterministic* system and a *non-deterministic* consumer, and therefore is **not** simply an API with a different caller. So: the *shape* of the advice (consolidate, hide the plumbing, return only high-signal results) rhymes with deep modules — but Anthropic's stated position is that agent tools require rethinking API practice, not inheriting it. Present the parallel as a useful lens, and note this caveat alongside it.

So the mapping is ours, and should be labelled as such:

| Ousterhout / Parnas | Agent tooling | Status |
| --- | --- | --- |
| **Deep module** = large functionality behind a small interface (*A Philosophy of Software Design*, 2018) | `schedule_event` hiding three API calls behind one tool signature | `[OUR ANALOGY]` — the consolidation advice is Anthropic's, and Anthropic does say tools should be like *"the functions of a well-designed codebase"*; the **depth** framing is ours |
| **Shallow module** = interface as complex as the implementation | A tool that *"merely wrap[s] existing software functionality"* — Anthropic's named anti-pattern | `[OUR ANALOGY]`, but Anthropic independently identifies the same anti-pattern |
| **Information hiding** (Parnas, *"On the Criteria To Be Used in Decomposing Systems into Modules"*, CACM 15(12), 1972) | Progressive disclosure: metadata in context, body behind a pointer | `[OUR ANALOGY]` — Anthropic uses the manual/table-of-contents analogy instead |
| **Interface complexity is paid by every caller** | Every tool definition is paid in input tokens on **every turn** | `[OUR ANALOGY]`; the token-cost half is verified (§3 attention budget) |

The parallel is strong and defensible — but it is an inference, and the document must not imply Anthropic drew it.

### 8.3 The quantified version of the argument

`[VERIFIED]` "Code execution with MCP: building more efficient AI agents", Anthropic Engineering, **4 November 2025** (full treatment in §13.10):

> "This reduces the token usage from **150,000 tokens to 2,000 tokens**—a time and cost saving of **98.7%**."

That is the cost of loading *all* tool definitions up front versus loading only the ones the current task needs, measured by Anthropic. Combined with §13.9's **84% token reduction / 39% performance improvement** from context editing + the memory tool, this is the empirical backbone of the "fewer, deeper, lazily-loaded tools" position.

### 8.4 MCP

Model Context Protocol — see §13.7 for the primitives and design principles as documented. The relevant design tension: **MCP makes it trivially easy to attach many tools, while Anthropic's own tool-writing guidance argues for few, consolidated ones** — and Anthropic's code-execution post quantifies the damage of the many-tools default at 150k tokens. That tension is real and worth naming explicitly in the study. `[OUR ANALOGY]` as framed; both halves individually `[VERIFIED]`.

---

## 9. Sub-agents / context isolation / orchestrator-worker

`[VERIFIED — with the best hard numbers in the agent literature]`

**Primary source.** Jeremy Hadfield, Barry Zhang, Kenneth Lien, Florian Scholz, Jeremy Fox, Daniel Ford, **"How we built our multi-agent research system"**, Anthropic Engineering, **13 June 2025**.
<https://www.anthropic.com/engineering/multi-agent-research-system>

### The architecture

> "orchestrator-worker pattern, where a lead agent coordinates the process while delegating to specialized subagents that operate in parallel."

### The numbers (all verbatim)

- **Performance:** *"multi-agent system with Claude Opus 4 as the lead agent and Claude Sonnet 4 subagents outperformed single-agent Claude Opus 4 by 90.2%"* on their internal research eval.
- **Token economics:** *"agents typically use about 4× more tokens than chat interactions"*; *"multi-agent systems use about 15× more tokens than chats."*
- **What drives performance:** on BrowseComp, *"token usage by itself explains 80% of the variance"*; *"three factors explained 95% of the performance variance"* (token usage, number of tool calls, model choice).

### The failure modes (verbatim) — the honest half

Early versions of the system produced agents *"spawning 50 subagents for simple queries"*, *"scouring the web endlessly for nonexistent sources"*, *"distracting each other with excessive updates"*, duplicating work from vague task descriptions, and selecting *"SEO-optimized content farms over authoritative"* sources. Human testers also found *"hallucinated answers on unusual queries"* and *"system failures."*

**Practical reading.** Sub-agents buy you **context isolation** at a **~15× token cost**. That is a real architectural trade, not a free win, and it only pays where the task parallelises and the sub-results compress well.

**Pocock's matching definition** (pinned SHA, `dictionary/Subagent.md`):

> "An agent spawned by another agent via a tool call. Runs in its own session with its own context window, and reports a single tool result back… **Cannot spawn further subagents** — the tree is one level deep. Subagents exist to isolate context, not to compose hierarchies."

> "The report is a secondary source: the parent gets the subagent's account of what it found, not the raw results, so anything the report leaves out is invisible to the parent."

`[PRACTITIONER]` for the "one level deep" claim as stated — it describes Claude Code's implementation, not a universal property.

---

## 10. Compaction, memory, statefulness across sessions

`[VERIFIED]`

### Anthropic's definitions

From "Effective context engineering for AI agents" (§3):

- **Compaction:** *"Taking a conversation nearing the context window limit, summarizing its contents, and reinitiating a new context window with the summary."*
- **Structured note-taking:** *"Agent regularly writes notes persisted to memory outside of the context window. These notes get pulled back into the context window at later times."*

From "Effective harnesses for long-running agents" (§7.1): *"Agents need a way to bridge the gap between coding sessions"* — solved by having the first session create `init.sh`, `claude-progress.txt`, and an initial git commit, i.e. **externalising state to the filesystem and to git.**

From "Harness design for long-running application development" (§7.1): *"A reset provides a clean slate, at the cost of the handoff artifact having enough state for the next agent to pick up the work cleanly."*

### The lossiness framing `[PRACTITIONER — but the sharpest articulation available]`

Pocock's dictionary (pinned SHA) is unusually precise about *why* compaction is dangerous, using a primary/secondary-source frame:

- **Compaction** (`dictionary/Compaction.md`): *"A handoff done in-memory: the previous session's history is summarised, and the summary seeds a fresh session. Lossy by design: the transcript is a primary source, the summary a secondary source — detail traded for headroom."* And: *"Timing matters too — compact at a phase boundary, after the plan is settled, not mid-task."*
- **Autocompact** (`dictionary/Autocompact.md`): fires *"when the context window approaches full… often around 80%"*. *"A manual compact happens at a phase boundary, when you can tell the model what to preserve. Autocompact fires mid-task… with the summary deciding for itself which of your decisions were worth keeping."*
- **Handoff artifact** (`dictionary/Handoff artifact.md`): *"the model is stateless, so nothing in a session survives clearing it… The environment persists. Writing the important state into a file moves it somewhere the next session can read it back from."* And: *"it records what the writing session believed, and anything it left out or got wrong is invisible to the reader. Where a claim matters, the next session should verify it against the primary source — the code, the tests — rather than inherit it."*
- **Memory system** (`dictionary/Memory system.md`): *"memories are secondary sources, so they drift: a fact recorded in March is loaded with equal confidence in June, after the project has moved on. A memory system needs pruning, the same way AGENTS.md does."*
- **Context pointer** (`dictionary/Context pointer.md`): *"A pointer is one line in the context window. The document behind it might be thousands of tokens, but those tokens cost nothing until the agent actually follows the pointer."* Requirement: *"a stable path, and enough description for the agent to know when following it is worth it."*

> `[OUR ANALOGY]` This is exactly what `CONTEXT.md` + ADRs + an issue tracker do in the repo being studied: they turn a **stateless** model plus a **lossy** in-memory summary into a **durable, reviewable, diffable** external state store. The ADR in particular is a handoff artifact whose whole point is that "why we decided this" survives every `/clear`. Anthropic and Pocock both describe file-based state externalisation; **neither names ADRs** — that mapping is ours.

---

## 11. Evals and verification loops for agents

`[VERIFIED]`

**Primary source.** Mikaela Grace, Jeremy Hadfield, Rodrigo Olivares, Jiri De Jonghe, **"Demystifying evals for AI agents"**, Anthropic Engineering, **9 January 2026**. <https://www.anthropic.com/engineering/demystifying-evals-for-ai-agents>

### Vocabulary (verbatim)

- **Task:** *"A single test with defined inputs and success criteria"*
- **Trial:** *"Each attempt at a task"* (run multiple, because models are non-deterministic)
- **Transcript:** *"The complete record of a trial, including outputs, tool calls, reasoning, intermediate results"*
- **Outcome:** *"The final state in the environment at the end of the trial"*
- **Grader:** *"Logic that scores some aspect of the agent's performance"*
- **Evaluation suite:** *"A collection of tasks designed to measure specific capabilities"*
- Plus **agent harness** and **eval harness** (see §7.1).

### The load-bearing guidance

- **Grade the outcome, not the narration** — the single most TDD-adjacent idea in the post:
  > "A flight-booking agent might say 'Your flight has been booked' at the end of the transcript, but the outcome is whether a reservation exists in the environment's SQL database."
- **LLM-as-judge needs calibration:** *"LLM-as-judge graders should be closely calibrated with human experts to gain confidence that there is little divergence between the human grading and model grading."*
- **Evals belong in CI:** *"Automated evals are especially useful pre-launch and in CI/CD, running on each agent change and model upgrade as the first line of defense."*
- **Start small — the concrete number:** *"We see teams delay building evals because they think they need hundreds of tasks. In reality, 20-50 simple tasks drawn from real failures is a great start."*

### Supporting evidence for "agents need verifiable feedback"

- **Evaluator-optimizer** pattern, "Building effective agents" (§3): *"One LLM call generates a response while another provides evaluation and feedback in a loop"* — recommended *"when we have clear evaluation criteria, and when iterative refinement provides measurable value."*
- **Separate the doer from the judge**, "Harness design for long-running application development" (§7.1): *"Separating the agent doing the work from the agent judging it proves to be a strong lever."* And: *"Tuning a standalone evaluator to be skeptical turns out to be far more tractable than making a generator critical of its own work."*
- **Give the agent the test tools**, "Effective harnesses for long-running agents" (§7.1): *"Providing Claude with these kinds of testing tools dramatically improved performance, as the agent was able to identify and fix bugs."* And the instruction *"Self-verify all features. Only mark features as 'passing' after careful testing."*
- **Evaluation-driven tool development**, "Writing effective tools for agents" (§8.1).

> `[OUR ANALOGY]` Red-green-refactor is an evaluator-optimizer loop with a **deterministic** grader. TDD's real gift to an agent is not the tests — it is that **the grader is cheap, fast, and not itself an LLM.** Anthropic's own guidance ("grade the outcome, not the transcript"; "separate the doer from the judge"; "self-verify") arrives at the same shape from the other direction, but **no Anthropic source frames it as TDD.** The equation `red/green ≡ outcome-graded eval` is ours.

---

## 12. Prompt/context anti-patterns with evidence

`[VERIFIED]`

### 12.1 Irrelevant context degrades accuracy

**Primary source.** Freda Shi, Xinyun Chen, Kanishka Misra, Nathan Scales, David Dohan, Ed H. Chi, Nathanael Schärli, Denny Zhou, **"Large Language Models Can Be Easily Distracted by Irrelevant Context"**, **ICML 2023**, arXiv `2302.00093`. <https://arxiv.org/abs/2302.00093>

- Introduces **GSM-IC** (Grade-School Math with Irrelevant Context): *"an arithmetic reasoning dataset with irrelevant information in the problem description."*
- Finding: *"model performance is dramatically decreased when irrelevant information is included."*
- Mitigations the authors identify: **decoding with self-consistency**, and **an explicit prompt instruction telling the model to ignore irrelevant information.**

**⚠️** The abstract does not state per-condition accuracy deltas; the drop magnitudes live in the paper's tables. Do not quote a specific GSM-IC percentage without reading the tables.

### 12.2 Distractor injection in long context

Chroma (§1): *"Even a single distractor reduces performance relative to the baseline (needle only), and adding four distractors compounds this degradation further."* Distractors were topically related but non-answering. Hallucination behaviour split by lab: **Claude models lowest hallucination rate (they abstain), GPT models highest.**

### 12.3 The practitioner taxonomy of context failure

`[PRACTITIONER — primary, well-sourced]` Drew Breunig, **"How Long Contexts Fail"**, **22 June 2025**. <https://www.dbreunig.com/2025/06/22/how-contexts-fail-and-how-to-fix-them.html>

Four named failure modes, verbatim:

1. **Context poisoning** — *"When a hallucination or other error makes it into the context, where it is repeatedly referenced."*
2. **Context distraction** — *"When a context grows so long that the model over-focuses on the context, neglecting what it learned during training."*
3. **Context confusion** — *"When superfluous content in the context is used by the model to generate a low-quality response."*
4. **Context clash** — *"When you accrue new information and tools in your context that conflicts with other information in the prompt."*

Numbers he cites (⚠️ **second-hand — verify each against its own source before quoting**; Breunig is aggregating, so these are his citations, not his measurements):

- **Gemini 2.5** past 100k tokens showed *"a tendency toward favoring repeating actions from its vast history rather than synthesizing novel plans"* (from the DeepMind Pokémon agent write-up).
- **Databricks / Llama 3.1 405B:** correctness began declining around **32k tokens**.
- **Berkeley Function-Calling Leaderboard v3:** every model performs worse with more tools; *"all models will occasionally call tools that aren't relevant."*
- **GeoEngine benchmark (46 tools):** a quantized **Llama 3.1 8B failed with all 46 tools but succeeded with only 19** within a 16k window.
- **Microsoft / Salesforce multi-turn study:** sharding a single prompt across turns produced *"an average drop of 39%"*; **OpenAI o3 fell from 98.1 to 64.1.**

> The tool-count numbers (Berkeley FCL, GeoEngine) are the strongest external evidence for Anthropic's "fewer, consolidated tools" guidance in §8, and therefore for the deep-tools argument. Chase them to their primary sources before putting weight on them.

### 12.4 The anti-pattern catalogue

| Anti-pattern | Evidence | Mitigation |
| --- | --- | --- |
| Dumping everything into the context "just in case" | Chroma LongMemEval: ~113k full vs ~300 focused, focused wins across all models | Just-in-time retrieval; context pointers |
| Burying the key fact in the middle | Liu et al. 2024: 75.8% → 53.8% (below the 56.1% closed-book baseline) | Put load-bearing content at head/tail; re-rank |
| Adding topically-similar-but-wrong material | Chroma distractors; Shi et al. GSM-IC | Filter retrieval by answerability, not just similarity |
| Trusting a bigger window to fix it | Liu et al.: extended-context models *"not necessarily better"*; RULER: only ~half of 17 models hold up at 32K | Budget for effective length, not advertised length |
| Bloated always-on instruction files | Anthropic: *"bloated tool sets that cover too much functionality or lead to ambiguous decision points"*; attention budget | Progressive disclosure; skills instead of AGENTS.md bulk |
| Re-pasting an ignored instruction | `[PRACTITIONER]` Pocock: *"Re-pasting the ignored instruction adds another competitor to the same crowded window and helps only briefly"* | Clear/compact and reload only what the task needs |
| Letting autocompact fire mid-task | `[PRACTITIONER]` Pocock: fires *"often around 80%"*, *"with the summary deciding for itself which of your decisions were worth keeping"* | Compact manually at a phase boundary with a preservation prompt |

---

## 13. Claude Code specifics — the harness, verified against official docs

`[VERIFIED]` All quotes below are from **raw Markdown source** of the official docs (fetched by appending `.md` to each path), so there is no summarizer in the chain.

### ⚠️ 13.0 The doc URLs moved

Every `docs.claude.com` URL now redirects:

| Old | Current |
| --- | --- |
| `docs.claude.com/en/docs/agents-and-tools/*` | `platform.claude.com/docs/en/agents-and-tools/*` |
| `docs.claude.com/en/docs/claude-code/*` | `code.claude.com/docs/en/*` (the `/claude-code/` segment is dropped) |
| `anthropic.com/engineering/claude-code-best-practices` | `code.claude.com/docs/en/best-practices` (the blog post was absorbed into the docs) |

### 13.1 Agent Skills

<https://platform.claude.com/docs/en/agents-and-tools/agent-skills/overview> · <https://platform.claude.com/docs/en/agents-and-tools/agent-skills/best-practices> · <https://code.claude.com/docs/en/skills>

**Progressive disclosure, verbatim from the overview:**

> "This filesystem-based architecture enables **progressive disclosure:** Claude loads information in stages as needed, rather than consuming context upfront."

> "Progressive disclosure ensures only relevant content occupies the context window at any given time."

From best-practices (a section literally headed `### Progressive disclosure patterns`):

> "SKILL.md serves as an overview that points Claude to detailed materials as needed, like a table of contents in an onboarding guide."

> "This filesystem-based model is what enables progressive disclosure. Claude can navigate and selectively load exactly what each task requires."

Phrase counts: best-practices ×6, overview ×2, plugins ×1. **`code.claude.com/docs/en/skills` uses the phrase zero times** — the term lives on the cross-product Agent Skills pages, not the Claude Code page.

**The three-level loading table (verbatim from the overview) — this is the money table:**

| Level | When loaded | Token cost | Content |
| --- | --- | --- | --- |
| **Level 1: Metadata** | Always (at startup) | **~100 tokens per Skill** | `name` and `description` from YAML frontmatter |
| **Level 2: Instructions** | When Skill is triggered | **Under 5k tokens** | SKILL.md body |
| **Level 3+: Resources** | As needed | **None until accessed** | Bundled files. Reference files load into context when read. Scripts run through bash, and only their output enters context |

Supporting quotes:

> "Claude loads this metadata at startup and includes it in the system prompt. […] This lightweight approach means you can install many Skills without context penalty: until a Skill is triggered, only its name and description occupy context."

> "When you request something that matches a Skill's description, Claude reads SKILL.md from the filesystem using bash. Only then does this content enter the context window."

> "When instructions mention executable scripts, Claude runs them through bash and receives only the output (the script code itself never enters context)."

Size rule (note: **lines**, and **tokens** — not words):

> "Keep SKILL.md body under 500 lines for optimal performance."

**Frontmatter.** Anthropic's cross-product spec documents exactly **two** fields:

- `name` — *"Maximum 64 characters · Must contain only lowercase letters, numbers, and hyphens · Cannot contain XML tags · Cannot contain reserved words: 'anthropic', 'claude'"*
- `description` — *"Must be non-empty · Maximum 1024 characters · Cannot contain XML tags"*

Claude Code extends this (all optional; only `description` recommended): `name`, `description`, `when_to_use`, `argument-hint`, `arguments`, `disable-model-invocation`, `user-invocable`, `allowed-tools`, `disallowed-tools`, `model`, `effort`, `context`, `agent`, `background`, `hooks`, `paths`, `shell`. Claude Code note: *"the combined `description` and `when_to_use` text is truncated at 1,536 characters in the skill listing to reduce context usage."*

**`license` and `metadata` are NOT in Anthropic's docs.** `[VERIFIED NEGATIVE]` They exist only in the third-party open standard at <https://agentskills.io/specification>, which Claude Code says it follows.

**`disable-model-invocation`, verbatim:**

> "Set to `true` to prevent Claude from automatically loading this skill. Use for workflows you want to trigger manually with `/name`. Also prevents the skill from being preloaded into subagents. […] Default: `false`."

> "Only you can invoke the skill. Use this for workflows with side effects or that you want to control timing, like `/commit`, `/deploy`, or `/send-slack-message`. You don't want Claude deciding to deploy because your code looks ready."

Critically, setting it also **removes the description from context** — the skill becomes invisible to the model until you type the slash command.

### 13.2 Subagents

<https://code.claude.com/docs/en/sub-agents>

> "Each subagent runs in its own context window with a custom system prompt, specific tool access, and independent permissions."

> "Each subagent starts with a fresh, isolated context window. It doesn't see your conversation history, the skills you've already invoked, or the files Claude has already read. Claude composes a delegation message that summarizes the task, and the subagent works from there. The exception is a fork, which inherits the parent conversation instead of starting fresh."

> "Use one when a side task would flood your main conversation with search results, logs, or file contents you won't reference again: the subagent does that work in its own context and returns only the summary."

Also: *"a subagent's context window is sized by its own model, not the parent's."* Tool restriction via `tools` (allowlist) and `disallowedTools` (denylist).

### 13.3 Hooks

<https://code.claude.com/docs/en/hooks-guide> · <https://code.claude.com/docs/en/hooks>

There are **30 hook events**, not the ~10 commonly cited. Selection relevant to context management and verification:

| Event | When it fires (verbatim) |
| --- | --- |
| `SessionStart` | "When a session begins or resumes" |
| `UserPromptSubmit` | "When you submit a prompt, before Claude processes it" |
| `PreToolUse` | "Before a tool call executes. Can block it" |
| `PostToolUse` | "After a tool call succeeds" |
| `PostToolUseFailure` | "After a tool call fails" |
| `SubagentStart` / `SubagentStop` | "When a subagent is spawned" / "When a subagent finishes" |
| `Stop` | "When Claude finishes responding" |
| `InstructionsLoaded` | "When a CLAUDE.md or `.claude/rules/*.md` file is loaded into context" |
| `PreCompact` / `PostCompact` | "Before context compaction" / "After context compaction completes" |
| `SessionEnd` | "When a session terminates" |

Others include `Setup`, `UserPromptExpansion`, `PermissionRequest`, `PermissionDenied`, `PostToolBatch`, `Notification`, `MessageDisplay`, `TaskCreated`, `TaskCompleted`, `StopFailure`, `TeammateIdle`, `ConfigChange`, `CwdChanged`, `FileChanged`, `WorktreeCreate`, `WorktreeRemove`, `Elicitation`, `ElicitationResult`.

> `[OUR ANALOGY]` `PreToolUse` blocking + `PostToolUse` checks are **deterministic invariants enforced outside the model** — the agent-harness equivalent of a type system or a pre-commit hook. This is the mechanical realisation of Hashimoto's "harness engineering" (§7.2): the fix lives in the harness, not in a prompt the model may drift away from.

### 13.4 Slash commands — merged into skills

Built-in commands: <https://code.claude.com/docs/en/commands>

> "**Custom commands have been merged into skills.** A file at `.claude/commands/deploy.md` and a skill at `.claude/skills/deploy/SKILL.md` both create `/deploy` and work the same way. Your existing `.claude/commands/` files keep working."

The context-management commands, verbatim:

- `/compact [instructions]` — *"Free up context by summarizing the conversation so far. Optionally pass focus instructions for the summary."*
- `/clear [name]` — *"Start a new conversation with empty context. Pass a name to label the previous conversation in the `/resume` picker. To free up context while continuing the same conversation, use `/compact` instead."* Aliases: `/reset`, `/new`.
- `/context [all]` — *"Visualize current context usage as a colored grid. Shows optimization suggestions for context-heavy tools, memory bloat, and capacity warnings."*
- `/rewind` — *"Rewind the conversation and/or code to a previous point, or summarize from a selected message."* Aliases: `/checkpoint`, `/undo`.

**This is the concrete mitigation for §1/§6.** `/context` makes context utilisation *observable*; `/compact` and `/clear` are the two levers.

### 13.5 Memory / CLAUDE.md

<https://code.claude.com/docs/en/memory>

Four tiers (note: the top tier is **"Managed policy"**, not "enterprise"):

| Tier | Path |
| --- | --- |
| Managed policy | macOS `/Library/Application Support/ClaudeCode/CLAUDE.md` · Linux/WSL `/etc/claude-code/CLAUDE.md` · Windows `C:\Program Files\ClaudeCode\CLAUDE.md` |
| User | `~/.claude/CLAUDE.md` |
| Project | `./CLAUDE.md` or `./.claude/CLAUDE.md` |
| Local | `./CLAUDE.local.md` |

Imports:

> "CLAUDE.md files can import additional files using `@path/to/import` syntax. Imported files are expanded and loaded into context at launch alongside the CLAUDE.md that references them."

> "Relative paths resolve relative to the file containing the import, not the working directory. Imported files can recursively import other files, with a maximum depth of four hops."

> "Import parsing skips Markdown code spans and fenced code blocks. To mention a path in your CLAUDE.md without importing it, wrap it in backticks."

**`[VERIFIED NEGATIVE]` The `#` shortcut is no longer documented.** It existed in the April 2025 best-practices post (*"press the `#` key to give Claude an instruction that it will automatically incorporate into the relevant CLAUDE.md"*) but is **NOT FOUND** in the current memory/commands/best-practices docs — replaced by `/memory` and an auto-memory system.

### 13.6 Plugins and marketplaces

<https://code.claude.com/docs/en/plugins> · <https://code.claude.com/docs/en/plugin-marketplaces>

> "Plugins let you extend Claude Code with custom functionality that can be shared across projects and teams. This guide covers creating your own plugins with skills, agents, hooks, and MCP servers."

A plugin can bundle: `.claude-plugin/plugin.json` (manifest), `skills/`, `commands/` (legacy flat form — *"Use `skills/` for new plugins"*), `agents/`, `hooks/hooks.json`, `.mcp.json`, `.lsp.json`, `monitors/monitors.json`, `bin/` (*"Executables added to the Bash tool's `PATH` while the plugin is enabled"*), `settings.json`. Marketplace manifest requires `name`, `owner`, `plugins`.

### 13.7 MCP

<https://code.claude.com/docs/en/mcp> · <https://modelcontextprotocol.io/docs/getting-started/intro> · <https://modelcontextprotocol.io/community/design-principles>

> "MCP (Model Context Protocol) is an open-source standard for connecting AI applications to external systems."

> "Think of MCP like a USB-C port for AI applications."

**Server primitives, verbatim:**

- **Tools:** *"Executable functions that AI applications can invoke to perform actions"*
- **Resources:** *"Data sources that provide contextual information to AI applications"*
- **Prompts:** *"Reusable templates that help structure interactions with language models"*

**⚠️ Client primitives have changed.** As of protocol version **`2026-07-28`**, **Sampling, Logging, and Roots are deprecated.** **Elicitation** (*"Allows servers to request additional information from users"*, via `elicitation/create`) is the only live client primitive. Any document still listing "sampling / elicitation / roots" as the current three is out of date.

**Design principles** (eight, verbatim names): *Convergence over choice · Composability over specificity · Interoperability over optimization · Stability over velocity · Capability over compensation · Demonstration over deliberation · Pragmatism over purity · Standardization over innovation.*

The two most relevant to the deep-tools argument:

> "**Composability over specificity** — MCP provides foundational primitives: resources, tools, and prompts. We don't add protocol features for use cases that can be constructed from these existing building blocks."

> "**Stability over velocity** — Adding to a protocol as widely adopted as MCP is easy. Removing from it is nearly impossible. Every addition is a permanent commitment… We optimize for decades, not quarters."

> `[OUR ANALOGY]` "Composability over specificity" and "stability over velocity" are, almost word for word, the argument for **narrow, stable interfaces** in module design. MCP's protocol designers reason like API designers; Anthropic's *tool*-writing guidance (§8) reasons about the token cost paid by a non-deterministic caller. Both pressures push the same way: fewer primitives, thicker behaviour behind each.

### 13.8 Claude Code best practices — and a documentation-drift warning

**⚠️ The April 2025 post no longer exists in its original form.** `https://www.anthropic.com/engineering/claude-code-best-practices` 301-redirects to <https://code.claude.com/docs/en/best-practices>, a living doc **with no publication date**, in which several sections have been rewritten or removed.

**Archived original** (Boris Cherny et al., **published 18 April 2025**), via Wayback:
<http://web.archive.org/web/20250502205533/https://www.anthropic.com/engineering/claude-code-best-practices>

The **TDD section, verbatim** — this is the single most important passage for the parallel the user is drawing, and it is **only available in the archive**:

> "This is an Anthropic-favorite workflow for changes that are easily verifiable with unit, integration, or end-to-end tests. Test-driven development (TDD) becomes even more powerful with agentic coding:"
>
> "Ask Claude to write tests based on expected input/output pairs. Be explicit about the fact that you're doing test-driven development so that it avoids creating mock implementations, even for functionality that doesn't exist yet in the codebase."
>
> "Tell Claude to run the tests and confirm they fail. Explicitly telling it not to write any implementation code at this stage is often helpful."
>
> "Ask Claude to write code that passes the tests, instructing it not to modify the tests. Tell Claude to keep going until all tests pass."

The **`/clear` guidance, verbatim (archived):**

> "During long sessions, Claude's context window can fill with irrelevant conversation, file contents, and commands. This can reduce performance and sometimes distract Claude. Use the `/clear` command frequently between tasks to reset the context window."

**Explore → plan → code → commit (archived):**

> "Ask Claude to read relevant files, images, or URLs… but explicitly tell it not to write any code just yet."
> "Ask Claude to make a plan… We recommend using the word 'think' to trigger extended thinking mode… 'think' < 'think hard' < 'think harder' < 'ultrathink.'"
> "Steps #1-#2 are crucial—without them, Claude tends to jump straight to coding a solution."

**Current live page.** `[VERIFIED NEGATIVE]` The dedicated TDD workflow section is **NOT FOUND** on the live page. It has been generalised into **"Give Claude a way to verify its work"**:

> "Claude stops when the work looks done. Without a check it can run, 'looks done' is the only signal available, and you become the verification loop… Give Claude something that produces a pass or fail, and the loop closes on its own."

Other current wording worth quoting:

> "Use `/clear` frequently between tasks to reset the context window entirely"

> "If you've corrected Claude more than twice on the same issue in one session, the context is cluttered with failed approaches. Run `/clear` and start fresh with a more specific prompt that incorporates what you learned. A clean session with a better prompt almost always outperforms a long session with accumulated corrections."

> "Keep it concise. For each line, ask: *'Would removing this cause Claude to make mistakes?'* If not, cut it. Bloated CLAUDE.md files cause Claude to ignore your actual instructions!"

> "CLAUDE.md is loaded every session, so only include things that apply broadly. For domain knowledge or workflows that are only relevant sometimes, use skills instead."

> "Since context is your fundamental constraint, subagents are one of the most powerful tools available. When Claude researches a codebase it reads lots of files, all of which consume your context. Subagents run in separate context windows and report back summaries"

> **Note for the study.** The drift from an explicit *TDD* section (2025) to a general *"give Claude a way to verify its work"* framing (current) is itself a finding: Anthropic generalised from "write tests first" to "close the verification loop with anything that produces pass/fail." The repo under study (`mattpocock/skills`) takes the *other* branch — it keeps TDD specific, and pins it to pre-agreed seams. Both are the same principle; only the specificity differs.

### 13.9 Context editing and the memory tool — the hard numbers

<https://www.anthropic.com/news/context-management> — **29 September 2025**

> "combining the memory tool with context editing improved performance by **39%** over baseline. Context editing alone delivered a **29%** improvement."

> "In a **100-turn web search evaluation**, context editing enabled agents to complete workflows that would otherwise fail due to context exhaustion—while **reducing token consumption by 84%.**"

Mechanism, verbatim:

> "Context editing automatically clears stale tool calls and results from within the context window when approaching token limits."

> "The memory tool enables Claude to store and consult information outside the context window through a file-based system… The memory tool operates entirely client-side through tool calls."

`[VERIFIED NEGATIVE]` These percentages appear **only** in the announcement post. The docs pages for context editing and the memory tool carry **no eval numbers** — do not cite the docs for them.

### 13.10 Code execution with MCP — the strongest number for the deep-tools argument

<https://www.anthropic.com/engineering/code-execution-with-mcp> — **4 November 2025**. The post contains a section literally titled **"Progressive disclosure."**

**The problems, verbatim:**

> "Tool descriptions occupy more context window space, increasing response time and costs. In cases where agents are connected to thousands of tools, they'll need to process hundreds of thousands of tokens before reading a request."

> "Every intermediate result must pass through the model. In this example, the full call transcript flows through twice. For a 2-hour sales meeting, that could mean processing an additional **50,000 tokens**."

**The fix and the number, verbatim:**

> "The agent discovers tools by exploring the filesystem: listing the `./servers/` directory to find available servers… then reading the specific tool files it needs… This lets the agent load only the definitions it needs for the current task. **This reduces the token usage from 150,000 tokens to 2,000 tokens—a time and cost saving of 98.7%.**"

> "Models are great at navigating filesystems. Presenting tools as code on a filesystem allows models to read tool definitions on-demand, rather than reading them all up-front."

Also recommended: a `search_tools` tool with *"a detail level parameter… (such as name only, name and description, or the full definition with schemas)"*, and pushing data filtering into executed code so *"the agent sees five rows instead of 10,000."*

> **150,000 → 2,000 tokens (98.7%) is the headline number for the whole progressive-disclosure / deep-tools thesis.** It is Anthropic's own measurement, it is stated in prose (not buried in a chart), and it is about *tool definitions* specifically — which is exactly the deep-vs-shallow-module axis.

---

## The parallel, stated honestly

| Software architecture | Agent architecture | Status |
| --- | --- | --- |
| Information hiding (Parnas 1972) | Progressive disclosure of skills (Anthropic 2025) | `[OUR ANALOGY]`; both halves `[VERIFIED]` |
| Deep module (Ousterhout 2018) | Consolidated tool (`schedule_event`) | `[OUR ANALOGY]`; Anthropic never says "deep" |
| Shallow module | Tool that *"merely wrap[s] existing software functionality"* | `[OUR ANALOGY]`; the anti-pattern itself is Anthropic's |
| Interface cost paid by every caller | Tool definitions + AGENTS.md paid in input tokens **every turn** | `[OUR ANALOGY]`; token cost `[VERIFIED]` |
| Red-green-refactor | Outcome-graded eval loop / evaluator-optimizer | `[OUR ANALOGY]`; the loop is `[VERIFIED]`, the TDD framing is ours |
| ADR | Handoff artifact / durable external state | `[OUR ANALOGY]`; file-externalisation is `[VERIFIED]` |
| Test harness | Agent harness | `[OUR ANALOGY]`; intent of the borrowing is `[UNVERIFIED]` |
| Bounded context / ubiquitous language | `CONTEXT.md` shared vocabulary | `[OUR ANALOGY]` |
| Module boundary chosen for testability | Sub-agent boundary chosen for context isolation | `[OUR ANALOGY]`; ~15× token cost is `[VERIFIED]` |
| Lazy loading / dynamic linking | Skill metadata (~100 tok) → body (<5k tok) → bundled files (0 until read) | `[OUR ANALOGY]`; the three-level table is `[VERIFIED]` from official docs |
| Static analysis / pre-commit hooks enforcing invariants | `PreToolUse` blocking + `PostToolUse` checks | `[OUR ANALOGY]`; the hook events are `[VERIFIED]` |
| "Don't repeat yourself" in a header everyone includes | *"Bloated CLAUDE.md files cause Claude to ignore your actual instructions!"* | `[VERIFIED]` (Claude Code docs) — the cost model is inverted: every always-on line is billed every turn |

---

## Verification notes — how each risky number was obtained

Recorded so a future reader can re-check the weakest links rather than trusting this file.

| Claim | How obtained | Confidence |
| --- | --- | --- |
| Liu et al. 20-doc table (75.8 / 53.8 / 63.2), closed-book 56.1%, oracle 88.3% | Full-text HTML rendering of arXiv 2307.03172 (ar5iv), Table 6 + Table 1 | High — but **re-check against the TACL PDF Table 6 before publishing externally** |
| TACL vol. 12 (2024), pp. 157–173, DOI `10.1162/tacl_a_00638` | ACL Anthology landing page | High |
| Chroma experiment design, similarity ranges, refusal rates, 306 prompts, 1,090 combinations | Chroma report page, two independent fetches | High |
| Chroma accuracy-vs-length curves | **Not extracted — they are charts.** Prose contains no such pairing | N/A — explicitly not claimed |
| Anthropic quotes (all posts) | Direct fetch of each anthropic.com engineering post | High |
| Claude Code / MCP docs quotes | **Raw Markdown source** (`.md` suffix on each Mintlify path), no summarizer in the chain | Very high |
| Pocock dictionary quotes | GitHub Contents API at pinned SHA `251fec7e`, base64-decoded | Very high |
| Hashimoto "harness engineering" coinage sentence | Direct fetch of mitchellh.com | High |
| "Dumb zone" NOT in HumanLayer's `ace-fca.md` | Direct fetch of the raw file, targeted phrase search | High (negative result) |
| No "deep"/"module"/"Ousterhout"/"Parnas" in Anthropic tool posts | Targeted term-presence checks on two posts | High (negative result) |
| Breunig's cited numbers (Databricks, Berkeley FCL, GeoEngine, MS/Salesforce) | **Second-hand via Breunig** — not chased to their own primary sources | **Low — verify before quoting** |
| OpenAI "Harness engineering" post content | **Not obtained (HTTP 403)** | None — nothing quoted |
| "~100K / 40% of window" dumb-zone threshold | Only in aggregators; absent from every primary source checked | **Rejected** |
| "Dex Horthy coined 'dumb zone' from 100,000 sessions" | Only in aggregators; contradicted by his own document | **Rejected** |

---

## Citations

All accessed **2026-07-31**.

### Peer-reviewed

1. Liu, N. F., Lin, K., Hewitt, J., Paranjape, A., Bevilacqua, M., Petroni, F., Liang, P. (2024). "Lost in the Middle: How Language Models Use Long Contexts." *TACL* **12**, 157–173. DOI `10.1162/tacl_a_00638`. <https://aclanthology.org/2024.tacl-1.9/> · arXiv `2307.03172` <https://arxiv.org/abs/2307.03172>
2. Xiao, G., Tian, Y., Chen, B., Han, S., Lewis, M. (2024). "Efficient Streaming Language Models with Attention Sinks." *ICLR 2024*. arXiv `2309.17453`. <https://arxiv.org/abs/2309.17453>
3. Hsieh, C.-P., Sun, S., Kriman, S., Acharya, S., Rekesh, D., Jia, F., Zhang, Y., Ginsburg, B. (2024). "RULER: What's the Real Context Size of Your Long-Context Language Models?" *COLM 2024*. arXiv `2404.06654`. <https://arxiv.org/abs/2404.06654>
4. Shi, F., Chen, X., Misra, K., Scales, N., Dohan, D., Chi, E. H., Schärli, N., Zhou, D. (2023). "Large Language Models Can Be Easily Distracted by Irrelevant Context." *ICML 2023*. arXiv `2302.00093`. <https://arxiv.org/abs/2302.00093>
5. Vaswani, A. et al. (2017). "Attention Is All You Need." *NeurIPS 2017*. arXiv `1706.03762`. <https://arxiv.org/abs/1706.03762>
6. Parnas, D. L. (1972). "On the Criteria To Be Used in Decomposing Systems into Modules." *CACM* **15**(12), 1053–1058. <https://dl.acm.org/doi/10.1145/361598.361623>
7. Ousterhout, J. (2018; 2nd ed. 2021). *A Philosophy of Software Design*. Yaknyam Press. — source of "deep module" / "shallow module" (ch. "Modules Should Be Deep"). Referenced here only as the software-architecture half of the analogy; the classic-architecture side is covered in its own research note.

### Industry research reports

8. Hong, K., Troynikov, A., Huber, J. (14 July 2025). "Context Rot: How Increasing Input Tokens Impacts LLM Performance." Chroma Technical Report. <https://research.trychroma.com/context-rot> → <https://www.trychroma.com/research/context-rot> · code <https://github.com/chroma-core/context-rot>

### Anthropic Engineering

9. Schluntz, E., Zhang, B. (19 Dec 2024). "Building effective agents." <https://www.anthropic.com/engineering/building-effective-agents>
10. Hadfield, J., Zhang, B., Lien, K., Scholz, F., Fox, J., Ford, D. (13 Jun 2025). "How we built our multi-agent research system." <https://www.anthropic.com/engineering/multi-agent-research-system>
11. (11 Sep 2025). "Writing effective tools for agents." <https://www.anthropic.com/engineering/writing-tools-for-agents>
12. Anthropic (29 Sep 2025). "Managing context on the Claude Developer Platform" — context editing + memory tool announcement; **source of the 39% / 29% / 84% numbers**. <https://www.anthropic.com/news/context-management>
13. Rajasekaran, P., Dixon, E., Ryan, C., Hadfield, J. (29 Sep 2025). "Effective context engineering for AI agents." <https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents>
14. Zhang, B., Lazuka, K., Murag, M. (16 Oct 2025). "Equipping agents for the real world with Agent Skills." <https://www.anthropic.com/engineering/equipping-agents-for-the-real-world-with-agent-skills>
15. (4 Nov 2025). "Code execution with MCP: building more efficient AI agents" — **source of the 150,000 → 2,000 token (98.7%) figure**. <https://www.anthropic.com/engineering/code-execution-with-mcp>
16. Young, J. et al. (26 Nov 2025). "Effective harnesses for long-running agents." <https://www.anthropic.com/engineering/effective-harnesses-for-long-running-agents>
17. Grace, M., Hadfield, J., Olivares, R., De Jonghe, J. (9 Jan 2026). "Demystifying evals for AI agents." <https://www.anthropic.com/engineering/demystifying-evals-for-ai-agents>
18. Rajasekaran, P. (24 Mar 2026). "Harness design for long-running application development." <https://www.anthropic.com/engineering/harness-design-long-running-apps>
19. Cherny, B. et al. (18 Apr 2025). "Claude Code Best Practices." **Original post now redirects**; archived at <http://web.archive.org/web/20250502205533/https://www.anthropic.com/engineering/claude-code-best-practices>. Live successor (undated living doc): <https://code.claude.com/docs/en/best-practices>

### Official documentation (raw Markdown source verified)

20. Agent Skills overview. <https://platform.claude.com/docs/en/agents-and-tools/agent-skills/overview>
21. Agent Skills best practices. <https://platform.claude.com/docs/en/agents-and-tools/agent-skills/best-practices>
22. Claude Code — Skills (and custom slash commands). <https://code.claude.com/docs/en/skills>
23. Claude Code — Built-in commands. <https://code.claude.com/docs/en/commands>
24. Claude Code — Subagents. <https://code.claude.com/docs/en/sub-agents>
25. Claude Code — Hooks. <https://code.claude.com/docs/en/hooks> · guide: <https://code.claude.com/docs/en/hooks-guide>
26. Claude Code — Memory / CLAUDE.md. <https://code.claude.com/docs/en/memory>
27. Claude Code — Plugins. <https://code.claude.com/docs/en/plugins> · marketplaces: <https://code.claude.com/docs/en/plugin-marketplaces>
28. Claude Code — MCP. <https://code.claude.com/docs/en/mcp>
29. Model Context Protocol — introduction. <https://modelcontextprotocol.io/docs/getting-started/intro> · client concepts: <https://modelcontextprotocol.io/docs/learn/client-concepts> · design principles: <https://modelcontextprotocol.io/community/design-principles>
30. Agent Skills open standard (third-party spec Claude Code follows; source of `license`/`metadata`/`compatibility` fields). <https://agentskills.io/specification>

### HCI

31. Nielsen, J. (3 Dec 2006). "Progressive Disclosure." Nielsen Norman Group. <https://www.nngroup.com/articles/progressive-disclosure/>

### Practitioner sources (primary, but not peer-reviewed)

32. Pocock, M. *Dictionary of AI Coding*. <https://github.com/mattpocock/dictionary-of-ai-coding> — **pinned commit `251fec7ec3b08059e4203863024e6123090a54e3`**, repo created 2026-05-01. Entries cited: `Smart zone`, `Harness`, `Progressive disclosure`, `Attention budget`, `Attention degradation`, `Attention relationship`, `Skill`, `Subagent`, `Context pointer`, `Compaction`, `Autocompact`, `Handoff artifact`, `Memory system`.
33. Hashimoto, M. (5 Feb 2026). "My AI Adoption Journey." <https://mitchellh.com/writing/my-ai-adoption-journey>
34. Horthy, D. / HumanLayer. "Advanced Context Engineering for Coding Agents" (`ace-fca.md`). <https://github.com/humanlayer/advanced-context-engineering-for-coding-agents>
35. Breunig, D. (22 Jun 2025). "How Long Contexts Fail." <https://www.dbreunig.com/2025/06/22/how-contexts-fail-and-how-to-fix-them.html>
36. Kamradt, G. (Nov 2023). "Pressure Testing GPT-4 & Claude 2.1 Long Context" / *LLMTest_NeedleInAHaystack*. <https://github.com/gkamradt/LLMTest_NeedleInAHaystack> · <https://mail.gregkamradt.com/posts/pressure-testing-gpt-4-claude-2-1-long-context>
37. "Workaccount2" (18 Jun 2025). Hacker News comment coining "context rot." <https://news.ycombinator.com/item?id=44308711#44310054> — documented by Simon Willison, <https://simonwillison.net/2025/Jun/18/context-rot/>
38. Pocock, M. *skills* (the repo under study). <https://github.com/mattpocock/skills>

### Cited but NOT fetched (do not quote without verifying)

39. OpenAI. "Harness engineering: leveraging Codex in an agent-first world." <https://openai.com/index/harness-engineering/> — **HTTP 403 to automated fetch on 2026-07-31.** Existence and title confirmed via search; **no content verified.**

### Explicitly rejected as sources

Aggregator/SEO write-ups encountered during research and **not used** for any factual claim: explainx.ai, biggo.com, agenticonsult.de, agentpatterns.ai, learn.agentpatterns.ai, dev.to posts, medium.com posts, morphllm.com, zenml.io, glasp.co, milvus.io, videohighlight.com. Several of these carry the "~100K tokens / 40% of window" dumb-zone figure and the "Dex Horthy coined 'dumb zone' from 100,000 developer sessions" claim; **neither survives contact with the primary sources** (see §6).
