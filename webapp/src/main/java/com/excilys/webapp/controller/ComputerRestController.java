package com.excilys.webapp.controller;

import com.excilys.binding.mapper.impl.ComputerMapper;
import com.excilys.binding.mapper.impl.PageParametersMapper;
import com.excilys.binding.validation.ComputerValidator;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.dto.PageParametersDTO;
import com.excilys.core.model.Computer;
import com.excilys.core.model.Page;
import com.excilys.core.model.PageParameters;
import com.excilys.service.computer.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${path.rest.computer}")
public class ComputerRestController {

    @Autowired
    private IComputerService computerService;

    @Autowired
    private PageParametersMapper pageParamMapper;

    @Autowired
    private ComputerMapper computerMapper;

    @Autowired
    private ComputerValidator computerValidator;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ComputerDTO>> listAll() {
        PageParameters p = pageParamMapper.fromDTO(new PageParametersDTO());
        Page<ComputerDTO> computerPage = computerMapper.map(computerService.getComputersPage(p));
        return new ResponseEntity<>(computerPage.getList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<List<ComputerDTO>> listAll(@Valid @RequestBody PageParametersDTO params, BindingResult errors) {
        if (!errors.hasErrors()) {
            PageParameters p = pageParamMapper.fromDTO(params);
            Page<ComputerDTO> computerPage = computerMapper.map(computerService.getComputersPage(p));
            return new ResponseEntity<>(computerPage.getList(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ComputerDTO> get(@PathVariable("id") long id) {
        Computer computer = computerService.getComputer(id);
        if (computer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(computerMapper.toDTO(computer), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<ComputerDTO> create(@Valid @RequestBody ComputerDTO computer, BindingResult errors) {

        this.computerValidator.validate(computer, errors);

        if (!errors.hasErrors()) {
            Computer tmp = this.computerMapper.fromDTO(computer);
            tmp.setId(null);
            Computer created = this.computerService.createComputer(tmp);
            return new ResponseEntity<>(this.computerMapper.toDTO(created), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<ComputerDTO> update(@PathVariable("id") long id, @Valid @RequestBody ComputerDTO computer, BindingResult errors) {

        computer.setId(Long.toString(id));

        this.computerValidator.validate(computer, errors);

        if (!errors.hasErrors()) {
            this.computerService.updateComputer(this.computerMapper.fromDTO(computer));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.DELETE)
    public ResponseEntity<ComputerDTO> deleteComputer(@PathVariable("id") long id) {
        computerService.deleteComputer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/getautocomplete/{entry}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getSearchList(@PathVariable("entry") String entry) {
        List<String> result = this.computerService.findAutocompleteResult(entry);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
