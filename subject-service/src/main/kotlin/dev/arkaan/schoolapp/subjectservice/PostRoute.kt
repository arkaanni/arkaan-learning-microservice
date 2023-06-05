package dev.arkaan.schoolapp.subjectservice

import dev.arkaan.schoolapp.subjectservice.db.withJdbi
import dev.arkaan.schoolapp.subjectservice.domain.Subject
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import java.sql.SQLException

fun Route.postRoute() {
    insertSubject()
}

fun Route.insertSubject() {
    post("/subject") {
        val subject = call.receive<Subject>()
        this@insertSubject.withJdbi { jdbi ->
            jdbi.inTransaction<Unit, SQLException> {
                it.createUpdate("INSERT INTO subject (subject_code, name, description) VALUES(:code, :name, :desc)")
                    .bind("code", subject.subjectCode)
                    .bind("name", subject.name)
                    .bind("desc", subject.description)
                    .execute()
            }
        }
    }
}