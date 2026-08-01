# Notes

Working notes and stated preferences. Not a journal — only things that change how lessons get built.

## Stated preferences (2026-07-31, session 1)

- **Language split**: lesson + reference HTML in pt-BR; every technical term stays English verbatim (`seam`, not "costura"; `deep module`, not "módulo profundo"). Research files under `docs/` in English.
- **Running domain**: banking — `Account`, `Transfer`, `Balance`, `Ledger`. Chosen for strong invariants (money can't be created or destroyed), which makes TDD examples and illegal-state examples land hard. Every lesson's Java code extends this same domain rather than inventing a new one.
- **Depth range**: "explain it to a five-year-old, then to a PhD." Each lesson carries both poles — an intuition pass and a primary-source pass. Don't drop either end.
- **Evidence bar is high**: he explicitly asked for *evidências reais*. Cite numbers. Report negative results. Mark coinages as coinages. A confident fabricated definition of one of Matt Pocock's own terms is the worst possible failure here.
- **Delegation**: he wants investigative work done by subagents, with research persisted to `docs/` and only a consolidated report reaching the main thread. Keep doing this — don't do broad research inline.
- **Scope per session**: full curriculum map, but build lessons one at a time.

## Workspace layout decisions

- Teaching workspace root is `/home/rodrigo/Workspace/skills-engineering-best-practices/`.
- **Source of truth is `../skills`** (`/home/rodrigo/Workspace/skills/`). A vendored Windows copy sat at `./skills/` until 2026-07-31; it was verified identical to upstream (`2ab9580`, no local commits/stashes/untracked work) and deleted. Don't re-create it — the copy arrived with `AGENTS.md` flattened from a symlink to a zero-byte file, CRLF in shell scripts, and 562 `Zone.Identifier` files.
- **Never write research into `../skills/docs/`** — that path is Matt's own published docs pages. Our research goes to `<workspace-root>/docs/research/`.
- This workspace is its own git repo, published at `github.com/rodrigorjsf/skills-engineering-best-practices` (public, MIT). The parent `/home/rodrigo/Workspace` is a separate, unrelated repo.
- Lesson citations point at **upstream URLs**, never at local `/home/rodrigo/...` paths, which would be dead links in a month.

## Teaching approach

- Glossary (`reference/0001-glossary.html`) is the anchor. Every lesson links terms back to it. Once a term is defined there, its wording is binding across all lessons.
- Two-pole structure per lesson: intuition → mechanism → Java → primary source → the AI parallel → retrieval quiz.
- Quiz rule: all answer options equal word count, ideally equal character count. Java-code answers make this easy to violate — check before shipping.
- Storage strength over fluency: every lesson after 0001 opens with a spaced-retrieval question about a prior lesson, unannounced.
