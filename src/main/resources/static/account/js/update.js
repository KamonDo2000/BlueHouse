$(function () {
  $("#div-employee").hide();

  $("#role").on("change", function () {
    var selectedRole = $(this).val();
	
    if (selectedRole == 1 || selectedRole == 3) {
      $("#div-resident").show();
      $("#div-employee").hide();
    } else {
      $("#div-resident").hide();
      $("#div-employee").show();
    }
  });
});
