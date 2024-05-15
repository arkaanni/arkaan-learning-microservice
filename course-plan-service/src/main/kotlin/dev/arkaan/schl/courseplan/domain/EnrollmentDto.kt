package dev.arkaan.schl.courseplan.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class EnrollmentDto(
    @JsonProperty
    val id: String,
    @JsonProperty
    val coursePlanId: String,
    @JsonProperty
    val studentId: String,
    @JsonProperty
    val grade: Char
)
