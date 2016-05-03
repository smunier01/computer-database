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
        if (!this.intRegex.matcher(s).matches()) {
            throw new ValidatorException("Invalid ID : " + s);
        }
    }

    public void validateDate(final String s) {
        if (!this.dateRegex.matcher(s).matches()) {
            throw new ValidatorException("Invalid DATE : " + s);
        }
    }

    public void validateId(final Long id) {
        if ((id == null) || (id <= 0)) {
            throw new ValidatorException("Invalid ID : " + id);
        }
    }

    public void validatePageParameters(final PageParameters pparam) {

        // page number
        if (pparam.getPageNumber() < 0) {
            throw new ValidatorException("Invalid PageNumber : " + pparam.getPageNumber());
        }

        // page size
        if (pparam.getSize() <= 0) {
            throw new ValidatorException("Invalid PageSize : " + pparam.getSize());
        }

        // search
        if (pparam.getSearch() == null) {
            throw new ValidatorException("PageSearch cannot be null");
        }

        // order
        if (pparam.getOrder() == null) {
            throw new ValidatorException("PageOrder cannot be null");
        }
    }

    public void validateComputerDTO(final ComputerDTO computer) {

        // computer name (required)
        if ((computer.getName() == null) || "".equals(computer.getName())) {
            throw new ValidatorException("Invalid name : " + computer.getName());
        }

        // computer id (optional)
        if ((computer.getId() != null) && !computer.getId().isEmpty()) {
            this.validateInt(computer.getId());
        }

        // introduced date (optional)
        if ((computer.getIntroduced() != null) & !"".equals(computer.getIntroduced())) {
            this.validateDate(computer.getIntroduced());
        }

        // discontinued date (optional)
        if ((computer.getDiscontinued() != null) && !"".equals(computer.getDiscontinued())) {
            this.validateDate(computer.getDiscontinued());
        }

        // company id (optional)
        if ((computer.getCompanyId() != null) && !"".equals(computer.getCompanyId())) {
            this.validateInt(computer.getCompanyId());
        }

    }

    public void validateComputer(final Computer computer) {

        // computer name (required)
        if ((computer.getName() == null) || computer.getName().isEmpty()) {
            throw new ValidatorException("Invalid name : " + computer.getName());
        }

        // computer id (optional)
        if (computer.getId() != null) {
            this.validateId(computer.getId());
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
            this.validateCompany(computer.getCompany());
        }
    }

    public void validateCompany(final Company company) {

        // company id (required)
        this.validateId(company.getId());

        // @TODO name ???
    }
}
