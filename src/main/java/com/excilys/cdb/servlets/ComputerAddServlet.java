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
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.mapper.Validator;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

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

        this.computerService = ComputerService.getInstance();
        this.companyService = CompanyService.getInstance();
        this.computerMapper = ComputerMapper.getInstance();
        this.companyMapper = CompanyMapper.getInstance();
        this.validator = Validator.getInstance();

    }

    /**
     * Display the form to create a new computer.
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        // to display the add computer form, we only need the list of
        // companies

        final List<CompanyDTO> companyDtos = this.companyService.getCompanies().stream().map(this.companyMapper::toDTO)
                .collect(Collectors.toList());

        request.setAttribute("companies", companyDtos);

        request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);

    }

    /**
     * Create a new computer.
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final ComputerDTO computer = this.computerMapper.map(request);

            this.validator.validateComputerDTO(computer);

            this.computerService.createComputer(this.computerMapper.fromDTO(computer));

            response.sendRedirect(request.getContextPath() + "/dashboard");

        } catch (final IllegalArgumentException e1) {

            request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
        }
    }
}
