package dev.arkaan.schl.courseplan.client

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitObjectResult
import com.github.kittinunf.fuel.jackson.jacksonDeserializerOf
import dev.arkaan.schl.courseplan.client.domain.Student
import org.slf4j.LoggerFactory

class StudentHttpClient(
    private val baseUrl: String
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val getStudent: suspend (id: String) -> Student? = {
        Fuel.get("$baseUrl/student/$it")
            .timeout(2000)
            .awaitObjectResult(jacksonDeserializerOf<Student>())
            .fold(
                { data -> data},
                { error -> logger.warn("${::getStudent.name} failed: ${error.message}"); null }
            )
    }

    suspend fun getStudentById(id: String) = getStudent(id)
}
