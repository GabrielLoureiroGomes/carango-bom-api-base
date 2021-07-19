package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.controller.request.CreateVehicleRequest;
import br.com.caelum.carangobom.domain.Vehicle;
import br.com.caelum.carangobom.exception.BrandNotFoundException;
import br.com.caelum.carangobom.exception.VehicleNotFoundException;
import br.com.caelum.carangobom.mocks.VehicleMocks;
import br.com.caelum.carangobom.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleService vehicleService;

    private final VehicleMocks vehicleMocks = new VehicleMocks();

    @Test
    void testVehicleGetAll() throws Exception {
        List<Vehicle> listVehicles = vehicleMocks.getListVehicles();
        when(vehicleService.findAll()).thenReturn(listVehicles);
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/carangobom/v1/vehicle")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$", hasSize(listVehicles.size())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testVehicleGetById() throws Exception {
        Vehicle vehicle = vehicleMocks.getCorsa().get();
        when(vehicleService.findById(vehicle.getId())).thenReturn(vehicle);
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/carangobom/v1/vehicle/{id}", vehicle.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").value(vehicle.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testVehicleGetByIdNotFound() throws Exception {
        when(vehicleService.findById(Mockito.anyLong())).thenThrow(VehicleNotFoundException.class);
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/carangobom/v1/vehicle/{id}", 5L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof VehicleNotFoundException))
                .andDo(print());
    }

    @Test
    void testVehicleCreate() throws Exception {
        Vehicle vehicle = vehicleMocks.getCorsa().get();
        CreateVehicleRequest createVehicleRequest = new CreateVehicleRequest(vehicle.getModel(), vehicle.getPrice(), vehicle.getYear(), vehicle.getBrandId());

        when(vehicleService.create(Mockito.any())).thenReturn(vehicle);
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/carangobom/v1/vehicle")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createVehicleRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.model").value(vehicle.getModel()))
                .andDo(print());
    }

    @Test
    void testVehicleCreateBrandNotFound() throws Exception {
        Vehicle vehicle = vehicleMocks.getCorsa().get();
        CreateVehicleRequest createVehicleRequest = new CreateVehicleRequest(vehicle.getModel(), vehicle.getPrice(), vehicle.getYear(), vehicle.getBrandId());

        when(vehicleService.create(Mockito.any())).thenThrow(BrandNotFoundException.class);
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/carangobom/v1/vehicle")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createVehicleRequest))
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BrandNotFoundException))
                .andDo(print());
    }


    @Test
    void testVehicleUpdate() throws Exception {
        Vehicle vehicle = vehicleMocks.getCorsa().get();
        Integer newPrice = vehicle.getPrice() + 10000;
        vehicle.setPrice(newPrice);
        CreateVehicleRequest createVehicleRequest = new CreateVehicleRequest(vehicle.getModel(), vehicle.getPrice(), vehicle.getYear(), vehicle.getBrandId());

        when(vehicleService.update(Mockito.anyLong(), Mockito.any())).thenReturn(vehicle);
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/carangobom/v1/vehicle/{id}", vehicle.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createVehicleRequest))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.model").value(vehicle.getModel()))
                .andExpect(jsonPath("$.price").value(vehicle.getPrice()))
                .andDo(print());
    }

    @Test
    void testVehicleUpdateNotFound() throws Exception {
        Vehicle vehicle = vehicleMocks.getCorsa().get();
        Integer newPrice = vehicle.getPrice() + 10000;
        vehicle.setPrice(newPrice);
        CreateVehicleRequest createVehicleRequest = new CreateVehicleRequest(vehicle.getModel(), vehicle.getPrice(), vehicle.getYear(), vehicle.getBrandId());

        when(vehicleService.update(Mockito.anyLong(), Mockito.any())).thenThrow(VehicleNotFoundException.class);
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/carangobom/v1/vehicle/{id}", vehicle.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createVehicleRequest))
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof VehicleNotFoundException))
                .andDo(print());
    }

    @Test
    void testVehicleUpdateBrandNotFound() throws Exception {
        Vehicle vehicle = vehicleMocks.getCorsa().get();
        Integer newPrice = vehicle.getPrice() + 10000;
        vehicle.setPrice(newPrice);
        CreateVehicleRequest createVehicleRequest = new CreateVehicleRequest(vehicle.getModel(), vehicle.getPrice(), vehicle.getYear(), vehicle.getBrandId());

        when(vehicleService.update(Mockito.anyLong(), Mockito.any())).thenThrow(BrandNotFoundException.class);
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/carangobom/v1/vehicle/{id}", vehicle.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createVehicleRequest))
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BrandNotFoundException))
                .andDo(print());
    }


    @Test
    void testVehicleDelete() throws Exception {
        Long id = 1L;
        when(vehicleService.findById(id)).thenReturn(vehicleMocks.getCorsa().get());
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .delete("/carangobom/v1/vehicle/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testVehicleDeleteNotFound() throws Exception {
        Long id = 5L;
        doThrow(VehicleNotFoundException.class).when(vehicleService).delete(ArgumentMatchers.isA(Long.class));
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .delete("/carangobom/v1/vehicle/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof VehicleNotFoundException))
                .andDo(print());
    }

}
