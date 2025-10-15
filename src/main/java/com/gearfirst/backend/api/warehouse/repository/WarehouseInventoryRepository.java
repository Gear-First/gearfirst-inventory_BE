package com.gearfirst.backend.api.warehouse.repository;

import com.gearfirst.backend.api.inventory.entity.Inventory;
import com.gearfirst.backend.api.warehouse.entity.Warehouse;
import com.gearfirst.backend.api.warehouse.entity.WarehouseInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WarehouseInventoryRepository extends JpaRepository<WarehouseInventory,Long> {
    @Query("""
        SELECT wi
        FROM WarehouseInventory wi
        JOIN FETCH wi.inventory i
        JOIN FETCH wi.warehouse w
    """)
    List<WarehouseInventory> findAllWithRelations();

    @Query("""
        SELECT wi
        FROM WarehouseInventory wi
        JOIN wi.inventory i
        WHERE i.inventoryName LIKE %:name%
    """)
    List<WarehouseInventory> findByInventoryName(@Param("name") String name);

    @Query("""
    SELECT wi
    FROM WarehouseInventory wi
    JOIN wi.warehouse w
    JOIN wi.inventory i
    WHERE i.id = :inventoryId AND w.id = :warehouseId
""")
    Optional<WarehouseInventory> findByInventoryIdAndWarehouseId(
            @Param("inventoryId") Long inventoryId,
            @Param("warehouseId") Long warehouseId);


    // 입고일자 기준 (입고된 재고만 조회)
    @Query("""
        SELECT DISTINCT wi
        FROM WarehouseInventory wi
        JOIN wi.inventory i
        JOIN wi.warehouse w
        JOIN InboundItem ii ON ii.inventory.id = i.id
        JOIN InboundOrder io ON ii.inboundOrder.id = io.id
        WHERE io.inboundDate BETWEEN :startDate AND :endDate
    """)
    List<WarehouseInventory> findByInboundDateBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // 창고와 부품으로 재고 찾기
    @Query("""
        SELECT wi
        FROM WarehouseInventory wi
        JOIN FETCH wi.warehouse w
        JOIN FETCH wi.inventory i
        WHERE w = :warehouse AND i = :inventory
    """)
    Optional<WarehouseInventory> findByWarehouseAndInventory(
            @Param("warehouse") Warehouse warehouse,
            @Param("inventory") Inventory inventory
    );

    // 차량모델과 연결된 부품 ID + 이름 검색
    @Query("""
        SELECT wi FROM WarehouseInventory wi
        JOIN wi.inventory i
        JOIN CarModelParts cmp ON cmp.inventory.id = i.id
        WHERE cmp.carModel.carModelId = :carModelId
        AND LOWER(i.inventoryName) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<WarehouseInventory> findByCarModelAndKeyword(
            @Param("carModelId") Long carModelId,
            @Param("keyword") String keyword
    );
}
