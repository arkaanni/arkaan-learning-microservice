package dev.arkaan.schl.courseplan.db.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Enroll @JsonCreator constructor(
    @JsonProperty("coursePlanId")
    val coursePlanId: String,
    @JsonProperty("studentId")
    val studentId: String,
)