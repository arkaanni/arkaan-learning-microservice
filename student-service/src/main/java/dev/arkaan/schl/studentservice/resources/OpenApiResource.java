package dev.arkaan.schl.studentservice.resources;

import io.swagger.v3.jaxrs2.integration.resources.BaseOpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.Set;

@Path("/apidocs/student-service")
@OpenAPIDefinition(
    info = @Info(
        title = "student-service"
    )
)
public class OpenApiResource extends BaseOpenApiResource {

    public OpenApiResource() {
        resourcePackages = Set.of("dev.arkaan.schl.studentservice.resources");
    }

    @GET
    @Operation(hidden = true)
    @Produces("application/json")
    public Response get(@Context HttpHeaders headers, @Context UriInfo uriInfo) throws Exception {
        return getOpenApi(headers, null, null, uriInfo, "json");
    }
}
