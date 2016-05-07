package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.model.Page;

/**
 * implements different mapping methods to create or convert a Computer object.
 *
 * @author excilys
 */
public enum ComputerMapper {

    INSTANCE;

    private LocalDateMapper localDateMapper = LocalDateMapper.getInstance();

    /**
     * default constructor for the singleton.
     */
    private ComputerMapper() {

    }

    /**
     * public accessor for the singleton.
     *
     * @return return unique instance of the singleton
     */
    public static ComputerMapper getInstance() {
        return INSTANCE;
    }

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
     * Creates a Computer object form a ComputerDTO object.
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
     */
    public Computer map(ResultSet rs) throws SQLException, MapperException {

        Long id = rs.getLong("id");
        String name = rs.getString("name");

        LocalDate introduced = this.localDateMapper.fromTimestamp(rs.getTimestamp("introduced"));
        LocalDate discontinued = this.localDateMapper.fromTimestamp(rs.getTimestamp("discontinued"));

        Long companyId = rs.getLong("company_id");

        Company company = null;

        if (companyId > 0) {

            String companyName = rs.getString("company_name");

            if ((companyName == null) || "".equals(companyName)) {
                // there should never be a null or empty name when the id is not
                // null.
                throw new MapperException();
            } else {
                company = new Company(companyId, companyName);
            }

        }

        return new Computer.ComputerBuilder().id(id).name(name).introduced(introduced).discontinued(discontinued)
                .company(company).build();
    }

    /**
     * Creates a ComputerDTO object from a HttpServletRequest.
     *
     * @param request
     * @return
     */
    public ComputerDTO map(HttpServletRequest request) {

        ComputerDTO.Builder builder = new ComputerDTO.Builder();

        builder.id(request.getParameter("id"));
        builder.name(request.getParameter("computerName"));
        builder.introduced(request.getParameter("introduced"));
        builder.discontinued(request.getParameter("discontinued"));
        builder.companyId(request.getParameter("companyId"));

        return builder.build();

    }

    /**
     * convert a list of Computer to a list of ComputerDTO.
     *
     * @param companies
     * @return
     */
    public List<ComputerDTO> map(List<Computer> computers) {
        return computers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Page<ComputerDTO> map(Page<Computer> page) {

        return new Page.Builder<ComputerDTO>().list(this.map(page.getList())).params(page.getParams())
                .totalCount(page.getTotalCount()).build();
    }

}
