package com.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.mapper.LocalDateMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.PageParameters;

/**
 * Singleton for the ComputerDAO.
 *
 * implements all the CRUD operations defined in DAO<>.
 *
 * @author simon
 *
 */
@Repository
public class ComputerDAO implements DAO<Computer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    @Autowired
    private ComputerMapper mapper;

    @Autowired
    private LocalDateMapper dateMapper;

    private static final String FIND_BY_ID = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name FROM computer c LEFT JOIN company o on c.company_id=o.id WHERE c.id=?";

    private static final String CREATE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";

    private static final String UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";

    private static final String DELETE = "DELETE FROM computer WHERE id=?";

    private static final String DELETE_LIST = "DELETE FROM computer WHERE id IN (%s)";

    private static final String FIND_ALL = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name FROM computer c LEFT JOIN company o ON c.company_id=o.id";

    private static final String FIND_ALL_BETTER = "SELECT B.id, B.name, B.introduced, B.discontinued, B.company_id, C.name as company_name FROM (SELECT id FROM computer WHERE name like ? ORDER BY %s %s LIMIT ?, ?) A LEFT JOIN computer B on B.id=A.id LEFT JOIN company C on B.company_id=C.id";

    private static final String FIND_ALL_LIMIT_ORDER = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name FROM computer c %s left join company o ON c.company_id=o.id WHERE c.name like ? ORDER BY %s %s LIMIT ?,?";

    private static final String FIND_ALL_BETTER_NO_SEARCH = "SELECT B.id, B.name, B.introduced, B.discontinued, B.company_id, C.name as company_name FROM (SELECT id FROM computer ORDER BY %s %s LIMIT ?, ?) A LEFT JOIN computer B on B.id=A.id LEFT JOIN company C on B.company_id=C.id";

    private static final String COUNT = "SELECT count(id) as nb FROM computer";

    private static final String COUNT_SEARCH = "SELECT count(name) as nb FROM computer WHERE name like ?";

    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE company_id=?";

    private JdbcTemplate jdbcTemplate;

    @Resource(name = "HikariDatasource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Computer find(Long id) {
        try {
            return this.jdbcTemplate.queryForObject(FIND_BY_ID, (rs1, rowNum) -> ComputerDAO.this.mapper.map(rs1), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public Computer create(Computer obj) {

        try {

            Timestamp introduced = this.dateMapper.toTimestamp(obj.getIntroduced());
            Timestamp discontinued = this.dateMapper.toTimestamp(obj.getDiscontinued());
            Long companyId = obj.getCompany() == null ? null : obj.getCompany().getId();

            KeyHolder keyHolder = new GeneratedKeyHolder();

            int res = this.jdbcTemplate.update((PreparedStatementCreator) connection -> {
                PreparedStatement ps = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
                this.setParams(ps, obj.getName(), introduced, discontinued, companyId);
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
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }

        return obj;
    }

    @Override
    public Computer update(Computer obj) {

        try {
            Timestamp introduced = this.dateMapper.toTimestamp(obj.getIntroduced());
            Timestamp discontinued = this.dateMapper.toTimestamp(obj.getDiscontinued());
            Long companyId = obj.getCompany() == null ? null : obj.getCompany().getId();
            this.jdbcTemplate.update(UPDATE, new Object[] { obj.getName(), introduced, discontinued, companyId, obj.getId() });
        } catch (DataAccessException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }

        return obj;
    }

    @Override
    public void delete(Computer obj) {
        try {
            this.jdbcTemplate.update(DELETE, obj.getId());
        } catch (DataAccessException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Delete computers based on their company.
     *
     * @param id
     *            id of the company to whom the computers to delete belong.
     */
    public void deleteByCompanyId(Long id) {
        try {
            this.jdbcTemplate.update(DELETE_COMPUTER, id);
        } catch (DataAccessException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteAll(List<Long> objs) {

        StringBuilder builder = new StringBuilder();

        for (Long id : objs) {
            builder.append(id + ",");
        }

        String sql = String.format(DELETE_LIST, builder.deleteCharAt(builder.length() - 1).toString());

        try {
            this.jdbcTemplate.update(sql, objs);
        } catch (DataAccessException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public List<Computer> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Computer> findAll(PageParameters page) {
        try {
            String sql = String.format(FIND_ALL_BETTER, page.getOrder().toString(), page.getDirection().toString());
            return this.jdbcTemplate.query(sql, (rs, rowNum) -> ComputerDAO.this.mapper.map(rs), new Object[] { page.getSearch() + "%", page.getSize() * page.getPageNumber(), page.getSize() });
        } catch (DataAccessException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Count number of computers using a page parameters.
     *
     * @param page
     *            parameters for the query.
     * @return number of computers.
     */
    public long count(PageParameters page) {
        try {
            return this.jdbcTemplate.queryForObject(COUNT_SEARCH, Long.class, page.getSearch() + "%");
        } catch (DataAccessException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public long count() {
        try {
            return this.jdbcTemplate.queryForObject(COUNT, Long.class);
        } catch (DataAccessException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        }
    }
}
