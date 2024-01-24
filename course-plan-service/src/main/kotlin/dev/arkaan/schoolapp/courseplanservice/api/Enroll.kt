package dev.arkaan.schoolapp.courseplanservice.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Enroll(
    @JsonProperty("course_plan_id")
    val coursePlanId: String,
    @JsonProperty("student_id")
    val studentId: String,
)