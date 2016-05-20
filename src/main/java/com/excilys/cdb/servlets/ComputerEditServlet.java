package com.excilys.cdb.servlets;

import java.io.IOException;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.service.impl.CompanyService;
import com.excilys.cdb.service.impl.ComputerService;
import com.excilys.cdb.validation.Validator;

/**
 * Servlet implementation class ComputerEditServlet.
 */
@Configurable
public class ComputerEditServlet extends HttpServlet {

    static final Logger LOGGER = LoggerFactory.getLogger(ComputerEditServlet.class);

    private static final long serialVersionUID = 1L;

    @Autowired
    private IComputerService computerService;

    @Autowired
    private ICompanyService companyService;

    private final ComputerMapper computerMapper = ComputerMapper.getInstance();

    private final CompanyMapper companyMapper = CompanyMapper.getInstance();

    @Autowired
    private Validator validator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * Display the form to edit an existing computer.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.debug("Entering doGet()");

        String idStr = request.getParameter("id");

        if (!this.validator.isIdValid(idStr)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else if (request.getAttribute("computer") == null) {

            ComputerDTO computer = this.computerMapper.toDTO(this.computerService.getComputer(Long.parseLong(idStr)));

            if (computer == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                request.setAttribute("computer", computer);
            }
        }

        List<CompanyDTO> companies = this.companyMapper.map(this.companyService.getCompanies());
        request.setAttribute("companies", companies);
        request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);

        LOGGER.debug("Exiting doGet()");
    }

    /**
     * Edit an existing computer.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.debug("Entering doPost()");

        ComputerDTO computer = this.computerMapper.map(request);

        Set<String> errors = this.validator.validateComputerDTO(computer);

        if (errors.isEmpty()) {
            this.computerService.updateComputer(this.computerMapper.fromDTO(computer));
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            request.setAttribute("computer", computer);
            request.setAttribute("errors", errors);
            this.doGet(request, response);
        }

        LOGGER.debug("Exiting doPost()");
    }

}
