package dev.arkaan.schl.courseplan.client

import io.jooby.Extension
import io.jooby.Jooby

class ClientExtension: Extension {
    override fun install(application: Jooby) {
        val config = application.environment.config.getConfig("client")
        val scheduleHttpClient by lazy { ScheduleHttpClient(config.getString("schedule")) }
        val subjectHttpClient by lazy { SubjectHttpClient(config.getString("subject")) }
        val studentHttpClient by lazy { StudentHttpClient(config.getString("student")) }
        application.services.apply {
            put(ScheduleHttpClient::class.java, scheduleHttpClient)
            put(SubjectHttpClient::class.java, subjectHttpClient)
            put(StudentHttpClient::class.java, studentHttpClient)
        }
    }
}