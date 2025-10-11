package com.gearfirst.backend.api.warehouse.controller;

import com.gearfirst.backend.api.warehouse.dto.WarehouseInventoryResponse;
import com.gearfirst.backend.api.warehouse.service.WarehouseInventoryService;
import com.gearfirst.backend.common.response.ApiResponse;
import com.gearfirst.backend.common.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouse-inventories")
@RequiredArgsConstructor
@Tag(name ="Warehouse Inventory", description = "창고별 재고 API")
public class WarehouseInventoryController {
    private final WarehouseInventoryService warehouseInventoryService;

    @Operation(summary = "전체 재고 조회", description = "모든 창고별 재고를 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<WarehouseInventoryResponse>>> getAllInventories() {
        List<WarehouseInventoryResponse> responses = warehouseInventoryService.getAllInventories();
        return ApiResponse.success(SuccessStatus.GET_INVENTORY_LIST_SUCCESS, responses);
    }

    @Operation(summary = "재고 단건 조회", description = "재고 ID로 단건 조회합니다.")
    @GetMapping("/{inventoryId}/{warehouseId}")
    public ResponseEntity<ApiResponse<WarehouseInventoryResponse>> getInventory(@PathVariable Long inventoryId, @PathVariable Long warehouseId) {
        WarehouseInventoryResponse response = warehouseInventoryService.getInventory(inventoryId,warehouseId);
        return ApiResponse.success(SuccessStatus.GET_INVENTORY_SUCCESS, response);
    }

    @Operation(summary = "이름 검색", description = "부품 이름으로 재고를 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<WarehouseInventoryResponse>>> searchByName(@RequestParam String name) {
        List<WarehouseInventoryResponse> responses = warehouseInventoryService.getByInventoryName(name);
        return ApiResponse.success(SuccessStatus.GET_INVENTORY_SUCCESS, responses);
    }

    @Operation(summary = "날짜별 입출고 재고 검색", description = "입고·출고 날짜로 재고를 필터링합니다.")
    @GetMapping("/search/date")
    public ResponseEntity<ApiResponse<List<WarehouseInventoryResponse>>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<WarehouseInventoryResponse> responses = warehouseInventoryService.getInventoriesByDate(startDate, endDate);
        return ApiResponse.success(SuccessStatus.GET_INVENTORY_LIST_SUCCESS, responses);
    }
}
