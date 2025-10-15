package com.gearfirst.backend.api.warehouse.controller.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboundRequest {

    private Long purchaseOrderId;   // 발주 ID
    private Long warehouseId;       // 출고 창고 ID
    private List<Item> items;       // 출고할 부품 리스트

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Item {
        private Long warehouseInventoryId;  // 창고 재고 ID
        private int quantity;
    }
}
