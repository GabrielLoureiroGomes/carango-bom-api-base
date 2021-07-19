package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.controller.request.CreateVehicleRequest;
import br.com.caelum.carangobom.domain.Vehicle;
import br.com.caelum.carangobom.exception.VehicleNotFoundException;
import br.com.caelum.carangobom.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/carangobom/v1/vehicle")
@ResponseBody
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAll() {
        return new ResponseEntity<>(vehicleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable Long id) throws VehicleNotFoundException {
        var vehicle = vehicleService.findById(id);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vehicle> create(@Valid @RequestBody CreateVehicleRequest body) {
        Vehicle vehicle = vehicleService.create(body.toVehicle());
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable Long id, @Valid @RequestBody CreateVehicleRequest body) throws VehicleNotFoundException {
        Vehicle vehicle = vehicleService.update(id, body.toVehicle());
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) throws VehicleNotFoundException {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
