package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.cdb.jdbc.ConnectionMySQL;
import com.excilys.cdb.model.Company;

/**
 * 
 * @author excilys
 */
public class CompanyDAO extends DAO<Company> {

	/**
	 * find a company by its ID
	 */
	@Override
	public Company find(Long id) {
		Company company = null;

		String sql = "select id, name from company where id=?";

		PreparedStatement stmt = null;
		Connection con = ConnectionMySQL.getConnection();
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);

			stmt.setLong(1, id);

			rs = stmt.executeQuery();

			if (rs.first()) {

				String name = rs.getString("name");

				company = new Company(id, name);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(con, stmt, rs);
		}

		return company;
	}
	
	/**
	 * create a new company
	 */
	@Override
	public Company create(Company obj) {

		Connection con = ConnectionMySQL.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(
					"INSERT INTO company (name) VALUES(?)");
			stmt.setString(1, obj.getName());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(con, stmt, null);
		}
		
		return obj;
	}

	/**
	 * modify values of a company
	 */
	@Override
	public Company update(Company obj) {
		String sql = "UPDATE company SET name=? WHERE id=:?";

		Connection con = ConnectionMySQL.getConnection();
		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(sql);

			stmt.setString(1, obj.getName());
			stmt.setLong(2, obj.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(con, stmt, null);
		}

		return obj;
	}
	
	/**
	 * delete a company
	 */
	@Override
	public void delete(Company obj) {
		String sql = "DELETE FROM company WHERE id=?";

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
	 * return all companies
	 */
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

				Long id = rs.getLong("id");
				String name = rs.getString("name");

				Company company = new Company(id, name);

				result.add(company);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(con, stmt, rs);
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
			stmt.setInt(1, start);
			stmt.setInt(2, nb);
			
			rs = stmt.executeQuery();

			while (rs.next()) {

				Long id = rs.getLong("id");
				String name = rs.getString("name");

				Company company = new Company(id, name);

				result.add(company);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(con, stmt, rs);
		}

		return result;
	}

}
