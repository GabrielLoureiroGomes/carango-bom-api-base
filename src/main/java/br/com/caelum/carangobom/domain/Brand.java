package br.com.caelum.carangobom.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Brand {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}