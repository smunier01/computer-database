package com.excilys.core.doublon.model;

import com.excilys.core.dto.ComputerDTO;

import java.util.ArrayList;
import java.util.List;

public class Conflict {

    // list of the variable
    private ComputerDTO origin;
    private List<ComputerDTO> conflictComputers;

    /**
     * Default constructor.
     */
    public Conflict() {
        this(new ComputerDTO());
    }
    public Conflict(ComputerDTO origin, List<ComputerDTO> conflictComputers) {
        this.origin = origin;
        this.conflictComputers = conflictComputers;
    }

    /**
     * Constructor with a computer.
     * @param computer of the origin
     */
    public Conflict(ComputerDTO computer) {
        this.origin = computer;
        this.conflictComputers = new ArrayList<>();
    }

    public ComputerDTO getOrigin() {
        return origin;
    }
    public List<ComputerDTO> getConflictComputers() {
        return conflictComputers;
    }
    public void setOrigin(ComputerDTO origin) {
        this.origin = origin;
    }
    public void setConflictComputers(List<ComputerDTO> conflictComputers) {
        this.conflictComputers = conflictComputers;
    }
}
