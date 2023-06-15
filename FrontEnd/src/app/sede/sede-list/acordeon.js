const accordions = document.querySelectorAll(".accordion-header");

accordions.forEach(function(accordion) {
  accordion.addEventListener("click", function() {
    const content = this.nextElementSibling;
    if (content.style.display === "block") {
      content.style.display = "none";
      this.querySelector(".accordion-btn").textContent = "+";
    } else {
      content.style.display = "block";
      this.querySelector(".accordion-btn").textContent = "-";
    }
  });
});
