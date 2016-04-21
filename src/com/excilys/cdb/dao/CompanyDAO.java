package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.jdbc.ConnectionMySQL;
import com.excilys.cdb.mapper.CompanyResultSetMapper;
import com.excilys.cdb.mapper.ComputerResultSetMapper;
import com.excilys.cdb.model.Company;

/**
 * @author excilys
 */
public class CompanyDAO extends DAO<Company> {

    private final static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    private CompanyResultSetMapper mapper = CompanyResultSetMapper.getInstance();

    private static volatile CompanyDAO instance = null;

    private CompanyDAO() {
        super();
    }
    
    public static CompanyDAO getInstance() {
        
        if (instance == null) {
            synchronized (ComputerResultSetMapper.class) {
                if (instance == null) {
                    instance = new CompanyDAO();
                }
            }
        }

        return instance;
    }

    @Override
    public Company find(Long id) {
        Company company = null;

        String sql = "SELECT id, name FROM company WHERE id=?";

        PreparedStatement stmt = null;
        Connection con = ConnectionMySQL.getConnection();
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql);

            this.setParams(stmt, id);

            rs = stmt.executeQuery();

            if (rs.first()) {

                String name = rs.getString("name");

                company = new Company(id, name);

                logger.info("successfully found company of id : " + id);

            } else {
                logger.warn("couldn't find company of id : " + id);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return company;
    }

    @Override
    public Company create(Company obj) {

        Connection con = ConnectionMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO company (name) VALUES(?)");

            this.setParams(stmt, obj.getName());

            int res = stmt.executeUpdate();

            if (res > 0) {
                logger.info("succefully created company : " + obj.getName());
            } else {
                logger.info("couldn't create company : " + obj.getName());
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            this.closeAll(con, stmt);
        }

        return obj;
    }

    @Override
    public Company update(Company obj) {
        String sql = "UPDATE company SET name=? WHERE id=:?";

        Connection con = ConnectionMySQL.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(sql);

            this.setParams(stmt, obj.getName(), obj.getId());

            int res = stmt.executeUpdate();

            if (res > 0) {
                logger.info("succefully updated company : " + obj.getId());
            } else {
                logger.info("couldn't update company : " + obj.getId());
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            this.closeAll(con, stmt);
        }

        return obj;
    }

    @Override
    public void delete(Company obj) {
        String sql = "DELETE FROM company WHERE id=?";

        Connection con = ConnectionMySQL.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(sql);

            this.setParams(stmt, obj.getId());

            int res = stmt.executeUpdate();

            if (res > 0) {
                logger.info("succefully deleted company : " + obj.getName());
            } else {
                logger.warn("couldn't delete company : " + obj.getName());
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            this.closeAll(con, stmt);
        }
    }

    @Override
    public ArrayList<Company> findAll() {

        ArrayList<Company> result = new ArrayList<>();

        String sql = "SELECT id, name FROM company";

        Connection con = ConnectionMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {

                Company company = mapper.map(rs);

                result.add(company);

            }

            if (result.size() > 0) {
                logger.info("successfully retrieved " + result.size() + " companies");
            } else {
                logger.warn("couldn't retrieve any companies");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return result;
    }

    @Override
    public ArrayList<Company> findAll(int start, int nb) {
        ArrayList<Company> result = new ArrayList<>();

        String sql = "SELECT id, name FROM company LIMIT ?,?";

        Connection con = ConnectionMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql);

            this.setParams(stmt, start, nb);

            rs = stmt.executeQuery();

            while (rs.next()) {

                Company company = mapper.map(rs);

                result.add(company);
            }

            if (result.size() > 0) {
                logger.info("successfully retrieved " + result.size() + " companies");
            } else {
                logger.warn("couldn't retrieve any companies");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return result;
    }

}
