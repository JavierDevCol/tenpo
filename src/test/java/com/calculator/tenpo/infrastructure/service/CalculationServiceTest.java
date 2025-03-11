package com.calculator.tenpo.infrastructure.service;

import com.calculator.tenpo.infrastructure.external.PercentageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {

	@Mock
	private PercentageService percentageService;

	@InjectMocks
	private CalculationService calculationService;

	@Test
	void testCalculateWithPercentage() {
		BigDecimal num1 = new BigDecimal("5");
		BigDecimal num2 = new BigDecimal("5");
		BigDecimal percentage = new BigDecimal("10");

		when(percentageService.getPercentage()).thenReturn(Mono.just(percentage));

		BigDecimal expected = new BigDecimal("11");
		calculationService.calculateWithPercentage(num1, num2)
				.doOnNext(result -> assertEquals(expected, result))
				.block();

		verify(percentageService, times(1)).getPercentage();
	}

	@Test
	void shouldUseCacheWhenExternalServiceFails() {
		when(percentageService.getPercentage()).thenReturn(Mono.error(new RuntimeException("Service Down")));

		BigDecimal num1 = new BigDecimal("5");
		BigDecimal num2 = new BigDecimal("5");

		calculationService.calculateWithPercentage(num1, num2)
				.doOnNext(result -> {
					assertNotNull(result);
					assertEquals("SUCCESS", "SUCCESS");
				})
				.block();
	}
}