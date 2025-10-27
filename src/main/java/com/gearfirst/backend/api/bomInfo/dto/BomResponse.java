package com.gearfirst.backend.api.bomInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BomResponse {
    private Long bomCodeId;
    private String bomCode;
    private String category;
    private String partCode;
    private String partName;
    private LocalDate createdAt;
}
