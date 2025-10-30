package com.gearfirst.backend.api.material.dto;

import lombok.Data;

@Data
public class DeleteMaterialRequest {
    private Long materialId;
    private String materialName;
    private String materialCode;
}
