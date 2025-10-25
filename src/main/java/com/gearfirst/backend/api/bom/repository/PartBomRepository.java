package com.gearfirst.backend.api.bom.repository;

import com.gearfirst.backend.api.bom.entitiy.PartBomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartBomRepository extends JpaRepository<PartBomEntity, Long> {
}
