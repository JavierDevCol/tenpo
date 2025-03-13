package com.calculator.tenpo.infrastructure.controller;

import com.calculator.tenpo.aplication.port.input.CalculationPort;
import com.calculator.tenpo.infrastructure.input.controller.CalculationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculationControllerTest {

	@Mock
	private CalculationPort calculationService;

	@InjectMocks
	private CalculationController calculationController;

	private WebTestClient webTestClient;

	@BeforeEach
	void setUp() {
		webTestClient = WebTestClient.bindToController(calculationController).build();
	}

	@Test
	void testCalculateWithPercentage_Success() {
		BigDecimal num1 = new BigDecimal("100");
		BigDecimal num2 = new BigDecimal("50");
		BigDecimal expectedResponse = new BigDecimal("165");

		when(calculationService.calculateWithPercentage(num1, num2)).thenReturn(Mono.just(expectedResponse));

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder.path("/api/calculate")
						.queryParam("num1", num1)
						.queryParam("num2", num2)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(BigDecimal.class)
				.isEqualTo(expectedResponse);
	}

	@Test
	void testCalculateWithPercentage_BadRequest() {
		BigDecimal num1 = new BigDecimal("100");
		BigDecimal num2 = new BigDecimal("50");

		when(calculationService.calculateWithPercentage(num1, num2)).thenReturn(Mono.empty());

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder.path("/api/calculate")
						.queryParam("num1", num1)
						.queryParam("num2", num2)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Test
	void calculate_shouldHandleZeroValues() {
		BigDecimal num1 = BigDecimal.ZERO;
		BigDecimal num2 = BigDecimal.ZERO;
		BigDecimal expected = BigDecimal.ZERO;

		when(calculationService.calculateWithPercentage(num1, num2))
				.thenReturn(Mono.just(expected));

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/api/calculate")
						.queryParam("num1", "0")
						.queryParam("num2", "0")
						.build())
				.exchange()
				.expectStatus().isOk()
				.expectBody(BigDecimal.class)
				.isEqualTo(expected);
	}

	@Test
	void calculate_shouldHandleExtremeValues() {
		BigDecimal num1 = new BigDecimal("9999999999999999999.99999");
		BigDecimal num2 = new BigDecimal("0.00001");
		BigDecimal expected = new BigDecimal("11000000000000000000");

		when(calculationService.calculateWithPercentage(num1, num2))
				.thenReturn(Mono.just(expected));

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/api/calculate")
						.queryParam("num1", num1.toPlainString())
						.queryParam("num2", num2.toPlainString())
						.build())
				.exchange()
				.expectStatus().isOk()
				.expectBody(BigDecimal.class)
				.isEqualTo(expected);
	}}
