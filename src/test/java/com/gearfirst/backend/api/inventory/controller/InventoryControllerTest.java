package com.gearfirst.backend.api.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearfirst.backend.api.inventory.entity.Inventory;
import com.gearfirst.backend.api.inventory.enums.InventoryStatus;
import com.gearfirst.backend.api.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        inventoryRepository.deleteAll();
        inventoryRepository.save(
                Inventory.builder()
                        .inventoryName("브레이크 패드")
                        .inventoryCode("BP-001-s")
                        .currentStock(100)
                        .availableStock(100)
                        .warehouse("서울 창고")
                        .price(15000)
                        .inventoryStatus(InventoryStatus.STABLE)
                        .build()
        );
        inventoryRepository.save(
                Inventory.builder()
                        .inventoryName("에어필터")
                        .inventoryCode("AF-001-d")
                        .currentStock(0)
                        .availableStock(0)
                        .warehouse("대전 창고")
                        .price(21000)
                        .inventoryStatus(InventoryStatus.STABLE)
                        .build()
        );
    }

    @Test
    void 재고전체조회_API() throws Exception {
        mockMvc.perform(get("/api/v1/inventory")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].inventoryName")
                        .value(containsInAnyOrder("브레이크 패드","에어필터")));
    }

    @Test
    void 재고날짜검색_API() throws Exception {
        String startDate = LocalDateTime.now().minusDays(1).toLocalDate().toString();
        String endDate = LocalDateTime.now().plusDays(1).toLocalDate().toString();

        mockMvc.perform(get("/api/v1/inventory/search/date")
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].inventoryCode")
                        .value(containsInAnyOrder("BP-001-s", "AF-001-d")));
    }

    @Test
    void 재고단건조회_API() throws Exception {
        List<Inventory> inventories = inventoryRepository.findAll();
        Inventory inv = inventories.get(0);

        mockMvc.perform(get("/api/v1/inventory/" + inv.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.inventoryCode").value(inv.getInventoryCode()))
                .andExpect(jsonPath("$.data.inventoryName").value(inv.getInventoryName()));
    }

    @Test
    void 재고이름검색_API() throws Exception {
        List<Inventory> inventories = inventoryRepository.findByInventoryName("브레이크 패드");
        Inventory inv = inventories.get(0);

        mockMvc.perform(get("/api/v1/inventory/search/name")
                        .param("name", "브레이크 패드")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].inventoryCode")
                        .value(containsInAnyOrder("BP-001-s")));

    }


}