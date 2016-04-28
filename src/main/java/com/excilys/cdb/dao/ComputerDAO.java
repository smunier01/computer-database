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
import com.excilys.cdb.mapper.LocalDateToTimestamp;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.util.PageParameters;

/**
 * ComputerDAO class.
 *
 * @author excilys
 *
 */
public class ComputerDAO extends DAO<Computer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private final ComputerMapper mapper = ComputerMapper.getInstance();

    private final ConnectionMySQLFactory connectionFactory = ConnectionMySQLFactory.getInstance();

    private static volatile ComputerDAO instance = null;

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
    public Computer find(final Long id) throws DAOException {

        Computer computer = null;

        final String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name FROM computer c LEFT JOIN company o on c.company_id=o.id WHERE c.id=?";

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql);

            setParams(stmt, id);

            rs = stmt.executeQuery();

            if (rs.first()) {

                computer = mapper.map(rs);

                ComputerDAO.LOGGER.info("succefully found computer of id : " + id);
            } else {
                ComputerDAO.LOGGER.warn("couldn't find computer of id : " + id);
            }

        } catch (final SQLException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closeAll(con, stmt, rs);
        }

        return computer;
    }

    @Override
    public Computer create(final Computer obj) throws DAOException {

        final String sql = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            final Timestamp introduced = LocalDateToTimestamp.convert(obj.getIntroduced());

            final Timestamp discontinued = LocalDateToTimestamp.convert(obj.getDiscontinued());

            setParams(stmt, obj.getName(), introduced, discontinued, obj.getCompany().getId());

            final int res = stmt.executeUpdate();

            if (res > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    obj.setId(rs.getLong(1));
                    ComputerDAO.LOGGER.info("successfully created computer : " + obj.toString());
                } else {
                    ComputerDAO.LOGGER.error("Computer created but no ID could be obtained.");
                    throw new DAOException("Computer created but no ID could be obtained.");
                }

            } else {
                ComputerDAO.LOGGER.warn("couldn't create computer : " + obj.toString());
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
    public Computer update(final Computer obj) throws DAOException {
        final String sql = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(sql);

            final Timestamp introduced = LocalDateToTimestamp.convert(obj.getIntroduced());

            final Timestamp discontinued = LocalDateToTimestamp.convert(obj.getDiscontinued());

            setParams(stmt, obj.getName(), introduced, discontinued, obj.getCompany().getId(), obj.getId());

            final int res = stmt.executeUpdate();

            if (res > 0) {
                ComputerDAO.LOGGER.info("successfully updated computer : " + obj.toString());
            } else {
                ComputerDAO.LOGGER.warn("couldn't update computer : " + obj.toString());
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
    public void delete(final Computer obj) throws DAOException {
        final String sql = "DELETE FROM computer WHERE id=?";

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(sql);

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
    public List<Computer> findAll() throws DAOException {

        final ArrayList<Computer> result = new ArrayList<>();

        final String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name FROM computer c LEFT JOIN company o ON c.company_id=o.id";

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql);

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

        } catch (final SQLException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closeAll(con, stmt, rs);
        }

        return result;
    }

    @Override
    public List<Computer> findAll(final PageParameters page) throws DAOException {

        final ArrayList<Computer> result = new ArrayList<>();

        final String sql = "select c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name from computer c left join company o on c.company_id=o.id LIMIT ?,?";

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql);

            setParams(stmt, page.getSize() * (page.getPageNumber() + 1), page.getSize());

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

        } catch (final SQLException e) {
            ComputerDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closeAll(con, stmt, rs);
        }

        return result;
    }

    @Override
    public long count() throws DAOException {
        final String sql = "SELECT count(id) as nb FROM computer";

        final Connection con = connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        long nb = 0;

        try {
            stmt = con.prepareStatement(sql);

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
