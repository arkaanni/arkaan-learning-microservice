package dev.arkaan.schl.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
class GatewayApplicationTests {

	@Test
	void contextLoads() {
	}

}
