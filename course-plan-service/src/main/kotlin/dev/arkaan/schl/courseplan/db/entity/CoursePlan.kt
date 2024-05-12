package dev.arkaan.schl.courseplan.db.entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CoursePlan: Table("course_plan") {
    val studentId: Column<String> = char("id", 36)
    val subjectCode: Column<String> = varchar("subject_code", 20)
    val scheduleId: Column<String> = varchar("schedule_id", 36)

    override val primaryKey = PrimaryKey(studentId)
}