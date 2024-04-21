package dev.arkaan.schoolapp.subjectservice.api.request

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class SubjectRequest(
    val subjectCode: String,
    val name: String,
    val description: String
)