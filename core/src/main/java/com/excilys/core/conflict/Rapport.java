package com.excilys.core.conflict;

import com.excilys.core.dto.ComputerDTO;

import java.util.ArrayList;
import java.util.List;

public class Rapport {

    // list of the variables
    private List<ComputerDTO> toImport;
    private List<Conflict> toCheck;
    private List<Conflict> refuse;

    /**
     * The default constructor.
     */
    public Rapport() {
        this(new ArrayList<>());
    }

    /**
     * Constructor with the list of computerDTO.
     * @param toImport list of ComputerDTO
     */
    public Rapport(List<ComputerDTO> toImport) {
        this.toImport = toImport;
        this.toCheck = new ArrayList<>();
        this.refuse = new ArrayList<>();
    }

    public List<ComputerDTO> getToImport() {
        return toImport;
    }

    public List<Conflict> getRefuse() {
        return refuse;
    }

    public List<Conflict> getToCheck() {
        return toCheck;
    }

    public void setToImport(List<ComputerDTO> toImport) {
        this.toImport = toImport;
    }

    public void setToCheck(List<Conflict> toCheck) {
        this.toCheck = toCheck;
    }

    public void setRefuse(List<Conflict> refuse) {
        this.refuse = refuse;
    }

    public boolean hasErrors() {
        return refuse.size() > 0;
    }

}
