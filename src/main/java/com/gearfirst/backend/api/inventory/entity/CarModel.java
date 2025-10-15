package com.gearfirst.backend.api.inventory.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name ="car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_model_id")
    private Long carModelId;

    @Column(name="model_name", nullable = false)
    private String model_name;                 //모델명

    @Column(nullable = false)
    private String manufacturer;                //제조사

    @Column(name="release_year", nullable = false)
    private String releaseYear;                 //출시 연도

    @Column(name="engine_type")
    private String engineType;                  //엔진타입

    @Column(name="fuel_type")
    private String fuelType;                    //연료 종류

    private String transmission;                //변속기 타입

    @Column(nullable = false)
    private boolean active;                     //사용가능 여부

}
