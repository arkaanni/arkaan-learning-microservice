package dev.arkaan.schoolapp.subjectservice

import dev.arkaan.schoolapp.subjectservice.db.withJdbi
import dev.arkaan.schoolapp.subjectservice.domain.Subject
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.SQLException
import java.sql.SQLIntegrityConstraintViolationException

fun Route.postRoute() {
    insertSubject()
}

fun Route.insertSubject() {
    post("/subject") {
        withJdbi { jdbi ->
            val subject = call.receive<Subject>()
            var status = HttpStatusCode.OK
            var msg = "Success."
            jdbi.inTransaction<Unit, SQLException> {
                try {
                    it.createUpdate("INSERT INTO subject (subject_code, name, description) VALUES(?, ?, ?)")
                        .bind(0, subject.subjectCode)
                        .bind(1, subject.name)
                        .bind(2, subject.description)
                        .execute()
                } catch (e: Exception) {
                    if (e.cause is SQLIntegrityConstraintViolationException) {
                        status = HttpStatusCode.BadRequest
                        msg = "Subject already exists."
                    }
                }
            }
            this.call.respond(status, msg)
        }
    }
}