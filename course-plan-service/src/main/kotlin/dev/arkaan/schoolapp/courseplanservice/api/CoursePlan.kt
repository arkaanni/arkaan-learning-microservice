package dev.arkaan.schoolapp.courseplanservice.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize
data class CoursePlan(
    val id: String,
    @JsonProperty("student_id")
    val studentId: String,
    @JsonProperty("subject_code")
    val subjectCode: String
)
