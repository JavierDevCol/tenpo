package com.calculator.tenpo.domain.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class PercentageCalculation {

	private final BigDecimal num1;
	private final BigDecimal num2;
	private final BigDecimal percentage;

	public PercentageCalculation(BigDecimal num1, BigDecimal num2, BigDecimal percentage) {
		this.num1 = num1;
		this.num2 = num2;
		this.percentage = percentage;
	}

	public BigDecimal calculate() {
		return num1.add(num2).multiply(BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100))));
	}
}
