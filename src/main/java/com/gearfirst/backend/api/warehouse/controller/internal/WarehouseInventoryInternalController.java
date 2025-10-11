package com.gearfirst.backend.api.warehouse.controller.internal;

import com.gearfirst.backend.api.warehouse.dto.InventoryInternalResponse;
import com.gearfirst.backend.api.warehouse.service.WarehouseInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/warehouse-inventories")
public class WarehouseInventoryInternalController {
    private final WarehouseInventoryService warehouseInventoryService;

    @GetMapping("/{id}")
    public InventoryInternalResponse getInventoryById(@PathVariable Long id) {
        return warehouseInventoryService.getInventoryInternalResponse(id);
    }
}
