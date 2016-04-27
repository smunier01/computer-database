package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public enum ComputerResultSetMapper implements ResultSetMapper<Computer> {

    INSTANCE;

    ComputerResultSetMapper() {

    }

    public static ComputerResultSetMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Computer map(final ResultSet rs) throws SQLException {

        final Long id = rs.getLong("id");
        final String name = rs.getString("name");
        final LocalDate introduced = TimestampToLocalDate.convert(rs.getTimestamp("introduced"));
        final LocalDate discontinued = TimestampToLocalDate.convert(rs.getTimestamp("discontinued"));
        final Long companyId = rs.getLong("company_id") <= 0 ? null : rs.getLong("company_id");
        final String companyName = rs.getString("company_name");

        final Company company = new Company(companyId, companyName);

        final Computer computer = new Computer.ComputerBuilder().id(id).name(name).introduced(introduced)
                .discontinued(discontinued).company(company).build();

        return computer;
    }

}
