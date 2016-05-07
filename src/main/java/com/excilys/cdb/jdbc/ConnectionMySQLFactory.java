package com.excilys.cdb.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.ComputerDAO;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Factory creating mysql connections using Hikari for the connection pool.
 *
 * @author simon
 */
public enum ConnectionMySQLFactory {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private final String PROPERTY_FILE = "mysql.properties";

    private String url;

    private String user;

    private String passwd;

    private HikariDataSource ds;

    /**
     * accessor for the factory singleton.
     *
     * @return unique instance of the factory
     */
    public static ConnectionMySQLFactory getInstance() {
        return INSTANCE;
    }

    /**
     * constructor of the factory.
     *
     * this is where the driver is loaded and the property file is read
     */
    private ConnectionMySQLFactory() {

        Properties props = new Properties();
        InputStream in = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");

            in = ConnectionMySQLFactory.class.getClassLoader().getResourceAsStream(this.PROPERTY_FILE);
            props.load(in);

            this.url = props.getProperty("DB_URL");
            this.user = props.getProperty("DB_USERNAME");
            this.passwd = props.getProperty("DB_PASSWORD");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(this.url);
            config.setUsername(this.user);
            config.setPassword(this.passwd);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.setMaximumPoolSize(400);

            this.ds = new HikariDataSource(config);

        } catch (IOException e) {
            // TODO not sure what to do here ?
            this.LOGGER.error("could not read mysql.properties");
        } catch (ClassNotFoundException e) {
            // TODO not sure what to do here ?
            this.LOGGER.error("mysql jdbc driver could not be loaded");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                this.LOGGER.warn("could not close InputStream of the mysql property file");
            }
        }
    }

    /**
     * Factory main method, creates Connection objects.
     *
     * @return Connection object
     */
    public Connection create() {

        Connection con = null;

        try {
            // con = DriverManager.getConnection(url, user, passwd);
            con = this.ds.getConnection();
        } catch (SQLException e) {
            // TODO not sure what to do here
            this.LOGGER.error("could not get Connection");
        }

        return con;
    }

    /**
     * close the connection pool.
     *
     * this should only be called when the application is shuting down.
     */
    public void close() {
        this.ds.close();
    }

}
