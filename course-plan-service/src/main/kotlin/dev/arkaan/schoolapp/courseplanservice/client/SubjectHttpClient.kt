package dev.arkaan.schoolapp.courseplanservice.client

import dev.arkaan.schoolapp.courseplanservice.db.NotFoundException
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.ws.rs.client.WebTarget
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.jetty.http.HttpStatus

class SubjectHttpClient @Inject constructor(
    @Named("SubjectClient") private val subjectApi: WebTarget
) : SubjectClient {

    private val getSubjectByCode: (code: String) -> Response = {
        subjectApi.path("/subject/$it")
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get()
    }

    override suspend fun checkSubjectExist(code: String) {
        getSubjectByCode(code).run {
            if (status == HttpStatus.NOT_FOUND_404)
                throw NotFoundException("Subject does not exists.")
        }
    }
}