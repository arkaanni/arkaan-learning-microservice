package dev.arkaan.schoolapp.subjectservice

import dev.arkaan.schoolapp.subjectservice.domain.Subject
import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.PropertySource
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.runtime.server.EmbeddedServer
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import io.restassured.RestAssured.*
import org.hamcrest.Matchers.*

@Testcontainers
class E2ETest {

    companion object {
        private lateinit var server: EmbeddedServer
        private lateinit var client: RequestSpecification

        @Container
        @JvmField
        val mysql = MySQLContainer(DockerImageName.parse("mysql:8.0.32")).apply {
            withInitScript("init_db.sql")
            waitingFor(Wait.forHealthcheck())
        }

        @BeforeAll
        @JvmStatic
        fun setUp() {
            server = ApplicationContext.run(EmbeddedServer::class.java, PropertySource.of(mapOf(
                "datasource.subject.jdbc-url" to "jdbc:mysql://${mysql.host}:${mysql.firstMappedPort}/${mysql.databaseName}",
                "datasource.subject.username" to mysql.username,
                "datasource.subject.password" to mysql.password,
                "datasource.subject.driver-class-name" to mysql.driverClassName
            )), "test")
            server.start()
            client = given().baseUri(server.uri.toString())
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            server.stop()
            mysql.close()
        }
    }

    @Test
    fun `should running`() {
        Assertions.assertTrue(server.isRunning)
    }

    @Test
    fun `get all subjects`() {
         // TODO :( ssdsddd
    }

    @Test
    fun `get subject by code`() {
        val code = "MK01"
        client.get("/subject/$code")
            .then()
            .statusCode(HttpStatus.OK.code)
            .body(`is`("{\"subjectCode\":\"MK01\",\"name\":\"Mathematics\",\"description\":\"Mathematics for first year.\"}"))
    }

    @Test
    fun `insert subject should success`() {
        client.body("{\"subjectCode\":\"MP01\",\"name\":\"Physics\",\"description\":\"Physics\"}")
            .contentType(MediaType.APPLICATION_JSON)
            .post("/subject")
            .then()
            .statusCode(HttpStatus.OK.code)
    }

    @Test
    fun `insert subject should fail if already exists`() {
        client.body("{\"subjectCode\":\"MK01\",\"name\":\"Mathematics\",\"description\":\"Mathematics for first year.\"}")
            .contentType(MediaType.APPLICATION_JSON)
            .post("/subject")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.code)
            .body(`is`("Subject already exists."))
    }
}