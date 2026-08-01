# Engineering Skills Resources

Curated, verified sources. Every entry was checked during research — anything that could not be verified is marked and quarantined in `## Gaps`, never smoothed into the main list.

Full working notes, with numbers and verbatim quotes, live in `docs/research/`.

## Knowledge — the skills themselves

- [Repo: **mattpocock/skills**](https://github.com/mattpocock/skills)
  The primary artifact under study. 17 engineering skills + 5 productivity skills. MIT. Verified upstream via `package.json:7` and `.claude-plugin/plugin.json:6`. **Our local copy at `/home/rodrigo/Workspace/skills` is identical to upstream `main` at `2ab9580` — no divergence.** Version skew in the repo itself: `package.json` 1.1.0, `plugin.json` 1.2.0.
- **Published docs pages** — `https://aihero.dev/skills-<skill-name>`
  Matt's human-facing prose on each skill; explains the *why* that the `SKILL.md` omits. Pattern **verified 22/22 → HTTP 200**, with a negative control (`skills-obviously-not-a-real-skill-xyz123` → 404) proving they are not soft-404s. Example: <https://www.aihero.dev/skills-grill-with-docs>.
  **Provenance trap:** the `SKILL.md` and its published page are *different texts*. Always cite whichever one the quote actually came from.
- [Repo: **mattpocock/dictionary-of-ai-coding**](https://github.com/mattpocock/dictionary-of-ai-coding) — 76 entries, published at `https://www.aihero.dev/ai-coding-dictionary/<slug>`
  **The best primary source for his vocabulary**, and the reason we can quote him instead of an aggregator. Holds `smart zone` / `dumb zone`, `harness`, `attention budget`, `attention degradation`, `grilling`. Pinned SHA used in our research: `251fec7ec3b08059e4203863024e6123090a54e3`.
- [Repo: **mattpocock/sandcastle**](https://github.com/mattpocock/sandcastle)
  His AFK (away-from-keyboard) agent harness. Use for: lesson 0018, as a worked example of harness engineering by the same author.

### Videos — watch in this order

Titles and dates pulled per-video with `Accept-Language: en-US`; the channel page serves auto-translated titles, so anything you see in Portuguese is YouTube's translation, not his wording.

1. [**mattpocock/skills: A complete AI Coding workflow, end-to-end**](https://www.youtube.com/watch?v=M6mYodf0dJM) (16 Jul 2026, 17m) — the whole stateful chain in one sitting. Start here.
2. [**Most devs don't understand how context windows work**](https://www.youtube.com/watch?v=-uW5-TaVXu4) (22 Oct 2025) — the context-rot / smart-zone argument in his own words.
3. [**I stopped using /grill-me for coding…**](https://www.youtube.com/watch?v=6BB6exR8Zd8) (14 May 2026) — why `grill-with-docs` replaced plain grilling. Track III.
4. [**Full Walkthrough: Workflow for AI Coding**](https://www.youtube.com/watch?v=-QFHIoCo-Ko) (AI Engineer channel, 24 Apr 2026, 1h36m) — the long-form version.
5. [**How To De-Slop A Codebase Ruined By AI**](https://www.youtube.com/watch?v=3MP8D-mdheA) (29 Apr 2026) — pairs with `improve-codebase-architecture`.

> Full annotated list, coinage ledger, and a staged watch/read plan: `docs/research/04-matt-pocock-sources.md`.

## Knowledge — software engineering

- **Michael C. Feathers, _Working Effectively with Legacy Code_** — Prentice Hall PTR (Pearson), Upper Saddle River NJ, **2005**, xxi + 434 pp. — Ch. 4, "The Seam Model" (confirmed against the publisher's TOC)
  Where `seam` was coined. Use for: seams, enabling points, characterization tests, and the general problem of changing untested code safely.
  **Year:** circulates widely as 2004; both the publisher page and the Internet Archive catalogue record say **2005**.
  **ISBN — more than one is in circulation, so cite the printing you actually used.** The publisher page gave `978-0-13-117705-5`; the Internet Archive record lists `9780132931748` / `9780132931755` (later/e-book printings). Not a contradiction, but don't present one as *the* ISBN.
  **Legitimate access:** [archive.org catalogue record](https://archive.org/details/working-effectively-with-legacy-code) (Controlled Digital Lending — needs your own account), an O'Reilly/Safari subscription, or a library. Ch. 4 is what matters here.
- [Martin Fowler, **bliki: LegacySeam**](https://martinfowler.com/bliki/LegacySeam.html) (4 Jan 2024)
  Two pages. Verbatim corroboration of Feathers' definition and the highest-trust free source for it. Use for: citing the seam definition without owning the book.
- **John Ousterhout, _A Philosophy of Software Design_** — 2nd ed., **2021**, ISBN 173210221X — Ch. 4, "Modules Should Be Deep"
  Deep vs shallow modules. Read it knowing the skills repo **rejects** a depth-as-line-ratio metric ("rewards padding the implementation") *and attributes it to him* — which is the part that does not hold up.
  **SETTLED — see `docs/research/05-ousterhout-depth.md`.** He states **no implementation-lines/interface-lines ratio**, and his own text contradicts one: *"general-purpose interfaces are simpler and deeper than special-purpose ones, and they result in less code in the implementation"* (author-hosted 2nd-ed. extract, Ch. 6, p. 40). Under a line-count ratio, less implementation code would make a module *shallower*. His CS 190 notes demote size outright — *"Size doesn't really matter that much"*, *"The most important thing is depth: the power of the abstraction."* **The qualification that must not be dropped:** a ratio *does* appear in CS 190 Spring 2016 — *"maximize functionality/(interface complexity)"* — but neither term is a line count. So the accurate claim is "no **line-count** ratio", not "no ratio". Lesson 0002 tells it as: the repo rejects a **folk simplification** of Ousterhout that circulates in blog summaries — the `(Ousterhout)` attribution is the part that fails — while `depth-as-leverage` is a *restatement* of his `functionality/(interface complexity)`, not a correction of it.
- [**Ousterhout vs Robert C. Martin, `aposd-vs-clean-code`**](https://github.com/johnousterhout/aposd-vs-clean-code) (Sept 2024 – Feb 2025)
  A real, public, long-form disagreement between two authorities. Use for: seeing how senior people argue design. **Caveat:** it is about *method length*, so it does **not** settle how APOSD measures depth.
- **David Parnas, "On the Criteria To Be Used in Decomposing Systems into Modules"** — CACM 15(12), 1972
  The foundational information-hiding paper; everything above descends from it. Use for: the PhD-level pass on why interfaces hide decisions, not code.
- **Eric Evans, _Domain-Driven Design_** (2003)
  The source of `ubiquitous language` and `bounded context` — both of which the skills repo **uses but never defines**, so the definition has to come from here. Use for: Track III.
- **Kent Beck, _Test-Driven Development: By Example_** — Addison-Wesley, **2002**
  The red-green-refactor loop, from which the repo deliberately deviates by evicting refactoring to the review stage. Use for: lesson 0007, and for judging whether the deviation is sound.

### What the TDD evidence actually says — read this before arguing for TDD

This is the single most important section for the "teach and evangelise" goal, because the honest version is *stronger* than the sales pitch and survives a hostile question.

- **The favourable case study is weaker than it is quoted as being.** Nagappan et al. (2008) reports a 40–90% defect-density reduction — but it is **four case studies, not experiments**; the authors themselves concede *"there can never be an accurate equal comparison"*; the TDD projects were greenfield while the comparators included legacy enhancement work; and the widely-repeated "15–35% slower" figure is explicitly *"subjectively estimated by management,"* not measured.
- **The meta-analyses are lukewarm.** Rafique & Misic's 27-study meta-analysis found only a **small** quality effect and **no discernible productivity effect**. Munir et al. found that high-rigour studies show quality gains *"at the price of degrading productivity."*
- **The most damaging result is also the most interesting.** Fucci et al. (IEEE TSE 2017, 39 professionals) found that **_"Sequencing, the order in which test and production code are written, had no important influence"_** — what carried the effect was **granularity and uniformity** of the increments. Their conclusion: TDD's benefits *"may not be due to its distinctive test-first dynamic."*
- **The field knows it disagrees with itself.** Ghafari et al. (ESEM 2020) documents *why* the studies conflict.

**The defensible claim, and the one to actually make:** *small, uniform increments are what the evidence supports. Test-first is a forcing function that produces them — not, on the evidence, the active ingredient itself.* That claim is defensible in front of a skeptic; "TDD reduces defects 40–90%" is not.
- **Michael Nygard, "Documenting Architecture Decisions"** (2011)
  The original ADR post. Use for: lesson 0013, against the repo's stricter three-part gate.

> Full literature map with publisher/year/ISBN verification, empirical evidence on TDD, and an ordered reading path: `docs/research/02-se-literature.md`.

## Knowledge — agents and context

- [**Chroma Research, "Context Rot: How Increasing Input Tokens Impacts LLM Performance"**](https://research.trychroma.com/context-rot) (Hong, Troynikov, Huber — 14 Jul 2025)
  18 models, 6 experiment families. The strongest empirical evidence available on degradation inside the stated window. Findings worth having at hand: *"Even a single distractor reduces performance"*; shuffled haystacks beat logically-structured ones across all 18 models; LongMemEval ~113k full context vs ~300 focused tokens, focused wins on every model.
  **Caveat that matters:** the accuracy-vs-length curves are charts only. No prose in the report states "X% at 1k → Y% at 100k." Anyone quoting such a pair is interpolating.
- **Liu et al., "Lost in the Middle: How Language Models Use Long Contexts"** — TACL vol. 12 (2024), 157–173, DOI `10.1162/tacl_a_00638`
  Peer-reviewed, therefore the highest-trust citation in this whole area. GPT-3.5-Turbo on 20 documents: **75.8% first → 53.8% middle → 63.2% last**. The closed-book baseline is 56.1% — meaning a document in the middle position performs *worse than supplying no documents at all*. The U-curve is model-dependent (Claude-1.3 was near-flat).
- [**Anthropic, "Effective context engineering for AI agents"**](https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents) (29 Sep 2025)
  Defines context engineering as *"curating and maintaining the optimal set of tokens during LLM inference"*, aiming at *"the smallest possible set of high-signal tokens."* Use for: Track IV, and for the just-in-time retrieval argument.
- [**Anthropic, "Writing effective tools for agents"**](https://www.anthropic.com/engineering/writing-tools-for-agents) (11 Sep 2025)
  The named anti-pattern: *"tools that merely wrap existing software functionality."* Use for: lesson 0018 — **and read the caveat**: Anthropic explicitly says agent tools require rethinking API practice, not inheriting it. Our deep-module analogy is a lens, not their claim.
- [**Anthropic, "Demystifying evals for AI agents"**](https://www.anthropic.com/engineering/demystifying-evals-for-ai-agents) (9 Jan 2026)
  The canonical definition of `harness`. Also: grade the outcome, not the transcript; *"20-50 simple tasks drawn from real failures is a great start."*
- [**Mitchell Hashimoto, "My AI Adoption Journey"**](https://mitchellh.com/writing/my-ai-adoption-journey) (5 Feb 2026)
  Where `harness engineering` is coined, self-identified: *"I don't know if there is a broad industry-accepted term for this yet, but I've grown to calling this 'harness engineering.'"*
- **Shi et al., "Large Language Models Can Be Easily Distracted by Irrelevant Context"** — ICML 2023
  GSM-IC. *"performance is dramatically decreased when irrelevant information is included."* Use for: the evidence behind "less context, better output."
- [**Nielsen Norman Group, progressive disclosure**](https://www.nngroup.com/articles/progressive-disclosure/)
  The HCI lineage: *"show only a few of the most important options… larger set upon request."* Pair with Anthropic's Agent Skills docs, which call progressive disclosure *"the core design principle that makes Agent Skills flexible and scalable."*

> Numbers, verbatim quotes, and the full marker-by-marker breakdown: `docs/research/03-agent-engineering.md`.

## Wisdom (communities)

Four verified, split cleanly by half. Bar: a dated artifact on or after 2026-02-01, a public URL or documented join path, a named on-topic thread, and stated cost. Full verification evidence — including everything rejected and why — is in `docs/research/07-communities.md`.

- [**Lobsters**](https://lobste.rs) — free; **invitation required** to post, granted by an existing user. Public moderation log: *"All moderator actions on this site are visible to everyone."* Active `2026-07-31`. Example: ["Design Patterns Suck"](https://lobste.rs/s/7qssyu/design_patterns_suck), `2026-06-26`, 34 comments.
  Use for: design and testing argument with real comment counts — `/t/practices`, `/t/testing`. **Not** for the agent half; `/t/ai` is ML internals.
- [**Virtual DDD**](https://virtualddd.com/sessions/) — free open [Discord](https://discord.gg/tRJkcsFDKN), public session archive; per-session RSVP cost `[unverified]`. Sessions dated through `5 Aug 2026`. Example: ["Critically Engaging with Models" with Rebecca Wirfs-Brock](https://virtualddd.com/sessions/critically-engaging-with-models-a-conversation-with-rebecca-and-mathias/), `28 Jan 2026`.
  Use for: modelling, ubiquitous language, bounded contexts — named practitioners arguing, not linking.
- [**`anthropics/skills` — Issues**](https://github.com/anthropics/skills/issues) — free, GitHub account, no application. Active `2026-08-01`. Examples: [#492](https://github.com/anthropics/skills/issues/492) (`2026-03-02`, 43 comments, skill-namespace trust boundary), [#1487](https://github.com/anthropics/skills/issues/1487) (`2026-07-27`, a skill injecting ~156k tokens).
  Use for: skill authoring and progressive disclosure argued with numbers. **Issues, not Discussions** — the Discussions tab is showcases with zero comments.
- [**Hacker News**](https://news.ycombinator.com) — free, no application. Example: ["The new rules of context engineering for Claude 5 generation models"](https://news.ycombinator.com/item?id=49051361), `2026-07-25`, 462 points / 404 comments.
  Use for: volume of argument on context rot and harness design. Untopic-moderated — signal-to-noise is far worse than Lobsters; go for the big threads, not the front page.

**Events, not communities** (verified dates, listed for planning): SoCraTes Germany `August 27–30, 2026` (lottery application); Explore DDD `September 21-25, 2026`, Denver; KanDDDinsky `October 14-16, 2026`, Berlin (€950 conference / €250 open space); DDD Europe, Amsterdam, `June 1-4` 2027.

**Negative result worth keeping:** Matt Pocock runs no public community on this subject. `aihero.dev` has no forum, and `mattpocock.com/discord` is "Matt's TS Wizards" — TypeScript. Contact with his thinking is one-way: repo, docs pages, videos.

**Discord is a systematic blind spot.** Anthropic's and HumanLayer's Discords have verified, free, documented join paths — but activity and signal cannot be evidenced from outside, so neither is recommended here. Joining and reporting from inside is a different kind of evidence and should be labelled as such.

## Gaps

Things the mission needs and the research has not yet supplied. These drive the next research pass.

- ~~**Communities.** No high-reputation community has been verified for either half of this topic.~~ **RESOLVED** — four verified, see `## Wisdom (communities)` above and `docs/research/07-communities.md`. **Two residual gaps remain, both narrower.** (a) **Discord interiors are unverifiable from outside.** Anthropic's and HumanLayer's servers have free, documented join paths, but no dated artifact is visible without joining — so neither is recommended. Joining and reporting from inside is a *different kind of evidence* and must be labelled as such if it ever lands here. (b) **`wiki.c2.com` serves a JavaScript shell to automated fetch** and needs a manual browser check before it can be judged live or dead.
- ~~**Blocked on a licensed copy of Feathers.**~~ **RESOLVED.** Ch. 4 (pp. 29–44) has been read against the book text. Both boxed definitions (`Seam` p. 31, `Enabling Point` p. 36) and the full seam taxonomy (preprocessing / link / object, pp. 33–44) are now quoted verbatim with printed page numbers in `lessons/0001-seam.html` and `reference/0001-glossary.html`. Fowler's rendering of the seam definition was confirmed **exact**.
- ~~**Does Ousterhout actually define depth as a ratio?**~~ **RESOLVED** — `docs/research/05-ousterhout-depth.md`. No line-count ratio; his text falsifies one. A `functionality/(interface complexity)` ratio does exist in his lecture notes, so the repo's *shape* is right and its **attribution** is what fails. See his entry above. Ch. 4's literal text was still not obtained from any legitimate source — the author-hosted extract carries only Ch. 6 and Ch. 21 — so no Ch. 4 page number is claimed anywhere.
- ~~**Cockburn's walking skeleton.** Origin unresolved; both known URLs return **404**.~~ **RESOLVED** — `docs/research/06-walking-skeleton-and-harness-post.md`. The live site really is 404, but his old `alistair.cockburn.us` pages are archived intact. The *Crystal Clear* (2004) definition is quoted verbatim from his own page (**Wayback capture 2008-10-16**), and an earlier wiki text of his (last modified **2001-08-12**, capture **2004-05-13**) predates the book. He himself dates the idea to *"around 1994, named it somewhere between then and 1997"* — record that as **his claim**, not as fact; the page's `6/1/1996` field is CMS metadata and is not a publication date.
- **`vertical slice` has no located coiner** — a **new** gap opened by the pass above. Walking skeleton (Cockburn) and tracer bullet (Hunt & Thomas) both have quoted coiners; `vertical slice` as a *work-slicing* term does not. **Do not assign it one in lesson 0006.** Watch the homonym: *Vertical Slice Architecture* (a code-organisation pattern, Jimmy Bogard, c. 2018, `[unverified]`) is a different concept sharing the words.
- **Deliberately omitted rather than guessed:** Bloch's *Effective Java* item numbers, Evans' chapter numbers, Beck's ISBN, Agans' full list of 9 rules, Stevens/Myers/Constantine 1974 details. Per the no-guessed-locators rule. Add: the *Crystal Clear* page number for the walking-skeleton definition, and the *Pragmatic Programmer* book text of "Tracer code is not disposable…" — which reached us **only through secondary aggregators** and stays `[unverified]`.
- ~~**OpenAI's "Harness engineering" post** returned HTTP 403 to automated fetch.~~ **RESOLVED** — same file. Live URL still defeats automated fetch (403, or a 200 serving `Enable JavaScript and cookies to continue`); the **Wayback Machine** returns the full text. Ryan Lopopolo, **11 Feb 2026**. All four figures now **Quoted**, verified identical across two captures five months apart (`20260211221555`, `20260729080121`) — so no post-publication edit. **Cite them with their qualifiers:** *"on the order of a million lines"*, *"roughly 1,500 pull requests"*, and *three* engineers **only for the five-month window** — the post says the team "has grown to now seven engineers". The *"1/10th the time"* speed-up is self-reported (*"We estimate"*), never measured.

`02-se-literature.md` now carries **125 explicit `[unverified]` marks**, triaged in its own appendix into cheap / moderate / blocked. That count is a feature, not a defect — it is the difference between a document that knows what it doesn't know and one that reads confidently everywhere.

## Source hazards found during research

Recorded because they will waste time again otherwise.

- **`openai.com/index/*` cannot be fetched programmatically.** A plain fetch gets **403**; a browser user-agent gets **200** with a 10 KB bot interstitial reading `Enable JavaScript and cookies to continue`. Go straight to the Wayback Machine — it has many `200` captures, interleaved with `403` ones where the crawler hit the same wall. Always diff two captures far apart in time before quoting numbers.
- **`c2.com/cgi/wiki?X` 302s to `wiki.c2.com/?X`, which serves a JS shell.** No text is retrievable without a browser. Don't budget time for it.
- **Alistair Cockburn's site was restructured and the old URLs 404.** Everything worth citing lives under `alistair.cockburn.us` in the Wayback CDX index — query it with `filter=urlkey:.*<term>.*` rather than guessing a single URL.
- **Every `docs.claude.com` URL has moved** to `code.claude.com` / `platform.claude.com`. Old links in blog posts and answers are dead.
- **Anthropic's April 2025 Claude Code best-practices post contained an explicit TDD workflow section. It is gone** from the current rewritten doc and survives only on the Wayback Machine — quoted in full in `docs/research/03-agent-engineering.md` §13.8. Cite the archive, not the live page.
- **The "~100K tokens / ~40% of the context window" dumb-zone figure is not in any Pocock source.** It appears only in aggregator write-ups of a talk. His written source says *"around 125K-150K tokens — though this is debated"* and explicitly states the zones do **not** track a percentage of the window.
- **"Dumb zone was coined by Dex Horthy" does not hold up.** The phrase does not appear in HumanLayer's own `ace-fca.md`. What *is* his: the 40–60% utilization target and *intentional compaction*.
