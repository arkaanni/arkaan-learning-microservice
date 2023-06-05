package dev.arkaan.schoolapp.subjectservice.domain

import kotlinx.serialization.Serializable

@Serializable
data class Subject(
    val subjectCode: String,
    val name: String,
    val description: String
)
