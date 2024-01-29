package dev.arkaan.schoolapp.courseplanservice.api

import com.fasterxml.jackson.annotation.JsonProperty

data class CoursePlan(
    val studentId: String,
    @JsonProperty("subjectCode")
    val subjectCode: String,
    @JsonProperty("scheduleId")
    val scheduleId: String
)
