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
 * Factory creating connection objects.
 *
 * @author excilys
 */
public class ConnectionMySQLFactory {

    static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private static String url;

    private static String user;

    private static String passwd;

    private static volatile ConnectionMySQLFactory instance = null;

    /**
     * constructor of the factory.
     *
     * this is where the driver is loaded and the property file is read
     */
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

        } catch (final IOException e) {
            // TODO not sure what to do here ?
            ConnectionMySQLFactory.LOGGER.error("could not read mysql.properties");
        } catch (final ClassNotFoundException e) {
            // TODO not sure what to do here ?
            ConnectionMySQLFactory.LOGGER.error("mysql jdbc driver could not be loaded");
        } finally {
            try {
                in.close();
            } catch (final IOException e) {
                ConnectionMySQLFactory.LOGGER.warn("could not close InputStream of the mysql property file");
            }
        }
    }

    /**
     * accessor for the factory singleton.
     *
     * @return unique instance of the factory
     */
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

    /**
     * factory main method, creates Connection objects.
     *
     * @return Connection object
     */
    public Connection create() {

        Connection con = null;

        try {
            con = DriverManager.getConnection(ConnectionMySQLFactory.url, ConnectionMySQLFactory.user,
                    ConnectionMySQLFactory.passwd);
        } catch (final SQLException e) {
            // TODO not sure what to do here
            ConnectionMySQLFactory.LOGGER.error("could not get Connection");
        }

        return con;
    }

}
