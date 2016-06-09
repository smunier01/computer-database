package com.excilys.binding.validation;

import com.excilys.core.dto.PageParametersDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Validate PageParameters using spring validation framework.
 */
@Component
public class PageParametersValidator implements Validator {

    /**
     * valid order strings
     */
    private static Set<String> orders = new HashSet<>(Arrays.asList("name", "introduced", "discontinued", "company_name"));

    /**
     * valid directions strings
     */
    private static Set<String> directions = new HashSet<>(Arrays.asList("asc", "desc"));

    @Override
    public boolean supports(Class<?> clazz) {
        return PageParametersDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors err) {

        PageParametersDTO params = (PageParametersDTO) obj;

        if (params.getSize() < 1) {
            err.reject("size", "errors.invalid.id");
        }

        if (params.getPageNumber() < 0) {
            err.reject("pageNumber", "errors.invalid.page");
        }

        // order (optional)
        if ((params.getOrder() != null) && !params.getOrder().isEmpty()) {
            if (!orders.contains(params.getOrder())) {
                err.reject("order", "errors.invalid.order");
            }
        }

        // direction (optional)
        if ((params.getDirection() != null) & !"".equals(params.getDirection())) {
            if (!directions.contains(params.getDirection())) {
                err.reject("dir", "errors.invalid.dir");
            }
        }
    }
}
