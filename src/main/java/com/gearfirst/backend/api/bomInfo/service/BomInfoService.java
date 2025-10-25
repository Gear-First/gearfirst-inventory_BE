package com.gearfirst.backend.api.bomInfo.service;

import com.gearfirst.backend.api.bom.entitiy.PartBomEntity;
import com.gearfirst.backend.api.bom.repository.PartBomRepository;
import com.gearfirst.backend.api.bomInfo.dto.MaterialInfo;
import com.gearfirst.backend.api.bomInfo.dto.MaterialOfPartIdRequest;
import com.gearfirst.backend.api.bomInfo.dto.MaterialOfPartRequest;
import com.gearfirst.backend.api.bomInfo.dto.MaterialOfPartResponse;
import com.gearfirst.backend.api.bomInfo.entity.BomInfoEntity;
import com.gearfirst.backend.api.bomInfo.repository.BomInfoRepository;
import com.gearfirst.backend.api.material.entity.MaterialEntity;
import com.gearfirst.backend.api.material.repository.MaterialRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BomInfoService {
    private final BomInfoRepository bomInfoRepository;
    private final PartBomRepository partBomRepository;
    private final MaterialRepository materialRepository;

    public List<MaterialOfPartResponse> getMaterialList(Long partId){
        return bomInfoRepository.findByPartId(partId).stream()
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

        bomInfoRepository.deleteByPartId(request.getPartId());

        List<BomInfoEntity> bomInfos = request.getMaterialInfos().stream()
                .map(b -> {
                    MaterialEntity material = materialRepository.findById(b.getMaterialId())
                            .orElseThrow(() -> new RuntimeException("자재 없음"));

                    return BomInfoEntity.builder()
                            .part(part)
                            .material(material)
                            .quantity(b.getQuantity())
                            .build();
                }).toList();

        bomInfoRepository.saveAll(bomInfos);
    }

    @Transactional
    public void deleteMaterialList(MaterialOfPartIdRequest request) {
        List<Long> deleteMaterialIds = request.getMaterialIds();

        for (Long materialId : deleteMaterialIds) {
            bomInfoRepository.deleteByPartIdAndMaterialId(request.getPartId(), materialId);
        }
    }
}
