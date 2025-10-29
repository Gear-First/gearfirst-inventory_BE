package com.gearfirst.backend.api.material.controller;

import com.gearfirst.backend.api.bomInfo.dto.PageResponse;
import com.gearfirst.backend.api.material.dto.CompanyRequest;
import com.gearfirst.backend.api.material.dto.MaterialRequest;
import com.gearfirst.backend.api.material.dto.MaterialResponse;
import com.gearfirst.backend.api.material.dto.RegistrationResponse;
import com.gearfirst.backend.api.material.service.MaterialService;
import com.gearfirst.backend.common.response.ApiResponse;
import com.gearfirst.backend.common.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Get Material API", description = "자재 API")
public class MaterialController {
    private final MaterialService materialService;

    @Operation(summary = "자재 리스트 조회(검색)", description = "자재 리스트를 조회한다.")
    @GetMapping("/getMaterialList")
    public ResponseEntity<ApiResponse<PageResponse<MaterialResponse>>> getMaterialList(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<MaterialResponse> bomList = materialService.getMaterialList(startDate, endDate, keyword, pageable);
        PageResponse<MaterialResponse> response = new PageResponse<>(
                bomList.getContent(),
                bomList.getNumber(),
                bomList.getSize(),
                bomList.getTotalElements(),
                bomList.getTotalPages()
        );
        return ApiResponse
                .success(SuccessStatus.GET_MATERIAL_LIST_OF_PART_SUCCESS, response);
    }

    @Operation(summary = "자재 등록", description = "새로운 자재를 등록합니다.")
    @PostMapping("/addMaterial")
    public ResponseEntity<ApiResponse<RegistrationResponse>> addMaterial(@RequestBody List<MaterialRequest> materialRequest) {
        RegistrationResponse response = materialService.addMaterial(materialRequest);

        return ApiResponse
                .success(SuccessStatus.ADD_MATERIAL_SUCCESS, response);
    }

    @Operation(summary = "자재 삭제", description = "기존 자재를 삭제합니다.")
    @PostMapping("/deleteMaterial")
    public ResponseEntity<ApiResponse<RegistrationResponse>> deleteMaterial(@RequestBody List<MaterialRequest> materialRequest) {
        RegistrationResponse response = materialService.deleteMaterial(materialRequest);

        return ApiResponse
                .success(SuccessStatus.DELETE_MATERIAL_SUCCESS, response);
    }

    @Operation(summary = "업체 등록", description = "자재 납품 업체 등록")
    @PostMapping("/addCompany")
    public ResponseEntity<ApiResponse<Void>> addCompany(@RequestBody CompanyRequest request) {
        materialService.addCompany(request);

        return ApiResponse
                .success_only(SuccessStatus.ADD_COMPANY_OF_MATERIAL_SUCCESS);
    }
}
