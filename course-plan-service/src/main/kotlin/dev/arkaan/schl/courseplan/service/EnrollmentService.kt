package dev.arkaan.schl.courseplan.service

import dev.arkaan.schl.courseplan.client.StudentHttpClient
import dev.arkaan.schl.courseplan.client.domain.Student
import dev.arkaan.schl.courseplan.db.entity.Enrollment
import dev.arkaan.schl.courseplan.db.inTransaction
import dev.arkaan.schl.courseplan.db.queryForResult
import dev.arkaan.schl.courseplan.domain.CoursePlanDto
import dev.arkaan.schl.courseplan.domain.EnrollmentDto
import dev.arkaan.schl.courseplan.domain.EnrollmentRequest
import io.jooby.Context
import io.jooby.StatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class EnrollmentService(
    private val db: Database,
    private val studentClient: StudentHttpClient,
    private val coursePlanService: CoursePlanService
) {

    fun getEnrollment(forStudentId: String): List<EnrollmentDto> =
        Enrollment.queryForResult {
            selectAll()
                .where(studentId eq forStudentId)
                .map(::fromResult)
        }

    fun getEnrollment(forStudentId: String, forCoursePlanId: String): EnrollmentDto? =
        Enrollment.queryForResult {
            selectAll()
                .limit(1)
                .where {
                    studentId eq forStudentId
                    coursePlanId eq forCoursePlanId
                }
                .map(::fromResult)
                .firstOrNull()
        }

    suspend fun enroll(ctx: Context): String = withContext(Dispatchers.IO) {
        val request = ctx.body(EnrollmentRequest::class.java)
        with(request) {
            val coursePlanAndStudent = awaitAll(
                async { coursePlanService.getCoursePlan(coursePlanId) },
                async { studentClient.getStudentById(studentId) },
                async { getEnrollment(studentId, coursePlanId) }
            )
            val coursePlanDto = coursePlanAndStudent[0] as CoursePlanDto?
            val student = coursePlanAndStudent[1] as Student?
            val enrollmentDto = coursePlanAndStudent[2] as EnrollmentDto?

            ctx.responseCode = StatusCode.BAD_REQUEST
            if (student == null) {
                return@with "Student not found"
            }
            if (coursePlanDto == null) {
                return@with "Course plan not found"
            }
            if (enrollmentDto != null) {
                return@with "Already enrolled"
            }
            // whats the correct way for this?
            val isEligible = student.semester == coursePlanDto.semester
            return@with if (isEligible) {
                insertEnrollment(studentId, coursePlanId)
                ctx.responseCode = StatusCode.CREATED
                "Enrollment success"
            } else {
                "Student not eligible to enroll"
            }
        }
    }

    private fun insertEnrollment(forStudentId: String, forCoursePlanId: String) {
        Enrollment.inTransaction(db) {
            insert {
                it[studentId] = forStudentId
                it[coursePlanId] = forCoursePlanId
            }
        }
    }
}