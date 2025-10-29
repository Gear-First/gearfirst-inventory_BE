package com.gearfirst.backend.api.material.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CompanyRequest {
    private Long materialId;
    private String materialCode;
    private String materialName;
    private int price;
    private String companyName;
    private int quantity;
    private int spentDay;
    private LocalDate surveyDate;
    private LocalDate untilDate;
}
