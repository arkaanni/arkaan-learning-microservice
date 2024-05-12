package dev.arkaan.schl.courseplan.service

import dev.arkaan.schl.courseplan.client.ScheduleHttpClient
import dev.arkaan.schl.courseplan.client.SubjectHttpClient
import dev.arkaan.schl.courseplan.db.entity.CoursePlan
import dev.arkaan.schl.courseplan.db.queryForResult
import dev.arkaan.schl.courseplan.domain.CoursePlanDto
import dev.arkaan.schl.courseplan.domain.CoursePlanRequest
import io.jooby.kt.HandlerContext
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll

class CoursePlanService(
    private val db: Database,
    private val scheduleClient: ScheduleHttpClient,
    private val subjectClient: SubjectHttpClient
    ) {
    fun getCoursePlan(): suspend HandlerContext.() -> List<CoursePlanDto> = {
        queryForResult(db) {
            with(CoursePlan) {
                selectAll()
                    .map {
                        CoursePlanDto(it[studentId], it[subjectCode], it[scheduleId])
                    }
            }
        }
    }

    fun createCoursePlan(): suspend HandlerContext.() -> Any = {
        val coursePlan = ctx.body(CoursePlanRequest::class.java)
        checkSubjectAndSchedule(coursePlan.subjectCode, coursePlan.scheduleId)
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