package com.gearfirst.backend.api.warehouse.entity;

import com.gearfirst.backend.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 창고의 기본정보 변경, 운영상태 관리, 창고별 재고 관리 주체
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "warehouse")
public class Warehouse extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "warehouse_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(name="manager_name",nullable = false)
    private String managerName;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private boolean active = true;

    // === 빌더 + 생성자 ===
    @Builder
    public Warehouse(String name, String location, String managerName, String contact) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("창고 이름은 필수입니다.");
        if (location == null || location.isBlank()) throw new IllegalArgumentException("창고 위치는 필수입니다.");
        if (managerName == null || managerName.isBlank()) throw new IllegalArgumentException("담당자명은 필수입니다.");
        if (contact == null || contact.isBlank()) throw new IllegalArgumentException("연락처는 필수입니다.");

        this.name = name;
        this.location = location;
        this.managerName = managerName;
        this.contact = contact;
    }

    //운영상태 관리 - WarehouseInventory, OutboundOrder 생성 시 “활성 창고만” 허용해야 함
    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    //창고 기본정보 수정
    public void updateInfo(String newName, String newLocation, String newManagerName, String newContact){
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("창고 이름은 비어 있을 수 없습니다.");
        }
        if (newLocation == null || newLocation.isBlank()) {
            throw new IllegalArgumentException("창고 위치는 비어 있을 수 없습니다.");
        }
        if (newContact == null || newContact.isBlank()) {
            throw new IllegalArgumentException("창고 연락처는 비어 있을 수 없습니다.");
        }
        this.name = newName;
        this.location = newLocation;
        this.managerName = newManagerName;
        this.contact = newContact;
    }

    //입출고 가능 여부 검증 - InboundOrder, OutboundOrder 생성 전 반드시 호출
    public void validateActive() {
        if (!this.active) {
            throw new IllegalStateException("비활성화된 창고에서는 입출고를 수행할 수 없습니다.");
        }
    }

}
