# 06 — Walking skeleton, tracer bullet, vertical slice · and OpenAI's "Harness engineering" post

> **Renumbering note, 2026-08-01.** References to "lesson 0006" below mean the **vertical slice / tracer bullet / walking skeleton** lesson, which became **0008** when DIP took 0006 and YAGNI/DRY took 0007. Numbers updated in place; this note records why.


Both items were carried in `RESOURCES.md` `## Gaps`. **Both are now settled.**
Every claim below carries a tier: **Quoted** · **Metadata verified** ·
**Chapter located** · **`[unverified]`**.

Method note: every text marked **Quoted** was fetched raw with `curl` and the
sentence copied character-for-character out of the extracted body. Nothing in
the Quoted tier came from a model summarising a page.

---

## Part 1 — Cockburn's walking skeleton

### Verdict

**Resolved.** The definition is recoverable in Cockburn's own words, from his
own site, via the Wayback Machine. The live-site 404s in `RESOURCES.md` are
real — his site was restructured — but the old `alistair.cockburn.us` page is
archived intact.

### The primary artifact

**Quoted.** Archived page `alistair.cockburn.us/Walking+skeleton`.
**Wayback capture: 2008-10-16 06:32:14 UTC** (`20081016063214`).
<https://web.archive.org/web/20081016063214/http://alistair.cockburn.us/Walking+skeleton>

Page footer, verbatim: `Posted by: Alistair on 6/19/2008 1:07:05 PM` ·
`Last modified by: Alistair on 10/3/2008 11:15:29 PM`. The page's own date
field reads `6/1/1996` — **`[unverified]` as a publication date**; it is a CMS
metadata field on a page whose body quotes a 2004 book, so it cannot be the
date this text was written. Do not cite 1996 as the origin date.

**His own origin claim, verbatim (Quoted):**

> I first found this pattern or strategy around 1994, named it somewhere
> between then and 1997. Around 1999 I found that it pairs well with
> Incremental Rearchitecture. Here's the description from the Crystal Clear
> book, 2004.

That is a *claim about* 1994–1997, not evidence of it. Report it as his claim.

**The definition, verbatim (Quoted)** — introduced by him on that page as the
text from *Crystal Clear* (2004):

> A Walking Skeleton is a tiny implementation of the system that performs a
> small end-to-end function. It need not use the final architecture, but it
> should link together the main architectural components. The architecture and
> the functionality can then evolve in parallel.

**What varies with the system, verbatim (Quoted):**

> What constitutes a walking skeleton varies with the system being designed.
> For a client-server system, it would be a single screen-to-database-and-back
> capability. For a multi-tier or multi-platform system, it is a working
> connection between the tiers or platforms. For a compiler, it consists of
> compilation of the simplest element of the language, possibly just a single
> token. For a business process, it is walking through a single and simple
> business transaction […]

**Skeleton vs spike — his own boundary, verbatim (Quoted):**

> A walking skeleton is different from a spike. A spike is "the smallest
> implementation that demonstrates plausible technical success." The spike
> typically takes between a few hours and a few days to complete, and is thrown
> away afterward, since it was built with nonproduction coding habits. A spike
> serves to answer the question: Are we headed in the wrong direction?
>
> A walking skeleton, on the other hand, is permanent code, built with
> production coding habits, regression tests, and is intended to grow with the
> system. Once the system is up and running, it will stay up and running for
> the rest of the project, despite the Incremental Rearchitecture that is quite
> likely to occur.

**The load-bearing sentence for lesson 0008 — Cockburn himself naming the
neighbours, verbatim (Quoted):**

> Other authors have other names for similar sorts of ideas. The Poppendiecks
> (2003) write about a "spanning application"); Dave Thomas and Andy Hunt use
> what they call "tracer bullets" (Hunt 1999).

Note the exact hedge: **"other names for *similar sorts of* ideas"** — not
"the same idea". He acknowledges kinship without collapsing them.

### The earlier artifact — his own Crystal wiki

