package dev.arkaan.schoolapp.subjectservice

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.micronaut.runtime.Micronaut
import jakarta.inject.Singleton
import javax.sql.DataSource

fun main(args: Array<String>) {
    Micronaut.run(*args)
}

