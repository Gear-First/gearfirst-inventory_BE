package com.gearfirst.backend.api.bomInfo.entity;

import com.gearfirst.backend.api.bom.entitiy.PartBomEntity;
import com.gearfirst.backend.api.material.entity.MaterialEntity;
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
public class BomInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // N:1 관계 - 어떤 부품의 자재인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private PartBomEntity part;

    // N:1 관계 - 어떤 자재를 사용했는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private MaterialEntity material;

    private int price;
    private int quantity;
}
