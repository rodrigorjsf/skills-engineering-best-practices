# 16 — Four gaps closed from artifacts already in hand

**Date:** 2026-08-01
**Scope:** the items that did not depend on the Track I checkpoint. Two from books now on the shelf, one from two arXiv PDFs finally opened, and one that turned out to have been closed all along.

---

## 1 · Tracer bullet — the sentence that had only ever reached us through aggregators

`RESOURCES.md` carried this as a named omission: *"the Pragmatic Programmer book text of 'Tracer code is not disposable…' — which reached us **only through secondary aggregators** and stays `[unverified]`."*

**Now read from the book.** *The Pragmatic Programmer: your journey to mastery*, 20th Anniversary Edition, © 2020 Pearson, `ISBN-13 978-0-13-595705-9`. **Topic 12, "Tracer Bullets"** — the book is organised by numbered Topics, not chapters, so that is the correct locator form.

This artifact is the **English original**, so the tier is **Quoted**, not `Quoted (translation)`.

> **Tracer code is not disposable: you write it for keeps.** It contains all the error checking, structuring, documentation, and self-checking that any piece of production code has. It simply is not fully functional. However, once you have achieved an end-to-end connection among the components of your system, you can check how close to the target you are, adjusting if necessary.

**Why this matters for lesson 0008.** The whole point of that lesson is separating three ideas the internet treats as synonyms. This sentence is the **discriminator**: a tracer bullet is *production code that is incomplete*, which is precisely what a throwaway prototype is not. Hunt & Thomas draw that line themselves in the following Topic — **Topic 13, "Prototypes and Post-it Notes"** — so the two are adjacent and contrasted **by the authors**, not by us.

The preceding paragraph is worth having too, because it is the worked example: a UI, a query library and a SQL generator wired end to end where *"all we could do was submit a query that listed all the rows in a table, but it proved that the UI could talk to the libraries."* That is a **walking skeleton** in Cockburn's sense, described by different authors without the term — record the convergence, **not** an influence claim.

---

## 2 · Agans' nine rules — verbatim, and the chapter map

`RESOURCES.md` listed *"Agans' full list of 9 rules"* among the things **deliberately omitted rather than guessed**. It is now on the shelf: *Debugging*, David J. Agans, **AMACOM**, `ISBN-13 978-0-8144-7457-0` (pbk) / `978-0-8144-2678-4` (ebook).

**Chapter 2, "The Rules—Suitable for Framing"** prints them as a framed list, carrying its own copyright line — `© 2001 DAVID AGANS · WWW.DEBUGGINGRULES.COM` <span>QUOTED</span>:

> UNDERSTAND THE SYSTEM
> MAKE IT FAIL
> QUIT THINKING AND LOOK
> DIVIDE AND CONQUER
> CHANGE ONE THING AT A TIME
> KEEP AN AUDIT TRAIL
> CHECK THE PLUG
> GET A FRESH VIEW
> IF YOU DIDN'T FIX IT, IT AIN'T FIXED

**The nine rules map one-to-one onto chapters 3–11**, which is the strongest possible corroboration that the list is the book's spine and not a marketing summary:

| # | Rule | Chapter |
|---|---|---|
| 1 | Understand the System | 3 |
| 2 | Make It Fail | 4 |
| 3 | Quit Thinking and Look | 5 |
| 4 | Divide and Conquer | 6 |
| 5 | Change One Thing at a Time | 7 |
| 6 | Keep an Audit Trail | 8 |
| 7 | Check the Plug | 9 |
| 8 | Get a Fresh View | 10 |
| 9 | If You Didn't Fix It, It Ain't Fixed | 11 |

Chapter 12 is *All the Rules in One Story*; chapter 14, *The View from the Help Desk*, restates seven of them for a support context.

**For lesson 0012.** Two rules do real work against the repo's diagnosis loop and should lead: **QUIT THINKING AND LOOK** (the anti-guess rule — the lesson's title is *"Diagnosis as a loop, not a guess"*) and **CHANGE ONE THING AT A TIME**. Note the date on the framed list: **© 2001**, four years before Feathers' *seam*.

---

## 3 · The two arXiv bodies — opened, and one number gets richer

Both papers were cited **from their abstracts only**. Both PDFs are now downloaded and searched.

### SHERLOC — arXiv `2606.24820`, 26 pages

