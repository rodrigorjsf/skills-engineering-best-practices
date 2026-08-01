# 08 — Eric Evans, *Domain-Driven Design*: `ubiquitous language`, `bounded context`, `context map`

**Question.** The `mattpocock/skills` repo **uses** `ubiquitous language` and
`bounded context` but never defines them. Lessons 0011 and 0012 (Track III) need
Evans' own definitions, his real chapter locators (the workspace has so far
omitted them under the no-guessed-locators rule), and an honest read on whether
the repo's written `CONTEXT.md` departs from Evans' original claim.

**Source read.** `docs/books/DDD - Eric Evans.pdf`, 359 pages, read with PyMuPDF.

---

## 0. Which artifact this PDF actually is — read before citing a page

**Metadata verified** — from the PDF's own front matter and file metadata.

This PDF is **not** the published Addison-Wesley book. It is Evans' **final
pre-publication manuscript**. Title page (manuscript p. 1 = PDF p. 1), verbatim:

> Domain-Driven Design
> Tackling Complexity in the Heart of Software
>
> Eric Evans
>
> (Final Manuscript, April 15, 2003)
>
> For current draft and other information, see
> www.domainlanguage.com
>
> Copyright © 2003, Eric Evans. All rights reserved.

(The title page also carries Evans' 2003 personal email and phone number. Not
transcribed here — no evidentiary value.)

There is **no ISBN, no publisher, and no printing line** anywhere in the front
matter. PDF metadata corroborates the draft status: `title` =
`Microsoft Word _ Book_AfterFinal_doc`, `producer` = `PStill 1.60 by Dipl.-Ing.
F. Siegert`, `creationDate` = `Fri Apr 18 03:33:29 2003`. A running header
repeats `(Final Manuscript, April 15, 2003) © Eric Evans, 2003` on every page.

**Pagination.** Printed page numbers and PDF page numbers are **1:1** — verified
by header inspection (PDF p. 24 prints `24`; PDF p. 238 prints `238`). But they
are *manuscript* pages. The published book is 560 pages and paginates
differently. **Every locator below is written as "manuscript p. N (= PDF p. N)"
and must keep that qualifier in any lesson**, or the course will publish page
numbers that match no copy the reader can buy.

**Three independent proofs the draft's own locators were still settling** — do
not treat this manuscript's cross-references as authoritative:

| Manuscript page | Text | Conflict |
|---|---|---|
| p. 25, p. 30 | "see Chapter 12, 'Maintaining Model Integrity'" | TOC and p. 27 say Chapter **14** |
| p. 10 | "Part IV: Strategic Design" | p. 82 says "Part **III**: Strategic Design" |
| p. 34 | "declarative design (discussed in Chapter ##)" | literal unfilled placeholder |

### The published edition, for citation

**Metadata verified** — publisher page,
<https://www.informit.com/store/domain-driven-design-tackling-complexity-in-the-heart-9780321125217>:

- ISBN-13 **978-0-321-12521-7**, ISBN-10 **0-321-12521-5**
- Addison-Wesley Professional, **20 August 2003**, 1st edition, 560 pages

**Chapter located** — the same page carries a verbatim table of contents for the
**published** edition. Chapter lines as printed there:

> I. PUTTING THE DOMAIN MODEL TO WORK. / 1. Crunching Knowledge. /
> 2. Communication and the Use of Language. / 3. Binding Model and
> Implementation. / II. THE BUILDING BLOCKS OF A MODEL-DRIVEN DESIGN. /
> 4. Isolating the Domain. / 5. A Model Expressed in Software. / 6. The Life
> Cycle of a Domain Object. / 7. Using the Language: An Extended Example. /
> III. REFACTORING TOWARD DEEPER INSIGHT. / 8. Breakthrough. / 9. Making
> Implicit Concepts Explicit. / 10. Supple Design. / 11. Applying Analysis
> Patterns. / 12. Relating Design Patterns to the Model. / 13. Refactoring
> Toward Deeper Insight. / IV. STRATEGIC DESIGN. / 14. Maintaining Model
> Integrity. / 15. Distillation. / 16. Large-Scale Structure. / 17. Bringing
> the Strategy Together.

