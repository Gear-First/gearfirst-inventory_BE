package com.gearfirst.backend.api.bomInfo.repository;

import com.gearfirst.backend.api.bomInfo.entity.BomCodeEntity;
import com.gearfirst.backend.api.bomInfo.entity.BomInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BomCodeRepository extends JpaRepository<BomCodeEntity, Long>, JpaSpecificationExecutor<BomCodeEntity> {
    BomCodeEntity findByPartId(Long partId);
}
