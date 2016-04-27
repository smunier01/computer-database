package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.util.PageParameters;

/**
 * Servlet implementation class ComputerServlet
 */
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ComputerService computerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();

        this.computerService = ComputerService.getInstance();
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

        final String pageStr = request.getParameter("page");
        int page = 0;

        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }

        final String psizeStr = request.getParameter("psize");
        final int pageSize = psizeStr != null ? Integer.parseInt(psizeStr) : 10;

        try {
            // we need the number of computers for the pagination
            nbComputers = this.computerService.countComputers();

            // list of computers
            computers = this.computerService.getComputers(new PageParameters(pageSize, page));

        } catch (final ServiceException e) {
            // internal error if DAOexception
            request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
        }

        // set the attributes for the jsp

        request.setAttribute("currentPage", page);
        request.setAttribute("maxPerPages", pageSize);
        request.setAttribute("nbComputers", nbComputers);
        request.setAttribute("computers", computers);

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}
