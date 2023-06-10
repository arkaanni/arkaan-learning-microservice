package dev.arkaan.schoolapp.courseplanservice.db

import com.fasterxml.jackson.annotation.JsonProperty

class DatabaseConfiguration {
    @JsonProperty
    lateinit var url: String

    @JsonProperty
    lateinit var driverClass: String

    @JsonProperty
    lateinit var user: String

    @JsonProperty
    lateinit var password: String
}