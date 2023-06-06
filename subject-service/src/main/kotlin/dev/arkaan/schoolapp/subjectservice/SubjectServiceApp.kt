package dev.arkaan.schoolapp.subjectservice

import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.setUp() {
    install(ContentNegotiation) {
        jackson()
    }

    routing {
        getRoute()
        postRoute()
    }
}
