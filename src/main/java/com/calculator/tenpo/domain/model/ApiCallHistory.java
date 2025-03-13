package com.calculator.tenpo.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ApiCallHistory {
	private Long id;
	private LocalDateTime timestamp;
	private String endpoint;
	private String requestParams;
	private String response;
	private String status;
}

