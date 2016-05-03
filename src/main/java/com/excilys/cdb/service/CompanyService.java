package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.util.PageParameters;
import com.excilys.cdb.validation.Validator;

/**
 * enum singleton for the company service.
 *
 * @author excilys
 */
public enum CompanyService {

    INSTANCE;

    static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyDAO companyDAO = CompanyDAO.getInstance();

    private final Validator validator = Validator.getInstance();

    /**
     * default constructor for the singleton.
     */
    CompanyService() {

    }

    /**
     * accessor for the CompanyService singleton.
     *
     * @return unique instance of CompanyService
     */
    public static CompanyService getInstance() {
        return INSTANCE;
    }

    /**
     * get company by its id.
     *
     * @param id
     *            of the company
     * @return null if invalid id or computer doesn't exist
     * @throws ServiceException
     *             exception
     */
    public Company getCompany(final Long id) throws ServiceException {
        this.validator.validateId(id);
        return this.companyDAO.find(id);
    }

    /**
     * get list of company.
     *
     * @param page
     *            page parameters
     * @return the list of company
     * @throws ServiceException
     *             exception
     */
    public List<Company> getCompanies(final PageParameters page) {
        this.validator.validatePageParameters(page);
        return this.companyDAO.findAll(page);
    }

    public void deleteCompany(final Long id) {
        this.validator.validateId(id);

        final Company company = this.companyDAO.find(id);

        if (company != null) {
            this.companyDAO.delete(company);
        }
    }

    /**
     * get the entire company list with no page parameters.
     *
     * @return list of company
     * @throws ServiceException
     *             exception
     */
    public List<Company> getCompanies() {
        return this.companyDAO.findAll();
    }

    /**
     * get the number of companies.
     *
     * @return number of companies
     * @throws ServiceException
     *             exception
     */
    public long countCompanies() {
        return this.companyDAO.count();
    }
}
