package com.gearfirst.backend.api.inventory.entity;

import com.gearfirst.backend.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="car_model_part")
public class CarModelParts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_model_part_id")
    private Long carModelPartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="inventory_id",nullable = false)
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="car_model_id",nullable = false)
    private CarModel carModel;


}
