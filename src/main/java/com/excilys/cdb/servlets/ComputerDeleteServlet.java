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

/**
 * Servlet implementation class ComputerDeleteServlet.
 */
@WebServlet("/ComputerDeleteServlet")
public class ComputerDeleteServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDeleteServlet.class);

    private static final long serialVersionUID = 1L;

    private final IComputerService computerService = ComputerService.getInstance();

    /**
     * delete a list of computers.
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.debug("Entering doPost()");

        final String selection = request.getParameter("selection");

        if (selection != null) {
            Stream.of(selection.split(",")).map(Long::parseLong).forEach(this.computerService::deleteComputer);
        }

        response.sendRedirect(request.getContextPath() + "/dashboard");

        LOGGER.debug("Exiting doPost()");
    }
}
