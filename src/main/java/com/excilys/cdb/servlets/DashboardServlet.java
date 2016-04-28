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

        computerService = ComputerService.getInstance();
    }

    private int getInt(HttpServletRequest request, String key, int def) {
        String str = request.getParameter(key);

        int result;

        if (str == null) {
            result = def;
        } else {
            try {
                result = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                result = def;
            }
        }

        return result;
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

        // page should be optional and 0 by default

        long page = getInt(request, "page", 0);

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

        long nbComputers = 0;

        try {
            // we need the number of computers for the pagination
            nbComputers = computerService.countComputers();

            // list of computers
            computers = computerService.getComputers(new PageParameters(psize, page));

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
        // TODO this should be a PageParameters object ...
        request.setAttribute("currentPage", page);
        request.setAttribute("nbPages", nbComputers / psize);
        request.setAttribute("maxPerPages", psize);
        request.setAttribute("nbComputers", nbComputers);
        request.setAttribute("computers", dtos);

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}
