package com.calculator.tenpo.aplication.port.out;


import com.calculator.tenpo.aplication.model.dto.ApiCallHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApiCallHistoryPort {

	void save(ApiCallHistoryDto apiCallHistory);

	Page<ApiCallHistoryDto> getAllHistory(Pageable pageable);

}
