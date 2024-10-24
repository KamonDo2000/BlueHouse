$(function () {
  $("#back-list").click(function () {
    window.location.href = "list";
  });

  $("#Office").change(function () {
    if ($(this).val() == "Services" && $(".mService").length == 0) {
      changeValueDuty();
    } else if ($(this).val() == "Engineering" && $(".mEngin").length == 0) {
      changeValueDuty();
    } else if ($(this).val() == "Environment" && $(".mEnviront").length == 0) {
      changeValueDuty();
    } else {
      $("#dutyMana").show();
      $("#dutyEmp").show();
    }
  });

  function changeValueDuty() {
    $("#dutyMana").prop("selected", true);
    $("#dutyMana").show();
    $("#dutyEmp").hide();
  }
});
