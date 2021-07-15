package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.domain.Vehicle;
import br.com.caelum.carangobom.mocks.VehicleMocks;
import br.com.caelum.carangobom.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    private VehicleMocks vehicleMocks = new VehicleMocks();

    @Test
    void testVehicleGetAll() throws Exception {
        List<Vehicle> listVehicles = vehicleMocks.getListVehicles();
        when(vehicleService.findAll()).thenReturn(listVehicles);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/carangobom/v1/vehicle").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(listVehicles.size())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testVehicleGetById() throws Exception {
        Vehicle vehicle = vehicleMocks.getCorsa().get();
        when(vehicleService.findById(vehicle.getId())).thenReturn(vehicle);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/carangobom/v1/vehicle/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(vehicle.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

//    @Test
//    public void testVehicleGetByIdNotFound() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/carangobom/v1/vehicle/1")).andExpect(status().isOk());
//    }

    @Test
    void testVehicleCreate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/carangobom/v1/vehicle")).andExpect(status().isCreated());
    }

//    @Test
//    public void testVehicleCreateBrandNotFound() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/carangobom/v1/vehicle")).andExpect(status().isCreated());
//    }

    @Test
    void testVehicleUpdate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/carangobom/v1/vehicle/1")).andExpect(status().isOk());
    }

//    @Test
//    public void testVehicleUpdateNotFound() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/carangobom/v1/vehicle/1")).andExpect(status().isOk());
//    }

    @Test
    void testVehicleDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/carangobom/v1/vehicle/1")).andExpect(status().isOk());
    }

//    @Test
//    public void testVehicleDeleteNotFound() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/carangobom/v1/vehicle/1")).andExpect(status().isOk());
//    }

}
