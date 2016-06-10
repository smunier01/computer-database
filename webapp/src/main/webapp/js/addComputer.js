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

    var validateFieldName = function f() {
        var elem = $('#name');
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

        return f;
    }();

    var validateFieldIntroduced = function f() {
        var elem = $('#introduced');
        var parent = elem.parent().first();

        if (!validateDate(elem.val())) {
            parent.addClass('has-error');
            parent.removeClass('has-success');
            name = false;
        } else {
            parent.addClass('has-success');
            parent.removeClass('has-error');
            name = true;
        }

        validateForm();

        return f;
    }();

    var validateFieldDiscontinued = function f() {
        var elem = $('#discontinued');
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

        return f;
    }

    $('#name').on('input', validateFieldName);

    $('#introduced').on('input', validateFieldIntroduced);

    $('#discontinued').on('input', validateFieldDiscontinued());
});
