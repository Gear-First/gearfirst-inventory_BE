package com.gearfirst.backend.api.inventory.service;

import com.gearfirst.backend.api.inventory.entity.Inventory;
import com.gearfirst.backend.api.inventory.repository.InventoryRepository;
import com.gearfirst.backend.common.exception.NotFoundException;
import com.gearfirst.backend.common.response.ErrorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class InventoryServiceImplTest {
    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 재고_단건조회_성공() {
        // given
        Inventory mockInventory = Inventory.builder()
                .inventoryName("브레이크 패드")
                .inventoryCode("BP-001")
                .currentStock(100)
                .availableStock(100)
                .warehouse("A창고")
                .build();

        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(mockInventory));

        // when
        Inventory result = inventoryService.getInventory(1L);

        // then
        assertThat(result.getInventoryName()).isEqualTo("브레이크 패드");
    }

    @Test
    void 재고_단건조회_실패_NotFound() {
        // given
        when(inventoryRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> inventoryService.getInventory(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ErrorStatus.NOT_FOUND_INVENTORY_EXCEPTION.getMessage());
    }
    @Test
    void 재고_목록조회_성공() {
        // given
        Inventory inv1 = Inventory.builder()
                .inventoryName("브레이크 패드")
                .inventoryCode("BP-001")
                .currentStock(100)
                .availableStock(100)
                .warehouse("A창고")
                .build();

        Inventory inv2 = Inventory.builder()
                .inventoryName("에어필터")
                .inventoryCode("AF-001")
                .currentStock(50)
                .availableStock(50)
                .warehouse("B창고")
                .build();

        when(inventoryRepository.findAll()).thenReturn(List.of(inv1, inv2));

        // when
        List<Inventory> result = inventoryService.getInventories(null, null);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting("inventoryName")
                .containsExactlyInAnyOrder("브레이크 패드", "에어필터");
    }
}