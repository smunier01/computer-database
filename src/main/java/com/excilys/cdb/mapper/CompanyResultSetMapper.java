package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.Company;

public class CompanyResultSetMapper implements ResultSetMapper<Company> {

    private volatile static CompanyResultSetMapper instance = null;

    private CompanyResultSetMapper() {
        super();
    }

    public static CompanyResultSetMapper getInstance() {
        if (CompanyResultSetMapper.instance == null) {
            synchronized (ComputerResultSetMapper.class) {
                if (CompanyResultSetMapper.instance == null) {
                    CompanyResultSetMapper.instance = new CompanyResultSetMapper();
                }
            }
        }

        return CompanyResultSetMapper.instance;
    }

    @Override
    public Company map(final ResultSet rs) throws SQLException {
        final Long id = rs.getLong("id");
        final String name = rs.getString("name");

        return new Company(id, name);
    }

}
