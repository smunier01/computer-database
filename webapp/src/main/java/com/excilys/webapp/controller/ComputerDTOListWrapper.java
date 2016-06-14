package com.excilys.webapp.controller;

import com.excilys.core.dto.ComputerDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixelfeather on 10/06/16.
 */
public class ComputerDTOListWrapper {
    private List<ComputerDTO> dtos = new ArrayList();

    public List<ComputerDTO> getDtos() {
        return dtos;
    }

    public void setDtos(List<ComputerDTO> dtos) {
        this.dtos = dtos;
    }
}
