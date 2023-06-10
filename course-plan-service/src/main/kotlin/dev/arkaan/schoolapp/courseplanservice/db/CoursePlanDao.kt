package dev.arkaan.schoolapp.courseplanservice.db

import org.jdbi.v3.core.Jdbi
import java.sql.SQLException

class CoursePlanDao(
    private val db: Jdbi
) {
    fun addCoursePlan(studentId: String, subjectCode: String) {
        db.inTransaction<Unit, SQLException> {

        }
    }
}