package br.com.caelum.carangobom.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VehicleControllerTest {

    @Autowired
    private VehicleController vehicleController;

    @Test
    void testDeleteVehicleFail() {
        ResponseEntity<?> response = vehicleController.delete(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetVehicleFail() {
        ResponseEntity<?> response = vehicleController.getById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
