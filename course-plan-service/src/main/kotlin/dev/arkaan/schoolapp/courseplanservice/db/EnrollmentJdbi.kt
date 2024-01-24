package dev.arkaan.schoolapp.courseplanservice.db

import jakarta.inject.Inject
import org.jdbi.v3.core.Jdbi
import java.sql.SQLException

class EnrollmentJdbi @Inject constructor(private val jdbi: Jdbi) : EnrollmentDao {
    override suspend fun enroll(coursePlanId: String, studentId: String) {
        jdbi.useTransaction<SQLException> {
            it.createUpdate("""
                INSERT INTO course_plan.enrollment (course_plan_id, student_id) 
                VALUES(?, ?)
                """)
                .bind(0, coursePlanId)
                .bind(1, studentId)
                .execute()
        }
    }
}