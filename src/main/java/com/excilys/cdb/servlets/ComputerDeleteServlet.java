package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class ComputerDeleteServlet.
 */
@WebServlet("/ComputerDeleteServlet")
public class ComputerDeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ComputerService computerService = ComputerService.getInstance();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerDeleteServlet() {
        super();
    }

    /**
     * delete a list of computers.
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final String selection = request.getParameter("selection");

        if (selection != null) {
            Stream.of(selection.split(",")).map(Long::parseLong).forEach(computerService::deleteComputer);
        }

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
