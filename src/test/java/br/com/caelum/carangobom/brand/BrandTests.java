package br.com.caelum.carangobom.brand;

import br.com.caelum.carangobom.brand.BrandMocks;
import br.com.caelum.carangobom.controller.BrandController;
import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BrandTests {

    @InjectMocks
    private BrandController brandController;

    @Mock
    private BrandRepository brandRepository;

    @BeforeEach
    public void getMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("FIND ALL BRANDS")
    void testGetAllBrands() {
        when(brandRepository.findAll()).thenReturn(BrandMocks.getListBrands());
        List<Brand> brands = brandController.findAll();
        verify(brandRepository).findAll();
        assertEquals(5, brands.size());
    }

    @Test
    @DisplayName("FIND BRAND BY ID")
    void testGetBrandById() {
        Long id = 1L;
        when(brandRepository.findById(any())).thenReturn(BrandMocks.getBmw());
        Brand brand = brandController.findById(id);
        verify(brandRepository).findById(any());
        assertEquals(5L, brand.getId());
    }

    @Test
    @DisplayName("CREATE BRAND")
    void testCreateBrand() {
        when(brandRepository.create(any())).thenReturn(BrandMocks.getAudi());
        Brand brand = brandController.create(BrandMocks.getAudi());
        verify(brandRepository).create(any());
        assertEquals(2, brand.getId());
    }

    @Test
    @DisplayName("UPDATE BRAND")
    void testUpdateRecipe() {
        when(brandRepository.update(any(), any())).thenReturn(BrandMocks.getFiat());
        Brand brand = brandController.update(BrandMocks.getFiat().getId(), BrandMocks.getFiat());
        verify(brandRepository).update(any(), any());
        assertEquals(3L, brand.getId());
    }

    @Test
    @DisplayName("DELETE BRAND")
    void testDeleteRecipe() {
        Long id = 4L;
        doNothing().when(brandRepository).delete(any());
        brandController.delete(id);
        verify(brandRepository).delete(any());
    }

}
