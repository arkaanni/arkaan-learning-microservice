package dev.arkaan.schoolapp.subjectservice.db

import io.ktor.server.application.*

fun Application.jdbi() {
    val jdbi = DB.getConnection(environment)
}