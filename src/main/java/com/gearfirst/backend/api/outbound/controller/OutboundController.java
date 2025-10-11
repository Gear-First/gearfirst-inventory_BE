package com.gearfirst.backend.api.outbound.controller;

import com.gearfirst.backend.api.outbound.service.OutboundService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/outbound")
@RequiredArgsConstructor
@Tag(name ="Outbound", description = "출고 명령 API")
public class OutboundController {
    private OutboundService outboundService;

}
