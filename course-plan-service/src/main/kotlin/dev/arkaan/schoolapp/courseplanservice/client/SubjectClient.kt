package dev.arkaan.schoolapp.courseplanservice.client

import dev.arkaan.schoolapp.courseplanservice.api.Subject
import jakarta.ws.rs.client.WebTarget
import jakarta.ws.rs.core.MediaType

class SubjectClient(
    private val subjectApi: WebTarget
) {
    fun getSubjectByCode(code: String): Subject? =
        subjectApi.path("/subject/$code")
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get(Subject::class.java)
}