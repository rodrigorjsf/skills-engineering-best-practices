# 11 — YAGNI, DRY, and the cost of a premature abstraction

**Question this file answers:** do YAGNI and DRY *contradict* a seam-oriented,
dependency-inverting design discipline? Short answer, from the sources rather
than from us: **no — and Fowler, the Pragmatic Programmers, and the Mock Objects
authors each pre-empt the contradiction explicitly in their own text.** The
tension people feel is real, but it is a tension with the *folk* readings of
YAGNI and DRY, not with what the primary sources say.

Every claim below is tagged with one of four tiers: **Quoted** · **Metadata
verified** · **Chapter located** · **`[unverified]`**. Artifacts were downloaded
and read; nothing here is relayed from a blog post that quotes a primary source.

Artifacts fetched and read for this file (2026-08-01):

| Artifact | URL | Form |
| --- | --- | --- |
| Fowler, "Yagni" (26 May 2015) | `https://martinfowler.com/bliki/Yagni.html` | full HTML |
| Metz, "The Wrong Abstraction" (20 Jan 2016) | `https://sandimetz.com/blog/2016/1/20/the-wrong-abstraction` | full HTML |
| Thomas & Hunt, *TPP 20th Anniv.*, Topic 9 "The Evils of Duplication" | `https://media.pragprog.com/titles/tpp20/dry.pdf` | 14-page publisher excerpt PDF |
| Venners interview with Hunt & Thomas, "Orthogonality and the DRY Principle" (10 Mar 2003) | `https://www.artima.com/articles/orthogonality-and-the-dry-principle` | full HTML |
| Freeman, Pryce, Mackinnon, Walnes, "Mock Roles, not Objects" (OOPSLA 2004) | `https://jmock.org/oopsla2004.pdf` | 11-page PDF |
| c2 wiki, `YouArentGonnaNeedIt` | Wayback `http://web.archive.org/web/20160806195015/http://c2.com/cgi/wiki?YouArentGonnaNeedIt` | full HTML |
| Rainsberger, "Clarifying the Rule of Three in Refactoring" (7 Nov 2025) | `https://blog.jbrains.ca/permalink/clarifying-the-rule-of-three-in-refactoring/` | full HTML |
| Anthropic, "Building Effective Agents" | `https://www.anthropic.com/engineering/building-effective-agents` | full HTML |

---

## 1. YAGNI — origin and precise meaning

### 1.1 The origin. It is not Ron Jeffries.

**Quoted** — Fowler, footnote 1 of `https://martinfowler.com/bliki/Yagni.html`:

> The origin of the phrase is an early conversation between Kent Beck and Chet
> Hendrickson on the C3 project. Chet came up to Kent with a series of
> capabilities that the system would soon need, to each one Kent replied "you
> aren't going to need it". Chet's a fast learner, and quickly became renowned
> for his ability to spot opportunities to apply yagni.

So the *utterance* is Kent Beck's, prompted by Chet Hendrickson, on the C3
(Chrysler Comprehensive Compensation) project. **Ron Jeffries is not the
coiner** on this evidence. He is, however, the author of the best-known
*argument* for it on the c2 wiki (see 1.2). Fowler adds, same footnote:

> Although "yagni" began life as an acronym, I feel it's now entered our
> lexicon as a regular word, and thus forego the capital letters.

**Quoted** — Fowler on the practice it names:

> Yagni is a way to refer to the XP practice of Simple Design (from the first
> edition of The White Book, the second edition refers to the related notion of
> "incremental design").

`[unverified]` — Fowler says "This principle was first discussed and fleshed out
on Ward's Wiki." We did not establish whether the c2 page predates or postdates
the C3 conversation; c2 pages are anonymous and undated.

### 1.2 The c2 wiki statement

**Quoted** — Wayback snapshot 2016-08-06 of `c2.com/cgi/wiki?YouArentGonnaNeedIt`.
The page's canonical one-liner (no named author on the page — c2 is collectively
edited):

> YouArentGonnaNeedIt (often abbreviated YAGNI, or YagNi on this wiki) is an
> ExtremeProgramming practice which states:
>
> "Always implement things when you actually need them, never when you just
> foresee that you need them."

And, critically for our question, the very next paragraph:

> This doesn't mean you should avoid building flexibility into your code. It
> means you shouldn't overengineer something based on what you think you might
> need later on.

The page's two stated reasons:

> You save time, because you avoid writing code that you turn out not to need.
> Your code is better, because you avoid polluting it with 'guesses' that turn
> out to be more or less wrong but stick around anyway.

