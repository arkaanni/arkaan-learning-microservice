package dev.arkaan.schoolapp.subjectservice.db.entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Subject: Table("subject") {
    private val id: Column<UShort> = ushort("id").autoIncrement()
    val subjectCode: Column<String> = varchar("subject_code", 20)
    val name: Column<String> = varchar("name", 100)
    val description: Column<String> = text("description")

    override val primaryKey = PrimaryKey(id)
}