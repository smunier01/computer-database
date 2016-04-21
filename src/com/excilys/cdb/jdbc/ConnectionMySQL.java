package com.excilys.cdb.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton to connect to the database
 * 
 * @author excilys
 */
public class ConnectionMySQL {

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";

	private static String user = "admincdb";

	private static String passwd = "qwerty1234";

	private ConnectionMySQL() {
	}

	public static Connection getConnection() {

		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return con;
	}
	
}
