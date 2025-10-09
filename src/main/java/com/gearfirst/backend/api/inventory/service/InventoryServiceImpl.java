package com.gearfirst.backend.api.inventory.service;

import com.gearfirst.backend.api.inventory.dto.InventoryResponse;
import com.gearfirst.backend.api.inventory.entity.Inventory;
import com.gearfirst.backend.api.inventory.repository.InventoryRepository;
import com.gearfirst.backend.common.exception.NotFoundException;
import com.gearfirst.backend.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public List<InventoryResponse> getAllInventories(){
        return inventoryRepository.findAll().stream()
                .map(InventoryResponse::fromEntity)
                .toList();
    }

    @Override
    public List<InventoryResponse> getInventoriesByDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이후일 수 없습니다.");
        }

        List<Inventory> inventores = inventoryRepository.findByInboundDateBetween(
                startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX)
        );
        return inventores.stream().map(InventoryResponse::fromEntity)
                .toList();
    }

    @Override
    public InventoryResponse getInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_INVENTORY_EXCEPTION.getMessage()));
        return InventoryResponse.fromEntity(inventory);
    }

    @Override
    public List<InventoryResponse> getInventoryByName(String inventoryName) {
        return inventoryRepository.findByInventoryName(inventoryName).stream()
                .map(InventoryResponse::fromEntity)
                .toList();
    }

}
