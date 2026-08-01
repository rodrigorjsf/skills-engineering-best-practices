/* ==========================================================================
   Shared lesson behaviour. Linked by every lesson and reference document.

   Two jobs:
     1. Quiz widgets — immediate, automatic feedback (the tightest loop we can
        get without a human in it).
     2. Theme toggle — remembers the reader's choice across lessons.

   Markup contract for a quiz:

     <div class="quiz" data-answer="1">
       <div class="quiz-head">Retrieval</div>
       <p class="quiz-q">Question text</p>
       <ul class="quiz-options">
         <li><button class="quiz-opt">Option zero</button></li>
         <li><button class="quiz-opt">Option one</button></li>
       </ul>
       <div class="quiz-feedback"><p>Why the right answer is right.</p></div>
     </div>

   data-answer is the zero-based index of the correct option.
   ========================================================================== */

(function () {
  "use strict";

  /* --- Quizzes ---------------------------------------------------------- */

  function wireQuiz(quiz) {
    var answer = parseInt(quiz.getAttribute("data-answer"), 10);
    var options = Array.prototype.slice.call(quiz.querySelectorAll(".quiz-opt"));
    var feedback = quiz.querySelector(".quiz-feedback");

    if (isNaN(answer) || !options.length) return;

    options.forEach(function (opt, index) {
      opt.setAttribute("type", "button");
      opt.addEventListener("click", function () {
        if (quiz.dataset.answered === "true") return;
        quiz.dataset.answered = "true";

        options.forEach(function (o, i) {
          o.disabled = true;
          if (i === answer) o.classList.add("correct");
          else if (i === index) o.classList.add("wrong");
        });

        if (feedback) feedback.classList.add("shown");

        // Record the attempt so a lesson can show a score if it wants to.
        quiz.dataset.correct = index === answer ? "true" : "false";
        quiz.dispatchEvent(
          new CustomEvent("quiz:answered", {
            bubbles: true,
            detail: { correct: index === answer, chosen: index, answer: answer }
          })
        );
      });
    });
  }

  /* --- Checkpoint tally -------------------------------------------------- */

  /* A checkpoint has to get its answers off the page and into a conversation.
   * localStorage is unreliable over file:// (and is per-origin anyway), so the
   * payload is rendered into a textarea that is visible from the first paint:
   * if every clipboard route fails, the reader can still select and copy.
   *
   * The signal is `chosen`, not `correct`. Which distractor was picked names
   * the misreading; a bare score does not.
   *
   * Inert unless a page carries [data-tally], so lessons are unaffected. */

  function mountTally() {
    var box = document.querySelector("[data-tally]");
    if (!box) return;

    var out = box.querySelector(".tally-out");
    var btn = box.querySelector(".tally-copy");
    var quizzes = Array.prototype.slice.call(
      document.querySelectorAll(".quiz[data-qid]")
    );
    if (!out || !quizzes.length) return;

    var LETTERS = "ABCDEFGH";
    var title = (document.title || "checkpoint").trim();

    function render() {
      var lines = [];
      var answered = 0;
      var right = 0;

      quizzes.forEach(function (quiz) {
        var id = quiz.getAttribute("data-qid");
        var cross = quiz.getAttribute("data-cross") || "";
        var answer = parseInt(quiz.getAttribute("data-answer"), 10);
        var chosen = quiz.dataset.chosen;

        if (chosen === undefined || chosen === "") {
          lines.push(id + "  " + cross + "  (sem resposta)");
          return;
        }

        answered++;
        var ok = quiz.dataset.correct === "true";
        if (ok) right++;

        lines.push(
          id + "  " + cross +
          "  escolhi " + LETTERS.charAt(parseInt(chosen, 10)) +
          " · correta " + LETTERS.charAt(answer) +
          "  " + (ok ? "ok" : "ERRO")
        );
      });

      out.value =
        title + "\n" +
        lines.join("\n") + "\n" +
        "respondidas " + answered + "/" + quizzes.length +
        " · acertos " + right;
    }

    document.addEventListener("quiz:answered", function (e) {
      var quiz = e.target;
      if (!quiz || !quiz.getAttribute || !quiz.getAttribute("data-qid")) return;
      quiz.dataset.chosen = String(e.detail.chosen);
      render();
    });

    if (btn) {
      var label = btn.textContent;
      btn.addEventListener("click", function () {
        out.focus();
        out.select();
        try { out.setSelectionRange(0, out.value.length); } catch (e) { /* ok */ }

        function done(copied) {
          btn.textContent = copied ? "copiado" : "selecionado — use Ctrl+C";
          setTimeout(function () { btn.textContent = label; }, 2600);
        }

        var copied = false;
        try { copied = document.execCommand("copy"); } catch (e) { copied = false; }
        if (copied) { done(true); return; }

        if (navigator.clipboard && navigator.clipboard.writeText) {
          navigator.clipboard.writeText(out.value).then(
            function () { done(true); },
            function () { done(false); }
          );
          return;
        }
        done(false);
      });
    }

    render();
  }

  /* --- Mermaid ---------------------------------------------------------- */

  /* Diagrams are authored as <pre class="mermaid">…</pre>.
   *
   * Published Artifacts render that natively. Locally, lessons open over
   * file://, where nothing renders it unless assets/mermaid.min.js has been
   * vendored alongside this file. A CDN is deliberately NOT an option: the
   * Artifact CSP blocks external hosts, so a CDN link would work locally and
   * fail silently once published.
   *
   * With no renderer available we leave the source visible and legible rather
   * than pretending a diagram is there — an empty box teaches nothing, and a
   * reader can still follow `A --> B`. */

  function mermaidBlocks() {
    return Array.prototype.slice.call(document.querySelectorAll("pre.mermaid"));
  }

  function renderMermaid() {
    var blocks = mermaidBlocks();
    if (!blocks.length) return;

    // Keep the original source so a theme change can re-render from it;
    // mermaid replaces the element's content with SVG on first run.
    blocks.forEach(function (b) {
      if (!b.dataset.src) b.dataset.src = b.textContent;
    });

    if (!window.mermaid) {
      blocks.forEach(function (b) { b.classList.add("mermaid-unrendered"); });
      return;
    }

    blocks.forEach(function (b) {
      b.classList.remove("mermaid-unrendered");
      b.removeAttribute("data-processed");
      b.textContent = b.dataset.src;
    });

    try {
      window.mermaid.initialize({
        startOnLoad: false,
        securityLevel: "strict",
        theme: currentTheme() === "dark" ? "dark" : "default",
        fontFamily: getComputedStyle(document.body)
          .getPropertyValue("--font-sans") || "sans-serif",
        // Mermaid's default 16px renders small once the SVG is scaled down to
        // the measure. Bumping the font grows the diagram's natural size, which
        // is what `useMaxWidth` then scales to — so labels stay legible.
        fontSize: 17,
        flowchart: {
          useMaxWidth: true,
          htmlLabels: false,
          padding: 12,
          nodeSpacing: 45,
          rankSpacing: 55
        }
      });
      window.mermaid.run({ nodes: blocks });
    } catch (e) {
      // A malformed diagram must not take the rest of the lesson down.
      blocks.forEach(function (b) { b.classList.add("mermaid-unrendered"); });
    }
  }

  /* --- Theme ------------------------------------------------------------ */

  var STORAGE_KEY = "teach-theme";

  function applyTheme(theme) {
    if (theme === "light" || theme === "dark") {
      document.documentElement.setAttribute("data-theme", theme);
    } else {
      document.documentElement.removeAttribute("data-theme");
    }
  }

  function currentTheme() {
    var stored = null;
    try { stored = localStorage.getItem(STORAGE_KEY); } catch (e) { /* file:// */ }
    if (stored) return stored;
    return window.matchMedia &&
      window.matchMedia("(prefers-color-scheme: dark)").matches
      ? "dark"
      : "light";
  }

  function mountThemeToggle() {
    if (document.querySelector(".theme-toggle")) return;

    var btn = document.createElement("button");
    btn.className = "theme-toggle";
    btn.type = "button";
    btn.setAttribute("aria-label", "Alternar tema claro/escuro");
    btn.textContent = currentTheme() === "dark" ? "☀" : "☾";

    Object.assign(btn.style, {
      position: "fixed",
      top: "1rem",
      right: "1rem",
      width: "2.2rem",
      height: "2.2rem",
      borderRadius: "50%",
      border: "1px solid var(--rule)",
      background: "var(--paper-sunk)",
      color: "var(--ink-soft)",
      cursor: "pointer",
      fontSize: "0.95rem",
      lineHeight: "1",
      zIndex: "50"
    });

    btn.addEventListener("click", function () {
      var next = currentTheme() === "dark" ? "light" : "dark";
      applyTheme(next);
      try { localStorage.setItem(STORAGE_KEY, next); } catch (e) { /* file:// */ }
      btn.textContent = next === "dark" ? "☀" : "☾";
      // Mermaid bakes theme colours into the generated SVG, so a CSS variable
      // swap alone would leave diagrams in the previous theme.
      renderMermaid();
    });

    document.body.appendChild(btn);
  }

  /* --- Progress through the lesson -------------------------------------- */

  function mountProgressBar() {
    var bar = document.createElement("div");
    Object.assign(bar.style, {
      position: "fixed",
      top: "0",
      left: "0",
      height: "2px",
      width: "0%",
      background: "var(--accent)",
      zIndex: "60",
      transition: "width 0.08s linear"
    });
    document.body.appendChild(bar);

    function update() {
      var max = document.documentElement.scrollHeight - window.innerHeight;
      var pct = max > 0 ? (window.scrollY / max) * 100 : 0;
      bar.style.width = Math.min(100, Math.max(0, pct)) + "%";
    }

    window.addEventListener("scroll", update, { passive: true });
    window.addEventListener("resize", update);
    update();
  }

  /* --- Boot ------------------------------------------------------------- */

  function init() {
    applyTheme(currentTheme());
    Array.prototype.forEach.call(document.querySelectorAll(".quiz"), wireQuiz);
    mountTally();
    mountThemeToggle();
    mountProgressBar();
    renderMermaid();
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", init);
  } else {
    init();
  }
})();
