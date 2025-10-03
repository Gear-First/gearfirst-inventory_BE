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
    public List<Inventory> getInventories(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return inventoryRepository.findByInboundDateBetween(
                    startDate.atStartOfDay(),
                    endDate.atTime(LocalTime.MAX)
            );
        }
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory getInventory(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_INVENTORY_EXCEPTION.getMessage()));

    }
}
