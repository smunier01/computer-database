package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

public enum ComputerMapper {

    INSTANCE;

    private final Validator validator = Validator.getInstance();

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
     * Creates a ComputerDTO object from a Computer.
     *
     * @param computer
     *            computer
     * @return ComputerDTo object
     */
    public ComputerDTO toDTO(final Computer computer) {
        return new ComputerDTO(computer);
    }

    /**
     * Creates a Computer object from a jdbc ResultSet.
     *
     * @param rs
     *            ResultSet containing the informations about the computer
     * @return instance of the computer created
     * @throws SQLException
     *             exception
     * @throws MapperException
     */
    public Computer map(final ResultSet rs) throws SQLException, MapperException {

        final Long id = rs.getLong("id");
        final String name = rs.getString("name");
        final LocalDate introduced = TimestampToLocalDate.convert(rs.getTimestamp("introduced"));
        final LocalDate discontinued = TimestampToLocalDate.convert(rs.getTimestamp("discontinued"));

        Long companyId = rs.getLong("company_id");

        Company company = null;

        if (companyId > 0) {

            String companyName = rs.getString("company_name");

            if ((companyName == null) || "".equals(companyName)) {
                // there should never be a null or empty name when the id is not
                // null.
                throw new MapperException();
            } else {
                company = new Company(companyId, companyName);
            }

        }

        return new Computer.ComputerBuilder().id(id).name(name).introduced(introduced).discontinued(discontinued)
                .company(company).build();
    }

    /**
     * Creates a Computer object from a servlet HttpServletRequest.
     *
     * @param request
     *            HttpServletRequest containing the informations about the
     *            computer
     * @return instance of the computer created
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

        final ComputerBuilder builder = new Computer.ComputerBuilder();

        // computer id (optional, when we want to create a computer)

        final String idStr = request.getParameter("id");
        final long id;

        if (idStr == null) {
            id = 0L;
        } else if (validator.validateInt(idStr)) {
            id = Long.parseLong(idStr);
        } else {
            throw new MapperException();
        }

        // introduced date (optional)

        final String introducedStr = request.getParameter("introduced");

        LocalDate introduced;

        if ((introducedStr == null) || "".equals(introducedStr)) {
            introduced = null;
        } else if (validator.validateDate(introducedStr)) {
            introduced = LocalDate.parse(introducedStr);
        } else {
            throw new MapperException();
        }

        // discontinued date (optional)

        final String discontinuedStr = request.getParameter("discontinued");

        LocalDate discontinued = null;

        if ((discontinuedStr == null) || "".equals(discontinuedStr)) {
            discontinued = null;
        } else if (validator.validateDate(discontinuedStr)) {
            discontinued = LocalDate.parse(discontinuedStr);
        } else {
            throw new MapperException();
        }

        // company (optional)

        final String companyIdStr = request.getParameter("companyId");

        Company company;

        if ((companyIdStr == null) || "".equals(companyIdStr)) {
            company = null;
        } else if (validator.validateInt(companyIdStr)) {
            company = new Company();
            company.setId(Long.parseLong(companyIdStr));
        } else {
            throw new MapperException();
        }

        return builder.id(id).name(nameStr).introduced(introduced).discontinued(discontinued).company(company).build();

    }

}
