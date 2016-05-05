package com.excilys.cdb.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ITransactionManager {
    void init();

    void commit() throws SQLException;

    void rollback();

    void close();

    Connection get();
}
