package dev.arkaan.schoolapp.courseplanservice.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import dev.arkaan.schoolapp.courseplanservice.CoursePlanServiceApp
import dev.arkaan.schoolapp.courseplanservice.api.CoursePlanRequest
import dev.arkaan.schoolapp.courseplanservice.api.Student
import dev.arkaan.schoolapp.courseplanservice.api.Subject
import io.dropwizard.testing.ConfigOverride
import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit5.DropwizardAppExtension
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport
import jakarta.ws.rs.client.Entity
import jakarta.ws.rs.core.MediaType
import org.eclipse.jetty.http.HttpStatus
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
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
        private val mapper = ObjectMapper()

        @JvmStatic
        @Container
        private val mysql = MySQLContainer(DockerImageName.parse("mysql:8.0.32")).apply {
            withUsername("test")
            withPassword("test")
            withDatabaseName("test")
            withExposedPorts(3306, 3307)
            withInitScript("init_db.sql")
            waitingFor(Wait.forHealthcheck())
        }

        @JvmStatic
        private val wireMock = WireMockServer(8888)

        init {
            mysql.start()
            wireMock.start()
        }

        @JvmField
        @RegisterExtension
        val server = DropwizardAppExtension(
            CoursePlanServiceApp::class.java,
            ResourceHelpers.resourceFilePath("test-config.yml"),
            ConfigOverride.config(
                "db.url", "jdbc:mysql://${mysql.host}:${mysql.getMappedPort(3306)}/test",
            ),
            ConfigOverride.config("client.student", "localhost:${wireMock.port()}"),
            ConfigOverride.config("client.subject", "localhost:${wireMock.port()}")
        )

        @JvmStatic
        @BeforeAll
        fun setUp() {
            wireMock.stubFor(
                get(urlEqualTo("/students/13123")).willReturn(
                    ok(
                        mapper.writeValueAsString(Student().apply {
                            studentId = "13123"
                            firstName = "John"
                            lastName = "Titor"
                        })
                    ).withHeader("content-type", MediaType.APPLICATION_JSON)
                )
            )
            wireMock.stubFor(
                get(urlEqualTo("/subject/MK01")).willReturn(
                    ok(
                        mapper.writeValueAsString(
                            Subject(
                                "MK01",
                                "Mathematics"
                            )
                        )
                    ).withHeader("content-type", MediaType.APPLICATION_JSON)
                )
            )
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            mysql.stop()
            wireMock.stop()
        }
    }

    @Test
    fun `should add new course plan if student and subject are valid`() {
        assertNotNull(server.environment)
        val response = server.client().target("http://localhost:${server.localPort}")
            .path("/course-plan")
            .request()
            .post(
                Entity.entity(
                    CoursePlanRequest("13123", "MK01", 1, 2023),
                    MediaType.APPLICATION_JSON
                )
            )
        assertEquals(HttpStatus.OK_200, response.status)
        assertEquals("OK", response.readEntity(String::class.java))
    }
}