package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.cdb.jdbc.ConnectionMySQL;
import com.excilys.cdb.mapper.TimestampToLocalDate;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDAO extends DAO<Computer> {

	/**
	 * find a computer by its ID
	 */
	@Override
	public Computer find(Long id) {
		Computer computer = null;

		String sql = "select c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name from computer c left join company o on c.company_id=o.id WHERE c.id=?";

		Connection con = ConnectionMySQL.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement(sql);

			stmt.setLong(1, id);

			rs = stmt.executeQuery();

			if (rs.first()) {

				String name = rs.getString("name");
				
				LocalDate introduced = TimestampToLocalDate.convert(rs.getTimestamp("introduced"));
				LocalDate discontinued = TimestampToLocalDate.convert(rs.getTimestamp("discontinued"));
				
				Long companyId = rs.getLong("company_id");
				String companyName = rs.getString("company_name");

				computer = new Computer(id, name, introduced, discontinued, new Company(companyId, companyName));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(con, stmt, rs);
		}

		return computer;
	}

	/**
	 * create a new computer
	 */
	@Override
	public Computer create(Computer obj) {

		Connection con = ConnectionMySQL.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(
					"INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)");
			stmt.setString(1, obj.getName());
			
			LocalDate introduced = obj.getIntroduced();
			
			if (introduced.equals(LocalDate.MIN)) {
				stmt.setNull(2, java.sql.Types.TIMESTAMP);
			} else {
				stmt.setTimestamp(2, Timestamp.valueOf(introduced.atStartOfDay()));
			}
			
			LocalDate discontinued = obj.getDiscontinued();
			
			if (discontinued.equals(LocalDate.MIN)) {
				stmt.setNull(3, java.sql.Types.TIMESTAMP);
			} else {
				stmt.setTimestamp(3, Timestamp.valueOf(discontinued.atStartOfDay()));
			}

			Long companyId = obj.getCompany().getId();
			
			if (companyId > 0) {
				stmt.setLong(4, companyId);
			} else {
				stmt.setNull(4, java.sql.Types.BIGINT);
			}

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(con, stmt, null);
		}
		
		return obj;
	}

	/**
	 * modify values of a computer
	 */
	@Override
	public Computer update(Computer obj) {
		String sql = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=:?";

		Connection con = ConnectionMySQL.getConnection();
		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(sql);

			stmt.setString(1, obj.getName());
			
			LocalDate introduced = obj.getIntroduced();
			
			if (introduced.equals(LocalDate.MIN)) {
				stmt.setNull(2, java.sql.Types.TIMESTAMP);
			} else {
				stmt.setTimestamp(2, Timestamp.valueOf(introduced.atStartOfDay()));
			}
			
			LocalDate discontinued = obj.getDiscontinued();
			
			if (discontinued.equals(LocalDate.MIN)) {
				stmt.setNull(3, java.sql.Types.TIMESTAMP);
			} else {
				stmt.setTimestamp(3, Timestamp.valueOf(discontinued.atStartOfDay()));
			}

			Long companyId = obj.getCompany().getId();
			
			if (companyId > 0) {
				stmt.setLong(4, companyId);
			} else {
				stmt.setNull(4, java.sql.Types.BIGINT);
			}
			
			stmt.setLong(5, obj.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(con, stmt, null);
		}

		return obj;
	}

	/**
	 * delete a computer
	 */
	@Override
	public void delete(Computer obj) {
		String sql = "DELETE FROM computer WHERE id=?";

		Connection con = ConnectionMySQL.getConnection();
		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(sql);

			stmt.setLong(1, obj.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(con, stmt, null);
		}
	}

	/**
	 * return all computers
	 */
	@Override
	public ArrayList<Computer> findAll() {

		ArrayList<Computer> result = new ArrayList<>();

		String sql = "select c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name from computer c left join company o on c.company_id=o.id";

		Connection con = ConnectionMySQL.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement(sql);

			rs = stmt.executeQuery();

			while (rs.next()) {

				Long id = rs.getLong("id");
				String name = rs.getString("name");

				LocalDate introduced = TimestampToLocalDate.convert(rs.getTimestamp("introduced"));
				LocalDate discontinued = TimestampToLocalDate.convert(rs.getTimestamp("discontinued"));
				
				Long companyId = rs.getLong("company_id");
				String companyName = rs.getString("company_name");

				Company company = new Company(companyId, companyName);
				Computer computer = new Computer(id, name, introduced, discontinued, company);
				result.add(computer);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(con, stmt, rs);
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

			stmt.setInt(1, start);
			stmt.setInt(2, nb);
			
			rs = stmt.executeQuery();

			while (rs.next()) {

				Long id = rs.getLong("id");
				String name = rs.getString("name");

				LocalDate introduced = TimestampToLocalDate.convert(rs.getTimestamp("introduced"));
				LocalDate discontinued = TimestampToLocalDate.convert(rs.getTimestamp("discontinued"));
				
				Long companyId = rs.getLong("company_id");
				String companyName = rs.getString("company_name");

				Company company = new Company(companyId, companyName);
				Computer computer = new Computer(id, name, introduced, discontinued, company);
				result.add(computer);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(con, stmt, rs);
		}

		return result;
	}

}
