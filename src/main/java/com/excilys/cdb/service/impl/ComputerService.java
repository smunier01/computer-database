package com.excilys.cdb.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.util.PageParameters;
import com.excilys.cdb.validation.Validator;

/**
 *
 * @author excilys
 *
 */
public enum ComputerService implements IComputerService {

    INSTANCE;

    static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    private final ComputerDAO computerDAO = ComputerDAO.getInstance();

    private final Validator validator = Validator.getInstance();

    private ComputerService() {

    }

    /**
     * accessor for the ComputerService singleton.
     *
     * @return unique instance of ComputerService
     */
    public static ComputerService getInstance() {
        return INSTANCE;
    }

    @Override
    public void deleteComputer(final Long id) {
        LOGGER.debug("entering deleteComputer()");
        this.validator.validateId(id);

        final Computer computer = this.computerDAO.find(id);

        if (computer != null) {
            this.computerDAO.delete(computer);
        }
    }

    @Override
    public void updateComputer(final Computer computer) {
        LOGGER.debug("entering updateComputer()");
        this.validator.validateId(computer.getId());
        this.validator.validateComputer(computer);
        this.computerDAO.update(computer);
    }

    @Override
    public Computer createComputer(final Computer computer) {
        LOGGER.debug("entering createComputer()");
        this.validator.validateComputer(computer);
        return this.computerDAO.create(computer);
    }

    @Override
    public Computer getComputer(final Long id) {
        LOGGER.debug("entering getComputer()");
        this.validator.validateId(id);
        return this.computerDAO.find(id);
    }

    @Override
    public List<Computer> getComputers(final PageParameters page) {
        LOGGER.debug("entering getComputers(page)");
        this.validator.validatePageParameters(page);
        return this.computerDAO.findAll(page);
    }

    @Override
    public long countComputers(final PageParameters page) {
        LOGGER.debug("entering countComputers(page)");
        return this.computerDAO.count(page);
    }
}
