package com.gearfirst.backend.api.warehouse.repository;

import com.gearfirst.backend.api.warehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
