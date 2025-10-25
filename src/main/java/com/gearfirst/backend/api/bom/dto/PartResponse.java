package com.gearfirst.backend.api.bom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PartResponse {
    private Long id;
    private String partName;
    private String partCode;
}
