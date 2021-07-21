package br.com.caelum.carangobom.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Brand {

    private Long id;
    private String name;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}