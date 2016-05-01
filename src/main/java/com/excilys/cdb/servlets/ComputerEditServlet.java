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
import com.excilys.cdb.mapper.MapperException;
import com.excilys.cdb.mapper.Validator;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

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

        computerService = ComputerService.getInstance();
        companyService = CompanyService.getInstance();
        computerMapper = ComputerMapper.getInstance();
        companyMapper = CompanyMapper.getInstance();
        validator = Validator.getInstance();
    }

    /**
     * Display the form to edit an existing computer.
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final String idStr = request.getParameter("id");

        if ((idStr == null) || !validator.validateInt(idStr)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {

            long id = Long.parseLong(idStr);

            // get the list of companies for the dropdown menu and convert it to
            // DTOs.
            List<CompanyDTO> companyDtos = companyService.getCompanies().stream().map(companyMapper::toDTO)
                    .collect(Collectors.toList());

            Computer computer = computerService.getComputer(id);

            if (computer == null) {
                // if this computer id doesn't exist, 404 not found page
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                request.setAttribute("companies", companyDtos);
                request.setAttribute("computer", new ComputerDTO(computer));
                request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
            }
        }

    }

    /**
     * Edit an existing computer.
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Computer c = computerMapper.map(request);

            computerService.updateComputer(c.getId(), c.getName(), c.getIntroduced(), c.getDiscontinued(),
                    c.getCompany().getId());

            response.sendRedirect(request.getContextPath() + "/dashboard");

        } catch (MapperException e1) {
            // if the mapper could not create the object, redisplay the form..
            request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
        }
    }

}
