package dev.arkaan.schoolapp.courseplanservice.api

import com.fasterxml.jackson.annotation.JsonProperty

class Student {
    @JsonProperty("student_id")
    lateinit var studentId: String
    @JsonProperty("first_name")
    lateinit var firstName: String
    @JsonProperty("last_name")
    lateinit var lastName: String
}
