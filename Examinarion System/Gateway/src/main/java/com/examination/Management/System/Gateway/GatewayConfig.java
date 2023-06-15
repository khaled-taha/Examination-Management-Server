package com.examination.Management.System.Gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("exam-service", r -> r.path("api/exam/**")
                        .uri("http://localhost:8082"))
                .route("code-Judgment", r -> r.path("api/code/**")
                        .uri("http://localhost:8083"))
                .build();
    }
}
