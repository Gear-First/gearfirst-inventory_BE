package com.gearfirst.backend.api.material.service;

import com.gearfirst.backend.api.material.dto.*;
import com.gearfirst.backend.api.material.entity.MaterialEntity;
import com.gearfirst.backend.api.material.repository.MaterialRepository;
import com.gearfirst.backend.api.material.spec.MaterialSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;

    public Page<MaterialResponse> getMaterialList(String startDate, String endDate, String keyword, Pageable pageable) {
        // 1. Specification 클래스를 호출하여 동적 쿼리를 생성합니다.
        Specification<MaterialEntity> spec = MaterialSpecification.build(startDate, endDate, keyword);

        // 2. 리포지토리의 findAll 메서드에 Specification과 Pageable을 전달합니다.
        Page<MaterialEntity> materialEntityPage = materialRepository.findAll(spec, pageable);

        // 3. Page<Entity>를 Page<Dto>로 변환하여 반환합니다.
        return materialEntityPage.map(this::convertToDto);
    }

    private MaterialResponse convertToDto(MaterialEntity entity) {
        MaterialResponse dto = new MaterialResponse();
        dto.setId(entity.getId());
        dto.setMaterialCode(entity.getMaterialCode());
        dto.setMaterialName(entity.getMaterialName());

        return dto;
    }

    public RegistrationResponse addMaterial(List<MaterialRequest> materialRequests) {
        RegistrationResponse response = new RegistrationResponse();
        response.setTotalCount(materialRequests.size());

        List<MaterialResponse> successList = new ArrayList<>();
        List<SkippedMaterial> skippedList = new ArrayList<>();
        List<FailedMaterial> failedList = new ArrayList<>();

        for(MaterialRequest req : materialRequests) {
            try {
                if(materialRepository.existsByMaterialCode(req.getMaterialCode())) {
                    skippedList.add(new SkippedMaterial(
                            req.getMaterialCode(), req.getMaterialName(), "이미 존재하는 자재입니다."
                    ));
                    continue;
                }

                MaterialEntity entity = new MaterialEntity();
                entity.setMaterialName(req.getMaterialName());
                entity.setMaterialCode(req.getMaterialCode());

                MaterialEntity saved = materialRepository.save(entity);

                successList.add(new MaterialResponse(
                        saved.getId(),
                        saved.getMaterialName(),
                        saved.getMaterialCode(),
                        saved.getCreatedAt().toLocalDate()
                ));
            } catch (Exception e) {
                failedList.add(new FailedMaterial(req, e.getMessage()));
            }
        }

        response.setSuccessList(successList);
        response.setSkippedList(skippedList);
        response.setFailedList(failedList);
        response.setSuccessCount(successList.size());
        response.setSkippedCount(skippedList.size());
        response.setFailedCount(failedList.size());

        return response;
    }

    public RegistrationResponse deleteMaterial(List<MaterialRequest> materialRequests) {
        RegistrationResponse response = new RegistrationResponse();
        response.setTotalCount(materialRequests.size());

        List<MaterialResponse> successList = new ArrayList<>();
        List<SkippedMaterial> skippedList = new ArrayList<>();
        List<FailedMaterial> failedList = new ArrayList<>();

        for(MaterialRequest req : materialRequests) {
            try {
                if(!materialRepository.existsByMaterialCode(req.getMaterialCode())) {
                    skippedList.add(new SkippedMaterial(
                            req.getMaterialCode(), req.getMaterialName(), "존재하지 않는 자재입니다."
                    ));
                    continue;
                }

                MaterialEntity entity = new MaterialEntity();
                entity.setMaterialName(req.getMaterialName());
                entity.setMaterialCode(req.getMaterialCode());

                materialRepository.delete(entity);

                successList.add(new MaterialResponse(
                        entity.getId(),
                        entity.getMaterialName(),
                        entity.getMaterialCode(),
                        entity.getCreatedAt().toLocalDate()
                ));
            } catch (Exception e) {
                failedList.add(new FailedMaterial(req, e.getMessage()));
            }
        }

        response.setSuccessList(successList);
        response.setSkippedList(skippedList);
        response.setFailedList(failedList);
        response.setSuccessCount(successList.size());
        response.setSkippedCount(skippedList.size());
        response.setFailedCount(failedList.size());

        return response;
    }
}
