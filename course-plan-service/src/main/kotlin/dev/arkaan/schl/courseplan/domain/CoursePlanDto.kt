package dev.arkaan.schl.courseplan.domain

data class CoursePlanDto(
    val id: String,
    val subjectCode: String,
    val scheduleId: String,
    val year: Short
)