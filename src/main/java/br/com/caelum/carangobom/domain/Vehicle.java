package br.com.caelum.carangobom.domain;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class Vehicle {

    private Long id;
    private Long brandId;
    private String model;
    private String year;
    private BigDecimal price;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