So **Chapter 2 and Chapter 14 carry identical numbers and titles in the
manuscript and in the published edition** — the two locators this workspace
needs are safe. Two titles *did* change between manuscript and print, which is
why the check was worth running: manuscript Ch. 7 "Using the Language in an
Example: A Cargo Shipping System" → published "Using the Language: An Extended
Example"; manuscript Ch. 13 "Bringing the Pieces Together" → published
"Refactoring Toward Deeper Insight". Cite manuscript titles only for the
chapters whose titles you have checked.

Recommended citation form for lessons:

> Eric Evans, *Domain-Driven Design: Tackling Complexity in the Heart of
> Software* (Addison-Wesley, 2003), ISBN 978-0-321-12521-7. Quotations here are
> from the Final Manuscript of 15 April 2003; page numbers are manuscript pages
> and do not match the published edition.

---

## 1. Chapter and part locators — settled

**Chapter located** — from the manuscript's own Table of Contents (manuscript
pp. 3–5), read directly. Chapter numbers and titles for Ch. 2 and Ch. 14 are
independently confirmed against the published edition's verbatim TOC (§0).

| Item | Chapter | Manuscript page |
|---|---|---|
| **Communication and the Use of Language** | **Chapter 2** | 24 |
| — `UBIQUITOUS LANGUAGE` (pattern) | §, Ch. 2 | 25 |
| — Documents and Diagrams | §, Ch. 2 | 31 |
| **Maintaining Model Integrity** | **Chapter 14** | 235 |
| — `BOUNDED CONTEXT` (pattern) | §, Ch. 14 | 238 |
| — `CONTINUOUS INTEGRATION` | §, Ch. 14 | 242 |
| — `CONTEXT MAP` (pattern) | §, Ch. 14 | 244 |
| Glossary | — | 353–355 |

**Part structure** (Part titles from the Preface, manuscript pp. 9–10; chapter
ranges from the TOC's blank-line groupings; both confirmed against the published
TOC in §0, which prints the same four parts with the same chapter ranges):

- **Part I — Putting the Domain Model to Work**: Chapters 1–3
- **Part II — The Building Blocks of Model-Driven Design**: Chapters 4–7
- **Part III — Refactoring Toward Deeper Insight**: Chapters 8–13
- **Part IV — Strategic Design**: Chapters 14–17

So: **`ubiquitous language` is a Part I / Chapter 2 idea; `bounded context` and
`context map` are Part IV / Chapter 14 ideas.** That gap is itself teachable —
the shared language comes first, in the small; the boundary comes 12 chapters
later, when one language can no longer cover the whole system.

---

## 2. `UBIQUITOUS LANGUAGE`

### 2.1 The glossary definition

**Quoted** — manuscript p. 355 (= PDF p. 355), Glossary:

> UBIQUITOUS LANGUAGE, a language structured around the domain model, and used
> by all team members to connect all the activities of the team with the
> software.

### 2.2 The pattern statement ("Therefore" block)

**Quoted** — manuscript p. 26 (= PDF p. 26), Ch. 2:

> Therefore,
>
> Use the model as the backbone of a language. Commit the team to using that
> language relentlessly in all communication within the team and in the code.
> Use the same language in diagrams, writing, and, especially speech.
>
> Iron out difficulties by experimenting with alternative expressions, which
> reflect alternative models. Then refactor the code, renaming classes, methods
> and modules to conform to the new model. Resolve confusion over terms in
> conversation, in just the way we converge on agreed meaning of ordinary
> words. Domain experts object to terms or structures that are awkward or
> inadequate to convey domain understanding, while developers watch for
> ambiguity or inconsistency that will trip up design.

### 2.3 Why the language must be shared between developers and domain experts

**Quoted** — manuscript p. 25 (= PDF p. 25). This is the strongest passage on
the failure mode, and it is the one a lesson should use:

> Across this linguistic divide, the domain experts vaguely describe what they
> want. Developers, struggling to understand a domain new to them, vaguely
> understand. A few members of the team manage to become bilingual, and speak to
> the domain experts in their language and the developers in theirs, but they
> become bottlenecks of information flow, and their translations are inexact.

