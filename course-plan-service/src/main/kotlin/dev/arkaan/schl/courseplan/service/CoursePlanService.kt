package dev.arkaan.schl.courseplan.service

import dev.arkaan.schl.courseplan.client.ScheduleHttpClient
import dev.arkaan.schl.courseplan.client.SubjectHttpClient
import dev.arkaan.schl.courseplan.db.entity.CoursePlan
import dev.arkaan.schl.courseplan.db.inTransaction
import dev.arkaan.schl.courseplan.domain.CoursePlanDto
import dev.arkaan.schl.courseplan.domain.CoursePlanRequest
import io.jooby.StatusCode
import io.jooby.kt.HandlerContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class CoursePlanService(
    private val db: Database,
    private val scheduleClient: ScheduleHttpClient,
    private val subjectClient: SubjectHttpClient
    ) {
    fun getCoursePlan(): suspend HandlerContext.() -> List<CoursePlanDto> = {
        CoursePlan.inTransaction(db) {
            selectAll()
                .map {
                    CoursePlanDto(it[id], it[subjectCode], it[scheduleId], it[year])
                }
        }
    }

    fun createCoursePlan(): suspend HandlerContext.() -> String = {
        val coursePlan = ctx.body(CoursePlanRequest::class.java)
        val check = checkSubjectAndSchedule(coursePlan.subjectCode, coursePlan.scheduleId)
        if (check) {
            CoursePlan.inTransaction(db) {
                insert {
                    it[subjectCode] = coursePlan.subjectCode
                    it[scheduleId] = coursePlan.scheduleId
                    it[semester] = coursePlan.semester
                    it[year] = coursePlan.year
                }
            }
            "New course plan added"
        } else {
            ctx.responseCode = StatusCode.BAD_REQUEST
            "Subject code and/or schedule id not found"
        }
    }

    private suspend fun checkSubjectAndSchedule(
        subjectCode: String,
        scheduleId: String
    ): Boolean = withContext(Dispatchers.IO) {
        val responses = awaitAll(
            async { scheduleClient.checkScheduleExist(scheduleId) },
            async { subjectClient.checkSubjectExist(subjectCode) }
        )
        responses[0].and(responses[1])
    }
}