$(function() {

	var name = false;
	var introduced = true;
	var discontinued = true;

	var validateString = function(s) {
		return s != "";
	};

	var validateDate = function(s) {
		return !isNaN(Date.parse(s)) || s == "";
	};

	var validateLong = function(s) {
		return !isNaN(s);
	};

	var validateForm = function f() {

		if (name && introduced && discontinued) {
			$('#buttonForm').removeClass('disabled');
			$('#buttonForm').removeAttr('disabled');
		} else {
			$('#buttonForm').addClass('disabled');
			$('#buttonForm').attr('disabled', 'disabled');
		}

		return f;
	}();

	$('#computerName').on('input', function() {

		var elem = $(this);
		var parent = elem.parent().first();

		if (!validateString(elem.val())) {
			parent.addClass('has-error');
			parent.removeClass('has-success');
			name = false;
		} else {
			parent.addClass('has-success');
			parent.removeClass('has-error');
			name = true;
		}

		validateForm();
	});

	$('#introduced').on('input', function() {
		if (!validateDate($(this).val())) {
			$(this).parent().first().addClass('has-error');
			$(this).parent().first().removeClass('has-success');
			introduced = false;
		} else {
			$(this).parent().first().addClass('has-success');
			$(this).parent().first().removeClass('has-error');
			introduced = true;
		}

		validateForm();
	});

	$('#discontinued').on('input', function() {
		if (!validateDate($(this).val())) {
			$(this).parent().first().addClass('has-error');
			$(this).parent().first().removeClass('has-success');
			discontinued = false;
		} else {
			$(this).parent().first().addClass('has-success');
			$(this).parent().first().removeClass('has-error');
			discontinued = true;
		}

		validateForm();
	});
});
