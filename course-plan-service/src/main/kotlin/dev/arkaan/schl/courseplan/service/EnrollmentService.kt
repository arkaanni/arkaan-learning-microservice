package dev.arkaan.schl.courseplan.service

import dev.arkaan.schl.courseplan.db.entity.Enrollment
import dev.arkaan.schl.courseplan.db.inTransaction
import dev.arkaan.schl.courseplan.domain.EnrollmentDto
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll

class EnrollmentService(
    private val db: Database
) {

    fun getEnrollment(studentId: String): List<EnrollmentDto> =
        Enrollment.inTransaction(db) {
            selectAll()
                .where(id eq studentId)
                .map {
                    EnrollmentDto(it[id], it[coursePlanId], it[Enrollment.studentId], it[grade])
                }
        }

}