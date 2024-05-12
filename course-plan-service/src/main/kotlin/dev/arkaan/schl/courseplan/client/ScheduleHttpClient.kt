package dev.arkaan.schl.courseplan.client

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.*
import com.github.kittinunf.fuel.jackson.jacksonDeserializerOf
import dev.arkaan.schl.courseplan.domain.Schedule
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory

class ScheduleHttpClient(
    private val baseUrl: String
) {
    private val logger = LoggerFactory.getLogger(ScheduleHttpClient::class.java)
    private val getSchedule : suspend (id: String) -> Schedule? = {
        Fuel.get(path = baseUrl + it).awaitObjectResult(jacksonDeserializerOf<Schedule>())
            .fold(
                { data -> data },
                { error -> logger.warn("${::getSchedule.name} failed: ${error.message}"); null}
            )
    }

    private val getRecurringSchedule: suspend (id: String) -> Schedule? = {
        Fuel.get(path = "$baseUrl/recurring/$it").awaitObjectResult(jacksonDeserializerOf<Schedule>())
            .fold(
                { data -> data },
                {error -> logger.warn("${::getRecurringSchedule.name} failed: ${error.message}"); null}
            )
    }

    suspend fun checkScheduleExist(id: String): Boolean = coroutineScope {
        val responses = awaitAll(
            async { getSchedule(id) != null },
            async { getRecurringSchedule(id) != null }
        )
        responses[0].or(responses[1])
    }
}