package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.util.PageParameters;

/**
 * enum singleton for the company service.
 *
 * @author excilys
 */
public enum CompanyService {

    INSTANCE;

    static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyDAO companyDAO = CompanyDAO.getInstance();

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

        Company company = null;

        if ((id != null) && (id > 0)) {
            try {
                company = companyDAO.find(id);
            } catch (final DAOException e) {
                throw new ServiceException(e);
            }
        } else {
            ComputerService.LOGGER.warn("illegal argument when retrieving company, id : " + id);
            throw new IllegalArgumentException();
        }

        return company;
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
    public List<Company> getCompanies(final PageParameters page) throws ServiceException {

        List<Company> companies;

        if ((page.getSize() >= 0) && (page.getPageNumber() > 0)) {
            try {
                companies = companyDAO.findAll(page);
            } catch (final DAOException e) {
                throw new ServiceException(e);
            }
        } else {
            ComputerService.LOGGER.warn("can't get companies with page = " + page);
            throw new IllegalArgumentException();
        }

        return companies;
    }

    /**
     * get the entire company list with no page parameters.
     *
     * @return list of company
     * @throws ServiceException
     *             exception
     */
    public List<Company> getCompanies() throws ServiceException {
        List<Company> companies;

        try {
            companies = companyDAO.findAll();
        } catch (final DAOException e) {
            throw new ServiceException(e);
        }

        return companies;
    }

    /**
     * get the number of companies.
     *
     * @return number of companies
     * @throws ServiceException
     *             exception
     */
    public long countCompanies() throws ServiceException {

        long nbCompanies = 0;

        try {
            nbCompanies = companyDAO.count();
        } catch (final DAOException e) {
            throw new ServiceException(e);
        }

        return nbCompanies;
    }
}
