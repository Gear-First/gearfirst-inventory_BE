package com.gearfirst.backend.api.material.repository;

import com.gearfirst.backend.api.material.entity.CompanySequence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanySequenceRepository extends JpaRepository<CompanySequence, Long> {
    Optional<CompanySequence> findByDatePart(String datePart);
}
