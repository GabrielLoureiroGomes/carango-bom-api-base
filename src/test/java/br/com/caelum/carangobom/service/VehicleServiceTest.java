package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.domain.Vehicle;
import br.com.caelum.carangobom.exception.BrandNotFoundException;
import br.com.caelum.carangobom.exception.VehicleNotFoundException;
import br.com.caelum.carangobom.mocks.VehicleMocks;
import br.com.caelum.carangobom.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private BrandService brandService;

    private final VehicleMocks vehicleMocks = new VehicleMocks();

    @BeforeEach
    public void getMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldRetrieveEmptyList() {
        when(vehicleRepository.findAll()).thenReturn(new ArrayList<Vehicle>());
        List<Vehicle> vehicles = vehicleService.findAll();

        assertEquals(0, vehicles.size());
    }

    @Test
    void testShouldRetrieveAllVehicles() {
        when(vehicleRepository.findAll()).thenReturn(vehicleMocks.getListVehicles());
        List<Vehicle> vehicles = vehicleService.findAll();

        assertEquals(vehicleMocks.getListVehicles().size(), vehicles.size());
    }

    @Test
    void testShouldRetrieveVehicleById() {
        Long id = 1L;
        when(vehicleRepository.findById(id)).thenReturn(vehicleMocks.getCorsa());

        Vehicle vehicle = assertDoesNotThrow(() -> vehicleService.findById(id));
        assertEquals(id, vehicle.getId());
    }

    @Test
    void testShouldRetrieveVehicleByIdNotFound() {
        Long id = 4L;
        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.findById(id));
    }

    @Test
    void testShouldCreateVehicle() {
        when(vehicleRepository.create(any())).thenReturn(vehicleMocks.getUno().get());
        Vehicle vehicle = assertDoesNotThrow(() -> vehicleService.create(vehicleMocks.getUno().get()));

        assertNotNull(vehicle);
        assertEquals(vehicleMocks.getUno().get().getId(), vehicle.getId());
        assertEquals(vehicleMocks.getUno().get().getModel(), vehicle.getModel());
    }

    @Test
    void testShouldCreateVehicleBrandNotFound() {
        when(brandService.findBrandById(Mockito.anyLong())).thenThrow(BrandNotFoundException.class);

        assertThrows(Exception.class, () -> vehicleService.create(vehicleMocks.getUno().get()));
    }

    @Test
    void testShouldDeleteVehicle() {
        Long id = 1L;
        when(vehicleRepository.findById(id)).thenReturn(vehicleMocks.getCorsa());
        when(vehicleRepository.findAll()).thenReturn(vehicleMocks.getListVehicles().subList(1, vehicleMocks.getListVehicles().size()));

        assertDoesNotThrow(() -> vehicleService.delete(id));
        List<Vehicle> vehicles = vehicleService.findAll();
        assertEquals(vehicleMocks.getListVehicles().size() - 1, vehicles.size());
        assertEquals(0, vehicles.stream().filter(v -> v.getId().equals(id)).collect(Collectors.toList()).size());
    }

    @Test
    void testShouldDeleteVehicleNotFound() {
        Long id = 4L;
        when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.delete(id));
    }

    @Test
    void testShouldUpdateVehicle() {
        Vehicle vehicleModified = vehicleMocks.getCorsa().get();
        vehicleModified.setModel("Celta");
        when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(vehicleMocks.getCorsa());
        when(vehicleRepository.update(Mockito.any())).thenReturn(vehicleModified);

        Vehicle vehicleUpdated = assertDoesNotThrow(() -> vehicleService.update(vehicleModified.getId(), vehicleModified));
        assertEquals(vehicleModified.getId(), vehicleUpdated.getId());
        assertEquals(vehicleModified.getModel(), vehicleUpdated.getModel());
    }

    @Test
    void testShouldUpdateVehicleNotFound() {
        Vehicle vehicle = vehicleMocks.getCorsa().get();
        when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.update(vehicle.getId(), vehicle));
    }

    @Test
    void testShouldUpdateVehicleBrandNotFound() {
        Long brandId = 5L;
        Vehicle vehicleModified = vehicleMocks.getCorsa().get();
        vehicleModified.setModel("BMW3");
        vehicleModified.setBrandId(brandId);
        when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(vehicleMocks.getCorsa());
        when(brandService.findBrandById(brandId)).thenThrow(BrandNotFoundException.class);

        assertThrows(Exception.class, () -> vehicleService.update(vehicleModified.getId(), vehicleModified));
    }

}