**Quoted** — the page attributes a dialogue explicitly to Ron Jeffries ("A
scenario from RonJeffries explains the practices"), including:

> But unless your universe is very different from mine, you can't 'save' time by
> doing the work now, unless it will take more time to do it later than it will
> to do now.

### 1.3 Fowler's cost model — all four names verified

**Quoted**, all from `https://martinfowler.com/bliki/Yagni.html`. The four costs
and their exact definitions:

1. **cost of build** — "there's an obvious cost of the presumptive feature - the
   cost of build: all the effort spent on analyzing, programming, and testing
   this now useless feature."
2. **cost of delay** — introduced as "the cost of delayed value", then used as
   *cost of delay*: "By expending our effort on the piracy pricing software we
   didn't build some other feature. […] This cost of delay due to the presumptive
   feature is two months revenue from storm insurance."
3. **cost of carry** — "The code for the presumptive feature adds some
   complexity to the software, this complexity makes it harder to modify and
   debug that software, thus increasing the cost of other features." And: "We'll
   incur a cost of carry on every feature built between now and the time the
   piracy insurance software starts being useful."
4. **cost of repair** — for the third class, *the right feature built wrong*:
   "you often realize that a feature coded six months ago wasn't done the way you
   now realize it should be done. In that case you have accumulated
   TechnicalDebt and have to consider the cost of repair for that feature or the
   on-going costs of working around its difficulties."

**Quoted** — his own summary of the structure: *"So we end up with three classes
of presumptive features, and four kinds of costs that occur when you neglect
yagni for them."*

**Quoted** — his definition of the key term (footnote 2): *"In this post I use
'presumptive feature' to refer to any code that supports a feature that isn't yet
being made available for use."*

**Hard numbers** — **Quoted**, footnote 3: *"The ⅔ number is suggested by Kohavi
et al, who analyzed the value of features built and deployed on products at
microsoft and found that, even with careful up-front analysis, only ⅓ of them
improved the metrics they were designed to improve."* Fowler uses this in the
body as "factoring in the probability that you're building an unnecessary
feature, for which your odds are at least ⅔."
`[unverified]` — we did not fetch the Kohavi et al. paper itself; treat the ⅓
figure as Fowler-reported, not as verified from the study.

He also quotes Jeremy Miller inline, **Quoted**:

> Reminder, any extensibility point that's never used isn't just wasted effort,
> it's likely to also get in your way as well

### 1.4 Does YAGNI apply to abstractions, or only to features?

**This is the pivot, and Fowler answers it directly — we do not need to extend
anything.** **Quoted**:

> My insurance example talks about relatively user-visible functionality, but the
> same argument applies for abstractions to support future flexibility. When
> building the storm risk calculator, you may consider putting in abstractions
> and parameterizations now to support piracy and other risks later. Yagni says
> not to do this, because you may not need the other pricing functions, or if you
> do your current ideas of what abstractions you'll need will not match what you
> learn when you do actually need them. **This doesn't mean to forego all
> abstractions, but it does mean any abstraction that makes it harder to
> understand the code for current requirements is presumed guilty.**

(Bold ours.) And, on the small scale — **Quoted**:

> As a developer it's easy to spend an hour adding an abstraction that we're sure
> will soon be needed. Yet all the arguments above still apply, and a lot of small
> yagni decisions add up to significant reductions in complexity to a code base

So: **YAGNI does apply to abstractions and interfaces**, on Fowler's own text.
The test he gives is not "does it have two implementations" — it is *"does it
make the code for current requirements harder to understand?"*

### 1.5 The scoping sentence — the one that dissolves the contradiction

**Quoted**, and this is the single most load-bearing line in the whole file:

> Now we understand why yagni is important we can dig into a common confusion
> about yagni. **Yagni only applies to capabilities built into the software to
> support a presumptive feature, it does not apply to effort to make the software
> easier to modify.** Yagni is only a viable strategy if the code is easy to
> change, so expending effort on refactoring isn't a violation of yagni because
> refactoring makes the code more malleable. Similar reasoning applies for
> practices like SelfTestingCode and ContinuousDelivery. These are enabling
> practices for evolutionary design, without them yagni turns from a beneficial
> practice into a curse.

(Bold ours.) Followed by his own pull-quote — **Quoted**:

> Yagni is not a justification for neglecting the health of your code base. Yagni
> requires (and enables) malleable code.

And the complexity clause — **Quoted**:

> I also argue that yagni only applies when you introduce extra complexity now
> that you won't take advantage of until later. If you do something for a future
> need that doesn't actually increase the complexity of the software, then
> there's no reason to invoke yagni.

Finally, his honest caveat — **Quoted**:

> Having said all this, there are times when applying yagni does cause a problem,
> and you are faced with an expensive change when an earlier change would have
> been much cheaper. The tricky thing here is that these cases are hard to spot
> in advance, and much easier to remember than the cases where yagni saved effort.
> My sense is that yagni-failures are relatively rare and their costs are easily
> outweighed by when yagni succeeds.

Note the epistemic humility: "My sense is" — Fowler offers no data for the
relative frequency. That is his opinion, and he flags it as such (footnote 4
attributes the asymmetry to *availability bias*).

---

## 2. DRY — origin and the misreading

### 2.1 Metadata

**Metadata verified** — *The Pragmatic Programmer: From Journeyman to Master*,
Andrew Hunt & David Thomas, Addison-Wesley, published **20 Oct 1999**, 352 pages,
**ISBN-13 978-0-201-61622-4** (ISBN-10 0-201-61622-X).
Source: `https://www.informit.com/store/pragmatic-programmer-from-journeyman-to-master-9780201616224`.

**Metadata verified** — *The Pragmatic Programmer: your journey to mastery, 20th
Anniversary Edition*, David Thomas & Andrew Hunt, **September 2019**, 320 pages,
**ISBN 9780135957059**. Source: `https://pragprog.com/titles/tpp20/the-pragmatic-programmer-20th-anniversary-edition/`.
The publisher excerpt PDF carries "Copyright © 2020 Pearson Education, Inc." and
"ISBN-13: 978-0-13-595705-9".

**Chapter located** — the excerpt PDF is headed **"9 — The Evils of Duplication"**
and the DRY statement is **Tip 15, "DRY—Don't Repeat Yourself"**. Chapter/topic
number and tip number are read directly off the PDF; no page number is given here
because the excerpt's page numbering is the excerpt's own.

### 2.2 The canonical statement — verbatim from the book excerpt

**Quoted** — from the **20th Anniversary Edition** (© 2020 Pearson), publisher
excerpt at `https://media.pragprog.com/titles/tpp20/dry.pdf`:

> We feel that the only way to develop software reliably, and to make our
> developments easier to understand and maintain, is to follow what we call the
> DRY principle:
>
> **Every piece of knowledge must have a single, unambiguous, authoritative
> representation within a system.**

The recalled wording is **exactly correct as of the 20th Anniversary Edition**,
and the word is **knowledge** — not *code*, not *text*.

**`[unverified]` — the 1999 first-edition wording was NOT fetched. Do not
attribute this sentence to "Hunt & Thomas, 1999" in a lesson.** The excerpt's own
admission ("in the first edition of this book we did a poor job of explaining just
what we meant") establishes that the *explanation* changed between editions; it
does not establish that the *tip sentence* is byte-identical across them. Cite it
as **Thomas & Hunt, 20th Anniversary Edition (2019/2020), Topic 9, Tip 15** until
someone reads the 1999 text.

### 2.3 The misreading — admitted by the authors, twice, sixteen years apart

**(a) 2003, in Dave Thomas's own words.** **Quoted** —
`https://www.artima.com/articles/orthogonality-and-the-dry-principle`, Bill
Venners interview, published 10 March 2003:

> **Bill Venners:** What's the DRY principle?
>
> **Dave Thomas:** Don't Repeat Yourself (or DRY) is probably one of the most
> misunderstood parts of the book.
>
> **Bill Venners:** How is DRY misunderstood and what is the correct way to
> understand it?
>
> **Dave Thomas:** Most people take DRY to mean you shouldn't duplicate code.
> That's not its intention. The idea behind DRY is far grander than that.
>
> DRY says that every piece of system knowledge should have one authoritative,
> unambiguous representation. Every piece of knowledge in the development of
> something should have a single representation. A system's knowledge is far
> broader than just its code. It refers to database schemas, test plans, the
> build system, even documentation.

**This predates the 20th-anniversary edition by sixteen years** and is a stronger
result than the 2019 clarification, because it is conversational and unhedged.

**(b) 2019/2020, in the book itself.** **Quoted** — same excerpt PDF, section
headed **"DRY is More Than Code"**:

> Let's get something out of the way up-front. In the first edition of this book
> we did a poor job of explaining just what we meant by Don't Repeat Yourself.
> Many people took it to refer to code only: they thought that DRY means "don't
> copy-and-paste lines of source."
>
> That is part of DRY, but it's a tiny and fairly trivial part.
>
> DRY is about the duplication of knowledge, of intent. It's about expressing the
> same thing in two different places, possibly in two totally different ways.
> Here's the acid test: when some single facet of the code has to change, do you
> find yourself making that change in multiple places, and in multiple different
> formats?

### 2.4 The decisive section: **"Not All Code Duplication is Knowledge Duplication"**

This is the section the brief predicted exists, and it does — it is a named
section heading in the 20th-anniversary edition. **Quoted**, same PDF:

> As part of your online wine ordering application you're capturing and validating
> your user's age, along with the quantity they're ordering. According to the site
> owner, they should both be numbers, and both greater than zero. So you code up
> the validations:
>
> ```
> def validate_age(value):
>     validate_type(value, :integer)
>     validate_min_integer(value, 0)
>
> def validate_quantity(value):
>     validate_type(value, :integer)
>     validate_min_integer(value, 0)
> ```
>
> During code review, the resident know-all bounces this code, claiming it's a
> DRY violation: both function bodies are the same.
>
> **They are wrong. The code is the same, but the knowledge they represent is
> different. The two functions validate two separate things that just happen to
> have the same rules. That's a coincidence, not a duplication.**

(Bold ours.) This is the authors *explicitly forbidding* the unification of two
identical code bodies representing different knowledge — the precise claim the
brief asked to verify, verified from the artifact.

**Domain bonus for this workspace:** the excerpt's own worked example of code
duplication is a **banking** one — `print_balance(account)` over
`account.debits`, `account.credits`, `account.fees`, `account.balance`. The
running domain of our course and the running example of the canonical DRY text
coincide. Usable directly in a lesson.

### 2.5 The Rule of Three

`[unverified]` — **we did not fetch Fowler's *Refactoring* text**, so the exact
wording and the Don Roberts attribution are **not verified from the primary
source here.** They are widely reported (Wikipedia, Rainsberger's reference list)
as: "Three strikes and you refactor," attributed by Fowler to Don Roberts in
*Refactoring* (1999). **Do not state the wording or the attribution in a lesson as
verified until someone reads the book.**

Standing constraint carried forward from this workspace: the free laputan.org
*Refactoring* PDF is a **pre-publication draft** (16 smells, not 22), not the 1st
edition. If it is ever used for the Rule of Three, it must be labelled
*pre-publication draft* and no page number may be given. The chapter of it we
hold locally is Chapter 3, "Bad Smells in Code"; the Rule of Three is not in it.

**Quoted** — J. B. Rainsberger, "Clarifying the Rule of Three in Refactoring",
7 November 2025, `https://blog.jbrains.ca/permalink/clarifying-the-rule-of-three-in-refactoring/`.
He states the tension in exactly the terms of our question:

> More than a quarter century after this heuristic received its name, it continues
> to confuse and divide programmers. Most notably, some programmers swear by it
> while others (and I count myself as one) take a more relaxed attitude towards
> it. This lies at the center of the ongoing debate between whether to favor
> removing duplication or to wait before extracting an abstraction, lest it become
> unhelpful.

And his own resolution — **Quoted**:

> I don't mind removing duplication more aggressively, primarily because I don't
> mind inlining when I regret my choice.

> Should you follow the Rule of Three? I don't know.

Rainsberger's move is worth noting: he dissolves the rule by making the *reverse*
operation cheap. If inlining an abstraction is routine, the cost of extracting
prematurely collapses — which is the same structural argument Fowler makes when
he says yagni "requires (and enables) malleable code."

---

## 3. The wrong abstraction — Sandi Metz

**Metadata verified** — "The Wrong Abstraction", Sandi Metz, posted **20 January
2016** on `sandimetz.com`. Her own note on provenance, **Quoted**:

> I originally wrote the following for my Chainline Newsletter, but I continue to
> get tweets about this idea, so I'm re-publishing the article here on my blog.
> This version has been lightly edited.

And, importantly for attribution — the thesis line is **older than the blog post**.
**Quoted**:

> My RailsConf 2014 "all the little things" talk included a section where I
> asserted:
>
> **duplication is far cheaper than the wrong abstraction**
>
> And in the summary, I went on to advise:
>
> **prefer duplication over the wrong abstraction**

So the recalled line is **verbatim correct**, and its first utterance is the
**RailsConf 2014** talk, not the 2016 post. Cite it as "Metz, *All the Little
Things*, RailsConf 2014; re-stated in 'The Wrong Abstraction', 2016."

### 3.1 Her lifecycle, verbatim

**Quoted**, numbering hers:

> 1. Programmer A sees duplication.
> 2. Programmer A extracts duplication and gives it a name. This creates a new
>    abstraction. It could be a new method, or perhaps even a new class.
> 3. Programmer A replaces the duplication with the new abstraction.
> 4. Ah, the code is perfect. Programmer A trots happily away.
> 5. Time passes.
> 6. A new requirement appears for which the current abstraction is almost perfect.
> 7. Programmer B gets tasked to implement this requirement.
> 8. Programmer B feels honor-bound to retain the existing abstraction, but since
>    isn't exactly the same for every case, they alter the code to take a
>    parameter, and then add logic to conditionally do the right thing based on
>    the value of that parameter.
> 9. What was once a universal abstraction now behaves differently for different
>    cases.
> 10. Another new requirement arrives. Programmer X. Another additional parameter.
>     Another new conditional. Loop until code becomes incomprehensible.

(The `since isn't exactly the same` fragment is the source's own typo — verified
against the raw HTML, there is no elided markup between "since" and "isn't".
Reproduced verbatim; do not silently fix it in a quote.)

### 3.2 The mechanism she blames

**Quoted**:

> Existing code exerts a powerful influence. Its very presence argues that it is
> both correct and necessary. We know that code represents effort expended, and
> we are very motivated to preserve the value of this effort. And, unfortunately,
> the sad truth is that the more complicated and incomprehensible the code, i.e.
> the deeper the investment in creating it, the more we feel pressure to retain
> it (the "sunk cost fallacy").

Note this is the *inverse* of Fowler's cost-of-carry: Fowler prices the ongoing
drag; Metz prices the psychological lock-in that prevents removal.

### 3.3 Her prescription — and her diagnostic test

**Quoted**:

> When dealing with the wrong abstraction, the fastest way forward is back. Do the
> following:
>
> 1. Re-introduce duplication by inlining the abstracted code back into every
>    caller.
> 2. Within each caller, use the parameters being passed to determine the subset
>    of the inlined code that this specific caller executes.
> 3. Delete the bits that aren't needed for this particular caller.

The **falsifiable test** — this is the most operationally useful line in the
article and is often omitted when it is quoted. **Quoted**:

> If you find yourself passing parameters and adding conditional paths through
> shared code, the abstraction is incorrect. It may have been right to begin with,
> but that day has passed.

And her observation about what inlining reveals — **Quoted**:

> When you rewind decisions in this way, it's common to find that although each
> caller ostensibly invoked a shared abstraction, the code they were running was
> fairly unique.

Closing line — **Quoted**:

> When the abstraction is wrong, the fastest way forward is back. This is not
> retreat, it's advance in a better direction.

### 3.4 Other voices in this vein

`[unverified]` — Kent Beck's "make the change easy, then make the easy change"
was **not fetched** for this file. Do not cite it as verified. Same for Dan North
and Kevlin Henney on this topic: **nothing fetched, nothing claimed.**

---

## 4. The contradiction question — centrepiece

### 4.1 Restating the two sides

**(a)** A seam-oriented, dependency-inverting discipline ("depend on
abstractions", "accept dependencies, don't create them", "the interface is the
test surface") structurally *pushes toward more interfaces*, because every seam
is an interface and every testable collaboration wants one.

**(b)** YAGNI says don't build the abstraction until something needs it — and
Fowler confirms (§1.4) that this covers abstractions, not only features. DRY-as-
knowledge says don't unify two things that merely look alike (§2.4). Metz says
the wrong abstraction is worse than the duplication it replaced (§3).

### 4.2 Does YAGNI forbid a seam introduced solely to make a test possible?

**No — and Fowler says so explicitly, in the source, without any extension from
us.** From §1.5, **Quoted**:

> Yagni only applies to capabilities built into the software to support a
> presumptive feature, it does not apply to effort to make the software easier to
> modify. […] Similar reasoning applies for practices like SelfTestingCode and
> ContinuousDelivery. These are enabling practices for evolutionary design,
> without them yagni turns from a beneficial practice into a curse.

`SelfTestingCode` is named. A seam that exists to make the code self-testing is
therefore, by Fowler's own carve-out, **outside YAGNI's scope**. The
contradiction the reader senses is with the *folk* YAGNI ("never add anything you
don't strictly need right now"), not with Fowler's YAGNI.

**The limiting condition is still binding, though.** Fowler's other test (§1.4)
does apply: *"any abstraction that makes it harder to understand the code for
current requirements is presumed guilty."* And (§1.5): *"yagni only applies when
you introduce extra complexity now that you won't take advantage of until later.
If you do something for a future need that doesn't actually increase the
complexity of the software, then there's no reason to invoke yagni."* A test
*does* take advantage of the seam **now**. So the seam is not deferred value —
it is realised value, today, in the test.

### 4.3 Is a test double a legitimate "second implementation"?

**No named source located poses the question in those words.** That is a genuine
negative result and it is reported as such. What we *did* find is the adjacent
question, answered head-on, in a peer-reviewed OOPSLA paper.

**Metadata verified** — Steve Freeman, Nat Pryce, Tim Mackinnon, Joe Walnes,
"Mock Roles, not Objects", ThoughtWorks UK. OOPSLA 2004 companion. PDF at
`https://jmock.org/oopsla2004.pdf`, 11 pages. ACM SIGPLAN categories
D.2.2 [Software Engineering]: Design Tools and Techniques.

**Quoted**, abstract:

> Mock Objects is an extension to Test-Driven Development that supports good
> Object-Oriented design by guiding the discovery of a coherent system of types
> within a code base. **It turns out to be less interesting as a technique for
> isolating tests from third-party libraries than is widely thought.**

(Bold ours.) And the opening line of §1 — **Quoted**:

> Mock Objects is misnamed. It is really a technique for identifying types in a
> system based on the roles that objects play.

> In particular, we now understand that the most important benefit of Mock
> Objects is what we originally called "interface discovery".

**The key passage — and it is, almost word for word, a YAGNI argument.** They
ground the whole practice in Lean **pull**. **Quoted**, §2.1 "Need-Driven
Development":

> A core principle of Lean Development is that value should be pulled into
> existence from demand, rather than pushed from implementation: "The effect of
> 'pull' is that production is not based on forecast; commitment is delayed until
> demand is present to indicate what the customer really wants."
>
> This is the flow of programming with Mock Objects. By testing an object in
> isolation, the programmer is forced to consider an object's interactions with
> its collaborators in the abstract, possibly before those collaborators exist.
> **TDD with Mock Objects guides interface design by the services that an object
> requires, not just those it provides.** This process results in a system of
> narrow interfaces each of which defines a role in an interaction between
> objects, rather than wide interfaces that describe all the features provided by
> a class. We call this approach Need-Driven Development.

(Bold ours.) They then show it concretely — **Quoted**, §3.1.1 "Discovering a New
Interface":

> To write the test, all we need is an empty interface called ObjectLoader so we
> can construct the mock object. To pass the test, all we need is a load() method
> that can accept a key. **We have discovered the need for a type.**

(Bold ours.) And their own scoping heuristic, which limits where seams may be
introduced — **Quoted**, §4.1 "Only Mock Types You Own":

> Mock Objects is a design technique so programmers should only write mocks for
> types that they can change. Otherwise they cannot change the design to respond
> to requirements that arise from the process. Programmers should not write mocks
> for fixed types, such as those defined by the runtime or external libraries.
> Instead they should write thin wrappers to implement the application
> abstractions in terms of the underlying infrastructure. Those wrappers will
> have been defined as part of a need-driven test.

Plus a warning that mocks *diagnose* rather than paper over bad design —
**Quoted**, §4:

> Mock Objects is a design aid, but is no substitute for skilled developers. Our
> experience is that mock-based tests quickly become too complicated when the
> system design is weak. **The use of mock objects amplifies problems such as
> tight coupling and misallocated responsibilities.** One response to such
> difficulties is to stop using Mock Objects, but we believe that it is better to
> use this as a motivator for improving the design.

**Metadata verified** — the book-length treatment: Steve Freeman & Nat Pryce,
*Growing Object-Oriented Software, Guided by Tests*, published **12 Oct 2009**,
384 pages, **ISBN-13 978-0-321-50362-6** (ISBN-10 0-321-50362-7). Source:
`https://www.informit.com/store/growing-object-oriented-software-guided-by-tests-9780321503626`.
We did **not** read the book text; no claim here is sourced to it.

### 4.4 The synthesis

`[OUR ANALOGY]` — everything in this subsection is our inference, built on the
quotes above. No located author states it in this combined form.

The principles are not in conflict; they are **four different tests applied at
four different moments**, and they agree on one thing: *the
justification for an abstraction must exist at the moment you create it.*

| Principle | Question it asks | Fails when |
| --- | --- | --- |
| YAGNI (Fowler) | Does anything *use* this today? Does it make current code harder to read? | The abstraction serves a presumptive feature only |
| DRY-as-knowledge (Thomas & Hunt) | Do these two sites express the *same knowledge*? | Two identical bodies represent two different rules |
| Wrong abstraction (Metz) | Are callers passing parameters and adding conditionals? | The shared code has become a condition-laden procedure |
| Need-driven design (Freeman & Pryce) | Did a *need* pull this interface into existence? | The interface was pushed from implementation, or forecast |

A seam introduced by a failing test passes all four:

- YAGNI: it has a consumer **today** — the test. Fowler's `SelfTestingCode`
  carve-out covers it explicitly.
- DRY: it does not unify anything, so the coincidence trap does not apply.
- Metz: it was not extracted from observed duplication, so her lifecycle never
  starts. Her failure mode is *extraction from duplication*; a seam is
  *discovery from need*.
- Freeman & Pryce: this is precisely their Need-Driven Development — the
  interface is *pulled into existence from demand*.

A seam introduced **without** a test, "because we'll probably swap the
implementation one day," fails all four. That is the real dividing line, and it
is not "one implementation vs two" — it is **pushed vs pulled**.

`[OUR ANALOGY]` — the "one adapter = hypothetical seam, two = real" heuristic in
Matt Pocock's `codebase-design` vocabulary is *stricter* than Fowler and
Freeman/Pryce would require, since a test double supplies the second
implementation on their reading. Worth flagging to the reader as a deliberate
tightening rather than a restatement, but note this comparison is ours; nobody
located makes it.

### 4.5 The honest residue

The tension does not vanish entirely. **Quoted** (Fowler again):

> there are times when applying yagni does cause a problem, and you are faced
> with an expensive change when an earlier change would have been much cheaper.
> The tricky thing here is that these cases are hard to spot in advance

And Rainsberger, **Quoted**, admits the community has not settled it: *"it
continues to confuse and divide programmers."* A lesson should say so.

---

## 5. YAGNI/DRY in an LLM/agent context

### 5.1 What the upstream repo actually does with YAGNI

The repo at `../skills` contains **exactly one** occurrence of "YAGNI" in a
SKILL.md, and **zero** occurrences of "DRY" or "don't repeat yourself".

**Quoted** — `skills/engineering/improve-codebase-architecture/SKILL.md:20`:

> **Scope before you scan — YAGNI.** Deepening a module pays off by making future
> changes to it easier, so put extra weight on the parts of the codebase that
> have recently changed. Decide *where* to look before you look:

**Metadata verified** — introduced in commit `45afd80`, "improve-codebase-
architecture: scope the scan to where change is landing (YAGNI)", **2026-07-13**.

**Quoted** — the accompanying changeset, `.changeset/yagni-scope-improve-architecture.md`,
which is the author explaining his own usage:

> Add a YAGNI scoping filter to the **`improve-codebase-architecture`** skill's
> Explore step. Instead of scanning the whole repo evenly, it now scopes to where
> change is actually landing: if you name a direction it takes it, otherwise it
> reads the last ~20 commit messages to bias exploration toward actively-developed
> paths. **A deepening opportunity in code nobody touches is a refactor you'll
> never cash in — the leverage only pays off where you keep editing** — so the
> report stops tidying dormant corners of the repo.

(Bold ours.)

**Verdict: a legitimate extension, not a drift — and closer to Fowler than the
folk reading is.** The argument, from the changeset text rather than from
assertion:

The folk YAGNI is "don't build it yet." Pocock's use is "don't *refactor* it
yet, here." Superficially that looks like a different rule applied to a different
object (attention rather than code). But read it against Fowler's cost model and
it lines up: a refactor of dormant code is effort spent whose payoff — easier
future changes — arrives only if future changes arrive. It is a **presumptive
investment**, and it is priced by exactly Fowler's *cost of delay* (the effort
could have gone to a hot path that pays back now) and *cost of carry* (the
refactored-but-untouched module still has to be carried). The changeset even
states the payoff condition in Fowler's own economic register: *"a refactor
you'll never cash in."*

Where it *does* differ from Fowler, and this should be said in a lesson: Fowler's
carve-out (§1.5) says yagni "does not apply to effort to make the software easier
to modify" — refactoring is explicitly *exempt*. Pocock is applying YAGNI to the
allocation of refactoring effort, which is the one place Fowler's sentence tells
you not to apply it.

`[OUR ANALOGY]` — the reconciliation is that Fowler's carve-out answers "may I
refactor at all?" (yes, always) while Pocock's heuristic answers "which of the
many possible refactors do I do first?" (the ones on hot paths). Those are
different questions and the two are compatible. But this reconciliation is
**ours**; neither author states it.

### 5.2 Over-abstraction and LLM agents — what actually exists

**One primary source found, and it is narrower than the claim people want to make
with it.** **Quoted** — Anthropic, "Building Effective Agents",
`https://www.anthropic.com/engineering/building-effective-agents`:

> There are many frameworks that make agentic systems easier to implement […]
> However, they often create extra layers of abstraction that can obscure the
> underlying prompts and responses, making them harder to debug. They can also
> make it tempting to add complexity when a simpler setup would suffice.
>
> We suggest that developers start by using LLM APIs directly: many patterns can
> be implemented in a few lines of code. If you do use a framework, ensure you
> understand the underlying code. Incorrect assumptions about what's under the
> hood are a common source of customer error.

And — **Quoted**:

> Consistently, the most successful implementations weren't using complex
> frameworks or specialized libraries. Instead, they were building with simple,
> composable patterns.

> Frameworks can help you get started quickly, but don't hesitate to reduce
> abstraction layers and build with basic components as you move to production.

**Scope warning — read this before using the quote.** Anthropic is talking about
**abstraction layers in the agent-building framework that a human developer
adopts**, and the harm they name is to **human debuggability** ("harder to
debug", "incorrect assumptions about what's under the hood"). They are **not**
claiming that over-abstracted application code is harder for an LLM to navigate.
Using this quote to support the latter would be exactly the kind of stretch this
workspace has already had to retract twice.

**No primary source found** for the specific proposition "over-abstraction hurts
LLM agents reading a codebase." Searches surfaced only secondary commentary and
survey papers that do not state it as a finding. This is reported as a **negative
result**. Any lesson making that claim must mark it `[OUR INFERENCE]`.

`[OUR ANALOGY]` — a defensible inference, clearly labelled: Metz's failure mode
(a shared method reached through many callers, differentiated by parameters and
conditionals) requires a reader to hold every caller in mind at once to predict
one call's behaviour. That is a *locality* cost, and locality is the property the
`codebase-design` vocabulary already prices for AI navigability. The bridge from
"hurts a human maintainer" to "hurts an LLM agent" is ours, and rests on the
shared premise that both read code with a bounded working set. **No cited author
makes it.**

---

## 6. Open items

- **Rule of Three, verbatim from Fowler** — unresolved. Needs a read of
  *Refactoring* (1st or 2nd ed.). Do not state the wording or the Don Roberts
  attribution as verified until then.
- **Kohavi et al.** — the ⅓ figure is Fowler-reported; the study was not fetched.
- **Kent Beck, "make the change easy, then make the easy change"** — not fetched;
  do not cite.
- **GOOS book text** — metadata verified only; the OOPSLA 2004 paper carries all
  claims made here.

---

## What is quotable in a lesson

Tier-1 **Quoted** only. Every line below was read from the artifact named.

1. **Fowler's scoping sentence — the single best line for the contradiction beat.**
   > "Yagni only applies to capabilities built into the software to support a presumptive feature, it does not apply to effort to make the software easier to modify."
   — Martin Fowler, "Yagni", 26 May 2015. `https://martinfowler.com/bliki/Yagni.html`

2. **Fowler on abstractions specifically.**
   > "This doesn't mean to forego all abstractions, but it does mean any abstraction that makes it harder to understand the code for current requirements is presumed guilty."
   — same URL.

3. **The canonical DRY statement.** Cite the edition — see §2.2; the 1999 wording is unverified.
   > "Every piece of knowledge must have a single, unambiguous, authoritative representation within a system."
   — Thomas & Hunt, *The Pragmatic Programmer, 20th Anniversary Edition*, Topic 9, Tip 15. `https://media.pragprog.com/titles/tpp20/dry.pdf`

4. **The authors' own retraction of the misreading.**
   > "In the first edition of this book we did a poor job of explaining just what we meant by Don't Repeat Yourself. Many people took it to refer to code only: they thought that DRY means 'don't copy-and-paste lines of source.' That is part of DRY, but it's a tiny and fairly trivial part."
   — same PDF, section "DRY is More Than Code".

5. **The coincidence rule — the strongest anti-over-DRY line available.**
   > "They are wrong. The code is the same, but the knowledge they represent is different. The two functions validate two separate things that just happen to have the same rules. That's a coincidence, not a duplication."
   — same PDF, section "Not All Code Duplication is Knowledge Duplication".

6. **Dave Thomas, sixteen years earlier, in conversation.**
   > "Most people take DRY to mean you shouldn't duplicate code. That's not its intention. The idea behind DRY is far grander than that."
   — Bill Venners interview, 10 March 2003. `https://www.artima.com/articles/orthogonality-and-the-dry-principle`

7. **Metz's thesis.**
   > "duplication is far cheaper than the wrong abstraction"
   — Sandi Metz, first asserted in "All the Little Things", RailsConf 2014; re-stated in "The Wrong Abstraction", 20 Jan 2016. `https://sandimetz.com/blog/2016/1/20/the-wrong-abstraction`

8. **Metz's falsifiable test — better than the thesis line for teaching.**
   > "If you find yourself passing parameters and adding conditional paths through shared code, the abstraction is incorrect. It may have been right to begin with, but that day has passed."
   — same URL.

9. **The pull argument for seams — the direct answer to "isn't a seam a YAGNI violation?"**
   > "TDD with Mock Objects guides interface design by the services that an object requires, not just those it provides. This process results in a system of narrow interfaces each of which defines a role in an interaction between objects, rather than wide interfaces that describe all the features provided by a class. We call this approach Need-Driven Development."
   — Freeman, Pryce, Mackinnon & Walnes, "Mock Roles, not Objects", OOPSLA 2004, §2.1. `https://jmock.org/oopsla2004.pdf`

10. **The upstream repo's own YAGNI usage, in the author's words.**
    > "A deepening opportunity in code nobody touches is a refactor you'll never cash in — the leverage only pays off where you keep editing"
    — `mattpocock/skills`, `.changeset/yagni-scope-improve-architecture.md`, commit `45afd80`, 2026-07-13.
