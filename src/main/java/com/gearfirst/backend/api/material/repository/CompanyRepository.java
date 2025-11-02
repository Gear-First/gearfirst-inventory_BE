package com.gearfirst.backend.api.material.repository;

import com.gearfirst.backend.api.material.entity.CompanyEntity;
import com.gearfirst.backend.api.material.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyRepository extends JpaRepository<CompanyEntity,Long>, JpaSpecificationExecutor<CompanyEntity> {
}
