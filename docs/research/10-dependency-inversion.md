# The Dependency Inversion Principle — origin, exact wording, and the distinction people collapse

Research file for the lesson that compares **DIP** (Robert C. Martin, 1996) with what Matt Pocock's
`agent-skills` repo actually teaches. The repo teaches **dependency injection** and a
**seam-placement discipline**; it never mentions dependency inversion. This file supplies the
primary-source material to make that comparison rigorous.

> **Method note.** The 1996 article was *downloaded and read in full* from the Wayback Machine
> (10 pages of body text, 12 PDF pages). Fowler's 2004 article was *downloaded and read in full*.
> Dan North's two CUPID posts were *downloaded and read in full*. Nothing in the "Quoted" tier below
> is second-hand from a blog that quotes the source.

---

## Evidence tiers

| Tier | Meaning |
| --- | --- |
| **Quoted** | The text was fetched and the quote is verbatim from it. A URL is given. |
| **Metadata verified** | Title / author / publisher / year / ISBN came from a publisher page, DOI, or the author's own site. |
| **Chapter located** | A real table of contents or searchable excerpt was seen. Chapter numbers without this tag are *not* given. |
| **`[unverified]`** | Everything else. Believed true, not confirmed against a primary source. |

Standing rules: no guessed locators; no page number that was not seen; an unverified claim stays
visibly unverified.

---

## 1. The original article

### 1.1 What the artifact is, and what it says about its own date

**Artifact fetched.** `dip.pdf`, 12 PDF pages, retrieved from the Internet Archive's capture of
Object Mentor's article library:
<https://web.archive.org/web/20151204043748/http://www.objectmentor.com/resources/articles/dip.pdf>
(original URL `http://www.objectmentor.com/resources/articles/dip.pdf`).

**Title, verbatim from page 1 of the PDF: "The Dependency Inversion Principle".** **Quoted.**

The article dates *itself* from the inside. **Quoted**, page 1:

> "This is the third of my **Engineering Notebook** columns for **The C++ Report**."

> "My last article (Mar, 96) talked about the Liskov Substitution Principle (LSP)."

> "This need to check the type violates the Open-Closed Principle (OCP) that we discussed last
> January."

So from the artifact alone: **OCP = January 1996; LSP = March 1996; DIP = the third column,
therefore after March 1996**, in *The C++ Report*. That much is **Quoted**.

**The specific month "May 1996" is `[unverified]`.** It is the figure that circulates everywhere
(Wikipedia, countless blogs), but no artifact seen in this research states it. Do not print
"May 1996" as verified. **Also do not use the PDF's internal creation date (`D:19970307222940`) as a
publication date** — that is an Acrobat Distiller stamp from when the PDF was produced, not when the
column ran.

**Corroboration of the year 1996 from a second Object Mentor artifact.** Erik Meade,
*Design Principles in Test First Programming* (PDF metadata creation date `D:20000811`), fetched from
<https://web.archive.org/web/2015/http://www.objectmentor.com/resources/articles/DesignPrin.pdf>.
**Quoted:**

> "…the Open/Closed, Liskov Substitution Principle and Dependency Inversion principles, introduced
> to us in **1996** by Robert C. Martin."

Martin also dates the *ancestry* of the whole principle set himself. **Quoted**, from his own wiki at
<http://butunclebob.com/ArticleS.UncleBob.PrinciplesOfOod>:

> "In March of 1995, in comp.object, I wrote an article that was the first glimmer of a set of
> principles for OOD that I have written about many times since."

### 1.2 The principle, verbatim

**Quoted**, page 6 of the PDF (the original sets it in small caps; reproduced here in normal case,
wording unchanged):

> **The Dependency Inversion Principle**
>
> **A. High level modules should not depend upon low level modules. Both should depend upon
> abstractions.**
>
> **B. Abstractions should not depend upon details. Details should depend upon abstractions.**

This is the two-part statement. Part **B** is the half that almost every retelling drops, and it is
the half that carries the ownership claim discussed in §3.

**Why the word "inversion".** **Quoted**, page 6:

> "One might question why I use the word 'inversion'. Frankly, it is because more traditional
> software development methods, such as Structured Analysis and Design, tend to create software
> structures in which high level modules depend upon low level modules, and in which abstractions
> depend upon details. … Thus, the dependency structure of a well designed object oriented program is
> 'inverted' with respect to the dependency structure that normally results from traditional
> procedural methods."

