# Curriculum map

The full path from "what is a seam" to "I can build my own skill." Lessons are built one at a time, in this order, each one a single tangible win. This map is the plan; it is not the promise that all of it gets built at once.

Every lesson follows the same six-beat structure, which is how the mission's "five-year-old to PhD" constraint is satisfied without splitting the audience:

| Beat | What it does |
|---|---|
| **Intuição** | The five-year-old pass. One physical, non-code analogy. No jargon. |
| **Mecanismo** | The precise definition, in the repo's own words, quoted. |
| **Java** | The same idea in code, in the banking domain, bad-then-good. |
| **Fonte primária** | The PhD pass. Who invented it, in what book, what page, and what critique exists. |
| **Paralelo IA** | Why the same property that helps a human maintainer helps an LLM agent. Cited, or flagged as our own inference. |
| **Retrieval** | Quiz or free-recall. Effortful retrieval, not re-reading. |

Terminology is bound by `reference/0001-glossary.html`. Once a term is defined there, no lesson redefines it differently.

---

## Track I — Structure: where you cut

The unit of everything. If you can't place a seam, you can't test, can't slice, can't delegate to an agent.

**0001 — Seam** *(built first)*
The place you can change behaviour without editing in that place. Feathers' definition, the repo's two uses of it (`codebase-design`: where the interface lives; `tdd`: where the test observes), and the rule that decides whether a seam is real: *one adapter means a hypothetical seam, two means a real one.* Java: a `TransferService` that constructs its own clock and gateway, versus one that accepts them. AI parallel: a seam is where a task can be handed to a sub-agent with a bounded contract.

**0002 — Deep vs shallow module** *(built)*
Depth as *leverage at the interface* — behaviour per unit of interface learned, and **a property of the interface, not the implementation**. The Java demonstration is the argument: `ShallowLedger` and `DeepLedger` have **byte-identical method bodies** (verified mechanically); the delta is five `private` keywords plus `post(Transfer)`. Six methods + an ordering rule + an invariant on the caller become one call — and three wrong sequences that compiled stop being expressible. The repo rejects a lines-of-implementation-to-lines-of-interface ratio ("rewards padding the implementation") and attributes it to Ousterhout — **the rejection is sound, the attribution is what fails.** `ratio` occurs zero times in all 188 pages, Figure 4.1 carries no numbers or axes, and he rejects line count twice; the ratio he *does* write is `functionality/(interface complexity)`, neither term a line count. So `depth-as-leverage` restates him rather than correcting him, and the lesson teaches how a folk simplification gets attributed back to the author it distorts. Settled in `docs/research/05-ousterhout-depth.md`. The deletion test appears once, applied, and hands the technique to 0005. AI parallel: `schedule_event` collapsing three tool calls into one — the same move as `post(Transfer)` — plus Anthropic's measured 150,000 → 2,000 tokens, which makes interface cost literal instead of cognitive.

**0003 — Interface is more than the signature**
The repo's definition is unusually strict: invariants, ordering constraints, error modes, required configuration, performance characteristics — *everything a caller must know to use the module correctly*. Java: two methods with identical signatures and wildly different interfaces. Parnas' information hiding as the origin. AI parallel: an under-specified interface is where an agent hallucinates; the unwritten facts are exactly what it guesses at.

**0004 — Adapter, and internal vs external seams**
Role versus substance. Small adapter + large implementation (a Postgres repository) versus large adapter + small implementation (an in-memory fake). Ports-and-adapters as the named pattern. Why a deep module may be internally composed of mockable parts that are *not* part of its interface. Java: `AccountRepository` with a Postgres and an in-memory adapter.

**0005 — The deletion test and finding deepening opportunities**
The working technique from `improve-codebase-architecture`: scanning a codebase for places depth is missing. Locality and leverage as the two payoffs. Java: refactor a pass-through service out of existence.

---

## Track II — Process: how you move

**0006 — Vertical slice, tracer bullet, walking skeleton**
Three related and frequently confused ideas, separated precisely — led by Cockburn's own sentence naming the neighbours and hedging them as *"similar sorts of ideas"*, and corroborated by a second, independent voice: **Clint Shank, axiom #60 of *97 Things Every Software Architect Should Know* (O'Reilly, 2009), "what Alistair Cockburn calls a Walking Skeleton"** — valuable precisely because it is not Cockburn talking about himself. `vertical slice` still has **no located coiner** and must not be assigned one. The repo's anti-pattern of **horizontal slicing** — "bulk tests verify *imagined* behavior" — and why writing all tests first is a distinct failure from writing no tests. Java: one slice of `transfer` end-to-end versus a shelf of speculative tests.

**0007 — Red-green-refactor, and the rule that refactoring is not in the loop**
Beck's loop, plus the repo's sharp deviation: *"Refactoring is not part of the loop. It belongs to the review stage."* Why that deviation exists in an agent context. The empirical record on TDD reported honestly — including the replications that found no effect.

**0008 — Pre-agreed seams: the test-planning contract**
"No test is written at an unconfirmed seam." Why agreeing the seams up front is how effort lands on critical paths rather than on every edge case. This is the hinge between Track I and Track II, and the single most transferable practice for agentic work.

**0009 — Test anti-patterns: implementation-coupled and tautological**
The tell for each. The tautological test as a specifically LLM-shaped failure — an agent asked to write a test for code it just wrote will recompute the expected value the same way. Java: `assertEquals(a + b, add(a, b))`, and its subtler cousins. Mocking policy; Testcontainers versus mocks.

**0010 — Diagnosis as a loop, not a guess**
`reproduce → minimise → hypothesise → instrument → fix → regression-test`. Delta debugging as the formal ancestor of "minimise". Why an agent that skips *reproduce* produces confident nonsense.

