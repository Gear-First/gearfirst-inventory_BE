package com.gearfirst.backend.api.material.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FailedMaterial {
    private MaterialRequest request;
    private String errorMessage;
}
