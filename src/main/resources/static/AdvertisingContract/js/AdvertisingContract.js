$(function() {
	if (window.location.pathname === '/advertisingContract/add') {
		
		$('#title-Text').text('Add New Advertising Contract');

		$('#form-data').attr('action', 'save');

		$('#update').hide();
		$('#create').show();

	} else {
		
		$('#title-Text').text('Update Advertising Contract');

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
