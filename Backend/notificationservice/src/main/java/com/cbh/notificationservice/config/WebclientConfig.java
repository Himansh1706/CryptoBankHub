package com.cbh.notificationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {

	@Bean
	WebClient.Builder saltedgeWebClientBuilder() {
		return WebClient.builder().defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	}

	@Bean
	WebClient saltedgeWebClient() {
		return saltedgeWebClientBuilder().build();
	}

	public <T> T get(String url, Class<T> responseType) {
		return saltedgeWebClient().get().uri(url).retrieve().bodyToMono(responseType).block();
	}

	public <T> T post(String url, Object requestBody, Class<T> responseType) {
		return saltedgeWebClient().post().uri(url).body(BodyInserters.fromValue(requestBody)).retrieve()
				.bodyToMono(responseType).block();
	}

}
