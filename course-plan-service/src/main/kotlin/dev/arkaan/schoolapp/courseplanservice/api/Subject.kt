package dev.arkaan.schoolapp.courseplanservice.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Subject(
    @JsonProperty("subjectCode")
    val code: String,
    @JsonProperty("name")
    val name: String
)
