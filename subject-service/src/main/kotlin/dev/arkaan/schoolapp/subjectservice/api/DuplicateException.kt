package dev.arkaan.schoolapp.subjectservice.api

class DuplicateException(private val name: String): RuntimeException() {

    override val message: String
        get() = "$name already exists."
}