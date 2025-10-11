package com.gearfirst.backend.api.outbound.enums;

public enum OutboundOrderStatus {
    ORDERED("지시됨"),
    PACKING("준비중"),
    SHIPPED("출고 완료(배송 출발)"),
    COMPLETED("완료"),
    CANCELED("취소");

    private final String description;

    OutboundOrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
