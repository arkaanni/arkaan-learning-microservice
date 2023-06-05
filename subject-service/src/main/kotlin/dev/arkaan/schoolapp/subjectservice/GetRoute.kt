package dev.arkaan.schoolapp.subjectservice

import dev.arkaan.schoolapp.subjectservice.db.withJdbi
import dev.arkaan.schoolapp.subjectservice.domain.Subject
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.SQLException

fun Route.getRoute() {
    getAllSubjects()
}

fun Route.getAllSubjects() {
    withJdbi { jdbi ->
        get("/subject") {
            val subjects = jdbi.withHandle<List<Subject>, SQLException> {
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
            call.respond(subjects)
        }
    }
}