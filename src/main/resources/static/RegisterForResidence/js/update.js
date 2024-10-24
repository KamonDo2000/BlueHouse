$(function() {
	if (window.location.pathname === '/registerForResidence/add') {

		$('#title-Text').text('Add New RegiForResi');

		$('#form-data').attr('action', 'save');

		$('#update').hide();
		$('#create').show();

	} else {

		$('#title-Text').text('Update RegiForResi');

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

const REGEX_ID = /^RFS\d{3}$/;
const REGEX_NAME = /^[a-zA-Z\s]{5,50}$/;
const REGEX_CCCD = /^\d{12}$/;
const REGEX_TYPE = /^(Luu Tru Ngan Han|Luu Tru Dai Han)$/;
const REGEX_PHONE = /^09\d{8,9}$/;

document.getElementById('moveInDate').addEventListener('change', validateMoveInDate);
document.getElementById('moveOutDate').addEventListener('change', validateMoveOutDate);

	function validateMoveInDate() {
		const moveInDateInput = document.getElementById('moveInDate');
		const moveInDate = new Date(moveInDateInput.value);
		const currentDate = new Date();
		const errorMessage = document.getElementById('moveInDate-error');

		if (moveInDate > currentDate) {
			errorMessage.textContent = 'Move In Date phải nhỏ hơn hoặc bằng ngày hiện tại.';
			moveInDateInput.value = ''; // Xóa giá trị Move In Date nếu không hợp lệ
		} else {
			errorMessage.textContent = '';
		}
	}

	function validateMoveOutDate() {
		const moveOutDateInput = document.getElementById('moveOutDate');
		const moveOutDate = new Date(moveOutDateInput.value);
		const moveInDateInput = document.getElementById('moveInDate');
		const moveInDate = new Date(moveInDateInput.value);
		const errorMessage = document.getElementById('moveOutDate-error');

		if (moveOutDate <= moveInDate) {
			errorMessage.textContent = 'Move Out Date phải lớn hơn ngày hiện tại.';
			moveOutDateInput.value = ''; // Xóa giá trị Move Out Date nếu không hợp lệ
		} else {
			errorMessage.textContent = '';
		}
	}
function validate(event) {
	event.preventDefault();
	let check = true;

	let idresi = $("#id-residence").val();
	let rela = $("#relationshipWithHomeowner").val();
	let idNati = $("#idNational").val();
	let type = $("#type").val();
	let Phone = $("#phone").val();
	let birthofdate = $("#dateOfBirth").val();
	let moveindate = $("#moveInDate").val();
	let moveoutdate = $("#moveOutDate").val();
	
	if (!idresi) {
        $("#idResi-error").text("Vui lòng nhập id");
        check = false;
    } else if (!REGEX_ID.test(idresi)) {
        $("#idResi-error").text("Nhập sai định dạng");
        check = false;
	} else {
		$("#idResi-error").text("");
	}

	if (!REGEX_NAME.test(rela)) {
		$("#rela-error").text("wrong format only text");
		check = false;
	} else {
		$("#rela-error").text("");
	}

	if (!REGEX_CCCD.test(idNati)) {
		$("#idNati-error").text("wrong format 12 number");
		check = false;
	} else {
		$("#idNati-error").text("");
	}

	if (!REGEX_TYPE.test(type)) {
		$("#type-error").text("wrong format");
		check = false;
	} else {
		$("#type-error").text("");
	}

	if (!REGEX_PHONE.test(Phone)) {
		$("#phone-error").text("wrong format 09 and 10 or 11 number");
		check = false;
	} else {
		$("#phone-error").text("");
	}

	if (!birthofdate) {
        $("#dateBirth-error").text("Bạn hãy nhập");
        check = false;
    }
    
	validateMoveInDate();
    validateMoveOutDate();

    if (!moveindate) {
        $("#moveInDate-error").text("Bạn hãy nhập");
        check = false;
    }

    if (!moveoutdate) {
        $("#moveOutDate-error").text("Bạn hãy nhập");
        check = false;
    }
    
	if (check) {
		document.getElementById("create").dispatchEvent(new MouseEvent("click"));
	}
}
