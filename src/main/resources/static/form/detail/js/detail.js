$(function() {
    $('#reason-error').hide();
    $('#select-error').hide();
    $('#id-error').hide();
    $('#opinion-error').hide();
    $('#rate-error').hide();
    $('#location-error').hide();
    $('#submitDeny').click(function(event) {
        if(checkFormDeny()){
            $('#denyForm').submit();
        }
    });

})
function checkFormDeny (){
    if( $("#reason-deny").val() === "") {
        $('#reason-error').show();
        return false;
    }else{
        $('#reason-error').hide();
        return true;
    }
}
function checkSelect(){
    if( $("#optionSelect").val() === ""){
       
        $('#select-error').show();
    }else{
        $('#select-error').hide();
        $('#selectedEmployeeForm').submit();
    }
}
function checkOpinion(){
    if( $("#opinion").val() === ""){
       
        $('#opinion-error').show();
    }else{
        $('#opinion-error').hide();
        $('#opinionForm').submit();
    }
}
function checkIdAsset(){
    if( $("#id").val() === "" &&  $("#location").val() === ""){
        $('#id-error').show();
        $('#location-error').show();
    }
    if( $("#id").val() === ""){     
        $('#id-error').show();
        return;
    }if( $("#location").val() === ""){      
        $('#location-error').show();
        return;
    } else{
        $('#id-error').hide();
        $('#location-error').hide();
        $('#idAssetForm').submit();
    }
}
function validateFile() {
    const fileInput = document.getElementById('file');
    const filePath = fileInput.value;
    const errorMessage = document.getElementById('error-message');

    // Check if file input is empty
    if (!filePath) {
        errorMessage.textContent = "Please select a file.";
        return false;
    }

    // Allowed file types (image extensions)
    const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;

    // Check if file is an image
    if (!allowedExtensions.exec(filePath)) {
        errorMessage.textContent = "Please upload a valid image file (JPG, JPEG, PNG, GIF).";
        fileInput.value = '';  // Clear the file input field
        return false;
    }

    // Clear error message if validation passes
    errorMessage.textContent = "";
    $('#completedForm').submit();
    return true;
}
function checkRate(){
    if (!$("input[name='rating']:checked").val()) {
        $('#rate-error').show();
        $('#rate-error').text("Please select a rating!");
    } else {
        $('#rate-error').hide();
        $('#rateForm').submit();
    }
}
function showResident(){
    $('#infoResident').removeClass('d-none');
    $('#infoForm').addClass('d-none');
    $('#infoManager').addClass('d-none');
    $('#infoEmployee').addClass('d-none');
    $('#infoRepair').addClass('d-none');
}
function cancel(){
    console.log("oke oke");
    
    $('#infoForm').removeClass('d-none');
    $('#infoResident').addClass('d-none');
    $('#infoManager').addClass('d-none');
    $('#infoEmployee').addClass('d-none');
    $('#infoRepair').addClass('d-none');
}
function showManager(){
    $('#infoResident').addClass('d-none');
    $('#infoForm').addClass('d-none');
    $('#infoManager').removeClass('d-none');
    $('#infoEmployee').addClass('d-none');
    $('#infoRepair').addClass('d-none');
}
function showEmployee(){
    $('#infoResident').addClass('d-none');
    $('#infoForm').addClass('d-none');
    $('#infoManager').addClass('d-none');
    $('#infoEmployee').removeClass('d-none');
    $('#infoRepair').addClass('d-none');
}
function showRepair(){
    $('#infoResident').addClass('d-none');
    $('#infoForm').addClass('d-none');
    $('#infoManager').addClass('d-none');
    $('#infoEmployee').addClass('d-none');
    $('#infoRepair').removeClass('d-none');
    
}