---

## Track III — Language: how you agree

**0011 — Ubiquitous language and the glossary as an artifact**
Evans' original claim. The repo's operational form: `CONTEXT.md`, "totally devoid of implementation details… a glossary and nothing else." **Do not teach this as a divergence from Evans** — it was checked and there isn't one. Evans insists on speech (his section is titled *Modeling Out Loud*: *"We naturally come to share the language that we speak in a way that never happens with diagrams and documents"*) but explicitly does not forbid documents (*"a group of any size will probably need the stability and sharability of some written documents"*), and his first liveness criterion — *"a document shouldn't try to do what the code already does well"* — is what the repo satisfies nearly word for word. The teachable point is **ours, and flagged as ours**: the repo swaps a self-correcting medium for one with no self-correction, which makes Evans' liveness test *more* load-bearing, not less. The `_Avoid_:` convention — recording rejected synonyms is as valuable as recording the chosen term. Java: renaming a class after a glossary fight, and what stopped being ambiguous.

**0012 — Bounded context and context maps**
When one glossary is not enough. `CONTEXT-MAP.md` as the repo's form. Java: `Account` meaning two different things in ledger and in onboarding.

**0013 — ADRs: the three-part test**
The repo's unusually strict gate — hard to reverse **and** surprising without context **and** the result of a real trade-off. Nygard's original post. Why an ADR is durable state that survives a context window.

**0014 — Grilling: reaching shared understanding**
The whole practice: one question at a time, look up facts but never decisions, recommend an answer with every question, and *do not act until shared understanding is confirmed*. Why this is the highest-leverage human-LLM protocol available, and what it borrows from Socratic method, design review, and Three Amigos. Includes an actual grilling transcript to analyse.

---

## Track IV — The AI parallel, made explicit

**0015 — Context rot, with numbers**
What actually degrades and by how much. The Chroma report's experimental design; Liu et al.'s U-shaped curve. Not "long context is bad" — the specific, measured shape of the failure.

**0016 — Progressive disclosure, both lineages**
Nielsen's HCI origin and its adoption in skill design: metadata first, body on demand, supporting files last. Why `codebase-design/SKILL.md` links out to `DEEPENING.md` instead of inlining it — the skill is itself a deep module.

**0017 — Durable state beats a long context**
The core argument of the whole curriculum. `CONTEXT.md`, ADRs, issues, triage labels, handoff docs, wayfinder maps — each one a decision paid for once and reread cheaply forever. Map the full write/read matrix of the stateful chain.

**0018 — Harness engineering**
Tools, permissions, hooks, sub-agent isolation. Deep tools versus many shallow tools, argued from Track I. What the term is actually sourced to, and what is our own inference.

**0019 — The stateful flow end to end**
The verified chain: `setup-matt-pocock-skills` (once) → `grill-with-docs → to-spec → to-tickets → implement → code-review`, where `implement` **nests** `tdd` per slice and **calls** `code-review`. Plus the on-ramps, which are where most people get the shape wrong: `triage → implement`; `wayfinder → to-spec` (**never** straight to `implement`); and `diagnosing-bugs → improve-codebase-architecture →` back to `grill-with-docs` — the only cycle in the graph. Walked once on a real banking feature, naming the artifact at every hop.

**0020 — Writing your own skill**
The synthesis. Apply Tracks I–IV to authoring a skill: thin `SKILL.md` (small interface), supporting files (deep implementation), invocation policy (the seam), state externalised to files. The final win of the mission.

---

## Reference documents

Built alongside the lessons, revisited long after them.

- `0001-glossary.html` — every term, the repo's verbatim definition, the primary source, the rejected synonyms. **Binding on all lessons.** Built before lesson 0001.
- `0002-stateful-flow.html` — the flow graph and the artifact write/read matrix.
- `0003-java-patterns.html` — the seam/adapter/test patterns in Java, copy-pasteable.
- `0004-bibliography.html` — the reading path, ordered, with what each source is for.
- `0005-grilling-protocol.html` — the grilling checklist, for use during a real session.

## Research corpus

Persisted under `docs/research/`, produced by delegated agents:

- `01-skills-corpus.md` — authoritative harvest of the repo: term ledger, stateful flow graph, per-skill dossier, quote bank. All claims `file:line`-cited.
- `02-se-literature.md` — verified software-engineering literature map, including honest reporting of empirical evidence.
- `03-agent-engineering.md` — verified AI/agent primary sources, with measured numbers.
- `04-matt-pocock-sources.md` — the author's own primary sources and a coinage ledger.
- `05-ousterhout-depth.md` — settles whether Ousterhout defines depth as a ratio. He does not define a *line-count* one.
- `06-walking-skeleton-and-harness-post.md` — settles Cockburn's `walking skeleton` (archived primary text), separates it from `tracer bullet` and `vertical slice` in each author's own words, and recovers OpenAI's *Harness engineering* post from the Wayback Machine with all four figures quoted and qualified.
- `07-communities.md` — four verified venues for testing this work on real practitioners, plus what could not be verified from outside.
- `08-ddd-evans.md` — Evans on `ubiquitous language` and `bounded context`, chapters confirmed, page numbers labelled *manuscript* because the local copy is the April 2003 final manuscript and not the printed book. Includes a hypothesis the agent set out to prove and then disproved.
- `09-supplementary-sources.md` — provenance checks on the two supplementary sources: the IJSRP walking-skeleton paper (no DOI, no DOAJ, zero empirical data — kept only as a specimen of evidence drift) and *97 Things* (archive-confirmed as the unedited wiki contributions, so a finding aid and never the citation of record).
