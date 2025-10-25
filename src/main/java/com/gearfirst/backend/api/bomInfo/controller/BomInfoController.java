package com.gearfirst.backend.api.bomInfo.controller;

import com.gearfirst.backend.api.bomInfo.dto.MaterialOfPartIdRequest;
import com.gearfirst.backend.api.bomInfo.dto.MaterialOfPartRequest;
import com.gearfirst.backend.api.bomInfo.dto.MaterialOfPartResponse;
import com.gearfirst.backend.api.bomInfo.service.BomInfoService;
import com.gearfirst.backend.common.response.ApiResponse;
import com.gearfirst.backend.common.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Get BOM API", description = "부품에 대한 자재 조회 API")
public class BomInfoController {
    private final BomInfoService bomInfoService;

    @Operation(summary = "부품의 자재 리스트 조회", description = "선택한 부품의 자재 리스트를 조회한다.")
    @GetMapping("/getMaterialList/{partId}")
    public ResponseEntity<ApiResponse<List<MaterialOfPartResponse>>> getMaterialList(@PathVariable Long partId) {
        List<MaterialOfPartResponse> materialList = bomInfoService.getMaterialList(partId);

        return ApiResponse
                .success(SuccessStatus.GET_MATERIAL_LIST_OF_PART_SUCCESS, materialList);
    }

    @Operation(summary = "부품의 자재 등록", description = "선택한 부품의 자재를 등록한다.")
    @PostMapping("/addMaterialList/{partId}")
    public ResponseEntity<ApiResponse<Void>> addMaterialList(@RequestBody MaterialOfPartRequest request) {
        bomInfoService.addMaterialList(request);

        return ApiResponse
                .success_only(SuccessStatus.ADD_MATERIAL_OF_PART_SUCCESS);
    }

    @Operation(summary = "부품의 자재 삭제", description = "선택한 부품의 자재를 삭제한다.")
    @PostMapping("/deleteMaterialList/{partId}")
    public ResponseEntity<ApiResponse<Void>> deleteMaterialList(@RequestBody MaterialOfPartIdRequest request) {
        bomInfoService.deleteMaterialList(request);

        return ApiResponse
                .success_only(SuccessStatus.DELETE_MATERIAL_OF_PART_SUCCESS);
    }
}
