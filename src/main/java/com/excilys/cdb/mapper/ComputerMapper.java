package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

public enum ComputerMapper {

    INSTANCE;

    /**
     * default constructor for the singleton.
     */
    ComputerMapper() {

    }

    /**
     * public accessor for the singleton.
     *
     * @return return unique instance of the singleton
     */
    public static ComputerMapper getInstance() {
        return INSTANCE;
    }

    /**
     *
     * @param computer
     * @return
     */
    public ComputerDTO toDTO(final Computer computer) {
        return new ComputerDTO(computer);
    }

    /**
     * Creates a Computer object from a jdbc ResultSet.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
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

    /**
     * Creates a Computer object from a servlet HttpServletRequest.
     *
     * @param request
     * @return
     * @throws MapperException
     *             throw exception if the object could not be mapped (wrong
     *             parameters etc..)
     */
    public Computer map(final HttpServletRequest request) throws MapperException {

        // computer name (required)

        final String nameStr = request.getParameter("computerName");

        if ((nameStr == null) || "".equals(nameStr)) {
            throw new MapperException();
        }

        ComputerBuilder builder = new Computer.ComputerBuilder();

        // computer id (optional, when we want to create a computer)

        final String idStr = request.getParameter("id");
        final long id;

        System.out.println(idStr);
        if (idStr == null) {
            id = 0L;
        } else {
            try {
                id = Long.parseLong(idStr);
            } catch (final NumberFormatException e) {
                // a non-valid id has been specified
                throw new MapperException(e);
            }
        }

        // introduced date (optional)

        final String introducedStr = request.getParameter("introduced");

        LocalDate introduced;

        if ((introducedStr == null) || "".equals(introducedStr)) {
            introduced = null;
        } else {
            try {
                introduced = LocalDate.parse(introducedStr);
            } catch (final DateTimeParseException e) {
                // a non-valid date has been specified
                throw new MapperException(e);
            }
        }

        // discontinued date (optional)

        final String discontinuedStr = request.getParameter("discontinued");

        LocalDate discontinued = null;

        if ((discontinuedStr == null) || "".equals(discontinuedStr)) {
            discontinued = null;
        } else {
            try {
                discontinued = LocalDate.parse(discontinuedStr);
            } catch (final DateTimeParseException e) {
                // a non-valid date has been specified
                throw new MapperException(e);
            }
        }

        // company (optional)

        final String companyIdStr = request.getParameter("companyId");

        Company company = new Company();

        if ((companyIdStr != null) && !"".equals(companyIdStr)) {
            try {
                long companyId = Long.parseLong(companyIdStr);

                if (companyId != 0) {
                    company = new Company();
                    company.setId(companyId);
                }

            } catch (final NumberFormatException e) {
                // a non-valid id has been specified
                throw new MapperException(e);
            }
        }

        return builder.id(id).name(nameStr).introduced(introduced).discontinued(discontinued).company(company).build();

    }

}
