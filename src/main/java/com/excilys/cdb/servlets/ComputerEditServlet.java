package com.excilys.cdb.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.util.Util;

/**
 * Servlet implementation class ComputerEditServlet
 */
@WebServlet("/ComputerEditServlet")
public class ComputerEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ComputerService computerService = new ComputerService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        // show form to edit a computer

        final String idStr = request.getParameter("id");

        if (idStr != null) {

            final long id = Long.parseLong(idStr);
            Computer computer = null;
            List<Company> companies;

            try {

                companies = this.computerService.getCompanies();
                computer = this.computerService.getComputer(id);

                if (computer == null) {
                    request.getRequestDispatcher("/WEB-INF/views/404.html").forward(request, response);
                } else {
                    request.setAttribute("companies", companies);
                    request.setAttribute("computer", computer);
                    request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
                }

            } catch (final DAOException e) {
                request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/views/404.html").forward(request, response);
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        // execute the update on the computer

        final String idStr = request.getParameter("id");

        // check if the computer we are trying to update exists.

        Computer computer = null;

        try {

            final String nameStr = request.getParameter("computerName");
            final String introducedStr = request.getParameter("introduced");
            final String discontinuedStr = request.getParameter("discontinued");
            final String companyIdStr = request.getParameter("companyId");

            // check if the computer we are trying to update exists.

            final long idComputer = Long.parseLong(idStr);
            computer = this.computerService.getComputer(idComputer);

            final long companyId = Long.parseLong(companyIdStr);

            // check if parameters are valid

            if (nameStr == null && !"".equals(nameStr)) {

                request.setAttribute("computer", computer);
                request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);

            } else {

                final LocalDate introduced = Util.stringToLocalDate(introducedStr);
                final LocalDate discontinued = Util.stringToLocalDate(discontinuedStr);

                // update the computer object

                this.computerService.updateComputer(idComputer, nameStr, introduced, discontinued, companyId);

                response.sendRedirect(request.getContextPath() + "/sup2");

            }

        } catch (final DAOException e) {
            request.setAttribute("error", "could not update computer");
            request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
        }
    }

}