**Quoted.** `alistair.cockburn.us/crystal/wiki/WalkingSkeleton`, page footer
`(last modified 2001-08-12 22:15:00)`. **Wayback capture: 2004-05-13 04:55:53
UTC** (`20040513045553`).
<https://web.archive.org/web/20040513045553/http://alistair.cockburn.us/crystal/wiki/WalkingSkeleton>

This is the **earliest located primary text**, three years before the book:

> Basically, WalkingSkeleton says the first thing to build, architecturally, is
> a barely functional system that just connects all the pieces: WS to server to
> DB and back, or more if there are more systems to connect. A "hello world"
> that connects all the pieces of the system. A skeleton, one that barely runs,
> walks if you will. Over time you add flesh (functionality) to the connected
> system, and it always runs, or, runs more and better over time.

> In short, get all the pieces connected first, and then start making the
> pieces able to shift and grow with respect to the others.

Signed `-- AlistairCockburn` on the page.

And, replying on the same page to `JasonYip`'s suggestion that it is the same
as `ArchitecturalSpike` (Quoted):

> In my concept of WalkingSkeleton, the code is not thrown away. It is what you
> build upon to make the system.

> And, truth in advertising and all that, it is, of course, the case that the
> initial WalkingSkeleton, over the during of building the project, gets
> completely replaced… (not "thrown away", of course, because that would be
> wasteful ;-) but "replaced".

*(That last quote reproduces his typo `over the during of` verbatim.)*

### Sources tried and their outcome

| Route | Outcome |
| --- | --- |
| `alistaircockburn.com` (current site) | Not reached; site restructured — the 404s recorded in `RESOURCES.md` stand. |
| Wayback CDX index over `alistair.cockburn.us/*` | **Worked.** 40+ captures listed; two used above. |
| `c2.com/cgi/wiki?WalkingSkeleton` | 302 → `wiki.c2.com/?WalkingSkeleton`, which serves a JS shell — **no text retrieved**. Negative result. |
| *Crystal Clear* (2004) book text | Not obtained directly. The definition above is Cockburn quoting his own book on his own site — good enough for **Quoted**, but the page number is **omitted, not guessed**. |

---

## Part 2 — Tracer bullet (Hunt & Thomas)

### Publisher's own tip text

**Quoted.** Pragmatic Bookshelf's own tips page, <https://pragprog.com/tips/>.
**Edition confirmed:** the page names the **20th Anniversary Edition** in its own
text and lists **100 tips** (`Tip #1`–`Tip #100`) — the first edition had fewer.
So these page numbers are **2019 pages, not 1999 pages**. That matters: Cockburn
cites the tracer-bullet idea as *"(Hunt 1999)"*, the first edition. Never pair
his 1999 attribution with pg. 51 as if they were the same book.

> Tip #20, pg. 51:
> **Use Tracer Bullets to Find the Target**
> Tracer bullets let you home in on your target by trying things and seeing how
> close they land.

> Tip #21, pg. 57:
> **Prototype to Learn**
> Prototyping is a learning experience. Its value lies not in the code you
> produce, but in the lessons you learn.

**Chapter located** — page numbers 51 and 57 are the publisher's own.

### The authors in their own words

**Quoted.** Bill Venners' interview with Andy Hunt and Dave Thomas,
"Tracer Bullets and Prototypes", Artima.
<https://www.artima.com/articles/tracer-bullets-and-prototypes>
**Metadata verified** against the raw page body: byline `Bill Venners`, date
line `April 21, 2003`, and a `Part I`…`Part IX` + `Summary` navigation strip
placing this instalment at **Part VIII**.

Dave Thomas, on what the analogy buys you:

> Instead, the tracer bullet analogy says, "Let's try and produce something
> really early on that we can actually give to the user to see how close we
> will be to the target. As time goes on, we can adjust our aim slightly by
> seeing where we are in relation to our user's target." You're looking at
> small iterations, skeleton code, which is non-functional, but enough of an
> application to show people how it's going to hang together.

Andy Hunt, under the heading **"Starting with a Skeleton Application"** — this
is the sentence that makes the walking-skeleton overlap explicit:

