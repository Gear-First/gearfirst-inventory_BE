package com.gearfirst.backend.api.bom.service;

import com.gearfirst.backend.api.bom.dto.PartDto;
import com.gearfirst.backend.api.bom.dto.PartResponse;
import com.gearfirst.backend.api.bom.entitiy.PartBomEntity;
import com.gearfirst.backend.api.bom.repository.PartBomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartBomService {
    private final PartBomRepository partBomRepository;

    public List<PartResponse> getPartList() {
        List<PartBomEntity> entities = partBomRepository.findAll();

        return entities.stream().map(e -> new PartResponse(e.getId(), e.getPartName(), e.getPartCode())).toList();
    }

    @KafkaListener(
            topics = "create-part",
            groupId = "inventory-group",
            containerFactory = "partKafkaListenerContainerFactory"
    )
    public void handlePartCreated(PartDto partDto, Acknowledgment acknowledgment) {
        System.out.println("주문 완료 메시지 수신: " + partDto.getId() + " " + partDto.getPartName());

        try {
            PartBomEntity partBom = PartBomEntity.builder()
                    .id(partDto.getId())
                    .partCode(partDto.getPartCode())
                    .partName(partDto.getPartName())
                    .category(partDto.getCategory())
                    .supplierName(partDto.getSupplierName())
                    .build();

            partBomRepository.save(partBom);

            acknowledgment.acknowledge();
        } catch (Exception e) {
            // 3. 로직이 실패하면, Exception을 던집니다. (ack.acknowledge()는 호출되지 않음)
            // Exception이 발생하면, 2단계에서 설정한 `DefaultErrorHandler`가
            // 이 이벤트를 감지하여 재시도 및 DLQ 전송을 자동으로 처리합니다.
            throw new RuntimeException("재고 처리 중 오류 발생", e);
        }
    }
}
