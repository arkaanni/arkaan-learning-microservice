package dev.arkaan.schoolapp.studentservice.resources;

import dev.arkaan.schoolapp.studentservice.ServiceApp;
import dev.arkaan.schoolapp.studentservice.ServiceConfiguration;
import dev.arkaan.schoolapp.studentservice.api.Student;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@ExtendWith(DropwizardExtensionsSupport.class)
class E2ETest {

    @Container
    private static MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.32"))
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test")
            .withExposedPorts(3306, 3307)
            .withInitScript("init_db.sql")
            .waitingFor(Wait.forHealthcheck());

    static {
        mysql.start();
    }

    @AfterAll
    static void tearDown() {
        mysql.stop();
    }

    private static final DropwizardAppExtension<ServiceConfiguration> server = new DropwizardAppExtension<>(
            ServiceApp.class,
            ResourceHelpers.resourceFilePath("test-config.yml"),
            ConfigOverride.config("db.url", String.format("jdbc:mysql://%s:%d/test", mysql.getHost(), mysql.getMappedPort(3306)))
    );

    @Test
    void shouldReturn_student_ifExist() {
        Response response = server.client()
                .target("http://localhost:8443")
                .path("/students/121983")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, response.getStatus());
        var student = response.readEntity(Student.class);
        assertEquals("121983", student.getStudentId());
    }

    @Test
    void shouldAdd_new_student() {
        var student = new Student("123456", "John", "Doe", "Earth", "1234567890", (byte) 1);
        try (Response response = server.client()
                .target("http://localhost:8443")
                .path("/students")
                .request()
                .post(Entity.entity(student, MediaType.APPLICATION_JSON))) {
            assertEquals(200, response.getStatus());
        }
    }

    @Test
    void shouldReturn_badRequest_if_studentId_alreadyExists() {
        var student = new Student("121983", "John", "Doe", "Earth", "1234567890", (byte) 1);
        try (Response response = server.client()
                .target("http://localhost:8443")
                .path("/students")
                .request()
                .post(Entity.entity(student, MediaType.APPLICATION_JSON))) {
            assertEquals(400, response.getStatus());
        }
    }
}