**Metadata verified from the PDF itself.** *SHERLOC: Structured Diagnostic Localization for Code Repair Agents* — Hovhannes Tamoyan, Sean Narenthiran, Erik Arakelyan, Mira Mezini, Boris Ginsburg (**NVIDIA**, Santa Clara).

The abstract claim, confirmed in the body <span>QUOTED</span>: *"half their budget on locating faults before editing."*

**The 67% figure is confirmed — and it decomposes**, which the corpus did not have <span>QUOTED</span>:

> The dominant failure mode is **reasoning error (40%)**: the model explored the correct area, often viewing the ground-truth file, but ultimately selected a different file in its final answer. Combined with **close misses (27%, correct directory but wrong file)**, **67% of failures stem from picking the wrong file among nearby candidates** rather than from failing to reach the right area. Only **4% involve genuinely multi-file bugs** where the ground truth spans 3+ files.

**This strengthens lesson 0006's AI parallel rather than changing it.** The lesson infers that a one-implementor interface hurts because it adds a near-identical candidate to choose among, and flags the bridge as **ours**. The decomposition shows the dominant sub-mode is *the model saw the right file and still picked another* — which is a **discrimination** failure, not a **retrieval** failure. That is the shape the analogy needs, and it is now measured rather than assumed. **The bridge to indirection is still ours** — the paper varies nothing about codebase structure.

### Code Isn't Memory — arXiv `2606.22417`, 10 pages

*Code Isn't Memory: A Structural Codebase Index Inside a Coding Agent* — Bhola, Krishnan, Kurmala, NS. All four ablation numbers confirmed in the body <span>QUOTED</span>: localization acc@5 **44.3% → 84.5%**; resolve **41.9% → 50.4%**, *"paired Wilcoxon p = 0.003"*; cost **$2.30 vs $2.92** per solve.

The significance test is a detail the abstract-only citation lacked and is worth carrying — it is the difference between a reported delta and a tested one.

**The standing limit is unchanged and must stay stated:** this varies the *tool* (an index is added to the harness), never the *codebase*. It is **not** evidence that indirection hurts agents. `12-indirection-and-agents.md`'s sixteen prohibitions stand.

---

## 4 · Evans' chapters — the gap list was stale, not the research

`RESOURCES.md` listed *"Evans' chapter numbers"* among the deliberately-omitted. **They were not open.** `08-ddd-evans.md` already carried them at **Chapter located**, with printed pages:

| Concept | Chapter | Printed p. |
|---|---|---|
| `ubiquitous language` | **2** — *Communication and the Use of Language*, Part I | 24 |
| `bounded context`, `context map` | **14** — *Maintaining Model Integrity*, Part IV | 235 |

**Independently corroborated from the PDF on disk**, which is a second artifact rather than a re-reading of the same one. Extracting the book and walking its headings puts `## UBIQUITOUS LANGUAGE` inside `## **2. Communication and the Use of Language**` and `## BOUNDED CONTEXT` inside `## **14. Maintaining Model Integrity**`, across a 17-chapter structure that matches the published book.

**The methodological point** — and it is the same one that produced §0 of `15-*`: this was reported as unlocatable because `get_toc()` returned 0. `get_toc()` reads **embedded bookmarks**. The book prints its own table of contents *and* cross-references its own chapters in the body prose. **A stale gap list is its own defect class**: it costs a future session real time chasing something already answered, and it makes the corpus look less complete than it is.

---

## 5 · Net effect

| Gap | Before | After |
|---|---|---|
| Tracer bullet book text | `[unverified]`, aggregators only | **Quoted** — TPP 20th, Topic 12 |
| Agans' 9 rules | deliberately omitted | **Quoted** — ch. 2, plus the ch. 3–11 map |
| SHERLOC 67% | abstract only | **Quoted** from the body, **and decomposed** 40% + 27% |
| Code Isn't Memory ablations | abstract only | **Quoted** from the body, **with `p = 0.003`** |
| Evans' chapters | listed as omitted | **was never open** — list corrected |

**Unchanged, deliberately:** the `vertical slice` coiner (still none located), the *Crystal Clear* printed page number (structural — §2 of `15-*`), and the standing negative result that **no source measures indirection against agent success**.

**Still not amended:** `lessons/0006-dependency-inversion.html`, whose AI parallel §3 would strengthen. The checkpoint is out and its Q5/Q6 interrogate that lesson; fold in after the answers return.
