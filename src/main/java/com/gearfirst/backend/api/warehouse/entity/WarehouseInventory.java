package com.gearfirst.backend.api.warehouse.entity;

import com.gearfirst.backend.api.inventory.entity.Inventory;
import com.gearfirst.backend.api.inventory.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 특정 창고에 존재하는 특정 부품의 수량과 상태를 관리하는 주체
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "warehouse_inventory")
public class WarehouseInventory {

    private static final int LOW_STOCK_THRESHOLD = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "warehouse_inventory_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="inventory_id", nullable = false)
    private Inventory inventory;

    @Column(name = "current_stock", nullable = false)
    private int currentStock = 0;   // DB 기본값과 맞추기 위해 초기화


    @Column(name="available_stock", nullable = false)
    private int availableStock = 0;                  //가용 재고

    @Enumerated(EnumType.STRING)
    @Column(name="inventory_status", nullable = false)
    private InventoryStatus inventoryStatus = InventoryStatus.STABLE;     //재고 상태

    //창고에 부품 재고를 등록한다.
    public static WarehouseInventory create(Warehouse warehouse, Inventory inventory) {
        if (warehouse == null) throw new IllegalArgumentException("창고 정보가 필요합니다.");
        if (inventory == null) throw new IllegalArgumentException("부품 정보가 필요합니다.");

        WarehouseInventory wi = new WarehouseInventory();
        wi.warehouse = warehouse;
        wi.inventory = inventory;
        wi.currentStock = 0;
        wi.availableStock = 0;
        wi.inventoryStatus = InventoryStatus.STABLE;
        return wi;
    }


    // === 도메인 메서드 ===
    public void increaseStock(int quantity) {
        validateQuantity(quantity);
        this.currentStock += quantity;
        this.availableStock += quantity;
        updateStatus();
    }

    public void decreaseStock(int quantity) {
        validateQuantity(quantity);
        if (this.availableStock < quantity) throw new IllegalStateException("가용 재고 부족");
        this.currentStock -= quantity;
        this.availableStock -= quantity;
        updateStatus();

        // TODO: 향후 이벤트 시스템 도입 시
        // if (this.isLowStock()) publish(new LowStockEvent(this.inventory.getId()));
    }
    private void validateQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("수량은 0보다 커야 합니다.");
    }
    //재고 상태 업데이트
    public void updateStatus(){
        if(this.currentStock < LOW_STOCK_THRESHOLD){
            this.inventoryStatus = InventoryStatus.NEED_RESTOCK;
        }else{
            this.inventoryStatus = InventoryStatus.STABLE;
        }
    }
    //재고 부족 여부 판단
    public boolean isLowStock() {
        return this.inventoryStatus == InventoryStatus.NEED_RESTOCK;
    }
}
