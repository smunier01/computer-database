package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.jdbc.ConnectionManager;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.PageParameters;

/**
 * Singleton for the CompanyDAO.
 *
 * implements all the CRUD operations defined in DAO<>.
 *
 * @author simon
 */
@Repository
public class CompanyDAO implements DAO<Company> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    @Autowired
    private CompanyMapper companyMapper;

    private static final String FIND_BY_ID = "SELECT id, name FROM company WHERE id=?";

    private static final String FIND_ALL = "SELECT id, name FROM company";

    private static final String FIND_ALL_LIMIT = "SELECT id, name FROM company LIMIT ?,?";

    private static final String INSERT = "INSERT INTO company (name) VALUES (?)";

    private static final String UPDATE = "UPDATE company SET name=? WHERE id=?";

    private static final String DELETE = "DELETE FROM company WHERE id=?";

    private static final String COUNT = "SELECT count(id) as nb FROM company";

    private JdbcTemplate jdbcTemplate;

    @Resource(name = "HikariDatasource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Company find(Long id) {

        Company company = null;

        try {
            company = this.jdbcTemplate.queryForObject(FIND_BY_ID, Company.class, id);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }

        return company;
    }

    @Override
    public Company create(Company obj) {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            int res = this.jdbcTemplate.update((PreparedStatementCreator) connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                this.setParams(ps, obj.getName());
                return ps;
            }, keyHolder);

            if (res > 0) {
                obj.setId(keyHolder.getKey().longValue());
                LOGGER.info("successfully created computer : " + obj.toString());
            } else {
                LOGGER.warn("Could not create computer : " + obj.toString());
                throw new DAOException("Could not create computer.");
            }

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }

        return obj;
    }

    @Override
    public Company update(Company obj) {

        try {

            int res = this.jdbcTemplate.update(UPDATE, obj.getName(), obj.getId());

            if (res > 0) {
                CompanyDAO.LOGGER.info("succefully updated company : " + obj.getId());
            } else {
                CompanyDAO.LOGGER.warn("couldn't update company : " + obj.getId());
            }

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }

        return obj;
    }

    @Override
    public void delete(Company obj) {

        try {

            this.jdbcTemplate.update(DELETE, obj.getId());

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteAll(List<Long> objs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Company> findAll() {

        List<Company> result = null;

        try {

            result = this.jdbcTemplate.query(FIND_ALL, (rs, rowNum) -> this.companyMapper.map(rs));

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }

        return result;
    }

    @Override
    public List<Company> findAll(PageParameters page) {
        List<Company> result;

        try {

            result = this.jdbcTemplate.query(FIND_ALL_LIMIT, (rs, rowNum) -> this.companyMapper.map(rs), page.getSize() * page.getPageNumber(), page.getSize());

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }

        return result;
    }

    @Override
    public long count() throws DAOException {

        long nb = 0;

        try {

            this.jdbcTemplate.queryForObject(COUNT, Long.class);

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }

        return nb;
    }

}
