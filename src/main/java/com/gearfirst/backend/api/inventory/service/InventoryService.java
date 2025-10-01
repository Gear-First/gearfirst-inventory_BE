package com.gearfirst.backend.api.inventory.service;

import com.gearfirst.backend.api.inventory.entity.Inventory;

import java.time.LocalDate;
import java.util.List;

public interface InventoryService {
    List<Inventory> getInventories(LocalDate startDate, LocalDate endDate);
    Inventory getInventory(Long id);
}
