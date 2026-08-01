# 07 — Communities (Wisdom)

Research pass closing the `## Wisdom (communities)` gap in `RESOURCES.md`.

**Date of this pass: 2026-08-01.** "Currently active" is therefore operationalised as
**a dated artifact on or after 2026-02-01** (the last ~6 months). Every date below can be
re-checked against that cutoff.

## Method, and the one rule that decided most outcomes

Search engines were used only to *find* candidate URLs. **Every date in this document was
read off a fetch of the page or an API response, never off a search snippet.** Search
snippets carry stale and reconstructed dates and are not evidence.

A second rule followed from the first and decided roughly half the candidates:

> **A working invite link is not evidence of activity.** For a Discord or Slack workspace
> you cannot see message dates from outside. Such a venue clears the bar only if there is a
> **public, dated, external artifact** — a published event page, a public archive, a linked
> recap. Otherwise it is recorded as *join path verified; activity and signal not externally
> verifiable without joining*, which is a negative result, not a pass.

That asymmetry is the whole shape of the result: **publicly indexed, per-thread-dated venues
clear the bar; closed chat does not.**

### Who fetched what

Discovery was delegated to two parallel subagents. **Every load-bearing URL behind the four
verified entries was then re-fetched by the main thread** — the Lobsters tag pages, `/about`
and the "Design Patterns Suck" story; the Virtual DDD session archive and the Wirfs-Brock
session page; `anthropics/skills` via `gh api` (GraphQL and search); the HN Algolia API; the
`claude.com/community` page; Pocock's site and Discord invite; and the KanDDDinsky pretix page.

**Not independently re-checked by the main thread**, and therefore carrying one extra hop of
trust: the SoCraTes / Explore DDD / DDD Europe rows of the events table, and every entry in
`## Rejected` whose only evidence is an HTTP status or a single date
(`aposd-vs-clean-code`, `groups.io`, `wiki.c2.com`, the London and Barcelona craft groups,
`forum.cursor.com`, `community.openai.com`, `discuss.kurrent.io`, `luma.com`). Those are
rejections, so an error there costs a missed candidate, not a fabricated recommendation —
which is the cheaper direction for the error to run.

### On the four evidence tiers

The workspace's four tiers were written for literature. **"Chapter located" has no analogue
for a community and is deliberately unused here.** The other three apply:

| Tier | Used here to mean |
|---|---|
| **Quoted** | Text was fetched; the quote is verbatim; the URL is given. |
| **Metadata verified** | A date, a count, a price, or a join path read from the page or from a repo/API response. |
| **`[unverified]`** | Everything else — including anything visible only after joining. |

---

## Verified — clears all four bars

Bars: (1) dated artifact ≥ 2026-02-01, (2) working public URL or documented join path,
(3) at least one concrete named on-topic discussion with a URL, (4) cost / application stated.

### 1. Lobsters (`lobste.rs`) — classical software design

- **Active.** *Metadata verified.* `https://lobste.rs/t/practices` front page carries stories
  dated **`2026-07-31`** ("Accepting a messy git history", 14 comments) and
  **`2026-07-30`** ("Code Review Responses: Add Context When It Counts").
  `https://lobste.rs/t/testing` most recent **`2026-07-27 15:30:01`**.
- **URL / join path.** Public read at `https://lobste.rs`. Posting requires an invitation.
  *Quoted*, from `https://lobste.rs/about`:
  > "The quickest way to receive an invitation is to talk to someone you recognize from the site."
  > "There's no limit on how many invitations a user can send"
- **Cost.** Free. Invitation-only for participation — an **application-by-acquaintance**
  model, not a payment. New accounts cannot invite others or submit unfamiliar domains for
  their first 70 days.
- **Concrete on-topic threads** — *Metadata verified*, each date and comment count read off
  the story page or the tag listing:
  - **"Design Patterns Suck"** — `2026-06-26`, **34 comments** —
    <https://lobste.rs/s/7qssyu/design_patterns_suck>
    *Quoted*, from a comment on that page:
    > "Design patterns are a bestiary. You encounter them in the wild, learn to recognize them, describe them, and name them, so that you can benefit from common language."
  - **"Prefactoring: Clear the Way for Your New Feature"** — `2026-07-22 00:49:05`, 7 comments —
    <https://lobste.rs/s/x8soyh/prefactoring_clear_way_for_your_new>
  - **"Why Fakes Beat Mocks and Testcontainers"** — `2026-07-05 04:21:37` —
    <https://lobste.rs/s/qecfnj/why_fakes_beat_mocks_testcontainers>
  - **"A Philosophy of Software Design" vs "Clean Code"** — `2025-02-24 10:42:31`, 75 votes,
    **40 comments**, last comment `2025-02-26 15:56:34` —
    <https://lobste.rs/s/xcqfp7/philosophy_software_design_vs_clean_code>
    **Outside the 6-month window** — listed as evidence of topical depth, not of current activity.
