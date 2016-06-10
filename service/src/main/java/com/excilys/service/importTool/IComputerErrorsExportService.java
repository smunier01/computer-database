package com.excilys.service.importTool;

import java.util.List;
import java.util.Map;

import com.excilys.core.conflict.format.Error;
import com.excilys.core.conflict.format.ErrorMessage;
import com.excilys.core.conflict.format.Fields;

public interface IComputerErrorsExportService {
    // CSV Delimiters
    static final String COMMA_DELIMITER = ",";
    static final String LINE_DELIMITER = "\n";
    // File header
    static final String FILE_HEADER = "name, introduced, discontined, company_name, errors";

    // Error messages
    static final String ERROR_NAME = "/!\\ Computer name cannot be empty ";
    static final String ERROR_TIMESTAMP = "/!\\ Date must be between 1970-01-01 and 2037-12-31 ";
    static final String ERROR_DATE_FORMAT = "/!\\ Date must be like YYYY-MM-DD ";
    static final String ERROR_COMPARED_DATES = "/!\\ Introduced date must be before discontinued ";
    static final String ERROR_COMPANY = "/!\\ This company does not exist in DB ";

    /**
     * Export all computers with format errors in a String.
     *
     * @param dtosErrors
     *            list of computers to export
     */
    String csvFileWriter(List<Error> dtosErrors);

    /**
     * Write all error messages in the string buffer "writer".
     *
     * @param writer
     *            file writer linked with the file to write in
     * @param errorMap
     *            map which contains all error messages
     */
    void writeErrors(StringBuffer writer,
            Map<Fields, List<ErrorMessage>> errorMap);

    /**
     * Write all the date's error messages in the file linked with the file
     * writer "writer".
     *
     * @param dateErrors
     *            list of error messages
     * @param writer
     *            file writer linked with the file to write in
     */
    void writeDateErrors(List<ErrorMessage> dateErrors, StringBuffer writer);

}
