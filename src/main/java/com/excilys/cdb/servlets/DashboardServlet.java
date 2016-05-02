package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.List;

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
import com.excilys.cdb.validation.Validator;

/**
 * Servlet implementation class ComputerServlet.
 */
public class DashboardServlet extends HttpServlet {

    static final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);

    private static final long serialVersionUID = 1L;

    private final ComputerService computerService = ComputerService.getInstance();

    private final ComputerMapper computerMapper = ComputerMapper.getInstance();

    private final PageParametersMapper pageMapper = PageParametersMapper.getInstance();

    private final Validator validator = Validator.getInstance();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
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

        validator.validatePageParameters(pparam);

        List<ComputerDTO> computers = computerMapper.map(computerService.getComputers(pparam));

        // we need the total number of computers for the pagination
        final long nbComputers;

        // small optimization.. if we are on the first page and the number of
        // computers returned is less than the page size, then there is no need
        // to count the computers.
        if ((computers.size() < pparam.getSize()) && (pparam.getPageNumber() == 0)) {
            nbComputers = computers.size();
        } else {
            nbComputers = computerService.countComputers(pparam);
        }

        // set the attributes for the jsp

        request.setAttribute("nbPages", Math.max(1, ((nbComputers + pparam.getSize()) - 1) / pparam.getSize()));
        request.setAttribute("nbComputers", nbComputers);
        request.setAttribute("computers", computers);
        request.setAttribute("pparam", pparam);

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);

    }
}
