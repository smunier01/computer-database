package com.excilys.service;

import com.excilys.core.model.Computer;
import com.excilys.core.model.PageParameters;

import java.util.List;

public interface IComputerRestService {

    /**
     * Get all the computers of the data base.
     *
     * @return the list of computers
     */
    List<Computer> getList();

    /**
     * Return the list of computers in function of the page parameters.
     *
     * @param params of the page to get
     * @return the list of computer
     */
    List<Computer> getList(PageParameters params);

    /**
     * Get the computers in function of his id.
     *
     * @param id to get the computer
     * @return the computer
     */
    Computer getComputerById(long id);

    /**
     * Create a computer in the data base and return him with his new id.
     *
     * @param computer to create
     * @return the computer
     */
    Computer createComputer(Computer computer);

    /**
     * Update the computers.
     *
     * @param computer to update
     * @return the updated computer
     */
    Computer updateComputer(Computer computer);

    /**
     * Computer to delete in function of his id.
     *
     * @param id of the computer o delete
     */
    void deleteComputer(long id);
}
