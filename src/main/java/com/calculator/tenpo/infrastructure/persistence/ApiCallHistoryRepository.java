package com.calculator.tenpo.infrastructure.persistence;


import com.calculator.tenpo.domain.model.domain.ApiCallHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiCallHistoryRepository extends JpaRepository<ApiCallHistory, Long> {
}
