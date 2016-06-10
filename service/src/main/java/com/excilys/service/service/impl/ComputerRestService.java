package com.excilys.service.service.impl;

import com.excilys.binding.mapper.impl.ComputerMapper;
import com.excilys.binding.mapper.impl.PageParametersMapper;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.model.Computer;
import com.excilys.core.model.PageParameters;
import com.excilys.service.service.IComputerRestService;
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
public class ComputerRestService implements IComputerRestService {

    // list of the variables
    private final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
    private static final String AUTH_USER = "user";
    private static final String AUTH_PASSWORD = "user";
    private static final String BASE_URL = "http://localhost:8080/cdb/rest/computer";
    private WebTarget target;

    @Autowired
    private ComputerMapper computerMapper;

    @Autowired
    private PageParametersMapper pageParametersMapper;

    /**
     * Default constructor.
     */
    public ComputerRestService() {
        HttpAuthenticationFeature auth = HttpAuthenticationFeature
                .universalBuilder()
                .credentialsForBasic(AUTH_USER, AUTH_PASSWORD)
                .build();
        Client client = ClientBuilder.newClient().register(auth).register(JacksonJsonProvider.class);
        target = client.target(BASE_URL);
    }

    @Override
    public List<Computer> getList() {
        this.LOGGER.debug("entering getList()");

        Response response = target
                .path("/")
                .request(MediaType.APPLICATION_JSON)
                .get();

        return computerMapper.fromDTO(response.readEntity(new GenericType<List<ComputerDTO>>() {
        }));
    }

    @Override
    public List<Computer> getList(PageParameters params) {
        this.LOGGER.debug("entering getList(params)");

        Response response = target
                .path("/page")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pageParametersMapper.toDTO(params)));

        return computerMapper.fromDTO(response.readEntity(new GenericType<List<ComputerDTO>>() {
        }));
    }

    @Override
    public Computer getComputerById(long id) {
        this.LOGGER.debug("entering getComputerById()");

        Response response = target
                .path("/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get();

        return computerMapper.fromDTO(response.readEntity(ComputerDTO.class));
    }

    @Override
    public Computer createComputer(Computer computer) {
        this.LOGGER.debug("entering createComputer()");

        Response response = target
                .path("/")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(computerMapper.toDTO(computer)));

        return computerMapper.fromDTO(response.readEntity(ComputerDTO.class));
    }

    @Override
    public Computer updateComputer(Computer computer) {
        this.LOGGER.debug("entering updateComputer()");

        Response response = target
                .path("/" + computer.getId())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(computerMapper.toDTO(computer)));

        return computerMapper.fromDTO(response.readEntity(ComputerDTO.class));
    }

    @Override
    public void deleteComputer(long id) {
        this.LOGGER.debug("entering deleteComputer()");
        target.path("/" + id).request(MediaType.APPLICATION_JSON).delete();
    }
}
