package com.calculator.tenpo.infrastructure.input.controller;


import com.calculator.tenpo.aplication.port.input.CalculationPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/calculate")
@RequiredArgsConstructor
public class CalculationController {

	private final CalculationPort calculationPort;

	@GetMapping
	public Mono<ResponseEntity<BigDecimal>> calculate(@RequestParam @NonNull BigDecimal num1, @RequestParam @NonNull BigDecimal num2) {
		return calculationPort.calculateWithPercentage(num1, num2)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}
}

