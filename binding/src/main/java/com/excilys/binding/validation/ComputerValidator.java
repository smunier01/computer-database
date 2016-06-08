package com.excilys.binding.validation;

import com.excilys.core.dto.ComputerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * validate computer using string validation framework.
 */
@Component
public class ComputerValidator implements Validator {

    @Autowired
    private ValidatorUtil validator;

    @Override
    public boolean supports(Class<?> c) {
        return ComputerDTO.class.equals(c);
    }

    @Override
    public void validate(Object obj, Errors err) {

        // computer name (required)
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "errors.invalid.name");

        ComputerDTO computer = (ComputerDTO) obj;

        // computer id (optional)
        if ((computer.getId() != null) && !computer.getId().isEmpty()) {
            if (!this.validator.isIdValid(computer.getId())) {
                err.reject("id", "errors.invalid.id");
            }
        }

        // introduced date (optional)
        if ((computer.getIntroduced() != null) & !"".equals(computer.getIntroduced())) {
            if (!this.validator.isDateValid(computer.getIntroduced())) {
                err.reject("introduced", "errors.invalid.introduced");
            }
        }

        // discontinued date (optional)
        if ((computer.getDiscontinued() != null) && !"".equals(computer.getDiscontinued())) {
            if (!this.validator.isDateValid(computer.getDiscontinued())) {
                err.reject("discontinued", "errors.invalid.discontinued");
            }
        }

        // company id (optional)
        if ((computer.getCompanyId() != null) && !"".equals(computer.getCompanyId())) {
            if (!this.validator.isIdValid(computer.getCompanyId())) {
                err.reject("companyId", "errors.invalid.companyId");
            }
        }

    }

}
