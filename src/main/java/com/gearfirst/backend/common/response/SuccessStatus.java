package com.gearfirst.backend.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessStatus {
    /** 200 SUCCESS */
    SEND_SAMPLE_SUCCESS(HttpStatus.OK,"샘플 조회 성공"),
    GET_PART_LIST_SUCCESS(HttpStatus.OK,"부품 리스트 조회 성공"),
    GET_MATERIAL_LIST_OF_PART_SUCCESS(HttpStatus.OK,"부품의 자재 리스트 조회 성공"),
    ADD_MATERIAL_SUCCESS(HttpStatus.OK,"자재 등록 성공"),
    DELETE_MATERIAL_SUCCESS(HttpStatus.OK,"자재 삭제 성공"),
    ADD_MATERIAL_OF_PART_SUCCESS(HttpStatus.OK,"부품의 자재 등록 성공"),
    DELETE_MATERIAL_OF_PART_SUCCESS(HttpStatus.OK, "부품의 자재 삭제 성공"),
    ADD_COMPANY_OF_MATERIAL_SUCCESS(HttpStatus.OK,"자재 납품 업체 등록 성공"),
    SELECT_COMPANY_SUCCESS(HttpStatus.OK,"자재 납품 업체 선정 성공"),

    /** 201 CREATED */
    CREATE_SAMPLE_SUCCESS(HttpStatus.CREATED, "샘플 등록 성공"),

    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getStatusCode() {
        return this.httpStatus.value();
    }
}
