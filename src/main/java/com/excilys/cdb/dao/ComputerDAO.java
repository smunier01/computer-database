package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.jdbc.ConnectionMySQLFactory;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.mapper.LocalDateMapper;
import com.excilys.cdb.mapper.MapperException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.util.PageParameters;
import com.excilys.cdb.util.PageParameters.Direction;
import com.excilys.cdb.util.PageParameters.Order;

/**
 * ComputerDAO class.
 *
 * @author excilys
 *
 */
public class ComputerDAO extends DAO<Computer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private final ComputerMapper mapper = ComputerMapper.getInstance();

    private final LocalDateMapper dateMapper = LocalDateMapper.getInstance();

    private final ConnectionMySQLFactory connectionFactory = ConnectionMySQLFactory.getInstance();

    private static volatile ComputerDAO instance = null;

    private static final String FIND_BY_ID = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name FROM computer c LEFT JOIN company o on c.company_id=o.id WHERE c.id=?";

    private static final String CREATE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";

    private static final String UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";

    private static final String DELETE = "DELETE FROM computer WHERE id=?";

    private static final String DELETE_LIST = "DELETE FROM computer WHERE id IN %s";

    private static final String FIND_ALL = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name FROM computer c LEFT JOIN company o ON c.company_id=o.id";

    private static final String FIND_ALL_LIMIT_ORDER = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name FROM computer c left join company o ON c.company_id=o.id WHERE c.name like ? ORDER BY %s %s LIMIT ?,?";

    private static final String COUNT = "SELECT count(id) as nb FROM computer";

    private static final String COUNT_SEARCH = "SELECT count(id) as nb FROM computer WHERE name like ?";

    /**
     * default constructor for the singleton.
     */
    private ComputerDAO() {
        super();
    }

    /**
     * public accessor for the singleton.
     *
     * @return unique instance of the class
     */
    public static ComputerDAO getInstance() {

        if (ComputerDAO.instance == null) {
            synchronized (ComputerDAO.class) {
                if (ComputerDAO.instance == null) {
                    ComputerDAO.instance = new ComputerDAO();
                }
            }
        }

        return ComputerDAO.instance;
    }

    @Override
    public Computer find(final Long id) {

        Computer computer = null;

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(FIND_BY_ID);

            setParams(stmt, id);

            rs = stmt.executeQuery();

            if (rs.first()) {

                computer = mapper.map(rs);

                ComputerDAO.LOGGER.info("succefully found computer of id : " + id);
            } else {
                ComputerDAO.LOGGER.warn("couldn't find computer of id : " + id);
            }

        } catch (final SQLException | MapperException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closeAll(con, stmt, rs);
        }

        return computer;
    }

    @Override
    public Computer create(final Computer obj) {

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);

            final Timestamp introduced = dateMapper.toTimestamp(obj.getIntroduced());

            final Timestamp discontinued = dateMapper.toTimestamp(obj.getDiscontinued());

            final Long companyId = obj.getCompany() == null ? null : obj.getCompany().getId();

            setParams(stmt, obj.getName(), introduced, discontinued, companyId);

            final int res = stmt.executeUpdate();

            if (res > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.first()) {
                    obj.setId(rs.getLong(1));
                    ComputerDAO.LOGGER.info("successfully created computer : " + obj.toString());
                } else {
                    ComputerDAO.LOGGER.error("Computer created but no ID could be obtained.");
                    throw new DAOException("Computer created but no ID could be obtained.");
                }

            } else {
                ComputerDAO.LOGGER.warn("Could not create computer : " + obj.toString());
                throw new DAOException("Could not create computer.");
            }

        } catch (final SQLException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closeAll(con, stmt, rs);
        }

        return obj;
    }

    @Override
    public Computer update(final Computer obj) {

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(UPDATE);

            final Timestamp introduced = dateMapper.toTimestamp(obj.getIntroduced());

            final Timestamp discontinued = dateMapper.toTimestamp(obj.getDiscontinued());

            final Long companyId = obj.getCompany() == null ? null : obj.getCompany().getId();

            setParams(stmt, obj.getName(), introduced, discontinued, companyId, obj.getId());

            final int res = stmt.executeUpdate();

            if (res > 0) {
                ComputerDAO.LOGGER.info("Successfully updated computer : " + obj.toString());
            } else {
                ComputerDAO.LOGGER.warn("Could not update computer : " + obj.toString());
            }

        } catch (final SQLException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closeAll(con, stmt);
        }

        return obj;
    }

    @Override
    public void delete(final Computer obj) {

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(DELETE);

            setParams(stmt, obj.getId());

            final int res = stmt.executeUpdate();

            if (res > 0) {
                ComputerDAO.LOGGER.info("successfully deleted computer : " + obj.toString());
            } else {
                ComputerDAO.LOGGER.warn("couldn't delete computer : " + obj.toString());
            }

        } catch (final SQLException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closeAll(con, stmt);
        }
    }

    @Override
    public void deleteAll(final List<Long> objs) {

        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < objs.size(); i++) {
            builder.append("?,");
        }

        final String s = String.format(DELETE_LIST, builder.deleteCharAt(builder.length() - 1).toString());

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(s);

            stmt.executeUpdate();

        } catch (final SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(con, stmt);
        }
    }

    @Override
    public List<Computer> findAll() {

        final ArrayList<Computer> result = new ArrayList<>();

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(FIND_ALL);

            rs = stmt.executeQuery();

            while (rs.next()) {

                final Computer computer = mapper.map(rs);

                result.add(computer);

            }

            if (result.size() > 0) {
                ComputerDAO.LOGGER.info("successfully retrieved " + result.size() + " computer(s)");
            } else {
                ComputerDAO.LOGGER.warn("couldn't retrieve any computers");
            }

        } catch (final SQLException | MapperException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closeAll(con, stmt, rs);
        }

        return result;
    }

    @Override
    public List<Computer> findAll(final PageParameters page) {

        final ArrayList<Computer> result = new ArrayList<>();

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = con.prepareStatement(
                    String.format(FIND_ALL_LIMIT_ORDER, page.getOrder().toString(), page.getDirection().toString()));

            final String search = page.getSearch() == null ? "" : page.getSearch();

            setParams(stmt, "%" + search + "%", page.getSize() * page.getPageNumber(), page.getSize());

            rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(mapper.map(rs));
            }

            if (result.size() > 0) {
                ComputerDAO.LOGGER.info("successfully retrieved " + result.size() + " computer(s)");
            } else {
                ComputerDAO.LOGGER.warn("couldn't retrieve any computers");
            }

        } catch (final SQLException | MapperException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closeAll(con, stmt, rs);
        }

        return result;
    }

    public long count(final PageParameters page) {

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        long nb = 0;

        try {
            stmt = con.prepareStatement(COUNT_SEARCH);

            setParams(stmt, "%" + page.getSearch() + "%");

            rs = stmt.executeQuery();

            if (rs.first()) {
                nb = rs.getLong("nb");
            }

        } catch (final SQLException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closeAll(con, stmt, rs);
        }

        return nb;
    }

    @Override
    public long count() {

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        long nb = 0;

        try {
            stmt = con.prepareStatement(COUNT);

            rs = stmt.executeQuery();

            if (rs.first()) {
                nb = rs.getLong("nb");
            }

        } catch (final SQLException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closeAll(con, stmt, rs);
        }

        return nb;
    }
}
