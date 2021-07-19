package br.com.caelum.carangobom.mocks;

import br.com.caelum.carangobom.domain.Vehicle;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class VehicleMocks {

    public Optional<Vehicle> getCorsa() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setBrandId(BrandMocks.getChevrolet().getId());
        vehicle.setModel("Corsa");
        vehicle.setPrice(23000);
        vehicle.setYear("2012");
        vehicle.setCreatedAt(LocalDate.now());
        vehicle.setUpdatedAt(null);
        return Optional.of(vehicle);
    }

    public Optional<Vehicle> getA3() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(2L);
        vehicle.setBrandId(BrandMocks.getAudi().getId());
        vehicle.setModel("A3");
        vehicle.setPrice(150000);
        vehicle.setYear("2020");
        vehicle.setCreatedAt(LocalDate.now());
        vehicle.setUpdatedAt(null);
        return Optional.of(vehicle);
    }

    public Optional<Vehicle> getUno() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(3L);
        vehicle.setBrandId(BrandMocks.getFiat().getId());
        vehicle.setModel("Uno");
        vehicle.setPrice(30000);
        vehicle.setYear("2018");
        vehicle.setCreatedAt(LocalDate.now());
        vehicle.setUpdatedAt(null);
        return Optional.of(vehicle);
    }

    public List<Vehicle> getListVehicles() {
        return Arrays.asList(getCorsa().get(), getA3().get(), getUno().get());
    }
}
