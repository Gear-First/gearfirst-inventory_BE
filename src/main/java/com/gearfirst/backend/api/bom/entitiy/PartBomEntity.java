package com.gearfirst.backend.api.bom.entitiy;

import com.gearfirst.backend.api.bomInfo.entity.BomInfoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "part_bom")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartBomEntity {
    @Id
    private Long id;

    private String partName;
    private String partCode;
    private String category;
    private String supplierName;
}
