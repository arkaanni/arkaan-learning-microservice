package dev.arkaan.schl.courseplan.db.entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CoursePlan: Table("course_plan") {
    val id: Column<String> = char("id", 36)
    val subjectCode: Column<String> = varchar("subject_code", 20)
    val scheduleId: Column<String> = varchar("schedule_id", 36)
    val semester: Column<Byte> = byte("semester")
    val year: Column<Short> = short("year")

    override val primaryKey = PrimaryKey(id)
}