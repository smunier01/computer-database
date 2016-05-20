package com.excilys.cdb.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.PageParameters;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.validation.Validator;

/**
 * enum singleton for the company service.
 *
 * @author excilys
 */
@Service
@Transactional
public class CompanyService implements ICompanyService {

    private final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private ComputerDAO computerDAO;

    @Autowired
    private Validator validator;

    @Override
    @Transactional(readOnly = true)
    public Company getCompany(Long id) {
        this.LOGGER.debug("entering getCompany()");
        this.validator.validateId(id);
        return this.companyDAO.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> getCompanies(PageParameters page) {
        this.LOGGER.debug("entering getCompanies(page)");
        this.validator.validatePageParameters(page);
        return this.companyDAO.findAll(page);
    }

    @Override
    @Transactional
    public void deleteCompany(Long id) {
        this.LOGGER.debug("entering deleteCompany()");

        Company company = this.companyDAO.find(id);

        if (company != null) {
            this.computerDAO.deleteByCompanyId(id);
            this.companyDAO.delete(company);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> getCompanies() {
        this.LOGGER.debug("entering getCompanies()");
        return this.companyDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public long countCompanies() {
        this.LOGGER.debug("entering countCompanies()");
        return this.companyDAO.count();
    }
}
