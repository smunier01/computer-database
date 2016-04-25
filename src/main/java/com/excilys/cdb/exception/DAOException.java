package com.excilys.cdb.exception;

public class DAOException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DAOException() {
        super();
    }

    public DAOException(final String message) {
        super(message);
    }

    public DAOException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DAOException(final Throwable cause) {
        super(cause);
    }
}