Martin's much later one-line compression, from his own wiki
(<http://butunclebob.com/ArticleS.UncleBob.PrinciplesOfOod>) — **Quoted**:

> "**DIP** — The Dependency Inversion Principle — Depend on abstractions, not on concretions."

### 1.3 What the principle is *for* — the problem it solves

Martin opens by defining bad design as three named traits. **Quoted**, page 2:

> "1. It is hard to change because every change affects too many other parts of the system.
> (Rigidity)
> 2. When you make a change, unexpected parts of the system break. (Fragility)
> 3. It is hard to reuse in another application because it cannot be disentangled from the current
> application. (Immobility)"

And the diagnosis. **Quoted**, page 2:

> "What is it that makes a design rigid, fragile and immobile? It is the interdependence of the
> modules within that design."

The motivating *purpose* is **reuse of high-level policy**. **Quoted**, page 6:

> "Moreover, it is high level modules that we want to be able to reuse. We are already quite good at
> reusing low level modules in the form of subroutine libraries. When high level modules depend upon
> low level modules, it becomes very difficult to reuse those high level modules in different
> contexts."

> "This predicament is absurd! It is the high level modules that ought to be forcing the low level
> modules to change. … High level modules simply should not depend upon low level modules in any
> way."

And the framing claim. **Quoted**, page 7:

> "This is the principle that is at the very heart of framework design."

### 1.4 The motivating examples — BOTH are in the article

The question in the brief ("Copy/Reader/Writer *or* lamp/button — verify which") resolves to
**both**. The article uses **Copy** to introduce the idea and **Button/Lamp** to show who owns the
abstraction.

**Example 1 — the Copy program.** **Quoted**, page 3, section heading: *"Example: the 'Copy'
program."*

> "Consider a simple program that is charged with the task of copying characters typed on a keyboard
> to a printer."

Naive version (Listing 1, page 4) — **Quoted verbatim**:

```c
void Copy()
{
  int c;
  while ((c = ReadKeyboard()) != EOF)
    WritePrinter(c);
}
```

The degradation he predicts when you add a second device with a flag (Listing 2). **Quoted**,
page 4:

> "However this adds new interdependencies to the system. As time goes on, and more and more devices
> must participate in the copy program, the 'Copy' module will be littered with if/else statements
> and will be dependent upon many lower level modules. It will eventually become rigid and fragile."

Inverted version (Listing 3, page 5) — **Quoted verbatim**:

```cpp
class Reader
{
public:
  virtual int Read() = 0;
};

class Writer
{
public:
  virtual void Write(char) = 0;
};

void Copy(Reader& r, Writer& w)
{
  int c;
  while((c=r.Read()) != EOF)
    w.Write(c);
}
```

**Quoted**, page 5:

> "Thus the dependencies have been *inverted*; the 'Copy' class depends upon abstractions, and the
> detailed readers and writers depend upon the same abstractions."

**A detail worth teaching: DIP is not an OO-only claim.** Martin immediately concedes that C's
`stdio.h` achieves the same inversion without classes. **Quoted**, pages 5–6:

> "It is true that Listing 4 does not use classes and pure virtual functions, yet it still uses
> abstraction and polymorphism to achieve its ends. Moreover, it still uses dependency inversion! …
> Thus the device independence within the `stdio.h` library is another example of dependency
> inversion."

**Example 2 — Button and Lamp.** This is the stronger example for our purposes because it makes the
*ownership* of the interface explicit. **Quoted**, page 8:

> "Note that the Button class depends directly upon the Lamp class. In fact, the `button.cc` module
> `#include`s the `lamp.h` module. This dependency implies that the button class must change, or at
> very least be recompiled, whenever the Lamp class changes."

The recovery move. **Quoted**, page 10, section heading *"Finding the Underlying Abstraction"*:

> "What is the high level policy? It is the abstractions that underlie the application, the truths
> that do not vary when the details are changed. In the Button/Lamp example, the underlying
> abstraction is to detect an on/off gesture from a user and relay that gesture to a target object.
> What mechanism is used to detect the user gesture? Irrelevant! What is the target object?
> Irrelevant! These are details that do not impact the abstraction."

And the code (Listing 6, page 10) — **Quoted verbatim**, abridged to the load-bearing declarations:

```cpp
class ButtonClient
{
public:
  virtual void TurnOn() = 0;
  virtual void TurnOff() = 0;
};

class Button
{
public:
  Button(ButtonClient&);
  void Detect();
  virtual bool GetState() = 0;
private:
  ButtonClient* itsClient;
};

class Lamp : public ButtonClient
{
public:
  virtual void TurnOn();
  virtual void TurnOff();
};
```

**Read the ownership off the code.** `ButtonClient` is declared *for* `Button` — the high-level
policy. `Lamp`, the low-level detail, is what `: public ButtonClient` bends to fit. The interface
belongs to the caller, not to the implementor. That is dependency **inversion**. The constructor
`Button(ButtonClient&)` is dependency **injection**. The article does both in the same eight lines,
which is exactly why the two get conflated.

**And what to do when the detail is third-party.** **Quoted**, page 11:

> "Once could make a legitimate complaint about the design in Figure/Listing 6. The device controlled
> by the button must be derived from ButtonClient. What if the Lamp class comes from a third party
> library, and we cannot modify the source code."
>
> "Figure 7 demonstrates how the Adapter pattern can be used to connect a third party Lamp object to
> the model. The LampAdapter class simply translates the TurnOn and TurnOff message inherited from
> ButtonClient, into whatever messages the Lamp class needs to see."

(Note the vocabulary collision with the repo: Martin's *Adapter* here is the GoF pattern; the repo's
**adapter** is "a concrete thing that satisfies an interface at a seam" — `SKILL.md:26`. Related, not
identical. Do not let a lesson slide between the two senses without flagging it.)

**Layering — the transitivity argument.** **Quoted**, page 7:

> "*Dependency is transitive*. The Policy Layer depends upon something that depends upon the Utility
> Layer, thus the Policy Layer transitively depends upon the Utility Layer. This is very
> unfortunate."

### 1.5 Closing framing

**Quoted**, page 12:

> "The principle of dependency inversion is at the root of many of the benefits claimed for
> object-oriented technology. Its proper application is necessary for the creation of reusable
> frameworks."

(The PDF renders "benefits" with an `fi` ligature; the transcription above is the plain-ASCII form.
Elsewhere the artifact does contain genuine typos — "Once could make a legitimate complaint" on
page 11 is the article's own, reproduced verbatim in §1.4.)

Also **Quoted**, page 12, useful for the genealogy:

> "This article is an extremely condensed version of a chapter from my new book: Patterns and
> Advanced Principles of OOD, to be published soon by Prentice Hall."

That working title is *not* the title the book eventually shipped under — see §2.

---

## 2. The later restatements

### 2.1 *Agile Software Development, Principles, Patterns, and Practices*

**Metadata verified** from the publisher's store page,
<https://www.informit.com/store/agile-software-development-principles-patterns-and-practices-9780135974445>:

- Full title: **Agile Software Development, Principles, Patterns, and Practices**
- Author: **Robert C. Martin** · Publisher: **Pearson** · Edition: **1st**
- **Copyright year: 2003** · **552 pages**
- **ISBN-10: 0-13-597444-5 · ISBN-13: 978-0-13-597444-5**

> **Note on the year.** This book is very commonly cited as **2002**. The publisher's page states
> **2003**. Cite 2003 with the publisher page as evidence, or write "2002/2003" — do not silently
> print 2002 as verified.

**Chapter located.** Part II of the publisher's TOC, reproduced verbatim:

> **7. What Is Agile Design?**
> **8. SRP: The Single-Responsibility Principle.**
> **9. OCP: The Open-Closed Principle.**
> **10. LSP: The Liskov Substitution Principle.**
> **11. DIP: The Dependency-Inversion Principle.**
> **12. ISP: The Interface-Segregation Principle.**

So **Chapter 11, "DIP: The Dependency-Inversion Principle"**, in Part II (Agile Design). Note the
hyphenation *"Dependency-Inversion"* in the book — and *"Single-Responsibility"*,
*"Interface-Segregation"* — versus the 1996 article's unhyphenated *"Dependency Inversion"*. The
hyphenation is confirmed against the verbatim TOC lines above, not inferred.

The chapter's *body text* was not fetched. Any claim about what the chapter says beyond its title is
`[unverified]`.

### 2.2 *Clean Architecture*

**Metadata verified** from the publisher's store page,
<https://www.informit.com/store/clean-architecture-a-craftsmans-guide-to-software-structure-9780134494166>:

- Full title: **Clean Architecture: A Craftsman's Guide to Software Structure and Design**
- Author: **Robert C. Martin** · Publisher/series: **Pearson**, Robert C. Martin Series
- Edition: **1st** · **Copyright year: 2018** · **432 pages**
- **ISBN-10: 0-13-449416-4 · ISBN-13: 978-0-13-449416-6**

> **Note on the year.** Commonly cited as **2017** (first printing). The publisher states **2018**.
> Same discipline as above.

**Chapter located** (publisher TOC): **Chapter 11, "DIP: The Dependency Inversion Principle",
beginning on page 87**, with sections **"Stable Abstractions" · "Factories" · "Concrete Components" ·
"Conclusion"**. Separately, **Chapter 14, "Component Coupling", page 111**, contains **"The Stable
Dependencies Principle"** and **"The Stable Abstractions Principle"**.

**On the stable/volatile qualification: `[unverified]`.** The brief asks whether Martin himself
qualifies DIP — e.g. that stable concretions such as `String` need not be inverted, only *volatile*
ones. The **section title "Stable Abstractions" inside Chapter 11 is real and located**, and it is
consistent with that qualification existing. But **the body text of Chapter 11 was not fetched**, so
the qualification itself, and any wording like "don't refer to volatile concrete classes," is
**`[unverified]`** here. Do not paraphrase it into a lesson from memory or from a blog that quotes
it. Cost to close: acquire the book, or a legitimate searchable excerpt of pp. 87–90.

**What Martin *does* verifiably qualify, in the 1996 artifact.** Nothing. The 1996 article states DIP
without a "only when it varies" escape hatch, and generalises hard — *"Dependency Inversion can be
applied wherever one class sends a message to another"* (**Quoted**, page 8). That absence of a brake
in the founding text is itself the finding, and is what North attacks in §5.

---

## 3. THE CENTRAL DISTINCTION — DI vs DIP vs IoC

This is the load-bearing section.

### 3.1 Fowler's article — metadata and the naming

**Artifact fetched and read in full:** Martin Fowler, **"Inversion of Control Containers and the
Dependency Injection pattern"**, <https://martinfowler.com/articles/injection.html>.

**Dates — Quoted, from the fetched page's own markup and its revision history:**

- `<p class='date'>23 January 2004</p>` — the byline date rendered on the page.
- `<meta content='2004-01-23' property='og:article:modified_time'>`
- From the article's own "Revision History" block, verbatim:
  > "23 January 2004: Redid the configuration code of the interface…"
  > "16 January 2004: Added a short example of both locator and…"
  > "**14 January 2004: First Publication**"

So: **first published 14 January 2004, revised 23 January 2004**, and it is the 23rd that the page
displays as its date. Cite "January 2004" and, if precision is needed, "first published 14 January
2004 (Quoted, from the article's own revision history)".

**Where he names it. Quoted:**

> "As a result I think we need a more specific name for this pattern. Inversion of Control is too
> generic a term, and thus people find it confusing. As a result with a lot of discussion with
> various IoC advocates we settled on the name Dependency Injection."

Note two things a lesson should not smooth over: he says **"we settled on"**, not "I coined" — the
naming is credited to a discussion with IoC advocates, not to himself alone. And the reason is
purely **terminological hygiene**: IoC was too generic.

**What "inversion" means in *his* usage** is the inversion of *control flow*, not of dependency
direction. **Quoted:**

> "When I first ran into inversion of control, it was in the main control of a user interface. …
> With graphical (or even screen based) UIs the UI framework would contain this main loop and your
> program instead provided event handlers for the various fields on the screen. The main control of
> the program was inverted, moved away from you to the framework."

> "For this new breed of containers the inversion is about how they lookup a plugin implementation."

**The three forms. Quoted:**

> "There are three main styles of dependency injection. The names I'm using for them are Constructor
> Injection, Setter Injection, and Interface Injection. If you read about this stuff in the current
> discussions about Inversion of Control you'll hear these referred to as type 1 IoC (interface
> injection), type 2 IoC (setter injection) and type 3 IoC (constructor injection). I find numeric
> names rather hard to remember, which is why I've used the names I have here."

**What DI is mechanically. Quoted:**

> "The basic idea of the Dependency Injection is to have a separate object, an assembler, that
> populates a field in the lister class with an appropriate implementation for the finder interface"

**Constructor injection, his Java example. Quoted verbatim:**

```java
class MovieLister...
  public MovieLister(MovieFinder finder) {
    this.finder = finder;
  }
```

**DI vs Service Locator. Quoted:**

> "The fundamental choice is between Service Locator and Dependency Injection. The first point is
> that both implementations provide the fundamental decoupling that's missing in the naive example -
> in both cases application code is independent of the concrete implementation of the service
> interface. The important difference between the two patterns is about how that implementation is
> provided to the application class. With service locator the application class asks for it
> explicitly by a message to the locator. With injection there is no explicit request, the service
> appears in the application class - hence the inversion of control."

**And his own brake on IoC, which almost nobody quotes. Quoted:**

> "Inversion of control is a common feature of frameworks, but it's something that comes at a price.
> It tends to be hard to understand and leads to problems when you are trying to debug. So on the
> whole I prefer to avoid it unless I need it. This isn't to say it's a bad thing, just that I think
> it needs to justify itself over the more straightforward alternative."

**The conclusion he actually lands on. Quoted:**

> "…the principle of separating configuration from use."

### 3.2 The silence that is itself evidence

**Verified by exhaustive search of the fetched artifact:** the phrases **"dependency inversion"** and
**"inversion principle"** appear **zero times** in `injection.html`. Method: the page was downloaded,
tags stripped, and case-insensitively grepped; both patterns returned no matches.

**Scope this claim precisely.** It says: *the article that named Dependency Injection never mentions
the Dependency Inversion Principle.* It does **not** say Fowler never linked them anywhere.

**Related check, both index pages actually fetched and grepped.**

- Tag index, <https://martinfowler.com/tags/> (fetched): the dependency-adjacent entries are
  **"Dependency And Association"** (`/bliki/DependencyAndAssociation.html`) and
  **"Dependency Composition"** (`/articles/dependency-composition.html`), plus the DI article itself.
  No "Dependency Inversion" tag or entry.
- Bliki index, <https://martinfowler.com/bliki/> (fetched, 43 KB of real HTML — not a redirect
  stub): the substring "dependen" occurs **exactly once** in the whole page, inside unrelated prose
  ("…have one that contains many good but independent and uncoordinated…"). **No bliki entry title
  contains the word at all.**

So: **no bliki entry on the Dependency Inversion Principle, and no "DIP in the Wild" article by
Fowler, was found on either index.** That is a real check against real fetched pages, but it is an
index check — not a proof of absence across every page of his site.

### 3.3 Stating the distinction precisely

Put the two verbatim statements side by side:

| | **Dependency Injection** (Fowler, 2004) | **Dependency Inversion** (Martin, 1996) |
| --- | --- | --- |
| Verbatim core | "have a separate object, an assembler, that populates a field in the lister class with an appropriate implementation for the finder interface" | "High level modules should not depend upon low level modules. Both should depend upon abstractions." + "Abstractions should not depend upon details. Details should depend upon abstractions." |
| What it constrains | **How** a collaborator reaches the object: passed in, not constructed inside. | **Who owns the abstraction**: the high-level policy declares it; the detail conforms to it. |
| Kind of claim | A wiring **mechanism**. | A **direction-of-dependency** claim about the source graph. |
| Can you have one without the other? | **Yes.** Inject a concrete class → injection, zero inversion. | **Yes.** `#include "abstract_writer.h"` and `new PrinterWriter()` inside → inversion of the interface, no injection. |

**The unambiguous case that injection ≠ inversion.** Take Fowler's own constructor-injection
snippet's shape and substitute a concrete type:

```java
// Injection, no inversion — the parameter type IS the low-level detail.
class TransferService {
    TransferService(PostgresLedgerRepository ledger) { ... }
}
```

Nothing here violates Fowler's definition: an assembler still populates the field, the class still
does not `new` its collaborator, and it is still testable-ish. But it violates **part A** of Martin's
statement flatly — the high-level module `TransferService` depends upon the low-level module
`PostgresLedgerRepository`. **Injection happened; inversion did not.**

The inverse case matters too, and is why "DIP = DI" is wrong in both directions: Martin's Listing 4
(`stdio.h`) has **inversion with no injection at all** — no assembler, no constructor parameter,
just an abstraction that both the policy and the driver depend on. **Quoted**, page 6: *"Thus the
device independence within the `stdio.h` library is another example of dependency inversion."*

**The ownership test, stated as a question you can run on any codebase.** Martin never phrases it as
a test, but Listing 6 shows the shape: **whose package does the interface live in?** If
`ButtonClient` lives with `Button` (the policy), the dependency is inverted. If the interface ships
with the implementor — if `LedgerRepository` lives in the persistence package next to its Postgres
implementation — you have an interface, and injection, and **no inversion**: the policy still points
at the detail's package. Flag this framing as **`[OUR ANALOGY]`** when used in a lesson; the
package-location heuristic is our restatement, not Martin's wording.

**IoC vs DI vs DIP, in one line each:**

- **IoC** — control flow runs from the framework into your code. Fowler's UI-loop example. Broadest,
  oldest, and per Fowler *"too generic a term."* **Quoted.**
- **DI** — a specific IoC pattern: an assembler supplies a collaborator. **Quoted.**
- **DIP** — a design principle about who depends on whom and who owns the abstraction. **Quoted.**
  Not a pattern, not a mechanism, and not mentioned in the article that named DI.

---

## 4. Who coined "SOLID"?

**Status: `[unverified` — Martin's own words were not located`]`.**

What was checked, and what was found:

- <http://butunclebob.com/ArticleS.UncleBob.PrinciplesOfOod> — Martin's own page listing the
  principles. **Fetched and grepped in full.** It lists SRP/OCP/LSP/ISP/DIP and the package
  principles; **the word "Feathers" does not appear, and the acronym "SOLID" is never introduced or
  attributed.** The only occurrence of the string "solid" on the page is inside a reader comment
  using the word in its ordinary sense.
- `http://butunclebob.com/ArticleS.UncleBob.GettingASolidStart` — **404, "Not Found."**
- Web search returns many secondary sources asserting that **Michael Feathers** noticed the initials
  spelled SOLID (around 2004). These are blogs and encyclopedia entries, **not Martin's own words**,
  and by this workspace's evidence bar they are **not evidence**.

**Therefore: write it as `[unverified]`.** Acceptable lesson phrasing: *"The acronym SOLID is widely
credited to Michael Feathers; Martin's own statement of that credit was not located
`[unverified]`."* Do **not** assert "Martin credits Feathers."

This matters for the course specifically because **Feathers also coined `seam`**, which the repo uses
by name (`SKILL.md:22`). If the SOLID credit holds, the same person named both the acronym the repo
avoids and the term it adopts — a genuinely interesting note, but only if verified. **Cost to close:**
find the passage in *Clean Architecture* Part III / the design-principles introduction, or a talk
transcript where Martin says it.

---

## 5. The critiques

### 5.1 Dan North — the substantive published critique of DIP

**Artifacts fetched and read in full:**

- **"CUPID — the back story"**, Dan North, <https://dannorth.net/blog/cupid-the-back-story/>
- **"CUPID — for joyful coding"**, Dan North, <https://dannorth.net/blog/cupid-for-joyful-coding/>

**Important navigation fact:** the URLs `dannorth.net/cupid-for-joyful-coding/` and
`dannorth.net/2022/02/10/cupid-for-joyful-coding/` are **noindex redirect stubs**. The live path is
`dannorth.net/blog/…`. Cite the `/blog/` form.

**Which post carries the DIP critique.** The DIP objection is in **"CUPID — the back story"**, under
a heading **"Dependency Inversion Principle"** inside a section titled **"Why every single element of
SOLID is wrong"**. **The word "dependency" appears only once in "CUPID — for joyful coding", and
"dependency inversion" not at all** (verified by grep of the fetched text). A lesson citing North on
DIP must cite the *back story* post, not the CUPID-properties post.

**His DIP objection, quoted in full. Quoted**, <https://dannorth.net/blog/cupid-the-back-story/>:

> "While there is nothing fundamentally wrong with DIP, I don't think it is an overstatement to say
> that our obsession with dependency inversion has single-handedly caused billions of dollars in
> irretrievable sunk cost and waste over the last couple of decades. The real principle here is
> option inversion. A dependency is only interesting when there might be multiple ways of providing
> it, and you only need to invert the relationship when you believe the wiring is important enough to
> become a separate concern. That's quite a high bar, and mostly all you ever need is a main method.
> If instead you subscribe to the idea that all dependencies should be inverted all the time, you end
> up with J2EE, OSGi, Spring, or any other 'declarative assembly' framework where the structuring of
> the components is itself a twisty maze of config. J2EE deserves a special mention for deciding that
> each type of dependency inversion - EJBs, servlets, web domains, remote service location, even the
> configuration configuration - should be owned by different roles. In the wild, there are entire
> shadow codebases where each class is backed by exactly one interface, which only exists to satisfy
> a wiring framework or to inject a mock or stub for automated testing theatre. The promise of 'you
> can just swap out the database' evaporates as soon as you try to, well, swap out the database. Most
> dependencies don't need inverting, because most dependencies aren't options, they are just the way
> we are going to do it this time. So my - by now entirely unsurprising - suggestion is to write
> simple code, by focusing on use rather than reuse."

Note that North **concedes the principle itself** ("nothing fundamentally wrong with DIP"). His
target is the *unconditional* application — precisely the gap flagged at the end of §2.2.

**His broader objection to SOLID as a category. Quoted**,
<https://dannorth.net/blog/cupid-for-joyful-coding/>:

> "When I started formulating a response to the five SOLID principles, I envisioned replacing each
> one with something that I found more useful or relevant. I soon realized that the idea of
> principles itself was problematic. Principles are like rules: you are either compliant or you are
> not. This gives rise to 'bounded sets' of rule-followers and rule-enforcers rather than 'centred
> sets' of people with shared values. Instead, I started thinking about properties: qualities or
> characteristics of code rather than rules to follow."

**Quoted**, <https://dannorth.net/blog/cupid-the-back-story/>:

> "When I look at SOLID, I see a mix of things that were once good advice, patterns that apply in a
> context, and advice that is easy to misapply. I wouldn't offer any of it as context-free advice to
> new programmers."

**The five CUPID properties, quoted for completeness**,
<https://dannorth.net/blog/cupid-for-joyful-coding/>:

> "Composable: plays well with others · Unix philosophy: does one thing well · Predictable: does what
> you expect · Idiomatic: feels natural · Domain-based: the solution domain models the problem domain
> in language and structure"

### 5.2 The "one interface, one implementor" bloat critique

**This requirement is closed by a named, published, primary source — North, above.** The exact line
to cite:

> "In the wild, there are entire shadow codebases where each class is backed by exactly one
> interface, which only exists to satisfy a wiring framework or to inject a mock or stub for
> automated testing theatre."

Cite that rather than hunting forum consensus. **This is published criticism by a named author with a
stable URL**, not practitioner folklore.

**What is *not* established here.** No quantitative study measuring interface-per-implementation
bloat in enterprise Java or C# was located in this research. Any number attached to this claim would
be **`[unverified]`**. The wide practitioner agreement with North's observation (visible in the IDE
affordance discussed in §6) is **practitioner opinion, not literature** — label it that way.

