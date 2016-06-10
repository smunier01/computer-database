package com.excilys.core.conflict;

import com.excilys.core.dto.ComputerDTO;

public abstract class Conflict {

    // list of the variable
    protected ComputerDTO computerDTO;

    public void setComputerDTO(ComputerDTO computerDTO) {
        this.computerDTO = computerDTO;
    }

    public ComputerDTO getComputerDTO() {
        return computerDTO;
    }
}
