package dev.arkaan.schoolapp.studentservice.resources;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(
            ContainerRequestContext requestContext,
            ContainerResponseContext responseContext
    ) throws IOException {
        responseContext
            .getHeaders()
            .add(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        responseContext
            .getHeaders()
            .addAll(CrossOriginFilter.ACCESS_CONTROL_ALLOW_METHODS_HEADER, "GET", "POST", "PUT", "DELETE", "OPTIONS");
        responseContext
            .getHeaders()
            .addAll(CrossOriginFilter.ACCESS_CONTROL_ALLOW_HEADERS_HEADER,
                    "X-Requested-With", "Content-Type", "Accept", "Origin");
    }
}