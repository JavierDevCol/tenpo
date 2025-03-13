package com.calculator.tenpo.infrastructure.config;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitingConfig {

	@Bean
	public Bucket bucket() {
		return Bucket.builder()
				.addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1))))
				.build();
	}

}