> Central to tracer bullet development is the idea of a skeleton application,
> in which one thin line of execution goes end to end. In a skeleton
> application, you have some bit of functionality—even if it's just the
> equivalent of "Hello, world!"—that goes all the way from the UI, through
> business logic, through whatever else is in the middle, all the way to a
> database. It may do nothing more than put a checkbox value into the database
> as a Boolean. Whatever the skeleton application does, it does end to end,
> skeletally thin.

Dave Thomas, drawing the prototype boundary:

> Prototypes by their nature are not designed to be long lasting code.
> Prototypes are designed to be thrown away. They're one-offs. It is
> inappropriate to over-engineer a prototype. A prototype is like a town in a
> western movie. It's all facade. There's nothing behind it. You cannot move in
> and raise a family in one of those houses.

### The one quote we could NOT promote

The most-cited sentence from the book —

> "Tracer code is not disposable: you write it for keeps. It contains all the
> error checking, structuring, documentation, and self-checking that any piece
> of production code has. It simply is not fully functional."

— reached us **only through secondary aggregators and reading-notes blogs**,
never through the book text or a publisher-hosted excerpt.
**Tier: `[unverified]` — quoted-by-secondary, not verified against the book.**
Do not cite it as `Quoted` in a lesson. What was tried: `archive.org`
`advancedsearch` for a lendable copy (`numFound: 0`), the `hits_inside`
full-text API (timed out / no result), and `pragprog.com` for a free sample
chapter (only the tips list is public). The *content* of that sentence is
independently supported at **Quoted** tier by the Artima interview above and by
Cockburn's skeleton-vs-spike passage, so lesson 0008 loses nothing by dropping
it.

---

## Part 3 — Vertical slice

### Verdict: no single coiner located

**`[unverified]`.** Unlike the other two, "vertical slice" as a *work-slicing*
term has **no located coiner**. It circulates through agile story-splitting
practice with no attributable first use found in this pass. **Do not assign it
a coiner in lesson 0008.**

**A homonym trap worth recording.** "Vertical Slice Architecture" — a *code
organisation* pattern, popularised by Jimmy Bogard around 2018 — is a
**different concept** that shares the words. It is about where code lives in a
repository (feature-shaped folders instead of layer-shaped ones), not about how
work is sliced for delivery. Conflating the two would be a real defect.
**Metadata `[unverified]`** — the Bogard attribution comes from a secondary
community site, not from Bogard's own post.

**The negative was probed, not assumed.** A second targeted pass looked for a
coiner among the obvious candidates — Cockburn's own **Elephant Carpaccio**
exercise (which is *about* slicing stories into "really thin vertical slices",
and which secondary sources credit to Cockburn) and Bill Wake's story-splitting
work. Neither yielded a source in which anyone **defines or claims** the term.
Elephant Carpaccio *uses* the phrase as already-understood vocabulary, which is
evidence the term predates it. **`[unverified]` stands.**

### How the skills repo itself uses the terms

**Quoted**, from upstream `mattpocock/skills` (local read of `../skills`, repo
at `2ab9580`). The repo **never uses "walking skeleton"** and treats the other
two as one thing:

`docs/engineering/to-tickets.md:17` —

> Every ticket is a **tracer bullet** — a thin *vertical* slice that cuts
> through all integration layers end-to-end (schema, API, UI, tests), never a
> horizontal slice of one layer. A completed slice is demoable or verifiable on
> its own, which is what makes each ticket safe to hand to an agent.

`docs/engineering/to-tickets.md:40` —

> A **horizontal** slice ships one layer of the change — all the schema, or all
> the API — and nothing works until every layer lands. A **vertical** slice, the
> tracer bullet, ships one narrow path through *every* layer at once, so it can
> be demoed the moment it's done.

`skills/engineering/tdd/SKILL.md:30` —

> Work in **vertical slices** instead — one test → one implementation →
> repeat, each test a **tracer bullet** that responds to what the last cycle
> taught you.

Note the **scale shift**: in `to-tickets` a tracer bullet is a *ticket*; in
`tdd` it is a *single test*. Same word, two granularities, in the same repo.

Pocock's own published page, <https://www.aihero.dev/tracer-bullets> (**Quoted**,
raw fetch; page carries no byline or date on the rendered HTML):

