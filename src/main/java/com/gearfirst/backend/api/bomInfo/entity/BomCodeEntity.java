package com.gearfirst.backend.api.bomInfo.entity;

import com.gearfirst.backend.api.bom.entitiy.PartBomEntity;
import com.gearfirst.backend.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bom_code")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BomCodeEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bomCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private PartBomEntity part;
}