And, same page, the pattern's problem statement:

> On a project without a common language, developers have to translate for
> domain experts. Domain experts translate between developers and still other
> domain experts. Translation is always inaccurate and hides disconnects in
> understanding between the domain experts and developers, between different
> developers and between different domain experts. Translation muddles model
> concepts, which leads to destructive refactoring of code. The indirectness of
> communication conceals the formation of schisms, where different team members
> use terms differently but don't realize it, which leads to unreliable software
> that doesn't fit together […] The effort of translation prevents the interplay
> of knowledge and ideas that lead to deep model insights.

Also p. 25, the boxed problem statement:

> It is a serious problem when the language used on a project is fractured.
> Domain experts use their jargon while technical team members have their own
> language tuned for discussing the domain in terms of design. Translation
> blunts communication and makes knowledge crunching anemic. Yet none of these
> dialects can be a common language because none serves all needs.

On the "shield the business experts" objection — **Quoted**, manuscript p. 30,
section *One Team, One Language*:

> Technical people often feel the need to "shield" the business experts from the
> domain model. "Too abstract for them", "they don't understand objects", and
> "we have to collect requirements in their terminology". These are just a few
> of the reasons I've heard for having two languages on the team. Forget them.
> […] If sophisticated domain experts don't understand the model, there is
> something wrong with the model.

> Multiplicity of language is often necessary, but the division should never be
> between the domain experts and the developers.

Evans also gives a concrete demonstration rather than only an argument: two
parallel dialogs, *Scenario 1: Minimal abstraction of the domain* and
*Scenario 2: Domain model enriched to support discussion* (manuscript pp. 27–29),
identical in structure, differing only in whether `Route Specification` and
`Itinerary` exist as shared nouns. **Directly reusable as a lesson figure.**

---

## 3. `BOUNDED CONTEXT`

### 3.1 The glossary definition

**Quoted** — manuscript p. 353 (= PDF p. 353), Glossary:

> BOUNDED CONTEXT, delimited applicability of a particular model so that team
> members have a clear and shared understanding of what has to be consistent and
> how it relates to other models in other contexts

### 3.2 The pattern statement

**Quoted** — manuscript p. 239 (= PDF p. 239), Ch. 14:

> Therefore,
>
> Explicitly define the context within which a model applies. Explicitly set
> boundaries in terms of team organization, usage within specific parts of the
> application, and physical manifestations such as code bases and database
> schemas. Keep the model strictly consistent within these bounds, but don't be
> distracted or confused by issues outside.

Immediately following, same page (note this wording differs slightly from the
glossary entry — keep them separate, do not merge):

> A BOUNDED CONTEXT delimits the applicability of a particular model so that team
> members have a clear and shared understanding of what has to be consistent and
> how it relates to other contexts. Within that context, work to keep the model
> logically unified, but do not worry about applicability outside those bounds.
> In other contexts, other models apply, with differences in terminology,
> concepts and rules, and different dialects of the UBIQUITOUS LANGUAGE. By
> drawing an explicit boundary, you can keep the model pure, and therefore
> potent, where it is applicable.

The definition of "context" that precedes it, manuscript p. 239:

> The model context is whatever set of conditions must apply in order to be able
> to say that the terms in a model have a specific meaning.

Epigraph, manuscript p. 238 — usable verbatim as the lesson 0012 *Intuição* beat:

> Cells can exist because their membranes define what is in and out and
> determine what can pass.

### 3.3 How he distinguishes it from a `MODULE`

**Quoted** — manuscript p. 239 (= PDF p. 239), in a block marked
`<<BEGIN SIDEBAR>>` / `<<END SIDEBAR>>` in the manuscript. Evans wrote this
sidebar for exactly this question:

> This issue sometimes gets confused with the motivations for MODULES. True,
> when it is recognized that two sets of objects make up different models they
> are typically placed in separate MODULES, and this does provide different
> name-spaces that allow them to compile even if they have name overlaps. But
> this is just an implementation mechanism for code separation of different
> models. This issue is preceded by the fundamental problems, recognizing model
> differences and deciding what to do with them. Furthermore, MODULES are also
> used to organize the elements within one model, so they don't communicate an
> intention to separate models. The separate name-spaces they create can
> actually make it harder to spot accidental model divergences. While technical
> tools can help us communicate and implement conceptual issues, they don't
> solve our conceptual problems.

