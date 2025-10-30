package com.gearfirst.backend.api.material.dto;

import lombok.Data;

@Data
public class SelectedCompanyRequest {
    private Long id;
    private int orderCnt;
    private int totalPrice;
}
