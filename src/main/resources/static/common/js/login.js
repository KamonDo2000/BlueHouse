$(function() {
	$("#show-pass").css("display", "none");

	$("#hide-pass").click(function() {
		$("#show-pass").css("display", "unset");
		$("#hide-pass").css("display", "none");
		$("#password").attr("type", "text");
	});

	$("#show-pass").click(function() {
		$("#show-pass").css("display", "none");
		$("#hide-pass").css("display", "unset");
		$("#password").attr("type", "password");
	});
});