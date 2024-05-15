package dev.arkaan.schl.courseplan.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class EnrollmentRequest(
    @JsonProperty
    val coursePlanId: String,
    @JsonProperty
    val studentId: String,
)
