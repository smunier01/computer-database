package com.excilys.binding.mapper;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.model.Company;
import com.excilys.core.model.Computer;
import com.excilys.core.model.Computer.ComputerBuilder;
import com.excilys.core.model.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


/**
 * implements different mapping methods to create or convert a Computer object.
 *
 * @author simon
 */
@Component
public class ComputerMapper {

    /**
     * Creates a ComputerDTO object from a Computer.
     *
     * @param computer computer
     * @return ComputerDTo object
     */
    public ComputerDTO toDTO(Computer computer) {
        return new ComputerDTO(computer);
    }

    /**
     * Creates a Computer object from a ComputerDTO object.
     *
     * @param computer ComputerDTO instance to convert.
     * @return instance of a Computer
     */
    public Computer fromDTO(ComputerDTO computer) {

        ComputerBuilder builder = new Computer.ComputerBuilder();

        builder.name(computer.getName());

        if ((computer.getId() != null) && !computer.getId().isEmpty()) {
            builder.id(Long.parseLong(computer.getId()));
        }

        if ((computer.getIntroduced() != null) && !"".equals(computer.getIntroduced())) {
            builder.introduced(LocalDate.parse(computer.getIntroduced()));
        }

        if ((computer.getDiscontinued() != null) && !"".equals(computer.getDiscontinued())) {
            builder.discontinued(LocalDate.parse(computer.getDiscontinued()));
        }

        if ((computer.getCompanyId() != null) && !"".equals(computer.getCompanyId())) {
            builder.company(new Company(Long.parseLong(computer.getCompanyId()), computer.getCompanyName()));
        }

        return builder.build();
    }

    /**
     * convert a list of Computer to a list of ComputerDTO.
     *
     * @param computers list of Computer
     * @return list of ComputerDTO
     */
    public List<ComputerDTO> map(List<Computer> computers) {
        return computers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * convert a page of Computer to a page of ComputerDTO.
     *
     * @param page page to convert
     * @return page result
     */
    public Page<ComputerDTO> map(Page<Computer> page) {
        return new Page.Builder<ComputerDTO>().list(this.map(page.getList())).params(page.getParams())
                .totalCount(page.getTotalCount()).build();
    }

}
