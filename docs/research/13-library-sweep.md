# 13 — Library sweep: triaging the `ai-study-library` finding aid

**Date of sweep:** 2026-08-01. **Scope:** every `**Source:**` URL referenced by
`/home/rodrigo/Workspace/ai-study-library`, triaged by tier; then a targeted
fetch-and-verify pass on four named gaps.

**Standing constraint, restated so nobody re-litigates it.** `ai-study-library`
is a **finding aid only**. Leads found there are chased to the primary source,
and **nothing from that library is ever cited in our work**. It is a map to
other people's documents, not a document. Every citation below points at the
artifact itself, fetched here.

## Tier labels used in this file

Per `.claude/rules/research.md`, plus one sub-tier this sweep forced into
existence.

| Label | Meaning |
| --- | --- |
| `[QUOTED]` | Text was fetched; the quote is verbatim; the URL is given. |
| `[QUOTED — ASR]` | **New sub-tier.** Text came from YouTube **auto-generated captions** — machine transcription, not an authored text. It sits *below* `[QUOTED]`. See §3.1 for the specific error signature. |
| `[METADATA VERIFIED]` | Title / author / date read off the artifact's own `<meta>` tags, DOI, or publisher page. |
| `[NEGATIVE — VERIFIED]` | The artifact was fetched and the phrase **is not in it**. A finding, not a gap. |
| `[unverified]` | Everything else. Stays visibly unverified. |
| `[OUR INFERENCE]` | Our reasoning, not any source's claim. |

---

## 1. Method, and the true counts

Counts here are **derived**, not remembered (CLAUDE.md).

- The mechanical extraction produced **59 lines**.
- Those 59 lines contain **61 distinct URLs** across **32 distinct hosts**.
  Several lines were compound (`A (primary); B (related)`, `A | B`,
  `A / B`) and three were near-duplicates differing only by trailing
  whitespace or an `abs`/`html` suffix.
- Nothing was dropped: all 61 are tiered in §2. Two rows are bare host roots
  (`builder.aws.com`, `community.aws`) and one is a bare host root with a
  recorded 403 (`researchgate.net`) — those are tiered as *dead / not a
  source* rather than silently discarded.

**Methodological finding — the extraction under-counts.** The `**Source:**`
grep misses entries that record provenance in **YAML frontmatter** instead. In
this library that is `n = 1`:

```
source_url: "https://www.humanlayer.dev/blog/skill-issue-harness-engineering-for-coding-agents"
```

`[QUOTED]` — frontmatter of
`ai-study-library/docs/harness-engineering/skill-issue-harness-engineering-for-coding-agents.md`.

That single missed row turned out to be **the most consequential URL in the
whole library** for our gaps (§3 and §5). Any future sweep of this library must grep
`^source_url:` as well as `^\*\*Source:\*\*`. True total referenced:
**62 distinct URLs**.

### Tier counts (headline buckets)

