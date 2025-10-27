package com.gearfirst.backend.api.bomInfo.entity;

import com.gearfirst.backend.api.bom.entitiy.PartBomEntity;
import com.gearfirst.backend.api.material.entity.MaterialEntity;
import com.gearfirst.backend.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bom_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BomInfoEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // N:1 관계 - 어떤 자재를 사용했는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private MaterialEntity material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bom_code_id")
    private BomCodeEntity bomCode;

    private int quantity;
}
