package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public enum CompanyMapper {

    INSTANCE;

    /**
     * default constructor for the singleton.
     */
    CompanyMapper() {

    }

    /**
     * public accessor for the singleton.
     *
     * @return return unique instance of the singleton
     */
    public static CompanyMapper getInstance() {
        return INSTANCE;
    }

    /**
     * creates company object from a jdbc ResultSet.
     *
     * @param rs
     *            ResultSet containing the information about the company.
     * @return instance of the company created.
     * @throws SQLException
     *             throws exception if the object could not be mapped
     */
    public Company map(final ResultSet rs) throws SQLException {
        final Long id = rs.getLong("id");
        final String name = rs.getString("name");

        return new Company(id, name);
    }

    /**
     * creates company object from a HttpServletRequest.
     *
     * @param request
     *            HttpServletRequest containing the information about the
     *            company.
     * @return instance of the company created.
     * @throws UnsupportedOperationException
     *             not yet implemented
     */
    public Company map(final HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }

    /**
     * convert from Company to CompanyDTO.
     *
     * @param company
     *            company
     * @return CompanyDTO object
     */
    public CompanyDTO toDTO(final Company company) {
        return new CompanyDTO(company);
    }

}
