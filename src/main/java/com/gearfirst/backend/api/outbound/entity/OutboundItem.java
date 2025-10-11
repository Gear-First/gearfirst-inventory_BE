package com.gearfirst.backend.api.outbound.entity;

import com.gearfirst.backend.api.inventory.entity.Inventory;
import com.gearfirst.backend.api.outbound.enums.OutboundItemStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "outbound_item")
public class OutboundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "outbound_item_id")
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutboundItemStatus outboundItemStatus = OutboundItemStatus.READY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="inventory_id")
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="outbound_order_id")
    private OutboundOrder outboundOrder;

    //생성자+빌더
    @Builder
    public OutboundItem(OutboundOrder outboundOrder, Inventory inventory, int quantity) {
        validate(quantity, inventory, outboundOrder);
        this.outboundOrder = outboundOrder;
        this.inventory = inventory;
        this.quantity = quantity;
        this.outboundItemStatus = OutboundItemStatus.READY;
    }


    //출고 검증
    private void validate(int quantity, Inventory inventory, OutboundOrder outboundOrder){
        if (quantity <= 0) throw new IllegalArgumentException("출고 수량은 0보다 커야 합니다.");
        if (inventory == null) throw new IllegalArgumentException("출고 품목(부품) 정보가 필요합니다.");
        if (outboundOrder == null) throw new IllegalArgumentException("출고 명령 정보가 필요합니다.");
    }

    //상태 전이
    public void startPicking() {
        if (this.outboundItemStatus != OutboundItemStatus.READY)
            throw new IllegalStateException("출고 준비 상태에서만 포장을 시작할 수 있습니다.");
        this.outboundItemStatus = OutboundItemStatus.PACKING;
    }

    public void ship() {
        if (this.outboundItemStatus != OutboundItemStatus.PACKING)
            throw new IllegalStateException("포장 완료 후에만 출고할 수 있습니다.");
        this.outboundItemStatus = OutboundItemStatus.SHIPPED;
    }

    public void cancel() {
        if (this.outboundItemStatus == OutboundItemStatus.SHIPPED)
            throw new IllegalStateException("이미 출고된 품목은 취소할 수 없습니다.");
        this.outboundItemStatus = OutboundItemStatus.CANCELED;
    }

    // 출고 완료 여부 판단 - 출고 완료된 품목만 재고 차감하도록 하기 위해
    public boolean isShipped() {
        return this.outboundItemStatus == OutboundItemStatus.SHIPPED;
    }

    //재고 차감시 사용할 수 있도록 정보 제공
    public Inventory getInventoryForUpdate() {
        return this.inventory;
    }

    public int getOutboundQuantity() {
        return this.quantity;
    }

}

