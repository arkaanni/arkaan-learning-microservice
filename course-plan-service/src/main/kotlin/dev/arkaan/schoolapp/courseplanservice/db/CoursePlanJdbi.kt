package dev.arkaan.schoolapp.courseplanservice.db

import dev.arkaan.schoolapp.courseplanservice.api.CoursePlan
import jakarta.inject.Inject
import org.jdbi.v3.core.Jdbi
import java.sql.SQLException

class CoursePlanJdbi @Inject constructor(
    private val db: Jdbi
) : CoursePlanDao {
    override suspend fun addOne(studentId: String, subjectCode: String, semester: Byte, year: Short, scheduleId: String) {
        db.inTransaction<Unit, SQLException> {
            it.createUpdate("INSERT INTO course_plan (student_id, subject_code, semester, `year`, schedule_id) VALUES (?, ?, ?, ?, ?)")
                .bind(0, studentId)
                .bind(1, subjectCode)
                .bind(2, semester)
                .bind(3, year)
                .bind(4, scheduleId)
                .execute()
        }
    }

    override suspend fun getAll(): List<CoursePlan> {
        return db.withHandle<List<CoursePlan>, SQLException> {
            it.createQuery("SELECT * FROM course_plan")
                .map { rs, _  ->
                    val id = rs.getString("id")
                    val studentId = rs.getString("student_id")
                    val subjectId = rs.getString("subject_code")
                    val scheduleId = rs.getString("schedule_id")
                    CoursePlan(id, studentId, subjectId, scheduleId)
                }
                .toList()
        }
    }
}