package com.calculator.tenpo.infrastructure.output.adapter;

import com.calculator.tenpo.aplication.model.dto.ApiCallHistoryDto;
import com.calculator.tenpo.aplication.port.out.ApiCallHistoryPort;
import com.calculator.tenpo.infrastructure.model.mapper.ApiCallHistoryMapper;
import com.calculator.tenpo.infrastructure.model.entity.ApiCallHistoryEntity;
import com.calculator.tenpo.infrastructure.output.repository.ApiCallHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApiCallHistoryAdapter implements ApiCallHistoryPort{

	private final ApiCallHistoryRepository apiCallHistoryRepository;
	private final ApiCallHistoryMapper apiCallHistoryMapper;

	@Override
	public void save(ApiCallHistoryDto apiCallHistory) {
		apiCallHistoryRepository.save(ApiCallHistoryEntity.builder()
				.timestamp(LocalDateTime.now())
				.endpoint(apiCallHistory.getEndpoint())
				.requestParams(apiCallHistory.getRequestParams())
				.response(apiCallHistory.getResponse())
				.status(apiCallHistory.getStatus())
				.build());
	}

	public Page<ApiCallHistoryDto> getAllHistory(Pageable pageable) {
		return apiCallHistoryRepository.findAll(pageable)
				.map(apiCallHistoryMapper::toDto);
	}
}