Three distinctions, all supported by that quote:

1. A `MODULE` is an **implementation mechanism**; a `BOUNDED CONTEXT` is a
   **conceptual** decision that precedes it.
2. `MODULE`s also organise elements **within one model**, so a module boundary
   carries no information about whether models differ.
3. Namespaces can make divergence **harder** to spot, not easier.

`MODULES (AKA PACKAGES)` is itself a Chapter 5 pattern, manuscript p. 79
(**Chapter located**).

### 3.4 Distinguishing it from a "subsystem" — **negative result**

**No such passage exists.** A regex sweep of manuscript pp. 231–282 (the whole of
Chapter 14) returns ~30 uses of "subsystem", all informal and none definitional.
Evans uses the word for things *inside* one context (`CUSTOMER/SUPPLIER
DEVELOPMENT TEAMS`, p. 252), for external legacy systems (`ANTICORRUPTION LAYER`,
pp. 257–260), and for the thing an `OPEN HOST SERVICE` exposes (p. 263). The
nearest thing to a contrast is a failure-mode remark in the Part IV introduction
(manuscript p. 233):

> A set of small distinct subsystems glued together with ad-hoc interfaces will
> lack the power to solve enterprise wide problems, and allows consistency
> problems to arise at every integration point.

That is a warning, not a definition. **Do not build a
`bounded context` vs `subsystem` distinction out of this text.** The `MODULE`
contrast is sharp and citable; the `subsystem` contrast is not in the book.

---

## 4. `CONTEXT MAP`

### 4.1 The glossary definition

**Quoted** — manuscript p. 353 (= PDF p. 353), Glossary:

> CONTEXT MAP, a representation of the BOUNDED CONTEXTS involved in a project and
> the actual relationships between them.

### 4.2 The pattern statement

**Quoted** — manuscript p. 244 (= PDF p. 244), Ch. 14:

> Identify each model in play on the project and define its BOUNDED CONTEXT. This
> includes the implicit models of non-object-oriented subsystems. Name each
> BOUNDED CONTEXT, and make the names part of the UBIQUITOUS LANGUAGE.
>
> Describe the points of contact between the models, outlining explicit
> translation for any communication and highlighting any sharing.
>
> Map the existing terrain. Take up transformations later.

### 4.3 The form of the map is deliberately unspecified

**Quoted** — manuscript p. 244. Directly relevant to whether the repo's
`CONTEXT-MAP.md` (a Markdown file) can legitimately claim this ancestry:

> The map does not have to be documented in any particular form. I find
> conceptual diagrams like the ones in this chapter to be helpful in visualizing
> and communicating the map. Others may prefer a more textual description or
> different graphical representation. In some situations, discussion among
> teammates may be sufficient. The level of detail can vary according to need.
> Whatever form it takes, it must be shared and understood by everyone on the
> project. It must provide a clear name for each BOUNDED CONTEXT, and it must
> make the points of contact and their natures clear.

**What the repo's artifact actually is** — **Quoted** from the live upstream
clone at `/home/rodrigo/Workspace/skills`.
`skills/engineering/domain-modeling/CONTEXT-FORMAT.md:36`:

> **Multiple contexts:** A `CONTEXT-MAP.md` at the repo root lists the contexts,
> where they live, and how they relate to each other:

The template that follows (`CONTEXT-FORMAT.md:38–52`) has exactly two headings,
`## Contexts` (a bulleted list of named contexts, each linking to its own
`CONTEXT.md`) and `## Relationships` (bullets stating the direction and mechanism
of each link — events consumed, shared types).

