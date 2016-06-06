package com.excilys.webapp.controller;

import com.excilys.binding.mapper.ComputerMapper;
import com.excilys.binding.mapper.PageParametersMapper;
import com.excilys.binding.validation.ComputerValidator;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.dto.PageParametersDTO;
import com.excilys.core.model.Computer;
import com.excilys.core.model.Page;
import com.excilys.core.model.PageParameters;
import com.excilys.service.service.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class ComputerRestController {

    @Autowired
    private IComputerService computerService;

    @Autowired
    private PageParametersMapper pageParamMapper;

    @Autowired
    private ComputerMapper computerMapper;

    @Autowired
    private ComputerValidator computerValidator;

    @RequestMapping(value = "/computer/", method = RequestMethod.GET)
    public ResponseEntity<List<ComputerDTO>> listAllComputers() {
        PageParameters p = pageParamMapper.fromDTO(new PageParametersDTO());
        Page<ComputerDTO> computerPage = computerMapper.map(computerService.getComputersPage(p));
        return new ResponseEntity<>(computerPage.getList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/computer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComputerDTO> getComputer(@PathVariable("id") long id) {
        Computer computer = computerService.getComputer(id);
        // TODO: I don't remember if this throw an exception or returns null
        if (computer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/computer/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@Valid @RequestBody ComputerDTO computer, UriComponentsBuilder ucBuilder, BindingResult errors) {

        // TODO: If I remember correctly, a special code (CONFLICT ?) should be returned if the element already exist

        this.computerValidator.validate(computer, errors);

        if (!errors.hasErrors()) {
            Computer created = this.computerService.createComputer(this.computerMapper.fromDTO(computer));

            // after a succeful POST, we need to redirect to the GET page using the new ID
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/computer/{id}").buildAndExpand(created.getId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/computer/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ComputerDTO> updateComputer(@PathVariable("id") long id, @Valid @RequestBody ComputerDTO computer, BindingResult errors) {
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

    @RequestMapping(value = "/computer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ComputerDTO> deleteComputer(@PathVariable("id") long id) {
        // TODO: I should probably do something in case of errors or not founds ...
        computerService.deleteComputer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
