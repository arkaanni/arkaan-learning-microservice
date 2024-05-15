package dev.arkaan.schl.courseplan.db.entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Enrollment: Table("enrollment") {
    val id: Column<String> = char("id", 36)
    val coursePlanId: Column<String> = char("course_plan_id", 36)
        .references(CoursePlan.id)
    val studentId: Column<String> = varchar("student_id", 50)
    val grade: Column<Char> = char("grade")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
