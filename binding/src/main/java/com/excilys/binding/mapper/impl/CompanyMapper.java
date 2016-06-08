package com.excilys.binding.mapper.impl;

import com.excilys.binding.mapper.ICompanyMapper;
import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.model.Company;
import com.excilys.core.model.Page;
import org.springframework.stereotype.Component;

/**
 * implements different mapping methods to create or convert a Company object.
 *
 * @author simon
 */
@Component
public class CompanyMapper implements ICompanyMapper {

    @Override
    public CompanyDTO toDTO(Company company) {
        return new CompanyDTO(company);
    }

    @Override
    public Company fromDTO(CompanyDTO dto) {
        return new Company(Long.parseLong(dto.getId()), dto.getName());
    }

    @Override
    public Page<CompanyDTO> map(Page<Company> page) {
        return new Page.Builder<CompanyDTO>()
                .list(this.toDTO(page.getList()))
                .params(page.getParams())
                .totalCount(page.getTotalCount())
                .build();
    }
}
