package com.gearfirst.backend.api.material.dto;

import lombok.Data;

@Data
public class MaterialRequest {
    private String materialName;
    private String materialCode;
}
