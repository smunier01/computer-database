package com.excilys.cdb.validation;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.PageParameters;

/**
 * Singleton class implementing methods to validate different values or objects.
 *
 * @author simon
 */
public enum Validator {

    INSTANCE;

    private final Pattern intRegex = Pattern.compile("[0-9]*[1-9][0-9]*");

    private final Pattern dateRegex = Pattern.compile("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])");

    private Validator() {

    }

    public static Validator getInstance() {
        return INSTANCE;
    }

    public void validateInt(String s) {
        if (!this.intRegex.matcher(s).matches()) {
            throw new ValidatorException("Invalid ID : " + s);
        }
    }

    public boolean isIdValid(String s) {
        return this.intRegex.matcher(s).matches();
    }

    public void validateDate(String s) {
        if (!this.dateRegex.matcher(s).matches()) {
            throw new ValidatorException("Invalid DATE : " + s);
        }
    }

    public boolean isDateValid(String s) {
        return this.dateRegex.matcher(s).matches();
    }

    public void validateId(Long id) {
        if ((id == null) || (id <= 0)) {
            throw new ValidatorException("Invalid ID : " + id);
        }
    }

    public void validatePageParameters(PageParameters pparam) {

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

        // order direction
        if (pparam.getDirection() == null) {
            throw new ValidatorException("OrderDirection cannot be null");
        }
    }

    /**
     * check the validity of a computerDTO object.
     *
     * @param computer
     *            computerDTO to check
     * @return set of field with errors
     */
    public Set<String> validateComputerDTO(ComputerDTO computer) {

        Set<String> errors = new HashSet<String>();

        // computer name (required)
        if ((computer.getName() == null) || "".equals(computer.getName())) {
            errors.add("name");
        }

        // computer id (optional)
        if ((computer.getId() != null) && !computer.getId().isEmpty()) {
            if (!this.isIdValid(computer.getId())) {
                errors.add("id");
            }
        }

        // introduced date (optional)
        if ((computer.getIntroduced() != null) & !"".equals(computer.getIntroduced())) {
            if (!this.isDateValid(computer.getIntroduced())) {
                errors.add("introduced");
            }
        }

        // discontinued date (optional)
        if ((computer.getDiscontinued() != null) && !"".equals(computer.getDiscontinued())) {
            if (!this.isDateValid(computer.getDiscontinued())) {
                errors.add("discontinued");
            }
        }

        // company id (optional)
        if ((computer.getCompanyId() != null) && !"".equals(computer.getCompanyId())) {
            if (!this.isIdValid(computer.getCompanyId())) {
                errors.add("companyId");
            }
        }

        return errors;

    }

    public void validateComputer(Computer computer) {

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
            // TODO compare to min/max timestamp
        }

        // discontinued date (optional)
        if (computer.getDiscontinued() != null) {
            // TODO compare to min/max timestamp
        }

        // company
        if (computer.getCompany() != null) {
            this.validateCompany(computer.getCompany());
        }
    }

    public void validateCompany(Company company) {

        // company id (required)
        this.validateId(company.getId());

        // TODO name ???
    }
}
