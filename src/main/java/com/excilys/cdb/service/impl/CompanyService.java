package com.excilys.cdb.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.util.PageParameters;
import com.excilys.cdb.validation.Validator;

/**
 * enum singleton for the company service.
 *
 * @author excilys
 */
public enum CompanyService implements ICompanyService {

    INSTANCE;

    static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyDAO companyDAO = CompanyDAO.getInstance();

    private final Validator validator = Validator.getInstance();

    /**
     * default constructor for the singleton.
     */
    private CompanyService() {

    }

    /**
     * accessor for the CompanyService singleton.
     *
     * @return unique instance of CompanyService
     */
    public static CompanyService getInstance() {
        return INSTANCE;
    }

    @Override
    public Company getCompany(final Long id) {
        LOGGER.debug("entering getCompany()");
        this.validator.validateId(id);
        return this.companyDAO.find(id);
    }

    @Override
    public List<Company> getCompanies(final PageParameters page) {
        LOGGER.debug("entering getCompanies(page)");
        this.validator.validatePageParameters(page);
        return this.companyDAO.findAll(page);
    }

    @Override
    public void deleteCompany(final Long id) {
        LOGGER.debug("entering deleteCompany()");
        this.validator.validateId(id);

        final Company company = this.companyDAO.find(id);

        if (company != null) {
            this.companyDAO.delete(company);
        }
    }

    @Override
    public List<Company> getCompanies() {
        LOGGER.debug("entering getCompanies()");
        return this.companyDAO.findAll();
    }

    @Override
    public long countCompanies() {
        LOGGER.debug("entering countCompanies()");
        return this.companyDAO.count();
    }
}
