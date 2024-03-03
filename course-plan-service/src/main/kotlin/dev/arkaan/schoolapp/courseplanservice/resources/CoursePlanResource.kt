package dev.arkaan.schoolapp.courseplanservice.resources

import dev.arkaan.schoolapp.courseplanservice.api.CoursePlanRequest
import dev.arkaan.schoolapp.courseplanservice.client.ScheduleClient
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
    private val subjectClient: SubjectClient,
    private val scheduleClient: ScheduleClient,
    private val coroutineScope: CoroutineScope,
    private val coursePlanDao: CoursePlanDao
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createCoursePlan(
        @Suspended response: AsyncResponse,
        @NotNull coursePlan: CoursePlanRequest
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            with(coursePlan) {
                val checkSubjectSchedule = checkSubjectAndSchedule(this@launch, subjectCode, scheduleId)
                if (!checkSubjectSchedule.first) {
                    response.resume(WebApplicationException(checkSubjectSchedule.second, 400))
                    return@launch
                }
                coursePlanDao.addOne(subjectCode, semester, year, scheduleId)
                response.resume(Response.ok().build())
            }
        }
    }

    private suspend fun checkSubjectAndSchedule(scope: CoroutineScope, subjectCode: String, scheduleId: String): Pair<Boolean, String> {
        val subjectExists =  scope.async { subjectClient.checkSubjectExist(subjectCode) }
        val scheduleExists = scope.async { scheduleClient.checkScheduleExist(scheduleId) }
        val awaitAll = awaitAll(subjectExists, scheduleExists)
        if (!awaitAll[0]) {
            return Pair(false, "Subject does not exists.")
        }
        if (!awaitAll[1]) {
            return Pair(false, "Schedule does not exists.")
        }
        return Pair(true, "Ok")
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllCoursePlan(
        @Suspended response: AsyncResponse
    ) {
        coroutineScope.launch {
            try {
                val res = coursePlanDao.getAll()
                response.resume(Response.ok(res).build())
            } catch (e: Exception) {
                response.resume(WebApplicationException(e.message))
            }
        }
    }
}