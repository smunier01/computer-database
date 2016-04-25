package com.excilys.cdb.jdbc;

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

        final Properties props = new Properties();
        InputStream in = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");

            in = ConnectionMySQLFactory.class.getClassLoader().getResourceAsStream("mysql.properties");
            props.load(in);

            ConnectionMySQLFactory.url = props.getProperty("DB_URL");
            ConnectionMySQLFactory.user = props.getProperty("DB_USERNAME");
            ConnectionMySQLFactory.passwd = props.getProperty("DB_PASSWORD");

        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ConnectionMySQLFactory getInstance() {

        if (ConnectionMySQLFactory.instance == null) {
            synchronized (ConnectionMySQLFactory.class) {
                if (ConnectionMySQLFactory.instance == null) {
                    ConnectionMySQLFactory.instance = new ConnectionMySQLFactory();
                }
            }
        }

        return ConnectionMySQLFactory.instance;
    }

    public Connection create() {

        Connection con = null;

        try {
            con = DriverManager.getConnection(ConnectionMySQLFactory.url, ConnectionMySQLFactory.user,
                    ConnectionMySQLFactory.passwd);
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

}
