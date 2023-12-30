package dev.arkaan.schoolapp.courseplanservice.client

import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.client.WebTarget
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.eclipse.jetty.http.HttpStatus

class StudentHttpClient @Inject constructor(
    @Named("StudentClient") private val studentApi: WebTarget
) : StudentClient {
    suspend fun getStudent(id: String): Response = withContext(Dispatchers.IO) {
        studentApi.path("/students/$id")
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get()
        }

    override suspend fun checkStudentExist(id: String) {
        getStudent(id)
            .run {
                if (status == HttpStatus.NOT_FOUND_404)
                    throw WebApplicationException("Student does not exists", HttpStatus.NOT_FOUND_404)
            }
    }
}