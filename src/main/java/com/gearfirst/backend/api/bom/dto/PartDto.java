package com.gearfirst.backend.api.bom.dto;

import lombok.Data;

@Data
public class PartDto {
    private Long id;
    private String partName;
    private String partCode;
    private String category;
    private String supplierName;
}
