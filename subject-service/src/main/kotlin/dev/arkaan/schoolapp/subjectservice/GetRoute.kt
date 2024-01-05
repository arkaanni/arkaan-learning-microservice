package dev.arkaan.schoolapp.subjectservice

import dev.arkaan.schoolapp.subjectservice.db.withJdbi
import dev.arkaan.schoolapp.subjectservice.domain.Subject
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.SQLException
import kotlin.jvm.optionals.getOrNull

fun Route.getRoute() {
    getAllSubjects()
    getSubjectByCode()
}

fun Route.getAllSubjects() {
    get("/subject") {
        val subjects = withJdbi { jdbi ->
            jdbi.withHandle<List<Subject>, SQLException> {
                it.createQuery("SELECT * FROM subject")
                    .map { r, _, _ ->
                        Subject(
                            r.getString("subject_code"),
                            r.getString("name"),
                            r.getString("description")
                        )
                    }
                    .toList()
            }
        }
        call.respond(subjects)
    }
}

fun Route.getSubjectByCode() {
    get("/subject/{code}") {
        val subject = withJdbi { jdbi ->
            val code = call.parameters["code"]
            jdbi.withHandle<Subject?, SQLException> {
                it.createQuery("SELECT id, subject_code, name, description FROM subject WHERE subject_code=?")
                    .bind(0, code)
                    .map { rs, _ -> Subject(rs.getString(2), rs.getString(3), rs.getString(4)) }
                    .findFirst()
                    .getOrNull()
            }
        }
        subject?.let {
            call.respond(HttpStatusCode.OK, it)
            return@let
        }
        call.respond(HttpStatusCode.NotFound)
    }
}