package dev.arkaan.schoolapp.courseplanservice.db

import org.jdbi.v3.core.Jdbi
import java.sql.SQLException

class CoursePlanDao(
    private val db: Jdbi
) {
    fun addCoursePlan(studentId: String, subjectCode: String, semester: Short, year: Short) {
        db.inTransaction<Unit, SQLException> {
            it.createUpdate("INSERT INTO course_plan (student_id, subject_code, semester, `year`) VALUES (?, ?, ?, ?)")
                .bind(0, studentId)
                .bind(1, subjectCode)
                .bind(2, semester)
                .bind(3, year)
                .execute()
        }
    }
}