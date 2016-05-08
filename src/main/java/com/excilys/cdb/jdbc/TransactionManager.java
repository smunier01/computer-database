package com.excilys.cdb.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public enum TransactionManager implements ITransactionManager {

    INSTANCE;

    ConnectionMySQLFactory factory = ConnectionMySQLFactory.getInstance();

    public static ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();

    public static ITransactionManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void init() {

        // throw exception if connection already exist on this thread
        if (threadConnection.get() != null) {
            throw new TransactionManagerException("init() has already been called.");
        }

        Connection con = this.factory.create();

        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw new TransactionManagerException("Could not set autocommit to false.");
        }

        threadConnection.set(con);
    }

    @Override
    public void commit() throws SQLException {
        Connection con = threadConnection.get();

        con.commit();
    }

    @Override
    public void rollback() {
        Connection con = threadConnection.get();

        try {
            con.rollback();
        } catch (SQLException e) {
            throw new TransactionManagerException("Could not perform rollback.");
        }
    }

    @Override
    public void close() {
        Connection con = threadConnection.get();

        try {
            con.close();
        } catch (SQLException e) {
            throw new TransactionManagerException("Could not close connection.");
        } finally {
            threadConnection.remove();
        }
    }

    @Override
    public Connection get() {

        Connection con = threadConnection.get();

        if (threadConnection.get() == null) {
            throw new TransactionManagerException("init() must be called first.");
        }

        return con;
    }

}
