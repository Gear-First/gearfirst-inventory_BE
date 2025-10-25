package com.gearfirst.backend.api.bomInfo.dto;

import lombok.Data;

import java.util.List;

@Data
public class MaterialOfPartRequest {
    private Long partId;
    private List<MaterialInfo> materialInfos;
}
