package com.excilys.cdb.mapper;

/**
 * Exception used when an object could not be mapped.
 *
 * @author simon
 */
public class MapperException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * default constructor.
     */
    public MapperException() {
        super();
    }

    /**
     * constructor taking only a message.
     *
     * @param message
     *            message for the exception
     */
    public MapperException(final String message) {
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
    public MapperException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor taking a throwable.
     *
     * @param cause
     *            throwable
     */
    public MapperException(final Throwable cause) {
        super(cause);
    }
}