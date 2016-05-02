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
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validation.Validator;

/**
 * Servlet implementation class ComputerEditServlet.
 */
@WebServlet("/ComputerEditServlet")
public class ComputerEditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ComputerService computerService;

    private final CompanyService companyService;

    private final ComputerMapper computerMapper;

    private final CompanyMapper companyMapper;

    private final Validator validator;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerEditServlet() {
        super();

        this.computerService = ComputerService.getInstance();
        this.companyService = CompanyService.getInstance();
        this.computerMapper = ComputerMapper.getInstance();
        this.companyMapper = CompanyMapper.getInstance();
        this.validator = Validator.getInstance();
    }

    /**
     * Display the form to edit an existing computer.
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final String idStr = request.getParameter("id");

        try {
            this.validator.validateInt(idStr);

            final long id = Long.parseLong(idStr);

            // get the list of companies for the dropdown menu and convert it to
            // DTOs.
            final List<CompanyDTO> companyDtos = this.companyService.getCompanies().stream()
                    .map(this.companyMapper::toDTO).collect(Collectors.toList());

            final Computer computer = this.computerService.getComputer(id);

            if (computer == null) {
                // if this computer id doesn't exist, 404 not found page
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                request.setAttribute("companies", companyDtos);
                request.setAttribute("computer", new ComputerDTO(computer));
                request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
            }

        } catch (final IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    /**
     * Edit an existing computer.
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final ComputerDTO c = this.computerMapper.map(request);

            this.validator.validateComputerDTO(c);

            this.computerService.updateComputer(this.computerMapper.fromDTO(c));

            response.sendRedirect(request.getContextPath() + "/dashboard");

        } catch (final IllegalArgumentException e1) {
            // if the mapper could not create the object, redisplay the form..
            request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
        }
    }

}
