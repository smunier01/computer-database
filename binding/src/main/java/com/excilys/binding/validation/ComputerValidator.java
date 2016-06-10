package com.excilys.binding.validation;

import com.excilys.core.conflict.format.ErrorMessage;
import com.excilys.core.conflict.format.Fields;
import com.excilys.core.dto.ComputerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * validate computer using string validation framework.
 */
@Component
public class ComputerValidator implements Validator {

    @Autowired
    private ValidatorUtil validator;

    private static final LocalDate MIN_TIMESTAMP = LocalDate.of(1970, 1, 1);
    private static final LocalDate MAX_TIMESTAMP = LocalDate.of(2037, 12, 31);

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

    /**
     * Check if computerDTO is correctly formed to matches model Computer
     *
     * @param computerDTO computerDTO to be post
     * @return Map of fields with an error with the list of the errors
     */
    public Map<Fields, List<ErrorMessage>> validateComputerDTO(ComputerDTO computerDTO) {

        boolean isIntroducedWellFormed = true;
        boolean isDiscontinuedWellFormed = true;
        Map<Fields, List<ErrorMessage>> errors = new HashMap<>();


        if (computerDTO.getName() == null || computerDTO.getName().trim().equalsIgnoreCase("")) {
            errors.put(Fields.NAME, new ArrayList<>());
            errors.get(Fields.NAME).add(ErrorMessage.NAME_NULL);
        }

        if (computerDTO.getIntroduced() != null && !computerDTO.getIntroduced().trim().equalsIgnoreCase("")) {
            isIntroducedWellFormed = validator.isDateValid(computerDTO.getIntroduced());
            if (!isIntroducedWellFormed) {
            errors.put(Fields.INTRODUCED, new ArrayList<>());
                errors.get(Fields.INTRODUCED).add(ErrorMessage.DATE_FORMAT);
            }
        }

        if (computerDTO.getDiscontinued() != null && !computerDTO.getDiscontinued().trim().equalsIgnoreCase("")) {
            isDiscontinuedWellFormed = validator.isDateValid(computerDTO.getDiscontinued());
            if (!isDiscontinuedWellFormed) {
                errors.put(Fields.DISCONTINUED, new ArrayList<>());
                errors.get(Fields.DISCONTINUED).add(ErrorMessage.DATE_FORMAT);
            }
        }

        if (isIntroducedWellFormed && isDiscontinuedWellFormed) {
            if (computerDTO.getIntroduced() != null && !computerDTO.getIntroduced().trim().equalsIgnoreCase("")) {
                LocalDate introduced = LocalDate.parse(computerDTO.getIntroduced());

                if (introduced.isBefore(MIN_TIMESTAMP)) {
                    errors.get(Fields.INTRODUCED).add(ErrorMessage.DATE_BEFORE_TIMESTAMP);
                }

                if (introduced.isAfter(MAX_TIMESTAMP)) {
                    errors.get(Fields.INTRODUCED).add(ErrorMessage.DATE_AFTER_TIMESTAMP);
                }

                if (computerDTO.getDiscontinued() != null && !computerDTO.getDiscontinued().trim().equalsIgnoreCase("")) {
                    LocalDate discontinued = LocalDate.parse(computerDTO.getDiscontinued());

                    if (discontinued.isBefore(MIN_TIMESTAMP)) {
                        errors.get(Fields.DISCONTINUED).add(ErrorMessage.DATE_BEFORE_TIMESTAMP);
                    }

                    if (discontinued.isAfter(MAX_TIMESTAMP)) {
                        errors.get(Fields.DISCONTINUED).add(ErrorMessage.DATE_AFTER_TIMESTAMP);
                    }

                    if (introduced.isAfter(discontinued)) {
                        errors.get(Fields.DISCONTINUED).add(ErrorMessage.INTRODUCED_AFTER_DISCONTINUED);
                    }
                }
            }
        }
        return errors;
    }

}
