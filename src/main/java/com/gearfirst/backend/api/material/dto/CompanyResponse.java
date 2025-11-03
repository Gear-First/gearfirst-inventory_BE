package com.gearfirst.backend.api.material.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CompanyResponse {
    private Long companyId;
    private String registNum;
    private String materialName;
    private String materialCode;
    private int spendDay;
    private int quantity;
    private LocalDate surveyDate;
    private String companyName;
    private int price;
    private LocalDate untilDate;
    private int orderCnt;
    private LocalDateTime createdAt;
}
