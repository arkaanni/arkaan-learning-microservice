package dev.arkaan.schoolapp.courseplanservice.api

import com.fasterxml.jackson.annotation.JsonProperty

data class CoursePlan(
    val studentId: String,
    @JsonProperty("subject_code")
    val subjectCode: String,
    @JsonProperty("schedule_id")
    val scheduleId: String
)
