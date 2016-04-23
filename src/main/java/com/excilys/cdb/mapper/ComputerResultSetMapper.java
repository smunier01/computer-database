package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerResultSetMapper implements ResultSetMapper<Computer> {

    private volatile static ComputerResultSetMapper instance = null;

    private ComputerResultSetMapper() {
        super();
    }

    public static ComputerResultSetMapper getInstance() {
        if (instance == null) {
            synchronized (ComputerResultSetMapper.class) {
                if (instance == null) {
                    instance = new ComputerResultSetMapper();
                }
            }
        }

        return instance;
    }

    @Override
    public Computer map(ResultSet rs) throws SQLException {

        Long id = rs.getLong("id");
        String name = rs.getString("name");
        LocalDate introduced = TimestampToLocalDate.convert(rs.getTimestamp("introduced"));
        LocalDate discontinued = TimestampToLocalDate.convert(rs.getTimestamp("discontinued"));
        Long companyId = rs.getLong("company_id") <=0 ? null : rs.getLong("company_id") ;
        String companyName = rs.getString("company_name");

        Company company = new Company(companyId, companyName);
        
        Computer computer = new Computer.ComputerBuilder()
                .id(id)
                .name(name)
                .introduced(introduced)
                .discontinued(discontinued)
                .company(company)
                .build();

        return computer;
    }

}
