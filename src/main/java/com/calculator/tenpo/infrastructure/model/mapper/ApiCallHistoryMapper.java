package com.calculator.tenpo.infrastructure.model.mapper;

import com.calculator.tenpo.aplication.model.dto.ApiCallHistoryDto;
import com.calculator.tenpo.infrastructure.model.entity.ApiCallHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApiCallHistoryMapper {

	ApiCallHistoryEntity toEntity(ApiCallHistoryDto domain);

	ApiCallHistoryDto toDto(ApiCallHistoryEntity entity);
}
