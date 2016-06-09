package com.excilys.console;

import com.excilys.binding.doublon.Hamming;
import com.excilys.binding.doublon.Levenshtein;
import com.excilys.binding.doublon.SimilarityCalculator;
import com.excilys.binding.mapper.impl.ComputerMapper;
import com.excilys.service.service.impl.ComputerRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

//        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//
//        context.registerShutdownHook();
//
//        Doublon p = context.getBean(Doublon.class);
//
//        List<Computer> l = p.computerRestService.getList();
//
//        Rapport r = new Rapport(p.computerMapper.toDTO(l));

        String left = "Apple";
        String right = "Apple Inc.";

        SimilarityCalculator sc1 = new Hamming();
        SimilarityCalculator sc2 = new Levenshtein();

        System.out.println(sc1.getPercentSimilarity(left, right));
        System.out.println(sc2.getPercentSimilarity(left, right));

    }
}