- **Moderation** — *Quoted*, from `/about`:
  > "All moderator actions on this site are visible to everyone and the identities of those moderators are public."
  > "Lobsters is focused pretty narrowly on computing"

  The site explicitly rejects shadow banning. This is the strongest moderation evidence found
  for any venue in this pass.

### 2. Virtual DDD (`virtualddd.com`) — DDD, modelling, design argument

- **Active.** *Metadata verified*, from `https://virtualddd.com/sessions/`:
  - **Wednesday, 5 August 2026** — "Surviving a National Blackout with Offline-First Architecture | Emilio Carrión"
  - **26 May 2026** — "It's like 10,000 streams when what you need is a queue — Chris Simon"
  - **11 May 2026** — "Rethinking software development: insights from 20+ years in the field"
  - **28 Jan 2026** — "Critically Engaging with Models a conversation with Rebecca"
  - **25 Nov 2025** — "Patterns of BDD Automation — a Fireside chat with Seb Rose and Gáspár Nagy"

  The site's own front page also carried a Bluesky post dated **31 Jul 2026**.
- **URL / join path.** Public session archive at `https://virtualddd.com/sessions/`.
  Discord invite, read off a session page: **`https://discord.gg/tRJkcsFDKN`**.
  *Quoted*, from `https://virtualddd.com/`:
  > "Join our Discord community — talks, questions and connections all week long."

  Sessions RSVP through Humanitix (`events.humanitix.com`).
- **Cost.** **Joining the community is open and free**: the Discord invite above requires no
  application and no payment, and the session archive is public. **Per-session RSVP cost is
  `[unverified]`** — no price text appears on `virtualddd.com` or on the Humanitix page for the
  5 Aug 2026 session. No payment step was observed, but "free" is not asserted for the RSVP
  because no page states it.
- **Concrete on-topic session** — *Metadata verified*:
  **"Critically Engaging with Models"** with **Rebecca Wirfs-Brock**, hosted by Andrea
  Magnorsky, **28 January 2026** —
  <https://virtualddd.com/sessions/critically-engaging-with-models-a-conversation-with-rebecca-and-mathias/>
  *Quoted*, from that page:
  > "Models, whether for a software system, a development process, diseases, political systems, or otherwise, are a way to look at (a part of) the world."

  Wirfs-Brock is a primary-source-grade author on responsibility-driven design; this is the
  single best evidence in this pass that the venue argues design rather than links to it.
- **Note.** `https://virtualddd.com/events` and `/events/` return **HTTP 404**;
  `https://www.meetup.com/virtual-domain-driven-design/` reports the group does not exist.
  Use `/sessions/` as the canonical entry point.

### 3. `github.com/anthropics/skills` — Issues + Discussions — agent / context engineering

