package com.excilys.webapp.controller;

import com.excilys.service.importTool.impl.ComputerImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/import")
public class ImportController {

    @Autowired
    ComputerImportService computerImportService;

    @RequestMapping(value =  "/computers", method = RequestMethod.GET)
    public String getImport(){
        computerImportService.importComputers();
        return "dashboard";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String handleError(){
        return "invalidComputerImport";
    }
}
