package com.gearfirst.backend.api.bom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchMaterialListResponse {
    private String materialName;
    private String materialCode;
    private int materialPrice;
    private int materialQuantity;
}
