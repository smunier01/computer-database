package com.excilys.persistence.dao;

/**
 * Exception used when there is a problem in one of the DAO classes.
 *
 * @author simon
 */
public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * default constructor.
     */
    public DAOException() {
        super();
    }

    /**
     * constructor taking only a message.
     *
     * @param message
     *            message for the exception
     */
    public DAOException(final String message) {
        super(message);
    }

    /**
     * constructor taking a string message and un throwable.
     *
     * @param message
     *            message
     * @param cause
     *            throwable
     */
    public DAOException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor taking a throwable.
     *
     * @param cause
     *            throwable
     */
    public DAOException(final Throwable cause) {
        super(cause);
    }
}