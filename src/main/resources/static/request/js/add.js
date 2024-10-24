function sending() {
   var id = document.getElementById("idForm").value;
   console.log(id);
    const selectElement = document.querySelector(".chosen-select")
      .selectedOptions;
    const selectedValues = [];
    for (let i = 0; i < selectElement.length; i++) {
      selectedValues.push(selectElement[i].value);
    }
    if(selectedValues.length == 0) {
        alert("Vui lòng chọn tài sản");
        return;
    }else{
        encodedArray = selectedValues.map(encodeURIComponent).join(",");
        window.location.href =
          "addListAssets?ListValue=" + encodedArray + "&idForm=" + id;
    }
  }
  