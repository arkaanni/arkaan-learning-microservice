package dev.arkaan.schoolapp.courseplanservice

import dev.arkaan.schoolapp.courseplanservice.client.ClientConfiguration
import dev.arkaan.schoolapp.courseplanservice.db.DatabaseConfiguration
import io.dropwizard.client.JerseyClientConfiguration
import io.dropwizard.core.Configuration
import jakarta.validation.constraints.NotNull

class CoursePlanServiceConfiguration : Configuration() {
    @NotNull
    var jerseyClient = JerseyClientConfiguration()
        private set

    @NotNull
    lateinit var client: ClientConfiguration

    @NotNull
    lateinit var db: DatabaseConfiguration
}