package dev.arkaan.schl.courseplan.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class EnrollmentDto(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("coursePlanId")
    val coursePlanId: String,
    @JsonProperty("studentId")
    val studentId: String,
    @JsonProperty("grade")
    val grade: Char?
)
