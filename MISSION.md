# Mission: Software-engineering discipline as agent architecture (Matt Pocock's engineering skills)

## Why

Rodrigo runs agentic refinement-and-implementation flows daily and wants them to stop degrading. Matt Pocock's engineering skills encode classic software-engineering discipline — seams, deep modules, ubiquitous language, vertical slices, grilling to shared understanding — as the control structure for those flows. He needs to (1) apply them to his own Java work, (2) argue for them with real evidence to teams and students, and (3) build his own skills and harnesses on the same principles.

The through-line: **the same properties that make code tractable for a human maintainer make a codebase and a workflow tractable for an LLM.** A small interface is a small context. A seam is a place to cut a task. A glossary is a shared prompt. He wants that correspondence proven, not asserted.

## Success looks like

- Can define every term in the glossary from memory — seam, deep module, leverage, locality, vertical slice, tracer bullet, ubiquitous language, bounded context, shared understanding — and name the primary source for each (Feathers, Parnas/Ousterhout, Evans, Beck, Hunt & Thomas).
- Can look at a Java class and say where the seam should go, why, and what gets tested through it — then write the test first.
- Can run a grilling session on his own plan and recognise when shared understanding has actually been reached versus when the LLM is agreeing to be agreeable.
- Can explain, with cited numbers, why long context degrades agent output (context rot, lost-in-the-middle) and how progressive disclosure, seams, and durable state files counter it.
- Can trace the entire stateful flow — `grill-with-docs → to-spec → to-tickets → implement → code-review`, with `implement` nesting `tdd` per slice, and the on-ramps (`triage`, `wayfinder`, `diagnosing-bugs`) entering at their correct points — naming what artifact carries state across each hop and why that beats holding it in the context window.
- Can teach any of the above to a mixed-seniority audience, with the primary source in hand.
- Can author his own skill that applies these principles (thin `SKILL.md`, supporting files loaded on demand, state externalised to files).

## Constraints

- **Language**: lessons and reference documents in pt-BR; technical terms in English, verbatim, never translated (seam, deep module, vertical slice, tracer bullet, context rot). Research documents in `docs/` in English.
- **Code language**: Java, throughout. One running domain — **banking: Account, Transfer, Balance, Ledger** — carried across every lesson so each new concept lands on familiar ground.
- **Depth range**: every concept explained from the five-year-old intuition up to the PhD-level primary source. Both ends, every time — the intuition earns the attention, the citation earns the trust.
- **Evidence over assertion**: claims are cited. Empirical results are reported honestly, including the studies that found no effect. Practitioner coinages are labelled as coinages, not dressed as literature.
- **Working memory**: one lesson teaches one thing and is completable quickly. Breadth lives in the curriculum map and the glossary, not inside a single lesson.

## Out of scope

- Learning Claude Code's operational surface (hooks, MCP wiring, plugin publishing) as an end in itself — only where it illustrates harness engineering.
- TypeScript, despite it being Matt's usual medium. Concepts get ported to Java.
- Rewriting or contributing to the upstream skills repo. This workspace studies it; it does not fork it.
