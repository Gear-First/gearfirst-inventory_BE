package com.gearfirst.backend.api.inventory.service;

import com.gearfirst.backend.api.inventory.entity.Inventory;
import com.gearfirst.backend.api.inventory.enums.InventoryStatus;
import com.gearfirst.backend.api.inventory.repository.InventoryRepository;
import com.gearfirst.backend.common.exception.NotFoundException;
import com.gearfirst.backend.common.response.ErrorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;       //특정 mock 객체가 어떤 메서드를 몇번 호출했는지 검증
import static org.mockito.Mockito.times;        //호출 횟수를 지정하는 helper 메서드
import static org.mockito.Mockito.when;         //Mock 동작을 세팅하는 Stubbing 메서드


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
                .price(50000)
                .build();

        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(mockInventory));

//        // when
//        Inventory result = inventoryService.getInventory(1L);
//
//        // then
//        assertThat(result.getInventoryName()).isEqualTo("브레이크 패드");
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
    void 재고_전체목록조회_성공() {
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

//        // when
//        List<Inventory> result = inventoryService.getAllInventories();
//
//        // then
//        assertThat(result).hasSize(2);
//        assertThat(result).extracting("inventoryName")
//                .containsExactlyInAnyOrder("브레이크 패드", "에어필터");
    }

    /**
     * inboundDate는 DB 에서 INSERT 할 때 자동 세팅 되게 되어있음
     * Mock 테스트에서는 DB에 개입하지 않으므로 inboundDate ==null 상태
     * 그래서 Repository 메서드 호출 여부만 검증
     */
    @Test
    void 재고_날짜_필터링조회_성공() {
        // given
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 31);

        Inventory inv1 = Inventory.builder()
                .inventoryName("브레이크 패드")
                .inventoryCode("BP-001")
                .currentStock(100)
                .availableStock(100)
                .warehouse("A창고")
                .inventoryStatus(InventoryStatus.STABLE)
                .build();

        Inventory inv2 = Inventory.builder()
                .inventoryName("에어필터")
                .inventoryCode("AF-001")
                .currentStock(50)
                .availableStock(50)
                .warehouse("B창고")
                .inventoryStatus(InventoryStatus.STABLE)
                .build();

        //inventoryRepository.findByInboundDateBetween()이 호출되면 무조건 List.of(inv1,inv2)를 리턴하도록 미리 지정
        when(inventoryRepository.findByInboundDateBetween(any(), any()))
                .thenReturn(List.of(inv1, inv2));

//        // when
//        List<Inventory> result = inventoryService.getInventoriesByDate(startDate,endDate);
//
//        // then
//        assertThat(result).hasSize(2);
//        assertThat(result).extracting("inventoryName") //리스트 안의 객체에서 inventoryName 속성만 추출해서 비교
//                .containsExactlyInAnyOrder("브레이크 패드", "에어필터");
//
//        verify(inventoryRepository, times(1)) //해당 목 객체가 1번 호출됐는지 확인
//                .findByInboundDateBetween(
//                        eq(startDate.atStartOfDay()),
//                        eq(endDate.atTime(LocalTime.MAX))
//                );
    }
}