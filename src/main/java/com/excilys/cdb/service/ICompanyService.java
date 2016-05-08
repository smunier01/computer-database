package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.PageParameters;

/**
 * CompanyService interface.
 *
 * @author simon
 */
public interface ICompanyService {

    /**
     * get company by its id.
     *
     * @param id
     *            of the company
     * @return null if invalid id or computer doesn't exist
     */
    Company getCompany(Long id);

    /**
     * get list of company.
     *
     * @param page
     *            page parameters
     * @return the list of company
     */
    List<Company> getCompanies(PageParameters page);

    /**
     * delete a company based on its ID.
     *
     * @param id
     *            id of the company to delete
     */
    void deleteCompany(Long id);

    /**
     * get the entire company list with no page parameters.
     *
     * @return list of company
     */
    List<Company> getCompanies();

    /**
     * get the number of companies.
     *
     * @return number of companies
     */
    long countCompanies();
}
