package dev.arkaan.schoolapp.courseplanservice.api

import com.fasterxml.jackson.annotation.JsonProperty

data class CoursePlanRequest(
    @JsonProperty
    val studentId: String,
    @JsonProperty
    val subjectId: String
)
