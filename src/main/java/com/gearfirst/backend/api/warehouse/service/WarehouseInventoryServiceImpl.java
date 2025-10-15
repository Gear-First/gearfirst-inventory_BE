package com.gearfirst.backend.api.warehouse.service;

import com.gearfirst.backend.api.inventory.dto.InventoryResponse;
import com.gearfirst.backend.api.warehouse.controller.internal.dto.InventoryResponseDto;
import com.gearfirst.backend.api.warehouse.dto.InventoryInternalResponse;
import com.gearfirst.backend.api.warehouse.dto.WarehouseInventoryResponse;
import com.gearfirst.backend.api.warehouse.entity.WarehouseInventory;
import com.gearfirst.backend.api.warehouse.repository.WarehouseInventoryRepository;
import com.gearfirst.backend.common.exception.NotFoundException;
import com.gearfirst.backend.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseInventoryServiceImpl implements WarehouseInventoryService{

    private final WarehouseInventoryRepository warehouseInventoryRepository;

    @Override
    // 전체 조회
    public List<WarehouseInventoryResponse> getAllInventories() {
        return warehouseInventoryRepository.findAllWithRelations()
                .stream()
                .map(WarehouseInventoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseInventoryResponse getInventory(Long inventoryId,Long warehouseId) {
        WarehouseInventory wi = warehouseInventoryRepository
                .findByInventoryIdAndWarehouseId(inventoryId, warehouseId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_INVENTORY_EXCEPTION.getMessage()));

        return WarehouseInventoryResponse.fromEntity(wi);
    }

    @Override
    // 이름 검색
    public List<WarehouseInventoryResponse> getByInventoryName(String name) {
        return warehouseInventoryRepository.findByInventoryName(name)
                .stream()
                .map(WarehouseInventoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    // 날짜 검색
    public List<WarehouseInventoryResponse> getInventoriesByDate(LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59);

        return warehouseInventoryRepository.findByInboundDateBetween(startDateTime, endDateTime)
                .stream()
                .map(WarehouseInventoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 내부 서비스용 응답 DTO 반환
    @Override
    public InventoryInternalResponse getInventoryInternalResponse(Long id) {
        WarehouseInventory wi = warehouseInventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_INVENTORY_EXCEPTION.getMessage()));

        return InventoryInternalResponse.fromEntity(wi);
    }

    //차량 모델 + 키워드 기반 부품 검색
    @Override
    public List<InventoryResponseDto> getInventoriesByCarModel(Long carModelId, String keyword) {
        List<WarehouseInventory> list =
                warehouseInventoryRepository.findByCarModelAndKeyword(carModelId, keyword);

        return list.stream()
                .map(InventoryResponseDto::from)
                .toList();
    }


}
