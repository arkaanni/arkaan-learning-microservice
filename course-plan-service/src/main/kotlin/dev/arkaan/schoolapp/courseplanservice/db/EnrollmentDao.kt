package dev.arkaan.schoolapp.courseplanservice.db

import com.google.inject.ImplementedBy

@ImplementedBy(EnrollmentJdbi::class)
interface EnrollmentDao {
    suspend fun enroll(coursePlanId: String, studentId: String)
}