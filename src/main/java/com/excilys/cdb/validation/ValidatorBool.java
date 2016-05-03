package com.excilys.cdb.validation;

import com.excilys.cdb.dto.ComputerDTO;

public enum ValidatorBool {

    INSTANCE;

    private final Validator validator = Validator.getInstance();

    public static ValidatorBool getInstance() {
        return INSTANCE;
    }

    public boolean validateInt(final String s) {
        try {
            this.validator.validateInt(s);
            return true;
        } catch (final ValidatorException e) {
            return false;
        }
    }

    public boolean validateDate(final String s) {
        try {
            this.validator.validateDate(s);
            return true;
        } catch (final ValidatorException e) {
            return false;
        }
    }

    public boolean validateComputerDTO(final ComputerDTO computer) {
        try {
            this.validator.validateComputerDTO(computer);
            return true;
        } catch (final ValidatorException e) {
            return false;
        }
    }

}
