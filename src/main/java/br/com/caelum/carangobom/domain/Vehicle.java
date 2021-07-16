package br.com.caelum.carangobom.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
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
