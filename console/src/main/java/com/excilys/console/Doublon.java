package com.excilys.console;

import com.excilys.binding.mapper.impl.ComputerMapper;
import com.excilys.core.doublon.model.Conflict;
import com.excilys.core.doublon.model.Rapport;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.doublon.service.DoublonService;
import com.excilys.service.service.impl.ComputerRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Doublon {

    @Autowired
    private ComputerRestService computerRestService;

    @Autowired
    private ComputerMapper computerMapper;

    @Autowired
    private DoublonService doublonService;

    /**
     * entry method for the CLI app.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {

        // TODO: class use to test the DoublonService
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        context.registerShutdownHook();

        Doublon d = context.getBean(Doublon.class);

        List<ComputerDTO> computers = new ArrayList<>();
        computers.add(new ComputerDTO("-1", "voiture", "introduced", "discontinued", "-1", "Apple"));
        computers.add(new ComputerDTO("-1", "plume", "introduced", "discontinued", "-1", "Apple Co."));
        computers.add(new ComputerDTO("-1", "MacBook Pro 154 inchh", "introduced", "discontinued", "-1", "Apple Inc."));

        Rapport rapport = d.doublonService.getRapport(computers);

        System.out.println("to import : ");
        for (ComputerDTO c : rapport.getToImport()) {
            System.out.println("-> : " + c.toString());
        }
        System.out.println("to check");
        for (Conflict c : rapport.getToCheck()) {
            System.out.println("-> : " + c.getOrigin().toString());
            for (ComputerDTO computer : c.getConflictComputers()) {
                System.out.println("---> : " + computer.toString());
            }
        }
        System.out.println("refuse");
        for (Conflict c : rapport.getRefuse()) {
            System.out.println("-> : " + c.getOrigin().toString());
            for (ComputerDTO computer : c.getConflictComputers()) {
                System.out.println("---> : " + computer.toString());
            }
        }

//        String str1 = "MacBook Pro 154 inchh";
//        String str2 = "Macintosh LC III";
//        Levenshtein l = new Levenshtein();
//        System.out.println(l.getPercentSimilarity(str1, str2));

    }
}
