package com.gearfirst.backend.api.bomInfo.spec;

import com.gearfirst.backend.api.bomInfo.entity.BomCodeEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BomSpecification {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    // 카테고리 검색 (연관된 PartBomEntity의 'category' 필드 검색)
    private static Specification<BomCodeEntity> byCategory(String category) {
        // root.join("part")를 통해 BomCodeEntity와 연관된 PartBomEntity에 접근
        return (root, query, cb) -> cb.equal(root.join("part").get("category"), category);
    }

    // 날짜 범위 검색 (BomCodeEntity가 상속받은 'createdAt' 필드 검색)
    private static Specification<BomCodeEntity> byCreatedAtBetween(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr, FORMATTER);
        LocalDate endDate = LocalDate.parse(endDateStr, FORMATTER);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        // (수정) between 대신 >= 와 < 조합으로 변경 (더 안전함)
        return (root, query, cb) -> cb.and(
                cb.greaterThanOrEqualTo(root.get("createdAt"), startDateTime), // >= 시작일
                cb.lessThan(root.get("createdAt"), endDateTime)                 // <  종료일+1일
        );
    }

    // 키워드 검색 (BomCodeEntity의 'bomCode' 또는 PartBomEntity의 'partCode', 'partName')
    private static Specification<BomCodeEntity> byKeyword(String keyword) {
        return (root, query, cb) -> {
            String likePattern = "%" + keyword + "%";
            // 3개의 필드에서 OR 검색
            return cb.or(
                    cb.like(root.get("bomCode"), likePattern),
                    cb.like(root.join("part").get("partCode"), likePattern),
                    cb.like(root.join("part").get("partName"), likePattern)
            );
        };
    }

    // --- 모든 조건을 조합하는 메인 메서드 (수정됨) ---
    public static Specification<BomCodeEntity> build(String category, String startDate, String endDate, String keyword) {

        // '항상 참'인 기본 Spec (WHERE 1=1)
        Specification<BomCodeEntity> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (category != null && !category.isEmpty()) {
            spec = spec.and(byCategory(category));
        }

        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            spec = spec.and(byCreatedAtBetween(startDate, endDate));
        }

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and(byKeyword(keyword));
        }

        return spec;
    }
}