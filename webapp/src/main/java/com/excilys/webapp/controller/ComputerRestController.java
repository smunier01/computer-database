package com.excilys.webapp.controller;

import com.excilys.binding.mapper.impl.ComputerMapper;
import com.excilys.binding.mapper.impl.PageParametersMapper;
import com.excilys.binding.validation.ComputerValidator;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.dto.PageParametersDTO;
import com.excilys.core.model.Computer;
import com.excilys.core.model.Page;
import com.excilys.core.model.PageParameters;
import com.excilys.service.service.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ComputerDTO> getComputer(@PathVariable("id") long id) {
        ComputerDTO computer = computerMapper.toDTO(computerService.getComputer(id));
        // TODO: I don't remember if this throw an exception or returns null
        if (computer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(computer, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<ComputerDTO> create(@Valid @RequestBody ComputerDTO computer, BindingResult errors) {

        // TODO: If I remember correctly, a special code (CONFLICT ?) should be returned if the element already exist

        this.computerValidator.validate(computer, errors);

        if (!errors.hasErrors()) {
            Computer created = this.computerService.createComputer(this.computerMapper.fromDTO(computer));
            return new ResponseEntity<>(this.computerMapper.toDTO(created), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<ComputerDTO> update(@PathVariable("id") long id, @Valid @RequestBody ComputerDTO computer, BindingResult errors) {
        // TODO: not cool to cast to long, to cast back to string and then cast back to long..
        computer.setId(Long.toString(id));

        this.computerValidator.validate(computer, errors);

        if (!errors.hasErrors()) {
            // TODO: if the computer doesn't exist, I should return a NOT_FOUND.
            this.computerService.updateComputer(this.computerMapper.fromDTO(computer));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.DELETE)
    public ResponseEntity<ComputerDTO> deleteComputer(@PathVariable("id") long id) {
        // TODO: I should probably do something in case of errors or not founds ...
        computerService.deleteComputer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
