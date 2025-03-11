package com.calculator.tenpo.infrastructure.service;


import com.calculator.tenpo.infrastructure.external.PercentageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalculationService {

	private final PercentageService percentageService;

	public Mono<BigDecimal> calculateWithPercentage(BigDecimal num1, BigDecimal num2) {
		return percentageService.getPercentage()
				.map(percentage -> {
					BigDecimal sum = num1.add(num2);
					BigDecimal increment = sum.multiply(percentage).divide(BigDecimal.valueOf(100));
					return sum.add(increment);
				})
				.doOnSuccess(result -> log.info("Calculated result: {}", result))
				.doOnError(error -> log.error("Error calculating percentage", error));
	}
}

