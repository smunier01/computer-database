package com.excilys.service.company;

import com.excilys.core.model.Company;
import com.excilys.core.model.PageParameters;

import java.util.List;


public interface ICompanyRestService {
    List<Company> getList();

    List<Company> getList(PageParameters params);

    Company getCompanyById(long id);

    Company createCompany(Company computer);

    Company updateCompany(Company computer);

    void deleteCompany(long id);
}
