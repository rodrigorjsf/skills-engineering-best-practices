# 15 — The shelf, triaged: what each book is, and what it unlocks

**Date:** 2026-08-01
**Trigger:** the reader acquired five books from the ranked list in the previous session, and challenged two of the methods used to assess the earlier batch. **Both challenges were correct** and are recorded in §0 because they change how this workspace reads a PDF from now on.

---

## 0 · Two method corrections

### `get_toc()` is the wrong instrument for "does this book have a table of contents"

`fitz.Document.get_toc()` reads the PDF's **embedded outline (bookmarks)** — metadata a converter may simply never have written. It says nothing about whether the book **prints** a table of contents on its pages.

`DDD - Eric Evans.pdf` returns `get_toc() → 0`, and on that basis the Evans chapter numbers were reported as still unlocatable. **They are not.** Running the `pdf` skill's extractor over the front matter surfaces `## **Table of Contents**` as a markdown table, plus every chapter heading in the body:

> `## **1. Crunching Knowledge**` · `## **2. Communication and the Use of Language**` → containing `## UBIQUITOUS LANGUAGE`

and inline cross-references throughout — *"see Chapter 12, 'Maintaining Model Integrity'"*, *"Chapter 15, 'Distillation'"*, *"the principle of the LAYERED ARCHITECTURE (Chapter 4)"*.

**Rule:** `get_toc() == 0` means *no bookmarks*. To answer "is there a TOC", extract the front matter and look. To answer "what chapter is X in", extract and search — the book cross-references itself.

### The `pdf` skill runs OCR per page, automatically

Its own documentation reads *"OCR is optional — requires tesseract"*, which was taken as "it will not OCR". It does: on a mixed PDF it reports `Using Tesseract for OCR processing. OCR on page.number=2/3 …` and OCRs exactly the pages that lack a text layer. Combined with §3 of `14-*`, the standing rule is now:

**A PDF without a text layer is not unreadable. Extract, then verify by reading the rendered page image before quoting.**

---

## 1 · Provenance triage — the five new arrivals

Metadata and front matter were read before any content judgement, per the standing hazard in `RESOURCES.md`.

| Book | Pages | Verdict |
|---|---|---|
| **The Pragmatic Programmer**, 20th Anniv. | 497 | **Clean.** `ISBN-13 978-0-13-595705-9`, `Copyright © 2020 Pearson Education, Inc.` calibre conversion, author metadata correct |
| **Growing Object-Oriented Software** | 385 | **Clean.** `ISBN 978-0-321-50362-6`, `Copyright © 2010 Pearson`, Addison-Wesley Signature Series. PDFKit.NET |
| **Debugging** (Agans) | 149 | **Clean.** `ISBN-13 978-0-8144-7457-0 (pbk)`, AMACOM. Title/author metadata correct; 130 outline entries |
| **Crystal Clear** (Cockburn) | 312 | **Authentic, but a manuscript — see §2** |
| **Refactoring** (Fowler) | 337 | **UNUSABLE — see §3** |

Two dates worth carrying, both cases of a conversion timestamp preceding publication — the same trap as the CIP block in `14-*`:

- **GOOS**: PDF `creationDate` **2009-09-24**, copyright line **© 2010**. Cite **2010**.
- **Pragmatic 20th**: PDF `creationDate` **2019-07-30**, copyright line **© 2020**. The book is commonly dated 2019. `RESOURCES.md` already records `ISBN 9780135957059` — which matches this artifact exactly.

---

## 2 · Crystal Clear — authentic, and it still cannot supply the page number

The running header is `Crystal Clear Preface / P. 1`, and the title page reads:

> Crystal Clear — A Human-Powered Methodology For Small Teams, including The Seven Properties of Effective Software Projects
> Alistair Cockburn · Humans and Technology
> **copyright 1998-2004, A. Cockburn · last save date: June 17pm, 2004**

This is **Cockburn's own manuscript**, not the Addison-Wesley edition (published later in 2004). For authenticity that is *better* than the printed book — it is the author's file. But:

