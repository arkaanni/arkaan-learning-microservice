package dev.arkaan.schoolapp.courseplanservice

import dev.arkaan.schoolapp.courseplanservice.client.StudentClient
import dev.arkaan.schoolapp.courseplanservice.resources.CoursePlanResource
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.core.Application
import io.dropwizard.core.setup.Environment

class CoursePlanServiceApp : Application<CoursePlanServiceConfiguration>() {
    override fun run(configuration: CoursePlanServiceConfiguration, environment: Environment) {
        val jerseyClient = JerseyClientBuilder(environment)
            .using(configuration.jerseyClient)
            .build(name)
        val studentClient = StudentClient(jerseyClient)
        environment.jersey().apply {
            register(CoursePlanResource(studentClient))
        }
    }
}

fun main(args: Array<String>) {
    CoursePlanServiceApp().run(*args)
}