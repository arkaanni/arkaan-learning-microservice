package dev.arkaan.schl.courseplan.client

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitObjectResult
import com.github.kittinunf.fuel.jackson.jacksonDeserializerOf
import dev.arkaan.schl.courseplan.client.domain.Subject
import org.slf4j.LoggerFactory

class SubjectHttpClient(
    private val baseUrl: String
) {
    private val logger = LoggerFactory.getLogger(SubjectHttpClient::class.java)
    suspend fun checkSubjectExist(id: String): Boolean =
        Fuel.get(path = "$baseUrl/subject/$id")
            .timeout(2000)
            .awaitObjectResult(jacksonDeserializerOf<Subject>())
            .fold(
                { _ -> true },
                { error -> logger.warn("${::checkSubjectExist.name} failed: ${error.message}"); false }
            )
}