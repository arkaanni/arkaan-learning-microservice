package dev.arkaan.schoolapp.subjectservice.service

import dev.arkaan.schoolapp.subjectservice.api.DuplicateException
import dev.arkaan.schoolapp.subjectservice.api.request.SubjectRequest
import dev.arkaan.schoolapp.subjectservice.api.response.SubjectResponse
import dev.arkaan.schoolapp.subjectservice.api.response.asResponse
import dev.arkaan.schoolapp.subjectservice.db.SubjectRepository
import dev.arkaan.schoolapp.subjectservice.domain.Subject
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.sql.SQLIntegrityConstraintViolationException
import java.util.stream.Collectors
import kotlin.jvm.Throws

@Singleton
class SubjectService @Inject constructor(
    private val subjectRepository: SubjectRepository
) {

    fun getAll(): List<SubjectResponse> {
        return subjectRepository.getAll().stream()
            .map(Subject::asResponse)
            .collect(Collectors.toList())
    }

    fun getByCode(code: String): SubjectResponse? {
        return subjectRepository.getByCode(code)?.asResponse()
    }

    fun insert(subject: SubjectRequest) {
        try {
            subjectRepository.insertOne(subject)
        } catch (e: Exception) {
            if (e.cause is SQLIntegrityConstraintViolationException) {
                throw DuplicateException("Subject")
            }
        }
    }
}