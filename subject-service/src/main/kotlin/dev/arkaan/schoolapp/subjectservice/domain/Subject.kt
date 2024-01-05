package dev.arkaan.schoolapp.subjectservice.domain

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class Subject(
    @JsonProperty("subject_code")
    val subjectCode: String,
    val name: String,
    val description: String
)
