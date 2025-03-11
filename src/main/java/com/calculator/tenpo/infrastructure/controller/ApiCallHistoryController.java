package com.calculator.tenpo.infrastructure.controller;

import com.calculator.tenpo.domain.model.domain.ApiCallHistory;
import com.calculator.tenpo.infrastructure.service.ApiCallHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class ApiCallHistoryController {

	private final ApiCallHistoryService service;

	@GetMapping
	public ResponseEntity<Page<ApiCallHistory>> getCallHistory(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ApiCallHistory> history = service.getAllHistory(pageable);
		return ResponseEntity.ok(history);
	}
}
