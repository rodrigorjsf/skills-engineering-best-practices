# Handoff: lesson 0006 shipped; checkpoint + sweep + reference docs agreed, not started

**Created:** 2026-08-01
**Branch:** `main` — working tree **clean**, pushed, `main...origin/main` in sync
**Repo:** `/home/rodrigo/Workspace/skills-engineering-best-practices`

---

## Summary

Lesson 0006 (dependency inversion) was researched, built, verified, retrofitted into lessons 0001–0005, and pushed. Then a **grilling session** settled seven decisions about what happens next. **Nothing from those seven decisions has been executed.** Start by building the checkpoint.

---

## Work completed this session

- [x] Three delegated research passes → `docs/research/10`, `11`, `12`
- [x] `lessons/0006-dependency-inversion.html` + `code/0006-dependency-inversion/InversionDemo.java`
- [x] Glossary **§6 — "Vocabulário clássico que o repositório *não* usa"** (DIP, DI, option inversion, YAGNI, DRY, wrong abstraction, Need-Driven Development)
- [x] Cross-link addenda in lessons 0001–0005
- [x] Curriculum renumbered **+2** from old 0006 down (vertical slice is now **0008**; map ends at **0022**)
- [x] `learning-records/0003-dip-enters-the-course-and-shifts-the-map.md`
- [x] `RESOURCES.md`: 14 new primary sources + 5 new gaps + renumber repair
- [x] Advisor review → 4 fixes (figcaption contradicted its own figure; unpinned Wayback URL; undisclosed `given`; wiki-link)

**Commits:** `33d73ba` `b243818` `37b7177` `e245c2f` `b6cd9bc`

### Findings worth not re-deriving

| Finding | Evidence |
|---|---|
| Repo teaches **injection**, never **inversion** | Over 113 files: `dependency inversion` **0**, `SOLID` (acronym) **0**, `DRY` **0**, `dependency injection` **1**, `YAGNI` **1**, vs `seam` **77**, `interface` **160** |
| DI ≠ DIP, proved by silence | *"dependency inversion"* occurs **0×** in Fowler's `injection.html`; his bliki has **no DIP entry** |
| **The two-adapter rule does not restrain DIP** | `DEEPENING.md:29` says *"(typically production + test)"* — a test double **is** the second adapter, so almost everything passes. The real brake is the 4-category taxonomy at `DEEPENING.md:5-25`, corroborated from the test side by `tdd/mocking.md:3,10-14`. **Neither file cites the other — the composition is ours** |
| YAGNI does **not** forbid a test-driven seam | Fowler, verbatim: *"it does not apply to effort to make the software easier to modify"* |
| Martin's DIP article, read in full | Pinned capture `web.archive.org/web/20151204043748/…/dip.pdf` — 12 pp., quotes at **p.6 / p.8 / p.10**, statement is in **SMALL CAPS** in the original. **Never date it "May 1996"** |

---

## The seven decisions (all confirmed, none executed)

1. **`ai-study-library` = finding aid only.** Leads chased to the primary source; **nothing from it is ever cited.** Precedent: the *97 Things* verdict.
2. **Next work = checkpoint + library sweep + reference docs.** **Lesson 0007 (YAGNI/DRY) waits for calibration** — evidence is fully gathered in `docs/research/11-*`.
3. **`checkpoints/` is a new artifact type**, own numbering, **one per closed track**. First: `checkpoints/0001-trilha-I.html`.
4. **Hybrid return channel.** MCQ tallied in memory (the widget already fires `quiz:answered` and sets `quiz.dataset.correct`) + a copy button; **free-recall prose is the primary signal.** `localStorage` is unreliable over `file://` — do not rely on it.
5. **`reference/0004-bibliography.html` exists**, under a hard rule: **never writes ISBN, page, year, publisher, or locator caveat** — those link to `RESOURCES.md`. Only order, argumentative thread, and one line of "what you gain reading this now". Rationale: a source *registry* and a reading *path* are different knowledge (Hunt & Thomas), so this is not duplication.
6. **Delegation cut by document nature.** `reference/0002-stateful-flow` and `0003-java-patterns` → **fully delegated** (extraction, verifiable against `file:line`). `0004-bibliography` and `0005-grilling-protocol` → **hand-authored** (curatorial).
7. **Sweep scope: triage 59, chase the gap-touching ones.** Output: `docs/research/13-*`.

---

## Next steps

### Immediate — build the checkpoint first, alone

`checkpoints/0001-trilha-I.html` is the only artifact needing **Rodrigo's** time, so it ships before anything else; he answers it while agents run.

