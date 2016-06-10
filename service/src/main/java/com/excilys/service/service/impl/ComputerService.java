package com.excilys.service.service.impl;

import com.excilys.binding.validation.ValidatorUtil;
import com.excilys.core.model.Computer;
import com.excilys.core.model.Page;
import com.excilys.core.model.PageParameters;
import com.excilys.persistence.dao.ComputerDAO;
import com.excilys.service.service.IComputerService;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ComputerService implements IComputerService {

    // list of the variables
    private final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
    @Autowired
    protected PlatformTransactionManager txManager;
    @Autowired
    private ComputerDAO computerDAO;
    @Autowired
    private ValidatorUtil validator;
    /**
     * cache for the total number of computers in the database.
     */
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
        this.validator.validateComputer(computer);
        this.computerDAO.update(computer);
    }

    @Override
    @Transactional
    public Computer createComputer(Computer computer) {
        this.LOGGER.debug("entering createComputer()");
        this.validator.validateComputer(computer);

        Computer c = this.computerDAO.create(computer);

        if (this.count != null) {
            this.count.incrementAndGet();
        }

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
    public List<Computer> getAll() {
        this.LOGGER.debug("entering get()");
        return this.computerDAO.findAll();
    }

    @Override
    public long countComputers(PageParameters page) {
        this.LOGGER.debug("entering countComputers(page)");
        this.validator.validatePageParameters(page);

        long result;

        // try to use the cache if there is no search query.
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

    /**
     * PostConstruct method to create the hibernate search index if needed.
     * <p>
     * TransactionCallbackWithoutResult is necessary in order to make sure that the context is fully instantiated.
     *
     * @throws Exception
     */
    @PostConstruct
    public void initIt() throws Exception {

        TransactionTemplate tmpl = new TransactionTemplate(txManager);

        tmpl.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    IndexReader reader = DirectoryReader.open(FSDirectory.open(FileSystems.getDefault().getPath("/tmp/lucene/indexes/com.excilys.core.model.Computer/")));

                    if (reader.maxDoc() == 0) {
                        LOGGER.warn("lucene index empty. Building index...");
                        computerDAO.buildIndex();
                    } else {
                        LOGGER.warn("Found " + reader.maxDoc() + " documents in the lucene index.");
                    }
                } catch (IOException e) {
                    try {
                        LOGGER.warn("No lucene index found. Building index...");
                        computerDAO.buildIndex();
                    } catch (InterruptedException | IOException e1) {
                        LOGGER.warn("Couldn't finish building lucene index.", e1);
                    }
                } catch (InterruptedException e) {
                    LOGGER.warn("Couldn't finish building lucene index.", e);
                }
            }
        });
    }

    @Override
    public List<String> findAutocompleteResult(String entry) {
        return this.computerDAO.findAutocompleteMatches(entry);
    }
}
