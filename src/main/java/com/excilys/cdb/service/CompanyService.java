package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.model.Company;

/**
 *
 * enum singleton for the company service
 *
 * @author excilys
 */
public enum CompanyService {

	INSTANCE;

	final static Logger logger = LoggerFactory.getLogger(CompanyService.class);

	/**
	 *
	 */
	private final CompanyDAO companyDAO = CompanyDAO.getInstance();

	/**
	 *
	 */
	private CompanyService() {

	}

	/**
	 * accessor for the CompanyService singleton
	 *
	 * @return unique instance of CompanyService
	 */
	public static CompanyService getInstance() {
		return INSTANCE;
	}

	/**
	 * get company by its id
	 *
	 * @param id
	 *            of the company
	 * @return null if invalid id or computer doesn't exist
	 * @throws ServiceException
	 */
	public Company getCompany(final Long id) throws ServiceException {

		Company company = null;

		if ((id != null) && (id > 0)) {
			try {
				company = companyDAO.find(id);
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
		} else {
			ComputerService.LOGGER.warn("illegal argument when retrieving company, id : " + id);
			throw new IllegalArgumentException();
		}

		return company;
	}

	/**
	 * get list of company
	 *
	 * @param start
	 * @param nb
	 *            number max of companies to return
	 * @return the list of company
	 * @throws ServiceException
	 */
	public List<Company> getCompanies(final int offset, final int nb) throws ServiceException {

		List<Company> companies;

		if ((offset >= 0) && (nb > 0)) {
			try {
				companies = companyDAO.findAll(offset, nb);
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
		} else {
			ComputerService.LOGGER.warn("can't get companies with offset = " + offset + " and nb = " + nb);
			throw new IllegalArgumentException();
		}

		return companies;
	}

	/**
	 * get the entire company list with no offset / limit
	 *
	 * @return list of company
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Company> getCompanies() throws ServiceException {

		List<Company> companies;

		try {
			companies = companyDAO.findAll();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return companies;
	}

	/**
	 * get the number of companies
	 *
	 * @return number of companies
	 * @throws ServiceException
	 */
	public long countCompanies() throws ServiceException {

		long nbCompanies = 0;

		try {
			nbCompanies = companyDAO.count();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return nbCompanies;
	}
}
