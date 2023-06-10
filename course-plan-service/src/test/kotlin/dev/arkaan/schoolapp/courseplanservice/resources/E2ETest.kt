package dev.arkaan.schoolapp.courseplanservice.resources

import dev.arkaan.schoolapp.courseplanservice.CoursePlanServiceApp
import dev.arkaan.schoolapp.courseplanservice.api.CoursePlanRequest
import dev.arkaan.schoolapp.courseplanservice.api.Student
import dev.arkaan.schoolapp.courseplanservice.api.Subject
import io.dropwizard.testing.ConfigOverride
import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit5.DropwizardAppExtension
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport
import jakarta.ws.rs.Path
import jakarta.ws.rs.client.Entity
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.jetty.http.HttpStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@Testcontainers
@ExtendWith(DropwizardExtensionsSupport::class)
class E2ETest {
    companion object {
        @JvmStatic
        @Container
        private val mysql = MySQLContainer(DockerImageName.parse("mysql:8.0.32")).apply {
            withUsername("test")
            withPassword("test")
            withDatabaseName("test")
            withExposedPorts(3306, 3307)

            waitingFor(Wait.forHealthcheck())
        }

        init {
            mysql.start()
        }

        @JvmStatic
        var server = DropwizardAppExtension(
            CoursePlanServiceApp::class.java,
            ResourceHelpers.resourceFilePath("test-config.yml"),
            ConfigOverride.config(
                "db.url",
                String.format("jdbc:mysql://%s:%d/test", mysql.host, mysql.getMappedPort(3306))
            ),
        )

        @Path("/")
        class Stub {
            @Path("/student/13123")
            fun getDummyStudent(): Response = Response.ok(
                Student().apply {
                    studentId = "13123"
                    firstName = "John"
                    lastName = "Titor"
                }).build()

            @Path("/subject/MK01")
            fun getDummySubject(): Response = Response.ok(
                Subject(
                    "MK01",
                    "Mathematics"
                )
            ).build()
        }
    }

    // TODO: Make the test work!
    @Test
    fun dummyTest() {
        assertNotNull(server.environment)

        val response = server.client().target("http://localhost:8444")
            .path("course-plan")
            .request()
            .post(
                Entity.entity(
                    CoursePlanRequest("13123", "MK01", 1, 2023),
                    MediaType.APPLICATION_JSON
                )
            )
        assertEquals(HttpStatus.OK_200, response.status)
    }
}