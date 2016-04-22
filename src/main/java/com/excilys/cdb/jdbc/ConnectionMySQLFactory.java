package com.excilys.cdb.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.ComputerDAO;

/**
 * Singleton to connect to the database
 * 5.1.38
 * @author excilys
 */
public class ConnectionMySQLFactory {

    final static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
    
	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";

	private static String user = "admincdb";

	private static String passwd = "qwerty1234";

	private static volatile ConnectionMySQLFactory instance = null;
	
    private ConnectionMySQLFactory() {
        super();
    }
    
    public static ConnectionMySQLFactory getInstance() {
        
        if (instance == null) {
            synchronized (ConnectionMySQLFactory.class) {
                if (instance == null) {
                    instance = new ConnectionMySQLFactory();
                }
            }
        }

        return instance;
    }

	public Connection create() {

		Connection con = null;

		try {
			con = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}
	
}
