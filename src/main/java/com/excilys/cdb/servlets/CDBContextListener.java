package com.excilys.cdb.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class CDBContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CDBContextListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        LOGGER.debug("ServletContextListener destroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        LOGGER.debug("ServletContextListener started");
    }

}
