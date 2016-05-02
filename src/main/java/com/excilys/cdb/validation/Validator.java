package com.excilys.cdb.validation;

import java.util.regex.Pattern;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.util.PageParameters;

public enum Validator {

    INSTANCE;

    private final Pattern intRegex = Pattern.compile("[0-9]*[1-9][0-9]*");

    private final Pattern dateRegex = Pattern.compile("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])");

    private Validator() {

    }

    public static Validator getInstance() {
        return INSTANCE;
    }

    public void validateInt(final String s) {
        if (!intRegex.matcher(s).matches()) {
            throw new ValidatorException();
        }
    }

    public void validateDate(final String s) {
        if (!dateRegex.matcher(s).matches()) {
            throw new ValidatorException();
        }
    }

    public void validateId(final Long id) {
        if ((id == null) || (id <= 0)) {
            throw new ValidatorException();
        }
    }

    public void validatePageParameters(PageParameters pparam) {

        // page number
        if (pparam.getPageNumber() < 0) {
            throw new ValidatorException();
        }

        // page size
        if (pparam.getSize() <= 0) {
            throw new ValidatorException();
        }

        // search
        if (pparam.getSearch() == null) {
            throw new ValidatorException();
        }

    }

    public void validateComputerDTO(final ComputerDTO computer) {

        // computer name (required)
        if ((computer.getName() == null) || "".equals(computer.getName())) {
            throw new ValidatorException();
        }

        // computer id (optional)
        if ((computer.getId() != null) && !computer.getId().isEmpty()) {
            validateInt(computer.getId());
        }

        // introduced date (optional)
        if ((computer.getIntroduced() != null) & !"".equals(computer.getIntroduced())) {
            validateDate(computer.getIntroduced());
        }

        // discontinued date (optional)
        if ((computer.getDiscontinued() != null) && !"".equals(computer.getDiscontinued())) {
            validateDate(computer.getDiscontinued());
        }

        // company id (optional)
        if ((computer.getCompanyId() != null) && !"".equals(computer.getCompanyId())) {
            validateInt(computer.getCompanyId());
        }

    }

    public void validateComputer(final Computer computer) {

        // computer name (required)
        if ((computer.getName() == null) || computer.getName().isEmpty()) {
            throw new ValidatorException();
        }

        // computer id (optional)
        if (computer.getId() != null) {
            validateId(computer.getId());
        }

        // introduced date (optional)
        if (computer.getIntroduced() != null) {
            // @TODO ompare to min/max timestamp ?
        }

        // discontinued date (optional)
        if (computer.getDiscontinued() != null) {
            // @TODO compare to min/max timestamp ?
        }

        // company
        if (computer.getCompany() != null) {
            validateCompany(computer.getCompany());
        }
    }

    public void validateCompany(final Company company) {

        // company id (required)
        validateId(company.getId());

        // @TODO name ???
    }
}
