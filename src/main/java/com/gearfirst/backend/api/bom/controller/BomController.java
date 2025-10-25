package com.gearfirst.backend.api.bom.controller;

import com.gearfirst.backend.api.bom.dto.PartResponse;
import com.gearfirst.backend.api.bom.service.PartBomService;
import com.gearfirst.backend.common.response.ApiResponse;
import com.gearfirst.backend.common.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Get PART API", description = "부품 조회 API")
public class BomController {
    private final PartBomService partBomService;

    @Operation(summary = "전체 부품 리스트 조회", description = "전체 부품 리스트를 조회한다.")
    @GetMapping("/getPartList")
    public ResponseEntity<ApiResponse<List<PartResponse>>> getPartList() {
        List<PartResponse> partList = partBomService.getPartList();

        return ApiResponse
                .success(SuccessStatus.GET_PART_LIST_SUCCESS, partList);
    }
}
