let choose = "All";
let encodedArray;
function toggleSelectSend(display) {
  document.getElementById("select-send").style.display = display;
}
toggleSelectSend("none");
document.getElementById("All").addEventListener("click", function () {
  toggleSelectSend("none");
  choose = "All";
});
document.getElementById("Emp").addEventListener("click", function () {
  toggleSelectSend("none");
  choose = "AllEmployee";
});
document.getElementById("Resi").addEventListener("click", function () {
  toggleSelectSend("none");
  choose = "AllResident";
});
document.getElementById("pick").addEventListener("click", function () {
  toggleSelectSend("flex");
   choose = "Choosen";
});

function sending() {
  const selectElement = document.querySelector(".chosen-select")
    .selectedOptions;
  const selectedValues = [];
  for (let i = 0; i < selectElement.length; i++) {
    selectedValues.push(selectElement[i].value);
  }

  if (choose != "Choosen") {
   	encodedArray = "null";
  }else{
	encodedArray = selectedValues.map(encodeURIComponent).join(",");
  }

  window.location.href =
    "saveReceiver?ListValue=" + encodedArray + "&choose=" + choose;
}
