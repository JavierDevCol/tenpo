package com.calculator.tenpo.infrastructure.input.controller;

import com.calculator.tenpo.aplication.model.dto.ApiCallHistoryDto;
import com.calculator.tenpo.aplication.port.out.ApiCallHistoryPort;
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

	private final ApiCallHistoryPort apiCallHistoryPort;

	@GetMapping
	public ResponseEntity<Page<ApiCallHistoryDto>> getCallHistory(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<ApiCallHistoryDto> history = apiCallHistoryPort.getAllHistory(pageable);
		return ResponseEntity.ok(history);
	}
}
