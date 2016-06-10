package com.excilys.webapp.controller;

import com.excilys.service.importTool.impl.ComputerImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
@RequestMapping(value = "/import")
public class ImportController {

    @Autowired
    ComputerImportService computerImportService;
    private static final String ROOT = "/home/nbelleme/Bureau/dev/cdb/service/src/main/resources/";

    @RequestMapping(value = "/computers", method = RequestMethod.GET)
    public String getImport() {

        return "addListComputers";
    }

    @RequestMapping(value = "/computers", method = RequestMethod.POST)
    public String postImport(@RequestParam("file") MultipartFile file) {

        File convFile = new File(file.getOriginalFilename());
        try {
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(new File(computerImportService.RESOURCE)));
            FileCopyUtils.copy(file.getInputStream(), stream);
            stream.close();
        }catch (Exception e){
            //TODO change exception handler
        }
        computerImportService.importComputers();
        return null;
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String handleError() {
        return "invalidComputerImport";
    }
}
