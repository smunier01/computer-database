package com.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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

		PreparedStatement stmt;

		try {
			stmt = this.connect.prepareStatement(sql);

			stmt.setLong(1, id);

			ResultSet rs = stmt.executeQuery();

			if (rs.first()) {

				String name = rs.getString("name");
				Date introduced = rs.getDate("introduced");
				Date discontinued = rs.getDate("discontinued");
				Long companyId = rs.getLong("company_id");
				String companyName = rs.getString("company_name");

				computer = new Computer(id, name, introduced, discontinued, new Company(companyId, companyName));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return computer;
	}

	/**
	 * create a new computer
	 */
	@Override
	public Computer create(Computer obj) {

		try {
			PreparedStatement prepare = this.connect.prepareStatement(
					"INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)");
			prepare.setString(1, obj.getName());
			prepare.setDate(2, new java.sql.Date(obj.getIntroduced().getTime()));
			prepare.setDate(3, new java.sql.Date(obj.getIntroduced().getTime()));

			Long companyId = obj.getCompany().getId();
			
			if (companyId > 0) {
				prepare.setLong(4, companyId);
			} else {
				prepare.setNull(4, java.sql.Types.BIGINT);
			}

			prepare.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

	/**
	 * modify values of a computer
	 */
	@Override
	public Computer update(Computer obj) {
		String sql = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=:?";

		PreparedStatement stmt;

		try {

			stmt = this.connect.prepareStatement(sql);

			stmt.setString(1, obj.getName());
			stmt.setDate(2, new java.sql.Date(obj.getIntroduced().getTime()));
			stmt.setDate(3, new java.sql.Date(obj.getIntroduced().getTime()));

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
		}

		return obj;
	}

	/**
	 * delete a computer
	 */
	@Override
	public void delete(Computer obj) {
		String sql = "DELETE FROM computer WHERE id=?";

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
	 * return all computers
	 */
	@Override
	public ArrayList<Computer> findAll() {

		ArrayList<Computer> result = new ArrayList<>();

		String sql = "select c.id, c.name, c.introduced, c.discontinued, c.company_id, o.name as company_name from computer c left join company o on c.company_id=o.id";

		PreparedStatement stmt;

		try {
			stmt = this.connect.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Long id = rs.getLong("id");
				String name = rs.getString("name");
				Date introduced = rs.getDate("introduced");
				Date discontinued = rs.getDate("discontinued");
				Long companyId = rs.getLong("company_id");
				String companyName = rs.getString("company_name");

				Company company = new Company(companyId, companyName);
				Computer computer = new Computer(id, name, introduced, discontinued, company);
				result.add(computer);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

}
