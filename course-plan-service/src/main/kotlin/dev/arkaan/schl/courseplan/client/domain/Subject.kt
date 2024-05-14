package dev.arkaan.schl.courseplan.client.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class Subject(
    @JsonProperty("subjectCode")
    val code: String,
    @JsonProperty("name")
    val name: String
)
