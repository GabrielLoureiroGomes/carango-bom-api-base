package br.com.caelum.carangobom.brand;

import br.com.caelum.carangobom.controller.BrandController;
import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BrandController.class)
class BrandControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    @Test
    void shouldFetchAllBrands() throws Exception {
        given(brandService.findAllBrands()).willReturn(BrandMocks.getListBrands());

        mockMvc.perform(get("/carangobom/v1/brand"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFetchOneBrandById() throws Exception {
        final Long brandId = 1L;
        Brand chevrolet = BrandMocks.getChevrolet();
        given(brandService.findBrandById(brandId)).willReturn(chevrolet);

        mockMvc.perform(get("/carangobom/v1/brand/{id}", brandId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(chevrolet.getName())));
    }

    @Test
    void shouldCreateNewBrand() throws Exception {
        given(brandService.createBrand(any())).willReturn(BrandMocks.getBmw());

        mockMvc.perform(post("/carangobom/v1/brand")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"BMW\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateNewBrand() throws Exception {
        final Long brandId = 4L;
        given(brandService.updateBrand(any(), any())).willReturn(BrandMocks.getFord());

        mockMvc.perform(patch("/carangobom/v1/brand/{id}", brandId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Ford\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteNewBrand() throws Exception {
        final Long brandId = 4L;
        given(brandService.findBrandById(any())).willReturn(BrandMocks.getFiat());
        doNothing().when(brandService).deleteBrand(any());

        mockMvc.perform(delete("/carangobom/v1/brand/{id}", brandId))
                .andExpect(status().isOk());
    }

}
