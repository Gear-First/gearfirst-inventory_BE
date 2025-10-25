package com.gearfirst.backend.api.bomInfo.repository;

import com.gearfirst.backend.api.bomInfo.entity.BomInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BomInfoRepository extends JpaRepository<BomInfoEntity, Long> {
    List<BomInfoEntity> findByPartId(Long partId);
    void deleteByPartId(Long partId);
    void deleteByPartIdAndMaterialId(Long partId, Long materialId);
}
