package com.excilys.cdb.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.jdbc.ConnectionMySQLFactory;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.PageParameters;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.validation.Validator;

/**
 * enum singleton for the company service.
 *
 * @author excilys
 */
public enum CompanyService implements ICompanyService {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    private CompanyDAO companyDAO = CompanyDAO.getInstance();

    private ComputerDAO computerDAO = ComputerDAO.getInstance();

    private Validator validator = Validator.getInstance();

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
    public Company getCompany(Long id) {
        this.LOGGER.debug("entering getCompany()");
        this.validator.validateId(id);
        return this.companyDAO.find(id);
    }

    @Override
    public List<Company> getCompanies(PageParameters page) {
        this.LOGGER.debug("entering getCompanies(page)");
        this.validator.validatePageParameters(page);
        return this.companyDAO.findAll(page);
    }

    @Override
    public void deleteCompany(Long id) {
        this.LOGGER.debug("entering deleteCompany()");
        this.validator.validateId(id);

        Company company = this.companyDAO.find(id);
        Connection con = ConnectionMySQLFactory.getInstance().create();

        if (company != null) {

            try {

                con.setAutoCommit(false);

                this.computerDAO.deleteByCompanyId(con, id);
                this.companyDAO.delete(company);

                con.commit();

            } catch (DAOException | SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new ServiceException(e1);
                }
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new ServiceException(e);
                }
            }
        }
    }

    @Override
    public List<Company> getCompanies() {
        this.LOGGER.debug("entering getCompanies()");
        return this.companyDAO.findAll();
    }

    @Override
    public long countCompanies() {
        this.LOGGER.debug("entering countCompanies()");
        return this.companyDAO.count();
    }
}