| Tier | Count |
| --- | ---: |
| Vendor-primary (incl. 2 spec-org GitHub discussion threads) | **20** |
| Author-primary (a named person's own site or repo) | **11** |
| Academic (incl. 2 low-trust) | **10** |
| Community / secondary | **14** |
| Dead, blocked, or not a source at all | **6** |
| **Total distinct URLs** | **61** |

Two tiering traps worth naming, because both look like the opposite of what
they are:

1. **`deepwiki.com/humanlayer/...` is not a HumanLayer property.** It is an
   AI-generated wiki *over* Horthy's repo. The path says `humanlayer`; the
   authorship does not. **Secondary.**
2. **A personal blog is author-primary for its own claims and secondary the
   moment it relays someone else's.** `syntackle.com`, `spletzer.com` and
   `paddo.dev` all relay Horthy in this library, and §3 shows that **all three
   render him differently, and none matches the talk.**

**Two hosts, five URLs** — `dev.to` (×4) and `finance.biggo.com` (×1) — are
**named explicitly on corpus 03's rejected-aggregator list**
(`03-agent-engineering.md:933`), which also rejects `medium.com`,
`explainx.ai`, `agenticonsult.de`, `agentpatterns.ai`, `morphllm.com`,
`zenml.io`, `glasp.co`, `milvus.io` and `videohighlight.com` — none of which
appear in this library. Those five rows are marked in §2 and were **not**
re-litigated.

---

## 2. Pass 1 — full triage of all 61

| # | URL | Tier | Note |
| --- | --- | --- | --- |
| 1 | `https://aclanthology.org/2024.tacl-1.9` | Academic | TACL v12 (2024). **Already in our corpus** — Liu et al., *Lost in the Middle* (03 §2). Nothing new. |
| 2 | `https://aqfer.com/long-live-mcp-a-recap-of-mcp-dev-summit-ny/` | Community/secondary | Vendor-blog conference recap. Second-hand for every claim in it. |
| 3 | `https://arxiv.org/abs/2307.03172` | Academic | **Already in our corpus** (03 §2, 12 §2): *Lost in the Middle*, arXiv 2023-07-06 / TACL 2024. |
| 4 | `https://arxiv.org/abs/2412.10079` | Academic | `[METADATA VERIFIED]` *Lost in the Middle, and In-Between: Enhancing Language Models' Ability to Reason Over Long Contexts in Multi-Hop QA*, 2024-12-13. **New to us.** Candidate follow-on for the long-context evidence base. |
| 5 | `https://arxiv.org/abs/2508.10146` | Academic | `[METADATA VERIFIED]` *Agentic AI Frameworks: Architectures, Protocols, and Design Challenges*, 2025-08-13. Survey; low teaching value for our lessons. |
| 6 | `https://arxiv.org/abs/2510.25445` | Academic | `[METADATA VERIFIED]` *Agentic AI: A Comprehensive Survey of Architectures, Applications, and Future Directions*, 2025-10-29. Survey. |
| 7 | `https://arxiv.org/abs/2602.00180` | Academic | `[METADATA VERIFIED]` *Spec-Driven Development: From Code to Contract in the Age of AI Coding Assistants*, 2026-01-30. **New to us**; relevant if a spec-driven lesson is ever scoped. |
| 8 | `https://arxiv.org/html/2602.00180v1` | Academic | Full-text HTML of the row above. Same artifact. |
| 9 | `https://builder.aws.com` | Dead / not a source | The library's own entry says *"specific article not found - content compiled from web search"*. Host root only. Unusable. |
| 10 | `https://community.aws` | Dead / not a source | Same entry as above. Host root only. Unusable. |
| 11 | `https://cursor.com/blog/agent-best-practices` | Vendor-primary | Cursor's own blog about its own agent. |
| 12 | `https://cursor.com/docs/agent/tools` | Vendor-primary | Cursor docs. Note: `/docs/agent/tools.md` returns **HTTP 404**; the HTML route is JS-rendered. |
| 13 | `https://cursor.com/docs/hooks` | Vendor-primary | Cursor docs. |
| 14 | `https://cursor.com/docs/mcp` | Vendor-primary | Cursor docs. |
| 15 | `https://cursor.com/docs/plugins` | Vendor-primary | Cursor docs. |
| 16 | `https://cursor.com/docs/rules` | Vendor-primary | Cursor docs. `.md` route works (16,636 bytes fetched). |
| 17 | `https://cursor.com/docs/skills` | Vendor-primary | **Fetched — see §4.** HTML route is JS-rendered (26 bytes of text); append `.md` for the source. |
| 18 | `https://cursor.com/docs/subagents` | Vendor-primary | Cursor docs. |
| 19 | `https://deepwiki.com/humanlayer/advanced-context-engineering-for-coding-agents/3-the-research-plan-implement-workflow` | Community/secondary | **Tiering trap.** An AI-generated wiki *over* Horthy's repo, not a HumanLayer property. Never cite; go to the repo. |
| 20 | `https://dev.to/ametel01/advanced-context-engineering-for-coding-agents-11p7` | Community/secondary | dev.to distillation of the Horthy talk. **`dev.to` is on corpus 03's rejected-aggregator list (03:933).** |
| 21 | `https://dev.to/bobbyblaine/spec-driven-development-write-the-spec-not-the-code-2p5o` | Community/secondary | Same. Appears twice in the raw extraction (trailing-whitespace duplicate). |
| 22 | `https://dev.to/pockit_tools/context-engineering-the-most-important-ai-skill-nobodys-teaching-you-4o91` | Community/secondary | Same. |
| 23 | `https://dev.to/thangchung/mcp-programmatic-tool-calling-code-mode-with-opensandbox-4n3n` | Community/secondary | Same. |
| 24 | `https://docs.anthropic.com/en/docs/agents-and-tools/tool-use/implement-tool-use` | Vendor-primary | Anthropic docs. Note the canonical host is now `platform.claude.com/docs/...` — the HumanLayer post links that form. |
| 25 | `https://docs.anthropic.com/en/docs/agents-and-tools/tool-use/strict-tool-use` | Vendor-primary | Anthropic docs. |
| 26 | `https://docs.anthropic.com/en/docs/build-with-claude/structured-outputs` | Vendor-primary | Anthropic docs. |
| 27 | `https://docs.anthropic.com/en/docs/build-with-claude/tool-use` | Vendor-primary | Anthropic docs. |
| 28 | `https://docs.anthropic.com/en/docs/test-and-evaluate/strengthen-guardrails/increase-consistency` | Vendor-primary | Anthropic docs. |
| 29 | `https://docs.github.com/en/copilot/concepts/context/mcp` | Vendor-primary | GitHub docs on Copilot + MCP. |
| 30 | `https://doi.org/10.32628/IJSRST25125127` | Academic (low-trust) | DOI resolver for the IJSRST row below. Same artifact. |
| 31 | `https://dzone.com/articles/model-context-protocol-agent2agent-practical` | Community/secondary | DZone. Contributor-authored aggregator. |
| 32 | `https://finance.biggo.com/news/aee6b781d1226400` | Community/secondary | **`biggo.com` is explicitly named on corpus 03's rejected-aggregator list (03:933)** as a carrier of the "~100K / 40% of window" dumb-zone claim. Do not use. |
| 33 | `https://github.com/anthropics/anthropic-cookbook` | Vendor-primary | Anthropic's own repo. |
| 34 | `https://github.com/arpagon/pi-context-zone` | Author-primary (for its own claims only) | **Fetched — see §6.** This is where the MRCR "Smart Zone Ends" table actually lives. It is *secondary* for everything it attributes to Horthy. |
| 35 | `https://github.com/cameronking4/programmatic-tool-calling-ai-sdk` | Author-primary (for its own claims only) | Individual's demo repo. |
| 36 | `https://github.com/modelcontextprotocol/modelcontextprotocol/discussions/2248` | Vendor-primary (spec org) / community thread | A GitHub *discussion*: the thread is community speech hosted on the spec org's repo. Tier per comment, not per host. |
| 37 | `https://github.com/modelcontextprotocol/modelcontextprotocol/discussions/2460` | Vendor-primary (spec org) / community thread | Same caveat. |
| 38 | `https://github.com/modelcontextprotocol/servers` | Vendor-primary | Reference-server repo. |
| 39 | `https://github.com/modelcontextprotocol/typescript-sdk` | Vendor-primary | SDK repo. |
| 40 | `https://github.com/topics/anthropic-mcp` | Not a source | A GitHub *topic index*. Contains no authored claim. |
| 41 | `https://huggingface.co/a2aprotocol` | Not a source | Org profile page. |
| 42 | `https://huggingface.co/blog/1bo/a2a-protocol-explained` | Community/secondary | Community blog post hosted on HF. |
| 43 | `https://huggingface.co/spaces/a2aprotocol/a2aprotocol` | Not a source | A Space (hosted demo), not a document. |
| 44 | `https://jeremydaly.com/context-engineering-for-commercial-agent-systems/` | Author-primary (for his own claims) | Jeremy Daly's own site. Secondary wherever he relays someone else. |
| 45 | `https://mail.ijsrst.com/index.php/home/article/view/IJSRST25125127` | Academic (low-trust) | IJSRST. `[unverified]` — journal standing and peer-review process **not assessed in this sweep**. Do not cite without checking. |
| 46 | `https://markusharrer.de/blog/2026/02/17/agentic-software-modernization-chances-and-traps/` | Author-primary (for his own claims) | Markus Harrer's own site. Cites the Horthy talk — secondary for that. |
| 47 | `https://martinfowler.com/articles/exploring-gen-ai/sdd-3-tools.html` | Author-primary | martinfowler.com — the *Exploring Gen AI* memo series. High-trust for its own reporting. Appears twice in the raw extraction. |
| 48 | `https://modelcontextprotocol.io/specification` | Vendor-primary | The MCP specification itself. |
| 49 | `https://optinampout.com/blogs/mcp-vs-a2a-vs-acp-agent-protocols-2026` | Community/secondary | SEO comparison post. |
| 50 | `https://paddo.dev/blog/million-token-context/` | Author-primary (for his own claims) | **Secondary and wrong** on the one claim we checked: it says *the 12-factor agents framework* puts the 40%/dumb-zone line. See §3.4 — that phrase is **absent from the whole repo**. |
| 51 | `https://paddo.dev/blog/your-agents-md-is-a-liability/` | Author-primary (for his own claims) | Cites IFScale / lost-in-the-middle / Chroma context rot. Chase each to its own primary before use. |
| 52 | `https://pmc.ncbi.nlm.nih.gov/articles/PMC12353729/` | Academic | PubMed Central, PMCID PMC12353729. Not fetched in this sweep. |
| 53 | `https://syntackle.com/blog/long-context-window-ai-model-catch/` | Author-primary (for its own claims) / secondary for the quote | **This is where our "academic concept" lead came from.** It renders the Horthy line as clean prose. §3.2 shows the talk's actual wording differs. Never cite it for the quote. |
| 54 | `https://tylerburleigh.com/blog/2026/02/22` | Author-primary (for his own claims) | Personal blog. |
| 55 | `https://www.epsilla.com/blogs/2026-04-19-tool-search-redefining-agent-tool-calling-epsilla-` | Community/secondary | Vendor blog about *another* vendor's feature. |
| 56 | `https://www.martinrichards.me/post/building_your_own_agent_harness/` | Author-primary (for his own claims) | **Fetched — see §5.1.** Martin C. Richards, 2026-03-10. Gap-3-touching, and it supplies a *third* competing coinage attribution. |
| 57 | `https://www.mindstudio.ai/blog/progressive-disclosure-ai-agents-context-management` | Community/secondary | Vendor blog on progressive disclosure. Adds nothing over Nielsen 2006 + Anthropic, both already in corpus 03 §4. |
| 58 | `https://www.reddit.com/r/mcp/comments/1jqbkhd/http2http3_support_in_the_future/` | Community/secondary | The library's own entry flags it *"(Reddit - requires verification)"*. |
| 59 | `https://www.researchgate.net` | Dead / blocked | The library's own entry: *"direct fetch returned 403 Forbidden"*, and it is a bare host root — **no article is identified at all.** Unusable as written. |
| 60 | `https://www.speakeasy.com/blog/skills-vs-mcp` | Community/secondary | Vendor blog comparing two things it does not own. |
| 61 | `https://www.spletzer.com/2026/03/shedding-dead-context/` | Author-primary (for his own claims) / secondary for the quote | Attributes to Horthy a *"middle 40-60% of a large context window"* dumb zone. §3.2 shows the talk says *"around the 40% line"* as a floor, not a 40–60% band. Do not repeat its framing. |

---

## 3. Gap 1 — Dex Horthy, *No Vibes Allowed*, and the origin of the 40% figure

**Status: RESOLVED, in the direction that strengthens the settled verdict.**

### 3.1 The artifact

`[METADATA VERIFIED]` — read from the watch page's own player JSON:

| Field | Value |
| --- | --- |
| Title | **No Vibes Allowed: Solving Hard Problems in Complex Codebases – Dex Horthy, HumanLayer** |
| Video ID | `rmvDxxNubIg` |
| Channel (`ownerChannelName`) | **AI Engineer** (`UCLKPca3kwwd-B59HNr-_lvA`) |
| `uploadDate` / `publishDate` | **`2025-12-02T14:52:58-08:00`** |
| `lengthSeconds` | **`1231`** (20:31) |

<https://www.youtube.com/watch?v=rmvDxxNubIg>

**The description carries a chapter list.** `[QUOTED]` — from the watch page's
`shortDescription`, published by the AI Engineer channel and written in
Horthy's first person (*"Hey - I'm Dex…"*):

> ```
> Timestamps:
> 00:00 intro: complex code
> 01:40 context engineering
> 02:53 advanced context
> 04:38 context obsession
> 05:55 dumb zone concept
> 07:26 context management
> 09:37 complex problem solved
> 10:45 semantic diffusion
> 12:14 onboarding agents
> 13:57 internal docs lies
> 15:03 mental alignment key
> 16:12 code snippet plans
> 17:38 don't outsource think
> 18:45 rpi: smart zone
> 19:46 cultural change hard
> ```

So **"dumb zone" and "smart zone" appear in a dated, first-person artifact
attached to the talk**, at `05:55` and `18:45`. HumanLayer's own blog
independently deep-links the phrase "the dumb zone" to
`https://youtu.be/rmvDxxNubIg?si=…&t=355` — **t=355 s = 5:55**, matching the
chapter marker exactly. `[QUOTED]` (href read from the page HTML).

### 3.2 The transcript — what he actually says

**Method.** YouTube's signed `timedtext` endpoint returned **HTTP 200 with a
zero-byte body** on every variant tried (`raw`, `fmt=json3`, `fmt=srv3`,
`fmt=vtt`) — captions now require a PoToken. Obtained instead via `yt-dlp`
(run through `uvx`; `pip --user` is blocked by PEP 668 on this machine),
`--write-auto-subs --sub-langs "en.*"`, producing `novibes.en-orig.vtt`
(222,817 bytes).

**These are auto-generated captions.** The error signature is visible and
consistent: *"Jeff Huntley"* for **Geoffrey Huntley**, *"cloud code"* for
**Claude Code**, *"codeex"* for **Codex**, *"sub aent"* for **subagent**,
*"Eigor"* for a speaker's name, *"specri dev"* for **spec-driven dev**. Treat
every string below as `[QUOTED — ASR]`, **not** as clean verbatim text. For a
lesson, cite the timestamp and paraphrase, or quote with the ASR caveat
visible on the page.

`[QUOTED — ASR]` — **05:45 → 06:33**, video `rmvDxxNubIg`:

> "Um, **Jeff Huntley** uh did a lot of research on coding agents. Uh, he put
> it really well. Just the more you use the context window, the worse outcomes
> you'll get. **This leads to a concept I'm in a very very academic concept
> called the dumb zone.** So, you have your context window. **You have 168,000
> tokens roughly.** Some are reserved for output and compaction. This varies by
> model. Um, but we'll use **cloud code** as an example here. **Around the 40%
> line is where you're going to start to see some diminishing returns depending
> on your task.** Um, if you have too many MCPs in your coding agent, you are
> doing all your work in the dumb zone and you're never going to get good
> results. People talked about this. I'm not going to talk about that one.
> **Your mileage may vary. 40% is like it depends on how complex the task is,
> but this is kind of a good guideline.** Um so back to compaction or as I will
> call it from now on cleverly avoiding the dumb zone."

Four things fall out of that paragraph.

**(a) The "academic concept" line is real, and it is a disclaimer.** The
secondary distillation we had (`syntackle.com`) rendered it as clean prose:
*"This leads to an academic concept called the 'dumb zone'."* The talk's actual
caption text is the garbled *"a concept I'm in a very very academic concept
called the dumb zone"* — a false start plus an intensifier the distillation
dropped. **The substance survives the garble: Horthy presents "dumb zone" as an
existing concept he is invoking, not one he is minting.** This is exactly the
outcome the task anticipated: **it strengthens the settled verdict** — the
"coined by Dex Horthy" attribution fails, and it fails partly because *he does
not claim it*.

**(b) The origin of the 40% figure is this talk.** `[QUOTED — ASR]`, above:
*"Around the 40% line is where you're going to start to see some diminishing
returns depending on your task"*, immediately hedged with *"Your mileage may
vary. 40% is like it depends on how complex the task is, but this is kind of a
good guideline."* **The number is his, spoken, and explicitly offered as a soft
guideline.** Everything downstream that presents "40%" as a threshold, a rule,
or a measured finding has hardened a hedge.

**(c) The "~100K tokens" pairing still does not check out.** He says the window
is **"168,000 tokens roughly"**. 40% of 168K is ≈ 67K, not 100K. `[OUR
INFERENCE]` — the circulating "~100K / 40% of the window" pairing is not
derivable from this talk either; the two numbers come from different places and
were welded together downstream. Corpus 03's rejection of that pairing stands.

**(d) He credits the underlying observation to someone else — and it traces.**
`[QUOTED]` — Geoffrey Huntley, *"Ralph Wiggum as a 'software engineer'"*,
`article:published_time` **2025-07-14T04:22:55.000Z**,
<https://ghuntley.com/ralph/>:

> "The name of the game is that you only have approximately 170k of context
> window to work with. So it's essential to use as little of it as possible.
> **The more you use the context window, the worse the outcomes you'll get.**"

That is the sentence Horthy paraphrases at 05:51. **Chain closed:** Huntley
(2025-07-14, written) → Horthy (2025-12-02, spoken) → aggregators (2026).

A second Huntley figure, worth recording because it sits in the same band as
Pocock's: `[QUOTED]` — *"I dream about AI subagents; they whisper to me while
I'm asleep"*, `article:published_time` **2025-04-13T01:22:02.000Z**,
<https://ghuntley.com/subagents/>:

> "Claude 3.7's advertised context window is 200k, but I've noticed that **the
> quality of output clips at the 147k-152k mark.** Regardless of which agent is
> used, when clipping occurs, tool call to tool call invocation starts to fail"

`[OUR INFERENCE]` — Huntley's *147k–152k* and Pocock's *"around 125K-150K
tokens — though this is debated"* are **two independent practitioner
observations landing in the same band**, arrived at from different vantage
points (one loop-runner, one dictionary author). We have **no evidence of
influence in either direction** and must not assert one.

### 3.3 Where the "100,000 developer sessions" claim comes from

Corpus 03 rejected *"Dex Horthy analyzed 100,000 developer sessions"* as
unsourced. The talk explains the corruption. `[QUOTED — ASR]` — **00:44 →
01:00**:

> "I want to talk about one of my favorite talks from AI engineer in June. And I
> know we all got the update from **Eigor** yesterday, but they wouldn't let me
> change my slides. So, this is going to be about what **Eigor** talked about in
> June. uh basically that **they surveyed a 100,000 developers across all
> company sizes** and they found that most of the time you use AI for software
> engineering you're doing a lot of rework a lot of codebase churn uh and it
> doesn't really work well for complex tasks brownfield code bases"

`[OUR INFERENCE]` — the aggregators collapsed *"a different speaker's survey of
100,000 developers, which Horthy is citing in his opening"* into *"Horthy
analyzed 100,000 developer sessions."* **Different person, different number,
different unit (developers, not sessions).** The rejection stands and now has a
mechanism. `[unverified]` — the speaker's real name; the ASR gives "Eigor" and
we did **not** confirm it against the AI Engineer June programme.

### 3.4 Negative results — stated plainly

| Checked | Result |
| --- | --- |
| **`humanlayer/12-factor-agents`, entire repo** (downloaded via `codeload`, tarball extracted, full recursive grep) | `[NEGATIVE — VERIFIED]` **"dumb zone" absent. "smart zone" absent. "40%" absent.** This **refutes** `paddo.dev/blog/million-token-context/`, which states *"The 12-factor agents framework puts it bluntly: fill your context window past 40% and you enter the 'dumb zone.'"* The framework says no such thing. |
| **`humanlayer.dev/blog/advanced-context-engineering`** — the *blog* form of `ace-fca.md`, `article:published_time` **`08/29/2025`**, `article:author` **`Dex`** | `[NEGATIVE — VERIFIED]` **"dumb zone" absent from this artifact too.** Corpus 03 established this for the Markdown file; it now holds for the published post as well — **two independent HumanLayer artifacts by Horthy, neither containing the phrase.** What it *does* say, `[QUOTED]`: *"designing your ENTIRE WORKFLOW around context management, and keeping utilization in the **40%-60% range** (depends on complexity of the problem)."* |
| **`humanlayer.dev/blog/skill-issue-harness-engineering-for-coding-agents`** (2026-03-12) | Uses "dumb zone" three times **with no attribution and no definition**, deep-linking Horthy's talk at `t=355` both times it sources the phrase. `[QUOTED]`: *"plug too many MCP tools into your agent, and the context window fills up with tool descriptions, pushing you into the dumb zone much faster"*. |
| **The word "academic" anywhere else in the talk** | `[NEGATIVE — VERIFIED]` — exactly one occurrence, the one quoted in §3.2. |
| **"context rot" in the talk** | `[NEGATIVE — VERIFIED]` — absent. The Chroma framing is HumanLayer's blog, not the talk. |

### 3.5 What this does to the settled verdict

**Unchanged and reinforced:**

- The `dumb zone` / `smart zone` **definition** we teach remains **Pocock's**,
  quoted, from *Dictionary of AI Coding*, with his own figure *"around
  125K-150K tokens — though this is debated"*.
- **"Coined by Dex Horthy" still fails** — and now fails on the strongest
  possible evidence: **his own framing of it as a pre-existing concept.**
- The **"~100K / 40% of the window"** pairing remains **not Pocock's** and is
  **not derivable from Horthy's talk either**.

**One chronological fact that must be recorded, because it constrains the open
coinage question:** the pair *dumb zone* / *smart zone* is attested in a public
artifact **published 2025-12-02**. Corpus 03 records Pocock's dictionary repo as
created **2026-05-01** (initial commit `71b89e931`). **The terms were
circulating roughly five months before Pocock's written entry exists.**

**Be precise about what carries that date, because the two terms are not equally
well attested:**

- ***dumb zone*** — carried by **both** the chapter list (*"05:55 dumb zone
  concept"*) **and** the ASR at 05:56, 06:17, 06:31, 12:56. Solid.
- ***smart zone*** — carried by the chapter list (*"18:45 rpi: smart zone"*)
  and by the ASR at **07:49, 12:52, 13:27 and 18:46**. **The chapter list's authorship is
  `[unverified]`** (it sits on the AI Engineer channel, in Horthy's first
  person; see §8), so the load-bearing evidence for *smart zone* is the **ASR**,
  which is a sub-`[QUOTED]` tier. It is attested, but at a weaker tier than
  *dumb zone*.

That does **not** identify a coiner — Horthy is invoking, not minting, and we
have not found who he is invoking — but it does mean **"coinage open" should not
drift into "probably Pocock's."** Our defensible position is narrower and should
be stated that way in lessons: *Pocock supplies the definition we teach; the
coinage is unattributed and predates his written entry.*

---

## 4. Gap 2 — progressive disclosure (lesson 0018)

**Status: null result on the canonical sources. Three new primary attestations
of the term in the wild.**

**The library adds nothing to the canon.** Its only dedicated entry is
`mindstudio.ai/blog/progressive-disclosure-ai-agents-context-management` — a
vendor blog, community/secondary, superseded in every respect by what corpus 03
§4 already holds: **Jakob Nielsen, "Progressive Disclosure", NN/g, 3 December
2006** and Anthropic's own verbatim *"Progressive disclosure is the core design
principle that makes Agent Skills flexible and scalable."* **No new canonical
source was found. Stated as a null result rather than dressed up as novelty.**

What the sweep *did* add — three dated primary uses, useful if lesson 0018
wants to show the term has crossed from HCI into agent tooling:

1. **Cursor, vendor-primary.** `[QUOTED]` — <https://cursor.com/docs/skills>
   (fetched as `cursor.com/docs/skills.md`; the HTML route is JS-rendered and
   yields 26 bytes of text):

   > "### Progressive
   >
   > Skills load resources on demand, keeping context usage efficient."

   `[NEGATIVE — VERIFIED]` — Cursor uses the **adjective**, not the compound
   "progressive disclosure". Worth noting precisely because it shows the
   *property* propagating faster than the *name*.

2. **OpenAI, vendor-primary.** `[QUOTED]` — *Harness engineering*, 11 Feb 2026,
   <https://openai.com/index/harness-engineering/>:

   > "This enables progressive disclosure: agents start with a small, stable
   > entry point and are taught where to look next, rather than being
   > overwhelmed up front."

3. **Vivek Trivedy / LangChain.** `[QUOTED]` — 10 Mar 2026,
   <https://blog.langchain.com/the-anatomy-of-an-agent-harness/>:

   > "Skills are a harness level primitive that solve this via progressive
   > disclosure."

4. **Horthy uses it too**, and for the *docs-sharding* case rather than the
   skills case. `[QUOTED — ASR]` — **12:52 → 13:10**, `rmvDxxNubIg`:

   > "you're going to use all the smart zone just to learn how it works. And
   > you're not going to be able to do any good tool calling in the dumb zone.
   > So that's uh you can you can shard this down the stack. You can do
   > **they're just talking about progressive disclosure**. You could split this
   > up, right? You could just put a file in the root of every repo and then
   > like at every level you have like additional context based on if you're
   > working here, this is what you need to know."

`[OUR INFERENCE]` — the four uses above are **the same Parnas-style
information-hiding move applied to four different carriers** (skill bodies,
plan documents, tool schemas, nested docs). Corpus 03 §4 already frames this as
`[OUR ANALOGY]`; these attestations widen the evidence for it without changing
it.

---

## 5. Gap 3 — "harness engineering" (lesson 0020)

**Status: the settled verdict needs amending. Flagging, per instruction.**

The settled verdict is *"harness engineering is **Mitchell Hashimoto's**,
self-identified 5 Feb 2026. **Not Pocock's.**"* The second half is untouched and
still correct. **The first half does not survive this sweep.**

### 5.1 The dated chain, all fetched here

| Date | Artifact | Does it contain the compound "harness engineering"? |
| --- | --- | --- |
| **2025-09-23** | Vivek Trivedy, *The Claude Code SDK and the Birth of HaaS (Harness as a Service)*, `article:published_time` `2025-09-23T14:00:00.000Z`, `<meta name="author" content="Vivek Trivedy">` — <https://www.vtrivedy.com/posts/claude-code-sdk-haas-harness-as-a-service> | `[NEGATIVE — VERIFIED]` **No.** Zero occurrences. He coins **"Harness as a Service (HaaS)"**: `[QUOTED]` *"the core primitive for working with AI is shifting from the LLM API (chat style endpoints) to the Harness API (customizable runtimes). **I call this Harness as a Service (HaaS)**."* |
| **2025-12-02** | Dex Horthy, *No Vibes Allowed* (talk upload) | **Yes**, at **18:55–19:06**. `[QUOTED — ASR]`: *"Um, **if you really want a hypy word, you can call this harness harness engineering**, which is part of context engineering, and it's how you integrate with the integration points on **codeex**, claude, cursor, whatever. How you customize your codebase."* |
| **2026-02-05** | Mitchell Hashimoto, *My AI Adoption Journey*, `article:published_time` `2026-02-05T00:00:00.000Z` — <https://mitchellh.com/writing/my-ai-adoption-journey#step-5-engineer-the-harness> | **Yes**, once. `[QUOTED]`: *"**I don't know if there is a broad industry-accepted term for this yet, but I've grown to calling this "harness engineering."** It is the idea that anytime you find an agent makes a mistake, you take the time to engineer a solution such that the agent never makes that mistake again. **I don't need to invent any new terms here; if another one exists, I'll jump on the bandwagon.**"* |
| **2026-02-11** | OpenAI, *Harness engineering: leveraging Codex in an agent-first world*, **By Ryan Lopopolo, Member of the Technical Staff**, dateline **February 11, 2026** — <https://openai.com/index/harness-engineering/> | **Yes** (title + body). |
| **2026-03-10** | Vivek Trivedy, *The Anatomy of an Agent Harness*, LangChain blog, byline *"Vivek Trivedy March 10, 2026"* — <https://blog.langchain.com/the-anatomy-of-an-agent-harness/> | **Yes**, ×6. `[QUOTED]`: *"**Harness Engineering helps humans inject useful priors to guide agent behavior.**"* |
| **2026-03-10** | Martin C. Richards, *Building Your Own Agent Harness*, `article:published_time` `2026-03-10T00:00:00Z`, `<meta name="author" content="Martin C. Richards">` — <https://www.martinrichards.me/post/building_your_own_agent_harness/> | **Yes**, ×5, and it makes a **third, different attribution**. `[QUOTED]`: *"**Mitchell Hashimoto gave this practice a name in February 2026: harness engineering.** OpenAI and Martin Fowler picked it up."* |
| **2026-03-12** | Kyle (HumanLayer), *Skill Issue: Harness Engineering for Coding Agents*, `article:published_time` `3/12/2026`, `article:author` `Kyle`, byline on page *"Kyle · March 12, 2026"* — <https://www.humanlayer.dev/blog/skill-issue-harness-engineering-for-coding-agents> | **Yes**, and it makes an **attribution**. `[QUOTED]`: *"**Harness engineering, coined by Viv**, describes the practice of leveraging these configuration points to customize and improve your coding agent's output quality and reliability."* — where *Viv* links to `x.com/Vtrivedy10` and to both Trivedy posts above. |

**Two caveats on this table, so no reader over-reads it.**

1. **`2025-12-02` is a *publication* date, not a delivery date.** It is the AI
   Engineer channel's `uploadDate` for the video. The talk itself refers to
   *"one of my favorite talks from AI engineer **in June**"* and to *"the update
   from Eigor **yesterday**"*, so it was delivered at a later event and
   published afterwards. **The delivery date is `[unverified]` — not
   established in this sweep.** It does not affect the comparison being made
   (any plausible delivery date still precedes 2026-02-05), but the file must
   not let a reader infer the talk happened on 2 December.
2. **This table is not an exhaustive priority search.** It is the set of
   artifacts reachable from this library's link graph plus the links inside
   them. **No general search for the phrase's first appearance was run.** An
   earlier use than 2025-12-02 may well exist.

**Unchased lead:** Richards' further-reading list names *"Harness Engineering by
Martin Fowler / Thoughtworks"*. `martinfowler.com` is in this library's URL list
(a different article, `sdd-3-tools.html`). **Not fetched here** — worth one curl
before lesson 0020 ships, since a Fowler-hosted piece would be a high-trust
fourth data point.

### 5.2 What breaks, and what to say instead

1. **Hashimoto never claimed to coin it.** His sentence is a statement of
   *personal usage* plus an **explicit disclaimer of invention** — *"I don't
   need to invent any new terms here; if another one exists, I'll jump on the
   bandwagon."* Corpus 03 quotes the first half at `03:336` but the summary
   line at `03:26` reads **"coined by Mitchell Hashimoto, 5 Feb 2026"**. That
   summary **over-reads its own quote**.
2. **A dated primary artifact uses the compound ~2 months earlier.** Horthy,
   2025-12-02, and he calls it *"a hypy word"* — phrasing that presupposes the
   term was **already circulating** when he spoke.
3. **HumanLayer's own attribution ("coined by Viv") does not survive its own
   links.** The Trivedy post that *predates* everyone (2025-09-23) **does not
   contain the phrase**; the Trivedy post that *does* contain it (2026-03-10)
   **postdates Horthy, Hashimoto and OpenAI.** `[OUR INFERENCE]` — this reads
   as a colleague-credit, not a provenance claim. **Do not adopt it.**
   And note that **two artifacts published two days apart give two different
   coiners** — Richards (2026-03-10) says Hashimoto, HumanLayer (2026-03-12)
   says Trivedy. `[OUR INFERENCE]` — that divergence is itself the strongest
   available evidence that **no coinage was ever established**, and it is the
   honest thing to teach.
4. **OpenAI's post is now fetchable.** Corpus 03 (`03:340`, `03:929`) records
   HTTP 403 on 2026-07-31 and quotes nothing. **A plain `curl` with a browser
   `User-Agent` returned HTTP 200 (561,474 bytes) on 2026-08-01.** Byline and
   dateline above are `[METADATA VERIFIED]` from that fetch. Corpus 06 §4
   already treats this post; the 403 note in corpus 03 is stale.

**Proposed replacement verdict for lesson 0020** — narrower, and every clause
carries a fetched artifact:

> `harness engineering` has **no established coiner**. The earliest use **among
> the artifacts checked in §5.1** — which is not an exhaustive priority search —
> is **Dex Horthy's talk, published 2 December 2025**, where he introduces it as
> *"a hypy word"*, i.e. as already in circulation. **Mitchell Hashimoto**
> (5 Feb 2026) is the clearest *definition* — *"anytime you find an agent makes
> a mistake, you take the time to engineer a solution such that the agent never
> makes that mistake again"* — and he **explicitly declines to claim the
> coinage**. **OpenAI** (11 Feb 2026) made it a vendor-level term. Two March
> 2026 write-ups two days apart name **two different coiners** (Hashimoto;
> Trivedy), and neither attribution survives its own dates. **It is not Matt
> Pocock's.**

---

## 6. Gap 4 — the MRCR "smart zone ends" table

**Status: RESOLVED. It is not a benchmark artifact.**

The table lives in the README of a **personal GitHub project**:
<https://github.com/arpagon/pi-context-zone> (fetched raw, `main`, 5,502
bytes). It is a context-usage status bar for the Pi coding agent. `[QUOTED]`:

> "The \"dumb zone\" threshold varies by model. Here's how current frontier
> models handle long context on the MRCR v2 (8-needle) benchmark — the gold
> standard for measuring **reasoning quality** (not just retrieval) across
> context lengths:
>
> | Model | Context Window | MRCR @ 128K | MRCR @ 256K | MRCR @ 1M | Smart Zone Ends |"

Findings:

1. **The "Smart Zone Ends" column is the repo author's own derivation.** No
   citation is given for it anywhere in the README, and no benchmark publishes
   a column by that name. `[OUR INFERENCE]` — it is the author's judgement
   applied on top of MRCR scores. It must never be presented as measured data.
2. **The MRCR scores themselves carry no source in the README either.** The
   reference list names *"MRCR v2 (8-needle) benchmark — Multi-Round Coreference
   Resolution"* with **no URL, no publisher, no date**. `[unverified]` — the
   numbers were not traced to a vendor model card in this sweep.
3. **The README repeats the exact claim corpus 03 already rejected.**
   `[QUOTED]`: *"Dex Horthy (HumanLayer) coined the term \"Dumb Zone\" after
   analyzing 100,000+ developer sessions."* §3.3 shows where that came from and
   why it is wrong. **The repo is therefore secondary and unreliable for
   attribution**, however useful its tool may be.
4. Its own thresholds table is likewise its own: `[QUOTED]` *"| Smart → Warm |
   40% | Dex Horthy's inflection point, validated across models |"* — the
   phrase *"validated across models"* is **uncited**.

**Verdict: do not use this table in a lesson.** If lesson 0018 or 0020 wants a
degradation curve, use the sources already in corpus 03 §2 (Liu et al., *Lost
in the Middle*, TACL 2024) and Chroma's context-rot report, both of which
publish their methodology.

---

## 7. Contradictions with settled verdicts — the flag list

Surfaced here rather than buried, per the sweep's instructions. **This file
edits nothing.** The locators below are the exact lines a future session should
change, so nobody has to re-derive them.

**Edit targets, in priority order:**

| Target | Currently says | Should say |
| --- | --- | --- |
| `docs/research/03-agent-engineering.md:26` | *"**"Harness engineering":** coined by **Mitchell Hashimoto, 5 Feb 2026**, who explicitly says no industry term existed yet."* | No established coiner; Hashimoto declines the coinage in the very sentence quoted at `03:336`; earlier public use exists (Horthy, published 2025-12-02). See §5.2. |
| `docs/research/03-agent-engineering.md:332` | *"**Verdict: traced. Mitchell Hashimoto self-identifies as coining it…**"* | He self-identifies as *using* it, not coining it. |
| `docs/research/03-agent-engineering.md:340` and `:929` | *"this URL returned **HTTP 403** to automated fetch on 2026-07-31, so **no quote or number from it is reproduced here**"* | Stale. HTTP 200 on 2026-08-01 with a browser `User-Agent`; byline **Ryan Lopopolo**, dateline **February 11, 2026**. Corpus 06 §4 already supersedes. |
| `CLAUDE.md:70` (coinage table, `harness engineering` row) | *"**Mitchell Hashimoto**, self-identified, 5 Feb 2026"* | Keep **"Not Pocock's — do not attribute it to him"**; replace the coinage attribution with "no established coiner; see `13-library-sweep.md` §5". |
| `docs/research/03-agent-engineering.md:281` | *"the **40–60% number is Horthy's**; the phrase 'dumb zone' is not attested in his written source"* | Both halves still hold, **and** the **40%** figure now has a spoken primary source (talk at 06:09) plus an upstream credit to Geoffrey Huntley. See §3.2. |

| Settled verdict | What this sweep found | Action |
| --- | --- | --- |
| *"`harness engineering` — **coined by Mitchell Hashimoto, 5 Feb 2026**"* (`03-agent-engineering.md:26`) | Hashimoto **explicitly disclaims inventing it** in the same paragraph corpus 03 quotes; and **Horthy uses the compound on 2025-12-02**, calling it *"a hypy word"*. HumanLayer credits **Viv Trivedy**, whose earlier post does not contain the phrase. | **Amend.** Replacement wording in §5.2. The *"not Pocock's"* half is unaffected. |
| *`dumb zone` / `smart zone` — coinage **open**; Pocock defines it* | Still true, but the pair is now attested publicly on **2025-12-02**, ~5 months before Pocock's dictionary repo exists (`2026-05-01`). | **Tighten, don't overturn.** Say "Pocock supplies the definition; the coinage is unattributed and predates his entry." |
| *"is Horthy's 'an academic concept called the dumb zone' line in a primary artifact?"* (the open question) | **Yes** — talk `rmvDxxNubIg` at **05:55**, though the caption text is garbled (*"a concept I'm in a very very academic concept called the dumb zone"*). **He disclaims the coinage himself.** | **Strengthens the existing verdict.** Cite the timestamp; quote only with the ASR caveat visible. |
| *"OpenAI harness post — HTTP 403, no content verified"* (`03:340`, `03:929`) | **HTTP 200 on 2026-08-01** via `curl` with a browser `User-Agent`. Byline **Ryan Lopopolo**, dateline **February 11, 2026**. | **Stale note.** Corpus 06 §4 supersedes; the 403 caveat in corpus 03 can be retired when that file is next touched. |
| *"'~100K / 40% of window' is not Pocock's"* | Confirmed, and the **40% half is now sourced** — Horthy's talk, spoken, hedged. The **100K half remains untraced**: he says **168,000 tokens**, whose 40% is ≈67K. | **No change.** Corpus 03's rejection holds; §3.2(c) adds the mechanism. |
| *"Dex Horthy analyzed 100,000 developer sessions" — rejected* | Confirmed rejected, **and explained**: at 00:53 he cites *another speaker's* survey of *"a 100,000 developers"*. Different person, different unit. | **No change.** §3.3 adds the provenance of the error. |

---

## 8. What was fetched, and what was not

**Fetched and verified in this sweep** (all on 2026-08-01):

| Artifact | How | Result |
| --- | --- | --- |
| `humanlayer.dev/blog/skill-issue-harness-engineering-for-coding-agents` | `curl` + UA, 148,062 B | Date, author, "coined by Viv", 3× "dumb zone", full link graph |
| `youtube.com/watch?v=rmvDxxNubIg` (watch page) | `curl` + UA, 1,426,704 B | Title, channel, upload date, length, chapter list |
| Auto-captions for `rmvDxxNubIg` | `uvx yt-dlp --write-auto-subs`, 222,817 B | Full ASR transcript |
| `github.com/humanlayer/12-factor-agents` (whole repo) | `codeload` tarball + recursive grep | **Negative** on "dumb zone", "smart zone", "40%" |
| `humanlayer.dev/blog/advanced-context-engineering` | `curl` + UA, 131,940 B | **Negative** on "dumb zone"; 40–60% line confirmed |
| `raw.githubusercontent.com/arpagon/pi-context-zone/main/README.md` | `curl`, 5,502 B | MRCR table located and characterised |
| `mitchellh.com/writing/my-ai-adoption-journey` | `curl` + UA, 65,498 B | Date + full disclaimer sentence |
| `openai.com/index/harness-engineering/` | `curl` + UA, 561,474 B | **HTTP 200** (was 403); byline + dateline |
| `blog.langchain.com/the-anatomy-of-an-agent-harness/` | `curl` + UA, 168,853 B | Byline, date, 6× "harness engineering" |
| `vtrivedy.com/posts/claude-code-sdk-haas-harness-as-a-service` | `curl` + UA, 58,188 B | Date, author, **negative** on "harness engineering" |
| `ghuntley.com/ralph/`, `/subagents/`, `/gutter/` | `curl` + UA | Published dates + the two quoted figures |
| `cursor.com/docs/skills.md`, `/rules.md` | `curl` | Skills text; **`/docs/agent/tools.md` → HTTP 404** |
| `martinrichards.me/post/building_your_own_agent_harness/` | `curl` + UA, 24,505 B | Date, author, 5× "harness engineering", a third attribution |
| `arxiv.org/abs/{2412.10079, 2510.25445, 2508.10146, 2602.00180}` | `curl` + UA, `citation_title` / `citation_date` meta | Titles + dates in §2 |

**Deliberately not fetched** (tiered in §2, not gap-touching): the MCP/A2A
protocol cluster (~15 URLs), the `dev.to` and aggregator rows already on corpus
03's rejected list, the AWS host roots, `researchgate.net` (bare host, recorded
403), and `pmc.ncbi.nlm.nih.gov/articles/PMC12353729/`. `martinrichards.me` sat
on that list until the adversarial-review pass flagged it as gap-3-touching; it
was then fetched, and it changed §5.

