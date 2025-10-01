package com.gearfirst.backend.api.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearfirst.backend.api.inventory.entity.Inventory;
import com.gearfirst.backend.api.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
                        .inventoryCode("BP-001")
                        .currentStock(100)
                        .availableStock(100)
                        .warehouse("A창고")
                        .build()
        );
    }
    @Test
    void 재고목록조회_API() throws Exception {
        mockMvc.perform(get("/api/v1/inventory")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].inventoryName").value("브레이크 패드"))
                .andExpect(jsonPath("$.data[0].currentStock").value(100));
    }

    @Test
    void 재고단건조회_API() throws Exception {
        Inventory inv = inventoryRepository.findAll().get(0);

        mockMvc.perform(get("/api/v1/inventory/" + inv.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.inventoryCode").value("BP-001"))
                .andExpect(jsonPath("$.data.inventoryName").value("브레이크 패드"));
    }
}