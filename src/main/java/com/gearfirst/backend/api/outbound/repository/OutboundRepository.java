package com.gearfirst.backend.api.outbound.repository;

import com.gearfirst.backend.api.outbound.entity.OutboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundRepository extends JpaRepository<OutboundOrder, Long> {
}
