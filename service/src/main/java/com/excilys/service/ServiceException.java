package com.excilys.service;

/**
 * Exception used when there is a problem in one of the Service classes.
 *
 * @author simon
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * default constructor.
     */
    public ServiceException() {
        super();
    }

    /**
     * constructor taking only a message.
     *
     * @param message message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * constructor taking a string message and a throwable.
     *
     * @param message message
     * @param cause   throwable
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor taking a throwable.
     *
     * @param cause throwable
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}