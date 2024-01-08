package com.cbh.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes().route("CRYPTO-SERVICE", r -> r.path("/crypto/**").uri("lb://CRYPTO-SERVICE"))
				.route("PAYMENT-SERVICE", r -> r.path("/payment/**").uri("lb://PAYMENT-SERVICE")).build();
	}
}
