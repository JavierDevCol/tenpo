package com.calculator.tenpo.infrastructure.output.repository;


import com.calculator.tenpo.infrastructure.model.entity.ApiCallHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiCallHistoryRepository extends JpaRepository<ApiCallHistoryEntity, Long> {
}
