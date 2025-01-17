package dev.arkaan.schoolapp.subjectservice.db

import com.zaxxer.hikari.HikariDataSource
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Factory
import jakarta.inject.Named
import jakarta.inject.Singleton
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

@ConfigurationProperties("datasource.subject")
data class DBConfiguration(
    val jdbcUrl: String,
    val username: String,
    val password: String,
    val dialect: String,
    val driverClassName: String
)

@Factory
class MyDataSource {

    @Singleton
    @Named("subject")
    fun datasource(config: DBConfiguration): DataSource {
        println(config)

        return HikariDataSource().apply {
            username = config.username
            password = config.password
            jdbcUrl = config.jdbcUrl
            driverClassName = config.driverClassName
            maximumPoolSize = 4
        }
    }

    @Singleton
    @Named("subject")
    fun exposed(@Named("subject") dataSource: DataSource): Database {
        val db by lazy {
            Database.connect(dataSource)
        }
        return db
    }
}