package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

/**
 * implements different mapping methods to create or convert a Company object.
 *
 * @author simon
 */
@Component
public class CompanyMapper {

    /**
     * creates company object from a jdbc ResultSet.
     *
     * @param rs
     *            ResultSet containing the information about the company.
     * @return instance of the company created.
     * @throws SQLException
     *             throws exception if the object could not be mapped
     */
    public Company map(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");

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
    public Company map(HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }

    /**
     * convert from Company to CompanyDTO.
     *
     * @param company
     *            company
     * @return CompanyDTO object
     */
    public CompanyDTO toDTO(Company company) {
        return new CompanyDTO(company);
    }

    /**
     * convert a list of Company to a list of CompanyDTO.
     *
     * @param companies
     * @return
     */
    public List<CompanyDTO> map(List<Company> companies) {
        return companies.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
