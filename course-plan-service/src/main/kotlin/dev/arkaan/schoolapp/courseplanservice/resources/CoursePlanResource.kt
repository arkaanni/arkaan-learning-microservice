package dev.arkaan.schoolapp.courseplanservice.resources

import dev.arkaan.schoolapp.courseplanservice.api.CoursePlanRequest
import dev.arkaan.schoolapp.courseplanservice.client.ScheduleClient
import dev.arkaan.schoolapp.courseplanservice.client.StudentClient
import dev.arkaan.schoolapp.courseplanservice.client.SubjectClient
import dev.arkaan.schoolapp.courseplanservice.db.CoursePlanDao
import jakarta.inject.Inject
import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.*
import jakarta.ws.rs.container.AsyncResponse
import jakarta.ws.rs.container.Suspended
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.coroutines.*

@Path("/course-plan")
class CoursePlanResource @Inject constructor(
    private val studentClient: StudentClient,
    private val subjectClient: SubjectClient,
    private val scheduleClient: ScheduleClient,
    private val coroutineScope: CoroutineScope,
    private val coursePlanDao: CoursePlanDao
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun createCoursePlan(
        @Suspended response: AsyncResponse,
        @NotNull coursePlan: CoursePlanRequest
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            with(coursePlan) {
                try {
                    studentClient.checkStudentExist(studentId)
                    subjectClient.checkSubjectExist(subjectCode)
                    scheduleClient.checkScheduleExist(scheduleId)
                } catch (e: WebApplicationException) {
                    response.resume(e)
                    cancel()
                    return@launch
                }
                coursePlanDao.addOne(studentId, subjectCode, semester, year, scheduleId)
            }
            response.resume(Response.ok().build())
            yield()
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllCoursePlan(
        @Suspended response: AsyncResponse
    ) {
        coroutineScope.launch {
            val q = async { coursePlanDao.getAll() }
            val res = q.await()
            yield()
            response.resume(Response.ok(res).build())
        }
    }
}