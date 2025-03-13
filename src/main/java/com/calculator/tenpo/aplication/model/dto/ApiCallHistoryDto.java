package com.calculator.tenpo.aplication.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiCallHistoryDto {
		private Long id;
		private String response;
		private LocalDateTime timestamp;
		private String endpoint;
		private String requestParams;
		private String status;
}
