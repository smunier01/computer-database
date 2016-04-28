package com.excilys.cdb.servlets;

import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.mapper.MapperException;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceException;

/**
 * Servlet implementation class ComputerFormServlet.
 */
@WebServlet("/ComputerFormServlet")
public class ComputerAddServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ComputerService computerService;

    private final CompanyService companyService;

    private final ComputerMapper computerMapper;

    private final CompanyMapper companyMapper;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerAddServlet() {

        super();

        computerService = ComputerService.getInstance();
        companyService = CompanyService.getInstance();
        computerMapper = ComputerMapper.getInstance();
        companyMapper = CompanyMapper.getInstance();

    }

    /**
     * Display the form to create a new computer.
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // to display the add computer form, we only need the list of
            // companies

            List<CompanyDTO> companyDtos = companyService.getCompanies().stream().map(companyMapper::toDTO)
                    .collect(Collectors.toList());

            request.setAttribute("companies", companyDtos);

            request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);

        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create a new computer.
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        Computer computer;

        try {
            computer = computerMapper.map(request);

            try {
                computerService.createComputer(computer);

                // redirect to the main page on success
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } catch (ServiceException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (MapperException e1) {
            // if the mapper could not create the object, redisplay the form..
            request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
        }
    }
}
