package com.calculator.tenpo.infrastructure.controller;

import com.calculator.tenpo.infrastructure.controller.databuilder.ApiCallHistoryTestDataBuilder;
import com.calculator.tenpo.infrastructure.model.entity.ApiCallHistoryEntity;
import com.calculator.tenpo.infrastructure.output.repository.ApiCallHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class ApiCallHistoryEntityControllerIntegrationTest {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private ApiCallHistoryRepository apiCallHistoryRepository;

	private ApiCallHistoryTestDataBuilder apiCallHistoryTestDataBuilder;
	private ApiCallHistoryEntity history;

	@BeforeEach
	void setUp() {
		apiCallHistoryRepository.deleteAll(); // Limpia la base de datos antes de cada prueba
		apiCallHistoryTestDataBuilder = new ApiCallHistoryTestDataBuilder();

		// Insertar datos de prueba
		history = apiCallHistoryTestDataBuilder.build();
		apiCallHistoryRepository.save(history); // Guarda los datos en la base de datos
	}

	@Test
	void getCallHistory_successfulRetrieval()  {
		// Arrange

		// Act & Assert
		webTestClient.get()
				.uri("/api/history")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.content[0].id").isEqualTo(history.getId())
				.jsonPath("$.content[0].response").isEqualTo(history.getResponse())
				.jsonPath("$.content[0].endpoint").isEqualTo(history.getEndpoint())
				.jsonPath("$.content[0].requestParams").isEqualTo(history.getRequestParams())
				.jsonPath("$.content[0].status").isEqualTo(history.getStatus());
	}

	@Test
	void getCallHistory_withEmptyRepository_returnsEmptyPage() {
		// Arrange
		apiCallHistoryRepository.deleteAll();

		// Act & Assert
		webTestClient.get()
				.uri("/api/history")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.content.length()").isEqualTo(0)
				.jsonPath("$.totalElements").isEqualTo(0);
	}

	@Test
	void getCallHistory_withLargePageSize_returnsAllRecords() {
		// Count total records
		long totalRecords = apiCallHistoryRepository.count();

		// Request with large page size
		webTestClient.get()
				.uri("/api/history?size=100")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.totalElements").isEqualTo(totalRecords);
	}
}
