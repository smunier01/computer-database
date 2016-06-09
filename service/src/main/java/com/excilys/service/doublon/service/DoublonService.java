package com.excilys.service.doublon.service;

import com.excilys.core.doublon.model.Rapport;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.service.impl.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoublonService {

    @Autowired
    private ComputerService computerService;

    public Rapport getRapport(List<ComputerDTO> computers) {
        Rapport retVal = new Rapport();



        return retVal;
    }


}
