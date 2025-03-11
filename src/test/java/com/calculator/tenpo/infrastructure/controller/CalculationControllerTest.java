package com.calculator.tenpo.infrastructure.controller;


import com.calculator.tenpo.infrastructure.service.CalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(CalculationController.class)
class CalculationControllerTest {

	@MockBean
	private CalculationService calculationService;

	@InjectMocks
	private CalculationController controller;

	private final WebTestClient webTestClient = WebTestClient.bindToController(controller).build();

	@Test
	void shouldReturnCalculatedResult() {
		BigDecimal num1 = new BigDecimal("5");
		BigDecimal num2 = new BigDecimal("5");
		BigDecimal expected = new BigDecimal("11");

		when(calculationService.calculateWithPercentage(num1, num2)).thenReturn(Mono.just(expected));

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder.path("/api/calculate")
						.queryParam("num1", num1)
						.queryParam("num2", num2)
						.build())
				.exchange()
				.expectStatus().isOk()
				.expectBody(BigDecimal.class).isEqualTo(expected);
	}
}
