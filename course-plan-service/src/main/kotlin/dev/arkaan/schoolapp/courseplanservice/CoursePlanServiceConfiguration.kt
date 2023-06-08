package dev.arkaan.schoolapp.courseplanservice

import io.dropwizard.client.JerseyClientConfiguration
import io.dropwizard.core.Configuration
import org.jetbrains.annotations.NotNull

class CoursePlanServiceConfiguration : Configuration() {
    @NotNull
    var jerseyClient = JerseyClientConfiguration()
        private set
}