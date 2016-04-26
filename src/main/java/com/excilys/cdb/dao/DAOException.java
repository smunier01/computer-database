package com.excilys.cdb.dao;

/**
 * Exception used when there is a problem in one of the DAO classes
 * 
 * @author simon
 */
public class DAOException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	public DAOException() {
		super();
	}

	/**
	 * constructor taking only a message
	 *
	 * @param message
	 */
	public DAOException(final String message) {
		super(message);
	}

	/**
	 * constructor taking a string message and un throwable
	 *
	 * @param message
	 * @param cause
	 */
	public DAOException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * constructor taking a throwable
	 *
	 * @param cause
	 */
	public DAOException(final Throwable cause) {
		super(cause);
	}
}