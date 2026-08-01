---
paths:
  - "docs/research/**/*.md"
---

# The research corpus

English, cited, `NN-topic.md`. Written by delegated agents; the main thread does
orientation and authoring, and does not re-run a search it delegated.

## Four tiers. Label every claim.

| Tier                  | Meaning                                                                                                  |
| --------------------- | -------------------------------------------------------------------------------------------------------- |
| **Quoted**            | Text was fetched; the quote is verbatim; a URL is given.                                                 |
| **Metadata verified** | Title/author/publisher/year/ISBN from a publisher page, DOI, or the author's own site.                   |
| **Chapter located**   | A real table of contents or searchable excerpt was seen. Chapter numbers without this tag are not given. |
| **`[unverified]`**    | Everything else. Stays visibly unverified.                                                               |

## Standing rules

- **No guessed locators.** If a table of contents could not be fetched, omit the
  chapter number rather than invent it.
- **No smoothing.** An unverified claim is never rewritten into confident prose.
- **Report empirical evidence honestly, including negative results.** The TDD
  literature contains replications that found no effect. Those get reported. A
  course that oversells its own thesis fails the "teach it with evidence" goal.
- **Separate three things and never blur them:** what a source says · what
  practitioners say · what *we* are inferring by analogy.

## The `./skills/...` trap

These files contain strings like `./skills/engineering/...` **inside quoted
material** — verbatim excerpts of the upstream repo's own README links. They are
quotes, not paths into this workspace. **Never rewrite them**, and filter
`docs/research/` out of any repo-wide path rewrite.

## Delegating

- Broad research goes to subagents, run in parallel in a single message,
  `run_in_background: true`.
- Each agent **writes a persisted file here** and returns a **short report**
  (under ~700 words) — never a file dump into the main context.
- Agent prompts must carry the non-negotiable rules explicitly: primary sources
  only, `[unverified]` for anything unconfirmed, never fabricate a
  quote/date/URL/timestamp, capture numbers.
