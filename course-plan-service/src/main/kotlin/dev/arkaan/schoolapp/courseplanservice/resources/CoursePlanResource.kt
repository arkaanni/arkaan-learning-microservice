package dev.arkaan.schoolapp.courseplanservice.resources

import dev.arkaan.schoolapp.courseplanservice.client.StudentClient
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response

@Path("/course-plan")
class CoursePlanResource(
    private val studentClient: StudentClient
) {
    @GET
    @Path("/dummy")
    fun dummy(): Response {
        return Response.ok(studentClient.getDummy()).build()
    }
}