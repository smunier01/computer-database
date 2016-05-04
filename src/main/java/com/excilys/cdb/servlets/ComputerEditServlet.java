package com.excilys.cdb.servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.service.impl.CompanyService;
import com.excilys.cdb.service.impl.ComputerService;
import com.excilys.cdb.validation.Validator;
import com.excilys.cdb.validation.ValidatorException;

/**
 * Servlet implementation class ComputerEditServlet.
 */
@WebServlet("/ComputerEditServlet")
public class ComputerEditServlet extends HttpServlet {

    static final Logger LOGGER = LoggerFactory.getLogger(ComputerEditServlet.class);

    private static final long serialVersionUID = 1L;

    private final IComputerService computerService = ComputerService.getInstance();

    private final ICompanyService companyService = CompanyService.getInstance();

    private final ComputerMapper computerMapper = ComputerMapper.getInstance();

    private final CompanyMapper companyMapper = CompanyMapper.getInstance();

    private final Validator validator = Validator.getInstance();

    /**
     * Display the form to edit an existing computer.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.debug("Entering doGet()");

        String idStr = request.getParameter("id");

        // @TODO not sure how useful this is...
        this.validator.validateInt(idStr);
        long id = Long.parseLong(idStr);

        List<CompanyDTO> companies = this.companyMapper.map(this.companyService.getCompanies());

        Computer computer = this.computerService.getComputer(id);

        if (computer == null) {
            // if this computer id doesn't exist, 404 not found page
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            request.setAttribute("companies", companies);
            request.setAttribute("computer", new ComputerDTO(computer));
            request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
        }

        LOGGER.debug("Exiting doGet()");
    }

    /**
     * Edit an existing computer.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.debug("Entering doPost()");

        final ComputerDTO c = this.computerMapper.map(request);

        try {

            this.validator.validateComputerDTO(c);

            this.computerService.updateComputer(this.computerMapper.fromDTO(c));

            response.sendRedirect(request.getContextPath() + "/dashboard");

        } catch (ValidatorException e) {
            // if the object is not valid, redisplay the page
            request.setAttribute("id", c.getId());
            request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
        }

        LOGGER.debug("Exiting doPost()");
    }

}
