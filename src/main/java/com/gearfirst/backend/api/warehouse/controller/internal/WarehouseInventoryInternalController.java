package com.gearfirst.backend.api.warehouse.controller.internal;

import com.gearfirst.backend.api.warehouse.controller.internal.dto.InventoryResponseDto;
import com.gearfirst.backend.api.warehouse.controller.internal.dto.OutboundRequest;
import com.gearfirst.backend.api.warehouse.dto.InventoryInternalResponse;
import com.gearfirst.backend.api.warehouse.service.WarehouseInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/warehouse-inventories")
public class WarehouseInventoryInternalController {
    private final WarehouseInventoryService warehouseInventoryService;

    //부품 단건 조회
    @GetMapping("/{id}")
    public InventoryInternalResponse getInventoryById(@PathVariable Long id) {
        return warehouseInventoryService.getInventoryInternalResponse(id);
    }
    //출고 명령 생성
//    @PostMapping("/outbounds")
//    public void createOutboundOrder(@RequestBody OutboundRequest request){
//        warehouseInventoryService.createOutboundOrder(request);
//    }
    //차량 모델 + 키워드 기반 부품 검색
    @GetMapping("/{carModelId}/{keyword}")
    public List<InventoryResponseDto> getInventoriesByCarModel(@PathVariable Long carModelId, @PathVariable String keyword){
        return warehouseInventoryService.getInventoriesByCarModel(carModelId, keyword);
    }

}
