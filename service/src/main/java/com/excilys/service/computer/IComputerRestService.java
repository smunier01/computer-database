package com.excilys.service;

import com.excilys.core.model.Computer;
import com.excilys.core.model.PageParameters;

import java.util.List;

public interface IComputerRestService {
    List<Computer> getList();

    List<Computer> getList(PageParameters params);

    Computer getComputerById(long id);

    Computer createComputer(Computer computer);

    Computer updateComputer(Computer computer);

    void deleteComputer(long id);
}
