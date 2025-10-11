package com.gearfirst.backend.api.outbound.enums;

public enum OutboundItemStatus {
    READY("준비중"),
    PACKING("포장중"),
    SHIPPED("출고 완료"),
    CANCELED("취소");

    private final String description;

    OutboundItemStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
