package dev.arkaan.schoolapp.courseplanservice

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.zaxxer.hikari.HikariDataSource
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.core.setup.Environment
import jakarta.inject.Named
import jakarta.ws.rs.client.Client
import jakarta.ws.rs.client.WebTarget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.jdbi.v3.core.Jdbi
import javax.sql.DataSource

class AppModule(
    private val configuration: CoursePlanServiceConfiguration,
    private val environment: Environment
) : AbstractModule() {
    override fun configure() {
        val jerseyClient = JerseyClientBuilder(environment)
            .using(configuration.jerseyClient)
            .build(environment.name)
        bind(Client::class.java).toInstance(jerseyClient)
        bind(CoroutineScope::class.java).toInstance(CoroutineScope(Dispatchers.Default))
    }

    @Provides
    @Named("StudentClient")
    fun provideStudentClientTarget(client: Client): WebTarget {
        return client.target("http://${configuration.client.student}")
    }

    @Provides
    @Named("SubjectClient")
    fun provideSubjectClientTarget(client: Client): WebTarget {
        return client.target("http://${configuration.client.subject}")
    }

    @Provides
    fun provideDataSource(): DataSource {
        return HikariDataSource().apply {
            configuration.db.let {
                driverClassName = it.driverClass
                jdbcUrl = it.url
                username = it.user
                password = it.password
            }
        }
    }

    @Provides
    fun provideJdbi(dataSource: DataSource): Jdbi {
        return Jdbi.create(dataSource)
    }
}