package com.gearfirst.backend.api.warehouse.dto;

import com.gearfirst.backend.api.warehouse.entity.WarehouseInventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InventoryInternalResponse {

    private Long id;                // warehouse_inventory_id
    private String inventoryName;
    private String inventoryCode;
    private int availableStock;
    private String warehouse;
    private String status;
    private int price;

    public static InventoryInternalResponse fromEntity(WarehouseInventory wi) {
        return InventoryInternalResponse.builder()
                .id(wi.getId())
                .inventoryName(wi.getInventory().getInventoryName())
                .inventoryCode(wi.getInventory().getInventoryCode())
                .availableStock(wi.getAvailableStock())
                .warehouse(wi.getWarehouse().getName())
                .status(wi.getInventoryStatus().name())
                .price(wi.getInventory().getPrice())
                .build();
    }
}
