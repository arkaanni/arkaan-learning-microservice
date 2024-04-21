package dev.arkaan.schoolapp.subjectservice.db

import dev.arkaan.schoolapp.subjectservice.api.DuplicateException
import dev.arkaan.schoolapp.subjectservice.api.request.SubjectRequest
import dev.arkaan.schoolapp.subjectservice.domain.Subject
import io.ktor.http.*
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.inject.Singleton
import org.jdbi.v3.core.Jdbi
import java.sql.SQLException
import java.sql.SQLIntegrityConstraintViolationException
import kotlin.jvm.optionals.getOrNull

@Singleton
class SubjectRepository @Inject constructor(
    @Named("subject") private val jdbi: Jdbi
) {

    fun getAll(): List<Subject> {
        return jdbi.withHandle<List<Subject>, SQLException> {
            it.createQuery("SELECT * FROM subject")
                .map { r, _, _ ->
                    Subject(
                        r.getString("subject_code"),
                        r.getString("name"),
                        r.getString("description")
                    )
                }
                .toList()
        }
    }

    fun getByCode(code: String): Subject? {
        return jdbi.withHandle<Subject?, SQLException> {
            it.createQuery("SELECT id, subject_code, name, description FROM subject WHERE subject_code=?")
                .bind(0, code)
                .map { rs, _ -> Subject(rs.getString(2), rs.getString(3), rs.getString(4)) }
                .findFirst()
                .getOrNull()
        }
    }

    fun insertOne(subject: SubjectRequest) {
        jdbi.inTransaction<Unit, SQLException> {
                it.createUpdate("INSERT INTO subject (subject_code, name, description) VALUES(?, ?, ?)")
                    .bind(0, subject.subjectCode)
                    .bind(1, subject.name)
                    .bind(2, subject.description)
                    .execute()
        }
    }
}