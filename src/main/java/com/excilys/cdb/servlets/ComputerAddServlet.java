package com.excilys.cdb.servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.impl.CompanyService;
import com.excilys.cdb.service.impl.ComputerService;
import com.excilys.cdb.validation.Validator;
import com.excilys.cdb.validation.ValidatorException;

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

    private final Validator validator;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerAddServlet() {

        super();

        computerService = ComputerService.getInstance();
        companyService = CompanyService.getInstance();
        computerMapper = ComputerMapper.getInstance();
        companyMapper = CompanyMapper.getInstance();
        validator = Validator.getInstance();

    }

    /**
     * Display the form to create a new computer.
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        // for the drop down menu, get the list of companies and convert it to a
        // list of DTO
        final List<CompanyDTO> companies = companyMapper.map(companyService.getCompanies());

        request.setAttribute("companies", companies);

        request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);

    }

    /**
     * Create a new computer.
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final ComputerDTO computer = computerMapper.map(request);

            validator.validateComputerDTO(computer);

            computerService.createComputer(computerMapper.fromDTO(computer));

            response.sendRedirect(request.getContextPath() + "/dashboard");

        } catch (final ValidatorException e) {

            request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
        }

    }
}
