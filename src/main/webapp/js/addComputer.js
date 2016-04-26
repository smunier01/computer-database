$(function() {
    
    var validateString = function(s) {
	return s != "";
    };

    var validateDate = function(s) {
	return (Date.parse(s) > 0);
    };

    var validateLong = function(s) {
	return !isNaN(s);
    };

    $('#computerName').on('input', function() {
	if (!validateString($(this).val())) {
	    $(this).parent().first().addClass('has-error');
	    $(this).parent().first().removeClass('has-success');
	} else {
	    console.log("ok");
	    $(this).parent().first().addClass('has-success');
	    $(this).parent().first().removeClass('has-error');
	}
    });

    $('#introduced').on('input', function() {
	if (!validateDate($(this).val())) {
	    $(this).parent().first().addClass('has-error');
	    $(this).parent().first().removeClass('has-success');
	} else {
	    console.log("ok");
	    $(this).parent().first().addClass('has-success');
	    $(this).parent().first().removeClass('has-error');
	}
    });

    $('#discontinued').on('input', function() {
	if (!validateDate($(this).val())) {
	    $(this).parent().first().addClass('has-error');
	    $(this).parent().first().removeClass('has-success');
	} else {
	    console.log("ok");
	    $(this).parent().first().addClass('has-success');
	    $(this).parent().first().removeClass('has-error');
	}
    });

    $('#companyId').on('input', function() {
	if (!validateLong($(this).val())) {
	    $(this).parent().first().addClass('has-error');
	} else {
	    $(this).parent().first().removeClass('has-error');
	}
    });
    
});
