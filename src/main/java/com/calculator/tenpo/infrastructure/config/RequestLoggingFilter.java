package com.calculator.tenpo.infrastructure.config;

import com.calculator.tenpo.infrastructure.service.ApiCallHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestLoggingFilter implements WebFilter {

	private final ApiCallHistoryService historyService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String path = exchange.getRequest().getPath().toString();

		if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger") || path.startsWith("/swagger-ui") || path.startsWith("/actuator")) {
			return chain.filter(exchange);
		}

		long startTime = System.currentTimeMillis();
		String endpoint = exchange.getRequest().getPath().toString();
		String params = exchange.getRequest().getQueryParams().toString();

		ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(exchange.getResponse()) {
			@Override
			public Mono<Void> writeWith(org.reactivestreams.Publisher<? extends org.springframework.core.io.buffer.DataBuffer> body) {
				return Mono.from(body).flatMap(dataBuffer -> {
					byte[] content = new byte[dataBuffer.readableByteCount()];
					dataBuffer.read(content);
					String responseBody = new String(content, StandardCharsets.UTF_8);
					HttpStatus status = HttpStatus.valueOf(getStatusCode().value());

					String statusText = status.is2xxSuccessful() ? "SUCCESS" : "ERROR";

					historyService.saveCallHistory(endpoint, params, responseBody, statusText);

					long duration = System.currentTimeMillis() - startTime;
					log.info("Request to {} took {}ms - Status: {}", endpoint, duration, statusText);

					return super.writeWith(Mono.just(dataBuffer));
				});
			}
		};

		return chain.filter(exchange.mutate().response(responseDecorator).build());
	}
}