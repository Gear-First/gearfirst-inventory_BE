package com.gearfirst.backend.api.bomInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaterialOfPartResponse {
    private Long materialId;
    private String materialName;
    private String materialCode;
    private int materialPrice;
    private int materialQuantity;
}
