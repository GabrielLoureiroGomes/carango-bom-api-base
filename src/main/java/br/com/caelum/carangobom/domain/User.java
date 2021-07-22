package br.com.caelum.carangobom.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
