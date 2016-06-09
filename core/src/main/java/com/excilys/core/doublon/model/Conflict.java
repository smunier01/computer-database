package com.excilys.core.doublon.model;

import com.excilys.core.dto.ComputerDTO;

import java.util.ArrayList;

/**
 * Created by excilys on 09/06/16.
 */
public class Conflict {

    // list of the variable
    private ComputerDTO origin;
    private ArrayList<ComputerDTO> conflictComputers;

    /**
     * Default constructor.
     */
    public Conflict() {
        this(new ComputerDTO());
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
    public ArrayList<ComputerDTO> getConflictComputers() {
        return conflictComputers;
    }
}
