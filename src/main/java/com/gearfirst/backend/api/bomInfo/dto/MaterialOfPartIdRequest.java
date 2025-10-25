package com.gearfirst.backend.api.bomInfo.dto;

import lombok.Data;

import java.util.List;

@Data
public class MaterialOfPartIdRequest {
    private Long partId;
    private List<Long> materialIds;
}
