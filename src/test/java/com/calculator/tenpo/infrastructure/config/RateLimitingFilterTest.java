package com.calculator.tenpo.infrastructure.config;


import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RateLimitingFilterTest {

	@Mock
	private Bucket bucket;

	@Mock
	private WebFilterChain filterChain;

	@InjectMocks
	private RateLimitingFilter rateLimitingFilter;

	private MockServerWebExchange exchange;

	@BeforeEach
	void setUp() {
		exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/api/test").build());
	}

	@Test
	void shouldAllowRequestWhenTokensAreAvailable() {
		when(bucket.tryConsume(1)).thenReturn(true);
		when(filterChain.filter(exchange)).thenReturn(Mono.empty());

		rateLimitingFilter.filter(exchange, filterChain).block();

		verify(filterChain, times(1)).filter(exchange);
		assertThat(exchange.getResponse().getStatusCode()).isNull();
	}

	@Test
	void shouldRejectRequestWhenRateLimitExceeded() {
		when(bucket.tryConsume(1)).thenReturn(false);

		rateLimitingFilter.filter(exchange, filterChain).block();

		assertThat(exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.TOO_MANY_REQUESTS);
		verify(filterChain, never()).filter(exchange);
	}
}