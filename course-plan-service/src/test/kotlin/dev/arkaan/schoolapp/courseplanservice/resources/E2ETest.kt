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
import jakarta.ws.rs.client.Entity
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.jetty.http.HttpStatus
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName
import kotlin.test.assertEquals

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
        @AfterAll
        fun tearDown() {
            mysql.stop()
            wireMock.stop()
        }
    }

    @BeforeEach
    fun resetWireMock() {
        wireMock.resetAll()
    }

    private val endpoint = "http://localhost:${server.localPort}/course-plan"

    @Test
    fun `should get all course plan`() {
        val response = server.client().target(endpoint)
            .request()
            .accept(MediaType.APPLICATION_JSON_TYPE)
            .get()
        assertEquals(HttpStatus.OK_200, response.status)
    }

    @Test
    fun `should not add new course plan if student not exists`() {
        mockSubjectCLient()
        val response = server.client().target(endpoint)
            .request()
            .post(
                Entity.entity(
                    CoursePlanRequest("13123", "MK01", 1, 2023),
                    MediaType.APPLICATION_JSON
                )
            )
        assertEquals(HttpStatus.NOT_FOUND_404, response.status)
        assertEquals("{\"code\":404,\"message\":\"Student does not exists.\"}", response.readEntity(String::class.java))
    }

    @Test
    fun `should not add new course plan if subject not exists`() {
        mockStudentClient()
        val response = server.client().target(endpoint)
            .request()
            .post(
                Entity.entity(
                    CoursePlanRequest("13123", "MK01", 1, 2023),
                    MediaType.APPLICATION_JSON
                )
            )
        assertEquals(HttpStatus.NOT_FOUND_404, response.status)
        assertEquals("{\"code\":404,\"message\":\"Subject does not exists.\"}", response.readEntity(String::class.java))
    }

    @Test
    fun `should add new course plan if student and subject are valid`() {
        mockStudentClient()
        mockSubjectCLient()
        val response = server.client().target(endpoint)
            .request()
            .post(
                Entity.entity(
                    CoursePlanRequest("13123", "MK01", 1, 2023),
                    MediaType.APPLICATION_JSON
                )
            )
        assertEquals(HttpStatus.OK_200, response.status)
    }

    private fun mockStudentClient() {
        wireMock.stubFor(
            get(urlEqualTo("/students/13123")).willReturn(
                ok(
                    mapper.writeValueAsString(Response.ok(Student().apply {
                        studentId = "13123"
                        firstName = "John"
                        lastName = "Titor"
                    }).build())
                ).withHeader("Content-Type", MediaType.APPLICATION_JSON)
            )
        )
    }

    private fun mockSubjectCLient() {
        wireMock.stubFor(
            get(urlEqualTo("/subject/MK01")).willReturn(
                ok(
                    mapper.writeValueAsString(Response.ok(
                        Subject(
                            "MK01",
                            "Mathematics"
                        )).build())
                ).withHeader("content-type", MediaType.APPLICATION_JSON)
            )
        )
    }
}