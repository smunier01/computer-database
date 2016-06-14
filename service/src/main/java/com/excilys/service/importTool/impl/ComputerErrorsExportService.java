package com.excilys.service.importTool.impl;

import java.util.List;
import java.util.Map;

import com.excilys.core.conflict.format.Error;
import org.springframework.stereotype.Service;

import com.excilys.core.conflict.format.ErrorMessage;
import com.excilys.core.conflict.format.Fields;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.importTool.IComputerErrorsExportService;

@Service
public class ComputerErrorsExportService
        implements IComputerErrorsExportService {

    @Override
    public String csvFileWriter(List<Error> dtosErrors) {
        StringBuffer writer = new StringBuffer(FILE_HEADER + LINE_DELIMITER);
        ComputerDTO tmpDTO;
        for (Error error : dtosErrors) {
            tmpDTO = error.getComputerDTO();
            writer.append(tmpDTO.getName()).append(COMMA_DELIMITER);
            writer.append(tmpDTO.getIntroduced()).append(COMMA_DELIMITER);
            writer.append(tmpDTO.getDiscontinued()).append(COMMA_DELIMITER);
            writer.append(tmpDTO.getCompanyName()).append(COMMA_DELIMITER);
            writeErrors(writer, error.getErrorMap());
            writer.append(LINE_DELIMITER);
        }
        return writer.toString();
    }

    @Override
    public void writeErrors(StringBuffer writer,
            Map<Fields, List<ErrorMessage>> errorMap) {
        if (errorMap.containsKey(Fields.NAME)) {
            writer.append("NAME: " + ERROR_NAME + ERROR_SEPARATOR);
        }
        if (errorMap.containsKey(Fields.INTRODUCED)) {
            writer.append("INTRODUCED DATE: ");
            writeDateErrors(errorMap.get(Fields.INTRODUCED), writer);
            writer.append(ERROR_SEPARATOR);
        }
        if (errorMap.containsKey(Fields.DISCONTINUED)) {
            writer.append("DISCONTINUED DATE: ");
            List<ErrorMessage> tmp = errorMap.get(Fields.DISCONTINUED);
            writeDateErrors(tmp, writer);
            if (tmp.contains(ErrorMessage.INTRODUCED_AFTER_DISCONTINUED)) {
                writer.append(ERROR_COMPARED_DATES);
            }
            writer.append(ERROR_SEPARATOR);
        }
        if (errorMap.containsKey(Fields.COMPANY_NAME)) {
            writer.append("COMPANY: " + ERROR_COMPANY + ERROR_SEPARATOR);
        }
    }

    @Override
    public void writeDateErrors(List<ErrorMessage> dateErrors,
            StringBuffer writer) {
        if (dateErrors.contains(ErrorMessage.DATE_AFTER_TIMESTAMP)
                || dateErrors.contains(ErrorMessage.DATE_BEFORE_TIMESTAMP)) {
            writer.append(ERROR_TIMESTAMP);
        }
        if (dateErrors.contains(ErrorMessage.DATE_FORMAT)) {
            writer.append(ERROR_DATE_FORMAT);
        }
    }
}
