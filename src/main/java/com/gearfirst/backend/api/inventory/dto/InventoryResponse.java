package com.gearfirst.backend.api.inventory.dto;

import com.gearfirst.backend.api.inventory.entity.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InventoryResponse {
    private Long inventoryId;
    private String inventoryName;
    private String inventoryCode;
    private int price;

    public static InventoryResponse fromEntity(Inventory inventory) {
        return new InventoryResponse(
                inventory.getId(),
                inventory.getInventoryName(),
                inventory.getInventoryCode(),
                inventory.getPrice()
        );
    }
}
