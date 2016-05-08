package com.excilys.cdb.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for a TransactionManager.
 *
 * @author simon
 */
public interface ITransactionManager {

    /**
     * Initialize the transaction.
     */
    void init();

    /**
     * commit the transaction.
     *
     * @throws SQLException
     *             SQLException when the commit fail. Good time to rollback.
     */
    void commit() throws SQLException;

    /**
     * rollback the transaction.
     */
    void rollback();

    /**
     * End the transaction.
     */
    void close();

    /**
     * retrieve a Connection object.
     *
     * This should fail if init() hasn't been called first.
     *
     * @return Connection
     */
    Connection get();
}
