package com.excilys.webapp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.core.conflict.format.Error;
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

    @RequestMapping(method = RequestMethod.GET)
    public void test(HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"importErrors.csv\"");

        List<Error> dtosErrors = new ArrayList<>(2);
        Error err = new Error();
        err.setComputerDTO(
                new ComputerDTO("0", "test1", "200", "", "0", "testCompany"));
        Map<Fields, List<ErrorMessage>> map = new HashMap<>();
        List<ErrorMessage> errMess = new ArrayList<>();
        errMess.add(ErrorMessage.DATE_FORMAT);
        map.put(Fields.INTRODUCED, errMess);
        err.setErrorMap(map);
        dtosErrors.add(err);

        err = new Error();
        err.setComputerDTO(
                new ComputerDTO("a", "test2", null, "", "0", "blabla"));
        errMess.add(ErrorMessage.COMPANY_NOT_FOUND);
        map.put(Fields.COMPANY_NAME, errMess);
        err.setErrorMap(map);
        dtosErrors.add(err);

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
