package dev.arkaan.schoolapp.subjectservice.api.response

import dev.arkaan.schoolapp.subjectservice.domain.Subject
import io.micronaut.core.annotation.Introspected

@Introspected
data class SubjectResponse(
    val subjectCode: String,
    val name: String,
    val description: String
)

fun Subject.asResponse(): SubjectResponse {
    return SubjectResponse(
        subjectCode,
        name,
        description
    )
}
