package com.excilys.cdb.jdbc;

public class TransactionManagerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * default constructor.
     */
    public TransactionManagerException() {
        super();
    }

    /**
     * constructor taking only a message.
     *
     * @param message
     *            message for the exception
     */
    public TransactionManagerException(final String message) {
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
    public TransactionManagerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor taking a throwable.
     *
     * @param cause
     *            throwable
     */
    public TransactionManagerException(final Throwable cause) {
        super(cause);
    }
}
