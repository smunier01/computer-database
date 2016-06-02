package com.excilys.binding.mapper;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.model.Company;
import com.excilys.core.model.Computer;
import com.excilys.core.model.Computer.ComputerBuilder;
import com.excilys.core.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Autowired
    private LocalDateMapper localDateMapper;

    /**
     * Creates a ComputerDTO object from a Computer.
     *
     * @param computer
     *            computer
     * @return ComputerDTo object
     */
    public ComputerDTO toDTO(Computer computer) {
        return new ComputerDTO(computer);
    }

    /**
     * Creates a Computer object from a ComputerDTO object.
     *
     * @param computer
     *            ComputerDTO instance to convert.
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
     * Creates a Computer object from a jdbc ResultSet.
     *
     * @param rs
     *            ResultSet containing the informations about the computer
     * @return instance of the computer created
     * @throws SQLException
     *             exception
     * @throws MapperException
     *             exception
     */
    public Computer map(ResultSet rs) throws SQLException, MapperException {

        Long id = rs.getLong(1);
        String name = rs.getString(2);

        LocalDate introduced = this.localDateMapper.fromTimestamp(rs.getTimestamp(3));
        LocalDate discontinued = this.localDateMapper.fromTimestamp(rs.getTimestamp(4));

        Long companyId = rs.getLong(5);

        Company company = null;

        if (companyId > 0) {

            String companyName = rs.getString(6);

            if ((companyName == null) || "".equals(companyName)) {
                // there should never be a null or empty name when the id is not
                // null.
                throw new MapperException();
            } else {
                company = new Company(companyId, companyName);
            }

        }

        return new Computer.ComputerBuilder()
                .id(id)
                .name(name)
                .introduced(introduced)
                .discontinued(discontinued)
                .company(company)
                .build();
    }

    /**
     * convert a list of Computer to a list of ComputerDTO.
     *
     * @param companies
     *            list of Computer
     * @return list of ComputerDTO
     */
    public List<ComputerDTO> map(List<Computer> computers) {
        return computers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Page<ComputerDTO> map(Page<Computer> page) {
        return new Page.Builder<ComputerDTO>().list(this.map(page.getList())).params(page.getParams())
                .totalCount(page.getTotalCount()).build();
    }

}
