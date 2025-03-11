package com.calculator.tenpo.infrastructure.service;


import com.calculator.tenpo.domain.model.domain.ApiCallHistory;
import com.calculator.tenpo.infrastructure.persistence.ApiCallHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApiCallHistoryService {

	private final ApiCallHistoryRepository repository;

	@Transactional
	public void saveCallHistory(String endpoint, String requestParams, String response, String status) {
		ApiCallHistory history = ApiCallHistory.builder()
				.timestamp(LocalDateTime.now())
				.endpoint(endpoint)
				.requestParams(requestParams)
				.response(response)
				.status(status)
				.build();
		repository.save(history);
	}

	public Page<ApiCallHistory> getAllHistory(Pageable pageable) {
		return repository.findAll(pageable);
	}
}