**Known unverified, listed so nobody mistakes silence for confirmation:**

- `[unverified]` — the identity of the June AI Engineer speaker Horthy cites
  (ASR: *"Eigor"*). Not confirmed against the conference programme.
- `[unverified]` — whether the AI Engineer channel's description text was
  authored by Horthy or transcribed from his abstract by the conference. It is
  first-person, which is suggestive, not proof.
- `[unverified]` — the MRCR v2 numbers in `pi-context-zone`. No vendor model
  card was consulted.
- `[unverified]` — the standing of *IJSRST* (`mail.ijsrst.com`,
  `doi.org/10.32628/IJSRST25125127`). The DOI resolves; the journal's
  peer-review process was **not** assessed.
- `[unverified]` — who, if anyone, coined `dumb zone` before 2025-12-02. The
  question is now *older* than we thought, not answered.

---

## 9. Sources

1. Horthy, D. *No Vibes Allowed: Solving Hard Problems in Complex Codebases*. AI Engineer channel, uploaded **2025-12-02**. <https://www.youtube.com/watch?v=rmvDxxNubIg> — transcript quotes are **auto-generated captions**.
2. Horthy, D. *Advanced Context Engineering for Coding Agents*. HumanLayer blog, **2025-08-29**. <https://www.humanlayer.dev/blog/advanced-context-engineering>
3. HumanLayer. *12-factor-agents* (repository, `main`). <https://github.com/humanlayer/12-factor-agents>
4. Kyle (HumanLayer). *Skill Issue: Harness Engineering for Coding Agents*, **2026-03-12**. <https://www.humanlayer.dev/blog/skill-issue-harness-engineering-for-coding-agents>
5. Huntley, G. *Ralph Wiggum as a "software engineer"*, **2025-07-14**. <https://ghuntley.com/ralph/>
6. Huntley, G. *I dream about AI subagents; they whisper to me while I'm asleep*, **2025-04-13**. <https://ghuntley.com/subagents/>
7. Huntley, G. *autoregressive queens of failure*, **2025-04-21**. <https://ghuntley.com/gutter/>
8. Hashimoto, M. *My AI Adoption Journey*, **2026-02-05**. <https://mitchellh.com/writing/my-ai-adoption-journey>
9. Lopopolo, R. (OpenAI). *Harness engineering: leveraging Codex in an agent-first world*, **2026-02-11**. <https://openai.com/index/harness-engineering/>
10. Trivedy, V. *The Claude Code SDK and the Birth of HaaS (Harness as a Service)*, **2025-09-23**. <https://www.vtrivedy.com/posts/claude-code-sdk-haas-harness-as-a-service>
11. Trivedy, V. *The Anatomy of an Agent Harness*. LangChain blog, **2026-03-10**. <https://blog.langchain.com/the-anatomy-of-an-agent-harness/>
12. Richards, M. C. *Building Your Own Agent Harness*, **2026-03-10**. <https://www.martinrichards.me/post/building_your_own_agent_harness/>
13. arpagon. *pi-context-zone* (repository README, `main`). <https://github.com/arpagon/pi-context-zone>
14. Cursor. *Agent Skills*. <https://cursor.com/docs/skills>
15. arXiv metadata for `2412.10079`, `2510.25445`, `2508.10146`, `2602.00180` — titles and dates from each abstract page's `citation_title` / `citation_date` meta tags.

**Not used for any factual claim** (finding aid or rejected aggregator):
`ai-study-library` itself; `syntackle.com`; `spletzer.com`; `paddo.dev`;
`deepwiki.com`; all `dev.to` rows; `finance.biggo.com`; `mindstudio.ai`;
`optinampout.com`; `dzone.com`; `aqfer.com`; `epsilla.com`; `speakeasy.com`.
Where one of these is quoted above, it is quoted **as the object of a
correction**, never as evidence.
