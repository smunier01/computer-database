package com.excilys.cdb.validation;

import com.excilys.cdb.dto.ComputerDTO;

public enum ValidatorBool {

    INSTANCE;

    private Validator validator = Validator.getInstance();

    public static ValidatorBool getInstance() {
        return INSTANCE;
    }

    public boolean validateInt(String s) {
        try {
            this.validator.validateInt(s);
            return true;
        } catch (ValidatorException e) {
            return false;
        }
    }

    public boolean validateDate(String s) {
        try {
            this.validator.validateDate(s);
            return true;
        } catch (ValidatorException e) {
            return false;
        }
    }

    public boolean validateComputerDTO(ComputerDTO computer) {
        try {
            this.validator.validateComputerDTO(computer);
            return true;
        } catch (ValidatorException e) {
            return false;
        }
    }

}
