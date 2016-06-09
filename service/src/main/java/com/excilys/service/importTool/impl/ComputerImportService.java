package com.excilys.service.importTool.impl;

import au.com.bytecode.opencsv.CSVReader;
import com.excilys.binding.validation.ComputerValidator;
import com.excilys.binding.validation.ValidatorUtil;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.model.Computer;
import com.excilys.service.IComputerImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComputerImportService implements IComputerImportService {


    @Autowired
    private ComputerValidator computerValidator;
    private static final String RESOURCE = "/home/nbelleme/Bureau/dev/cdb/service/src/main/resources/computer.csv";

    @Override
    public void importComputers() {

        File file = new File(RESOURCE);
        try {
            FileReader fr = new FileReader(file);
            CSVReader csvReader = new CSVReader(fr, ',');
            csvReader.readNext();
            String[] nextLine;
            List<Computer> computers = new ArrayList<>();

            ValidatorUtil validatorUtil = new ValidatorUtil();
            while ((nextLine = csvReader.readNext()) != null) {
                String name = nextLine[0];
                String introducedString = nextLine[1];
                String discontinuedString = nextLine[2];
                String companyName = nextLine[3];

                ComputerDTO computerDTO = new ComputerDTO.Builder()
                        .name(name)
                        .introduced(introducedString)
                        .discontinued(discontinuedString)
                        .build();
                System.out.println(computerDTO.toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
