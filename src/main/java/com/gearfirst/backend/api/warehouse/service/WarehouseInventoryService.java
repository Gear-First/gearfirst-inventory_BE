package com.gearfirst.backend.api.warehouse.service;

import com.gearfirst.backend.api.warehouse.dto.InventoryInternalResponse;
import com.gearfirst.backend.api.warehouse.dto.WarehouseInventoryResponse;

import java.time.LocalDate;
import java.util.List;

public interface WarehouseInventoryService {
    // 전체 조회
    public List<WarehouseInventoryResponse> getAllInventories();
    // 단건 조회
    public WarehouseInventoryResponse getInventory(Long inventoryId,Long warehouseId);
    // 이름 검색
    public List<WarehouseInventoryResponse> getByInventoryName(String name);
    // 날짜 검색
    public List<WarehouseInventoryResponse> getInventoriesByDate(LocalDate start, LocalDate end);
    // 내부 서비스용 응답 DTO 반환
    InventoryInternalResponse getInventoryInternalResponse(Long id);
}
