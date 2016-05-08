package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.service.impl.ComputerService;
import com.excilys.cdb.validation.Validator;

/**
 * Servlet implementation class ComputerDeleteServlet.
 */
@WebServlet("/ComputerDeleteServlet")
public class ComputerDeleteServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDeleteServlet.class);

    private static final long serialVersionUID = 1L;

    private IComputerService computerService = ComputerService.getInstance();

    private Validator validator = Validator.getInstance();

    /**
     * delete a list of computers.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.debug("Entering doPost()");

        String selection = request.getParameter("selection");

        if (selection != null) {
            Stream.of(selection.split(","))
                    .filter(this.validator::isIdValid)
                    .map(Long::parseLong)
                    .forEach(this.computerService::deleteComputer);
        }

        response.sendRedirect(request.getContextPath() + "/dashboard");

        LOGGER.debug("Exiting doPost()");
    }
}
