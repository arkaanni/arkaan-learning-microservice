package dev.arkaan.schl.roomservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "room-service")
)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
