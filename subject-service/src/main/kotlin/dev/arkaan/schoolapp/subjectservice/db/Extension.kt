package dev.arkaan.schoolapp.subjectservice.db

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jdbi.v3.core.Jdbi

fun db() = DB.getJdbi()

suspend fun <T> withJdbi(action: CoroutineScope.(jdbi: Jdbi) -> T): T = withContext(Dispatchers.IO) { action(db()) }

