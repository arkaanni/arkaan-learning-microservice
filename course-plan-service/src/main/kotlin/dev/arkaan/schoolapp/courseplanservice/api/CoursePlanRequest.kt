package dev.arkaan.schoolapp.courseplanservice.api

import com.fasterxml.jackson.annotation.JsonProperty

data class CoursePlanRequest(
    @JsonProperty("studentId")
    var studentId: String,
    @JsonProperty("subjectCode")
    var subjectCode: String,
    @JsonProperty("semester")
    var semester: Short,
    @JsonProperty("year")
    var year: Short
)
