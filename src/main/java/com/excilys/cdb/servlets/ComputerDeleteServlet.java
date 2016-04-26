package com.excilys.cdb.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceException;

/**
 * Servlet implementation class ComputerDeleteServlet
 */
@WebServlet("/ComputerDeleteServlet")
public class ComputerDeleteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	ComputerService computerService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ComputerDeleteServlet() {
		super();

		computerService = ComputerService.getInstance();
	}

	/**
	 * delete a list of computers
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String selection = request.getParameter("selection");

		if (selection != null) {
			final String[] ids = selection.split(",");
			for (final String s : ids) {

				final long id = Long.parseLong(s);

				if (id > 0) {
					try {
						computerService.deleteComputer(id);
					} catch (final ServiceException e) {
						request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
					}
				}
			}
		}

		response.sendRedirect(request.getContextPath() + "/dashboard");
	}

}
