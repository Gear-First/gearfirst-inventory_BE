package com.gearfirst.backend.api.material.service;

import com.gearfirst.backend.api.material.dto.*;
import com.gearfirst.backend.api.material.entity.MaterialEntity;
import com.gearfirst.backend.api.material.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;

    public List<MaterialResponse> getMaterialList() {
        List<MaterialEntity> entities = materialRepository.findAll();

        return entities.stream().map(e ->
                new MaterialResponse(e.getMaterialName(), e.getMaterialCode())).toList();
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
                        saved.getMaterialName(),
                        saved.getMaterialCode()
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
                        entity.getMaterialName(),
                        entity.getMaterialCode()
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
