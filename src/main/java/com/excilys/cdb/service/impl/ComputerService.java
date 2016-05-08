package com.excilys.cdb.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.PageParameters;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.validation.Validator;

/**
 *
 * @author excilys
 *
 */
public enum ComputerService implements IComputerService {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    private ComputerDAO computerDAO = ComputerDAO.getInstance();

    private Validator validator = Validator.getInstance();

    private final Map<String, Long> queryCounts = new ConcurrentHashMap<String, Long>(1);

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
    public void deleteComputer(Long id) {
        this.LOGGER.debug("entering deleteComputer()");
        this.validator.validateId(id);

        Computer computer = this.computerDAO.find(id);

        if (computer != null) {
            this.computerDAO.delete(computer);
            this.decTotalCount();
        }
    }

    @Override
    public void updateComputer(Computer computer) {
        this.LOGGER.debug("entering updateComputer()");
        this.validator.validateId(computer.getId());
        this.validator.validateComputer(computer);
        this.computerDAO.update(computer);
    }

    @Override
    public Computer createComputer(Computer computer) {
        this.LOGGER.debug("entering createComputer()");
        this.validator.validateComputer(computer);
        Computer c = this.computerDAO.create(computer);
        this.incTotalCount();
        return c;
    }

    @Override
    public Computer getComputer(Long id) {
        this.LOGGER.debug("entering getComputer()");
        this.validator.validateId(id);
        return this.computerDAO.find(id);
    }

    @Override
    public List<Computer> getComputers(PageParameters page) {
        this.LOGGER.debug("entering getComputers(page)");
        this.validator.validatePageParameters(page);
        return this.computerDAO.findAll(page);
    }

    @Override
    public Page<Computer> getComputersPage(PageParameters param) {
        this.LOGGER.debug("entering getComputersPage()");
        this.validator.validatePageParameters(param);
        List<Computer> computers = this.computerDAO.findAll(param);

        // we need the total number of computers for the pagination
        long nbComputers;

        // small optimization.. if we are on the first page and the number of
        // computers returned is less than the page size, then there is no need
        // to count the computers.
        if ((computers.size() < param.getSize()) && (param.getPageNumber() == 0)) {
            nbComputers = computers.size();
        } else {
            nbComputers = this.countComputers(param);
        }

        return new Page.Builder<Computer>().list(computers).totalCount(nbComputers).params(param).build();
    }

    @Override
    public long countComputers(PageParameters page) {
        this.LOGGER.debug("entering countComputers(page)");
        this.validator.validatePageParameters(page);

        long result = 0;

        if (page.getSearch().isEmpty()) {
            Long cnt = this.queryCounts.get("total");

            if (cnt == null) {
                result = this.computerDAO.count(page);
                this.queryCounts.putIfAbsent("total", result);
            } else {
                result = cnt;
            }
        } else {
            result = this.computerDAO.count(page);
        }

        return result;
    }

    private void decTotalCount() {
        Long cnt = this.queryCounts.get("total");

        if (cnt != null) {
            this.queryCounts.put("total", cnt - 1);
        }
    }

    private void incTotalCount() {
        Long cnt = this.queryCounts.get("total");

        if (cnt != null) {
            this.queryCounts.put("total", cnt + 1);
        }

    }
}
