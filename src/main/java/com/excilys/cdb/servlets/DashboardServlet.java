package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.util.PageParameters;

/**
 * Servlet implementation class ComputerServlet.
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
     * get the list of computers to display on the dashboard.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        // true is used to create a new session if one doesn't already exist
        final HttpSession session = request.getSession(true);

        List<Computer> computers = null;
        long nbComputers = 0;

        // offset should be optional and 0 by default

        final String pageStr = request.getParameter("page");
        int page = 0;

        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }

        // psize (size of a page) is optional and 10 by default
        // we store it in the session variable

        int psize = 0;

        try {
            psize = Integer.parseInt(request.getParameter("psize"));
            session.setAttribute("psize", psize);
        } catch (final NumberFormatException ignored) {
            final Object spsize = session.getAttribute("psize");

            if (spsize == null) {
                psize = 10;
            } else {
                psize = (Integer) spsize;
            }
        }

        try {
            // we need the number of computers for the pagination
            nbComputers = this.computerService.countComputers();

            // list of computers
            computers = this.computerService.getComputers(new PageParameters(psize, page));

        } catch (final IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        } catch (final ServiceException e) {
            request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
        }

        // convert to a list of ComputerDTO

        final List<ComputerDTO> dtos = new ArrayList<>();

        for (final Computer c : computers) {
            dtos.add(new ComputerDTO(c));
        }

        // set the attributes for the jsp

        request.setAttribute("currentPage", page);
        request.setAttribute("nbPages", nbComputers / psize);
        request.setAttribute("maxPerPages", psize);
        request.setAttribute("nbComputers", nbComputers);
        request.setAttribute("computers", dtos);

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}
