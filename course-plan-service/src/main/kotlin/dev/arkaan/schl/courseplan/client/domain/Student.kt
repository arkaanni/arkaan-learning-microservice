package dev.arkaan.schl.courseplan.client.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class Student(
    @JsonProperty
    val studentId: String,
    @JsonProperty
    val firstName: String,
    @JsonProperty
    val lastName: String,
    @JsonProperty
    val semester: Byte
)
