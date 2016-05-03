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
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.impl.CompanyService;
import com.excilys.cdb.service.impl.ComputerService;
import com.excilys.cdb.validation.Validator;
import com.excilys.cdb.validation.ValidatorException;

/**
 * Servlet implementation class ComputerEditServlet.
 */
@WebServlet("/ComputerEditServlet")
public class ComputerEditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ComputerService computerService = ComputerService.getInstance();

    private final CompanyService companyService = CompanyService.getInstance();

    private final ComputerMapper computerMapper = ComputerMapper.getInstance();

    private final CompanyMapper companyMapper = CompanyMapper.getInstance();

    private final Validator validator = Validator.getInstance();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerEditServlet() {
        super();
    }

    /**
     * Display the form to edit an existing computer.
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final String idStr = request.getParameter("id");

        // @TODO not sure how useful this is...
        validator.validateInt(idStr);
        final long id = Long.parseLong(idStr);

        final List<CompanyDTO> companies = companyMapper.map(companyService.getCompanies());

        final Computer computer = computerService.getComputer(id);

        if (computer == null) {
            // if this computer id doesn't exist, 404 not found page
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            request.setAttribute("companies", companies);
            request.setAttribute("computer", new ComputerDTO(computer));
            request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
        }
    }

    /**
     * Edit an existing computer.
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final ComputerDTO c = computerMapper.map(request);

        try {

            validator.validateComputerDTO(c);

            computerService.updateComputer(computerMapper.fromDTO(c));

            response.sendRedirect(request.getContextPath() + "/dashboard");

        } catch (final ValidatorException e) {
            // if the object is not valid, redisplay the page
            request.setAttribute("id", c.getId());
            request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
        }
    }

}
