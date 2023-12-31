package dev.arkaan.schoolapp.courseplanservice.db

import com.codahale.metrics.health.HealthCheck
import jakarta.inject.Inject
import org.jdbi.v3.core.Jdbi

class DBHealthCheck @Inject constructor(private val db: Jdbi) : HealthCheck() {
    
    override fun check(): Result {
        return try {
            db.open().close()
            Result.healthy()
        } catch (e: Exception) {
            return Result.unhealthy("Unable to connect to database.")
        }
    }
}