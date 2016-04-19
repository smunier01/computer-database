package com.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

		PreparedStatement stmt;

		try {
			stmt = this.connect.prepareStatement(sql);

			stmt.setLong(1, id);

			ResultSet rs = stmt.executeQuery();

			if (rs.first()) {

				String name = rs.getString("name");

				company = new Company(id, name);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return company;
	}
	
	/**
	 * create a new company
	 */
	@Override
	public Company create(Company obj) {

		try {
			PreparedStatement prepare = this.connect.prepareStatement(
					"INSERT INTO company (name) VALUES(?)");
			prepare.setString(1, obj.getName());

			prepare.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

	/**
	 * modify values of a company
	 */
	@Override
	public Company update(Company obj) {
		String sql = "UPDATE company SET name=? WHERE id=:?";

		PreparedStatement stmt;

		try {

			stmt = this.connect.prepareStatement(sql);

			stmt.setString(1, obj.getName());
			stmt.setLong(2, obj.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	/**
	 * delete a company
	 */
	@Override
	public void delete(Company obj) {
		String sql = "DELETE FROM company WHERE id=?";

		PreparedStatement stmt;

		try {

			stmt = this.connect.prepareStatement(sql);

			stmt.setLong(1, obj.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * return all companies
	 */
	@Override
	public ArrayList<Company> findAll() {

		ArrayList<Company> result = new ArrayList<>();

		String sql = "SELECT id, name FROM company";

		PreparedStatement stmt;

		try {
			stmt = this.connect.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Long id = rs.getLong("id");
				String name = rs.getString("name");

				Company company = new Company(id, name);

				result.add(company);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

}
