package com.gearfirst.backend.api.outbound.controller.internal;

import com.gearfirst.backend.api.outbound.dto.OutboundOrderCreateRequest;
import com.gearfirst.backend.api.outbound.dto.OutboundOrderResponse;
import com.gearfirst.backend.api.outbound.service.OutboundService;
import com.gearfirst.backend.common.response.ApiResponse;
import com.gearfirst.backend.common.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/outbounds")
@Tag(name = "Outbound", description = "출고 명령 API")
public class OutboundInternalController {
    private final OutboundService outboundService;

    @Operation(summary="출고 명령 생성", description = "본사가 승인된 발주서를 기반으로 창고에 출고 명령을 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<OutboundOrderResponse>> createOutboundOrder(
            @RequestBody OutboundOrderCreateRequest request
    ){
        OutboundOrderResponse response = outboundService.createOutboundOrder(request);
        return ApiResponse.success(SuccessStatus.CREATE_OUTBOUND_ORDER_TO_WAREHOUSE_SUCCESS, response);
    }
}
