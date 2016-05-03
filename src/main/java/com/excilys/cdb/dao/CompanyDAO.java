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

import com.excilys.cdb.jdbc.ConnectionMySQLFactory;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.util.PageParameters;

/**
 * CompanyDAO class.
 *
 * @author excilys
 */
public class CompanyDAO extends DAO<Company> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    private static volatile CompanyDAO instance = null;

    private final CompanyMapper companyMapper = CompanyMapper.getInstance();

    private final ConnectionMySQLFactory connectionFactory = ConnectionMySQLFactory.getInstance();

    private static final String FIND_BY_ID = "SELECT id, name FROM company WHERE id=?";

    private static final String FIND_ALL = "SELECT id, name FROM company";

    private static final String FIND_ALL_LIMIT = "SELECT id, name FROM company LIMIT ?,?";

    private static final String INSERT = "INSERT INTO company (name) VALUES (?)";

    private static final String UPDATE = "UPDATE company SET name=? WHERE id=?";

    private static final String DELETE = "DELETE FROM company WHERE id=?";

    private static final String COUNT = "SELECT count(id) as nb FROM company";

    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE company_id = ?";

    /**
     * default constructor for the singleton.
     */
    private CompanyDAO() {
        super();
    }

    /**
     * getInstance accessor for the singleton.
     *
     * @return the unique instance of CompanyDAO
     */
    public static CompanyDAO getInstance() {

        if (instance == null) {
            synchronized (CompanyDAO.class) {
                if (instance == null) {
                    instance = new CompanyDAO();
                }
            }
        }

        return instance;
    }

    /**
     * get a company by its id.
     */
    @Override
    public Company find(final Long id) {

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

        } catch (final SQLException e) {
            CompanyDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return company;
    }

    @Override
    public Company create(final Company obj) {

        final Connection con = this.connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            this.setParams(stmt, obj.getName());

            final int res = stmt.executeUpdate();

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

        } catch (final SQLException e) {
            CompanyDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt);
        }

        return obj;
    }

    /**
     * update an existing company.
     *
     * @return instance of the company updated
     */
    @Override
    public Company update(final Company obj) {

        final Connection con = this.connectionFactory.create();
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

        } catch (final SQLException e) {
            CompanyDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt);
        }

        return obj;
    }

    /**
     * delete a company.
     */
    @Override
    public void delete(final Company obj) {

        final Connection con = this.connectionFactory.create();

        PreparedStatement stmtDeleteComputers = null;
        PreparedStatement stmt = null;

        try {

            con.setAutoCommit(false);

            stmt = con.prepareStatement(DELETE);
            stmtDeleteComputers = con.prepareStatement(DELETE_COMPUTER);

            this.setParams(stmt, obj.getId());
            this.setParams(stmtDeleteComputers, obj.getId());

            stmt.executeUpdate();
            stmtDeleteComputers.executeUpdate();

            con.commit();

        } catch (final SQLException e) {

            CompanyDAO.LOGGER.error(e.getMessage());

            try {
                con.rollback();
            } catch (final SQLException e1) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e);
            }
            throw new DAOException(e);
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (final SQLException e) {
                LOGGER.error(e.getMessage());
            }
            this.closeAll(con, stmt, stmtDeleteComputers);
        }
    }

    @Override
    public void deleteAll(final List<Long> objs) {

    }

    /**
     * get all the companies.
     *
     * @return list of company
     */
    @Override
    public List<Company> findAll() {

        final List<Company> result = new ArrayList<>();

        final Connection con = this.connectionFactory.create();
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

        } catch (final SQLException e) {
            CompanyDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return result;
    }

    /**
     * get companies using an offset and a limit.
     *
     * @return list of company
     */
    @Override
    public List<Company> findAll(final PageParameters page) {
        final ArrayList<Company> result = new ArrayList<>();

        final Connection con = this.connectionFactory.create();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(FIND_ALL_LIMIT);

            this.setParams(stmt, page.getSize() * page.getPageNumber(), page.getSize());

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

        } catch (final SQLException e) {
            CompanyDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return result;
    }

    /**
     * @return number of companies
     */
    @Override
    public long count() throws DAOException {

        final Connection con = this.connectionFactory.create();
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
            CompanyDAO.LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return nb;
    }

}
