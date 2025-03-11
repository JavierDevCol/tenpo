package com.calculator.tenpo.infrastructure.external;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Service
@Slf4j
public class PercentageService {

	@Cacheable(value = "percentageCache", key = "'percentage'", unless = "#result == null")
	public Mono<BigDecimal> getPercentage() {
		log.info("Fetching percentage from external service...");
		return Mono.just(BigDecimal.valueOf(Math.random() * 90 + 10).setScale(2, RoundingMode.HALF_UP))
				.delayElement(Duration.ofSeconds(1))
				.retry(3);
	}
}
