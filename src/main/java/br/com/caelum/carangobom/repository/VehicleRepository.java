package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.Dashboard;
import br.com.caelum.carangobom.domain.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository {

    List<Vehicle> findAll();

    List<Dashboard> fetchDashboard();

    Optional<Vehicle> findById(Long id);

    Optional<Vehicle> create(Vehicle vehicle);

    Optional<Vehicle> update(Long id, Vehicle vehicle);

    void delete(Long id);
}
