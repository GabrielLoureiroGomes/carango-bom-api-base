package br.com.caelum.carangobom.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class Brand {

    private Long id;
    @NotBlank
    @Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
    private String name;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}