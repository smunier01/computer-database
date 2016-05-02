package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.util.PageParameters;

/**
 *
 * @author excilys
 *
 */
public enum ComputerService {

    INSTANCE;

    static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    private final ComputerDAO computerDAO = ComputerDAO.getInstance();

    /**
     *
     */
    ComputerService() {

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
     * @throws ServiceException
     *             exception
     */
    public void deleteComputer(final Long id) {

        if ((id == null) || (id <= 0)) {
            ComputerService.LOGGER.warn("can't delete computer with id : " + id);
            throw new IllegalArgumentException();
        }

        // we get the computer to see if it exists
        final Computer computer = this.computerDAO.find(id);

        if (computer != null) {
            this.computerDAO.delete(computer);
        }
    }

    public void updateComputer(final Computer computer) {

        if ((computer.getId() == null) || computer.getId() <= 0 || !computer.getName().isEmpty()) {
            ComputerService.LOGGER.warn("wrong parameter when updating computer");
            throw new IllegalArgumentException();
        }

        this.computerDAO.update(computer);
    }

    /**
     * add a new computer to the database based on a computer object.
     *
     * @param c
     *            computer object to add to the database
     * @return instance of the computer with id updated
     * @throws ServiceException
     *             exception
     */
    public Computer createComputer(final Computer c) {
        if ((c.getName() == null) || "".equals(c.getName())) {
            LOGGER.warn("wrong parameters when creating computer");
            throw new IllegalArgumentException();
        }

        return this.computerDAO.create(c);
    }

    /**
     * get computer by its id.
     *
     * @param id
     *            id of the computer
     * @return computer or null if invalid id or computer doesn't exist
     * @throws ServiceException
     *             exception
     */
    public Computer getComputer(final Long id) {

        if ((id == null) || (id <= 0)) {
            ComputerService.LOGGER.warn("can't get computer with id : " + id);
            throw new IllegalArgumentException();
        }

        return this.computerDAO.find(id);

    }

    /**
     * get list of computers.
     *
     * @param page
     *            page parameters
     * @return the list of computers
     * @throws ServiceException
     *             exception
     */
    public List<Computer> getComputers(final PageParameters page) {
        if ((page.getPageNumber() < 0) || (page.getSize() <= 0)) {
            ComputerService.LOGGER.warn("can't get computers with page = " + page);
            throw new IllegalArgumentException();
        }

        return this.computerDAO.findAll(page);
    }

    /**
     * get the number of computers.
     *
     * @return number of computers
     * @throws ServiceException
     *             exception
     */
    public long countComputers(final PageParameters page) throws ServiceException {
        return this.computerDAO.count(page);
    }
}
