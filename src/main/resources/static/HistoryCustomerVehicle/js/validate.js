
const REGEX_VEHI = /^[0-9]{2}[A-Z][0-9]{1}-[0-9]{5}$/;


document.getElementById('moveInDate').addEventListener('change', validateMoveInDate);

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
function validate(event) {
	event.preventDefault();
	let check = true;

	let vehinum = $("#vehicleNumber").val();
	let moveindate = $("#moveInDate").val();
	
	if (!vehinum) {
        $("#vehi-error").text("Vui lòng nhập id");
        check = false;
    } else if (!REGEX_VEHI.test(vehinum)) {
        $("#vehi-error").text("Nhập sai định dạng");
        check = false;
	} else {
		$("#vehi-error").text("");
	}

	validateMoveInDate();

    if (!moveindate) {
        $("#moveInDate-error").text("Bạn hãy nhập");
        check = false;
    }

	if (check) {
		document.getElementById("create").dispatchEvent(new MouseEvent("click"));
	}
}