package com.gearfirst.backend.api.inventory.repository;

import com.gearfirst.backend.api.inventory.entity.Inventory;
import com.gearfirst.backend.api.inventory.enums.InventoryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    @DisplayName("입고일자 기준으로 범위 조회가 가능하다")
    void findByInboundDateBetween() {
        // given
        Inventory inv1 = Inventory.builder()
                .inventoryName("에어필터")
                .inventoryCode("AF-001")
                .currentStock(50)
                .availableStock(50)
                .warehouse("서울 창고")
                .price(15000)
                .inventoryStatus(InventoryStatus.STABLE)
                .build();

        Inventory inv2 = Inventory.builder()
                .inventoryName("오일필터")
                .inventoryCode("OF-001")
                .currentStock(10)
                .availableStock(10)
                .warehouse("대전 창고")
                .price(21000)
                .inventoryStatus(InventoryStatus.NEED_RESTOCK)
                .build();

        inventoryRepository.save(inv1);
        inventoryRepository.save(inv2);

        LocalDateTime start = LocalDateTime.now().minusDays(1); //현재 시간에서 하루를 빼는 메서드
        LocalDateTime end = LocalDateTime.now().plusDays(1);    //현재 시간에서 하루를 더하는 메서드

        // when
        List<Inventory> result = inventoryRepository.findByInboundDateBetween(start, end);

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    void findByInventoryName(){
        // given
        Inventory inv1 = Inventory.builder()
                .inventoryName("에어필터")
                .inventoryCode("AF-001")
                .currentStock(50)
                .availableStock(50)
                .warehouse("서울 창고")
                .price(15000)
                .inventoryStatus(InventoryStatus.STABLE)
                .build();

        inventoryRepository.save(inv1);

        // when
        List<Inventory> result = inventoryRepository.findByInventoryName("에어필터");

        // then
        assertThat(result.get(0).getInventoryName()).isEqualTo("에어필터");
        assertThat(result.get(0).getWarehouse()).isEqualTo("서울 창고");
    }
}
