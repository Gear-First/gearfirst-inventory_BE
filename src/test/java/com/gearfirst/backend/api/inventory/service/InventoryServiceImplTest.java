package com.gearfirst.backend.api.inventory.service;

import com.gearfirst.backend.api.inventory.dto.InventoryResponse;
import com.gearfirst.backend.api.inventory.entity.Inventory;
import com.gearfirst.backend.api.inventory.enums.InventoryStatus;
import com.gearfirst.backend.api.inventory.repository.InventoryRepository;
import com.gearfirst.backend.common.exception.NotFoundException;
import com.gearfirst.backend.common.response.ErrorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;         //Mock 동작을 세팅하는 Stubbing 메서드

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {
//    @Mock
//    private InventoryRepository inventoryRepository;
//
//    @InjectMocks
//    private InventoryServiceImpl inventoryService;
//
//    private Inventory inventory1;
//    private Inventory inventory2;
//    private Inventory inventory3;
//
//
//    @BeforeEach
//    void setUp() {
//        inventory1 = Inventory.builder()
//                .inventoryName("에어필터")
//                .inventoryCode("AF-001-s")
//                .currentStock(50)
//                .availableStock(50)
//                .warehouse("서울 창고")
//                .price(15000)
//                .inventoryStatus(InventoryStatus.STABLE)
//                .build();
//
//        inventory2 = Inventory.builder()
//                .inventoryName("오일필터")
//                .inventoryCode("OF-001-b")
//                .currentStock(20)
//                .availableStock(20)
//                .warehouse("부산 창고")
//                .price(18000)
//                .inventoryStatus(InventoryStatus.STABLE)
//                .build();
//
//        inventory3 = Inventory.builder()
//                .inventoryName("에어필터")
//                .inventoryCode("AF-001-b")
//                .currentStock(50)
//                .availableStock(50)
//                .warehouse("부산 창고")
//                .price(10700)
//                .inventoryStatus(InventoryStatus.STABLE)
//                .build();
//    }
//
//    @Test
//    void 재고_전체조회_성공() {
//        // given
//        when(inventoryRepository.findAll()).thenReturn(List.of(inventory1,inventory2));
//        // when
//        List<InventoryResponse> result = inventoryService.getAllInventories();
//
//        // then
//        assertThat(result).hasSize(2);
//        assertThat(result.get(0).getInventoryName()).isEqualTo("에어필터");
//        assertThat(result.get(1).getInventoryName()).isEqualTo("오일필터");
//        assertThat(result.get(0).getWarehouse()).isEqualTo("서울 창고");
//        assertThat(result.get(1).getWarehouse()).isEqualTo("부산 창고");
//    }
//
//    @Test
//    void 재고_단건조회_성공(){
//        //given
//        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory1));
//
//        //when
//        InventoryResponse result = inventoryService.getInventory(1L);
//
//        //then
//        assertThat(result.getInventoryName()).isEqualTo("에어필터");
//        assertThat(result.getWarehouse()).isEqualTo("서울 창고");
//
//    }
//
//    @Test
//    void 재고_단건조회_실패_NotFound() {
//        // given
//        when(inventoryRepository.findById(999L)).thenReturn(Optional.empty());
//
//        // when & then
//        assertThatThrownBy(() -> inventoryService.getInventory(999L))
//                .isInstanceOf(NotFoundException.class)
//                .hasMessage(ErrorStatus.NOT_FOUND_INVENTORY_EXCEPTION.getMessage());
//    }
//
//    @Test
//    void 재고명_검색_성공(){
//        //given
//        when(inventoryRepository.findByInventoryName("에어필터")).thenReturn(List.of(inventory1,inventory3));
//
//        //when
//        List<InventoryResponse> result = inventoryService.getInventoryByName("에어필터");
//
//        //then
//        assertThat(result.get(0).getWarehouse()).isEqualTo("서울 창고");
//        assertThat(result.get(1).getWarehouse()).isEqualTo("부산 창고");
//        assertThat(result.get(0).getInventoryCode()).isEqualTo("AF-001-s");
//        assertThat(result.get(1).getInventoryCode()).isEqualTo("AF-001-b");
//    }
//
//
//    @Test
//    void 재고_날짜필터링조회_성공() {
//        // given
//        LocalDate start = LocalDate.now().minusDays(1);
//        LocalDate end = LocalDate.now();
//
//        // inboundDateBetween() mock
//        when(inventoryRepository.findByInboundDateBetween(any(), any()))
//                .thenReturn(List.of(inventory1, inventory2));
//
//        // when
//        List<InventoryResponse> result = inventoryService.getInventoriesByDate(start, end);
//
//        // then
//        assertThat(result).hasSize(2);
//        assertThat(result.get(1).getInventoryName()).isEqualTo("오일필터");
//    }
//
//    @Test
//    void 재고_날짜필터링조회_실패_시작일이_종료일이후() {
//        LocalDate start = LocalDate.now();
//        LocalDate end = LocalDate.now().minusDays(3);
//
//        assertThatThrownBy(() -> inventoryService.getInventoriesByDate(start, end))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("시작일은 종료일보다 이후일 수 없습니다.");
//    }
}