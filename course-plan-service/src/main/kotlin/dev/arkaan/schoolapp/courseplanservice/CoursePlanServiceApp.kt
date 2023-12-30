package dev.arkaan.schoolapp.courseplanservice

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.arkaan.schoolapp.courseplanservice.client.StudentClient
import dev.arkaan.schoolapp.courseplanservice.client.SubjectClient
import dev.arkaan.schoolapp.courseplanservice.db.CoursePlanDao
import dev.arkaan.schoolapp.courseplanservice.resources.CoursePlanResource
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.configuration.EnvironmentVariableSubstitutor
import io.dropwizard.configuration.SubstitutingSourceProvider
import io.dropwizard.core.Application
import io.dropwizard.core.setup.Bootstrap
import io.dropwizard.core.setup.Environment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.jdbi.v3.core.Jdbi

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
        val hikariConfig = HikariConfig().apply {
            configuration.db.let {
                driverClassName = it.driverClass
                jdbcUrl = it.url
                username = it.user
                password = it.password
            }
        }
        val dataSource = HikariDataSource(hikariConfig)
        val jdbi = Jdbi.create(dataSource)

        val studentClient = StudentClient(jerseyClient.target("http://${configuration.client.student}"))
        val subjectClient = SubjectClient(jerseyClient.target("http://${configuration.client.subject}"))
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        val coursePlanDao = CoursePlanDao(jdbi)
        environment.jersey().apply {
            register(CoursePlanResource(studentClient, subjectClient, coroutineScope, coursePlanDao))
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            CoursePlanServiceApp().run(*args)
        }
    }
}


