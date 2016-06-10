package com.excilys.webapp.controller;

import com.excilys.binding.mapper.impl.CompanyMapper;
import com.excilys.binding.mapper.impl.PageParametersMapper;
import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.dto.PageParametersDTO;
import com.excilys.core.model.Company;
import com.excilys.core.model.PageParameters;
import com.excilys.service.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${path.rest.company}")
public class CompanyRestController {

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private PageParametersMapper pageParamMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<CompanyDTO>> listAll() {
        PageParameters p = pageParamMapper.fromDTO(new PageParametersDTO());
        return new ResponseEntity<>(companyMapper.toDTO(companyService.getCompanies(p)), HttpStatus.OK);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<List<CompanyDTO>> listAll(@Valid @RequestBody PageParametersDTO params, BindingResult errors) {
        if (!errors.hasErrors()) {
            PageParameters p = pageParamMapper.fromDTO(params);
            return new ResponseEntity<>(companyMapper.toDTO(companyService.getCompanies(p)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CompanyDTO> get(@PathVariable("id") long id) {
        Company company = companyService.getCompany(id);

        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(companyMapper.toDTO(company), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<CompanyDTO> create(@Valid @RequestBody CompanyDTO company, BindingResult errors) {
        if (!errors.hasErrors()) {
            Company created = this.companyService.createCompany(this.companyMapper.fromDTO(company));
            return new ResponseEntity<>(this.companyMapper.toDTO(created), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<CompanyDTO> update(@PathVariable("id") long id, @Valid @RequestBody CompanyDTO company, BindingResult errors) {

        if (!errors.hasErrors()) {
            Company c = this.companyMapper.fromDTO(company);
            c.setId(id);
            this.companyService.updateCompany(c);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value="/getautocomplete/{entry}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getSearchList(@PathVariable("entry") String entry) {
        List<String> result = this.companyService.findAutocompleteResult(entry);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
