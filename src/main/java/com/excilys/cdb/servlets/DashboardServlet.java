package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.mapper.PageParametersMapper;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.util.PageParameters;

/**
 * Servlet implementation class ComputerServlet.
 */
public class DashboardServlet extends HttpServlet {

    static final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);

    private static final long serialVersionUID = 1L;

    private final ComputerService computerService;

    private final ComputerMapper computerMapper;

    private final PageParametersMapper pageMapper;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();

        computerService = ComputerService.getInstance();
        computerMapper = ComputerMapper.getInstance();
        pageMapper = PageParametersMapper.getInstance();
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

        // page parameters for the getComputers
        final PageParameters pparam = pageMapper.map(request);

        // we need the total number of computers for the pagination
        final long nbComputers = computerService.countComputers();

        final List<ComputerDTO> computers = computerService.getComputers(pparam).stream().map(computerMapper::toDTO)
                .collect(Collectors.toList());

        // set the attributes for the jsp

        System.out.println(pparam.getPageNumber());

        request.setAttribute("nbPages", nbComputers / pparam.getSize());
        request.setAttribute("nbComputers", nbComputers);
        request.setAttribute("computers", computers);
        request.setAttribute("pparam", pparam);

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);

    }
}
