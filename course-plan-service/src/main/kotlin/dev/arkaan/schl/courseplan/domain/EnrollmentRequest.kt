package dev.arkaan.schl.courseplan.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class EnrollmentRequest(
    @JsonProperty("coursePlanId")
    val coursePlanId: String,
    @JsonProperty("studentId")
    val studentId: String,
)
