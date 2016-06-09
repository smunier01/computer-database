package com.excilys.webapp.controller;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.binding.mapper.impl.CompanyMapper;
import com.excilys.binding.mapper.impl.ComputerMapper;
import com.excilys.binding.mapper.impl.PageParametersMapper;
import com.excilys.binding.validation.ComputerValidator;
import com.excilys.binding.validation.PageParametersValidator;
import com.excilys.binding.validation.ValidatorException;
import com.excilys.binding.validation.ValidatorUtil;
import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.dto.PageParametersDTO;
import com.excilys.core.model.Computer;
import com.excilys.core.model.Page;
import com.excilys.core.model.PageParameters;
import com.excilys.service.service.ICompanyService;
import com.excilys.service.service.IComputerService;

@Controller
@RequestMapping("${path.base}")
public class ComputerController {

    @Autowired
    private IComputerService computerService;

    @Autowired
    private ComputerMapper computerMapper;

    @Autowired
    private ValidatorUtil validator;

    @Autowired
    private PageParametersMapper pageParamMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private ComputerValidator computerValidator;

    @Autowired
    private PageParametersValidator paramsValidator;

    /**
     * Display the dashboard with the list of computers.
     *
     * @param model Spring ModelMap.
     * @param param PageParameters object containing the url parameters.
     * @return servlet name
     */
    @RequestMapping(path = "${path.dashboard}", method = RequestMethod.GET)
    public String mainDashboard(ModelMap model, @Valid @ModelAttribute PageParametersDTO param, BindingResult errors, HttpServletRequest request) {
        paramsValidator.validate(param, errors);

        if (!errors.hasErrors()) {
            PageParameters p = pageParamMapper.fromDTO(param);
            Page<ComputerDTO> computerPage = computerMapper.map(computerService.getComputersPage(p));
            model.addAttribute("page", computerPage);
        } else {
            throw new ValidatorException(errors);
        }
        
        // Add the information "isAdmin" to the model
        model.addAttribute("isAdmin", request.isUserInRole("ROLE_ADMIN"));

        return "dashboard";
    }

    /**
     * Display the edit computer form.
     *
     * @param model Spring ModelMap
     * @param id    id of the computer to edit
     * @return servlet name
     */
    @RequestMapping(path = "${path.computer.edit}", method = RequestMethod.GET)
    public String getEditComputer(ModelMap model, @RequestParam long id) {

        Computer computer = this.computerService.getComputer(id);

        if (computer == null) {
            throw new NoSuchElementException();
        } else {
            model.addAttribute("computer", this.computerMapper.toDTO(computer));
        }

        List<CompanyDTO> companies = this.companyMapper.toDTO(this.companyService.getCompanies());
        model.addAttribute("companies", companies);

        return "editComputer";
    }

    /**
     * Edit computer with POST request.
     *
     * @param model    Spring ModelMap
     * @param computer DTO of the new values of the computer
     * @param errors   BindingResult containing validation errors
     * @return servlet name
     */
    @RequestMapping(path = "${path.computer.edit}", method = RequestMethod.POST)
    public String postEditComputer(ModelMap model, @Valid @ModelAttribute ComputerDTO computer, BindingResult errors) {

        this.computerValidator.validate(computer, errors);

        if (!errors.hasErrors()) {
            this.computerService.updateComputer(this.computerMapper.fromDTO(computer));
            return "redirect:${path.dashboard}";
        } else {
            model.addAttribute("computer", computer);
            model.addAttribute("errors", errors);

            List<CompanyDTO> companies = this.companyMapper.toDTO(this.companyService.getCompanies());
            model.addAttribute("companies", companies);

            return "editComputer";
        }
    }

    /**
     * Display the add computer form.
     *
     * @param model Spring ModelMap
     * @return servlet name
     */
    @RequestMapping(path = "${path.computer.add}", method = RequestMethod.GET)
    public String getAddComputer(ModelMap model) {
        List<CompanyDTO> companies = this.companyMapper.toDTO(this.companyService.getCompanies());
        model.addAttribute("companies", companies);
        return "addComputer";
    }

    /**
     * Add computer with POST request.
     *
     * @param model    Spring ModelMap
     * @param computer DTO of computer to create
     * @param errors   BindingResult containing validation errors
     * @return servlet name
     */
    @RequestMapping(path = "${path.computer.add}", method = RequestMethod.POST)
    public String postAddComputer(ModelMap model, @Valid @ModelAttribute ComputerDTO computer, BindingResult errors) {

        this.computerValidator.validate(computer, errors);

        if (!errors.hasErrors()) {
            this.computerService.createComputer(this.computerMapper.fromDTO(computer));
            return "redirect:${path.dashboard}";
        } else {
            model.addAttribute("computer", computer);
            model.addAttribute("errors", errors);
            return this.getAddComputer(model);
        }
    }

    /**
     * Delete list of computers with a POST request.
     *
     * @param selection ids of computers to delete
     * @return servlet name
     */
    @RequestMapping(path = "${path.computer.delete}", method = RequestMethod.POST)
    public String postDeleteComputer(@RequestParam String selection) {

        this.computerService.deleteComputers(
                Stream.of(selection.split(","))
                        .filter(this.validator::isIdValid)
                        .map(Long::parseLong)
                        .collect(Collectors.toList()));

        return "redirect:${path.dashboard}";
    }
}
