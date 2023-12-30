package dev.arkaan.schoolapp.courseplanservice.db

import com.google.inject.ImplementedBy

@ImplementedBy(CoursePlanJdbi::class)
interface CoursePlanDao {
    fun addCoursePlan(studentId: String, subjectCode: String, semester: Short, year: Short)
}