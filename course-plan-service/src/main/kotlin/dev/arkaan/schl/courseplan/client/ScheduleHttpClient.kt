package dev.arkaan.schl.courseplan.client

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitObjectResult
import com.github.kittinunf.fuel.jackson.jacksonDeserializerOf
import dev.arkaan.schl.courseplan.client.domain.Schedule
import org.slf4j.LoggerFactory

class ScheduleHttpClient(
    private val baseUrl: String
) {
    private val logger = LoggerFactory.getLogger(ScheduleHttpClient::class.java)
    private val getSchedule : suspend (id: String) -> Schedule? = {
        Fuel.get(path = "$baseUrl/schedule/$it")
            .timeout(2000)
            .awaitObjectResult(jacksonDeserializerOf<Schedule>())
            .fold(
                { data -> data },
                { error -> logger.warn("${::getSchedule.name} failed: ${error.message}"); null }
            )
    }

    private val getRecurringSchedule: suspend (id: String) -> Schedule? = {
        Fuel.get(path = "$baseUrl/schedule/recurring/$it")
            .timeout(2000)
            .awaitObjectResult(jacksonDeserializerOf<Schedule>())
            .fold(
                { data -> data },
                { error -> logger.warn("${::getRecurringSchedule.name} failed: ${error.message}"); null }
            )
    }

    suspend fun checkScheduleExist(id: String): Boolean = getSchedule(id) != null

    suspend fun checkRecurringScheduleExist(id: String) : Boolean = getRecurringSchedule(id) != null
}