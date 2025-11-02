package com.gearfirst.backend.api.material.repository;

import com.gearfirst.backend.api.material.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface MaterialRepository extends JpaRepository<MaterialEntity, Long>, JpaSpecificationExecutor<MaterialEntity> {
}