**Verdict:** a Markdown `CONTEXT-MAP.md` is within the form Evans permits — he
explicitly allows "a more textual description". Measured against his three
stated requirements: (b) *a clear name for each* `BOUNDED CONTEXT` — satisfied by
construction, `## Contexts` is a named list; (c) *points of contact and their
natures made clear* — satisfied by construction, `## Relationships` names both
the direction and the mechanism; (a) *shared and understood by everyone on the
project* — **not determinable from either text**; it is a property of a team, not
of a file format. That last one is the criterion a lesson should hand the reader
as a question, not as a verdict.

One further constraint, manuscript p. 244 — the map describes **what is**, not
what should be:

> Keeping in mind that the CONTEXT MAP always represents the situation as it
> stands, the relationships you find may not fit these patterns initially.

And p. 245:

> Remember, don't change the map until the change in reality is done.

---

## 5. Is the language **spoken**, not just written?

**Yes — but the strong reading ("Evans forbids written glossaries") is not
supported.** The text supports a more precise and more teachable claim.

### 5.1 What Evans says about speech

**Quoted** — manuscript p. 25 (= PDF p. 25), the boxed problem statement:

> Even the same person uses different language in speech and in writing, and so
> the most incisive expressions of the domain often emerge in a transient form
> that is never captured in the code or even in writing.

**Quoted** — manuscript p. 29 (= PDF p. 29), section titled **Modeling Out
Loud**:

> The disconnect between speech and other forms of communication is particularly
> harmful because we humans have a genius for spoken language. Unfortunately,
> when people speak, they usually don't use the language of the domain model.

> One of the best ways of refining a model is to explore with speech, trying out
> loud various constructs from possible model variations. Rough edges are easy
> to hear.

He then gives three spoken renderings of the same design, ending at the one that
survives being said aloud (manuscript p. 29):

> 3. A Routing Service finds an Itinerary that satisfies a Route Specification.

**Quoted** — manuscript p. 29, the claim that speech does something documents
cannot:

> As we use the UBIQUITOUS LANGUAGE of the domain model in discussions,
> especially those with domain experts, with whom we discuss scenarios and
> requirements, we become more fluent in the language and teach each other its
> nuances. We naturally come to share the language that we speak in a way that
> never happens with diagrams and documents.

**Quoted** — manuscript p. 30, the instruction:

> Play with the model as you talk about the system. Describe scenarios out loud
> using the elements and interactions of the model, combining concepts in ways
> allowed by the model. Find easier ways to say what you need to say and then
> take those new ideas back down to the diagrams and code.

He grounds it in an empirical claim about human cognition (manuscript p. 29),
citing Pinker: "our brains seem to be somewhat specialized for dealing with
complexity in spoken language (one good treatment for laymen, like myself, is
[Pinker 94])" — and offers a pidgin analogy and a personal anecdote about an
immersion Spanish class. Note: this is Evans' claim *by analogy from
psycholinguistics*, not a measured result about software teams.

### 5.2 What Evans says about written documents — he does **not** forbid them

**Quoted** — manuscript p. 33 (= PDF p. 33):

> Verbal communication supplements the code with meaning. But while talking is
> critical to connecting everyone to the model, a group of any size will
> probably need the stability and sharability of some written documents.

**Quoted** — manuscript p. 30:

> The domain experts can use the language of the model in writing use cases, and
> can work even more directly with the model by specifying acceptance tests.

Instead of a prohibition, he supplies a **two-criteria liveness test for a
document** (manuscript p. 33):

> First, a document shouldn't try to do what the code already does well. The code
> already supplies the detail.

> The second criterion is that a document must be involved in project activities.
> The easiest way to judge this is to observe its interaction with the
> UBIQUITOUS LANGUAGE. Is the document written in the language people speak on
> the project (now)? Is it written in the language embedded in the code?

> Listen to the UBIQUITOUS LANGUAGE and how it is changing. If the terms explained
> in a design document don't start showing up in conversations and code, it is
> not fulfilling its purpose. […] If it is having no impact on the UBIQUITOUS
> LANGUAGE, something is wrong.

He also names the decay mode a static glossary is exposed to (manuscript p. 32):

> Once a document takes on a persistent form, it often loses its connection with
> the flow of the project. It is left behind by the evolution of the code, or it
> is left behind by the evolution of the language of the project.

### 5.3 The precise divergence for lesson 0011

