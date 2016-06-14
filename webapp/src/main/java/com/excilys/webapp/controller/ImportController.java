package com.excilys.webapp.controller;

import com.excilys.binding.mapper.impl.ComputerMapper;
import com.excilys.core.conflict.Rapport;
import com.excilys.service.computer.IComputerService;
import com.excilys.service.importTool.IComputerImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/import")
public class ImportController {

    @Autowired
    private IComputerImportService importService;
    @Autowired
    private IComputerService service;
    @Autowired
    private ComputerMapper mapper;

    @RequestMapping(value = "/computers", method = RequestMethod.GET)
    public String getImport() {
        return "addListComputers";
    }

    @RequestMapping(value = "/computers", method = RequestMethod.POST)
    public String postImport(@RequestParam("file") MultipartFile file) {
        String fileName[] = file.getOriginalFilename().split("\\.");

        if (fileName.length != 2) {
            throw new IllegalArgumentException("File need to have an extension.");
        }

        String extension = fileName[1];

        Rapport rapport;
        switch (extension) {
            case "xml":
                rapport = importService.importComputersFromXML(file);
                break;
            case "csv":
                rapport = importService.importComputersFromCSV(file);
                break;
            default:
                throw new IllegalArgumentException("Accepted format : csv and xml.");
        }

        if (rapport.hasErrors()) {
            // TODO return errors in the view
        } else {
            // TODO check doublon in services

            rapport.getToImport().forEach(e -> service.createComputer(mapper.fromDTO(e)));
        }

        return "redirect:/admin";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String handleError() {
        return "invalidComputerImport";
    }
}
