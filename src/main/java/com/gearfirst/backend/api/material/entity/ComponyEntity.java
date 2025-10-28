package com.gearfirst.backend.api.material.entity;

import com.gearfirst.backend.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "company")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponyEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String materialName;
    private String materialCode;
    private int price;
    private int quantity;
    private LocalDate surveyDate; // 조사일
    private LocalDate contractDate; // 계약일

}
