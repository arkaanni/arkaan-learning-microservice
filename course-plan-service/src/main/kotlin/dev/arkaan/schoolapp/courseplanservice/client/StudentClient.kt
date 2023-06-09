package dev.arkaan.schoolapp.courseplanservice.client

import dev.arkaan.schoolapp.courseplanservice.api.Student
import jakarta.ws.rs.client.WebTarget
import jakarta.ws.rs.core.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudentClient(
    private val studentApi: WebTarget
) {
    suspend fun getStudentById(id: String): Student? = withContext(Dispatchers.IO) {
        studentApi.path("/students/$id")
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get(Student::class.java)
    }
}