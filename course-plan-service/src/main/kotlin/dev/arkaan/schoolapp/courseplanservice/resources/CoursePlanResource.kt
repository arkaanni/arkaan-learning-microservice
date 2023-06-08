package dev.arkaan.schoolapp.courseplanservice.resources

import dev.arkaan.schoolapp.courseplanservice.api.CoursePlanRequest
import dev.arkaan.schoolapp.courseplanservice.client.StudentClient
import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/course-plan")
class CoursePlanResource(
    private val studentClient: StudentClient
) {
    @GET
    @Path("/dummy")
    @Produces(MediaType.APPLICATION_JSON)
    fun dummy(): Response {
        return Response.ok(studentClient.getDummyStudent()).build()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun createCoursePlan(@NotNull coursePlan: CoursePlanRequest) {
        // TODO
    }
}