package com.excilys.service.doublon;

import com.excilys.binding.doublon.Hamming;
import com.excilys.binding.doublon.Levenshtein;
import com.excilys.binding.doublon.SimilarityCalculator;
import com.excilys.binding.mapper.IComputerMapper;
import com.excilys.core.conflict.Rapport;

import com.excilys.core.conflict.doublon.Doublon;
import com.excilys.core.conflict.doublon.DoublonRules;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.computer.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@Service("doublonService")
public class DoublonServiceImpl implements DoublonService {

    @Autowired
    private IComputerService computerService;

    @Autowired
    private IComputerMapper computerMapper;

    @Autowired
    private Validator computerValidator;

    private List<DoublonRules> rulesCheck;
    private List<DoublonRules> rulesRefuse;
    private SimilarityCalculator hamming = new Hamming();
    private SimilarityCalculator levenshtein = new Levenshtein();

    /**
     * The default constructor.
     */
    public DoublonServiceImpl() {
        // load the first rules
        this.rulesCheck = new ArrayList<>();
        this.rulesCheck.add(new DoublonRules(0.0, 100.0, 100.0, 60.0));
        this.rulesRefuse = new ArrayList<>();
        this.rulesRefuse.add(new DoublonRules(100.0, 100.0, 100.0, 70.0));

    }

    @Override
    public Rapport getRapport(List<ComputerDTO> computers) {
        List<ComputerDTO> computersInBase = computerMapper.toDTO(this.computerService.getAll());
        Rapport retVal = new Rapport();
        for (ComputerDTO computerOut : computers) {
            List<ComputerDTO> tList = check(computersInBase, computerOut, this.rulesRefuse);
            if (tList.isEmpty()) {
                tList.addAll(check(computersInBase, computerOut, this.rulesCheck));
                if (tList.isEmpty()) {
                    retVal.getToImport().add(computerOut);
                } else {
                    // TODO: need to add the conflicts
                    retVal.getToCheck().add(new Doublon(computerOut, tList));
                }
            } else {
                // TODO: need to add the conflicts
                retVal.getRefuse().add(new Doublon(computerOut, tList));
            }
        }
        return retVal;
    }

    /**
     * Use to check all the elements of the computers to check if we should import them in the database or not.
     *
     * @param computerIns to check
     * @param computerOut to check
     * @param rules       to use to check
     * @return the list of computer where we have a conflicts
     */
    private List<ComputerDTO> check(List<ComputerDTO> computerIns, ComputerDTO computerOut, List<DoublonRules> rules) {
        List<ComputerDTO> retVal = new ArrayList<>();
        for (ComputerDTO computerIn : computerIns) {
            for (DoublonRules rule : rules) {
                // TODO: need to check the date
                if ((levenshtein.getPercentSimilarity(computerIn.getName(), computerOut.getName()) >= rule.getName()) &&
                        (levenshtein.getPercentSimilarity(computerIn.getCompanyName(), computerOut.getCompanyName()) >= rule.getCompanyName())
                        ) {
                    retVal.add(computerIn);
                    break;
                }
            }
        }
        return retVal;
    }

}
