package com.excilys.cdb.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * 
 * @author excilys
 *
 */
public class ComputerService {

    private ComputerDAO computerDAO = ComputerDAO.getInstance();

    private CompanyDAO companyDAO = CompanyDAO.getInstance();

    final static Logger logger = LoggerFactory.getLogger(ComputerService.class);

    /**
     * 
     */
    public ComputerService() {

    }

    /**
     * delete a computer
     * 
     * @param id
     *            id of the computer to delete
     * @throws DAOException 
     */
    public void deleteComputer(Long id) throws DAOException {

        if (id == null || id <= 0) {
            logger.warn("can't delete computer with id : " + id);
            return;
        }

        // we get the computer to see if it exists
        Computer computer = this.computerDAO.find(id);

        if (computer != null) {
            this.computerDAO.delete(computer);
        }
    }

    /**
     * update a computer
     * 
     * @param id
     *            id of the computer
     * @param name
     *            new name
     * @param introduced
     *            new introduced date
     * @param discontinued
     *            new discontinued date
     * @param companyId
     *            new company id (0 if no company)
     * @throws DAOException 
     */
    public void updateComputer(Long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId) throws DAOException {

        // check parameters and return if something is wrong
        if (id == null || id <= 0 || "".equals(name) || introduced == null || discontinued == null
                || companyId == null) {
            logger.warn("wrong parameter when updating computer");
            return;
        }

        // use a default company if id <= 0
        Company company = companyId <= 0 ? new Company() : this.companyDAO.find(companyId);

        Computer computer = new Computer.ComputerBuilder()
                .id(id)
                .name(name)
                .introduced(introduced)
                .discontinued(discontinued)
                .company(company)
                .build();

        this.computerDAO.update(computer);

    }

    /**
     * create a new computer
     * 
     * @param name
     *            name of the computer
     * @param introduced
     *            introduced date
     * @param discontinued
     *            discontinued date
     * @param companyId
     *            id of the company (0 if no company)
     * @throws DAOException 
     */
    public void createComputer(String name, LocalDate introduced, LocalDate discontinued, Long companyId) throws DAOException {

        // check parameters and return if something is wrong
        if ("".equals(name) || introduced == null || discontinued == null || companyId == null) {
            logger.warn("wrong parameter when creating computer");
            return;
        }

        // use a default company if id <= 0
        Company company = companyId <= 0 ? new Company() : this.companyDAO.find(companyId);

        Computer computer = new Computer.ComputerBuilder()
                .name(name)
                .introduced(introduced)
                .discontinued(discontinued)
                .company(company)
                .build();

        this.computerDAO.create(computer);

    }

    /**
     * get company by its id
     * 
     * @param id
     * @return company or null if invalid id or computer doesn't exist
     * @throws DAOException 
     */
    public Company getCompany(Long id) throws DAOException {
        if (id != null && id > 0) {
            return this.companyDAO.find(id);
        } else {
            logger.warn("can't get company with id : " + id);
            return null;
        }
    }

    /**
     * get computer by its id
     * 
     * @param id
     * @return computer or null if invalid id or computer doesn't exist
     * @throws DAOException 
     */
    public Computer getComputer(Long id) throws DAOException {
        if (id != null && id > 0) {
            return this.computerDAO.find(id);
        } else {
            logger.warn("can't get computer with id : " + id);
            return null;
        }
    }

    /**
     * get list of computers
     * 
     * @param offset
     * @param nb
     *            number max of computers to return
     * @return the list of computers
     * @throws DAOException 
     */
    public List<Computer> getComputers(int offset, int nb) throws DAOException {
        if (offset >= 0 && nb > 0) {
            return this.computerDAO.findAll(offset, nb);
        } else {
            logger.warn("can't get computers with offset = " + offset + " and nb = " + nb);
            return new ArrayList<Computer>();
        }
    }

    /**
     * get list of company
     * 
     * @param start
     * @param nb
     *            number max of companies to return
     * @return the list of company
     * @throws DAOException 
     */
    public List<Company> getCompanies(int offset, int nb) throws DAOException {
        if (offset >= 0 && nb > 0) {
            return this.companyDAO.findAll(offset, nb);
        } else {
            logger.warn("can't get companies with offset = " + offset + " and nb = " + nb);
            return new ArrayList<Company>();
        }
    }
}
