package dev.arkaan.schoolapp.subjectservice.db

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jdbi.v3.core.Jdbi

fun Application.db() = DB.getJdbi(environment)

suspend fun <T> PipelineContext<Unit, ApplicationCall>.withJdbi(action: suspend (jdbi: Jdbi) -> T?): T? =
    withContext(Dispatchers.IO) { action(application.db()) }