package dev.arkaan.schoolapp.courseplanservice.resources

import dev.arkaan.schoolapp.courseplanservice.api.CoursePlanRequest
import dev.arkaan.schoolapp.courseplanservice.client.StudentClient
import dev.arkaan.schoolapp.courseplanservice.client.SubjectClient
import dev.arkaan.schoolapp.courseplanservice.db.CoursePlanDao
import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.*
import jakarta.ws.rs.container.AsyncResponse
import jakarta.ws.rs.container.Suspended
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.coroutines.*

@Path("/course-plan")
class CoursePlanResource(
    private val studentClient: StudentClient,
    private val subjectClient: SubjectClient,
    private val coroutineScope: CoroutineScope,
    private val coursePlanDao: CoursePlanDao
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun createCoursePlan(
        @Suspended response: AsyncResponse,
        @NotNull coursePlan: CoursePlanRequest
    ) {
        coroutineScope.launch {
            with(coursePlan) {
                try {
                    studentClient.checkStudentExist(studentId)
                    subjectClient.checkSubjectExist(subjectCode)
                } catch (e: WebApplicationException) {
                    response.resume(e)
                    cancel()
                    return@launch
                }
                coursePlanDao.addCoursePlan(studentId, subjectCode, semester, year)
            }
            response.resume(Response.ok().build())
        }
    }
}