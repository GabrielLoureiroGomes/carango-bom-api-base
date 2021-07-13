package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.domain.Vehicle;
import br.com.caelum.carangobom.exception.VehicleNotFoundException;
import br.com.caelum.carangobom.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BrandService brandService;

    public List<Vehicle> findAll() {
        return this.vehicleRepository.findAll();
    }

    public Vehicle findById(Long id) throws VehicleNotFoundException {
        Optional<Vehicle> vehicle = this.vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            return vehicle.get();
        } else {
            throw new VehicleNotFoundException("Veículo não encontrado!");
        }
    }

    public Vehicle create(Vehicle vehicle) {
        return this.vehicleRepository.create(vehicle);
    }

    public void delete(Long id) throws VehicleNotFoundException {
        Optional<Vehicle> vehicle = this.vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            this.vehicleRepository.delete(vehicle.get().getId());
        } else {
            throw new VehicleNotFoundException("Veículo não encontrado!");
        }
    }

    public Vehicle update(Long id, Vehicle vehicle) throws VehicleNotFoundException {
        Optional<Vehicle> optionalVehicle = this.vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            if (!vehicle.getBrandId().equals(optionalVehicle.get().getBrandId())) {
                this.brandService.findById(vehicle.getBrandId());
            }
            return this.vehicleRepository.update(vehicle);
        } else {
            throw new VehicleNotFoundException("Veículo não encontrado!");
        }
    }
}
