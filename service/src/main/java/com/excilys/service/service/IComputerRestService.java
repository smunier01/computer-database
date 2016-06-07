package com.excilys.service.service;

import com.excilys.core.dto.ComputerDTO;

import java.util.List;

public interface IComputerRestService {
    List<ComputerDTO> getList(int index);
    ComputerDTO getComputerById(long id);
    ComputerDTO createComputer(ComputerDTO computer);
    ComputerDTO updateComputer(ComputerDTO computer);
    void deleteComputer(long id);
}
