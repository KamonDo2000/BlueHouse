$(document).ready(function() {

    const aptObject = [];

    $('.Apt-arr').each(function() {
        const idApartment = $(this).find('.idApartment').val();
        const idHomeowner = $(this).find('.idHomeowner').val();
        const newObject = {
            idApartment: idApartment,
            idHomeowner: idHomeowner
        }
        aptObject.push(newObject);
    });
    
    $('#apartmentId').change(function() { 
        var value = $('#apartmentId').val();

        aptObject.forEach(function(arr) {
            if (arr.idApartment == value) {
                // $('#homeownerId').val(arr.idHomeowner);
                $('#homeownerId').val(arr.idHomeowner);
            }
        });
    });

    
   
})