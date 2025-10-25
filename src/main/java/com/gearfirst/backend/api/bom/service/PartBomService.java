package com.gearfirst.backend.api.bom.service;

import com.gearfirst.backend.api.bom.dto.PartResponse;
import com.gearfirst.backend.api.bom.entitiy.PartBomEntity;
import com.gearfirst.backend.api.bom.repository.PartBomRepository;
import lombok.RequiredArgsConstructor;
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


}
