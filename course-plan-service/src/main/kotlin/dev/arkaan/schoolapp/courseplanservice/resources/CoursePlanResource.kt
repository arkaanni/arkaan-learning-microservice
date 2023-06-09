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
                val getStudent = async(Dispatchers.IO) { studentClient.getStudentById(studentId) }
                val getSubject = async(Dispatchers.IO) { subjectClient.getSubjectByCode(subjectCode) }
                val awaitAll = awaitAll(getStudent, getSubject)
                if (awaitAll[0] == null || awaitAll[1] == null) {
                    response.resume(WebApplicationException("Subject or student not found", Response.Status.NOT_FOUND))
                    return@launch
                }
                db.withHandle<Unit, SQLException> {
                    // TODO :(
                    with(coursePlan) {
                        it.createUpdate("INSERT INTO course_plan (student_id, subject_code, semester, `year`) VALUES (:studentId, :subjectCode, :semester, :year)")
                            .bind("studentId", studentId)
                            .bind("subjectCode", subjectCode)
                            .bind("semester", semester)
                            .bind("year", year)
                            .execute()
                    }
                }
                response.resume("OK")
            }
        }
    }
}