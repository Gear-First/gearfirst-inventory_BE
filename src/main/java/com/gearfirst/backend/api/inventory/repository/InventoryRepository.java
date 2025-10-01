package com.gearfirst.backend.api.inventory.repository;

import com.gearfirst.backend.api.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // 날짜 범위 필터 조회
    List<Inventory> findByInboundDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
