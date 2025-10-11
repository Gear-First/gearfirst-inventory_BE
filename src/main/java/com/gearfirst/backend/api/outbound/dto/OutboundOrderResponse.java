package com.gearfirst.backend.api.outbound.dto;

import com.gearfirst.backend.api.outbound.enums.OutboundOrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OutboundOrderResponse {
    private Long outboundOrderId;
    private Long purchaseOrderId;
    private Long warehouseId;
    private OutboundOrderStatus status;
    private LocalDateTime createdAt;

    public static OutboundOrderResponse fromEntity(com.gearfirst.backend.api.outbound.entity.OutboundOrder order) {
        return OutboundOrderResponse.builder()
                .outboundOrderId(order.getId())
                .purchaseOrderId(order.getPurchaseOrderId())
                .warehouseId(order.getWarehouse().getId())
                .status(order.getOutboundOrderStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
