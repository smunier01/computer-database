package com.excilys.service.service.impl;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.service.IComputerRestService;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
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

    private static final String BASE_URL = "http://localhost:8080/cdb/rest/computer";

    private Client client;

    private WebTarget target;

    public ComputerRestService() {
        HttpAuthenticationFeature auth = HttpAuthenticationFeature
                .universalBuilder()
                .credentialsForBasic("user", "user")
                .build();

        client = ClientBuilder.newClient().register(auth).register(JacksonJsonProvider.class);
        target = client.target(BASE_URL);
    }

    @Override
    public List<ComputerDTO> getList(int index) {
        Response response = target
                .path("/")
                .request(MediaType.APPLICATION_JSON)
                .get();

        return response.readEntity(new GenericType<List<ComputerDTO>>(){});
    }

    @Override
    public ComputerDTO getComputerById(long id) {
        Response response = target
                .path("/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get();

        return response.readEntity(ComputerDTO.class);
    }

    @Override
    public ComputerDTO createComputer(ComputerDTO computer) {
        Response response = target
                .path("/")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(computer));

        return response.readEntity(ComputerDTO.class);
    }

    @Override
    public ComputerDTO updateComputer(ComputerDTO computer) {
        return null;
    }

    @Override
    public void deleteComputer(long id) {

    }
}
