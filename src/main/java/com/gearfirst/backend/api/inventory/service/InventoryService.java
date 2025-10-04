package com.gearfirst.backend.api.inventory.service;

import com.gearfirst.backend.api.inventory.domain.entity.Inventory;

import java.time.LocalDate;
import java.util.List;

public interface InventoryService {
    List<Inventory> getAllInventories();
    List<Inventory> getInventoriesByDate(LocalDate startDate, LocalDate endDate);
    Inventory getInventory(Long id);
}
