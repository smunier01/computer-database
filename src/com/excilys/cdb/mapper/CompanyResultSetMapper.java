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
        if (instance == null) {
            synchronized (ComputerResultSetMapper.class) {
                if (instance == null) {
                    instance = new CompanyResultSetMapper();
                }
            }
        }
        
        return instance;
    }
    
    @Override
    public Company map(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");

        return new Company(id, name);
    }

}
