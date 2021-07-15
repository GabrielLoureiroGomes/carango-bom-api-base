package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository {
    @Override
    public List<Vehicle> findAll() {
        return null;
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        return null;
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
