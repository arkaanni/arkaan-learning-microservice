package dev.arkaan.schoolapp.subjectservice

import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.PropertySource
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@MicronautTest(application = SubjectServiceApp::class)
@Testcontainers
class E2ETest {

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var app: EmbeddedServer

    companion object {
        private lateinit var server: EmbeddedServer

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
        Assertions.assertTrue(app.isRunning)
        Assertions.assertTrue(client.isRunning)
    }

    @Test
    fun `get by code`() {
        val code = "MK01"
        val response = client.toBlocking().retrieve("/subject/$code", HttpResponse::class.java)
        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertEquals(
            "{\"subject_code\":\"MK01\",\"name\":\"Mathematics\",\"description\":\"Mathematics for first year.\"}",
            response.body().toString()
        )
    }

//    @Test
//    fun `get all subjects`() = testSuspend {
//        val response = server.client.get("/subject")
//        assertEquals(HttpStatusCode.OK, response.status)
        // TODO :( ssdsddd
//    }

//    @Test
//    fun `get subject by code`() = testSuspend {
//        val code = "MK01"
//        val response = client.toBlocking().retrieve("/subject/$code", HttpResponse::class.java)
//        assertEquals(HttpStatus.OK, response.status)
//        assertEquals(
//            "{\"subject_code\":\"MK01\",\"name\":\"Mathematics\",\"description\":\"Mathematics for first year.\"}",
//            response.body().toString()
//        )
//    }

//    @Test
//    fun `insert subject should success`() = testSuspend {
//        val response = server.client
//            .config {
//                install(ContentNegotiation) { jackson() }
//            }.post("/subject") {
//                header("Content-Type", "application/json")
//                setBody(Subject("MP01", "Physics", "Physics"))
//            }
//        assertEquals(HttpStatusCode.OK, response.status)
//    }
//
//    @Test
//    fun `insert subject should fail if already exists`() = testSuspend {
//        val response = server.client
//            .config {
//                install(ContentNegotiation) { jackson() }
//            }.post("/subject") {
//                header("Content-Type", "application/json")
//                setBody(Subject("MK01", "Physics", "Physics"))
//            }
//        assertEquals(HttpStatusCode.BadRequest, response.status)
//        assertEquals("Subject already exists.", response.bodyAsText())
//    }
}