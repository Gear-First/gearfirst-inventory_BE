package com.gearfirst.backend.api.material.spec;

import com.gearfirst.backend.api.material.entity.CompanyEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CompanySpecification {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static Specification<CompanyEntity> byKeyword(String keyword) {
        return (root, query, cb) -> {
            String likePattern = "%" + keyword + "%";
            // 3개의 필드에서 OR 검색
            return cb.or(
                    cb.like(root.join("material").get("materialCode"), likePattern),
                    cb.like(root.join("material").get("materialName"), likePattern),
                    cb.like(root.get("companyName"), likePattern)
            );
        };
    }

    private static Specification<CompanyEntity> byStatus(boolean status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<CompanyEntity> build(String keyword, Boolean isSelected) {

        Specification<CompanyEntity> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and(byKeyword(keyword));
        }

        if (isSelected != null) {
            spec = spec.and(byStatus(isSelected));
        }

        return spec;
    }
}