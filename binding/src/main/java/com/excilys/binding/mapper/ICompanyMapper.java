package com.excilys.binding.mapper;

import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.model.Company;
import com.excilys.core.model.Page;

public interface ICompanyMapper extends DTOMapper<CompanyDTO, Company>{
    /**
     * convert a page of Company to a page of CompanyDTO.
     *
     * @param page page to convert
     * @return page result
     */
    Page<CompanyDTO> map(Page<Company> page);
}
