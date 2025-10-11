package com.gearfirst.backend.api.outbound.repository;

import com.gearfirst.backend.api.outbound.entity.OutboundItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundItemRepository extends JpaRepository<OutboundItem,Long> {
}