### 5.3 Fowler as an unwitting critic of over-inversion

Worth pairing with North: Fowler's own brake, already quoted in §3.1 — *"Inversion of control … comes
at a price. It tends to be hard to understand and leads to problems when you are trying to debug. So
on the whole I prefer to avoid it unless I need it."* The person who *named* dependency injection
recommends avoiding the inversion of control it depends on unless it justifies itself. That is a
strong, cheap, verbatim counterweight to cargo-cult DI. **Quoted.**

---

## 6. The Java-specific angle

### 6.1 The interface as the inversion mechanism

Java has no header/implementation split (Martin's §"Separating Interface from Implementation in C++"
problem, page 8 of the artifact, is a C++-specific complaint). In Java the `interface` keyword *is*
the abstraction, and the load-bearing question collapses to a package question: **does the interface
live in the policy's package or the adapter's package?** — the ownership test in §3.3. Marked
**`[OUR ANALOGY]`**; Martin does not phrase it this way.

### 6.2 `java.time.Clock` — a JDK-shipped inverted dependency

**Quoted**, from the official Java SE API documentation,
<https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/Clock.html>:

> "A clock providing access to the current instant, date and time using a time-zone."

> "The primary purpose of this abstraction is to allow alternate clocks to be plugged in as and when
> required. Applications use an object to obtain the current time rather than a static method. This
> can simplify testing."

