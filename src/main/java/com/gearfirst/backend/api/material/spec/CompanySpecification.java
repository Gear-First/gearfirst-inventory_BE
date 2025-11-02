package com.gearfirst.backend.api.material.spec;

import com.gearfirst.backend.api.material.entity.CompanyEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CompanySpecification {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static Specification<CompanyEntity> byUntilDate(String endDateStr) {
        LocalDate endDate = LocalDate.parse(endDateStr, FORMATTER);
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        return (root, query, cb) -> cb.and(
                cb.lessThan(root.get("untilDate"), endDateTime)                 // <  종료일+1일
        );
    }

    private static Specification<CompanyEntity> byMaterial(String material) {
        return (root, query, cb) -> {
            String likePattern = "%" + material + "%";
            // 3개의 필드에서 OR 검색
            return cb.or(
                    cb.like(root.join("material").get("materialCode"), likePattern),
                    cb.like(root.join("material").get("materialName"), likePattern)
            );
        };
    }

    private static Specification<CompanyEntity> byCompany(String company) {
        return (root, query, cb) -> {
            String likePattern = "%" + company + "%";
            // 3개의 필드에서 OR 검색
            return cb.or(
                    cb.like(root.get("companyName"), likePattern)
            );
        };
    }

    private static Specification<CompanyEntity> byStatus(boolean status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<CompanyEntity> build(String endDate, String company, String material, Boolean isSelected) {

        Specification<CompanyEntity> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (endDate != null && !endDate.isEmpty()) {
            spec = spec.and(byUntilDate(endDate));
        }

        if (material != null && !material.isEmpty()) {
            spec = spec.and(byMaterial(material));
        }

        if (company != null && !company.isEmpty()) {
            spec = spec.and(byCompany(company));
        }

        if (isSelected != null) {
            spec = spec.and(byStatus(isSelected));
        }

        return spec;
    }
}