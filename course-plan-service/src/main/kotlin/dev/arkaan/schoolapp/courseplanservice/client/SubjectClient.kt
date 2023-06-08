package dev.arkaan.schoolapp.courseplanservice.client

import jakarta.ws.rs.client.WebTarget

class SubjectClient(
    private val subjectApi: WebTarget
) {
    fun getSubjectByCode(code: String) {
        // TODO
    }
}