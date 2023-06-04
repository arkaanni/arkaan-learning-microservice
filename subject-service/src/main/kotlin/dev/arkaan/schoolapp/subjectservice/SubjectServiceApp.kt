package dev.arkaan.schoolapp.subjectservice

import dev.arkaan.schoolapp.subjectservice.db.DB
import dev.arkaan.schoolapp.subjectservice.getSubject.getAllSubject
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(ContentNegotiation) {
        jackson()
    }

    val jdbi = DB.getConnection(environment)

    routing {
        getAllSubject(jdbi)
    }
}
