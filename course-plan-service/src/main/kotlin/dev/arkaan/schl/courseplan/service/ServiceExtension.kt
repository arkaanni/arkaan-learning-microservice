package dev.arkaan.schl.courseplan.service

import dev.arkaan.schl.courseplan.client.ScheduleHttpClient
import dev.arkaan.schl.courseplan.client.StudentHttpClient
import dev.arkaan.schl.courseplan.client.SubjectHttpClient
import io.jooby.Extension
import io.jooby.Jooby
import org.jetbrains.exposed.sql.Database

class ServiceExtension: Extension {
    override fun install(application: Jooby) {
        val db = application.require(Database::class.java)
        val scheduleClient = application.require(ScheduleHttpClient::class.java)
        val subjectClient = application.require(SubjectHttpClient::class.java)
        val studentClient = application.require(StudentHttpClient::class.java)
        val coursePlanService by lazy { CoursePlanService(db, scheduleClient, subjectClient) }
        val enrollmentService by lazy { EnrollmentService(db, studentClient, coursePlanService) }
        with(application.services) {
            put(CoursePlanService::class.java, coursePlanService)
            put(EnrollmentService::class.java, enrollmentService)
        }
    }
}