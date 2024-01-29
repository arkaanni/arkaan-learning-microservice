package dev.arkaan.schoolapp.courseplanservice.api

import com.fasterxml.jackson.annotation.JsonProperty

data class CoursePlanRequest(
    @JsonProperty("subjectCode")
    val subjectCode: String,
    @JsonProperty("semester")
    val semester: Byte,
    @JsonProperty("year")
    val year: Short,
    @JsonProperty("scheduleId")
    val scheduleId: String
)
