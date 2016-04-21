package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.jdbc.ConnectionMySQL;
import com.excilys.cdb.mapper.ComputerResultSetMapper;
import com.excilys.cdb.mapper.LocalDateToTimestamp;
import com.excilys.cdb.mapper.TimestampToLocalDate;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDAO extends DAO<Computer> {

    final static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    private ComputerResultSetMapper mapper = ComputerResultSetMapper.getInstance();

    private static volatile ComputerDAO instance = null;

    private ComputerDAO() {
        super();
    }
    
    public static ComputerDAO getInstance() {
        
        if (instance == null) {
            synchronized (ComputerResultSetMapper.class) {
                if (instance == null) {
                    instance = new ComputerDAO();
                }
            }
        }

        return instance;
    }
    
    @Override
    public Computer find(Long id) {
        Computer computer = null;

        String sql = "SELECT c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name FROM computer c LEFT JOIN company o on c.company_id=o.id WHERE c.id=?";

        Connection con = ConnectionMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql);

            // stmt.setLong(1, id);
            setParams(stmt, id);

            rs = stmt.executeQuery();

            if (rs.first()) {
                String name = rs.getString("name");

                LocalDate introduced = TimestampToLocalDate.convert(rs.getTimestamp("introduced"));
                LocalDate discontinued = TimestampToLocalDate.convert(rs.getTimestamp("discontinued"));

                Long companyId = rs.getLong("company_id");
                String companyName = rs.getString("company_name");

                computer = new Computer(id, name, introduced, discontinued, new Company(companyId, companyName));

                logger.info("succefully found computer of id : " + id);
            } else {
                logger.warn("couldn't find computer of id : " + id);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return computer;
    }

    @Override
    public Computer create(Computer obj) {

        String sql = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
        Connection con = ConnectionMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(sql);

            Timestamp introduced = LocalDateToTimestamp.convert(obj.getIntroduced());

            Timestamp discontinued = LocalDateToTimestamp.convert(obj.getDiscontinued());

            this.setParams(stmt, obj.getName(), introduced, discontinued, obj.getCompany().getId());

            int res = stmt.executeUpdate();

            if (res > 0) {
                logger.info("successfully created computer : " + obj.toString());
            } else {
                logger.warn("couldn't create computer : " + obj.toString());
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            this.closeAll(con, stmt);
        }

        return obj;
    }

    @Override
    public Computer update(Computer obj) {
        String sql = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=:?";

        Connection con = ConnectionMySQL.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(sql);

            Timestamp introduced = LocalDateToTimestamp.convert(obj.getIntroduced());

            Timestamp discontinued = LocalDateToTimestamp.convert(obj.getDiscontinued());

            setParams(stmt, obj.getName(), introduced, discontinued, obj.getCompany().getId(), obj.getId());

            int res = stmt.executeUpdate();

            if (res > 0) {
                logger.info("successfully updated computer : " + obj.toString());
            } else {
                logger.warn("couldn't update computer : " + obj.toString());
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            this.closeAll(con, stmt);
        }

        return obj;
    }

    @Override
    public void delete(Computer obj) {
        String sql = "DELETE FROM computer WHERE id=?";

        Connection con = ConnectionMySQL.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(sql);

            this.setParams(stmt, obj.getId());

            int res = stmt.executeUpdate();

            if (res > 0) {
                logger.info("successfully deleted computer : " + obj.toString());
            } else {
                logger.warn("couldn't delete computer : " + obj.toString());
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            this.closeAll(con, stmt);
        }
    }

    @Override
    public ArrayList<Computer> findAll() {

        ArrayList<Computer> result = new ArrayList<>();

        String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name FROM computer c LEFT JOIN company o ON c.company_id=o.id";

        Connection con = ConnectionMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {

                Computer computer = mapper.map(rs);
                
                result.add(computer);

            }

            if (result.size() > 0) {
                logger.info("successfully retrieved " + result.size() + " computer(s)");
            } else {
                logger.warn("couldn't retrieve any computers");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return result;
    }

    @Override
    public ArrayList<Computer> findAll(int start, int nb) {

        ArrayList<Computer> result = new ArrayList<>();

        String sql = "select c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name from computer c left join company o on c.company_id=o.id LIMIT ?,?";

        Connection con = ConnectionMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(sql);

            this.setParams(stmt, start, nb);

            rs = stmt.executeQuery();

            while (rs.next()) {

                Computer computer = mapper.map(rs);

                result.add(computer);

            }

            if (result.size() > 0) {
                logger.info("successfully retrieved " + result.size() + " computer(s)");
            } else {
                logger.warn("couldn't retrieve any computers");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            this.closeAll(con, stmt, rs);
        }

        return result;
    }

}
