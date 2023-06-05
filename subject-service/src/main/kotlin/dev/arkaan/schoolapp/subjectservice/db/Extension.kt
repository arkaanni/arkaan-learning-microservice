package dev.arkaan.schoolapp.subjectservice.db

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jdbi.v3.core.Jdbi

fun Route.withJdbi(action: (jdbi: Jdbi) -> Unit) = action(application.db())

fun Application.db() = DB.getConnection(environment)