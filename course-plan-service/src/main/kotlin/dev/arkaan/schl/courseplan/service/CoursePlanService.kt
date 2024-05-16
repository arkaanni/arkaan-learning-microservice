package dev.arkaan.schl.courseplan.service

import dev.arkaan.schl.courseplan.client.ScheduleHttpClient
import dev.arkaan.schl.courseplan.client.SubjectHttpClient
import dev.arkaan.schl.courseplan.db.entity.CoursePlan
import dev.arkaan.schl.courseplan.db.inTransaction
import dev.arkaan.schl.courseplan.db.queryForResult
import dev.arkaan.schl.courseplan.domain.CoursePlanDto
import dev.arkaan.schl.courseplan.domain.CoursePlanRequest
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

class CoursePlanService(
    private val db: Database,
    private val scheduleClient: ScheduleHttpClient,
    private val subjectClient: SubjectHttpClient
    ) {

    fun getCoursePlan(): List<CoursePlanDto> =
        CoursePlan.queryForResult {
            selectAll()
                .map {
                    CoursePlanDto(it[id], it[subjectCode], it[scheduleId], it[semester], it[year])
                }
    }

    fun getCoursePlan(forId: String): CoursePlanDto =
        CoursePlan.queryForResult {
            selectAll()
                .limit(1)
                .where(id eq forId)
                .map { CoursePlanDto(it[id], it[subjectCode], it[scheduleId], it[semester], it[year]) }
                .first()
        }

    suspend fun createCoursePlan(ctx: Context): String {
        val coursePlan = ctx.body(CoursePlanRequest::class.java)
        val check = checkSubjectAndSchedule(coursePlan.subjectCode, coursePlan.scheduleId)
        return if (check) {
            val isAvailable = checkAvailability(coursePlan)
            if (isAvailable) {
                insertCoursePlan(coursePlan)
                "New course plan added"
            } else {
                ctx.responseCode = StatusCode.BAD_REQUEST
                "Course plan already exist for the schedule"
            }
        } else {
            ctx.responseCode = StatusCode.BAD_REQUEST
            "Subject code and/or schedule id not found"
        }
    }

    private fun insertCoursePlan(request: CoursePlanRequest) {
        CoursePlan.inTransaction(db) {
            insert {
                it[subjectCode] = request.subjectCode
                it[scheduleId] = request.scheduleId
                it[semester] = request.semester
                it[year] = request.year
            }
        }
    }

    private suspend fun checkAvailability(request: CoursePlanRequest): Boolean = withContext(Dispatchers.IO) {
        CoursePlan.queryForResult {
            select(scheduleId)
                .where {
                    scheduleId eq request.scheduleId
                    semester eq request.semester
                    year eq request.year
                }.empty()
        }
    }

    private suspend fun checkSubjectAndSchedule(
        subjectCode: String,
        scheduleId: String
    ): Boolean = withContext(Dispatchers.IO) {
        val responses = awaitAll(
            async { scheduleClient.checkRecurringScheduleExist(scheduleId) },
            async { subjectClient.checkSubjectExist(subjectCode) }
        )
        responses[0].and(responses[1])
    }
}