package dev.arkaan.schoolapp.courseplanservice.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Subject(
    @JsonProperty("subject_code")
    val code: String,
    @JsonProperty("name")
    val name: String
)
