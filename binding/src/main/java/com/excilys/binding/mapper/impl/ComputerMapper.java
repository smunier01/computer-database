package com.excilys.binding.mapper.impl;

import com.excilys.binding.mapper.IComputerMapper;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.model.Company;
import com.excilys.core.model.Computer;
import com.excilys.core.model.Computer.ComputerBuilder;
import com.excilys.core.model.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


/**
 * implements different mapping methods to create or convert a Computer object.
 *
 * @author simon
 */
@Component
public class ComputerMapper implements IComputerMapper {

    @Override
    public ComputerDTO toDTO(Computer computer) {
        return new ComputerDTO(computer);
    }

    @Override
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

    @Override
    public Page<ComputerDTO> map(Page<Computer> page) {
        return new Page.Builder<ComputerDTO>()
                .list(this.toDTO(page.getList()))
                .params(page.getParams())
                .totalCount(page.getTotalCount())
                .build();
    }

}
