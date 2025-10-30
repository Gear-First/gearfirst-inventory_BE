package com.gearfirst.backend.api.material.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "receipt_sequence")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CompanySequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String datePart;
    private int sequence;

    @Version
    private Long version;
}
