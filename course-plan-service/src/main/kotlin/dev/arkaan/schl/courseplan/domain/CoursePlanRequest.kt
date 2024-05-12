package dev.arkaan.schl.courseplan.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class CoursePlanRequest(
    @JsonProperty("subjectCode")
    var subjectCode: String,
    @JsonProperty("semester")
    var semester: Byte,
    @JsonProperty("year")
    var year: Short,
    @JsonProperty("scheduleId")
    var scheduleId: String
)
