package dev.arkaan.schoolapp.subjectservice

import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.setUp() {
    install(ContentNegotiation) {
        jackson()
    }
    install(CORS){
        allowHost("0.0.0.0:5173")
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
    }

    routing {
        getRoute()
        postRoute()
    }
}
