package com.excilys.console;

import com.excilys.binding.mapper.impl.ComputerMapper;
import com.excilys.core.doublon.model.Rapport;
import com.excilys.core.model.Computer;
import com.excilys.service.service.impl.ComputerRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Doublon {

    @Autowired
    private ComputerRestService computerRestService;

    @Autowired
    private ComputerMapper computerMapper;

    /**
     * entry method for the CLI app.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {

        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        context.registerShutdownHook();

        Doublon p = context.getBean(Doublon.class);

        List<Computer> l = p.computerRestService.getList();

        Rapport r = new Rapport(p.computerMapper.toDTO(l));

    }
}
