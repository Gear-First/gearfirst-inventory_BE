package com.gearfirst.backend.api.bomInfo.service;

import com.gearfirst.backend.api.bom.entitiy.PartBomEntity;
import com.gearfirst.backend.api.bom.repository.PartBomRepository;
import com.gearfirst.backend.api.bomInfo.dto.*;
import com.gearfirst.backend.api.bomInfo.entity.BomCodeEntity;
import com.gearfirst.backend.api.bomInfo.entity.BomInfoEntity;
import com.gearfirst.backend.api.bomInfo.repository.BomCodeRepository;
import com.gearfirst.backend.api.bomInfo.repository.BomInfoRepository;
import com.gearfirst.backend.api.bomInfo.spec.BomSpecification;
import com.gearfirst.backend.api.material.entity.MaterialEntity;
import com.gearfirst.backend.api.material.repository.MaterialRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BomInfoService {
    private final BomInfoRepository bomInfoRepository;
    private final PartBomRepository partBomRepository;
    private final MaterialRepository materialRepository;
    private final BomCodeRepository bomCodeRepository;

    public Page<BomResponse> getBomList(String category, String startDate, String endDate, String keyword, Pageable pageable) {
        // 1. Specification 클래스를 호출하여 동적 쿼리를 생성합니다.
        Specification<BomCodeEntity> spec = BomSpecification.build(category, startDate, endDate, keyword);

        // 2. 리포지토리의 findAll 메서드에 Specification과 Pageable을 전달합니다.
        Page<BomCodeEntity> bomEntityPage = bomCodeRepository.findAll(spec, pageable);

        // 3. Page<Entity>를 Page<Dto>로 변환하여 반환합니다.
        return bomEntityPage.map(this::convertToDto);
    }

    private BomResponse convertToDto(BomCodeEntity entity) {
        BomResponse dto = new BomResponse();
        dto.setBomCodeId(entity.getId());
        dto.setBomCode(entity.getBomCode());
        dto.setCategory(entity.getPart().getCategory());
        dto.setPartCode(entity.getPart().getPartCode());
        dto.setPartName(entity.getPart().getPartName());
        dto.setPartId(entity.getPart().getId());
        dto.setCreatedAt(entity.getCreatedAt().toLocalDate());

        return dto;
    }

    public List<MaterialOfPartResponse> getMaterialList(Long bomCodeId){
        return bomInfoRepository.findByBomCodeId(bomCodeId).stream()
                .map(b -> new MaterialOfPartResponse(
                        b.getMaterial().getMaterialName(),
                        b.getMaterial().getMaterialCode(),
                        b.getMaterial().getPrice(),
                        b.getQuantity()
                )).toList();
    }

    @Transactional
    public void addMaterialList(MaterialOfPartRequest request) {
        PartBomEntity part = partBomRepository.findById(request.getPartId())
                .orElseThrow(() -> new RuntimeException("부품 없음"));

        BomCodeEntity bom = bomCodeRepository.findByPartId(request.getPartId());
        if(bom != null && bom.getBomCode() != null) {
            bomInfoRepository.deleteByBomCodeId(bom.getId());
            bomCodeRepository.delete(bom);
            bomCodeRepository.flush();
        }

        String bomCode = part.getCategory() + part.getPartCode();

        BomCodeEntity bomCodeEntity = BomCodeEntity.builder()
                .bomCode(bomCode)
                .part(part).build();

        bomCodeRepository.save(bomCodeEntity);

        List<BomInfoEntity> bomInfos = request.getMaterialInfos().stream()
                .map(b -> {
                    MaterialEntity material = materialRepository.findById(b.getMaterialId())
                            .orElseThrow(() -> new RuntimeException("자재 없음"));

                    return BomInfoEntity.builder()
                            .bomCode(bomCodeEntity)
                            .material(material)
                            .quantity(b.getQuantity())
                            .build();
                }).toList();

        bomInfoRepository.saveAll(bomInfos);
    }

    @Transactional
    public void deleteMaterialList(MaterialOfPartIdRequest request) {
        List<Long> deleteMaterialIds = request.getMaterialIds();

        BomCodeEntity bom = bomCodeRepository.findByPartId(request.getPartId());

        for (Long materialId : deleteMaterialIds) {
            bomInfoRepository.deleteByBomCodeIdAndMaterialId(bom.getId(), materialId);
        }
    }
}
