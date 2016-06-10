package com.excilys.service.doublon.service;

import com.excilys.binding.doublon.Hamming;
import com.excilys.binding.doublon.Levenshtein;
import com.excilys.binding.doublon.SimilarityCalculator;
import com.excilys.binding.mapper.DTOMapper;
import com.excilys.core.doublon.model.Rapport;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.service.impl.ComputerService;

import java.util.List;

public class DoublonService {

    private ComputerService computerService;
    private DTOMapper mapper;


    private SimilarityCalculator hamming = new Hamming();
    private SimilarityCalculator levenshtein = new Levenshtein();

    public Rapport getRapport(List<ComputerDTO> computers) {
        List<ComputerDTO> computersInBase = mapper.toDTO(this.computerService.getAll());
        Rapport retVal = new Rapport();
        for (ComputerDTO cTemp : computers) {
            // TODO: to implements
        }
        return retVal;
    }


}
