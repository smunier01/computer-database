package com.excilys.binding.validation;

import com.excilys.core.model.Company;
import com.excilys.core.model.Computer;
import com.excilys.core.model.PageParameters;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Singleton class implementing methods to validate different values or objects.
 *
 * @author simon
 */
@Service
public class ValidatorUtil {

    private final Pattern intRegex = Pattern.compile("[0-9]*[1-9][0-9]*");

    private final Pattern dateRegex = Pattern.compile("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])");

    private static LocalDate minTimestamp = LocalDate.parse("1970-01-01");

    private static LocalDate maxTimestamp = LocalDate.parse("2037-12-31");

    /**
     * Use a regex to check if an ID is valid.
     *
     * @param s String representing the ID.
     * @return true if valid
     */
    public boolean isIdValid(String s) {
        return this.intRegex.matcher(s).matches();
    }

    /**
     * Use a regex to check if a date is valid.
     *
     * @param s String representing the date.
     * @return true if valid
     */
    public boolean isDateValid(String s) {
        return this.dateRegex.matcher(s).matches();
    }

    /**
     * Check for the legality of an ID.
     *
     * @param id the id
     * @throws ValidatorException ValidatorException if the id is illegal.
     */
    public void validateId(Long id) {
        if ((id == null) || (id <= 0)) {
            throw new ValidatorException("Illegal ID : " + id);
        }
    }

    /**
     * Check for the legality of a name.
     *
     * @param name the name
     * @throws ValidatorException ValidatorException if the name is illegal.
     */
    public void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new ValidatorException("Illegal name: " + name);
        }
    }

    /**
     * Check the illegality of a PageParameter object.
     *
     * @param params page parameter object to check
     * @throws ValidatorException ValidatorException if the PageParameters is illegal.
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
     * Check for the legality of a Computer.
     *
     * @param computer computer object to check.
     * @throws ValidatorException ValidatorException if the Computer is illegal.
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
     * @param company company object to check.
     */
    public void validateCompany(Company company) {

        // company id (required)
        this.validateId(company.getId());

        // company name not null or empty
        this.validateName(company.getName());
    }
}
