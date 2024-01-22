package dev.arkaan.schoolapp.courseplanservice.db

import dev.arkaan.schoolapp.courseplanservice.api.CoursePlan
import jakarta.inject.Inject
import org.jdbi.v3.core.Jdbi
import java.sql.SQLException

class CoursePlanJdbi @Inject constructor(
    private val db: Jdbi
) : CoursePlanDao {
    override suspend fun addOne(subjectCode: String, semester: Byte, year: Short, scheduleId: String) {
        db.inTransaction<Unit, SQLException> {
            it.createUpdate("INSERT INTO course_plan (subject_code, semester, `year`, schedule_id) VALUES (?, ?, ?, ?)")
                .bind(0, subjectCode)
                .bind(1, semester)
                .bind(2, year)
                .bind(3, scheduleId)
                .execute()
        }
    }

    override suspend fun getAll(): List<CoursePlan> {
        return db.withHandle<List<CoursePlan>, SQLException> {
            it.createQuery("SELECT * FROM course_plan")
                .map { rs, _  ->
                    val id = rs.getString("id")
                    val subjectId = rs.getString("subject_code")
                    val scheduleId = rs.getString("schedule_id")
                    CoursePlan(id, subjectId, scheduleId)
                }
                .toList()
        }
    }
}