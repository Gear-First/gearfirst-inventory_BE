package com.gearfirst.backend.api.bomInfo.spec;

import com.gearfirst.backend.api.bomInfo.entity.BomCodeEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BomSpecification {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    // 각 필드를 검색하는 Specification 메서드들을 정의합니다.

    // 카테고리 검색
    private static Specification<BomCodeEntity> byCategory(String category) {
        // "categoryName" 필드가 category 값과 일치하는지 검사
        return (root, query, cb) -> cb.equal(root.get("categoryName"), category);
    }

    // 날짜 범위 검색 (이전에 논의한 대로 '종료일 + 1일' 미만으로 검색)
    private static Specification<BomCodeEntity> byCreatedAtBetween(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr, FORMATTER);
        LocalDate endDate = LocalDate.parse(endDateStr, FORMATTER);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        return (root, query, cb) -> cb.between(root.get("createdAt"), startDateTime, endDateTime);
    }

    // 키워드 검색 (부품코드 또는 부품명)
    private static Specification<BomCodeEntity> byKeyword(String keyword) {
        return (root, query, cb) -> {
            String likePattern = "%" + keyword + "%";
            // WHERE partCode LIKE %keyword% OR partName LIKE %keyword%
            return cb.or(
                    cb.like(root.get("partCode"), likePattern),
                    cb.like(root.get("partName"), likePattern)
            );
        };
    }

    // --- 모든 조건을 조합하는 메인 메서드 ---
    public static Specification<BomCodeEntity> build(String category, String startDate, String endDate, String keyword) {

        // Specification.where(null)은 빈 쿼리로 시작한다는 의미입니다.
        Specification<BomCodeEntity> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        // 각 파라미터가 비어있지 않은 경우에만 쿼리 조건을 추가(and)합니다.
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