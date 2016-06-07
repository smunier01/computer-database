package com.excilys.binding.validation;


import com.excilys.core.dto.ComputerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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

        // TODO: errors messages should be externalized in a property file.

        // computer name (required)
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "invalid name");

        ComputerDTO computer = (ComputerDTO) obj;

        // computer id (optional)
        if ((computer.getId() != null) && !computer.getId().isEmpty()) {
            if (!this.validator.isIdValid(computer.getId())) {
                err.reject("id", "invalid id");
            }
        }

        // introduced date (optional)
        if ((computer.getIntroduced() != null) & !"".equals(computer.getIntroduced())) {
            if (!this.validator.isDateValid(computer.getIntroduced())) {
                err.reject("introduced", "invalid introduced date");
            }
        }

        // discontinued date (optional)
        if ((computer.getDiscontinued() != null) && !"".equals(computer.getDiscontinued())) {
            if (!this.validator.isDateValid(computer.getDiscontinued())) {
                err.reject("discontinued", "invalid discontinued date");
            }
        }

        // company id (optional)
        if ((computer.getCompanyId() != null) && !"".equals(computer.getCompanyId())) {
            if (!this.validator.isIdValid(computer.getCompanyId())) {
                err.reject("companyId", "invalid company id");
            }
        }

    }

}
