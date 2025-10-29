package com.gearfirst.backend.api.material.repository;

import com.gearfirst.backend.api.material.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {
}
