package dev.arkaan.schoolapp.roomservice;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class E2ETest {

    private static final MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.32"));

    @BeforeClass
    public static void setUp() {
        mysql.withInitScript("db.sql")
                .withUsername("test")
                .withPassword("test")
                .withDatabaseName("room")
                .waitingFor(Wait.forHealthcheck())
                .start();
    }

    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.jdbcUrl", () -> "jdbc:mysql://localhost:" + mysql.getFirstMappedPort() + "/room");
    }

    @AfterClass
    public static void tearDown() {
        mysql.stop();
    }

    @Test
    public void shouldGetCategoryList() throws Exception {
        mockMvc.perform(get("/room/category"))
                .andExpect(result -> assertEquals(200, result.getResponse().getStatus()));
    }

    @Test
    public void shouldNotAddCategoryIfAlreadyExists() throws Exception {
        mockMvc.perform(post("/room/category")
                .content("Classroom"))
                .andExpect(result -> {
                    assertEquals(400, result.getResponse().getStatus());
                    assertEquals("Category already exists", result.getResponse().getContentAsString());
                });
    }

    @Test
    public void shouldAddCategory() throws Exception {
        mockMvc.perform(post("/room/category")
                        .content("Meeting Room"))
                .andExpect(result -> {
                    assertEquals(200, result.getResponse().getStatus());
                });
    }
}
