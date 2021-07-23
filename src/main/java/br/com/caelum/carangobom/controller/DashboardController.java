package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.domain.Dashboard;
import br.com.caelum.carangobom.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carangobom/v1/dashboard")
public class DashboardController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<Dashboard>> fetchDashboard() {
        return new ResponseEntity<>(vehicleService.fetchDashboard(), HttpStatus.OK);
    }

}
