package dev.arkaan.schl.courseplan.client.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class Student(
    @JsonProperty("studentId")
    val studentId: String,
    @JsonProperty("firstName")
    val firstName: String,
    @JsonProperty("lastName")
    val lastName: String,
    @JsonProperty("semester")
    val semester: Byte
)
