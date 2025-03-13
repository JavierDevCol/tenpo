package com.calculator.tenpo.infrastructure.output.adapter;

import com.calculator.tenpo.aplication.port.out.PercentagePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PercentageAdapter implements PercentagePort {

	private final CacheManager cacheManager;

	@Cacheable(value = "percentageCache", key = "'percentage'", unless = "#result == null")
	public Mono<BigDecimal> getPercentage() {
		log.info("Fetching percentage from external service...");
		return getBigDecimalMono();
	}

	private Mono<BigDecimal> getBigDecimalMono() {
		return Mono.just(getPercentageRandom())
				.delayElement(Duration.ofSeconds(1))
				.retry(3)
				.onErrorResume(error -> {
					log.warn("Service failed after retries, checking cache...");
					return getPercentageCache();
				});
	}

	private Mono<BigDecimal> getPercentageCache() {
		BigDecimal cachedPercentage = Optional.ofNullable(cacheManager.getCache("percentageCache"))
		        .map(cache -> cache.get("percentage", BigDecimal.class))
		        .orElse(null);

		if (Objects.nonNull(cachedPercentage)) {
			log.info("Returning cached percentage: {}", cachedPercentage);
			return Mono.just(cachedPercentage);
		}

		log.error("No cached value found, returning default value");
		return Mono.just(new BigDecimal("10"));
	}

	private static BigDecimal getPercentageRandom() {
		return BigDecimal.valueOf(Math.random() * 90 + 10).setScale(2, RoundingMode.HALF_UP);
	}
}
