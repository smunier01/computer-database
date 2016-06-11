package com.excilys.webapp.controller;

import com.excilys.binding.validation.ValidatorException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlingController {

    /**
     * catch exceptions from the validator.
     *
     * @param ex ValidatorException
     * @return mav containing the validation-error jsp
     */
    @ExceptionHandler(ValidatorException.class)
    public ModelAndView handleValidatorException(ValidatorException ex) {
        ModelAndView mav = new ModelAndView("errors/validation-error");
        mav.getModelMap().addAttribute("errors", ex.getErrors());
        return mav;
    }

    /**
     * Handler for NoSuchElementException.
     *
     * @param ex NoSuchElementException
     * @return 404 not found jsp
     */
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException ex) {
        return "404";
    }

    /**
     * Handler for NoHandlerFoundException.
     *
     * @param ex NoHandlerFoundException
     * @return 404 not found jsp
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handlePageNotFoundException(NoHandlerFoundException ex) {
        return "404";
    }

    /**
     * Handler for IllegalArgumentException.
     *
     * @param ex IllegalArgumentException
     * @return 500 error jsp
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return "500";
    }

    /**
     * Handler every other exceptions.
     *
     * @param ex Exception
     * @return 505 jsp
     */
    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception ex) {
        return "505";
    }
}
