package com.gearfirst.backend.api.warehouse.dto;

import com.gearfirst.backend.api.warehouse.entity.WarehouseInventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WarehouseInventoryResponse {
    private Long warehouseInventoryId;
    private String warehouseName;
    private String inventoryName;
    private String inventoryCode;
    private int currentStock;
    private int availableStock;
    private String inventoryStatus;


    public static WarehouseInventoryResponse fromEntity(WarehouseInventory wi) {
        return WarehouseInventoryResponse.builder()
                .warehouseInventoryId(wi.getId())
                .warehouseName(wi.getWarehouse().getName())
                .inventoryName(wi.getInventory().getInventoryName())
                .inventoryCode(wi.getInventory().getInventoryCode())
                .currentStock(wi.getCurrentStock())
                .availableStock(wi.getAvailableStock())
                .inventoryStatus(wi.getInventoryStatus().name())
                .build();
    }
}
