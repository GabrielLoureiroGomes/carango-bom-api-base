package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.domain.Dashboard;
import br.com.caelum.carangobom.domain.Vehicle;
import br.com.caelum.carangobom.exception.BrandNotFoundException;
import br.com.caelum.carangobom.exception.BusinessException;
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

    public List<Dashboard> fetchDashboard() {
        return vehicleRepository.fetchDashboard();
    }

    public Vehicle findById(Long id) throws VehicleNotFoundException {
        Optional<Vehicle> vehicle = this.vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            return vehicle.get();
        } else {
            throw new VehicleNotFoundException(id.toString());
        }
    }

    public Vehicle create(Vehicle vehicle) throws BrandNotFoundException {
        this.brandService.findBrandById(vehicle.getBrandId());

        return this.vehicleRepository.create(vehicle).orElseThrow(BusinessException::new);
    }

    public void delete(Long id) throws VehicleNotFoundException {
        Optional<Vehicle> vehicle = this.vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            this.vehicleRepository.delete(vehicle.get().getId());
        } else {
            throw new VehicleNotFoundException(id.toString());
        }
    }

    public Vehicle update(Long id, Vehicle vehicle) throws VehicleNotFoundException, BrandNotFoundException {
        Optional<Vehicle> optionalVehicle = this.vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            if (!vehicle.getBrandId().equals(optionalVehicle.get().getBrandId())) {
                this.brandService.findBrandById(vehicle.getBrandId());
            }
            return this.vehicleRepository.update(id, vehicle).orElseThrow(BusinessException::new);
        } else {
            throw new VehicleNotFoundException(id.toString());
        }
    }
}
