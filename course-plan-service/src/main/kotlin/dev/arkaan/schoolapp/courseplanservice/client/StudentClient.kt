package dev.arkaan.schoolapp.courseplanservice.client

import dev.arkaan.schoolapp.courseplanservice.api.Student
import jakarta.ws.rs.client.WebTarget
import jakarta.ws.rs.core.MediaType

class StudentClient(
    private val studentApi: WebTarget
) {
    fun getDummyStudent(): Student {
        return studentApi.path("/students/121983")
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get(Student::class.java)
    }
}