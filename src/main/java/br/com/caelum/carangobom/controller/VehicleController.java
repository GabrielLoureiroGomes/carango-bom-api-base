package br.com.caelum.carangobom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carangobom/v1/vehicle")
public class VehicleController {

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable  long id) {
        return ResponseEntity.notFound().build();
    }
}
