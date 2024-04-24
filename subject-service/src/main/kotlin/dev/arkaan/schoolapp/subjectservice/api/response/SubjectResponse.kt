package dev.arkaan.schoolapp.subjectservice.api.response

import dev.arkaan.schoolapp.subjectservice.domain.SubjectDto
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class SubjectResponse(
    val subjectCode: String,
    val name: String,
    val description: String
)

fun SubjectDto.asResponse(): SubjectResponse {
    return SubjectResponse(
        subjectCode,
        name,
        description
    )
}
