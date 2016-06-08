package com.excilys.binding.validation;

import org.springframework.validation.BindingResult;

/**
 * RuntimeException thrown by the validators. Contains the list of fields/errors.
 */
public class ValidatorException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private BindingResult errors;

    /**
     * default constructor.
     */
    public ValidatorException() {
        super();
    }

    /**
     * constructor taking only a message.
     *
     * @param message message for the exception
     */
    public ValidatorException(String message) {
        super(message);
    }

    /**
     * constructor taking a string message and un throwable.
     *
     * @param message message
     * @param cause   throwable
     */
    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor taking a throwable.
     *
     * @param cause throwable
     */
    public ValidatorException(Throwable cause) {
        super(cause);
    }

    /**
     * constructor taking a spring list of errors.
     *
     * @param errors BindingResult errors
     */
    public ValidatorException(BindingResult errors) {
        super();
        this.errors = errors;
    }

    /**
     * returns the errors that caused the exception.
     *
     * @return BindResult containing the errors codes & messages.
     */
    public BindingResult getErrors() {
        return this.errors;
    }
}