**Its pagination is section-local and restarts** (`Preface P.1`, `P.2`, …), so it **cannot** supply the printed-book page number for the walking-skeleton definition. That specific gap in `RESOURCES.md` — *"Add: the Crystal Clear page number for the walking-skeleton definition"* — **stays open**, and it stays open for a structural reason rather than for want of looking.

**What it does unlock:** the definition text itself, quotable from the author's own manuscript with a section-relative locator and the caveat stated. That is enough for lesson **0008**, which needs Cockburn's words, not his publisher's pagination.

---

## 3 · Refactoring — do not cite this file

Page 1, verbatim:

> Refactoring: Improving the Design of Existing Code
> by Martin Fowler, Kent Beck (Contributor), John Brant (Contributor), William Opdyke, don Roberts
>
> **Another stupid release 2002☺**
> **For all the people which doesn't have money to buy a good book**

Metadata matches the release note: `title: Refactoring.doc`, `author: Administrator`, `producer: Acrobat PDFWriter 5.0 for Windows NT`. That is **precisely the signature `RESOURCES.md` warns about** — a word-processor artifact where a publisher's typesetter should be.

**It is an unauthorised retype of the 1st edition, not a draft** — the Java text, the JUnit chapter and the full catalogue are present across 337 pages. So the failure mode differs from the laputan.org PDF, and it is worth naming precisely:

| Artifact | Failure |
|---|---|
| `laputan.org/…/smells.pdf` | **Wrong content** — a pre-publication draft: 17 smells, no *Speculative Generality*, "Extract Component" |
| This file | **Right content, unverifiable fidelity** — a retype with no publisher chain, and **its own pagination**, which is not the book's |

**The irony is the point:** this book was acquired to retire the laputan hazard, and citing it would replace one unverifiable artifact with another. It also carries a legal problem the course should not build its citation base on.

**Status: finding aid only** — the same verdict already settled for `ai-study-library`. Chase leads from it to a citable source; never cite it.

**The lead it supplies**, to be chased rather than quoted:

> The Rule of Three — "Here's a guideline Don Roberts gave me: The first time you do something, you just do it. The second time you do something similar, you wince at the duplication, but you do the duplicate thing anyway. The third time you do something similar, you refactor." Tip: "Three strikes and you refactor."

The attribution to **Don Roberts** is therefore *probably* right, and `Don Roberts` occurs 11× in the file. **It stays `[unverified]`** until it is read in a legitimate edition. Note also that this is the **1st edition (Java)**; the current book is the **2nd edition (2018, JavaScript)**, and the smell catalogue differs between them — so edition must be stated whenever this is eventually cited.

---

## 4 · What is now unlocked, by lesson

| Lesson | Book | State |
|---|---|---|
| **0007** YAGNI / DRY / wrong abstraction | Pragmatic 20th | DRY was already **Quoted** from the free publisher excerpt. The book adds context, not a missing quote |
| **0008** vertical slice · tracer bullet · walking skeleton | Pragmatic 20th | **Closes a real gap** — *"Tracer code is not disposable"* had reached us **only through secondary aggregators** |
| **0008** | Crystal Clear | Definition quotable; **printed page number still unavailable** (§2) |
| **0009** red-green-refactor | — | **Still open.** Beck's *TDD by Example* was #3 on the list and was not acquired |
| **0010 / 0011** pre-agreed seams · test anti-patterns | GOOS | Freeman & Pryce, authors of *Mock Roles, not Objects*, already used as a counter-voice in `11-*` |
| **0012** diagnosis as a loop | Agans, *Debugging* | **Closes a real gap** — the full list of 9 rules was deliberately omitted rather than guessed |
| **0013 / 0014** ubiquitous language · bounded context | DDD (already held) | **Closable now** — chapter numbers recoverable, §0 |
| **0007** Rule of Three | Refactoring | **Not closed.** §3 |

**Still worth acquiring:** **Beck, *Test-Driven Development by Example* (2002)** for lesson 0009, and a **legitimate *Refactoring*** — 2nd edition (2018) preferred, since it is the current text.
