package com.calculator.tenpo.aplication.service;

import com.calculator.tenpo.aplication.port.out.PercentagePort;
import com.calculator.tenpo.domain.model.PercentageCalculation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {

	@Mock
	private PercentagePort percentagePort;

	@InjectMocks
	private CalculationService calculationService;


	@Test
	void calculateWithPercentage_successfulCalculation() {
		// Arrange
		BigDecimal num1 = BigDecimal.valueOf(100);
		BigDecimal num2 = BigDecimal.valueOf(50);
		BigDecimal percentage = BigDecimal.valueOf(10);
		BigDecimal expectedResult = new PercentageCalculation(num1, num2, percentage).calculate();

		when(percentagePort.getPercentage()).thenReturn(Mono.just(percentage));

		// Act
		Mono<BigDecimal> result = calculationService.calculateWithPercentage(num1, num2);

		// Assert
		StepVerifier.create(result)
				.expectNext(expectedResult)
				.verifyComplete();

		verify(percentagePort, times(1)).getPercentage();
	}

	@Test
	void calculateWithPercentage_errorGettingPercentage_returnsDefaultValue() {
		// Arrange
		BigDecimal num1 = BigDecimal.valueOf(100);
		BigDecimal num2 = BigDecimal.valueOf(50);
		BigDecimal defaultPercentage = BigDecimal.TEN;
		BigDecimal expectedResult = new PercentageCalculation(num1, num2, defaultPercentage).calculate();

		when(percentagePort.getPercentage()).thenReturn(Mono.error(new RuntimeException("Error getting percentage")));

		// Act
		Mono<BigDecimal> result = calculationService.calculateWithPercentage(num1, num2);

		// Assert
		StepVerifier.create(result)
				.expectNext(expectedResult)
				.verifyComplete();

		verify(percentagePort, times(1)).getPercentage();
	}
}