package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class ComputerServlet
 */
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ComputerService computerService = new ComputerService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
    }

    /**
     * get the list of computers to display on the dashboard
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        List<Computer> computers = null;
        long nbComputers = 0;

        // offset should be optional and 0 by default

        final String offsetStr = request.getParameter("offset");
        int offset = 0;

        if (offsetStr != null) {
            offset = Integer.parseInt(offsetStr);
        }

        try {
            // we need the number of computers for the pagination
            nbComputers = this.computerService.countComputers();

            // list of computers
            computers = this.computerService.getComputers(offset, 20);

        } catch (final DAOException e) {
            // internal error if DAOexception
            request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
        }

        // set the attributes for the jsp

        request.setAttribute("currentOffset", offset);
        request.setAttribute("maxPerPages", 20);
        request.setAttribute("nbComputers", nbComputers);
        request.setAttribute("computers", computers);

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}
