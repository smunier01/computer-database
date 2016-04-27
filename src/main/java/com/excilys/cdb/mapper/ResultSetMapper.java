package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper<T> {
    /**
     * convert result set to object.
     *
     * @param rs
     *            the result set with the informations
     * @return object
     * @throws SQLException
     *             exception
     */
    T map(ResultSet rs) throws SQLException;

}
