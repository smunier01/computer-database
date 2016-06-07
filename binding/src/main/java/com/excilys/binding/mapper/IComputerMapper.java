package com.excilys.binding.mapper;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.model.Computer;
import com.excilys.core.model.Page;

public interface IComputerMapper extends DTOMapper<ComputerDTO, Computer> {
    
    /**
     * convert a page of Computer to a page of ComputerDTO.
     *
     * @param page page to convert
     * @return page result
     */
    Page<ComputerDTO> map(Page<Computer> page);
}
