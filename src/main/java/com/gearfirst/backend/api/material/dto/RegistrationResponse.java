package com.gearfirst.backend.api.material.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegistrationResponse {
    private int totalCount;           // 전체 요청 개수
    private int successCount;         // 성공한 개수
    private int skippedCount;         // 중복으로 스킵된 개수
    private int failedCount;          // 실패한 개수

    private List<MaterialResponse> successList;  // 성공한 자재 목록
    private List<SkippedMaterial> skippedList;   // 중복 스킵된 자재 목록
    private List<FailedMaterial> failedList;     // 실패한 자재 목록
}
