package dev.arkaan.schoolapp.subjectservice.api

import java.lang.RuntimeException

class DuplicateException(private val name: String): RuntimeException() {

    override val message: String
        get() = "$name already exists."
}