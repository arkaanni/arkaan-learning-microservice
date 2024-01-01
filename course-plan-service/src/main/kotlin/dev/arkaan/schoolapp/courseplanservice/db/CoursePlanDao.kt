package dev.arkaan.schoolapp.courseplanservice.db

import com.google.inject.ImplementedBy
import dev.arkaan.schoolapp.courseplanservice.api.CoursePlan

@ImplementedBy(CoursePlanJdbi::class)
interface CoursePlanDao {
    suspend fun addOne(studentId: String, subjectCode: String, semester: Short, year: Short)

    suspend fun getAll(): List<CoursePlan>
}