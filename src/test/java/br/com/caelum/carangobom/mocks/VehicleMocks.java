package br.com.caelum.carangobom.mocks;

import br.com.caelum.carangobom.domain.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class VehicleMocks {

    private final BrandMocks brandMocks = new BrandMocks();

    public Optional<Vehicle> getCorsa() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setBrandId(brandMocks.getChevrolet().getId());
        vehicle.setModel("Corsa");
        vehicle.setPrice(new BigDecimal("23000.00"));
        vehicle.setYear("2012");
        vehicle.setCreatedAt(LocalDate.now());
        vehicle.setUpdatedAt(null);
        return Optional.of(vehicle);
    }

    public Optional<Vehicle> getA3() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(2L);
        vehicle.setBrandId(brandMocks.getAudi().getId());
        vehicle.setModel("A3");
        vehicle.setPrice(new BigDecimal("150000.00"));
        vehicle.setYear("2020");
        vehicle.setCreatedAt(LocalDate.now());
        vehicle.setUpdatedAt(null);
        return Optional.of(vehicle);
    }

    public Optional<Vehicle> getUno() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(3L);
        vehicle.setBrandId(brandMocks.getFiat().getId());
        vehicle.setModel("Uno");
        vehicle.setPrice(new BigDecimal("30000.00"));
        vehicle.setYear("2018");
        vehicle.setCreatedAt(LocalDate.now());
        vehicle.setUpdatedAt(null);
        return Optional.of(vehicle);
    }

    public List<Vehicle> getListVehicles() {
        return Arrays.asList(getCorsa().get(), getA3().get(), getUno().get());
    }
}
