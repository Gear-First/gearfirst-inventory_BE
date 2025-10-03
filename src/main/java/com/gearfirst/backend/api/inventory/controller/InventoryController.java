package com.gearfirst.backend.api.inventory.controller;

import com.gearfirst.backend.api.inventory.dto.InventoryResponse;
import com.gearfirst.backend.api.inventory.entity.Inventory;
import com.gearfirst.backend.api.inventory.service.InventoryService;
import com.gearfirst.backend.common.response.ApiResponse;
import com.gearfirst.backend.common.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Tag(name = "inventory", description = "inventory API 입니다.")
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(summary = "재고 전체 조회 API", description = "전체 재고 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getAllInventories(){

        List<Inventory> inventories = inventoryService.getInventories(null, null);
        List<InventoryResponse> responses = inventories.stream()
                .map(InventoryResponse::fromEntity)
                .collect(Collectors.toList());

        return ApiResponse.success(SuccessStatus.GET_INVENTORY_LIST_SUCCESS, responses);
    }

    @Operation(summary = "재고 검색 API", description = "날짜로 필터링 하여 조회합니다.")
    @GetMapping("/search/date")
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getInventories(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<Inventory> inventories = inventoryService.getInventories(startDate, endDate);
        List<InventoryResponse> responses = inventories.stream()
                .map(InventoryResponse::fromEntity)
                .collect(Collectors.toList());

        return ApiResponse.success(SuccessStatus.GET_INVENTORY_LIST_SUCCESS, responses);
    }
    @Operation(summary = "재고 단건 조회 API", description = "단건 재고 데이터를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryResponse>> getInventory(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventory(id);
        InventoryResponse response = InventoryResponse.fromEntity(inventory);

        return ApiResponse.success(SuccessStatus.GET_INVENTORY_SUCCESS, response);
    }
}
