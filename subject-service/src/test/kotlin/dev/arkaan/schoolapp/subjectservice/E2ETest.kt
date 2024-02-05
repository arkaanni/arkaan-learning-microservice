package dev.arkaan.schoolapp.subjectservice

import dev.arkaan.schoolapp.subjectservice.domain.Subject
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.ktor.test.dispatcher.*
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Test
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

/**
 * Reference: https://gist.github.com/Stexxe/87b6a4985ceda80e1444208cb64826c4
 */
class E2ETest {

    companion object {
        private lateinit var server: TestApplicationEngine

        @ClassRule
        @JvmField
        val mysql = MySQLContainer(DockerImageName.parse("mysql:8.0.32")).apply {
            withInitScript("init_db.sql")
        }

        @BeforeClass
        @JvmStatic
        fun setUp() {
            server = TestApplicationEngine(createTestEnvironment {
                developmentMode = false
                config = MapApplicationConfig(
                    "db.host" to mysql.host,
                    "db.port" to mysql.firstMappedPort.toString(),
                    "db.user" to mysql.username,
                    "db.password" to mysql.password,
                    "db.database" to mysql.databaseName,
                    "db.driver" to "com.mysql.cj.jdbc.Driver"
                )
                module {
                    setUp()
                }
            })
            server.start(false)
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            server.stop()
            mysql.close()
        }
    }

    @Test
    fun `get all subjects`() = testSuspend {
        val response = server.client.get("/subject")
        assertEquals(HttpStatusCode.OK, response.status)
        // TODO :( ssdsddd
    }

    @Test

    fun `get subject by code`() = testSuspend {
        val code = "MK01"
        val response = server.client.get("/subject/$code")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(
            "{\"subject_code\":\"MK01\",\"name\":\"Mathematics\",\"description\":\"Mathematics for first year.\"}",
            response.bodyAsText()
        )
    }

    @Test
    fun `insert subject should success`() = testSuspend {
        val response = server.client
            .config {
                install(ContentNegotiation) { jackson() }
            }.post("/subject") {
                header("Content-Type", "application/json")
                setBody(Subject("MP01", "Physics", "Physics"))
            }
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `insert subject should fail if already exists`() = testSuspend {
        val response = server.client
            .config {
                install(ContentNegotiation) { jackson() }
            }.post("/subject") {
                header("Content-Type", "application/json")
                setBody(Subject("MK01", "Physics", "Physics"))
            }
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Subject already exists.", response.bodyAsText())
    }
}