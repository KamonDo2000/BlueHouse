$(function() {
	if (window.location.pathname === '/financialSupportfee/add') {

		$('#title-Text').text('Add New financial Support fee');

		$('#form-data').attr('action', 'save');

		$('#update').hide();
		$('#create').show();

	} else {

		$('#title-Text').text('Update financial Support fee');

		$('#form-data').attr('action', 'update');
		$('#id-residence').attr('readonly', 'readonly');

		$('#create').hide();
		$('#update').show();
		$('#cancel').hide();
	}

	$('#back-list').click(function() {
		window.location.href = 'list';
	});
});



const REGEX_ID = /^TT\d{3}$/;
const REGEX_NAME = /^[a-zA-Z\s]{5,50}$/;
const REGEX_SO = /^[0-9]+$/;

document.getElementById('datefee').addEventListener('change', validateDatefee);

function validateDatefee() {
	const dateInput = document.getElementById('datefee');
	const datefee = new Date(dateInput.value);
	const currentDate = new Date();
	const errorMessage = document.getElementById('datefee-error');

	if (datefee > currentDate) {
		errorMessage.textContent = 'date phải nhỏ hơn hoặc bằng ngày hiện tại.';
		dateInput.value = ''; // Xóa giá trị Date nếu không hợp lệ
	} else {
		errorMessage.textContent = '';
	}
}

function validate(event) {
	event.preventDefault();
	let check = true;

	let idbill = $("#id-billCode").val();
	let namefee = $("#nameFeeType").val();
	let sponsorName = $("#sponsorName").val();
	let datefee = $("#datefee").val();
	let price = $("#price").val();

	if (!idbill) {
		$("#billCode-error").text("Vui lòng nhập id");
		check = false;
	} else if (!REGEX_ID.test(idbill)) {
		$("#billCode-error").text("Nhập sai định dạng");
		check = false;
	} else {
		$("#billCode-error").text("");
	}

	if (!REGEX_NAME.test(namefee)) {
		$("#namefee-error").text("wrong format only text");
		check = false;
	} else {
		$("#namefee-error").text("");
	}

	if (!REGEX_NAME.test(sponsorName)) {
		$("#namespon-error").text("wrong format only text");
		check = false;
	} else {
		$("#namespon-error").text("");
	}
	
	if (!REGEX_SO.test(price)) {
		$("#price-error").text("wrong format so");
		check = false;
	} else {
		$("#price-error").text("");
	}

	validateDatefee();

	if (!datefee) {
		$("#datefee-error").text("Bạn hãy nhập");
		check = false;
	}

	if (check) {
		document.getElementById("create").dispatchEvent(new MouseEvent("click"));
	}
}
