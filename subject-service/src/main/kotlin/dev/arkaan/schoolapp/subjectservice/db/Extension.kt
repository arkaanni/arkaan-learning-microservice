package dev.arkaan.schoolapp.subjectservice.db

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.coroutineScope
import org.jdbi.v3.core.Jdbi

fun db() = DB.getJdbi()

suspend fun <T> PipelineContext<Unit, ApplicationCall>.withJdbi(action: suspend (jdbi: Jdbi) -> T?): T? = action(db())
