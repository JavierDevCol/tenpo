package com.calculator.tenpo.aplication.service;

import com.calculator.tenpo.domain.model.PercentageCalculation;

import java.math.BigDecimal;

public class PercentageCalculationTestDataBuilder {
	private BigDecimal num1 = BigDecimal.valueOf(100);
	private BigDecimal num2 = BigDecimal.valueOf(50);
	private BigDecimal percentage = BigDecimal.valueOf(10);

	public PercentageCalculationTestDataBuilder withNum1(BigDecimal num1) {
		this.num1 = num1;
		return this;
	}

	public PercentageCalculationTestDataBuilder withNum2(BigDecimal num2) {
		this.num2 = num2;
		return this;
	}

	public PercentageCalculationTestDataBuilder withPercentage(BigDecimal percentage) {
		this.percentage = percentage;
		return this;
	}

	public PercentageCalculation build() {
		return new PercentageCalculation(num1, num2, percentage);
	}
}