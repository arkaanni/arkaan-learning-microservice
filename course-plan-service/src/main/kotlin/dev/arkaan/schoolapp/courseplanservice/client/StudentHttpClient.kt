package dev.arkaan.schoolapp.courseplanservice.client

import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.ws.rs.client.WebTarget
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.jetty.http.HttpStatus

class StudentHttpClient @Inject constructor(
    @Named("StudentClient") private val studentApi: WebTarget
) : StudentClient {
    
    private val getStudent: (id: String) -> Response = {
        studentApi.path("/students/$it")
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get()
        }

    override suspend fun checkStudentExist(id: String): Boolean {
        return getStudent(id).status != HttpStatus.NOT_FOUND_404
    }
}