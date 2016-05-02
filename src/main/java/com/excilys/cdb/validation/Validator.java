package com.excilys.cdb.validation;

import java.util.regex.Pattern;

import com.excilys.cdb.dto.ComputerDTO;

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
            throw new ValidatorException();
        }
    }

    public void validateDate(final String s) {
        if (!this.dateRegex.matcher(s).matches()) {
            throw new ValidatorException();
        }
    }

    public void validateComputerDTO(final ComputerDTO computer) {

        // computer name

        if ((computer.getName() == null) || "".equals(computer.getName())) {
            throw new ValidatorException();
        }

        // computer id
        if (computer.getId() != null && !computer.getId().isEmpty()) {
            this.validateInt(computer.getId());
        }

        // introduced date (optional)

        if (computer.getIntroduced() != null & !"".equals(computer.getIntroduced())) {
            this.validateDate(computer.getIntroduced());
        }

        // discontinued date (optional)

        if (computer.getDiscontinued() != null && !"".equals(computer.getDiscontinued())) {
            this.validateDate(computer.getDiscontinued());
        }

        // company id (optional)

        if (computer.getCompanyId() != null && !"".equals(computer.getCompanyId())) {
            this.validateInt(computer.getCompanyId());
        }

    }
}
