package br.com.caelum.carangobom.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class User {

    private Long id;
    private String name;
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
