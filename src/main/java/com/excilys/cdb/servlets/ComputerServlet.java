package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class ComputerServlet
 */
public class ComputerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ComputerService computerService = new ComputerService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        List<Computer> computers = null;
        long nbComputers = 0;

        final String offsetStr = request.getParameter("offset");
        int offset = 0;

        if (offsetStr != null) {
            offset = Integer.parseInt(offsetStr);
        }

        try {
            nbComputers = this.computerService.countComputers();
            System.out.println(offset);
            computers = this.computerService.getComputers(offset, 20);
        } catch (final DAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("currentOffset", offset);
        request.setAttribute("maxPerPages", 20);
        request.setAttribute("nbComputers", nbComputers);
        request.setAttribute("computers", computers);

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        System.out.println("Hello2");
        this.doGet(request, response);
    }

}
