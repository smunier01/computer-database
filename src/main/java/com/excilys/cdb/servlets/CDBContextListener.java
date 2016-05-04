package com.excilys.cdb.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.jdbc.ConnectionMySQLFactory;

@WebListener
public class CDBContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CDBContextListener.class);

    private final ConnectionMySQLFactory factory = ConnectionMySQLFactory.getInstance();

    @Override
    public void contextDestroyed(final ServletContextEvent arg0) {
        LOGGER.debug("ServletContextListener destroyed");

        this.factory.close();

    }

    @Override
    public void contextInitialized(final ServletContextEvent arg0) {
        LOGGER.debug("ServletContextListener started");
    }

}
