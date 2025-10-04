package com.gearfirst.backend.api.inventory.domain.entity;

import com.gearfirst.backend.api.inventory.domain.enums.InventoryStatus;
import com.gearfirst.backend.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inventory")
public class Inventory{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "invnetory_id")
    private Long id;

    @Column(name = "inventory_name", nullable = false, length = 100)
    private String inventoryName;

    @Column(name = "inventory_code", nullable = false, unique = true, length = 50)
    private String inventoryCode;

    @Column(name = "current_stock", nullable = false)
    private int currentStock = 0;   // DB 기본값과 맞추기 위해 초기화

    @Column(name = "available_stock", nullable = false)
    private int availableStock = 0;

    @Column(name = "warehouse", length = 100)
    private String warehouse;

    @Column(name = "inbound_date", updatable = false, nullable = false)
    private LocalDateTime inboundDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "inventory_status", nullable = false)
    private InventoryStatus inventoryStatus = InventoryStatus.STABLE;

    @PrePersist
    protected void onCreate() {
        if (this.inboundDate == null) {
            this.inboundDate = LocalDateTime.now();
        }
    }
    // === 생성자 + 빌더 ===
    @Builder
    public Inventory(String inventoryName,
                     String inventoryCode,
                     int currentStock,
                     int availableStock,
                     String warehouse,
                     InventoryStatus inventoryStatus) {
        this.inventoryName = inventoryName;
        this.inventoryCode = inventoryCode;
        this.currentStock = currentStock;
        this.availableStock = availableStock;
        this.warehouse = warehouse;
        this.inventoryStatus = inventoryStatus != null ? inventoryStatus : InventoryStatus.STABLE;
    }

    // === 도메인 메서드 ===
    public void increaseStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("입고 수량은 0보다 커야 합니다.");
        this.currentStock += quantity;
        this.availableStock += quantity;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("출고 수량은 0보다 커야 합니다.");
        if (this.availableStock < quantity) throw new IllegalStateException("가용 재고 부족");
        this.currentStock -= quantity;
        this.availableStock -= quantity;
    }

}

