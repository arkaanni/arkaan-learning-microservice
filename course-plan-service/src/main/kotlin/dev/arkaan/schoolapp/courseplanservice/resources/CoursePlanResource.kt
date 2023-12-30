package dev.arkaan.schoolapp.courseplanservice.resources

import dev.arkaan.schoolapp.courseplanservice.api.CoursePlanRequest
import dev.arkaan.schoolapp.courseplanservice.client.StudentClient
import dev.arkaan.schoolapp.courseplanservice.client.SubjectClient
import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.*
import jakarta.ws.rs.container.AsyncResponse
import jakarta.ws.rs.container.Suspended
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.coroutines.*
import org.jdbi.v3.core.Jdbi
import java.sql.SQLException

@Path("/course-plan")
class CoursePlanResource(
    private val studentClient: StudentClient,
    private val subjectClient: SubjectClient,
    private val coroutineScope: CoroutineScope,
    private val db: Jdbi
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
                    return@launch
                }
                db.withHandle<Unit, SQLException> {
                    it.createUpdate("INSERT INTO course_plan (student_id, subject_code, semester, `year`) VALUES (?, ?, ?, ?)")
                        .bind(0, studentId)
                        .bind(1, subjectCode)
                        .bind(2, semester)
                        .bind(3, year)
                        .execute()
                }
            }
            response.resume(Response.ok().build())
        }
    }
}