package com.calculator.tenpo.infrastructure.controller.databuilder;

import com.calculator.tenpo.infrastructure.model.entity.ApiCallHistoryEntity;

import java.time.LocalDateTime;

public class ApiCallHistoryTestDataBuilder {
	private Long id = 1L;
	private String response = "Success";
	private LocalDateTime timestamp = LocalDateTime.now();
	private String endpoint = "/api/test";
	private String requestParams = "param1=value1&param2=value2";
	private String status = "200 OK";

	public ApiCallHistoryEntity build() {
		ApiCallHistoryEntity history = new ApiCallHistoryEntity();
		history.setId(id);
		history.setResponse(response);
		history.setTimestamp(timestamp);
		history.setEndpoint(endpoint);
		history.setRequestParams(requestParams);
		history.setStatus(status);
		return history;
	}
}