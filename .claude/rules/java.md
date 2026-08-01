---
paths:
  - "code/**"
---

# Java examples

- **Examples are real programs that compile and run.** They live in
  `code/NNNN-slug/` matching the lesson number, runnable with `java File.java`
  (Java 25, Corretto, via mise — single-file source mode, no build step).
- **Output quoted in a lesson is captured output**, produced by actually running
  the program in the session that wrote the lesson. Never hand-written to look
  like output.
- Prefer the JDK's own seams where they exist — `java.time.Clock` was added for
  exactly this reason and is stronger evidence than an invented interface.
- Bad-then-good pairs must differ in **one** thing. If the "good" version also
  renames variables, reformats, and adds a helper, the lesson has taught nothing
  about the concept.
- The domain is banking — `Account`, `Transfer`, `Balance`, `Ledger` — in every
  example, without exception.
