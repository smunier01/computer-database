package com.excilys.cdb.service.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.PageParameters;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.validation.ValidatorUtil;

@Service
@Transactional
public class ComputerService implements IComputerService {

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    @Autowired
    private ComputerDAO computerDAO;

    @Autowired
    private ValidatorUtil validator;

    private AtomicLong count;

    @Override
    @Transactional
    public void deleteComputer(Long id) {
        this.LOGGER.debug("entering deleteComputer()");
        this.validator.validateId(id);

        Computer computer = this.computerDAO.find(id);

        if (computer != null) {
            this.computerDAO.delete(computer);
            this.count.decrementAndGet();
        }
    }

    @Override
    @Transactional
    public void deleteComputers(List<Long> ids) {
        this.LOGGER.debug("entering deleteComputers(List<Long>)");
        this.computerDAO.deleteAll(ids);
    }

    @Override
    @Transactional
    public void updateComputer(Computer computer) {
        this.LOGGER.debug("entering updateComputer()");
        this.validator.validateId(computer.getId());
        this.validator.validateComputer(computer);
        this.computerDAO.update(computer);
    }

    @Override
    @Transactional
    public Computer createComputer(Computer computer) {
        this.LOGGER.debug("entering createComputer()");
        this.validator.validateComputer(computer);
        Computer c = this.computerDAO.create(computer);
        this.count.incrementAndGet();
        return c;
    }

    @Override
    @Transactional(readOnly = true)
    public Computer getComputer(Long id) {
        this.LOGGER.debug("entering getComputer()");
        this.validator.validateId(id);
        return this.computerDAO.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Computer> getComputers(PageParameters page) {
        this.LOGGER.debug("entering getComputers(page)");
        this.validator.validatePageParameters(page);
        return this.computerDAO.findAll(page);
    }

    @Override
    @Transactional(readOnly = true)
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
            if (this.count == null) {
                this.count = new AtomicLong();
                result = this.computerDAO.count(page);
                this.count.set(result);
            } else {
                result = this.count.get();
            }
        } else {
            result = this.computerDAO.count(page);
        }

        return result;
    }

}
