package com.excilys.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HTTPErrorHandler {

    @RequestMapping(value = "/400")
    public String error400() {
        return "400";
    }

    @RequestMapping(value = "/403")
    public String error403() {
        return "403";
    }

    @RequestMapping(value = "/404")
    public String error404() {
        return "404";
    }

    @RequestMapping(value = "/500")
    public String error500() {
        return "500";
    }

}