const button1 = document.querySelector("#button1");
const button2 = document.querySelector("#button2");
const description1 = document.querySelector("#description1");
const description2 = document.querySelector("#description2");

button1.addEventListener("click", function() {
  description1.classList.add("active");
  description2.classList.remove("active");
});

button2.addEventListener("click", function() {
  description2.classList.add("active");
  description1.classList.remove("active");
});
