package com.excilys.cdb.service;

/**
 * Exception used when there is a problem in one of the Service classes.
 *
 * @author simon
 */
public class ServiceException extends Exception {

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
     * @param message
     *            message
     */
    public ServiceException(final String message) {
        super(message);
    }

    /**
     * constructor taking a string message and a throwable.
     *
     * @param message
     *            message
     * @param cause
     *            throwable
     */
    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor taking a throwable.
     *
     * @param cause
     *            throwable
     */
    public ServiceException(final Throwable cause) {
        super(cause);
    }
}