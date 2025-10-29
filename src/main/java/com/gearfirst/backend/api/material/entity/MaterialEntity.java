package com.gearfirst.backend.api.material.entity;

import com.gearfirst.backend.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "material")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String materialName;
    private String materialCode;
    private int price;
    private int quantity;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<CompanyEntity> companies;
}
