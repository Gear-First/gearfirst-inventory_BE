package com.gearfirst.backend.api.inventory.entity;

import com.gearfirst.backend.api.inventory.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 부품 자체의 유효한 상태를 유지하고, 부품 정보 변경 시 스스로 검증하는 역할
 */
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

    @Column(name = "price", nullable = false)
    private int price;

    private String category;
    private String descriptions;
    private String manufacturer;

    @Column(nullable = false)
    private boolean active = true;  //기본값

    // === 생성자 + 빌더 ===
    @Builder
    public Inventory(String inventoryName, String inventoryCode, int price) {
        if (price <= 0) throw new IllegalArgumentException("가격은 0보다 커야 합니다.");
        this.inventoryName = inventoryName;
        this.inventoryCode = inventoryCode;
        this.price = price;
    }

    // === 도메인 메서드 ===
    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }
    public void updateName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("부품명은 비어 있을 수 없습니다.");
        }
        this.inventoryName = newName;
    }

    public void updatePrice(int newPrice) {
        if (newPrice <= 0) {
            throw new IllegalArgumentException("가격은 0보다 커야 합니다.");
        }
        this.price = newPrice;
    }
    /**
     * TODO: inventory code 형식이 회사 규칙에 맞는지 검증
     */
//    public void validateCodeFormat() {
//        if (!this.inventoryCode.matches("^[A-Z]{2}-\\d{3}$")) {
//            throw new IllegalArgumentException("부품코드는 예: 'BP-001' 형식이어야 합니다.");
//        }
//    }

}

