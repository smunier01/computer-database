package com.excilys.service.company.impl;

import com.excilys.binding.mapper.impl.CompanyMapper;
import com.excilys.binding.mapper.impl.PageParametersMapper;
import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.model.Company;
import com.excilys.core.model.PageParameters;
import com.excilys.service.company.ICompanyRestService;
import com.excilys.service.computer.impl.ComputerService;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Service
public class CompanyRestService implements ICompanyRestService {

    // list of the variables
    private final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
    private static final String AUTH_USER = "user";
    private static final String AUTH_PASSWORD = "user";
    private static final String BASE_URL = "http://localhost:8080/cdb/rest/company";
    private WebTarget target;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private PageParametersMapper pageParametersMapper;

    /**
     * Default constructor.
     */
    public CompanyRestService() {
        HttpAuthenticationFeature auth = HttpAuthenticationFeature
                .universalBuilder()
                .credentialsForBasic(AUTH_USER, AUTH_PASSWORD)
                .build();

        Client client = ClientBuilder.newClient().register(auth).register(JacksonJsonProvider.class);
        target = client.target(BASE_URL);
    }

    @Override
    public List<Company> getList() {
        this.LOGGER.debug("entering getList()");

        Response response = target
                .path("/")
                .request(MediaType.APPLICATION_JSON)
                .get();

        return companyMapper.fromDTO(response.readEntity(new GenericType<List<CompanyDTO>>() {
        }));
    }

    @Override
    public List<Company> getList(PageParameters params) {
        this.LOGGER.debug("entering getList(params) {}", params);

        Response response = target
                .path("/page")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pageParametersMapper.toDTO(params)));

        return companyMapper.fromDTO(response.readEntity(new GenericType<List<CompanyDTO>>() {
        }));
    }

    @Override
    public Company getCompanyById(long id) {
        this.LOGGER.error("entering getCompanyById()");

        Response response = target
                .path("/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get();

        return companyMapper.fromDTO(response.readEntity(CompanyDTO.class));
    }

    @Override
    public Company createCompany(Company company) {
        this.LOGGER.error("entering createCompany()");

        Response response = target
                .path("/")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(companyMapper.toDTO(company)));

        return companyMapper.fromDTO(response.readEntity(CompanyDTO.class));
    }

    @Override
    public Company updateCompany(Company company) {
        this.LOGGER.error("entering updateCompany()");

        Response response = target
                .path("/" + company.getId())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(companyMapper.toDTO(company)));

        return companyMapper.fromDTO(response.readEntity(CompanyDTO.class));
    }

    @Override
    public void deleteCompany(long id) {
        this.LOGGER.error("entering deleteCompany()");
        target.path("/" + id).request(MediaType.APPLICATION_JSON).delete();
    }

}
