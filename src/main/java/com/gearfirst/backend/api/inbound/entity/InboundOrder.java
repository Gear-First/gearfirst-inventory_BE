package com.gearfirst.backend.api.inbound.entity;

import com.gearfirst.backend.api.inbound.enums.InboundOrderStatus;
import com.gearfirst.backend.api.warehouse.entity.Warehouse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 공급처로부터 특정 창고에 부품이 들어오는 입고 명령
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inbound_order")
public class InboundOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "ibound_order_id")
    private Long id;

    @Column(name="total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name="inbound_date", nullable = false)
    private LocalDateTime inboundDate;          //입고 일자

    @Column(name="supplier_name", nullable = false)
    private String supplierName;                 //공급처

    @Column(name="inbound_name", nullable = false)
    private String inboundName;                 //입고

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InboundOrderStatus status;          //입고 상태

    @Column(name = "total_amount")
    private int totalAmount;                    //총금액

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    // === 생성자 ===
    public InboundOrder(Warehouse warehouse, String supplierName, String inboundName) {
        if (warehouse == null) throw new IllegalArgumentException("창고 정보가 필요합니다.");
        if (supplierName == null || supplierName.isBlank()) throw new IllegalArgumentException("공급처는 필수입니다.");
        if (inboundName == null || inboundName.isBlank()) throw new IllegalArgumentException("입고명은 필수입니다.");

        this.warehouse = warehouse;
        this.supplierName = supplierName;
        this.inboundName = inboundName;
        this.status = InboundOrderStatus.REQUESTED;
    }

    public void start(){
        if(this.status != InboundOrderStatus.REQUESTED)
            throw new IllegalArgumentException("입고 요청 상태가 아닙니다.");
        this.status = InboundOrderStatus.IN_PROGRESS;
    }

    public void complete() {
        if (this.status != InboundOrderStatus.IN_PROGRESS)
            throw new IllegalStateException("입고 진행 중 상태에서만 완료할 수 있습니다.");
        this.status = InboundOrderStatus.COMPLETED;
        this.inboundDate = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status == InboundOrderStatus.COMPLETED)
            throw new IllegalStateException("이미 완료된 입고는 취소할 수 없습니다.");
        this.status = InboundOrderStatus.CANCELED;
    }

    public void calculateTotals(List<InboundItem> items) {
        this.totalQuantity = items.stream().mapToInt(InboundItem::getQuantity).sum();
        this.totalAmount = items.stream().mapToInt(i -> i.getPrice() * i.getQuantity()).sum();
    }


}
