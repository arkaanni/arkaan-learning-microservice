package dev.arkaan.schoolapp.courseplanservice.client

import dev.arkaan.schoolapp.courseplanservice.api.Student
import jakarta.ws.rs.client.WebTarget
import jakarta.ws.rs.core.MediaType

class StudentClient(
    private val studentApi: WebTarget
) {
    fun getStudentById(id: String): Student? =
        studentApi.path("/students/$id")
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get(Student::class.java)
}