package br.com.caelum.carangobom.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    private Long id;
    private Long brandId;
    private String model;
    private String year;
    private Integer price;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
