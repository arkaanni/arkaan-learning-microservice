package dev.arkaan.schoolapp.courseplanservice.resources

import dev.arkaan.schoolapp.courseplanservice.api.Enroll
import dev.arkaan.schoolapp.courseplanservice.db.EnrollmentDao
import jakarta.inject.Inject
import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.container.AsyncResponse
import jakarta.ws.rs.container.Suspended
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Path("/enrollment")
class EnrollmentResource @Inject constructor(
    private val enrollmentDao: EnrollmentDao,
    private val coroutineScope: CoroutineScope
) {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun enroll(
        @Suspended asyncResponse: AsyncResponse,
        @NotNull enroll: Enroll
    ) {
        coroutineScope.launch {
            try {
                enrollmentDao.enroll(enroll.coursePlanId, enroll.studentId)
                asyncResponse.resume(Response.ok().build())
            } catch (e: Exception) {
                asyncResponse.resume(WebApplicationException())
            }
        }
    }
}