package dev.arkaan.schoolapp.courseplanservice

import dev.arkaan.schoolapp.courseplanservice.client.StudentClient
import dev.arkaan.schoolapp.courseplanservice.resources.CoursePlanResource
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.configuration.EnvironmentVariableSubstitutor
import io.dropwizard.configuration.SubstitutingSourceProvider
import io.dropwizard.core.Application
import io.dropwizard.core.setup.Bootstrap
import io.dropwizard.core.setup.Environment

class CoursePlanServiceApp : Application<CoursePlanServiceConfiguration>() {

    override fun initialize(bootstrap: Bootstrap<CoursePlanServiceConfiguration>?) {
        super.initialize(bootstrap)

        val substitutor = EnvironmentVariableSubstitutor()
        bootstrap?.apply {
            configurationSourceProvider = SubstitutingSourceProvider(
                configurationSourceProvider, substitutor
            )
        }
    }

    override fun run(configuration: CoursePlanServiceConfiguration, environment: Environment) {
        val jerseyClient = JerseyClientBuilder(environment)
            .using(configuration.jerseyClient)
            .build(name)
        val studentClient = StudentClient(jerseyClient.target("http://${configuration.client.student}"))
        environment.jersey().apply {
            register(CoursePlanResource(studentClient))
        }
    }
}

fun main(args: Array<String>) {
    CoursePlanServiceApp().run(*args)
}