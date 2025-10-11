package com.gearfirst.backend.api.outbound.entity;

import com.gearfirst.backend.api.outbound.enums.OutboundOrderStatus;
import com.gearfirst.backend.api.warehouse.entity.Warehouse;
import com.gearfirst.backend.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 어떤 창고에서, 어떤 발주에 대한 출고가 언제 어떻게 완료되는지 통제하고 기록하는 주체
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "outbound_order")
public class OutboundOrder extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "outbound_order_id")
    private Long id;

    @Column(name="purchase_order_id", nullable = false)
    private Long purchaseOrderId;               //발주 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(nullable = false)
    private OutboundOrderStatus outboundOrderStatus = OutboundOrderStatus.ORDERED;

    @Column(name ="total_quantity",nullable = false)
    private int totalQuantity;                  //총 출고 금액

    @Column(name ="shipped_date")
    private LocalDateTime completedDate;          //완료일

    //생성자 + 빌더
    @Builder
    public OutboundOrder(Long purchaseOrderId, Warehouse warehouse){
        if (purchaseOrderId == null) throw new IllegalArgumentException("발주 ID가 필요합니다.");
        if (warehouse == null) throw new IllegalArgumentException("창고 정보가 필요합니다.");
        this.purchaseOrderId = purchaseOrderId;
        this.warehouse = warehouse;
        this.outboundOrderStatus = OutboundOrderStatus.ORDERED;
    }

    //출고 상태 관리
    public void startPreparing(){
        if(this.outboundOrderStatus != OutboundOrderStatus.ORDERED) throw new IllegalArgumentException("출고 준비는 지시 상태에서만 가능합니다.");
        this.outboundOrderStatus = OutboundOrderStatus.PACKING;
    }
    public void ship(){
        if(this.outboundOrderStatus != OutboundOrderStatus.PACKING)
            throw new IllegalArgumentException("출고 완료는 준비 중 상태에서만 가능합니다.");
        this.outboundOrderStatus = OutboundOrderStatus.SHIPPED;
    }

    public void complete() {
        if (this.outboundOrderStatus != OutboundOrderStatus.SHIPPED)
            throw new IllegalStateException("배송 완료는 출고 완료 상태에서만 가능합니다.");
        this.outboundOrderStatus = OutboundOrderStatus.COMPLETED;
        this.completedDate = LocalDateTime.now();
    }

    public void cancel() {
        if (this.outboundOrderStatus == OutboundOrderStatus.COMPLETED)
            throw new IllegalStateException("이미 완료된 출고는 취소할 수 없습니다.");
        this.outboundOrderStatus = OutboundOrderStatus.CANCELED;
    }

    //출고 총 수량 계산
    public void calculateTotalQuantity(List<OutboundItem> items){
        if (items == null || items.isEmpty())
            throw new IllegalArgumentException("출고 품목이 존재하지 않습니다.");
        this.totalQuantity = items.stream()
                .mapToInt(OutboundItem::getQuantity)
                .sum();
    }

    //출고 완효 후 후속 처리 이벤트 트리거
    public boolean isCompleted(){
        return this.outboundOrderStatus == OutboundOrderStatus.COMPLETED;
    }
}

