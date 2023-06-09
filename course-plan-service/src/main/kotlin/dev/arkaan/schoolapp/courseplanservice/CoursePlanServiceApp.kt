package dev.arkaan.schoolapp.courseplanservice

import dev.arkaan.schoolapp.courseplanservice.client.StudentClient
import dev.arkaan.schoolapp.courseplanservice.client.SubjectClient
import dev.arkaan.schoolapp.courseplanservice.resources.CoursePlanResource
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.configuration.EnvironmentVariableSubstitutor
import io.dropwizard.configuration.SubstitutingSourceProvider
import io.dropwizard.core.Application
import io.dropwizard.core.setup.Bootstrap
import io.dropwizard.core.setup.Environment
import io.dropwizard.jdbi3.JdbiFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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
        val jdbi = JdbiFactory().build(environment, configuration.db, "course_plan")
        val jerseyClient = JerseyClientBuilder(environment)
            .using(configuration.jerseyClient)
            .build(name)
        val studentClient = StudentClient(jerseyClient.target("http://${configuration.client.student}"))
        val subjectClient = SubjectClient(jerseyClient.target("http://${configuration.client.subject}"))
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        environment.jersey().apply {
            register(CoursePlanResource(studentClient, subjectClient, coroutineScope, jdbi))
        }
    }
}

fun main(args: Array<String>) {
    CoursePlanServiceApp().run(*args)
}