package com.gearfirst.backend.api.inventory.service;

import com.gearfirst.backend.api.inventory.domain.entity.Inventory;
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
    public List<Inventory> getAllInventories(){
        return inventoryRepository.findAll();
    }

    @Override
    public List<Inventory> getInventoriesByDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이후일 수 없습니다.");
        }

        return inventoryRepository.findByInboundDateBetween(
                startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX)
        );
    }

    @Override
    public Inventory getInventory(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_INVENTORY_EXCEPTION.getMessage()));

    }
}
