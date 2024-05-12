package dev.arkaan.schl.courseplan.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction


fun <T> queryForResult(db: Database, func: () -> T): T = transaction(db) { func() }