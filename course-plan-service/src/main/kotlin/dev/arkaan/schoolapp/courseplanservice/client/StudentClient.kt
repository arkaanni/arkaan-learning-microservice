package dev.arkaan.schoolapp.courseplanservice.client

import jakarta.ws.rs.client.Client
import jakarta.ws.rs.core.MediaType

class StudentClient(
    private val jerseyClient: Client
) {
    fun getDummy(): String {
        val target = jerseyClient.target("https://jsonplaceholder.typicode.com")
        return target.path("/posts/1")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .get(String::class.java)
    }
}