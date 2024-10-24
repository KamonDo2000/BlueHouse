// =========================================JS validate event====================================================
console.log("ok");

document
  .getElementById("form-data")
  .addEventListener("submit", function (event) {
    event.preventDefault();
    validateForm();
  });

function validateForm() {
  var name = document.getElementById("name").value;
  var location = document.getElementById("location").value;

  var dayStart = document.getElementById("startDate").value;
  var timeStart = document.getElementById("startTime").value;

  var dayEnd = document.getElementById("endDate").value;
  var timeEnd = document.getElementById("endTime").value;

  var currentDate = new Date().toISOString().split("T")[0];
  var isValid = true;

  if (!dayStart) {
    document.getElementById("error-startDate").textContent =
      "Day Start is required.";
    isValid = false;
  } else if (dayStart <= currentDate) {
    document.getElementById("error-startDate").textContent =
      "Day Start must be after today.";
    isValid = false;
  } else {
    document.getElementById("error-startDate").textContent = "";
  }

  if (!timeStart) {
    document.getElementById("error-startTime").textContent =
      "Time Start is required";
    isValid = false;
  } else {
    document.getElementById("error-startTime").textContent = "";
  }

  if (!dayEnd) {
    document.getElementById("error-endDate").textContent =
      "Day End is required.";
    isValid = false;
  } else if (dayStart > dayEnd) {
    document.getElementById("error-endDate").textContent =
      "Day Start must be before Day End.";
    isValid = false;
  } else {
    document.getElementById("error-endDate").textContent = "";
  }

  if (!timeEnd) {
    document.getElementById("error-endTime").textContent =
      "Time End is required.";
    isValid = false;
  } else if (dayStart === dayEnd && timeStart >= timeEnd) {
    document.getElementById("error-endTime").textContent =
      "If the days are the same, Time Start must be before Time End.";
    isValid = false;
  } else {
    document.getElementById("error-endTime").textContent = "";
  }

  if (name === "") {
    document.getElementById("error-name").textContent =
      "Event Title cannot be empty.";
    isValid = false;
  } else {
    document.getElementById("error-name").textContent = "";
  }

  if (location === "") {
    document.getElementById("error-location").textContent =
      "Event venues cannot be empty.";
    isValid = false;
  } else {
    document.getElementById("error-location").textContent = "";
  }

  if (isValid) {
    document.getElementById("form-data").submit();
  }
}
