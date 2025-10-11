package com.gearfirst.backend.api.outbound.service;


import com.gearfirst.backend.api.outbound.dto.OutboundOrderCreateRequest;
import com.gearfirst.backend.api.outbound.dto.OutboundOrderResponse;
import com.gearfirst.backend.api.outbound.entity.OutboundOrder;

public interface OutboundService {
    OutboundOrderResponse createOutboundOrder(OutboundOrderCreateRequest request);
}
