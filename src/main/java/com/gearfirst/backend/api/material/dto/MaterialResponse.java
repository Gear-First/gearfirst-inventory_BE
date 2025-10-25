package com.gearfirst.backend.api.material.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaterialResponse {
    private String materialName;
    private String materialCode;
}
