$(function() {
	if (window.location.pathname === '/vehicleRegistration/add') {

		$('#title-Text').text('Add New Vehicle Registration');

		$('#form-data').attr('action', 'save');

		$('#update').hide();
		$('#create').show();

	} else {

		$('#title-Text').text('Update Vehicle Registration');

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

const REGEX_ID = /^MX\d{3}$/;
const REGEX_VEHI = /^[0-9]{2}[A-Z][0-9]{1}-[0-9]{5}$/;


document.getElementById('registrationDate').addEventListener('change', validateRegistrationDate);
document.getElementById('expirationDate').addEventListener('change', validateExpirationDate);

	function validateRegistrationDate() {
		const regiDateInput = document.getElementById('registrationDate');
		const regiDate = new Date(regiDateInput.value);
		const currentDate = new Date();
		const errorMessage = document.getElementById('regidate-error');

		if (regiDate > currentDate) {
			errorMessage.textContent = 'regiDateInput phải nhỏ hơn hoặc bằng ngày hiện tại.';
			regiDateInput.value = ''; // Xóa giá trị Move In Date nếu không hợp lệ
		} else {
			errorMessage.textContent = '';
		}
	}

	function validateExpirationDate() {
		const expDateInput = document.getElementById('expirationDate');
		const expDate = new Date(expDateInput.value);
		const currentDate = new Date();
		const errorMessage = document.getElementById('expdate-error');

		if (expDate <= currentDate) {
			errorMessage.textContent = 'expDateInput phải lớn hơn ngày hiện tại.';
			expDateInput.value = ''; // Xóa giá trị expDateInput nếu không hợp lệ
		} else {
			errorMessage.textContent = '';
		}
	}
function validate(event) {
	event.preventDefault();
	let check = true;

	let idvehi = $("#idVehicle").val();
	let vehinumber = $("#vehicleNumber").val();
	let regiDate = $("#registrationDate").val();
	let expiDate = $("#expirationDate").val();
	
	if (!idvehi) {
        $("#idvehi-error").text("Vui lòng nhập id");
        check = false;
    } else if (!REGEX_ID.test(idvehi)) {
        $("#idvehi-error").text("Nhập sai định dạng");
        check = false;
	} else {
		$("#idvehi-error").text("");
	}

	if (!REGEX_VEHI.test(vehinumber)) {
		$("#vehi-error").text("wrong format only text");
		check = false;
	} else {
		$("#vehi-error").text("");
	}
    
	validateRegistrationDate();
    validateExpirationDate();

    if (!regiDate) {
        $("#regidate-error").text("Bạn hãy nhập");
        check = false;
    }

    if (!expiDate) {
        $("#expdate-error").text("Bạn hãy nhập");
        check = false;
    }
    
	if (check) {
		document.getElementById("create").dispatchEvent(new MouseEvent("click"));
	}
}
