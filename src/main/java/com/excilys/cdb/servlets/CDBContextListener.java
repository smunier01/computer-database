package com.excilys.cdb.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.excilys.cdb.jdbc.ConnectionMySQLFactory;

@WebListener
public class CDBContextListener implements ServletContextListener {

    private final ConnectionMySQLFactory factory = ConnectionMySQLFactory.getInstance();

    @Override
    public void contextDestroyed(final ServletContextEvent arg0) {
        System.out.println("ServletContextListener destroyed");

        this.factory.close();

    }

    @Override
    public void contextInitialized(final ServletContextEvent arg0) {
        System.out.println("ServletContextListener started");
    }

}
