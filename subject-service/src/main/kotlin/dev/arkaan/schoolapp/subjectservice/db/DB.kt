package dev.arkaan.schoolapp.subjectservice.db

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jdbi.v3.core.Jdbi
import org.slf4j.LoggerFactory
import javax.sql.DataSource

object DB {

    private lateinit var instance: Jdbi

    fun init(environment: ApplicationEnvironment) {
        instance = Jdbi.create(hikariConfig(environment.config))
        instance.open().close()
    }

    fun getJdbi(): Jdbi = instance

    private fun hikariConfig(config: ApplicationConfig): DataSource {
        val hikariConfig = HikariDataSource()
        with(config) {
            val host = property("db.host").getString()
            val port = property("db.port").getString()
            val database = property("db.database").getString()
            val user = property("db.user").getString()
            val dbPassword = property("db.password").getString()
            val driver = property("db.driver").getString()
            hikariConfig.apply {
                jdbcUrl = "jdbc:mysql://$host:${port}/$database"
                username = user
                password = dbPassword
                driverClassName = driver
            }
        }
        (LoggerFactory.getLogger("com.zaxxer.hikari") as Logger).apply {
            level = Level.DEBUG
        }
        return hikariConfig
    }
}