> "Best practice for applications is to pass a `Clock` into any method that requires the current
> instant and time-zone. A dependency injection framework is one way to achieve this:"

with the javadoc's own example, **quoted verbatim**:

```java
public class MyBean {
  private Clock clock;  // dependency inject
  ...
  public void process(LocalDate eventDate) {
    if (eventDate.isBefore(LocalDate.now(clock)) {
      ...
    }
  }
}
```

This is an unusually good teaching artifact: **the JDK's own documentation uses the phrase "dependency
inject" and names a "dependency injection framework"** — and `Clock` is a genuine inversion, because
the abstract `Clock` ships in `java.time` with the policy that uses it, and `Clock.fixed(…)` /
`Clock.system(…)` are the details conforming to it. `Clock` is DI *and* DIP at once — the same
double-duty as Martin's Listing 6.

It is also the cleanest banking example available: a `Transfer` that stamps `Instant.now()` inline is
untestable; a `Transfer` that takes a `Clock` is testable and inverted, with no framework involved.

### 6.3 Spring's `@Autowired` culture

**`[unverified]` as literature.** No primary-source critique of `@Autowired` culture was fetched in
this research. What *is* citable is **North naming Spring by name** in the block quoted in §5.1
("…you end up with J2EE, OSGi, Spring, or any other 'declarative assembly' framework where the
structuring of the components is itself a twisty maze of config"). That is a named author on a stable
URL. Everything beyond it — field injection being discouraged, constructor injection being preferred
by the Spring team — was **not verified here** and must not be asserted as fact in a lesson without
its own fetch of the Spring reference documentation.

### 6.4 The "go to implementation" tell

**Practitioner observation, `[OUR ANALOGY]` / practitioner opinion — not literature.** In an IDE,
`Ctrl`-clicking an interface that has exactly one implementor jumps straight through to it, because
there is nothing to disambiguate. That single-candidate jump is the visible symptom of North's
"shadow codebase" — the interface adds a navigation hop and buys nothing. This is a useful
*pedagogical* device precisely because the reader can run it on his own Java codebase in ten seconds.
Present it as an observation to test, not as a cited finding.

### 6.5 *Effective Java* — the item on dependency injection

**Metadata verified** from
<https://www.informit.com/store/effective-java-9780134685991>: *Effective Java*, **3rd edition**,
**copyright 2018**, **416 pages**, **ISBN-13: 978-0-13-468599-1**. The chapter containing it is
confirmed as **Chapter 2, "Creating and Destroying Objects"**.

**The item number and its exact wording remain `[unverified]`.** The publisher page renders chapter
headings only; it does not itemise Chapter 2, so the individual Item numbers were not seen. Per the
no-guessed-locators rule, **no item number is given here.**

**Action for `docs/research/02-se-literature.md:1712`: leave it as `[unverified]`.** That line records
Bloch's *"Prefer dependency injection to hardwiring resources"*; this research did not clear the bar
to upgrade it. **Cost to close:** a legitimate searchable excerpt or the book itself.

**Conceptual note if it is ever verified:** Bloch's rule as commonly quoted is an *injection* rule
("hardwiring" = `new`-ing the resource inside), not an *inversion* rule. It would sit on the DI side
of the §3.3 table, not the DIP side.

---

## 7. Against the repo — what Pocock's `agent-skills` does and does not adopt

**Absence verified by search of the upstream repo** (`/home/rodrigo/Workspace/skills`, read-only), a
case-insensitive recursive grep over all `*.md`: **"dependency inversion", "SOLID principle" and
"DRY" return zero matches.** The thesis's negative half holds.

**What the repo does say. Quoted verbatim from upstream:**

| Locator | Text |
| --- | --- |
| `skills/engineering/codebase-design/SKILL.md:71` | "**Accept dependencies, don't create them.**" |
| `skills/engineering/tdd/mocking.md:20` | "**1. Use dependency injection**" — with the gloss "Pass external dependencies in rather than creating them internally" |
| `skills/engineering/codebase-design/SKILL.md:65` | "**One adapter means a hypothetical seam. Two adapters means a real one.** Don't introduce a seam unless something actually varies across it." |
| `skills/engineering/codebase-design/SKILL.md:22` | "**Seam** *(Michael Feathers)* — a place where you can alter behaviour without editing in that place; the *location* at which a module's interface lives. Where to put the seam is its own design decision, distinct from what goes behind it." |

Upstream URLs: <https://github.com/mattpocock/skills/blob/main/skills/engineering/codebase-design/SKILL.md>
and <https://github.com/mattpocock/skills/blob/main/skills/engineering/tdd/mocking.md>.

### 7.1 Finding 1 — the repo's DI examples are injection with no inversion

The repo's two illustrations, **quoted verbatim**:

```typescript
// Testable
function processOrder(order, paymentGateway) {}

// Hard to test
function processOrder(order) {
  const gateway = new StripeGateway();
}
```
— `skills/engineering/codebase-design/SKILL.md`, under "Accept dependencies, don't create them."

```typescript
// Easy to mock
function processPayment(order, paymentClient) {
  return paymentClient.charge(order.total);
}

// Hard to mock
function processPayment(order) {
  const client = new StripeClient(process.env.STRIPE_KEY);
  return client.charge(order.total);
}
```
— `skills/engineering/tdd/mocking.md`, under "Use dependency injection."

Now set that against Martin's Listing 3, **quoted verbatim** (page 5 of the artifact):

```cpp
void Copy(Reader& r, Writer& w)
```

**The comparison is exact and it is the lesson.** Both are the same *shape* — collaborator passed as
a parameter rather than constructed inside. But Martin's parameter types are `Reader` and `Writer`:
abstractions **declared for `Copy`**, which the concrete `KeyboardReader` and `PrinterWriter` then
satisfy. The repo's parameters are named `paymentGateway` and `paymentClient` and **no abstraction is
declared anywhere in the example** — the "bad" version constructs `StripeGateway`, and the "good"
version receives something Stripe-shaped. That is **part A of DIP unaddressed**: the high-level
`processOrder` still knows the low-level detail's shape; only *who calls `new`* changed.

The repo teaches exactly Fowler's claim ("have a separate object … that populates a field") and
exactly not Martin's ("Abstractions should not depend upon details"). It is DI. It is not DIP.

Caveat to state honestly in a lesson: the repo's examples are **TypeScript**, which is structurally
typed and has no `implements` requirement, so the "who owns the interface" question is genuinely less
visible there than in Java or C++. That is a real explanation for the omission, not merely a defect.

### 7.2 Finding 2 — the two-adapter rule is the brake DIP never shipped with

Two independent primary sources converge here, which is why this is the strongest claim in the file.

**Martin, 1996 (Quoted, page 8):**
> "Dependency Inversion can be applied wherever one class sends a message to another."

**North, on what that unconditional reading costs (Quoted):**
> "A dependency is only interesting when there might be multiple ways of providing it, and you only
> need to invert the relationship when you believe the wiring is important enough to become a
> separate concern. That's quite a high bar…"
>
> "Most dependencies don't need inverting, because most dependencies aren't options, they are just
> the way we are going to do it this time."

**The repo (Quoted, `SKILL.md:65`):**
> "One adapter means a hypothetical seam. Two adapters means a real one. Don't introduce a seam
> unless something actually varies across it."

North says *invert only when the dependency is genuinely an option*. The repo says *introduce the
seam only when something actually varies across it, and the count of adapters is how you know*.
**These are the same rule; the repo states it as a countable test.** North diagnosed the disease and
named the cure abstractly ("option inversion"); the repo shipped an operational threshold. That the
repo arrives there **without ever naming DIP** is the finding.

**Mark the causal claim as inference.** There is **no evidence** that Pocock read North, or that the
two-adapter rule was authored *as* a response to DIP. **`[OUR ANALOGY]`** — the convergence is
documented; the influence is not.

### 7.3 Finding 3 — the repo relocates the question, from *whether* to *where*

Martin's question is **which direction** the dependency arrow points. The repo's question, per
`SKILL.md:22`, is **where the seam goes**: *"the location at which a module's interface lives. Where
to put the seam is its own design decision, distinct from what goes behind it."*

These are close but not the same. "Where does the interface live" is, in package terms, exactly the
ownership question in §3.3 — and yet the repo frames it as a *placement* decision with a *cost*
(one more thing to learn, one more hop), not as a *principle* with a right answer. Combined with the
two-adapter threshold, the repo's net position is: **inversion is a tool you pay for, not a law you
obey.** That is compatible with DIP-as-Martin-stated-it only if you read Martin's *"wherever one
class sends a message to another"* as enthusiasm rather than instruction.

### 7.4 Finding 4 — the Feathers thread

The repo adopts **`seam`**, and credits **Michael Feathers** by name (`SKILL.md:22`). Feathers is
also the person widely credited with the SOLID acronym — **`[unverified]`, see §4**. The repo thus
takes Feathers' *vocabulary for changing existing code* while declining Feathers'(?) *acronym for
designing new code*. Do not build a lesson beat on this until §4 is closed.

---

## 8. What is quotable in a lesson

Ten verbatim lines, each with a URL, ordered by usefulness.

1. **DIP itself, both halves** — Martin, *The Dependency Inversion Principle*, p. 6:
   > "A. High level modules should not depend upon low level modules. Both should depend upon
   > abstractions. B. Abstractions should not depend upon details. Details should depend upon
   > abstractions."
   <https://web.archive.org/web/20151204043748/http://www.objectmentor.com/resources/articles/dip.pdf>

2. **Why "inversion"** — same, p. 6:
   > "…the dependency structure of a well designed object oriented program is 'inverted' with respect
   > to the dependency structure that normally results from traditional procedural methods."
   <https://web.archive.org/web/20151204043748/http://www.objectmentor.com/resources/articles/dip.pdf>

3. **The three diseases** — same, p. 2:
   > "It is hard to change because every change affects too many other parts of the system.
   > (Rigidity) … When you make a change, unexpected parts of the system break. (Fragility) … It is
   > hard to reuse in another application because it cannot be disentangled from the current
   > application. (Immobility)"
   <https://web.archive.org/web/20151204043748/http://www.objectmentor.com/resources/articles/dip.pdf>

4. **The unconditional scope — the line North attacks** — same, p. 8:
   > "Dependency Inversion can be applied wherever one class sends a message to another."
   <https://web.archive.org/web/20151204043748/http://www.objectmentor.com/resources/articles/dip.pdf>

5. **The abstraction is what does not vary** — same, p. 10:
   > "What is the high level policy? It is the abstractions that underlie the application, the truths
   > that do not vary when the details are changed."
   <https://web.archive.org/web/20151204043748/http://www.objectmentor.com/resources/articles/dip.pdf>

6. **Fowler names DI, and why** — <https://martinfowler.com/articles/injection.html>:
   > "Inversion of Control is too generic a term, and thus people find it confusing. As a result with
   > a lot of discussion with various IoC advocates we settled on the name Dependency Injection."

7. **The three forms** — same URL:
   > "There are three main styles of dependency injection. The names I'm using for them are
   > Constructor Injection, Setter Injection, and Interface Injection."

8. **Fowler's own brake on IoC** — same URL:
   > "Inversion of control is a common feature of frameworks, but it's something that comes at a
   > price. It tends to be hard to understand and leads to problems when you are trying to debug. So
   > on the whole I prefer to avoid it unless I need it."

9. **North on over-inversion** — <https://dannorth.net/blog/cupid-the-back-story/>:
   > "Most dependencies don't need inverting, because most dependencies aren't options, they are just
   > the way we are going to do it this time."

10. **North on interface bloat** — same URL:
    > "In the wild, there are entire shadow codebases where each class is backed by exactly one
    > interface, which only exists to satisfy a wiring framework or to inject a mock or stub for
    > automated testing theatre."

**Bonus, for the Java beat** — <https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/Clock.html>:
> "Best practice for applications is to pass a `Clock` into any method that requires the current
> instant and time-zone. A dependency injection framework is one way to achieve this"

**Bonus, for the repo comparison** — `skills/engineering/codebase-design/SKILL.md:65`,
<https://github.com/mattpocock/skills/blob/main/skills/engineering/codebase-design/SKILL.md>:
> "One adapter means a hypothetical seam. Two adapters means a real one."

---

## 9. Open gaps, triaged by cost to close

| Gap | Tier now | Cost to close |
| --- | --- | --- |
| Exact month of the *C++ Report* DIP column | `[unverified]` ("after March 1996" is Quoted) | High — needs a *C++ Report* index or masthead scan. Low value; the internal dating is already better evidence. |
| ~~Martin crediting Feathers with "SOLID"~~ | **CLOSED 2026-08-01** — Quoted | Was: *Clean Architecture* Part III intro. Settled in `14-martin-books-and-the-volatility-gate.md` §1 from the printed book, **p. 58**: Feathers supplied the **word**, not the principles. |
| *Clean Architecture* Ch. 11 stable/volatile qualification | `[unverified]` (chapter + section title **located**) | Medium — needs the book or a legitimate excerpt of pp. 87–90. |
| *Agile PPP* Ch. 11 body text | `[unverified]` (chapter **located**) | Medium — same. |
| *Effective Java* item number + exact wording | `[unverified]` | Low–medium — a TOC that itemises Chapter 2. |
| Martin's 2000 *Design Principles and Design Patterns* paper | not obtained | Low — `Principles_and_Patterns.pdf` was **not** captured by the Wayback Machine at that path (CDX checked; only truncated URL fragments and a `.jpg` exist). |
| Spring team's own position on field vs constructor injection | `[unverified]` | Low — fetch the Spring Framework reference documentation directly. |
