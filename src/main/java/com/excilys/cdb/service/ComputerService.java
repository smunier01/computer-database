package com.excilys.cdb.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 *
 * @author excilys
 *
 */
public enum ComputerService {

	INSTANCE;

	final static Logger logger = LoggerFactory.getLogger(ComputerService.class);

	/**
	 * instance of the computerDAO class
	 */
	private final ComputerDAO computerDAO = ComputerDAO.getInstance();

	/**
	 *
	 */
	private final CompanyDAO companyDAO = CompanyDAO.getInstance();

	/**
	 *
	 */
	private ComputerService() {

	}

	/**
	 * accessor for the ComputerService singleton
	 *
	 * @return unique instance of ComputerService
	 */
	public static ComputerService getInstance() {
		return INSTANCE;
	}

	/**
	 * delete a computer
	 *
	 * @param id
	 *            id of the computer to delete
	 * @throws ServiceException
	 */
	public void deleteComputer(final Long id) throws ServiceException {

		if ((id == null) || (id <= 0)) {
			logger.warn("can't delete computer with id : " + id);
			throw new IllegalArgumentException();
		}

		try {
			// we get the computer to see if it exists
			final Computer computer = computerDAO.find(id);

			if (computer != null) {
				computerDAO.delete(computer);
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
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
	 * @throws ServiceException
	 */
	public void updateComputer(final Long id, final String name, final LocalDate introduced,
			final LocalDate discontinued, final Long companyId) throws ServiceException {

		// TODO introduced & discontinued should be valid even if null
		if ((id == null) || (id <= 0) || "".equals(name) || (introduced == null) || (discontinued == null)) {
			ComputerService.logger.warn("wrong parameter when updating computer");
			throw new IllegalArgumentException();
		}

		try {
			// use a default company if id <= 0 or id == null*
			final Company company = ((companyId == null) || (companyId <= 0)) ? new Company()
					: companyDAO.find(companyId);

			final Computer computer = new Computer.ComputerBuilder().id(id).name(name).introduced(introduced)
					.discontinued(discontinued).company(company).build();

			computerDAO.update(computer);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

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
	 * @throws ServiceException
	 */
	public void createComputer(final String name, final LocalDate introduced, final LocalDate discontinued,
			final Long companyId) throws ServiceException {

		// check parameters and return if something is wrong
		if ("".equals(name) || (introduced == null) || (discontinued == null) || (companyId == null)) {
			ComputerService.logger.warn("wrong parameter when creating computer");
			throw new IllegalArgumentException();
		}

		try {
			// use a default company if id <= 0 or id == null
			final Company company = ((companyId == null) || (companyId <= 0)) ? new Company()
					: companyDAO.find(companyId);

			final Computer computer = new Computer.ComputerBuilder().name(name).introduced(introduced)
					.discontinued(discontinued).company(company).build();

			computerDAO.create(computer);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	/**
	 * get computer by its id
	 *
	 * @param id
	 * @return computer or null if invalid id or computer doesn't exist
	 * @throws ServiceException
	 */
	public Computer getComputer(final Long id) throws ServiceException {

		if ((id == null) || (id <= 0)) {
			ComputerService.logger.warn("can't get computer with id : " + id);
			throw new IllegalArgumentException();
		}

		try {
			return computerDAO.find(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	/**
	 * get list of computers
	 *
	 * @param offset
	 * @param nb
	 *            number max of computers to return
	 * @return the list of computers
	 * @throws ServiceException
	 */
	public List<Computer> getComputers(final int offset, final int nb) throws ServiceException {
		if ((offset < 0) || (nb <= 0)) {
			logger.warn("can't get computers with offset = " + offset + " and nb = " + nb);
			throw new IllegalArgumentException();
		}

		try {
			return computerDAO.findAll(offset, nb);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * get the number of computers
	 *
	 * @return number of computers
	 * @throws ServiceException
	 */
	public long countComputers() throws ServiceException {
		try {
			return computerDAO.count();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