**What the source says.** Evans subordinates the written artifact to the spoken
language: speech is the *discovery and refinement* medium ("rough edges are easy
to hear"), and a document earns its place only by passing the two-criteria test.
He never prohibits a written glossary.

**What the repo actually says** — **Quoted** from the live upstream clone,
`skills/engineering/domain-modeling/SKILL.md:64`:

> `CONTEXT.md` should be totally devoid of implementation details. Do not treat
> `CONTEXT.md` as a spec, a scratch pad, or a repository for implementation
> decisions. It is a glossary and nothing else.

**Where that sits against Evans' two criteria — the corrected reading.**

- **Criterion one** ("a document shouldn't try to do what the code already does
  well. The code already supplies the detail.") — the repo's rule **satisfies
  it, by construction and almost word for word.** Stripping implementation
  details out of the glossary is precisely what Evans asks for. An earlier draft
  of this file claimed the opposite; that was wrong.
- **Criterion two** ("Is the document written in the language people speak on
  the project (now)? Is it written in the language embedded in the code?") —
  **undetermined from either source.** This criterion asks whether the glossary's
  *terms* are the code's terms. That is orthogonal to whether the glossary
  contains implementation *detail*: a file can be 100% free of implementation
  detail and still be written entirely in the language embedded in the code.
  Neither Evans' text nor the repo's rule can settle it; only an actual
  repository can.

So there is **no textual divergence to teach here.** What Evans gives lesson 0011
is a **test**, not a verdict — and the repo's operational form passes the half of
it that a file format can pass, while the other half remains a property of the
team using it. A lesson that claims Evans is at odds with `CONTEXT.md` would be
overstating the evidence.

**What is ours, not Evans'** — `<span class="flag ours">`: the observation that
an agent cannot participate in the spoken channel at all, so a repo optimising
for agents *must* move the language's carrier from speech to a written file, and
therefore inherits precisely the decay risk Evans identified on manuscript p. 32
("Once a document takes on a persistent form, it often loses its connection with
the flow of the project"). Evans wrote nothing about LLM agents. Do not imply
otherwise. The genuinely teachable point for lesson 0011 is not "the repo
diverges from Evans" but "the repo replaces Evans' self-correcting medium
(speech, where rough edges are heard immediately) with a medium that has no
self-correction, so Evans' liveness test becomes *more* load-bearing, not less."

---

## 6. Transcription note

The manuscript contains Evans' own pre-copyedit typos. All quotes above are
**verbatim, uncorrected**. Where a reader might otherwise blame this corpus:

- p. 353 Glossary, AGGREGATE: "restricted to on member" [sic]
- p. 33: "documents should compliment the code" [sic — twice on the page]
- p. 239: "it's primary consumer" [sic]
- p. 244: "a clear view into of the ongoing" [sic], "will spit off into different
  contexts" [sic]
- p. 10: "this onion pealing leads to" [sic]

None of these appear in the passages quoted in sections 2–5. They are listed so
that anyone quoting text *adjacent* to those passages preserves them rather than
silently correcting. The published edition presumably fixes them, which is a
further reason to carry the manuscript qualifier on every locator.

---

## 7. What this settles for the workspace

| Previously omitted | Now |
|---|---|
| Evans' chapter for `ubiquitous language` | **Chapter 2**, Part I — *Chapter located* |
| Evans' chapter for `bounded context` / `context map` | **Chapter 14**, Part IV — *Chapter located* |
| Evans' definitions | Verbatim, §2.1, §3.1, §4.1 — *Quoted* |
| Publication metadata | ISBN 978-0-321-12521-7, Addison-Wesley, 20 Aug 2003 — *Metadata verified* |
| Do published chapter numbers match the manuscript? | **Yes for Ch. 2 and Ch. 14** — verbatim published TOC, §0. Two *other* chapters were retitled in print. |
| `bounded context` vs `module` | Sharp, quotable — manuscript p. 239 sidebar |
| `bounded context` vs `subsystem` | **Negative result** — no such distinction in the text |
| Speech vs written glossary | Evans subordinates but does not forbid; **no divergence established** — §5.3 |
