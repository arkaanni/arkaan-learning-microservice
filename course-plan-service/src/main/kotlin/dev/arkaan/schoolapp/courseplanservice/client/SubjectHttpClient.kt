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

class SubjectHttpClient @Inject constructor(
    @Named("SubjectClient") private val subjectApi: WebTarget
) : SubjectClient {
    suspend fun getSubjectByCode(code: String): Response = withContext(Dispatchers.IO) {
        subjectApi.path("/subject/$code")
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get()
    }

    override suspend fun checkSubjectExist(code: String) {
        getSubjectByCode(code).run {
            if (status == HttpStatus.NOT_FOUND_404)
                throw WebApplicationException("Subject does not exists")
        }
    }
}