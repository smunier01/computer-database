package com.excilys.cdb.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.ComputerDAO;

/**
 * Singleton to connect to the database 5.1.38
 * 
 * @author excilys
 */
public class ConnectionMySQLFactory {

    final static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    private static String url;

    private static String user;

    private static String passwd;

    private static volatile ConnectionMySQLFactory instance = null;

    private ConnectionMySQLFactory() {
        Properties props = new Properties();
        InputStream in = null;

        try {

            in = ConnectionMySQLFactory.class.getClassLoader().getResourceAsStream("mysql.properties");
            props.load(in);

            url = props.getProperty("DB_URL");
            user = props.getProperty("DB_USERNAME");
            passwd = props.getProperty("DB_PASSWORD");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
