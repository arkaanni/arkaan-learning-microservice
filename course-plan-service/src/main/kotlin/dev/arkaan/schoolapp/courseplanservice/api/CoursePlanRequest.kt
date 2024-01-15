package dev.arkaan.schoolapp.courseplanservice.api

import com.fasterxml.jackson.annotation.JsonProperty

data class CoursePlanRequest(
    @JsonProperty("student_id")
    val studentId: String,
    @JsonProperty("subject_code")
    val subjectCode: String,
    @JsonProperty("semester")
    val semester: Byte,
    @JsonProperty("year")
    val year: Short,
    @JsonProperty("schedule_id")
    val scheduleId: String
)
