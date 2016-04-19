package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * abstract class describing methods of a dao object
 * 
 * @author excilys
 *
 * @param <T>
 */
public abstract class DAO<T> {

	public abstract T find(Long id);

	public abstract T create(T obj);

	public abstract T update(T obj);

	public abstract void delete(T obj);

	public abstract ArrayList<T> findAll();
	
	public abstract ArrayList<T> findAll(int start, int nb);

	public void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
