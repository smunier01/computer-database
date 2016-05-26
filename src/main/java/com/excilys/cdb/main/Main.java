package com.excilys.cdb.main;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {

    @Autowired
    private Menu menu;

    /**
     * entry method for the CLI app.
     *
     * @param args
     *            arguments
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        context.registerShutdownHook();

        Main p = context.getBean(Main.class);

        p.start(args);

    }

    private void start(String[] args) {

        while (this.menu.pick()) {
            System.out.println("pick menu :");
        }

        this.menu.close();
    }

}
