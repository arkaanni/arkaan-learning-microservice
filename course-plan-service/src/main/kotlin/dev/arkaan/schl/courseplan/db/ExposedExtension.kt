package dev.arkaan.schl.courseplan.db

import com.zaxxer.hikari.HikariDataSource
import io.jooby.Extension
import io.jooby.Jooby
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

class ExposedExtension: Extension {
    override fun install(application: Jooby) {
        val datasourceConfig = application.environment.config.getConfig("datasource")
        val datasource = HikariDataSource().apply {
            with(datasourceConfig) {
                jdbcUrl = getString("url")
                driverClassName = getString("driverClassName")
                username = getString("user")
                password = getString("password")

            }
        }
        val db: Database by lazy { Database.connect(datasource) }
        application.services.put(Database::class.java, db)
        application.onStop(datasource::close)
    }
}

fun <T: Table, R> T.inTransaction(db: Database, func: T.() -> R): R = transaction(db) { func() }

fun <T: Table, R> T.inTransaction(func: T.() -> R): R = transaction { func() }

fun <T: Table, R> T.queryForResult(func: T.() -> R): R = transaction { func() }