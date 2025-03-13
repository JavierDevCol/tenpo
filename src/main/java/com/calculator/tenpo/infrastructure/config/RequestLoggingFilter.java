package com.calculator.tenpo.infrastructure.config;

import com.calculator.tenpo.aplication.model.dto.ApiCallHistoryDto;
import com.calculator.tenpo.aplication.port.out.ApiCallHistoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestLoggingFilter implements WebFilter {

	private final ApiCallHistoryPort historyService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String path = exchange.getRequest().getPath().toString();

		if (path.startsWith("/webjars") || path.startsWith("/v3/api-docs") || path.startsWith("/swagger") || path.startsWith("/swagger-ui") || path.startsWith("/actuator")) {
			return chain.filter(exchange);
		}

		long startTime = System.currentTimeMillis();
		String params = exchange.getRequest().getQueryParams().toString();

		ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(exchange.getResponse()) {
			@Override
			public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
				return DataBufferUtils.join(Flux.from(body)) // Combinar los DataBuffers en uno solo
						.flatMap(dataBuffer -> {
							byte[] content = new byte[dataBuffer.readableByteCount()];
							dataBuffer.read(content);
							DataBufferUtils.release(dataBuffer); // Liberar memoria

							String responseBody = new String(content, StandardCharsets.UTF_8);
							HttpStatus status = HttpStatus.resolve(getStatusCode().value());
							String statusText = (status != null && status.is2xxSuccessful()) ? "SUCCESS" : "ERROR";

							// Guardar en el historial de llamadas de forma asíncrona
							historyService.save(ApiCallHistoryDto.builder()
									.timestamp(LocalDateTime.now())
									.endpoint(path)
									.requestParams(params)
									.response(responseBody)
									.status(statusText)
									.build());

							long duration = System.currentTimeMillis() - startTime;
							log.info("Request to {} took {}ms - Status: {} - Response: {}", path, duration, statusText, responseBody);

							return super.writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(content)));
						});
			}
		};

		return chain.filter(exchange.mutate().response(decoratedResponse).build());
	}
}