$(function() {
	if (window.location.pathname === '/rentalSpaceContract/add') {

		$('#title-Text').text('Add New Rental Space Contract');

		$('#form-data').attr('action', 'save');

		$('#update').hide();
		$('#create').show();

	} else {

		$('#title-Text').text('Update Rental Space Contract');

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

const REGEX_ID = /^HD\d{3}$/;
const REGEX_NAME = /^[a-zA-Z\s]{5,50}$/;


document.getElementById('moveInDate').addEventListener('change', validateMoveInDate);
document.getElementById('moveOutdate').addEventListener('change', validateMoveOutDate);

	function validateMoveInDate() {
		const moveInDateInput = document.getElementById('moveInDate');
		const moveInDate = new Date(moveInDateInput.value);
		const currentDate = new Date();
		const errorMessage = document.getElementById('moveindate-error');

		if (moveInDate > currentDate) {
			errorMessage.textContent = 'Move In Date phải nhỏ hơn hoặc bằng ngày hiện tại.';
			moveInDateInput.value = ''; // Xóa giá trị Move In Date nếu không hợp lệ
		} else {
			errorMessage.textContent = '';
		}
	}

	function validateMoveOutDate() {
		const moveOutDateInput = document.getElementById('moveOutdate');
		const moveInDateInput = document.getElementById('moveInDate');
		const moveOutDate = new Date(moveOutDateInput.value);
		const moveInDate = new Date(moveInDateInput.value);
		const errorMessage = document.getElementById('moveoutdate-error');

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

	let idcon = $("#contractCode").val();
	let tennan = $("#tenantCode").val();
	let duration = $("#duration").val();
	let moveindate = $("#moveInDate").val();
	let moveoutdate = $("#moveOutdate").val();
	
	if (!idcon) {
        $("#idcon-error").text("Vui lòng nhập id");
        check = false;
    } else if (!REGEX_ID.test(idcon)) {
        $("#idcon-error").text("Nhập sai định dạng");
        check = false;
	} else {
		$("#idcon-error").text("");
	}

	if (!REGEX_NAME.test(tennan)) {
		$("#tenan-error").text("wrong format only text");
		check = false;
	} else {
		$("#tenan-error").text("");
	}

	if (!duration) {
        $("#dura-error").text("Bạn hãy nhập");
        check = false;
    }
    
	validateMoveInDate();
    validateMoveOutDate();

    if (!moveindate) {
        $("#moveindate-error").text("Bạn hãy nhập");
        check = false;
    }

    if (!moveoutdate) {
        $("#moveoutdate-error").text("Bạn hãy nhập");
        check = false;
    }
    
	if (check) {
		document.getElementById("create").dispatchEvent(new MouseEvent("click"));
	}
}
