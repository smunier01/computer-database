package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.Company;

public enum CompanyResultSetMapper implements ResultSetMapper<Company> {

    INSTANCE;

    /**
     * default constructor for the singleton.
     */
    CompanyResultSetMapper() {

    }

    /**
     * public accessor for the singleton.
     *
     * @return return unique instance of the singleton
     */
    public static CompanyResultSetMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Company map(final ResultSet rs) throws SQLException {
        final Long id = rs.getLong("id");
        final String name = rs.getString("name");

        return new Company(id, name);
    }

}
