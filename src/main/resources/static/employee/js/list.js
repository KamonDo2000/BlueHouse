function moveTabs(evt, name) {
	$(".tabcontent").hide();
	$(".tablinks").removeClass("active");
	$(".tablinks").removeClass("bg-dark");
	$("#" + name).show();
	$(evt.currentTarget).addClass("active");
	$(evt.currentTarget).addClass("bg-dark");
}

let empID;

function deleteEmp(employeeID) {
	empID = employeeID
}



$('#confirmDel').click(function() {
	window.location.href = 'delete?employeeID='+ empID;
});
