package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository {

    List<Vehicle> findAll();

    Optional<Vehicle> findById(Long id);

    Vehicle create(Vehicle vehicle);

    Vehicle update(Vehicle vehicle);

    void delete(Long id);
}
