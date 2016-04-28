package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.mapper.MapperException;
import com.excilys.cdb.model.Company;
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

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerAddServlet() {

        super();

        computerService = ComputerService.getInstance();
        companyService = CompanyService.getInstance();
        computerMapper = ComputerMapper.getInstance();

    }

    /**
     * Display the form to create a new computer.
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        // to display the add computer form, we only need the list of companies

        List<Company> companies;

        try {
            companies = companyService.getCompanies();
        } catch (final ServiceException e) {
            companies = new ArrayList<>();
        }

        // convert it to DTOs

        final List<CompanyDTO> companyDtos = new ArrayList<>();

        for (final Company c : companies) {
            companyDtos.add(new CompanyDTO(c));
        }

        request.setAttribute("companies", companyDtos);

        request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);

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
                request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
            }

        } catch (MapperException e1) {
            // if the mapper could not create the object, redisplay the form..
            request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
        }

    }
}
