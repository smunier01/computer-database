package com.excilys.cdb.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.util.Util;

/**
 * Servlet implementation class ComputerFormServlet.
 */
@WebServlet("/ComputerFormServlet")
public class ComputerAddServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ComputerService computerService;

    private final CompanyService companyService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerAddServlet() {

        super();

        this.computerService = ComputerService.getInstance();
        this.companyService = CompanyService.getInstance();

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
            companies = this.companyService.getCompanies();
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

        final String nameStr = request.getParameter("computerName");
        final String introducedStr = request.getParameter("introduced");
        final String discontinuedStr = request.getParameter("discontinued");
        final String companyIdStr = request.getParameter("companyId");

        // only the computer name is required
        if ((nameStr == null) && !"".equals(nameStr)) {

            // re-display the form if parameters are wrong

            // TODO use request.setAttribute("computer", dto);

            request.setAttribute("computerName", nameStr);
            request.setAttribute("introduced", introducedStr);
            request.setAttribute("discontinued", discontinuedStr);
            request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);

        } else {

            final LocalDate introduced = Util.stringToLocalDate(introducedStr);
            final LocalDate discontinued = Util.stringToLocalDate(discontinuedStr);
            final long companyId = Long.parseLong(companyIdStr);

            try {

                // create the computer and redirect to the main page

                this.computerService.createComputer(nameStr, introduced, discontinued, companyId);
                response.sendRedirect(request.getContextPath() + "/dashboard");

            } catch (final ServiceException e) {

                request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);

            }
        }
    }
}
