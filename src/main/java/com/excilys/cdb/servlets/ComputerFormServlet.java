package com.excilys.cdb.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.util.Util;

/**
 * Servlet implementation class ComputerFormServlet
 */
@WebServlet("/ComputerFormServlet")
public class ComputerFormServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ComputerService computerService = new ComputerService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerFormServlet() {
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

        System.out.println("hello get");

        List<Company> companies;

        try {
            companies = this.computerService.getCompanies();
        } catch (final DAOException e) {
            companies = new ArrayList<>();
        }

        request.setAttribute("companies", companies);

        request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        String method = request.getParameter("method");

        if (method == null) {
            method = "POST";
        }

        switch (method) {
        case "DELETE":
            this.deleteComputer(request, response);
            break;
        case "PUT":
            this.updateComputer(request, response);
            break;
        default:
            this.createComputer(request, response);
        }
        /*
         * final String nameStr = request.getParameter("computerName"); final
         * String introducedStr = request.getParameter("introduced"); final
         * String discontinuedStr = request.getParameter("discontinued"); final
         * String companyIdStr = request.getParameter("companyId");
         *
         * if (nameStr == null && !"".equals(nameStr)) {
         * request.setAttribute("computerName", nameStr);
         * request.setAttribute("introduced", introducedStr);
         * request.setAttribute("discontinued", discontinuedStr);
         * request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").
         * forward(request, response); } else {
         *
         * final LocalDate introduced = this.stringToLocalDate(introducedStr);
         * final LocalDate discontinued =
         * this.stringToLocalDate(discontinuedStr); final long companyId =
         * Long.parseLong(companyIdStr);
         *
         * try { this.computerService.createComputer(nameStr, introduced,
         * discontinued, companyId);
         * response.sendRedirect(request.getContextPath() + "/sup2"); } catch
         * (final DAOException e) { request.setAttribute("error",
         * "could not create computer");
         * request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").
         * forward(request, response); } }
         */
    }

    private void updateComputer(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("update computer");
    }

    private void deleteComputer(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final String selection = request.getParameter("selection");

        if (selection != null) {
            final String[] ids = selection.split(",");
            for (final String s : ids) {

                final long id = Long.parseLong(s);

                if (id > 0) {
                    try {
                        this.computerService.deleteComputer(id);
                    } catch (final DAOException e) {
                        request.setAttribute("error", "could not delete computer of id : " + id);
                    }
                }
            }
        }

        response.setStatus(400);
        response.sendRedirect(request.getContextPath() + "/sup2");
    }

    private void createComputer(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String nameStr = request.getParameter("computerName");
        final String introducedStr = request.getParameter("introduced");
        final String discontinuedStr = request.getParameter("discontinued");
        final String companyIdStr = request.getParameter("companyId");

        if (nameStr == null && !"".equals(nameStr)) {
            request.setAttribute("computerName", nameStr);
            request.setAttribute("introduced", introducedStr);
            request.setAttribute("discontinued", discontinuedStr);
            request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
        } else {

            final LocalDate introduced = Util.stringToLocalDate(introducedStr);
            final LocalDate discontinued = Util.stringToLocalDate(discontinuedStr);
            final long companyId = Long.parseLong(companyIdStr);

            try {
                this.computerService.createComputer(nameStr, introduced, discontinued, companyId);
                response.sendRedirect(request.getContextPath() + "/sup2");
            } catch (final DAOException e) {
                request.setAttribute("error", "could not create computer");
                request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
            }
        }
    }
}
