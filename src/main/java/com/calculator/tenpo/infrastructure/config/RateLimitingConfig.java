package com.calculator.tenpo.infrastructure.config;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Configuration
public class RateLimitingConfig {

	@Bean
	public Bucket createBucket() {
		Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));
		return Bucket.builder().addLimit(limit).build();
	}

	public Mono<ServerResponse> rateLimit(ServerRequest request, Bucket bucket) {
		if (bucket.tryConsume(1)) {
			return Mono.empty();
		} else {
			return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
					.bodyValue("Rate limit exceeded. Please try again later.");
		}
	}
}