- **New questions, interleaved** — must require crossing two lessons. The 12 existing per-lesson quizzes stay untouched (he has never answered them, so they remain novel).
- Covers: seam · deep vs shallow · interface beyond the signature · adapter & internal seams · deletion test · DIP.
- Obeys `.claude/rules/lessons.md`: **uniform word count per quiz option**, sidenote is a sibling of `.page`, no hard-coded `px`, `.wide` only as explicit opt-in.
- Add `checkpoints/` to the file-conventions table in `CLAUDE.md` **in the same commit**.

### Then — three background agents in parallel, two documents by hand

**Agents (background, persisted files, short reports):**
1. **Sweep** → `docs/research/13-*`. Extract the 59 URLs mechanically first (`grep -rh '^\*\*Source:\*\*' docs/ ai/docs/ | sed 's/^\*\*Source:\*\* *//' | sort -u`), tier by domain, then **fetch and verify only gap-touching ones**: the Horthy *No Vibes Allowed* talk + the 40% figure, progressive disclosure (lesson **0018**), harness engineering (**0020**), and the MRCR *"smart zone ends"* table.
2. **`reference/0002-stateful-flow.html`** — flow graph + artifact write/read matrix, extracted from `../skills`.
3. **`reference/0003-java-patterns.html`** — from the 6 lessons and 6 runnable Java programs.

**Main thread, by hand:** `0004-bibliography` (under decision 5) and `0005-grilling-protocol` — **using this very session as the real grilling transcript**, which the curriculum asks for.

Every agent prompt must carry: four-tier evidence bar, **no guessed locators**, never fabricate a quote/date/URL, and **download the artifact rather than citing the corpus entry that cites it** (`.claude/rules/research.md`).

### Blocked on Rodrigo

- Answering the checkpoint. **No lesson should be built until it comes back** — lessons 0001–0006 were all calibrated blind and `learning-records/` holds zero evidence of understanding.

---

## Parked, deliberately

**`sandcastle` and the three authored skills as the subject of lessons 0021–0022.** `/home/rodrigo/Workspace/ai-study-library` contains `sandcastle/` (a real TypeScript project with `CONTEXT.md`, `AGENTS.md`, `.changeset/`) and `ai/skills/{dream,pdf,study-plan-generator}`. **MISSION success #7 — *"Can author his own skill"* — is already met three times over.** Using them collides with `Out of scope: TypeScript` — but the final lessons are about `SKILL.md`/`CONTEXT.md`, which are not code. **Requires a MISSION change + learning record + Rodrigo's confirmation.** Revisit near the end of Track II.

---

## Gotchas

- **Every number a demo prints must be derived from the JVM or labelled `given`.** This has been the repeated defect class (0002's summary table, 0003's `3 of 6`, 0004's sections C/D).
- **Mermaid backticks wrap the whole label**, not the line break — otherwise they render literally. Now in `.claude/rules/diagrams.md`.
- **A `.figure.wide` collides with an adjacent sidenote.** Move the sidenote a paragraph away.
- **Dark-theme screenshots** need `window.matchMedia` stubbed in a temporary `lessons/_darkprobe.html` (delete after). `data-theme` alone is overwritten by `lesson.js`.
- **Never write into `../skills/`.** Read-only upstream.
- **`docs/research/*.md` contains `./skills/...` strings inside quoted material** — never rewrite them.
- `web.archive.org` is blocked for `WebFetch`; use `curl` instead.

---

## Verification commands

```bash
cd /home/rodrigo/Workspace/skills-engineering-best-practices
for d in code/*/; do (cd $d && java $(basename $(ls *.java|head -1))); done   # 6 demos
grep -rioF "dependency inversion" ../skills/skills | wc -l                    # expect 0
```

Mechanical checks (quiz word-count uniformity, nested sidenotes, `px`, local paths, broken links, glossary anchors) and the verbatim-stdout check over every fenced output block were run this turn: **0 failures, 15 blocks / 138 lines matched.** Re-run both before any "done" claim.

**Derived counts — re-measure, never remember:** 6 of 22 lessons · 12 research files · 186 `[unverified]` marks.

---

## Open questions

- [ ] Does the checkpoint reveal a calibration miss big enough to change Track II's order?
- [ ] Does `sandcastle` become the worked example for 0021–0022? (MISSION change)
- [ ] Is the Horthy *"an academic concept called the 'dumb zone'"* line verbatim? If so it **strengthens** the existing verdict — he does not claim the coinage — and supplies the missing origin of the 40% figure. Currently only in secondary distillations.

---

_Handoff written at the end of a grilling session. Seven decisions confirmed; execution deliberately not started._
