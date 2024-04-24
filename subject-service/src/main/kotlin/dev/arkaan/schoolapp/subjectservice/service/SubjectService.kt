package dev.arkaan.schoolapp.subjectservice.service

import dev.arkaan.schoolapp.subjectservice.api.DuplicateException
import dev.arkaan.schoolapp.subjectservice.api.request.SubjectRequest
import dev.arkaan.schoolapp.subjectservice.api.response.SubjectResponse
import dev.arkaan.schoolapp.subjectservice.api.response.asResponse
import dev.arkaan.schoolapp.subjectservice.db.SubjectRepository
import dev.arkaan.schoolapp.subjectservice.domain.SubjectDto
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.stream.Collectors

@Singleton
class SubjectService @Inject constructor(
    private val subjectRepository: SubjectRepository
) {

    fun getAll(): List<SubjectResponse> {
        return subjectRepository.findAll().stream()
            .map(SubjectDto::asResponse)
            .collect(Collectors.toList())
    }

    fun getByCode(code: String): SubjectResponse? {
        return subjectRepository.findBySubjectCode(code)?.asResponse()
    }

    @Throws(DuplicateException::class)
    fun insert(subject: SubjectRequest) {
        subjectRepository.insertOne(subject)
    }
}