package com.excilys.service.company;

import com.excilys.core.model.Company;
import com.excilys.core.model.PageParameters;

import java.util.List;


public interface ICompanyRestService {

    /**
     * Return the list of all the company.
     *
     * @return the list of the company
     */
    List<Company> getList();

    /**
     * Return the list of the company in function of the page parameters.
     *
     * @param params of the page
     * @return the list of company
     */
    List<Company> getList(PageParameters params);

    /**
     * Get the company in fucntion of his id.
     *
     * @param id of the company
     * @return the company
     */
    Company getCompanyById(long id);

    /**
     * Create a company.
     *
     * @param company to create
     * @return the company we just created
     */
    Company createCompany(Company company);

    /**
     * Update a company.
     *
     * @param company to create
     * @return the company we just created
     */
    Company updateCompany(Company company);

    /**
     * Delete the company in function of the id.
     *
     * @param id of the company to delete
     */
    void deleteCompany(long id);
}
