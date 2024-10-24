let choose = "All";
let encodedArray;
function toggleSelectSend(display) {
  document.getElementById("select-send").style.display = display;
  document.getElementById("Choose").value = choose;
}
toggleSelectSend("none");
document.getElementById("All").addEventListener("click", function () {
  choose = "All";
  toggleSelectSend("none");
});
document.getElementById("Emp").addEventListener("click", function () {
  choose = "AllEmployee";
  toggleSelectSend("none");
});
document.getElementById("Resi").addEventListener("click", function () {
  choose = "AllResident";
  toggleSelectSend("none");
});
document.getElementById("pick").addEventListener("click", function () {
  choose = "Choosen";
  toggleSelectSend("flex");
});

document.addEventListener("DOMContentLoaded", function () {
  var selectElement = document.getElementById("ID-chosenValue");

  selectElement.addEventListener("change", function () {
    const selectElement = document.querySelector(".chosen-select")
      .selectedOptions;
    const selectedValues = [];
    for (let i = 0; i < selectElement.length; i++) {
      selectedValues.push(selectElement[i].value);
    }

    if (choose != "Choosen") {
      encodedArray = "null";
    } else {
      encodedArray = selectedValues.map(encodeURIComponent).join(",");
    }

    document.getElementById("valueSend").value = encodedArray;
  });
});

// =========================================JS validate event====================================================

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
