package com.calculator.tenpo.infrastructure.controller;


import com.calculator.tenpo.infrastructure.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/calculate")
@RequiredArgsConstructor
public class CalculationController {

	private final CalculationService calculationService;

	@GetMapping
	public Mono<ResponseEntity<BigDecimal>> calculate(@RequestParam BigDecimal num1, @RequestParam BigDecimal num2) {
		return calculationService.calculateWithPercentage(num1, num2)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}
}

