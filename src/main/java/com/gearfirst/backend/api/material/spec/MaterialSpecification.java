package com.gearfirst.backend.api.material.spec;

import com.gearfirst.backend.api.bomInfo.entity.BomCodeEntity;
import com.gearfirst.backend.api.material.entity.MaterialEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MaterialSpecification {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static Specification<MaterialEntity> byCreatedAtBetween(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr, FORMATTER);
        LocalDate endDate = LocalDate.parse(endDateStr, FORMATTER);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        return (root, query, cb) -> cb.and(
                cb.greaterThanOrEqualTo(root.get("createdAt"), startDateTime), // >= 시작일
                cb.lessThan(root.get("createdAt"), endDateTime)                 // <  종료일+1일
        );
    }

    private static Specification<MaterialEntity> byKeyword(String keyword) {
        return (root, query, cb) -> {
            String likePattern = "%" + keyword + "%";
            // 3개의 필드에서 OR 검색
            return cb.or(
                    cb.like(root.get("materialCode"), likePattern),
                    cb.like(root.get("materialName"), likePattern)
            );
        };
    }

    public static Specification<MaterialEntity> build(String startDate, String endDate, String keyword) {

        Specification<MaterialEntity> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            spec = spec.and(byCreatedAtBetween(startDate, endDate));
        }

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and(byKeyword(keyword));
        }

        return spec;
    }
}