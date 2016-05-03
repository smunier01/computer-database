package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.util.PageParameters;
import com.excilys.cdb.validation.Validator;

/**
 *
 * @author excilys
 *
 */
public enum ComputerService {

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

    /**
     * delete a computer.
     *
     * @param id
     *            id of the computer to delete
     * @throws IllegalArgumentException
     *             exception
     */
    public void deleteComputer(final Long id) {
        LOGGER.debug("entering deleteComputer()");
        this.validator.validateId(id);

        final Computer computer = this.computerDAO.find(id);

        if (computer != null) {
            this.computerDAO.delete(computer);
        }
    }

    /**
     * update a computer
     *
     * @param computer
     *            computer to update
     */
    public void updateComputer(final Computer computer) {
        LOGGER.debug("entering updateComputer()");
        this.validator.validateId(computer.getId());
        this.validator.validateComputer(computer);
        this.computerDAO.update(computer);
    }

    /**
     * add a new computer to the database based on a computer object.
     *
     * @param c
     *            computer object to add to the database
     * @return instance of the computer with id updated
     */
    public Computer createComputer(final Computer computer) {
        LOGGER.debug("entering createComputer()");
        this.validator.validateComputer(computer);
        return this.computerDAO.create(computer);
    }

    /**
     * get computer by its id.
     *
     * @param id
     *            id of the computer
     * @return computer or null if invalid id or computer doesn't exist
     */
    public Computer getComputer(final Long id) {
        LOGGER.debug("entering getComputer()");
        this.validator.validateId(id);
        return this.computerDAO.find(id);
    }

    /**
     * get list of computers.
     *
     * @param page
     *            page parameters
     * @return the list of computers
     */
    public List<Computer> getComputers(final PageParameters page) {
        LOGGER.debug("entering getComputers(page)");
        this.validator.validatePageParameters(page);
        return this.computerDAO.findAll(page);
    }

    /**
     * get the number of computers.
     *
     * @return number of computers
     */
    public long countComputers(final PageParameters page) {
        LOGGER.debug("entering countComputers(page)");
        return this.computerDAO.count(page);
    }
}
