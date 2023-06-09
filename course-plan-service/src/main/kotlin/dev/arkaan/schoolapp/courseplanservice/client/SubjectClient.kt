package dev.arkaan.schoolapp.courseplanservice.client

import dev.arkaan.schoolapp.courseplanservice.api.Subject
import jakarta.ws.rs.client.WebTarget
import jakarta.ws.rs.core.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SubjectClient(
    private val subjectApi: WebTarget
) {
    suspend fun getSubjectByCode(code: String): Subject? = withContext(Dispatchers.IO) {
        subjectApi.path("/subject/$code")
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get(Subject::class.java)
    }
}