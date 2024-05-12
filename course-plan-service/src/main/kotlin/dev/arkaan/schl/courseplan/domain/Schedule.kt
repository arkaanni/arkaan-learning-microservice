package dev.arkaan.schl.courseplan.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class Schedule(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("roomId")
    val roomId: Short
)