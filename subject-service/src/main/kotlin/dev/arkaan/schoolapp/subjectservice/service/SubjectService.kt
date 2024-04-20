package dev.arkaan.schoolapp.subjectservice.service

import dev.arkaan.schoolapp.subjectservice.api.response.SubjectResponse
import dev.arkaan.schoolapp.subjectservice.api.response.asResponse
import dev.arkaan.schoolapp.subjectservice.db.SubjectRepository
import dev.arkaan.schoolapp.subjectservice.domain.Subject
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.stream.Collectors

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
}