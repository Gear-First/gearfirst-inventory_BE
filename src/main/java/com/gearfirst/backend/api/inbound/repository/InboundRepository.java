package com.gearfirst.backend.api.inbound.repository;

import com.gearfirst.backend.api.inbound.entity.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundRepository extends JpaRepository<InboundOrder,Long> {
}