> The concept of a tracer bullet comes from The Pragmatic Programmer. It's a
> small, end-to-end slice of functionality that touches all the layers of your
> system at once.

> Instead of building horizontal layers in isolation, you build one tiny
> vertical slice

So Pocock uses "vertical slice" as the *shape* and "tracer bullet" as the
*name*, and attributes the name correctly to Hunt & Thomas.

### The precise distinction — as the authors themselves state it

Lesson 0006 must separate these. The defensible separation, built only from
Quoted text above:

| | **Walking skeleton** (Cockburn) | **Tracer bullet / tracer code** (Hunt & Thomas) | **Vertical slice** |
| --- | --- | --- | --- |
| **What it names** | An **artifact**: the first thing you build. | A **method**: iterate toward a moving target using feedback. | A **shape**: cutting through every layer rather than along one. |
| **Cardinality** | Normally **one**, at the start of the project. | **Many**, fired repeatedly ("you may realize, you're off by a little bit"). | Many; a property any unit of work can have. |
| **What it must connect** | *"link together the main architectural components"* — architecture-first. | *"one thin line of execution goes end to end"* — function-first. | All integration layers, per the repo's own usage. |
| **Disposable?** | No — *"permanent code, built with production coding habits"*. | No — a skeleton you grow one feature at a time. (A **spike** / **prototype** is the disposable one, in both authors' vocabularies.) | Not addressed by the term. |
| **Coiner located?** | **Yes** — Cockburn, quoted above. | **Yes** — Hunt & Thomas, quoted above. | **No — `[unverified]`.** |

**The one distinction that is genuinely load-bearing:** the walking skeleton is
*singular and architectural* — it exists to prove the components can be wired
together, and Cockburn says it "need not use the final architecture". The
tracer bullet is *plural and directional* — it exists to shorten the feedback
loop toward a target you cannot fully specify. You build **one** walking
skeleton; you fire **many** tracer bullets. "Vertical slice" describes the
cross-section shape both of them happen to have, which is why the three get
confused.

**And Cockburn's own verdict on the relationship, quoted above, is the
strongest available evidence:** he calls tracer bullets *"other names for
similar sorts of ideas"* — kinship, explicitly not identity. Lead lesson 0008
with that sentence; it is a primary source stating the very relationship the
lesson has to teach.

---

## Part 4 — OpenAI's "Harness engineering" post

### Verdict

**Resolved — reached at `Quoted` tier via the Wayback Machine.** The live URL
still defeats automated fetch; the archive does not.

### What was tried

| Route | Result |
| --- | --- |
| `WebFetch` on `https://openai.com/index/harness-engineering/` | **HTTP 403.** |
| `curl` with a desktop Chrome user-agent | **HTTP 200 but useless** — 10 KB body reading `Enable JavaScript and cookies to continue` (bot interstitial). |
| `https://openai.com/index/introducing-harness-engineering/` | **HTTP 404** — that slug does not exist. |
| **Wayback Machine** | **Worked.** **61 rows** in the CDX index for this URL as of 2026-08-01; many `200`, interleaved with `403` captures where the crawler hit the same interstitial. |

Two captures were extracted and **diffed against each other** to rule out a
post-publication edit of the figures:

- **2026-02-11 22:15:55 UTC** (`20260211221555`) — same day as publication.
  <https://web.archive.org/web/20260211221555/https://openai.com/index/harness-engineering/>
- **2026-07-29 08:01:21 UTC** (`20260729080121`) — latest `200` capture.
  <https://web.archive.org/web/20260729080121/https://openai.com/index/harness-engineering/>

**All four figures are byte-identical across the two captures.** No silent edit.

### Metadata

**Metadata verified.** Title: *Harness engineering: leveraging Codex in an
agent-first world*. Byline: **Ryan Lopopolo, Member of the Technical Staff**.
Date line: **February 11, 2026**. Category: Engineering. Publisher: OpenAI.

### The figures — now Quoted, with their qualifiers intact

**Quoted:**

> Over the past five months, our team has been running an experiment: building
> and shipping an internal beta of a software product with 0 lines of
> manually-written code.

> Five months later, the repository contains on the order of a million lines of
> code across application logic, infrastructure, tooling, documentation, and
> internal developer utilities. Over that period, roughly 1,500 pull requests
> have been opened and merged with a small team of just three engineers driving
> Codex. This translates to an average throughput of 3.5 PRs per engineer per
> day, and surprisingly the throughput has increased as the team has grown to
> now seven engineers.

> The first commit to an empty repository landed in late August 2025.

> We estimate that we built this in about 1/10th the time it would have taken
> to write the code by hand.

**Three qualifiers that must travel with the numbers.** Dropping any of them
overstates the post:

1. It is **"on the order of a million lines"** — an order-of-magnitude
   statement, not a measured `1,000,000`.
2. It is **"roughly 1,500"** pull requests.
3. **"three engineers" is not the team's final size.** The post says the team
   "has grown to now seven engineers". The three-engineer figure applies to the
   five-month window that produced the ~1,500 PRs; citing "a three-person team"
   without the growth clause misrepresents it.
4. The **1/10th** speed-up is explicitly *"We estimate"* — a self-reported
   estimate by the team under study, not a measurement. **Never cite it as
   measured.** It is the same class of claim as the "15–35% slower" figure in
   Nagappan et al. that `02-se-literature.md` already flags as
   *"subjectively estimated by management."*

### Substantive content worth having (Quoted)

The post's actual argument, beyond the headline numbers, is about context
budget — and it corroborates the workspace's progressive-disclosure thesis from
an independent vendor:

> Context is a scarce resource. A giant instruction file crowds out the task,
> the code, and the relevant docs—so the agent either misses key constraints or
> starts optimizing for the wrong ones.

> Too much guidance becomes non-guidance. When everything is "important,"
> nothing is. Agents end up pattern-matching locally instead of navigating
> intentionally.

> It rots instantly. A monolithic manual turns into a graveyard of stale rules.
> Agents can't tell what's still true, humans stop maintaining it, and the file
> quietly becomes an attractive nuisance.

> So instead of treating AGENTS.md as the encyclopedia, we treat it as the
> table of contents.

> A short AGENTS.md (roughly 100 lines) is injected into context and serves
> primarily as a map, with pointers to deeper sources of truth elsewhere.

> give Codex a map, not a 1,000-page instruction manual

> We regularly see single Codex runs work on a single task for upwards of six
> hours (often while the humans are sleeping).

### Coinage ledger note — sequence, not causation

> **⚠️ CORRECTED 2026-08-01.** This note read *"`RESOURCES.md` records Mitchell Hashimoto **self-identifying with** `harness engineering` on 5 Feb 2026."* **He does not self-identify as coining it** — the recorded quote was truncated one sentence early, and the next sentence is *"I don't need to invent any new terms here; if another one exists, I'll jump on the bandwagon."* The term now has **no located coiner**. See `13-library-sweep.md` and `03-agent-engineering.md` §7.2.

**Mitchell Hashimoto** publishes the fullest statement of `harness engineering`
on **5 Feb 2026**, adopting rather than coining it. This OpenAI post is dated
**11 Feb 2026** — **six days later**. Record the **sequence** as a fact; any
claim of influence in either direction is **`[unverified]`** and must not be
asserted. The post itself does not claim to coin the phrase and does not cite
Hashimoto. It remains true that the term is **not Pocock's**.

The sequence is also **longer than these two dates suggest**: the compound
already appears in Horthy's talk published **2 Dec 2025**, two months before
Hashimoto. Do not present 5 Feb 2026 as an origin point.

---

## What this changes in `RESOURCES.md`

Two `## Gaps` bullets are now stale and should be struck through and marked
RESOLVED, following the pattern the Feathers entry already set:

- **Cockburn's walking skeleton** — resolved; archived primary text quoted above.
- **OpenAI's "Harness engineering" post** — resolved; all four figures now
  `Quoted` from two independent Wayback captures, **with their qualifiers**.

One gap **opens**: `vertical slice` has no located coiner. That is an honest
negative result, not an omission.
