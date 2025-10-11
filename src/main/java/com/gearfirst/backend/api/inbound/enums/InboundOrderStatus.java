package com.gearfirst.backend.api.inbound.enums;

public enum InboundOrderStatus {
    REQUESTED("요청 됨"),
    IN_PROGRESS("진행 중"),
    COMPLETED("입고 완료"),
    CANCELED("취소 됨");

    private final String description;

    InboundOrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
