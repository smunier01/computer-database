package com.excilys.cdb.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.mapper.PageParametersMapper;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.PageParameters;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.service.impl.ComputerService;
import com.excilys.cdb.validation.Validator;

/**
 * Servlet implementation class ComputerServlet.
 */
public class DashboardServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);

    private static final long serialVersionUID = 1L;

    private final IComputerService computerService = ComputerService.getInstance();

    private final ComputerMapper computerMapper = ComputerMapper.getInstance();

    private final PageParametersMapper pageMapper = PageParametersMapper.getInstance();

    private final Validator validator = Validator.getInstance();

    /**
     * get the list of computers to display on the dashboard.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.debug("Entering doGet()");

        PageParameters params = this.pageMapper.map(request);

        this.validator.validatePageParameters(params);

        Page<ComputerDTO> computerPage = this.computerMapper.map(this.computerService.getComputersPage(params));

        request.setAttribute("page", computerPage);

        LOGGER.debug("Exiting doGet()");

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);

    }
}
