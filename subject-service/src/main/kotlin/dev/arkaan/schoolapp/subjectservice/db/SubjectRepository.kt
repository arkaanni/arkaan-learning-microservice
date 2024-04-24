package dev.arkaan.schoolapp.subjectservice.db

import dev.arkaan.schoolapp.subjectservice.api.DuplicateException
import dev.arkaan.schoolapp.subjectservice.api.request.SubjectRequest
import dev.arkaan.schoolapp.subjectservice.db.entity.Subject
import dev.arkaan.schoolapp.subjectservice.domain.SubjectDto
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.inject.Singleton
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLIntegrityConstraintViolationException

@Singleton
class SubjectRepository @Inject constructor(
    @Named("subject") private val db: Database
) {

    fun findAll(): List<SubjectDto> = with(Subject) {
        transaction(db) {
            selectAll().map {
                SubjectDto(it[subjectCode], it[name], it[description])
            }
        }
    }

    fun findBySubjectCode(code: String): SubjectDto? = with(Subject) {
        transaction(db) {
            select(subjectCode, name, description)
                .where { subjectCode.eq(code) }
                .singleOrNull()?.let {
                    SubjectDto(it[subjectCode], it[name], it[description])
                }
        }
    }

    fun insertOne(subject: SubjectRequest) = with(Subject) {
        transaction(db) {
            try {
                insert {
                    it[subjectCode] = subject.subjectCode
                    it[name] = subject.name
                    it[description] = subject.name
                }
            } catch (e: ExposedSQLException) {
                if (e.cause is SQLIntegrityConstraintViolationException) {
                    throw DuplicateException("Subject")
                } else {
                    // Something went wrong?
                }
            }
        }
    }
}