- **Active.** *Metadata verified* via `gh api` GraphQL, newest discussion
  **`2026-08-01T08:17:17Z`** ("Bouncer — a skill that decides whether a prototype is ready
  for real users", <https://github.com/anthropics/skills/discussions/1515>).
- **URL / join path.** `https://github.com/anthropics/skills/issues` and
  `https://github.com/anthropics/skills/discussions`. Public read; a free GitHub account to post.
- **Cost.** Free, no application.
- **Concrete on-topic threads** — *Metadata verified*, `created_at` and comment counts from
  the GitHub API:
  - **`2026-03-02T14:50:00Z`, 43 comments** — "Security: Community skills distributed under
    anthropic/ namespace enable trust boundary abuse" —
    <https://github.com/anthropics/skills/issues/492>
    A **design debate**, not a defect report — where the trust boundary of a skill namespace
    belongs. The longest argued thread found on the agent side outside Hacker News.
  - **`2026-06-17T15:54:47Z`, 9 comments** — "Proposing a second skill: compact-memory
    (symbolic notation for compact agent state)" —
    <https://github.com/anthropics/skills/issues/1329>
    A proposal being argued down and refined in public.
  - **`2026-07-27T06:35:40Z`, 4 comments** — "`claude-api` skill eagerly injects ~156k tokens,
    exhausting the context window in a single tool call" —
    <https://github.com/anthropics/skills/issues/1487>
    The most **topically on-mission** hit in the pass — a progressive-disclosure failure argued
    with a token number. Filed as a bug, so it is cited for its subject matter, not as evidence
    that the venue argues design; #492 and #1329 carry that.
  - **`2026-05-30T15:44:07Z`, 2 comments** — "Feature request: multi-file preload / inline
    bundling for skill reference files" —
    <https://github.com/anthropics/skills/issues/1220>
- **Honest caveat.** The **Discussions** tab is mostly skill *showcases* with **0 comments**
  (verified: 7 of the 8 newest discussions have zero). **The argument happens in Issues, not
  Discussions.** Point a reader at Issues.

### 4. Hacker News (`news.ycombinator.com`) — agent / context engineering

- **Active.** *Metadata verified* via the HN Algolia API
  (`hn.algolia.com/api/v1/search_by_date`), which returns `created_at`, `points` and
  `num_comments` as data fields.
- **URL / join path.** `https://news.ycombinator.com`. Free account, no application.
- **Cost.** Free.
- **Concrete on-topic threads** — *Metadata verified*:
  - **`2026-07-25T20:42:35Z` — 462 points, 404 comments** — "The new rules of context
    engineering for Claude 5 generation models" —
    <https://news.ycombinator.com/item?id=49051361>
  - **`2026-07-27T22:37:52Z` — 405 points, 119 comments** — "Benchmarking Opus 5 on
    SlopCodeBench" (HumanLayer's `advanced-context-engineering-for-coding-agents`) —
    <https://news.ycombinator.com/item?id=49076391>
  - **`2026-07-23T15:18:48Z` — 394 points, 271 comments** — "Why Software Factories Fail
    (or: harness engineering is not enough)" (HumanLayer) —
    <https://news.ycombinator.com/item?id=49023019>
- **Provenance note.** Direct fetches of `news.ycombinator.com/item?id=…` returned
  **HTTP 429** during this pass. Points and comment counts above come from the Algolia API,
  not from the rendered page. The item URLs are constructed from the returned `objectID`, which
  is HN's canonical ID scheme. `https://hn.algolia.com/api/v1/items/49051361` was fetched to
  check that mapping and **does return a real, populated comment thread**, confirming the ID
  resolves; the top-level `points`/`title` fields could not be read back out of that response,
  so those two numbers still rest on the `search_by_date` result alone.
- **Honest caveat.** HN is high-traffic and **not topic-moderated**. Signal-to-noise is far
  worse than Lobsters. What it demonstrably has is *volume of argument on exactly this
  subject* — a 404-comment thread on context engineering has no equivalent anywhere else
  found in this pass.

---

## Verified events (real, dated — but conferences, not persistent communities)

Listed separately because a once-a-year event is not the ongoing contact the `teach` skill
means by wisdom. All dates *Metadata verified* from the page named.

| Event | Dates (quoted) | Location | Cost / entry |
|---|---|---|---|
| **SoCraTes Germany** — <http://www.socrates-conference.de/> | `"August 27 – 30, 2026"` | `"Hotel Park Soltau, Germany"` | **Lottery application** — `"Apply for the lottery now"` via `app.socrates-conference.de`. Price `[unverified]` — not stated on the page. |
| **Explore DDD** — <https://exploreddd.com/> | `"September 21-25, 2026"` | `"Hydro at CSU Spur \| Denver, CO"` | `[unverified]` — `/tickets/` and `/register/` both return **HTTP 404**. |
| **KanDDDinsky 2026** — <https://pretix.eu/kandddinsky/2026/> | `October 14-16, 2026` | Park Inn, Alexanderplatz 7, Berlin | **Paid, prices re-fetched by the main thread**: `"Two Days Conference Ticket (14 Oct 2026 and 15. Oct 2026)"` **€950.00**; `"Open Space Ticket, valid for Oct 16. 2026"` **€250.00**; `"Two Days Conference Ticket plus Open Space (14 Oct 2026 and 16. Oct 2026)"` **€1,200.00**. |
| **DDD Europe 2027** — <https://2027.dddeurope.com/> | `"June 1-3"` workshops, `"June 3-4"` conference | `"Amsterdam, The Netherlands"` | `[unverified]` — ticket link only (`ti.to/dddbv/aardling27`). **The year 2027 is inferred from the hostname; it does not appear in the page text.** |

**Named on-topic sessions — KanDDDinsky 2026** (session titles and speakers reported by the
discovery subagent from the pretix page): "EventStorming Master Class" with **Alberto
Brandolini**; "Architecture for Flow Masterclass" with **Susanne Kaiser**; "Collaborative
Software Modelling and Design with Bytesize Sessions Masterclass" with **Andrea Magnorsky**.
The main thread's re-fetch of the same page surfaced only the three conference tickets above,
**not** the masterclass line items — so the masterclass **prices the subagent reported are
withheld here as `[unverified]`**, and the titles are `[unverified]` at main-thread level.

**SoCraTes fails bar 3 by construction**: it is a self-organised Open Space
(*quoted*: `"a self-organised Open Space"`, covering `"coding, testing, code quality, software
craft"`), so there is no published programme and no concrete session could be named.
DDD Europe **2026** (`June 10-12, 2026`, Antwerp) is already past as of this pass.

---

## Rejected / could not verify

Every entry here failed at least one bar. The reason is stated precisely so the check is not
repeated blindly next pass.

### Closed chat — join path verified, activity not externally verifiable

Not a judgement that these are inactive. A judgement that **from outside, activity and signal
cannot be evidenced**, which is what the bar asks for.

- **Claude / Anthropic Discord** — join path **documented and verified**:
  `https://claude.com/community` lists it, *quoted*:
  > "Real-time help, project sharing, and active discussions with thousands of developers."

  → `https://discord.com/invite/6PPFFzqPDZ`. `https://www.anthropic.com/discord` 307-redirects
  to the same invite. **Free.** Fetching the invite page returns only the word "Claude" — no
  member count, no message dates. **Member counts circulating in search snippets were
  discarded as unverifiable.** No public dated artifact was found. `[unverified]` on activity
  and signal.
- **HumanLayer Discord** — `https://www.humanlayer.dev/discord` → 307 →
  `https://discord.gg/3chc2y8jpT` → `https://discord.com/invite/3chc2y8jpT` (200, body renders
  only "humanlayer.com"). No member count, no dates. **Free.** Same verdict.
  What *is* verifiable is their repo — `humanlayer/advanced-context-engineering-for-coding-agents`,
  `pushed_at 2026-07-27T22:37:06Z`, **2,250 stars**, `has_discussions=false` (*metadata verified*
  via `gh api`). **That is a knowledge source, not a community** — discussion of it happens on
  Hacker News, which is why HN carries the entry instead.
- **Matt Pocock's Discord** — `https://www.mattpocock.com/discord` → 302 →
  `https://discord.gg/8S5ujhfTB3` → `https://discord.com/invite/8S5ujhfTB3`. Server name
  *quoted* from the invite page: **"Matt's TS Wizards"**. **TypeScript, not agent or context
  engineering.** Off-topic for this course's second half.

### Negative result worth recording: Matt Pocock runs no community on this subject

`https://www.aihero.dev` (200) exposes **no** Discord, forum, or community link. It sells a
paid cohort ("AI Coding for Real Engineers") with a waitlist; the page states
`"8,500+ Trained in cohorts"` (*metadata verified*, but a marketing figure and not a community
membership). `https://www.youtube.com/@mattpocockuk/about` returned only YouTube's chrome —
channel links **could not be confirmed**.

**Conclusion, stated plainly:** the author whose repo this course studies does **not** operate
a public community where these ideas are argued. Contact with his thinking is one-way — repo,
docs pages, videos. This is a real finding, and it means the second half's wisdom must come
from Anthropic's repos and from HN, not from him.

### Failed the activity bar

- **`github.com/johnousterhout/aposd-vs-clean-code`** — `Issues 0`, `Pull requests 0`, **no
  Discussions tab**; issues page reads `"No results"`. Most recent commits **`April 15, 2025`**.
  **No activity in 2026 at all.** Separately, it was never a community: *quoted* from its README,
  > "(This document is the result of a series of discussions, some online and some in person, held between Robert \"Uncle Bob\" Martin and John Ousterhout between September 2024 and February 2025)"

  Two people, one finished artifact. It stays in `RESOURCES.md` under **Knowledge**, where it
  already correctly sits.
- **`github.com/anthropics/claude-code/discussions`** — **HTTP 404**; Discussions are not
  enabled on that repo. The Issues tracker is very active (a pinned bug dated
  `2026-08-01T08:17:56Z`, <https://github.com/anthropics/claude-code/issues/83076>) but it is a
  **bug tracker, not an argument venue** — it fails the "high signal for this topic" bar on
  content, not on activity.
- **`groups.io/g/extremeprogramming`** — **HTTP 402 Payment Required**. No accessible archive;
  no 2026 message could be confirmed. No still-active mailing list was found for this subject.
- **London Software Craftsmanship Community** (`meetup.com/london-software-craftsmanship`) —
  **contradictory data, reported as-is rather than resolved**: the group page shows
  `"6,426 members"` and an upcoming "Kirill Boiarkin: Soft Skills in the age of AI",
  `Thursday, September 3` (**no year shown**), while the same fetch reports the last event as
  **`July 16, 2024`**, and `/events/` shows **0 upcoming events**. Current activity
  `[unverified]`. The named upcoming talk is also off-topic for seams/TDD/DDD.
- **Software Crafters Barcelona** (`softwarecrafters.barcelona`) — page quotes
  `"16 & 17 October"` and `"13TH EDITION"` but **no year appears anywhere in the page text**.
  Cannot be placed inside or outside the window. `[unverified]`.

### Failed the "could I even read it" bar

- **`wiki.c2.com`** (and `?RecentChanges`, `?WikiHistory`) — all three return only a
  client-rendered shell, *quoted*: `"This site uses features not available in older browsers."`
  No dates, no content reachable without a JS-executing browser. **Whether it is live or
  archival could not be confirmed.** `[unverified]` — needs a manual browser check, not another
  fetch.
- **`reddit.com/r/ClaudeAI`** — WebFetch blocked on both `www.` and `old.` hosts. **No dated
  2026 thread could be confirmed.** The only verifiable fact: `claude.com/community` documents
  it as an official channel, *quoted*:
  > "Long-form discussions, project showcases, and community knowledge that sticks around."

  Recommending it would mean recommending something that could not be read. It is not
  recommended.
- **`luma.com/claudecommunity`** — fetched twice; **no date text visible on any of the 17 event
  listings**. Named events ("Austin | Claude Code Meetup", "New York City | Claude Code for
  Developers (Sold Out)") are beginner/onboarding meetups, not context-engineering argument.
  Fails both the activity bar (no readable dates) and the signal bar.

### Active, but failed the signal bar for *this* topic

- **`forum.cursor.com`** (Discourse) — `/latest` most recent **`August 1, 2026`**, so clearly
  active. But the front page is billing and model complaints (top topic: "Legacy Individual
  Plan — Max Mode Required", 656 replies). The closest on-subject topic, "Proposal – Context &
  execution observability for more reliable Cursor agents"
  (`created_at 2026-07-30T15:57:12Z`, <https://forum.cursor.com/t/167080>), has **2 posts**.
  Free. Also vendor-specific to a tool this course does not study.
- **`community.openai.com`** (Discourse) — `/latest` most recent **`August 1, 2026`**; front
  page dominated by quota/billing. Best on-subject topic: "How do you preserve project context
  across Codex sessions in a large codebase?" (`2026-07-20T05:47:53Z`,
  <https://community.openai.com/t/1387550>), **9 posts**. Free. OpenAI-centric.
- **`discuss.kurrent.io`** (ex-EventStore Discourse) — live, most recent **`June 26, 2026`**.
  It lists a `Domain-Driven Design` category, but the category URL fetched
  (`/c/domain-driven-design/6`) resolved to **Webinars**, newest activity `April 30, 2025`.
  **No dated DDD-category thread could be confirmed.** Free registration.
- **Lobsters for the *agent* half** — recorded as a partial negative. `https://lobste.rs/t/ai`
  is active (most recent `2026-07-31 11:17:44`) but is **ML internals** — inference engines,
  attention kernels, MLIR — not context or harness engineering.
  `https://lobste.rs/t/vibecoding` is active daily (most recent `2026-07-31`) and does carry
  adjacent material — "A Practical Guide to Reducing Token Spend", `2026-07-29`, **0 comments**
  — but thin. **Lobsters earns its entry for the classical half only.** For the agent half, HN
  is where the argument is.

---

## Summary

**Four venues clear the bar**, and the split is clean:

| Half | Venue | Why it won |
|---|---|---|
| Classical design | **Lobsters** | Per-story dates, public moderation log, real design threads with real comment counts. |
| Classical design | **Virtual DDD** | Named practitioners arguing modelling, sessions dated through Aug 2026, public archive. |
| Agent engineering | **`anthropics/skills` Issues** | Progressive disclosure and context exhaustion argued with token numbers, in public, dated. |
| Agent engineering | **Hacker News** | The only venue with 100+-comment argument on context engineering. Low S/N, high volume. |

**The single most useful negative result:** the strongest-reputation venues on the agent side
are almost all Discord, and **Discord cannot be verified from outside**. The verified agent-side
list is short *because* the field's discussion lives in closed chat, not because the field is
quiet. Anyone extending this list should either join and report from inside — a different kind
of evidence, and it should be labelled as such — or keep to publicly indexed venues.
