# Matt Pocock — Primary Sources

Research pass completed **2026-07-31**. Purpose: give a learner the author's *own words* for the
ideas behind [`mattpocock/skills`](https://github.com/mattpocock/skills), rather than a paraphrase.

## How to read the evidence markers

| Marker | Meaning |
| --- | --- |
| **[QUOTE]** | Verbatim text authored by Matt, copied from a source I fetched. Byte-for-byte, no elision unless marked `…`. |
| **[PARA]** | My paraphrase of a page I fetched. His idea, my wording. |
| **[INFER]** | My inference — e.g. a video summary derived from title alone. Every one is labelled. |
| `[unverified URL]` | URL I could not confirm resolves. |

**URL verification method.** Every URL here was checked with
`curl -s -o /dev/null -L -w "%{http_code} %{url_effective}"`. A **negative control** was run first
(`https://aihero.dev/skills-obviously-not-a-real-skill-xyz123` → **404**, and
`https://www.aihero.dev/ai-coding-dictionary/definitely-not-a-real-term-xyz` → **404**), proving
aihero.dev returns real 404s and does **not** soft-404 into a 200. So a `200` on that host is genuine
evidence the page exists. **No `[unverified URL]` markers were needed** — everything listed resolved.

**Two important source-provenance facts, established by checking rather than assuming:**

1. **`SKILL.md` ≠ the published aihero.dev page.** The repo has *two* texts per skill:
   `skills/<bucket>/<name>/SKILL.md` (the agent-facing instruction) and
   `docs/<bucket>/<name>.md` (the human-facing explainer). **Only the latter is what
   `aihero.dev/skills-<name>` publishes** — verified by grepping the fetched HTML: the published
   `/skills-wayfinder` page contains the `docs/engineering/wayfinder.md` prose and does **not**
   contain the `SKILL.md` opening. Quotes below are therefore cited to whichever of the two they
   actually came from, with `SKILL.md` quotes pinned to a GitHub blob at commit `2ab9580`.
2. **Several aihero.dev article pages embed the full spoken transcript of the matching video** in
   the page source. Where a quote carries a `[MM:SS]` marker, that timestamp comes from the
   transcript published *on Matt's own page* — not from me watching the video.

**YouTube transcripts were not accessible.** Video descriptions *were*, in full, via
`ytInitialPlayerResponse.videoDetails.shortDescription` (the 160-char `<meta name="description">`
truncation was avoided). Chapter timestamps quoted below are Matt's own, published in his
descriptions. **No timestamp in this document is invented.**

---

## Section 1 — Canonical sources (all verified)

### 1.1 The repo

| Fact | Value | How verified |
| --- | --- | --- |
| Canonical URL | https://github.com/mattpocock/skills | `package.json` `repository.url`, `.claude-plugin/plugin.json` `repository`, and local `git remote -v` all agree |
| Description (GitHub) | **[QUOTE]** "Skills for Real Engineers. Straight from my .agents directory." | `gh api repos/mattpocock/skills` |
| Stars | **197,828** | `gh api` + raw `api.github.com`, 2026-07-31 |
| Forks | **17,030** | same |
| Watchers (subscribers) | 1,159 | same |
| Open issues | 258 | same |
| License | **MIT** | same |
| Created | 2026-02-03 | same |
| Last push | 2026-07-31T17:06:59Z | same |
| Default branch | `main`, HEAD = `2ab9580` (2026-07-28) | `gh api repos/mattpocock/skills/commits` |
| Tags published | `v1.1.0`, `v1.0.1`, `v1.0.0`, `mattpocock-skills@1.0.0` | `gh api .../tags` |

**Divergence of the local copy at `/home/rodrigo/Workspace/skills`: none on `main`.**
Local HEAD is `2ab9580` dated `2026-07-28T10:18:17+01:00`; upstream `main` HEAD is the same
`2ab9580` at `2026-07-28T09:18:17Z` (identical instant, different TZ rendering). The repo's
`pushed_at` is later (2026-07-31) because pushes to *other* branches update that field.

**One internal inconsistency worth knowing (a repo fact, not local staleness):**
`package.json` declares `"version": "1.1.0"` while `.claude-plugin/plugin.json` declares
`"version": "1.2.0"` — even though the repo's own `CLAUDE.md:12` instructs that the two be kept in
sync. Published git tags stop at `v1.1.0`.

### 1.2 Install instructions (from the live README, verified)

Two paths, and the README is explicit that you should pick **one**:

> **[QUOTE]** "Two ways in, two philosophies. **The [Claude Code plugin](https://code.claude.com/docs/en/plugins)** installs the whole set as a managed, read-only bundle that updates when I ship — you subscribe rather than fork. **[skills.sh](https://skills.sh/mattpocock/skills)** copies editable skill files into your project, so you can hack on them and make them your own. Pick one — installing both leaves you with every skill twice."

```bash
# Claude Code (official marketplace — nothing to add first)
claude plugins install mattpocock-skills
# or, in-session:
/plugin install mattpocock-skills

# Codex, other agents, and tinkerers
npx skills@latest add mattpocock/skills
```

Then, **once per repo**: `/setup-matt-pocock-skills`.

> **[QUOTE]** "**The installer lets you choose which skills to take — make sure `setup-matt-pocock-skills` is one of them.**"

### 1.3 Canonical sources table

| What | URL | Status | Notes |
| --- | --- | --- | --- |
| Skills repo | https://github.com/mattpocock/skills | 200 | MIT, 197.8k★ |
| Skills docs hub | https://www.aihero.dev/skills | 200 | title: "AI Skills for Real Engineers" |
| Skills catalog | https://www.aihero.dev/skills-catalog | 200 | |
| AI Hero home | https://www.aihero.dev/ | 200 | |
| AI Hero posts index | https://www.aihero.dev/posts | 200 | "AI Engineering Posts by Matt Pocock" |
| AI Hero open source | https://www.aihero.dev/open-source | 200 | |
| AI Hero courses | https://www.aihero.dev/courses | 200 | "Learn with Matt \| AI Hero" |
| Newsletter (skills) | https://www.aihero.dev/s/skills-newsletter | 200 → `/skills/subscribe` | the `homepage` field in `plugin.json` |
| Newsletter (general) | https://www.aihero.dev/newsletter | 200 | |
| Discord | https://www.aihero.dev/discord | 200 | |
| **AI Coding Dictionary** (repo) | https://github.com/mattpocock/dictionary-of-ai-coding | 200 | 3,314★, no license file, created 2026-05-01 |
| AI Coding Dictionary (site) | https://www.aicodingdictionary.com/ | 200 | vanity domain; **entry pages 404 here** — they live on aihero.dev |
| Dictionary on aihero | https://www.aihero.dev/ai-coding-dictionary | 200 | per-entry pattern `…/ai-coding-dictionary/<slug>` **works** |
| **Sandcastle** (AFK harness) | https://github.com/mattpocock/sandcastle | 200 | 7,134★, MIT. "Orchestrate sandboxed coding agents in TypeScript with sandcastle.run()" |
| YouTube channel | https://www.youtube.com/@mattpocockuk | 200 | channelId `UCswG6FSbgZjbWtdf_hMLaow` |
| YouTube RSS (for tracking) | https://www.youtube.com/feeds/videos.xml?channel_id=UCswG6FSbgZjbWtdf_hMLaow | 200 | |
| X / Twitter | https://x.com/mattpocockuk | 200 | linked from his own video descriptions |
| GitHub profile | https://github.com/mattpocock | 200 | |
| Total TypeScript (his older TS work) | https://www.totaltypescript.com/ | 200 | pre-AI era; see §3.6 |
| skills.sh listing | https://skills.sh/mattpocock/skills | 200 → `www.skills.sh/...` | third-party installer/registry |

---

## Section 2 — Per-skill published docs pages

**The `https://aihero.dev/skills-<skill-name>` pattern claimed in the repo's `CLAUDE.md` line 18 is
REAL and works for all 22 promoted skills.** Every one returns `200` and redirects to the `www.`
canonical host. Negative control (a fake slug on the same prefix) returns `404`, so these 200s mean
something.

These are the highest-trust "further reading" links for lessons.

### Engineering

| Skill | Published URL | Status |
| --- | --- | --- |
| ask-matt | https://www.aihero.dev/skills-ask-matt | ✅ 200 |
| grill-with-docs | https://www.aihero.dev/skills-grill-with-docs | ✅ 200 |
| triage | https://www.aihero.dev/skills-triage | ✅ 200 |
| improve-codebase-architecture | https://www.aihero.dev/skills-improve-codebase-architecture | ✅ 200 |
| setup-matt-pocock-skills | https://www.aihero.dev/skills-setup-matt-pocock-skills | ✅ 200 |
| to-spec | https://www.aihero.dev/skills-to-spec | ✅ 200 |
| to-tickets | https://www.aihero.dev/skills-to-tickets | ✅ 200 |
| implement | https://www.aihero.dev/skills-implement | ✅ 200 |
| wayfinder | https://www.aihero.dev/skills-wayfinder | ✅ 200 |
| prototype | https://www.aihero.dev/skills-prototype | ✅ 200 |
| diagnosing-bugs | https://www.aihero.dev/skills-diagnosing-bugs | ✅ 200 |
| research | https://www.aihero.dev/skills-research | ✅ 200 |
| tdd | https://www.aihero.dev/skills-tdd | ✅ 200 |
| domain-modeling | https://www.aihero.dev/skills-domain-modeling | ✅ 200 |
| codebase-design | https://www.aihero.dev/skills-codebase-design | ✅ 200 |
| code-review | https://www.aihero.dev/skills-code-review | ✅ 200 |
| resolving-merge-conflicts | https://www.aihero.dev/skills-resolving-merge-conflicts | ✅ 200 |

### Productivity

| Skill | Published URL | Status |
| --- | --- | --- |
| grilling | https://www.aihero.dev/skills-grilling | ✅ 200 |
| grill-me | https://www.aihero.dev/skills-grill-me | ✅ 200 |
| handoff | https://www.aihero.dev/skills-handoff | ✅ 200 |
| teach | https://www.aihero.dev/skills-teach | ✅ 200 |
| writing-great-skills | https://www.aihero.dev/skills-writing-great-skills | ✅ 200 |

**22 / 22 working. 0 broken.**

> **Which repo file backs which URL.** The published page renders `docs/<bucket>/<skill>.md`, e.g.
> https://www.aihero.dev/skills-to-tickets ← `docs/engineering/to-tickets.md`. It does **not** render
> `skills/<bucket>/<skill>/SKILL.md` — that is a different, agent-facing document with different
> prose. Verified by grep against the fetched HTML for `/skills-wayfinder`: the page contains
> `docs/engineering/wayfinder.md`'s "Every ticket is **HITL**…" paragraph, and contains **no** trace
> of `SKILL.md`'s "A loose idea has arrived…" opening. **When quoting, check which file you have.**

### 2.1 Bonus: the AI Coding Dictionary entry pattern also works

`https://www.aihero.dev/ai-coding-dictionary/<slug>` — verified 200 for `smart-zone`, `harness`,
`grilling`, `afk`, `attention-degradation`, `attention-budget`, `handoff`, `skill`, `spec`, `ticket`,
`primary-source`, `progressive-disclosure`, `prototyping`, `human-in-the-loop`, `context-pointer`.
Fake slug → 404.

**This dictionary is the single best primary source for Matt's vocabulary** — it is where he
*defines* his terms in prose, and it is on GitHub as plain Markdown at
`https://raw.githubusercontent.com/mattpocock/dictionary-of-ai-coding/main/dictionary/<Term>.md`
(76 entries).

---

## Section 3 — Annotated video list (most relevant first)

All video IDs below were fetched individually; each returned HTTP 200 with `"author":"Matt Pocock"`
(except #2, noted) and an `uploadDate` from the page's structured data. Titles are the **original
English** titles — YouTube serves auto-translated titles by locale, so these were pulled with
`Accept-Language: en-US` plus the channel RSS feed. Descriptions are the **full**
`shortDescription`, with promotional footers trimmed (trims marked).

### 3.1 Core — the workflow and the skills repo

**1. `mattpocock/skills`: A complete AI Coding workflow, end-to-end** — 2026-07-16, 17m17s
https://www.youtube.com/watch?v=M6mYodf0dJM
> **[QUOTE, full description]** "Complete tutorial for my skills repo with 170,000 stars. Learn how
> to install, set up, and use the main workflow: from initial setup through grill-with-docs
> interviews, specs, tickets, and implementation with code review."

His chapters: `0:54` Installing the skills repo · `4:34` Running setup and configuration · `6:52`
Getting started with Ask Matt · `8:23` Main workflow explained · `8:31` Grill with docs interview
session · `10:08` Creating specs and tickets · `13:51` Implementation and code review.
**Why it matters:** the single best entry point — the whole chain in one sitting.

**2. Full Walkthrough: Workflow for AI Coding — Matt Pocock** — 2026-04-24, **1h36m30s**
https://www.youtube.com/watch?v=-QFHIoCo-Ko
Channel: **AI Engineer** (conference channel, *not* @mattpocockuk) — verified `"author":"AI Engineer"`.
> **[QUOTE, official video description]** "A hands-on workshop covering the full lifecycle of
> AI-assisted development, from turning ambiguous requirements into agent-ready plans to running
> autonomous coding agents that ship production features. You'll learn to stress-test vague briefs
> into structured PRDs, slice work into thin "tracer bullet" vertical slices, and run an AI agent
> with TDD. You'll watch it select tasks, write tests, implement code, and commit. You'll then refine
> your prompts based on where it struggles, graduate to fully autonomous (AFK) runs, and learn to
> design codebases that maximize agent effectiveness."

**Why it matters:** the longest single artifact of the philosophy, and the description confirms the
whole vocabulary chain — PRD → tracer-bullet vertical slices → TDD → AFK → codebase design.
*Caveat: written on the conference channel, so possibly from Matt's submitted abstract rather than by
Matt directly.*

**3. New Skills! v1.1 brings /wayfinder, /research, /implement, /to-spec, /to-tickets** — 2026-07-08, 15m11s
https://www.youtube.com/watch?v=A8mokin_YOs
> **[QUOTE, full description]** "Skills v1.1 is here with major updates including renamed flow skills
> (/to-spec, /to-tickets), improved grilling, a complete development lifecycle, and the new Wayfinder
> skill for planning large projects."

His chapters: `0:37` /to-spec, /to-tickets · `2:34` Grilling skill improvements · `4:06` Complete
development lifecycle flow · `6:45` Code review with refactoring smells · `7:52` Introducing
Wayfinder for large plans · `11:21` Supporting skills: research and prototype · `12:12` TDD skill
updates · `13:15` **Migration guide**.
**Why it matters:** explains the v1.1 renames — exactly the vocabulary shift that makes older
material confusing.

**4. 5 Claude Code skills I use every single day** — 2026-03-16, 16m42s
https://www.youtube.com/watch?v=EJyuu6zlQCg
> **[QUOTE, full description]** "Learn the 5 AI agent skills I use every day to steer Claude Code and
> dramatically improve code quality. Master process-driven development with LLMs through practical
> examples and real workflows."

His chapters: `1:18` /grill-me · `3:55` **/write-a-prd** · `6:00` **/prd-to-issues** · `8:29` /tdd ·
`12:04` /improve-codebase-architecture.
**Why it matters:** shows the *pre-v1.1 names* — `/write-a-prd` and `/prd-to-issues` are what became
`/to-spec` and `/to-tickets`. Useful for decoding older writing about the repo.
Companion article: https://www.aihero.dev/5-agent-skills-i-use-every-day (200).

**5. How I use Claude Code for real engineering** — 2025-10-27, 10m11s
https://www.youtube.com/watch?v=kZ-zzHVUrO4
> **[QUOTE, full description]** "In this video, I walk through my complete workflow for tackling large
> coding projects using Claude Code's plan mode. I demonstrate how to start with a rough dictated
> prompt, use plan mode to explore the codebase and generate clarifying questions, and break complex
> work into multi-phase plans that can span multiple context windows. I show my custom rules
> configuration that keeps plans concise and adds unresolved questions, how to monitor context usage
> throughout implementation, and my strategy of storing plans as GitHub issues to preserve them
> across context resets. This approach combines upfront planning with aggressive auto-accept during
> implementation phases, allowing AI to handle substantial features while maintaining control and
> code quality."

**Why it matters:** the earliest full statement of the workflow, *before the skills existed*. Every
later skill is visible here in embryo — clarifying questions (→ grilling), plans as GitHub issues
(→ to-tickets/wayfinder), context monitoring (→ smart zone/handoff).

### 3.2 Context engineering / the dumb zone

**6. What is the dumb zone?** — 2026-07-20, 1m47s
https://www.youtube.com/watch?v=sOd7svdu_1I
> **[QUOTE, full description]** "Learn how to work smarter with AI models by understanding attention
> degradation and context window optimization. Discover why your million-token context window isn't
> as useful as it seems, and learn practical strategies to get better results while spending fewer
> tokens."

**Why it matters:** the shortest direct statement of the term. Pair with the dictionary entry (§4),
which has the actual prose definition.

**7. Most devs don't understand how context windows work** — 2025-10-22, 9m33s
https://www.youtube.com/watch?v=-uW5-TaVXu4
> **[QUOTE, full description]** "A deep dive into the context window - the most important constraint
> when using AI coding agents. Learn what makes up a context window (input and output tokens), why
> models have limits, and the critical "lost-in-the-middle" problem that causes models to
> deprioritize information buried in long conversations. Discover practical strategies for managing
> context effectively in Claude Code, including when to clear vs. compact conversations, why bigger
> context windows aren't always better, and how MCP servers can bloat your context. Understanding
> context windows is the key skill that separates developers who get great results from coding agents
> versus those who struggle."

**Why it matters:** the mechanism underneath the dumb zone, at length.

**8. Kill your MEMORY.md** — 2026-07-17, 1m29s
https://www.youtube.com/watch?v=A0scuiiGBC4
> **[QUOTE, full description]** "Learn why agent memory is more harmful than helpful. Matt Pocock
> explains why you should disable auto-memory in Claude Code and keep your AI agent completely
> stateless for predictability."

**9. Claude Code's system tools are SO BLOATED** — 2026-07-16, 1m38s
https://www.youtube.com/watch?v=oLx4yCbeklQ
> **[QUOTE, full description]** "Learn how to dramatically reduce unnecessary bloat in Claude Code's
> system prompt by disabling unused features and tools. In this video, I show you how to drop your
> system prompt size from 25,000 tokens down to just 8,000 by customizing your global settings.json
> file."

Companion article: https://www.aihero.dev/how-to-kill-the-bloat-in-claude-codes-system-prompt (200).
**Why it matters:** context budgeting applied to the *harness itself*, before you write a prompt.

### 3.3 Grilling

**10. I stopped using /grill-me for coding. Here's what I use instead:** — 2026-05-14, 15m16s
https://www.youtube.com/watch?v=6BB6exR8Zd8
> **[QUOTE, full description]** "Introducing /grill-with-docs: the evolution of /grill-me. This skill
> combines conversational AI interviewing with domain-driven design to help you establish shared
> language with your AI coding partner, resulting in better alignment and more efficient development."

His chapters: `0:56` Where /grill-me Fails · `2:47` /ubiquitous-language · `4:32` /grill-with-docs ·
`7:27` ADR's · `8:25` Demo · `12:24` Benefits · `13:26` Is /grill-me dead?
**Why it matters:** the *argument* for why grilling alone isn't enough, and why a shared language
belongs in the loop.

**11. 9 Things People Get Wrong With My /grill-\* skills** — 2026-05-25, 13m28s
https://www.youtube.com/watch?v=UzMNBN6xLLA
> **[QUOTE, full description]** "Learn how to master the /grill-with-docs skill and avoid common
> mistakes that waste context and planning time. This video covers nine key failure modes and how to
> fix them."

His chapters: `1:28` Low vs high fidelity questions · `3:44` Managing scope correctly · `5:41` Being
active not passive · `6:54` Preserving grilling session value · `8:40` Using smart models for
grilling · `10:24` Running parallel grilling sessions.
Companion article: https://www.aihero.dev/things-people-get-wrong-with-grill-me-and-grill-with-docs (200).
**Why it matters:** the best troubleshooting source. Failure modes teach faster than the happy path.

**12. I'm thinking about changing my most popular skill** — 2026-07-16, 1m20s
https://www.youtube.com/watch?v=U832hShMVnc
> **[QUOTE, full description]** "Matt Pocock is considering modifying his famous /grill-me skill to
> ask all questions at once instead of one at a time. Should he change the default behavior or create
> a new skill?"

**13. This change makes /grill-me SO MUCH BETTER** — 2026-07-17, 2m05s
https://www.youtube.com/watch?v=tLyfDIt9wHg
> **[QUOTE, full description]** "Exploring /grill-me new batch-based question system. Learn how Matt
> is improving the skill by asking questions in rounds instead of one-by-one, reducing wait times and
> context switching while handling question dependencies."

**Note for the learner:** #12 and #13 are the live debate behind the repo's `in-progress/batch-grill-me`
skill. The shipped `grilling` skill still says "one at a time"; the batch variant is unshipped.

**14. Using /grill-me for interviews?!** — 2026-07-20, 1m05s
https://www.youtube.com/watch?v=5hYsBUMmr-I
> **[QUOTE, full description]** "Discover how /grill-me is being used for technical interviews to
> assess AI skills and strategic thinking. Learn why this tool demands clear communication, critical
> thinking, and the long-term mindset needed when working with AI."

### 3.4 Codebase design, TDD, and the seam question

**15. Your codebase is NOT ready for AI (here's how to fix it)** — 2026-02-26, 8m50s
https://www.youtube.com/watch?v=uC44zFz7JSM
> **[QUOTE, full description]** "Most codebases aren't designed for AI. Learn why software
> architecture matters more than ever and how deep modules create the structure AI needs to work
> effectively."

Companion article (**strongly recommended — it embeds the full transcript**):
https://www.aihero.dev/how-to-make-codebases-ai-agents-love (200, dated 2026-02-26).

**16. Red Green Refactor is OP With Claude Code** — 2026-02-23, 5m19s
https://www.youtube.com/watch?v=hYZdIwFIy-c
> **[QUOTE, full description]** "Learn how to get better results from coding agents using Test Driven
> Development and the red-green refactor cycle. Discover why this 30-year-old software practice is
> perfect for AI-powered coding."

**Why it matters:** the origin of `/tdd`'s argument.

**17. How To De-Slop A Codebase Ruined By AI (with one skill)** — 2026-04-29, 11m19s
https://www.youtube.com/watch?v=3MP8D-mdheA
> **[QUOTE, full description]** "AI has accelerated software entropy, making codebases fall apart
> faster than ever. Learn how to rescue a messy codebase using deep modules, good architecture, and
> AI-assisted refactoring fundamentals."

**Why it matters:** `/improve-codebase-architecture`'s reason for existing.

**18. Do software fundamentals still matter?** — 2026-07-16, 2m12s
https://www.youtube.com/watch?v=eEjBhVI9Qok
> **[QUOTE, full description]** "Do Software Fundamentals Still Matter in the Age of AI? Discover why
> code quality, software entropy, and good design patterns are more important than ever for AI agents.
> Learn how to build codebases that AI loves to work in."

**19. My #1 book recommendation for strategic programming** — 2026-07-16, 1m05s
https://www.youtube.com/watch?v=t34UuBxB2YQ
> **[QUOTE, full description]** "Learn Strategic Programming From The Pragmatic Programmer: Discover
> how classic software engineering fundamentals remain essential in the AI era. Explore concepts like
> tracer bullets and programming by coincidence that shape how developers think long-term about
> codebases."

**The book is *The Pragmatic Programmer* (Hunt & Thomas)** — he links it in the description.
*(Correction of an earlier draft of this file, which inferred Ousterhout. It is not Ousterhout.)*
**Why it matters:** this is his own on-record attribution for **tracer bullets** → §4.

**20. There is no such thing as greenfield** — 2026-07-21, 1m27s
https://www.youtube.com/watch?v=0l7zOp260yc
> **[QUOTE, full description]** "Learn why the difference between greenfield and brownfield codebases
> is largely meaningless in modern software development. Matt explains how real-world constraints,
> existing code conventions, and documentation design make both types of projects more similar than
> you'd think."

### 3.5 Planning, specs, AFK, handoff

**21. /wayfinder: Nothing is too big to plan anymore** — 2026-07-30, 15m09s
https://www.youtube.com/watch?v=F3lL98Pj90o
> **[QUOTE, full description]** "Wayfinder is an AI planning skill that orchestrates massive projects
> across multiple agent sessions. Learn how to map foggy ideas into concrete execution plans with
> research, prototyping, and task management built-in."

His chapters: `0:00` Problems with existing planning tools · `2:10` How Wayfinder maps work · `4:30`
Tracking decisions in your issue tracker · `5:39` Setting up and working through maps · `7:40` Ticket
types and blocking relationships · `9:44` Creating specs and tickets from maps · `11:38` When to use
Wayfinder.

**22. Don't waste time on specs: /prototype instead** — 2026-07-23, 10m59s
https://www.youtube.com/watch?v=n0VhIVtviC0
> **[QUOTE, full description]** "Stop relying on detailed specs. Learn why prototyping with AI is now
> cheaper and more effective than ever before. Discover the Prototype and Wayfinder skills for
> high-fidelity design discussions."

His chapters: `0:00` The problem with spec-driven development · `1:29` **Understanding fidelity in
design** · `2:55` Prototyping in Wayfinder · `3:57` Building a search bar prototype · `6:15` Iterating
on the prototype · `8:00` From prototype to production · `8:53` Prototyping beyond frontend · `10:03`
Why higher fidelity matters.
He links **Shape Up** by Ryan Singer (https://basecamp.com/books/shape-up) in the description.
**Why it matters:** the deliberate counterweight to spec-driven development, in his own framing.

**23. I Open-Sourced My Own AFK Software Factory** — 2026-04-30, 11m25s
https://www.youtube.com/watch?v=E5-QK3CDVQM
> **[QUOTE, full description]** "Learn how to build AI coding agents that run completely AFK in
> isolated sandboxes. In this video, I introduce Sandcastle, a TypeScript library that lets you
> orchestrate Claude Code and other agents to automatically pick up tasks, implement features, review
> code, and merge it back to main—all without constant permission requests. Discover how to set up
> agents to run in parallel with proper sandboxing, handle complex workflows, and scale your
> development velocity."

Repo: https://github.com/mattpocock/sandcastle (7,134★, MIT).

**24. /handoff is my new favourite skill** — 2026-05-21, 12m24s
https://www.youtube.com/watch?v=dtAJ2dOd3ko
> **[QUOTE, full description]** "Learn how to hand off your AI coding sessions to separate agents
> using the /handoff skill. In this deep dive, I explain why I built this skill, how it differs from
> /compact, and show real-world patterns for managing multiple concurrent AI sessions."

His chapters: `1:36` Context windows and compact explained · `4:49` **Why handoff differs from
compact** · `6:14` Using handoff during grilling sessions · `7:20` Handing off to prototype · `9:23`
Cross-agent workflow benefits · `9:50` Skill design decisions.

**25. The 7 phases of AI-driven development** — 2026-03-03, 8m26s
https://www.youtube.com/watch?v=Ah9p7v7nJWg
> **[QUOTE, full description]** "The 7 phases of AI-driven development: idea, research, prototype,
> PRD, implementation planning, execution, and QA. Learn how to ship great work with AI coding
> assistants like Claude Code."

Companion article: https://www.aihero.dev/my-7-phases-of-ai-development (200).

**26. Ship working code while you sleep with the Ralph Wiggum technique** — 2026-01-05, 16m23s
https://www.youtube.com/watch?v=_IK18goX4X8
> **[QUOTE, full description, promo footer trimmed]** "Discover Ralph Wiggum, a devilishly simple
> approach to AI coding agents that uses just a for loop instead of complex orchestration systems.
> I'll show you the exact bash script I use to let Claude work through my entire backlog overnight,
> how to structure tasks as a PRD, and why keeping changes small with robust feedback loops
> (TypeScript, tests, CI) is the key to actually shipping working code. This technique mimics how real
> engineers work: pick a task, complete it, commit it, repeat."

**Ralph Wiggum is explicitly NOT his coinage** — he links the original in the description:
https://ghuntley.com/ralph/ (Geoffrey Huntley). He also links Anthropic's
["Effective harnesses for long-running agents"](https://www.anthropic.com/engineering/effective-harnesses-for-long-running-agents).
Companion article: https://www.aihero.dev/getting-started-with-ralph (200).

**27. Do you even need human review?** — 2026-07-20, 1m59s
https://www.youtube.com/watch?v=Yn8h5Ip-L9c
> **[QUOTE, full description]** "Learn how to strategically shift human review checkpoints in
> AI-powered development workflows. Discover when to involve humans early for alignment and when to
> let AI handle work autonomously before final review."

**28. Burn through the backlog from hell with /triage** — 2026-05-07, 10m17s
https://www.youtube.com/watch?v=MzWIIlx0Gpc
> **[QUOTE, full description]** "Learn how to use the Triage skill to manage GitHub issues and
> backlogs at scale. Discover how to turn messy human ideas into actionable tasks for AI agents using
> state machines and labels."

Companion article: https://www.aihero.dev/burn-through-your-backlog-with-my-triage-skill (200).

**29. Building a REAL feature with Claude Code: every step explained** — 2026-03-18, **44m16s**
https://www.youtube.com/watch?v=hX7yG1KVYhI
> **[QUOTE, full description]** "In this video, I walk through a real-world example of using Claude
> Code with my course video manager. I'll show you my entire workflow: from initial feature
> brainstorming through to autonomous implementation and QA testing."

Companion article: https://www.aihero.dev/real-world-feature-build-with-claude-code (200).
**Why it matters:** the longest unedited worked example on his own channel.

### 3.6 Also on the channel (verified, lower priority for this study)

| Date | Title | URL |
| --- | --- | --- |
| 2026-07-17 | Framework Hell, Tutorial Hell... now Skill Hell | https://www.youtube.com/watch?v=32LyZyFQhCQ |
| 2026-07-16 | My /teach skill is still insane | https://www.youtube.com/watch?v=glaIO6OYh74 |
| 2026-06-08 | Learn anything with the /teach skill | https://www.youtube.com/watch?v=s5T5oQJcJ6U |
| 2026-05-28 | Can Cursor's HARDCORE Review Skill Stop The Slop? | https://www.youtube.com/watch?v=mh5XZ-L5SFQ |
| 2026-05-13 | Anthropic's "dedicated monthly credit" is actually a huge cut | https://www.youtube.com/watch?v=lNOQaakmyDU |
| 2026-05-12 | New Skills! /handoff, /prototype, /review and /writing-\* \| Skills Changelog | https://www.youtube.com/watch?v=DNqsMXH6Eog |
| 2026-03-27 | Never Trust An LLM | https://www.youtube.com/watch?v=9VNG0h4pLh0 |
| 2026-03-23 | Claude Code tried to improve /init... Is it any better? | https://www.youtube.com/watch?v=llwTBpPqo9A |
| 2026-02-25 | How to actually force Claude Code to use the right CLI (don't use CLAUDE.md) | https://www.youtube.com/watch?v=3CSi8QAoN-s |
| 2026-02-24 | Never Run claude /init | https://www.youtube.com/watch?v=9tmsq-Gvx6g |
| 2026-02-21 | I'm using claude --worktree for everything now | https://www.youtube.com/watch?v=yv8VZpov8bk |
| 2026-01-15 | I was an AI skeptic. Then I tried plan mode | https://www.youtube.com/watch?v=WNx-s-RxVxk |
| 2026-01-12 | Frontend is HARDER for AI than backend (here's how to fix it) | https://www.youtube.com/watch?v=pSritFeoYFo |
| 2025-10-24 | AI SDK 6 is SWEET | https://www.youtube.com/watch?v=YCrj0E_P6ls |

**On the older TypeScript content.** Matt's pre-AI reputation is TypeScript teaching
(https://www.totaltypescript.com/, 200). **[INFER]** — I did **not** find an older TypeScript/testing
video of his that argues the *seams and interfaces* case the way `codebase-design` does. That
vocabulary traces to **Ousterhout**, not to his TypeScript catalogue. The nearest in-house sources
are the `codebase-design` skill and the *How To Make Codebases AI Agents Love* article. Cite Total
TypeScript as *context for who he is*, not as the source of these ideas.

---

## Section 4 — Coinage ledger

Three verdict values, because *appearing in his repo proves he uses and defines a term, not that he
coined it*:

- **HIS** — appears to originate with him; no earlier general usage found.
- **HIS USE, PRIOR ART** — he defines and popularises it, but the concept/name predates him (cited).
- **NOT HIS** — general industry/community vocabulary he adopted.

| Term | Verdict | His definition (quoted) | Source URL | Confidence |
| --- | --- | --- | --- | --- |
| **"dumb zone" / "smart zone"** | **HIS** | **[QUOTE]** "Early in a *session* the *agent* is in a "smart zone" — sharp, focused, recall is good. As the session grows it drifts into a "dumb zone": sloppier, forgetful, more mistakes — and more faithfulness *hallucinations*. Same *model*, same *harness* — just more *context*. The felt effect of *attention degradation*. On frontier models, the dumb zone commonly begins around 125K-150K *tokens* — though this is debated. *Clear* or *compact* when the session bloats; don't push through." | Dictionary entry: https://www.aihero.dev/ai-coding-dictionary/smart-zone · raw source: `https://raw.githubusercontent.com/mattpocock/dictionary-of-ai-coding/main/dictionary/Smart%20zone.md` · video: https://www.youtube.com/watch?v=sOd7svdu_1I | **High** — he defines it, gives usage dialogue, and dedicates a video to it. No pre-existing industry use of the pair found. |
| **"harness"** | **NOT HIS** — he defines it precisely, doesn't coin it | **[QUOTE]** "Everything around the *model* that turns it into an *agent*: *tools*, *system prompt*, *context-window management*, permissions, hooks. **Claude.ai** and **Claude Code** run on the same model but behave differently because their harnesses differ." | https://www.aihero.dev/ai-coding-dictionary/harness · raw: `.../dictionary/Harness.md` | **High** for the definition. "Harness" is standard AI-agent vocabulary — Anthropic itself publishes ["Effective harnesses for long-running agents"](https://www.anthropic.com/engineering/effective-harnesses-for-long-running-agents), which **Matt links in his own video description** (#26). His contribution is the crisp model/harness/environment/agent split, not the word. |
| **"harness engineering"** (as a named practice) | **`[unverified — not found in Matt's public sources]`** | — | — | See §4.1 for the five places I looked. Third-party write-ups foreground "the harness" when describing him, but **he does not use the compound phrase**. Do not attribute it to him. |
| **The model/harness/environment/agent split** | **HIS** framing | **[QUOTE, X post]** "4 of the most confusing terms in AI, defined: / Model: a blob of parameters, written during training. Does next-token prediction and nothing else. Stateless. / Harness: everything around the model that turns it into an agent: tools, system prompt, context window management, etc. / Environment: the world the agent acts on. Anything outside the harness that the agent perceives and acts on via tools. / Agent: a model, harnessed, in an environment. / — / Opus is a model. / Claude Code and Claude Web are different agents, because their harnesses differ - even though the models are the same. / The file system is an environment. MCP servers add tools to the environment." | https://x.com/mattpocockuk/status/2050456062520615131 (URL verified 200; text retrieved via the `api.fxtwitter.com` mirror, since x.com serves a JS shell) | **Medium-high** — text verified via a mirror rather than x.com HTML directly. |
| **"grilling"** (as a named practice) | **HIS** for the name in AI coding; **prior art acknowledged by him** for the practice | **[QUOTE, dictionary]** "A technique for developing a *design concept* with an *agent*: the agent interviews the user Socratically, one decision at a time, proposing a recommended answer for each. Slows the rush to a finished plan — no *handoff artifact* is written until the concept stabilises. … The technique exists because agents fill gaps silently. Asked to write a *spec* from a two-line prompt, the agent doesn't stop at the decisions you haven't made — it picks defaults and writes them in. … Grilling inverts this — instead of guessing, the agent has to ask." **[QUOTE, article]** "Before AI came along, devs called this **rubber ducking** - talking through your idea until you figured out all the permutations." | https://www.aihero.dev/ai-coding-dictionary/grilling · https://www.aihero.dev/my-grill-me-skill-has-gone-viral (2026-03-23) | **High.** He names it, defines it, gives it a dictionary entry, and explicitly cites rubber ducking as the pre-AI ancestor. |
| **"shared understanding"** | **HIS USE**, ordinary English — but load-bearing as a **stop condition** | **[QUOTE, `SKILL.md`, current]** "Interview me relentlessly about every aspect of this until we reach a shared understanding. Walk down each branch of the decision tree, resolving dependencies between decisions one-by-one. For each question, provide your recommended answer. … Do not act on it until I confirm we have reached a shared understanding." | https://github.com/mattpocock/skills/blob/2ab9580/skills/productivity/grilling/SKILL.md — **this exact wording is in `SKILL.md`, not on the published page.** The published page (https://www.aihero.dev/skills-grilling) paraphrases it: **[QUOTE, docs page]** "It won't start enacting the plan until you confirm the shared understanding has been reached." | **High** for how he uses it (a confirmation gate); **not** a coinage — it's common agile/DDD English. ⚠️ **Version drift:** the transcript on https://www.aihero.dev/my-grill-me-skill-has-gone-viral at `[00:50]` quotes an *earlier* wording — "every aspect of this **plan**… Walk down each branch of the **design tree**… If a question can be answered by exploring the code base, explore the code base instead." Cite whichever version you mean; don't mix them. |
| **"wayfinder" / "wayfinding"** | **HIS** as a skill name and framing | **[QUOTE, `SKILL.md`]** "A loose idea has arrived — too big for one agent session, and wrapped in fog: the way from here to the **destination** isn't visible yet. Wayfinding is about finding that way, not charging at the destination." | https://github.com/mattpocock/skills/blob/2ab9580/skills/engineering/wayfinder/SKILL.md — **`SKILL.md` only.** The published page (https://www.aihero.dev/skills-wayfinder) is different prose and does **not** contain this sentence. Video: https://www.youtube.com/watch?v=F3lL98Pj90o | **High** that this is his definition. "Wayfinding" itself is borrowed general English (navigation/UX). The **fog / frontier / map / destination** framing around it is his. |
| **"tracer-bullet tickets"** | **HIS USE, PRIOR ART = Hunt & Thomas** | **[QUOTE, published docs page]** "Every ticket is a **tracer bullet** — a thin *vertical* slice that cuts through all integration layers end-to-end (schema, API, UI, tests), never a horizontal slice of one layer. A completed slice is demoable or verifiable on its own, which is what makes each ticket safe to hand to an agent." | https://www.aihero.dev/skills-to-tickets ← `docs/engineering/to-tickets.md:17` (https://github.com/mattpocock/skills/blob/2ab9580/docs/engineering/to-tickets.md) | **High.** He credits the source himself, twice: video #19's description names *The Pragmatic Programmer* and "tracer bullets" together, and the repo README quotes Hunt & Thomas directly. The **compound** "tracer-bullet tickets" is his packaging. |
| **"deepening opportunity"** | **HIS** compound, **PRIOR ART = Ousterhout** (deep/shallow modules) | **[QUOTE, published docs page]** "`improve-codebase-architecture` scans a codebase for **deepening opportunities** — places where a shallow module (an interface nearly as complex as the thing it hides) could become a deep one — presents them as a self-contained visual HTML report, then grills through whichever one you pick." | https://www.aihero.dev/skills-improve-codebase-architecture ← `docs/engineering/improve-codebase-architecture.md:15` (https://github.com/mattpocock/skills/blob/2ab9580/docs/engineering/improve-codebase-architecture.md) | **High.** The README quotes Ousterhout verbatim: "The best modules are deep. They allow a lot of functionality to be accessed through a simple interface." "Deepening opportunity" as a *unit of work you can queue* is his framing. |
| **"AFK" / "ready-for-afk"** | **NOT HIS** for the acronym; **HIS** for making it a first-class workflow classification | **[QUOTE, dictionary]** "Away from keyboard. A working pattern where the user kicks off a *session* and leaves the *agent* to run unattended. … The characteristic failure is coming back to hours of finished, confident work built on a wrong call made in the first ten minutes. The work isn't sloppy — it's coherent, just coherent about the wrong thing." Plus his explicit rejection of the alternative name: **[QUOTE]** "*Avoid:* "background agent" — centers the machine ("running in the background") rather than the human pattern ("user has walked away"). AFK names the fact that matters: the user isn't watching." | https://www.aihero.dev/ai-coding-dictionary/afk · the label is defined at https://github.com/mattpocock/skills/blob/2ab9580/CONTEXT.md and https://github.com/mattpocock/skills/blob/2ab9580/skills/engineering/setup-matt-pocock-skills/triage-labels.md · video https://www.youtube.com/watch?v=E5-QK3CDVQM | **High.** In the repo it is one half of a binary: **[QUOTE, `wayfinder/SKILL.md`]** "Every ticket is either **HITL** — human in the loop, worked *with* a human who speaks for themselves — or **AFK**, driven by the agent alone. A HITL ticket only resolves through that live exchange; the agent never stands in for the human's side of it (a grilling agent that answers its own questions has broken this)." |
| *(bonus)* **"attention budget"** | **HIS** phrasing of a standard mechanism | **[QUOTE]** "Each *token* has a finite amount of influence to distribute across the rest of the *context*. … An instruction that was the loudest thing at 10k tokens of context is background hum at 150k. This is the mechanism behind *attention degradation*: the model doesn't forget; the signal gets lost in the noise." | https://www.aihero.dev/ai-coding-dictionary/attention-budget | High. This is the *mechanism* underneath the dumb zone. |
| *(bonus)* **"grey box module"** | **HIS** compound | **[QUOTE]** "Deep modules create a natural seam in your codebase. You carefully control and design the interface. The implementation inside? Delegate that to AI." (under the heading "Grey Box Modules") | https://www.aihero.dev/how-to-make-codebases-ai-agents-love | High. Not in the dictionary, but explicitly named in the article. |
| *(bonus)* **"leading word"** | **HIS USE, PRIOR ART = Leitwort** | **[QUOTE, `SKILL.md`]** "A **leading word** is a compact concept already living in the model's pretraining that the agent thinks with while running the skill (e.g. *lesson*, *fog of war*, *tracer bullets*). Repeated throughout the text …, it accumulates a distributed definition and anchors a whole region of behaviour in the fewest tokens, by recruiting priors the model already holds." | https://github.com/mattpocock/skills/blob/2ab9580/skills/productivity/writing-great-skills/SKILL.md and https://github.com/mattpocock/skills/blob/2ab9580/skills/productivity/writing-great-skills/GLOSSARY.md — **`SKILL.md`/`GLOSSARY.md`; the published page https://www.aihero.dev/skills-writing-great-skills carries a shorter version.** | High. He names the prior art himself: **[QUOTE, GLOSSARY]** "A compact concept — also called a *Leitwort* …". **This is the meta-key to the whole repo** — it explains *why* he keeps coining compact terms. |

### 4.1 Where I looked, for the term I could not verify

For **"harness engineering"**:

1. `grep -rn -i harness` across every `.md` in the local repo — **11 hits, all meaning "agent
   runtime"** (`.changeset/codex-skill-metadata.md`, `.agents/adr/0002-*.md`, `.agents/invocation.md`,
   `CLAUDE.md:24`), plus three unrelated uses meaning a *test* harness
   (`diagnosing-bugs/SKILL.md:25,27,106`). Never "harness engineering".
2. The full file listing of `mattpocock/dictionary-of-ai-coding` (76 entries) — there is a
   `Harness.md`; there is no "Harness engineering".
3. The raw text of `Harness.md` — defines the noun, never the compound.
4. The aihero.dev article index (`/posts`) and every article title on it.
5. Web search for `"harness engineering" OR "harness engineer"` scoped to him — only third-party
   blog paraphrases (thinknote.co.kr, startuphub.ai, aitoolly.com), none quoting him saying it.

**Conclusion:** he uses **harness** as a noun and argues the harness is the part you can actually
control — but **"harness engineering" is not a phrase I can attribute to him.**

---

## Section 5 — Recommended watch/read order

Designed so each step supplies vocabulary the next one assumes.

### Stage 0 — Vocabulary first (≈40 min, highest value per minute)

1. **Read** https://www.aihero.dev/ai-coding-dictionary — at minimum these entries, in this order:
   `harness` → `context-window` → `attention-budget` → `attention-degradation` → `smart-zone` →
   `session` → `clearing` / `compaction` → `handoff` → `skill` → `grilling` → `spec` → `ticket` →
   `afk` → `human-in-the-loop` → `primary-source`.
   *Rationale: everything else in his material is written in this vocabulary. Reading it after the
   videos means re-watching the videos.*
2. **Watch** *What is the dumb zone?* (1m47s) — https://www.youtube.com/watch?v=sOd7svdu_1I
3. **Watch** *Most devs don't understand how context windows work* (9m33s) —
   https://www.youtube.com/watch?v=-uW5-TaVXu4

### Stage 1 — Why the skills exist (≈30 min)

4. **Read** the repo README's "Why These Skills Exist" section —
   https://github.com/mattpocock/skills#why-these-skills-exist — four failure modes, each with a
   book behind it.
5. **Read** *How To Make Codebases AI Agents Love* —
   https://www.aihero.dev/how-to-make-codebases-ai-agents-love — deep modules, grey box modules,
   Memento. This is the architectural spine of `codebase-design`, `improve-codebase-architecture`,
   and the seam rule in `tdd`.
6. **Watch** *Do software fundamentals still matter?* (2m12s) —
   https://www.youtube.com/watch?v=eEjBhVI9Qok

### Stage 2 — The workflow end to end (≈35 min)

7. **Watch** *mattpocock/skills: A complete AI Coding workflow, end-to-end* (17m17s) —
   https://www.youtube.com/watch?v=M6mYodf0dJM
8. **Read** *My 7 Phases Of AI Development* — https://www.aihero.dev/my-7-phases-of-ai-development
9. **Run** `/ask-matt` in a real repo, reading https://www.aihero.dev/skills-ask-matt alongside it.

### Stage 3 — Alignment, the highest-leverage skill (≈45 min)

10. **Read** *My 'Grill Me' Skill Went Viral* — https://www.aihero.dev/my-grill-me-skill-has-gone-viral
11. **Watch** *I stopped using /grill-me for coding. Here's what I use instead:* (15m16s) —
    https://www.youtube.com/watch?v=6BB6exR8Zd8
12. **Watch** *9 Things People Get Wrong With My /grill-\* skills* (13m28s) —
    https://www.youtube.com/watch?v=UzMNBN6xLLA — failure modes teach faster than the happy path.

### Stage 4 — Feedback loops (≈20 min)

13. **Watch** *Red Green Refactor is OP With Claude Code* (5m19s) —
    https://www.youtube.com/watch?v=hYZdIwFIy-c
14. **Read** https://www.aihero.dev/skills-tdd — specifically the **seam** rule.
15. **Read** https://www.aihero.dev/skills-to-tickets — vertical slices, tracer bullets,
    expand–contract for wide refactors.

### Stage 5 — Scale and upkeep (≈50 min)

16. **Watch** *How To De-Slop A Codebase Ruined By AI* (11m19s) —
    https://www.youtube.com/watch?v=3MP8D-mdheA
17. **Watch** */wayfinder: Nothing is too big to plan anymore* (15m09s) —
    https://www.youtube.com/watch?v=F3lL98Pj90o
18. **Watch** *Don't waste time on specs: /prototype instead* (10m59s) —
    https://www.youtube.com/watch?v=n0VhIVtviC0 — the deliberate counterweight to step 15.
19. **Watch** *I Open-Sourced My Own AFK Software Factory* (11m25s) —
    https://www.youtube.com/watch?v=E5-QK3CDVQM

### Stage 6 — Meta: how to write skills of your own

20. **Read** https://www.aihero.dev/skills-writing-great-skills, then the fuller
    https://github.com/mattpocock/skills/blob/2ab9580/skills/productivity/writing-great-skills/GLOSSARY.md.
    **Leading words** is the idea that explains his whole naming style — read it last and every
    earlier coinage retroactively makes sense.

### Optional deep end

21. **Watch** *Full Walkthrough: Workflow for AI Coding — Matt Pocock* (1h36m30s, AI Engineer 2026) —
    https://www.youtube.com/watch?v=-QFHIoCo-Ko
22. **Watch** *Building a REAL feature with Claude Code: every step explained* (44m16s) —
    https://www.youtube.com/watch?v=hX7yG1KVYhI

---

## Section 6 — Written long-form

The `https://www.aihero.dev/posts` index (200) lists his articles. All below were fetched and
returned 200 with the exact titles shown. **Several embed the matching video's full transcript**,
making them the richest quotable primary sources available without watching anything.

| Title | URL | Date (as stated) | Why it matters |
| --- | --- | --- | --- |
| How To Make Codebases AI Agents Love | https://www.aihero.dev/how-to-make-codebases-ai-agents-love | 2026-02-26 | **The philosophy article.** Deep modules, grey box modules, why architecture beats prompting. |
| My 'Grill Me' Skill Went Viral | https://www.aihero.dev/my-grill-me-skill-has-gone-viral | 2026-03-23 | Origin + rationale for grilling; ties it to rubber ducking. |
| 9 Things People Get Wrong With /grill-me and /grill-with-docs | https://www.aihero.dev/things-people-get-wrong-with-grill-me-and-grill-with-docs | — | Failure modes. |
| grill-with-docs: Align Before You Build | https://www.aihero.dev/grill-with-docs | — | The shared-language argument. |
| My 7 Phases Of AI Development | https://www.aihero.dev/my-7-phases-of-ai-development | — | The lifecycle the skills chain onto. |
| 5 Agent Skills I Use Every Day | https://www.aihero.dev/5-agent-skills-i-use-every-day | — | Practical daily set (pre-v1.1 names). |
| 9 Ways AI Coding Has Rewired My Brain | https://www.aihero.dev/ways-ai-coding-has-rewired-my-brain | — | Attitude / mental-model piece. |
| Real-world feature build with Claude Code | https://www.aihero.dev/real-world-feature-build-with-claude-code | — | Worked example. |
| Skills Changelog: Ubiquitous Language -> /grill-with-docs | https://www.aihero.dev/skills-changelog-ubiquitous-language-grill-with-docs | — | Why `ubiquitous-language` was folded in. |
| triage: Turn Backlog Mess Into Agent-Ready Work | https://www.aihero.dev/burn-through-your-backlog-with-my-triage-skill | — | |
| Getting Started With Ralph | https://www.aihero.dev/getting-started-with-ralph | — | AFK loop. |
| How To Kill The Bloat In Claude Code's System Prompt | https://www.aihero.dev/how-to-kill-the-bloat-in-claude-codes-system-prompt | — | Harness tuning. |
| How To Use Claude Code Hooks To Enforce The Right CLI | https://www.aihero.dev/how-to-use-claude-code-hooks-to-enforce-the-right-cli | — | Harness tuning. |
| Learn Anything With My /teach Skill | https://www.aihero.dev/learn-anything-with-my-teach-skill | — | |
| LLM Fundamentals | https://www.aihero.dev/llm-fundamentals | — | Free course. |
| The AI Engineer Roadmap | https://www.aihero.dev/ai-engineer-roadmap | — | Free curriculum. |

X / Twitter: https://x.com/mattpocockuk — he posts definitional threads there (see the
model/harness/environment/agent post in §4). Note x.com serves a JS shell to fetchers; text has to be
read in a browser or via a mirror API.

### 6.1 The "why" passages, quoted

From the repo README, the clearest statement of intent:

> **[QUOTE]** "Developing real applications is hard. Approaches like GSD, BMAD, and Spec-Kit try to
> help by owning the process. But while doing so, they take away your control and make bugs in the
> process hard to resolve.
>
> These skills are designed to be small, easy to adapt, and composable. They work with any model.
> They're based on decades of engineering experience. Hack around with them. Make them your own."

> **[QUOTE]** "I built these skills as a way to fix common failure modes I see with Claude Code,
> Codex, and other coding agents."

The four failure modes, verbatim as headings: **"#1: The Agent Didn't Do What I Want"**,
**"#2: The Agent Is Way Too Verbose"**, **"#3: The Code Doesn't Work"**, **"#4: We Built A Ball Of
Mud"**. Each is answered with a specific skill and anchored to a book — Thomas & Hunt's *The
Pragmatic Programmer*, Evans' *Domain-Driven Design*, Beck's *Extreme Programming Explained*,
Ousterhout's *A Philosophy of Software Design*.

On misalignment:

> **[QUOTE]** "The most common failure mode in software development is misalignment. You think the
> dev knows what you want. Then you see what they've built - and you realize it didn't understand
> you at all. … This is just the same in the AI age. There is a communication gap between you and
> the agent. The fix for this is a **grilling session** - getting the agent to ask you detailed
> questions about what you're building."

On the shared language:

> **[QUOTE]** "It's hard to explain how powerful this is. It might be the single coolest technique in
> this repo. Try it, and see."

From *How To Make Codebases AI Agents Love* (both verified byte-for-byte against the page source, not
via a summarizer):

> **[QUOTE]** "AI is not a super-powered developer. It's a new starter with no memory. Every time you
> spawn an agent, it's like the guy from Memento stepping into your codebase going, "Okay, I'm here,
> what am I doing?""

> **[QUOTE]** "Deep modules create a natural seam in your codebase. You carefully control and design
> the interface. The implementation inside? Delegate that to AI."

---

## Section 7 — What AI Hero is

AI Hero is Matt Pocock's education business, and the ecosystem these skills ship from. Its
self-description on the homepage, verified against the page source — headline (`<h1>`) then
subhead (`<p>`):

> **[QUOTE]** "Engineering fundamentals aren't obsolete."
>
> **[QUOTE]** "They're your biggest advantage. AI Hero is the engineering process for working with
> coding agents, from an idea to shipped, reviewed code."

**[PARA, from the homepage and `/courses`, both 200]** Structure of the offering:

- **Free** — the 22 skills (docs at `aihero.dev/skills-*`), the **AI Coding Dictionary**, **LLM
  Fundamentals**, **The AI Engineer Roadmap**, a free 7-day email course, articles + videos.
- **Paid, cohort-based** — *AI Coding for Real Engineers* (a two-week cohort), *Claude Code for Real
  Engineers*, *Build Your Own AI Personal Assistant in TypeScript*, *Build DeepSearch in TypeScript*.
- **Newsletter** — audience numbers differ by source and by what they count, so cite the source, not
  the number: the skills README says **[QUOTE]** "you can join ~60,000 other devs on my newsletter";
  the dictionary README says **[QUOTE]** "Join 62,000+ developers"; the homepage stat block reads
  **99,000+** / "Developers learning" and **8,500+** / "Trained in cohorts".
- **Open source** — https://www.aihero.dev/open-source, plus the `ai-hero-dev` GitHub org, plus
  `mattpocock/skills`, `mattpocock/dictionary-of-ai-coding`, `mattpocock/sandcastle`.

**[INFER]** His earlier, pre-AI identity is **Total TypeScript** (https://www.totaltypescript.com/) —
TypeScript education. The skills repo reads as a continuation of that teaching practice pointed at
agents: the same instinct to name things precisely and hand over reusable vocabulary.

---

## Appendix — Reproducing this verification

```bash
# aihero.dev per-skill pattern, with negative control FIRST
curl -s -o /dev/null -L -w "%{http_code} %{url_effective}\n" \
  "https://aihero.dev/skills-obviously-not-a-real-skill-xyz123"   # -> 404 (control)
for s in ask-matt grill-with-docs triage improve-codebase-architecture \
         setup-matt-pocock-skills to-spec to-tickets implement wayfinder prototype \
         diagnosing-bugs research tdd domain-modeling codebase-design code-review \
         resolving-merge-conflicts grilling grill-me handoff teach writing-great-skills; do
  curl -s -o /dev/null -L -w "%{http_code} %{url_effective}\n" "https://aihero.dev/skills-$s"
done

# repo facts (exact numbers, not GitHub's "1.2k" rounding)
gh api repos/mattpocock/skills --jq '{stars:.stargazers_count,license:.license.spdx_id,pushed:.pushed_at}'

# dictionary entries as raw Markdown — the best primary source for his definitions
curl -s "https://raw.githubusercontent.com/mattpocock/dictionary-of-ai-coding/main/dictionary/Smart%20zone.md"
gh api repos/mattpocock/dictionary-of-ai-coding/git/trees/main?recursive=1 --jq '.tree[].path'

# YouTube: original English titles + dates (locale-independent)
curl -s -H "Accept-Language: en-US,en;q=0.9" "https://www.youtube.com/watch?v=<ID>" \
  | grep -oP '(?<=<meta name="title" content=")[^"]*'
# FULL description (the <meta name="description"> tag truncates at ~160 chars):
#   parse ytInitialPlayerResponse -> videoDetails.shortDescription
# channel feed (latest 15, English titles, no scraping):
curl -s "https://www.youtube.com/feeds/videos.xml?channel_id=UCswG6FSbgZjbWtdf_hMLaow"
```

**Two traps this pass hit, recorded so the next person doesn't:**

1. **Auto-translated YouTube titles.** The channel's `/videos` page served Portuguese titles (WSL
   locale). English titles here come from per-video requests with an explicit
   `Accept-Language: en-US` header, plus the channel RSS feed, which serves originals. If a title
   here doesn't match what you see, check your YouTube language before assuming it's wrong.
2. **Truncated descriptions.** `<meta name="description">` cuts at ~160 characters and appends `…`.
   Anything quoted from that tag *looks* like a complete sentence and isn't. Use
   `ytInitialPlayerResponse.videoDetails.shortDescription`.
