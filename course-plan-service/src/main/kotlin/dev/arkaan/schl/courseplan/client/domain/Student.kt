package dev.arkaan.schl.courseplan.client.domain

import com.fasterxml.jackson.annotation.JsonProperty

class Student {
    @JsonProperty("studentId")
    lateinit var studentId: String
    @JsonProperty("firstName")
    lateinit var firstName: String
    @JsonProperty("lastName")
    lateinit var lastName: String
}
