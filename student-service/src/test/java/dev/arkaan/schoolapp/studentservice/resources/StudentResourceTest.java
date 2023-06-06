package dev.arkaan.schoolapp.studentservice.resources;

import dev.arkaan.schoolapp.studentservice.ServiceApp;
import dev.arkaan.schoolapp.studentservice.ServiceConfiguration;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@ExtendWith(DropwizardExtensionsSupport.class)
class StudentResourceTest {

    @Container
    private static MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.32"))
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test")
            .withExposedPorts(3306, 3307)
            .waitingFor(Wait.forHealthcheck());

    static {
        mysql.start();
    }

    @AfterAll
    static void tearDown() {
        mysql.stop();
    }

    private static DropwizardAppExtension<ServiceConfiguration> server = new DropwizardAppExtension<>(
            ServiceApp.class,
            ResourceHelpers.resourceFilePath("test-config.yml"),
            ConfigOverride.config("db.url", String.format("jdbc:mysql://%s:%d/test", mysql.getHost(), mysql.getMappedPort(3306)))
    );

    @Test
    void dummyTest() {
        assertNotNull(server.client());
    }
}