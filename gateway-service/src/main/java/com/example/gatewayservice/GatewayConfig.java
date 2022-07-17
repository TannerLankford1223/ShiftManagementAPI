package com.example.gatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder routeBuilder) {

        return routeBuilder.routes()
                .route(r -> r.path("/api/v1/address/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("fallback")
                                .setFallbackUri("forward:/fallback/address"))
                                .rewritePath("/api/v1/address/api-docs", "/v3/api-docs"))
                        .uri("lb://address-service"))

                .route(r -> r.path("/api/v1/employee/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("fallback")
                                        .setFallbackUri("forward:/fallback/employee"))
                                .rewritePath("/api/v1/employee/api-docs", "/v3/api-docs"))
                        .uri("lb://employee-service"))

                .route(r -> r.path("/api/v1/shift/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("fallback")
                                        .setFallbackUri("forward:/fallback/shift"))
                                .rewritePath("/api/v1/shift/api-docs", "/v3/api-docs"))
                        .uri("lb://shift-service"))

                .route(r -> r.path("/api/v1/email/**")
                        .filters(f -> f.rewritePath("/api/v1/email/api-docs", "/v3/api-docs"))
                        .uri("lb://email-service"))

                .build();
    }
}
