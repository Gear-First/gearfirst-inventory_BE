package com.gearfirst.backend.api.outbound.service;

import com.gearfirst.backend.api.inventory.entity.Inventory;
import com.gearfirst.backend.api.inventory.repository.InventoryRepository;
import com.gearfirst.backend.api.outbound.dto.OutboundOrderCreateRequest;
import com.gearfirst.backend.api.outbound.dto.OutboundOrderResponse;
import com.gearfirst.backend.api.outbound.entity.OutboundItem;
import com.gearfirst.backend.api.outbound.entity.OutboundOrder;
import com.gearfirst.backend.api.outbound.repository.OutboundItemRepository;
import com.gearfirst.backend.api.outbound.repository.OutboundRepository;
import com.gearfirst.backend.api.warehouse.entity.Warehouse;
import com.gearfirst.backend.api.warehouse.entity.WarehouseInventory;
import com.gearfirst.backend.api.warehouse.repository.WarehouseInventoryRepository;
import com.gearfirst.backend.api.warehouse.repository.WarehouseRepository;
import com.gearfirst.backend.common.exception.NotFoundException;
import com.gearfirst.backend.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OutboundServiceImpl implements OutboundService{

    private final OutboundRepository outboundRepository;
    private final OutboundItemRepository outboundItemRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseInventoryRepository warehouseInventoryRepository;
    private final InventoryRepository inventoryRepository;

    /**
     * 본사 승인 발주에 대한 출고 명령
     */
    @Override
    public OutboundOrderResponse createOutboundOrder(OutboundOrderCreateRequest request) {
        //창고 존재 여부 검증
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(()-> new NotFoundException(ErrorStatus.NOT_FOUND_WAREHOUSE_EXCEPTION.getMessage()));
        //출고 명령 생성
        OutboundOrder order = OutboundOrder.builder()
                .purchaseOrderId(request.getPurchaseOrderId())
                .warehouse(warehouse)
                .build();
        outboundRepository.save(order);
        //품목별 출고 처리
        for(OutboundOrderCreateRequest.Item item: request.getItems()){
            Inventory inventory = inventoryRepository.findById(item.getInventoryId())
                    .orElseThrow(()-> new NotFoundException(ErrorStatus.NOT_FOUND_INVENTORY_EXCEPTION.getMessage()));

            WarehouseInventory warehouseInventory = warehouseInventoryRepository.findByWarehouseAndInventory(warehouse, inventory)
                    .orElseThrow(()-> new NotFoundException(ErrorStatus.NOT_FOUND_INVENTORY_FROM_WAREHOUSE_EXCEPTION.getMessage()));

            //출고 수행(재고차감)
            warehouseInventory.decreaseStock(item.getQuantity());

            //출고 상세 내역 저장
            OutboundItem outboundItem = OutboundItem.builder()
                    .outboundOrder(order)
                    .inventory(inventory)
                    .quantity(item.getQuantity())
                    .build();
            outboundItemRepository.save(outboundItem);
        }

        return OutboundOrderResponse.fromEntity(order);
    }
}
