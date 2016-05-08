package com.excilys.cdb.validation;

import java.time.LocalDate;
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

    public static LocalDate minTimestamp = LocalDate.parse("1970-01-01");

    public static LocalDate maxTimestamp = LocalDate.parse("2037-12-31");

    public static Validator getInstance() {
        return INSTANCE;
    }

    /**
     * Use a regex to check if an ID is valid.
     *
     * @param s
     *            String representing the ID.
     * @return true if valid
     */
    public boolean isIdValid(String s) {
        return this.intRegex.matcher(s).matches();
    }

    /**
     * Use a regex to check if a date is valid.
     *
     * @param s
     *            String representing the date.
     * @return true if valid
     */
    public boolean isDateValid(String s) {
        return this.dateRegex.matcher(s).matches();
    }

    /**
     * Check for the legality of an ID.
     *
     * @param id
     *            the id
     * @throws ValidatorException
     *             ValidatorException if the id is illegal.
     *
     */
    public void validateId(Long id) {
        if ((id == null) || (id <= 0)) {
            throw new ValidatorException("Illegal ID : " + id);
        }
    }

    /**
     * Check the illegality of a PageParameter object.
     *
     * @param params
     *            page parameter object to check
     * @throws ValidatorException
     *             ValidatorException if the PageParameters is illegal.
     */
    public void validatePageParameters(PageParameters params) {

        // page number
        if (params.getPageNumber() < 0) {
            throw new ValidatorException("Invalid PageNumber : " + params.getPageNumber());
        }

        // page size
        if (params.getSize() <= 0) {
            throw new ValidatorException("Invalid PageSize : " + params.getSize());
        }

        // search
        if (params.getSearch() == null) {
            throw new ValidatorException("PageSearch cannot be null");
        }

        // order
        if (params.getOrder() == null) {
            throw new ValidatorException("PageOrder cannot be null");
        }

        // order direction
        if (params.getDirection() == null) {
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

    /**
     * Check for the legality of a Computer.
     *
     * @param computer
     *            computer object to check.
     * @throws ValidatorException
     *             ValidatorException if the Computer is illegal.
     */
    public void validateComputer(Computer computer) {

        LocalDate d;

        // computer name (required)
        if ((computer.getName() == null) || computer.getName().isEmpty()) {
            throw new ValidatorException("Invalid name : " + computer.getName());
        }

        // computer id (optional)
        if (computer.getId() != null) {
            this.validateId(computer.getId());
        }

        // introduced date (optional)
        if ((d = computer.getIntroduced()) != null) {
            if (d.isBefore(minTimestamp) || d.isAfter(maxTimestamp)) {
                throw new ValidatorException("Invalid timestamp : " + d);
            }
        }

        // discontinued date (optional)
        if ((d = computer.getDiscontinued()) != null) {
            if (d.isBefore(minTimestamp) || d.isAfter(maxTimestamp)) {
                throw new ValidatorException("Invalid timestamp : " + d);
            }
        }

        // company
        if (computer.getCompany() != null) {
            this.validateCompany(computer.getCompany());
        }
    }

    /**
     * Check for the legality of a Computer.
     *
     * @param company
     *            company object to check.
     */
    public void validateCompany(Company company) {

        // company id (required)
        this.validateId(company.getId());

        // TODO name ???
    }
}
