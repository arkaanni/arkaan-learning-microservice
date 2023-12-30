package dev.arkaan.schoolapp.courseplanservice

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.arkaan.schoolapp.courseplanservice.client.StudentClient
import dev.arkaan.schoolapp.courseplanservice.client.SubjectClient
import dev.arkaan.schoolapp.courseplanservice.resources.CoursePlanResource
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.core.setup.Environment
import jakarta.ws.rs.client.Client
import jakarta.ws.rs.client.WebTarget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.jdbi.v3.core.Jdbi

class AppModule(
    private val configuration: CoursePlanServiceConfiguration,
    private val environment: Environment
) : AbstractModule() {
    override fun configure() {
        val hikariConfig = HikariConfig().apply {
            configuration.db.let {
                driverClassName = it.driverClass
                jdbcUrl = it.url
                username = it.user
                password = it.password
            }
        }
        
        val jerseyClient = JerseyClientBuilder(environment)
            .using(configuration.jerseyClient)
            .build(environment.name)
        
        bind(WebTarget::class.java).annotatedWith(Names.named("StudentClient")).toInstance(jerseyClient.target("http://${configuration.client.student}"))
        bind(WebTarget::class.java).annotatedWith(Names.named("SubjectClient")).toInstance(jerseyClient.target("http://${configuration.client.subject}"))
        
        bind(Jdbi::class.java).toInstance(Jdbi.create(HikariDataSource(hikariConfig)))
        bind(CoursePlanResource::class.java)
        bind(CoroutineScope::class.java).toInstance(CoroutineScope(Dispatchers.Default))
    }
}