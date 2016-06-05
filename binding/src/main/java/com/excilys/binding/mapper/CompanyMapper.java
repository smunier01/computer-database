package com.excilys.binding.mapper;

import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.model.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * implements different mapping methods to create or convert a Company object.
 *
 * @author simon
 */
@Component
public class CompanyMapper {

    /**
     * convert from Company to CompanyDTO.
     *
     * @param company company
     * @return CompanyDTO object
     */
    public CompanyDTO toDTO(Company company) {
        return new CompanyDTO(company);
    }

    /**
     * convert a list of Company to a list of CompanyDTO.
     *
     * @param companies
     * @return
     */
    public List<CompanyDTO> map(List<Company> companies) {
        return companies.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
