package com.gearfirst.backend.api.outbound.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OutboundOrderCreateRequest {
    private Long purchaseOrderId;
    private Long warehouseId;
    private List<Item> items;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private Long inventoryId;
        private int quantity;
    }
}
