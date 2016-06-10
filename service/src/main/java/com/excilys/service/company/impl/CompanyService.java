package com.excilys.service.company.impl;


import com.excilys.binding.validation.ValidatorUtil;
import com.excilys.core.model.Company;
import com.excilys.core.model.PageParameters;
import com.excilys.persistence.dao.CompanyDAO;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.service.ICompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService implements ICompanyService {

    private final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private ComputerDAO computerDAO;

    @Autowired
    private ValidatorUtil validator;

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

    @Override
    @Transactional
    public Company createCompany(Company company) {
        this.LOGGER.debug("entering createCompany()");
        return this.companyDAO.create(company);
    }

    @Override
    @Transactional
    public void updateCompany(Company company) {
        this.LOGGER.debug("entering updateCompany()");
        this.companyDAO.update(company);
    }

    @Override
    public List<String> findAutocompleteResult(String entry){
        return this.companyDAO.findAutocompleteMatches(entry);
    }
}
