package com.gearfirst.backend.api.warehouse.controller.internal.dto;

import com.gearfirst.backend.api.warehouse.entity.WarehouseInventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDto {
    private Long warehouseInventoryId;   // 창고별 재고 ID
    private Long inventoryId;            // 부품 ID
    private String inventoryName;
    private String inventoryCode;
    private int price;
    private int currentStock;
    private int availableStock;
    private String status;
    private String warehouseName;

    public static InventoryResponseDto from(WarehouseInventory entity) {
        return InventoryResponseDto.builder()
                .warehouseInventoryId(entity.getId())
                .inventoryId(entity.getInventory().getId())
                .inventoryName(entity.getInventory().getInventoryName())
                .inventoryCode(entity.getInventory().getInventoryCode())
                .price(entity.getInventory().getPrice())
                .currentStock(entity.getCurrentStock())
                .availableStock(entity.getAvailableStock())
                .status(entity.getInventoryStatus().name())
                .warehouseName(entity.getWarehouse().getName())
                .build();
    }
}
