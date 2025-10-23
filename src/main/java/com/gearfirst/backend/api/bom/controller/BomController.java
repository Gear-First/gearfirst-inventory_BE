package com.gearfirst.backend.api.bom.controller;

import com.gearfirst.backend.api.bom.dto.SearchMaterialListResponse;
import com.gearfirst.backend.api.bom.dto.SearchPartResponse;
import com.gearfirst.backend.common.response.ApiResponse;
import com.gearfirst.backend.common.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Get Receipt API", description = "접수 조회 API")
public class BomController {

    @Operation(summary = "전체 부품 리스트 조회", description = "전체 부품 리스트를 조회한다.")
    @GetMapping("/getPartList")
    public ResponseEntity<ApiResponse<List<SearchPartResponse>>> getPartList() {
        List<SearchPartResponse> partList = new ArrayList<>();
        partList.add(new SearchPartResponse("엔진", "engine"));
        partList.add(new SearchPartResponse("브레이크 패드", "brake pad"));
        partList.add(new SearchPartResponse("엔진 오일", "engine oil"));
        partList.add(new SearchPartResponse("브레이크 오일", "brake oil"));
        partList.add(new SearchPartResponse("창문", "window"));
        return ApiResponse
                .success(SuccessStatus.GET_PART_LIST_SUCCESS, partList);
    }

    @Operation(summary = "부품 자재 리스트 조회", description = "선택한 부품의 자재 리스트를 조회한다.")
    @GetMapping("/getMaterialList/{partId}")
    public ResponseEntity<ApiResponse<List<SearchMaterialListResponse>>> getMaterialList(@PathVariable Long partId) {
        List<SearchMaterialListResponse> materialList = new ArrayList<>();
        materialList.add(new SearchMaterialListResponse("자재A", "materialA", 1000, 2));
        materialList.add(new SearchMaterialListResponse("자재B", "materialB", 2000, 1));
        materialList.add(new SearchMaterialListResponse("자재C", "materialC", 1500, 1));
        materialList.add(new SearchMaterialListResponse("자재D", "materialD", 500, 10));
        materialList.add(new SearchMaterialListResponse("자재E", "materialE", 200, 5));
        return ApiResponse
                .success(SuccessStatus.GET_MATERIAL_LIST_SUCCESS, materialList);
    }
}
