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
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.mapper.MapperException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceException;

/**
 * Servlet implementation class ComputerEditServlet.
 */
@WebServlet("/ComputerEditServlet")
public class ComputerEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ComputerService computerService;

    private final CompanyService companyService;

    private final ComputerMapper computerMapper;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerEditServlet() {
        super();

        computerService = ComputerService.getInstance();
        companyService = CompanyService.getInstance();
        computerMapper = ComputerMapper.getInstance();
    }

    /**
     * Display the form to edit an existing computer.
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final String idStr = request.getParameter("id");

        if (idStr != null) {

            final long id = Long.parseLong(idStr);
            Computer computer = null;
            List<Company> companies;
            final List<CompanyDTO> companyDtos = new ArrayList<>();

            try {

                // we need the list of companies for the dropdown menu
                companies = companyService.getCompanies();

                for (final Company c : companies) {
                    companyDtos.add(new CompanyDTO(c));
                }

                // computer to display the current values
                computer = computerService.getComputer(id);

                if (computer == null) {
                    // if this computer id doesn't exist, 404 page
                    request.getRequestDispatcher("/WEB-INF/views/404.html").forward(request, response);
                } else {
                    request.setAttribute("companies", companyDtos);
                    request.setAttribute("computer", new ComputerDTO(computer));
                    request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
                }

            } catch (final ServiceException e) {
                request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/views/404.html").forward(request, response);
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

            try {
                computerService.updateComputer(c.getId(), c.getName(), c.getIntroduced(), c.getDiscontinued(),
                        c.getCompany().getId());

                response.sendRedirect(request.getContextPath() + "/dashboard");
            } catch (IllegalArgumentException e) {
                request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
            } catch (ServiceException e) {
                request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
            }

        } catch (MapperException e1) {
            // if the mapper could not create the object, redisplay the form..
            request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
        }
    }

}
