package com.distribuida.authors.Check;

import com.distribuida.authors.clients.AuthorRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Liveness
public class SimpleCheckHealth {

    @Inject
    @RestClient
    AuthorRestClient clientAuthors;

    public HealthCheckResponse call(){
        try {
            clientAuthors.getById(1);
        }
        catch (WebApplicationException ex) {
            ex.printStackTrace();
        }

        return HealthCheckResponse.up("Servicio Autor");
    }


}
