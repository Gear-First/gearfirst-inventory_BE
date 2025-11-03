package com.gearfirst.backend.api.material.service;

import com.gearfirst.backend.api.material.dto.*;
import com.gearfirst.backend.api.material.entity.CompanyEntity;
import com.gearfirst.backend.api.material.entity.CompanySequence;
import com.gearfirst.backend.api.material.entity.MaterialEntity;
import com.gearfirst.backend.api.material.repository.CompanyRepository;
import com.gearfirst.backend.api.material.repository.CompanySequenceRepository;
import com.gearfirst.backend.api.material.repository.MaterialRepository;
import com.gearfirst.backend.api.material.spec.CompanySpecification;
import com.gearfirst.backend.api.material.spec.MaterialSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final CompanySequenceRepository companySequenceRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public void selectCompany(List<SelectedCompanyRequest> request) {
        for(SelectedCompanyRequest sel : request) {
            CompanyEntity entity = companyRepository.findById(sel.getId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 업체가 없습니다."));

            entity.setStatus(true);
            entity.setOrderCnt(sel.getOrderCnt());
            entity.setTotalPrice(sel.getTotalPrice());

            companyRepository.save(entity);
        }
    }

    public void addCompany(CompanyRequest request) {
        MaterialEntity entity = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new EntityNotFoundException("해당 자재가 존재하지 않습니다."));

        String registId = generateWithRetry();

        CompanyEntity company = CompanyEntity.builder()
                .material(entity)
                .registNum(registId)
                .companyName(request.getCompanyName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .surveyDate(request.getSurveyDate())
                .spentDay(request.getSpentDay())
                .untilDate(request.getUntilDate())
                .build();

        try {
            int total_quantity = entity.getQuantity() + (company.getQuantity() / company.getSpentDay());
            int total_price = entity.getPrice() * entity.getQuantity() + company.getPrice() * (company.getQuantity() / company.getSpentDay());
            int per_price = total_price / total_quantity;

            entity.setPrice(per_price);
            entity.setQuantity(total_quantity);
            entity.getCompanies().add(company);
            materialRepository.save(entity);
        } catch (Exception e) {
            throw new ArithmeticException("값이 0 입니다.");
        }
    }

    public String generateWithRetry() {
        int maxRetry = 3;
        for (int i = 0; i < maxRetry; i++) {
            try {
                return generateRegistNumId();
            } catch (OptimisticLockException e) {
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
        }
        throw new RuntimeException("동시 처리 충돌로 ID 생성 실패");
    }

    @Transactional
    public String generateRegistNumId() {
        String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        CompanySequence sequence = companySequenceRepository
                .findByDatePart(datePart)
                .orElseGet(() -> companySequenceRepository.save(
                        CompanySequence.builder()
                                .datePart(datePart)
                                .sequence(0)
                                .build()
                ));

        // 낙관적 락은 여기서 동작: version 확인 후 +1
        sequence.setSequence(sequence.getSequence() + 1);

        // 버전 충돌 시 OptimisticLockException 발생
        companySequenceRepository.save(sequence);

        String status = "RG";

        String formattedSeq = String.format("%03d", sequence.getSequence());
        return String.format("%s-%s-%s", status, datePart, formattedSeq);
    }

    public Page<MaterialResponse> getMaterialList(String startDate, String endDate, String keyword, Pageable pageable) {
        // 1. Specification 클래스를 호출하여 동적 쿼리를 생성합니다.
        Specification<MaterialEntity> spec = MaterialSpecification.build(startDate, endDate, keyword);

        // 2. 리포지토리의 findAll 메서드에 Specification과 Pageable을 전달합니다.
        Page<MaterialEntity> materialEntityPage = materialRepository.findAll(spec, pageable);

        // 3. Page<Entity>를 Page<Dto>로 변환하여 반환합니다.
        return materialEntityPage.map(this::convertToDto);
    }

    public Page<CompanyResponse> getCompanyList(String endDate, String company, String material, Boolean isSelected, Pageable pageable) {
        // 1. Specification 클래스를 호출하여 동적 쿼리를 생성합니다.
        Specification<CompanyEntity> spec = CompanySpecification.build(endDate, company, material, isSelected);

        // 2. 리포지토리의 findAll 메서드에 Specification과 Pageable을 전달합니다.
        Page<CompanyEntity> companyEntityPage = companyRepository.findAll(spec, pageable);

        // 3. Page<Entity>를 Page<Dto>로 변환하여 반환합니다.
        return companyEntityPage.map(this::convertToDto);
    }

    private CompanyResponse convertToDto(CompanyEntity entity) {
        return CompanyResponse.builder()
                .materialName(entity.getMaterial().getMaterialName())
                .materialCode(entity.getMaterial().getMaterialCode())
                .spendDay(entity.getSpentDay())
                .companyId(entity.getId())
                .orderCnt(entity.getOrderCnt())
                .companyName(entity.getCompanyName())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .registNum(entity.getRegistNum())
                .surveyDate(entity.getSurveyDate())
                .untilDate(entity.getUntilDate())
                .build();
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
                if(materialRepository.existsById(req.getMaterialId())) {
                    skippedList.add(new SkippedMaterial(
                            req.getMaterialId(), req.getMaterialCode(), req.getMaterialName(), "이미 존재하는 자재입니다."
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

    public RegistrationResponse deleteMaterial(List<MaterialRequest> requests) {
        RegistrationResponse response = new RegistrationResponse();
        response.setTotalCount(requests.size());

        List<MaterialResponse> successList = new ArrayList<>();
        List<SkippedMaterial> skippedList = new ArrayList<>();
        List<FailedMaterial> failedList = new ArrayList<>();

        for(MaterialRequest reqId : requests) {
            try {
                if(!materialRepository.existsById(reqId.getMaterialId())) {
                    skippedList.add(new SkippedMaterial(
                            reqId.getMaterialId(), reqId.getMaterialName(), reqId.getMaterialCode(), "존재하지 않는 자재입니다."
                    ));
                    continue;
                }

                MaterialEntity entity = materialRepository.findById(reqId.getMaterialId())
                                .orElseThrow(() -> new EntityNotFoundException("해당 자제를 찾을 수 없습니다."));

                System.out.println(entity.getCreatedAt());
                System.out.println(entity.getMaterialName());

                materialRepository.delete(entity);

                successList.add(new MaterialResponse(
                        entity.getId(),
                        entity.getMaterialName(),
                        entity.getMaterialCode(),
                        entity.getCreatedAt().toLocalDate()
                ));
            } catch (Exception e) {
                failedList.add(new FailedMaterial(reqId, e.getMessage()));
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
