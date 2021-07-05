package br.com.caelum.carangobom.domain;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class Vehicle {

    private Long id;
    private Brand brand;
    private String model;
    private LocalDate year;
    private BigDecimal price;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
