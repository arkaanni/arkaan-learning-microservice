package dev.arkaan.schl.courseplan

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.kittinunf.fuel.Fuel
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import dev.arkaan.schl.courseplan.client.domain.Schedule
import dev.arkaan.schl.courseplan.client.domain.Subject
import io.jooby.Jooby
import io.jooby.MediaType
import io.jooby.StatusCode
import io.jooby.test.JoobyTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName
import kotlin.test.assertEquals

@JoobyTest(value = CoursePlanApp::class, port = 8899, factoryMethod = "createApp")
class E2ETest {
    companion object {
        private val mapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
        private const val BASE_URL = "http://localhost:8899/courseplan"

        @JvmStatic
        @Container
        private val mysql = MySQLContainer(DockerImageName.parse("mysql:8.0.32"))
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test")
            .withExposedPorts(3306, 3307)
            .withInitScript("init_db.sql")
            .waitingFor(Wait.forHealthcheck())

        @JvmStatic
        private val wireMock = WireMockServer(8888)

        @JvmStatic
        @Suppress("unused")
        fun createApp(): Jooby {
            mysql.start()
            wireMock.start()
            System.getProperties().apply {
                put("db_host", mysql.host)
                put("db_port", mysql.getMappedPort(3306).toString())
                put("db_user", mysql.username)
                put("db_password", mysql.password)
                put("application.env", "test")
                with (wireMock) {
                    put("subject_api", "localhost:${port()}")
                    put("student_api", "localhost:${port()}")
                    put("schedule_api", "localhost:${port()}")
                }
            }
            return CoursePlanApp()
        }

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

    @Test
    fun `should get all course plan`() {
        val response = Fuel.get(BASE_URL).response()
        assertEquals(StatusCode.OK_CODE, response.second.statusCode)
    }

    @Test
    fun `should not add new course plan if subject not exists`() {
        val response = Fuel.post(BASE_URL).apply {
            body("{\"subjectCode\":\"MK01\",\"semester\":1,\"year\":2023,\"scheduleId\":\"123123\"}")
            header("Content-Type", "application/json")
        }.response()

        assertEquals(StatusCode.BAD_REQUEST_CODE, response.second.statusCode)
        assertEquals("Subject code and/or schedule id not found",
            response.second.body().asString(MediaType.json.value))
    }

    @Test
    fun `should add new course plan if schedule and subject are valid`() {
        mockSubjectClient()
        mockScheduleClient()
        val response = Fuel.post(BASE_URL).apply {
            body("{\"subjectCode\":\"MK01\",\"semester\":1,\"year\":2023,\"scheduleId\":\"123123\"}")
            header("Content-Type", MediaType.json.value)
        }.response()
        println(response.second.body().asString(MediaType.json.value))
        assertEquals(StatusCode.OK_CODE, response.second.statusCode)
    }

    @Test
    fun `should add enrollment`() {
        // TODO
    }

    private fun mockScheduleClient() {
        wireMock.stubFor(
            get(urlEqualTo("/schedule/recurring/123123")).willReturn(
                ok(
                    mapper.writeValueAsString(Schedule("123123", 1))
                ).withHeader("Content-Type", MediaType.json.value)
            )
        )
    }

//    private fun mockStudentClient() {
//        wireMock.stubFor(
//            get(urlEqualTo("/students/13123")).willReturn(
//                ok(
//                    mapper.writeValueAsString(Student("13123", "John", "Titor", 1))
//                ).withHeader("Content-Type", MediaType.json.value)
//            )
//        )
//    }

    private fun mockSubjectClient() {
        wireMock.stubFor(
            get(urlEqualTo("/subject/MK01")).willReturn(
                ok(
                    mapper.writeValueAsString(Subject("MK01", "Mathematics"))
                ).withHeader("Content-Type", MediaType.json.value)
            )
        )
    }
}