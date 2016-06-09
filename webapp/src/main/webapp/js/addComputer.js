$(function () {

    var name = false;
    var introduced = true;
    var discontinued = true;

    var validateString = function (s) {
        return s != "";
    };

    var validateDate = function (s) {
        return !isNaN(Date.parse(s)) || s == "";
    };

    var validateLong = function (s) {
        return !isNaN(s);
    };

    var validateForm = function f() {

        var button = $('#buttonForm');

        if (name && introduced && discontinued) {
            button.removeClass('disabled');
            button.removeAttr('disabled');
        } else {
            button.addClass('disabled');
            button.attr('disabled', 'disabled');
        }

        return f;
    }();

    $('#name').on('input', function () {

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

    $('#introduced').on('input', function () {

        var elem = $(this);
        var parent = elem.parent().first();

        if (!validateDate(elem.val())) {
            parent.addClass('has-error');
            parent.removeClass('has-success');
            introduced = false;
        } else {
            parent.addClass('has-success');
            parent.removeClass('has-error');
            introduced = true;
        }

        validateForm();
    });

    $('#discontinued').on('input', function () {

        var elem = $(this);
        var parent = elem.parent().first();

        if (!validateDate(elem.val())) {
            parent.addClass('has-error');
            parent.removeClass('has-success');
            discontinued = false;
        } else {
            parent.addClass('has-success');
            parent.removeClass('has-error');
            discontinued = true;
        }

        validateForm();
    });
});
