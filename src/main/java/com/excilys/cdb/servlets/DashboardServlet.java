package com.excilys.cdb.servlets;

import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.util.PageParameters;
import com.excilys.cdb.util.Util;

/**
 * Servlet implementation class ComputerServlet.
 */
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ComputerService computerService;

    private final ComputerMapper computerMapper;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();

        computerService = ComputerService.getInstance();
        computerMapper = ComputerMapper.getInstance();
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

        // page should be optional and 0 by default
        int page = Util.getInt(request, "page", 0);

        // page size is stored in session variable, and is 10 by default
        int psize = Util.getIntFromSession(request, "psize", 10);

        try {

            // we need the total number of computers for the pagination
            long nbComputers = computerService.countComputers();

            List<ComputerDTO> computers = computerService.getComputers(new PageParameters(psize, page)).stream()
                    .map(computerMapper::toDTO).collect(Collectors.toList());

            // set the attributes for the jsp
            // TODO this should be a PageParameters object ...
            request.setAttribute("currentPage", page);
            request.setAttribute("nbPages", nbComputers / psize);
            request.setAttribute("maxPerPages", psize);
            request.setAttribute("nbComputers", nbComputers);
            request.setAttribute("computers", computers);

            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);

        } catch (final IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        } catch (final ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

    }
}
