package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.Vehicle;

import java.util.List;

public interface VehicleRepository {

    List<Vehicle> findAll();

    Vehicle findById(Long id);

    Vehicle create(Vehicle vehicle);

    Vehicle update(Vehicle vehicle);

    void delete(Long id);
}
