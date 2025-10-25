package com.gearfirst.backend.api.material.repository;

import com.gearfirst.backend.api.material.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
    boolean existsByMaterialCode(String materialCode);
}
