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
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Factory creating connection objects.
 *
 * @author excilys
 */
public enum ConnectionMySQLFactory {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private String url;

    private String user;

    private String passwd;

    private HikariDataSource ds;

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

            url = props.getProperty("DB_URL");
            user = props.getProperty("DB_USERNAME");
            passwd = props.getProperty("DB_PASSWORD");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(passwd);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            ds = new HikariDataSource(config);

        } catch (final IOException e) {
            // TODO not sure what to do here ?
            LOGGER.error("could not read mysql.properties");
        } catch (final ClassNotFoundException e) {
            // TODO not sure what to do here ?
            LOGGER.error("mysql jdbc driver could not be loaded");
        } finally {
            try {
                in.close();
            } catch (final IOException e) {
                LOGGER.warn("could not close InputStream of the mysql property file");
            }
        }
    }

    /**
     * accessor for the factory singleton.
     *
     * @return unique instance of the factory
     */
    public static ConnectionMySQLFactory getInstance() {
        return INSTANCE;
    }

    /**
     * factory main method, creates Connection objects.
     *
     * @return Connection object
     */
    public Connection create() {

        Connection con = null;

        try {
            // con = DriverManager.getConnection(url, user, passwd);
            con = ds.getConnection();
        } catch (final SQLException e) {
            // TODO not sure what to do here
            LOGGER.error("could not get Connection");
        }

        return con;
    }

}
