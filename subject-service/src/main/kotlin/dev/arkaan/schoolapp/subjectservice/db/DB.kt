package dev.arkaan.schoolapp.subjectservice.db

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jdbi.v3.core.Jdbi
import org.slf4j.LoggerFactory

class DB private constructor() {

    companion object {
        private var jdbi: Jdbi? = null

        fun getConnection(environment: ApplicationEnvironment): Jdbi {
            val dataSource = HikariDataSource(hikariConfig(environment.config))
            if (jdbi == null) {
                jdbi = Jdbi.create(dataSource)
            }
            return jdbi!!
        }

        private fun hikariConfig(config: ApplicationConfig): HikariConfig {
            val hikariConfig = HikariConfig()
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
                level = Level.INFO
            }
            return hikariConfig
        }
    }
}