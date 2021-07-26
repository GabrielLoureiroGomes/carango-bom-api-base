package br.com.caelum.carangobom.requests;

import br.com.caelum.carangobom.domain.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class CreateVehicleRequest {
    @NotBlank @NonNull
    String model;

    @NotNull @Positive(message="O preço do veículo deve ser positivo") @Min(message="O valor mínimo de um veículo é 100", value=100)
    Integer price;

    @NotNull
    String year;

    @NotNull @Positive
    Long brandId;

    public Vehicle toVehicle() {
        return new Vehicle(
                null,
                brandId,
                null,
                model,
                year,
                price,
                null,
                null
        );
    }

}
