$(document).ready(function()
{
  $("tr:odd").css({
    "background-color":"#000",
    "color":"#fff"});
});

const open = document.getElementById('open');
const modal_container = document.getElementById('modal_container')
const close = document.getElementById('close')

open.addEventListener('click',()=>{
  modal_container.classList.add('show');
});

close.addEventListener('click',()=>{
  modal_container.classList.remove('show');
});

function from_inputs() {
  /**
   * this is very functional and decently advanced for the students, they will most likely
   * have rudimentary lines where they manually create variables for each HTMLInputElement
   * on the page, then a variable for their values, etc.
   */
  const refs = ["name", "id", "gpa"].map((id) => {
    return document.getElementById(id);
  });

  /**
   * the function shouldn't add records if one of the values are `""`
   */
  if (
    !refs.every((el) => {
      return el.value !== "";
    })
  ) {
    return;
  }

  // this just destructures the array into the values
  add_record(
    ...refs.map((el) => {
      return el.value;
    })
  );

  /**
   * this is the bonus section of the code
   */
  refs.forEach((el) => {
    el.value = "";
  });
}

/**
 * this function takes a name, id and GPA and transforms it into a `<tr>` which
 * gets appended to `table#chart > tbody`
 * @param {String} name the name of the student
 * @param {Number} id the student's id
 * @param {Number} gpa the student's GPA
 */
function add_record(name, id, gpa) {
  /**
   * the smarter more inclined students might abstract the logic of `from_inputs()` into its own function
   * just like in this file, other than that
   * the students will have to modify their code to accept `Student` objects
   */

  /**
   * the `wrap()` function is optional and most students aren't going to have it
   */
  const data = [name, id, gpa].map((el) => {
    return wrap("td", el);
  });
  const row = wrap("tr", data.join(""));

  /**
   * the students need to append the new row to the tbody element,
   * not the table element
   */
  document.getElementById("chart").getElementsByTagName("tbody")[0].innerHTML +=
    row;
}

/**
 * an optional function which aids in HTML tag wrapping
 * @param {String} tag the HTML tag
 * @param {String} data something to wrap
 * @returns {String} `<tag>data</tag>`
 */
function wrap(tag, data) {
  return `<${tag}>${data}</${tag}>`;
}

const URL = "http://localhost:8080/lab3-1.0/api/students/json";

function post_to_server(endpoint, contentType) {

  // setting the url
  const url = "http://localhost:8080/lab3-1.0/api/format/" + endpoint;

  // getting the payload
  const payload = document.getElementById("chart").innerHTML;

  /// creating a response to the server
  const request = new XMLHttpRequest();
  request.open("POST", url);                              // setting the method
  request.setRequestHeader("Content-Type", "text/html");  // setting the sending content-type
  request.setRequestHeader("Accept", contentType);        // setting the receiving content-type

  // on response handler
  request.onload = () => {
    if (request.status !== 200) {
      console.error("Something went wrong went contacting the server");
      return
    }
    console.log("Received from the server: ", request.responseText) // this contains the received payload

    /**
     * this is how to programmatically download something in javascript.
     * 1. create an invisible anchor tag
     * 2. set the href attribute (contains file data)
     * 3. set the download attribute (contains the file name)
     * 4. click it
     */
    var element = document.createElement('a');
    element.setAttribute('href', `data:${contentType};charset=utf-8,` + encodeURIComponent(request.responseText));
    element.setAttribute('download', `students.${endpoint}`);
    element.click();
  }

  // sending the payload to the server
  request.send(payload);
}

/**
 * anonymous function that executes on script load
 *
 * this is what the students are going to be tested on
 */
(function () {
  fetch(URL)
    .then((res) => res.json()) // `.json()` returns a promise, not data
    .then((data) => {

      console.log(`Loaded data from ${URL}: `, data);

      // using `in` will not work
      for (const student of data["students"]) {
        // just a wrapper around `add_record()`
        from_student(student);
      }
    })
    .catch((err) => {
      console.log("something went wrong: " + err);
    });
})();
