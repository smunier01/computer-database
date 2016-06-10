package com.excilys.webapp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.excilys.core.conflict.format.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.core.conflict.format.ErrorMessage;
import com.excilys.core.conflict.format.Fields;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.importTool.impl.ComputerErrorsExportService;

@Controller
@RequestMapping(value = "/admin/export")
public class ExportController {

    @Autowired
    ComputerErrorsExportService exportService;

    @RequestMapping(method = RequestMethod.POST)
    public void exportErrorsToCsv(HttpServletResponse response,
            List<Error> dtosErrors) {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"importErrors.csv\"");

        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream
                    .write(exportService.csvFileWriter(dtosErrors).getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Error at file creation !", e);// FIXME
        }

    }

}
