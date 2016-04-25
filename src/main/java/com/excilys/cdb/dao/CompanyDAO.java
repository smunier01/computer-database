package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.jdbc.ConnectionMySQLFactory;
import com.excilys.cdb.mapper.CompanyResultSetMapper;
import com.excilys.cdb.mapper.ComputerResultSetMapper;
import com.excilys.cdb.model.Company;

/**
 * @author excilys
 */
public class CompanyDAO extends DAO<Company> {

    private final static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    private static volatile CompanyDAO instance = null;

    private final CompanyResultSetMapper mapper = CompanyResultSetMapper.getInstance();

    private final ConnectionMySQLFactory connectionFactory = ConnectionMySQLFactory.getInstance();

    private CompanyDAO() {
        super();
    }

    public static CompanyDAO getInstance() {

        if (CompanyDAO.instance == null) {
            synchronized (ComputerResultSetMapper.class) {
                if (CompanyDAO.instance == null) {
                    CompanyDAO.instance = new CompanyDAO();
                }
            }
        }

        return CompanyDAO.instance;
    }

    @Override
    public Company find(final Long id) throws DAOException {
        Company company = null;

        final String sql = "SELECT id, name FROM company WHERE id=?";

        PreparedStatement stmt = null;
        final Connection con = this.connectionFactory.create();
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql);

            this.setParams(stmt, id);

            rs = stmt.executeQuery();

            if (rs.first()) {

                final String name = rs.getString("name");

                company = new Company(id, name);

                CompanyDAO.logger.info("successfully found company of id : " + id);

            } else {
                CompanyDAO.logger.warn("couldn't find company of id : " + id);
            }

        } catch (final SQLException e) {
            CompanyDAO.logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return company;
    }

    @Override
    public Company create(final Company obj) throws DAOException {

        final String sql = "INSERT INTO company (name) VALUES (?)";
        final Connection con = this.connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            this.setParams(stmt, obj.getName());

            final int res = stmt.executeUpdate();

            if (res > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    obj.setId(rs.getLong(1));
                    CompanyDAO.logger.info("successfully created company : " + obj.toString());
                } else {
                    CompanyDAO.logger.error("Company created but no ID could be obtained.");
                    throw new DAOException("Company created but no ID could be obtained.");
                }
            } else {
                CompanyDAO.logger.warn("couldn't create company : " + obj.getName());
            }

        } catch (final SQLException e) {
            CompanyDAO.logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt);
        }

        return obj;
    }

    @Override
    public Company update(final Company obj) throws DAOException {
        final String sql = "UPDATE company SET name=? WHERE id=?";

        final Connection con = this.connectionFactory.create();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(sql);

            this.setParams(stmt, obj.getName(), obj.getId());

            final int res = stmt.executeUpdate();

            if (res > 0) {
                CompanyDAO.logger.info("succefully updated company : " + obj.getId());
            } else {
                CompanyDAO.logger.warn("couldn't update company : " + obj.getId());
            }

        } catch (final SQLException e) {
            CompanyDAO.logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt);
        }

        return obj;
    }

    @Override
    public void delete(final Company obj) throws DAOException {
        final String sql = "DELETE FROM company WHERE id=?";

        final Connection con = this.connectionFactory.create();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(sql);

            this.setParams(stmt, obj.getId());

            final int res = stmt.executeUpdate();

            if (res > 0) {
                CompanyDAO.logger.info("succefully deleted company : " + obj.getName());
            } else {
                CompanyDAO.logger.warn("couldn't delete company : " + obj.getName());
            }

        } catch (final SQLException e) {
            CompanyDAO.logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt);
        }
    }

    @Override
    public List<Company> findAll() throws DAOException {

        final List<Company> result = new ArrayList<>();

        final String sql = "SELECT id, name FROM company";

        final Connection con = this.connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {

                final Company company = this.mapper.map(rs);

                result.add(company);

            }

            if (result.size() > 0) {
                CompanyDAO.logger.info("successfully retrieved " + result.size() + " companies");
            } else {
                CompanyDAO.logger.warn("couldn't retrieve any companies");
            }

        } catch (final SQLException e) {
            CompanyDAO.logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return result;
    }

    @Override
    public List<Company> findAll(final int start, final int nb) throws DAOException {
        final ArrayList<Company> result = new ArrayList<>();

        final String sql = "SELECT id, name FROM company LIMIT ?,?";

        final Connection con = this.connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql);

            this.setParams(stmt, start, nb);

            rs = stmt.executeQuery();

            while (rs.next()) {

                final Company company = this.mapper.map(rs);

                result.add(company);
            }

            if (result.size() > 0) {
                CompanyDAO.logger.info("successfully retrieved " + result.size() + " companies");
            } else {
                CompanyDAO.logger.warn("couldn't retrieve any companies");
            }

        } catch (final SQLException e) {
            CompanyDAO.logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return result;
    }

    @Override
    public long count() throws DAOException {
        final String sql = "SELECT count(id) as nb FROM company";

        final Connection con = this.connectionFactory.create();
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
            ComputerDAO.logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return nb;
    }
}
