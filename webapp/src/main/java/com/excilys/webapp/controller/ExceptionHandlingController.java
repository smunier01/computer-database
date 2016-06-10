package com.excilys.webapp.controller;

import com.excilys.binding.validation.ValidatorException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(ValidatorException.class)
    public ModelAndView handleValidatorException(ValidatorException ex) {
        ModelAndView mav = new ModelAndView("errors/validation-format");
        mav.getModelMap().addAttribute("errors", ex.getErrors());
        return mav;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException ex) {
        return "404";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handlePageNotFoundException(NoHandlerFoundException ex) {
        return "404";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return "500";
    }

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception ex) {
        return "505";
    }
}
