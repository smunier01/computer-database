package com.excilys.webapp.controller;

import com.excilys.core.conflict.Rapport;
import com.excilys.service.importTool.impl.ComputerImportService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/import")
public class ImportController {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ImportController.class);


    @Autowired
    ComputerImportService computerImportService;
    private static final String ROOT = "/home/nbelleme/Bureau/dev/cdb/service/src/main/resources/";


    @RequestMapping(value = "/computers", method = RequestMethod.GET)
    public String getImport() {
        return "addListComputers";
    }

    @RequestMapping(value = "/computers", method = RequestMethod.POST)
    public String postImport(@RequestParam("file") MultipartFile file) {

        String tab[] = file.getOriginalFilename().split("\\.");
        //File extension
        String file_type = tab[1];

        Rapport rapport = null;
        switch (file_type) {
            case "xml":
                rapport = computerImportService.importComputersFromXML(file);
                break;
            case "csv":
                rapport = computerImportService.importComputersFromCSV(file);
                break;
            default:

                break;
        }
        return null;
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String handleError() {
        return "invalidComputerImport";
    }
}
