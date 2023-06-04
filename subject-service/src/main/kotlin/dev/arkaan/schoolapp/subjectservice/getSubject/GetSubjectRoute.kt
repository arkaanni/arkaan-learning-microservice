package dev.arkaan.schoolapp.subjectservice.getSubject

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jdbi.v3.core.Jdbi
import java.sql.SQLException

fun Route.getAllSubject(jdbi: Jdbi) {
    get("/subjects") {
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