package dev.arkaan.schoolapp.courseplanservice.db

import com.google.inject.ImplementedBy
import dev.arkaan.schoolapp.courseplanservice.api.CoursePlan

@ImplementedBy(CoursePlanJdbi::class)
interface CoursePlanDao {
    suspend fun addOne(subjectCode: String, semester: Byte, year: Short, scheduleId: String)

    suspend fun getAll(): List<CoursePlan>
}