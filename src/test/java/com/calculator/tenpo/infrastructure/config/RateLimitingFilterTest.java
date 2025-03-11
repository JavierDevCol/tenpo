package com.calculator.tenpo.infrastructure.config;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RateLimitingFilterTest {

	private Bucket bucket;
	private com.calculator.tenpo.infrastructure.config.RateLimitingFilter filter;
	private WebFilterChain filterChain;

	@BeforeEach
	void setUp() {
		Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));
		this.bucket = Bucket.builder().addLimit(limit).build();
		this.filter = new RateLimitingFilter(bucket);
		this.filterChain = mock(WebFilterChain.class);
	}

	@Test
	void shouldAllowRequestWhenTokensAvailable() {
		MockServerWebExchange exchange = MockServerWebExchange.from(
				org.springframework.mock.http.server.reactive.MockServerHttpRequest.get("/").build()
		);
		when(filterChain.filter(exchange)).thenReturn(Mono.empty());

		filter.filter(exchange, filterChain).block();
		assertEquals(HttpStatus.OK, exchange.getResponse().getStatusCode());
	}

	@Test
	void shouldRejectRequestWhenRateLimitExceeded() {
		for (int i = 0; i < 3; i++) {
			bucket.tryConsume(1);
		}
		MockServerWebExchange exchange = MockServerWebExchange.from(
				org.springframework.mock.http.server.reactive.MockServerHttpRequest.get("/").build()
		);

		filter.filter(exchange, filterChain).block();
		assertEquals(HttpStatus.TOO_MANY_REQUESTS, exchange.getResponse().getStatusCode());
	}
}