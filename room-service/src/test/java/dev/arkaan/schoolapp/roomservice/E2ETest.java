package dev.arkaan.schoolapp.roomservice;

import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@AutoConfigureMockMvc
public class E2ETest {

    @ClassRule
    public static MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.32"))
            .withExposedPorts(3306,3307)
            .withInitScript("db.sql")
            .withUsername("test")
            .withPassword("test")
            .waitingFor(Wait.forHealthcheck());

    @Autowired
    private MockMvc mockMvc;

    @AfterClass
    public static void tearDown() {
        mysql.stop();
    }

    @Test
    public void dummy() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/room"))
                .andReturn();
    }
}
