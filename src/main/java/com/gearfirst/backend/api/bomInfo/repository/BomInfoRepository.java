package com.gearfirst.backend.api.bomInfo.repository;

import com.gearfirst.backend.api.bomInfo.entity.BomInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BomInfoRepository extends JpaRepository<BomInfoEntity, Long> {
    List<BomInfoEntity> findByBomCodeId(Long bomCodeId);
    void deleteByBomCodeId(Long BomCodeId);
    void deleteByBomCodeIdAndMaterialId(Long bomCodeId, Long materialId);
}
