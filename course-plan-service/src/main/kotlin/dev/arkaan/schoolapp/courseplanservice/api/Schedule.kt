package dev.arkaan.schoolapp.courseplanservice.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Schedule(
    val id: String,
    @JsonProperty("room_id")
    val roomId: Short
)