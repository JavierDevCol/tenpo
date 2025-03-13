package com.calculator.tenpo.aplication.port.out;

import reactor.core.publisher.Mono;
import java.math.BigDecimal;

public interface PercentagePort {
	Mono<BigDecimal> getPercentage();
}