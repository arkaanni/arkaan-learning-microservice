package dev.arkaan.schoolapp.courseplanservice.resources

import io.swagger.v3.jaxrs2.integration.resources.BaseOpenApiResource
import io.swagger.v3.oas.annotations.Operation
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo

@Path("/course-plan-service.{type:json|yaml}")
class OpenApiResource : BaseOpenApiResource() {

    init {
        resourcePackages = setOf("dev.arkaan.schoolapp.courseplanservice.resources")
    }

    @GET
    @Produces("application/json", "application/yaml")
    @Operation(hidden = true)
    fun get(
        @Context headers: HttpHeaders,
        @Context uriInfo: UriInfo,
        @PathParam("type") type: String
    ): Response {
        return getOpenApi(headers, null, null, uriInfo, type)
    }
}