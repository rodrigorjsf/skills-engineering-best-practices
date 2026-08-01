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
    mountThemeToggle();
    mountProgressBar();
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", init);
  } else {
    init();
  }
})();
