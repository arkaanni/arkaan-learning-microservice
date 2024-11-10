package dev.arkaan.schl.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        return builder.routes()
                .route(p -> p
                        .path("/student")
                        .uri("http://" + uriConfiguration.getStudentService()))
                .route(p -> p
                        .path("/room/**")
                        .uri("http://" + uriConfiguration.getRoomService()))
                .route(p -> p
                        .path("/courseplan")
                        .uri("http://" + uriConfiguration.getCoursePlanService()))
                .route(p -> p
                        .path("/subject")
                        .uri("http://" + uriConfiguration.getSubjectService()))
                .build();
    }
}
