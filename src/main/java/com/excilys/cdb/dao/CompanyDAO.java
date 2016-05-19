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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.excilys.cdb.jdbc.ConnectionMySQLFactory;
import com.excilys.cdb.jdbc.ITransactionManager;
import com.excilys.cdb.jdbc.TransactionManager;
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
@Component
public class CompanyDAO implements DAO<Company> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    private final CompanyMapper companyMapper = CompanyMapper.getInstance();

    private final ConnectionMySQLFactory connectionFactory = ConnectionMySQLFactory.getInstance();

    private final ITransactionManager transactionManager = TransactionManager.getInstance();

    private static final String FIND_BY_ID = "SELECT id, name FROM company WHERE id=?";

    private static final String FIND_ALL = "SELECT id, name FROM company";

    private static final String FIND_ALL_LIMIT = "SELECT id, name FROM company LIMIT ?,?";

    private static final String INSERT = "INSERT INTO company (name) VALUES (?)";

    private static final String UPDATE = "UPDATE company SET name=? WHERE id=?";

    private static final String DELETE = "DELETE FROM company WHERE id=?";

    private static final String COUNT = "SELECT count(id) as nb FROM company";

    @Override
    public Company find(Long id) {

        Company company = null;

        PreparedStatement stmt = null;
        final Connection con = this.connectionFactory.create();
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(FIND_BY_ID);

            this.setParams(stmt, id);

            rs = stmt.executeQuery();

            if (rs.first()) {

                final String name = rs.getString("name");

                company = new Company(id, name);

                CompanyDAO.LOGGER.info("successfully found company of id : " + id);

            } else {
                CompanyDAO.LOGGER.warn("couldn't find company of id : " + id);
            }

        } catch (SQLException e) {
            CompanyDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return company;
    }

    @Override
    public Company create(Company obj) {

        Connection con = this.connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            this.setParams(stmt, obj.getName());

            int res = stmt.executeUpdate();

            if (res > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    obj.setId(rs.getLong(1));
                    CompanyDAO.LOGGER.info("successfully created company : " + obj.toString());
                } else {
                    CompanyDAO.LOGGER.error("Company created but no ID could be obtained.");
                    throw new DAOException("Company created but no ID could be obtained.");
                }
            } else {
                CompanyDAO.LOGGER.warn("couldn't create company : " + obj.getName());
            }

        } catch (SQLException e) {
            CompanyDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt);
        }

        return obj;
    }

    @Override
    public Company update(Company obj) {

        Connection con = this.connectionFactory.create();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(UPDATE);

            this.setParams(stmt, obj.getName(), obj.getId());

            final int res = stmt.executeUpdate();

            if (res > 0) {
                CompanyDAO.LOGGER.info("succefully updated company : " + obj.getId());
            } else {
                CompanyDAO.LOGGER.warn("couldn't update company : " + obj.getId());
            }

        } catch (SQLException e) {
            CompanyDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt);
        }

        return obj;
    }

    @Override
    public void delete(Company obj) {

        Connection con = this.transactionManager.get();

        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(DELETE);

            this.setParams(stmt, obj.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {

            CompanyDAO.LOGGER.error(e.getMessage());

            throw new DAOException(e);
        } finally {
            this.closeAll(stmt);
        }
    }

    @Override
    public void deleteAll(List<Long> objs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Company> findAll() {

        List<Company> result = new ArrayList<>();

        Connection con = this.connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(FIND_ALL);

            rs = stmt.executeQuery();

            while (rs.next()) {

                final Company company = this.companyMapper.map(rs);

                result.add(company);

            }

            if (result.size() > 0) {
                CompanyDAO.LOGGER.info("successfully retrieved " + result.size() + " companies");
            } else {
                CompanyDAO.LOGGER.warn("couldn't retrieve any companies");
            }

        } catch (SQLException e) {
            CompanyDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return result;
    }

    @Override
    public List<Company> findAll(PageParameters page) {
        ArrayList<Company> result = new ArrayList<>();

        Connection con = this.connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(FIND_ALL_LIMIT);

            this.setParams(stmt, page.getSize() * page.getPageNumber(), page.getSize());

            rs = stmt.executeQuery();

            while (rs.next()) {

                Company company = this.companyMapper.map(rs);

                result.add(company);
            }

            if (result.size() > 0) {
                CompanyDAO.LOGGER.info("successfully retrieved " + result.size() + " companies");
            } else {
                CompanyDAO.LOGGER.warn("couldn't retrieve any companies");
            }

        } catch (SQLException e) {
            CompanyDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return result;
    }

    @Override
    public long count() throws DAOException {

        Connection con = this.connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        long nb = 0;

        try {
            stmt = con.prepareStatement(COUNT);

            rs = stmt.executeQuery();

            if (rs.first()) {
                nb = rs.getLong("nb");
            }

        } catch (SQLException e) {
            CompanyDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return nb;
    }

}
