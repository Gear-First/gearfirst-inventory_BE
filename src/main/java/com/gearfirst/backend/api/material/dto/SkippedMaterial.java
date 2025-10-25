package com.gearfirst.backend.api.material.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkippedMaterial {
    private String materialCode;
    private String materialName;
    private String reason;
}
