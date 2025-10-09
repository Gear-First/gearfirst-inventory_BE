package com.gearfirst.backend.api.inventory.service;

import com.gearfirst.backend.api.inventory.dto.InventoryResponse;
import com.gearfirst.backend.api.inventory.entity.Inventory;

import java.time.LocalDate;
import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getAllInventories();
    List<InventoryResponse> getInventoriesByDate(LocalDate startDate, LocalDate endDate);
    InventoryResponse getInventory(Long id);
    List<InventoryResponse> getInventoryByName(String inventoryName);
}
