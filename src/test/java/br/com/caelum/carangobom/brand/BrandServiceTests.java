package br.com.caelum.carangobom.brand;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exception.BrandDuplicatedNameException;
import br.com.caelum.carangobom.exception.BrandNotFoundException;
import br.com.caelum.carangobom.repository.BrandRepository;
import br.com.caelum.carangobom.service.BrandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class BrandServiceTests {

    @InjectMocks
    private BrandService brandService;

    @Mock
    private BrandRepository brandRepository;


    @Test
    @DisplayName("FIND ALL BRANDS")
    void shouldGetAllBrands() {
        given(brandRepository.findAll()).willReturn(BrandMocks.getListBrands());
        List<Brand> expected = brandService.findAllBrands();
        assertEquals(expected, BrandMocks.getListBrands());
        assertEquals(5, expected.size());
        verify(brandRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("FIND BRAND BY ID")
    void testGetBrandById() {
        Long id = 1L;
        given(brandRepository.findById(any())).willReturn(Optional.of(BrandMocks.getBmw()));
        Brand expected = brandService.findBrandById(id);
        assertEquals(expected, BrandMocks.getBmw());
        verify(brandRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("FIND BRAND BY WITHOUT EXISTING ID")
    void shouldThrowErrorWhenGetBrandByWithoutExistingId() {
        Long id = 1L;
        given(brandRepository.findById(id)).willReturn(Optional.empty());
        assertThrows(BrandNotFoundException.class, () -> brandService.findBrandById(id));
        verify(brandRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("FIND BRAND BY NAME")
    void testGetBrandByName() {
        String name = "Chevrolet";
        given(brandRepository.findByName(any())).willReturn(Optional.of(BrandMocks.getChevrolet()));
        Brand expected = brandService.findBrandByName(name);
        assertEquals(expected, BrandMocks.getChevrolet());
        verify(brandRepository, times(1)).findByName(any());
    }

    @Test
    @DisplayName("FIND BRAND BY WITHOUT EXISTING NAME")
    void shouldThrowErrorWhenGetBrandByWithoutExistingName() {
        String name = "Jaguar";

        given(brandRepository.findByName(any())).willReturn(Optional.empty());
        assertThrows(BrandNotFoundException.class, () -> brandService.findBrandByName(name));
        verify(brandRepository, times(1)).findByName(any());
    }

    @Test
    @DisplayName("CREATE BRAND")
    void shouldCreateBrand() {
        Brand audi = BrandMocks.getAudi();

        given(brandRepository.findByName(any())).willReturn(Optional.empty());
        given(brandRepository.create(any())).willReturn(Optional.of(BrandMocks.getAudi()));

        Brand expected = brandService.createBrand(BrandMocks.getAudi());

        assertEquals(expected, audi);
        verify(brandRepository, times(1)).findByName(any());
        verify(brandRepository, times(1)).create(any());
    }

    @Test
    @DisplayName("CREATE BRAND BY EXISTING BRAND NAME")
    void shouldThrowErrorWhenCreateBrandWithExistingName() {
        Optional<Brand> audi = Optional.of(BrandMocks.getAudi());

        given(brandRepository.findByName(any())).willReturn(Optional.of(BrandMocks.getAudi()));
        given(brandRepository.create(any())).willReturn(Optional.of(BrandMocks.getAudi()));

        assertThrows(BrandDuplicatedNameException.class, () -> brandService.createBrand(audi.get()));
        verify(brandRepository, times(1)).findByName(any());
        verify(brandRepository, never()).create(any());
    }

    @Test
    @DisplayName("UPDATE BRAND")
    void shouldUpdateBrand() {
        Long id = 2L;

        given(brandRepository.findById(id)).willReturn(Optional.of(BrandMocks.getAudi()));
        given(brandRepository.findByName(BrandMocks.getAudi().getName())).willReturn(Optional.empty());
        given(brandRepository.update(id, BrandMocks.getFiat().getName())).willReturn(Optional.of(BrandMocks.getFiat()));

        Brand expected = brandService.updateBrand(id, BrandMocks.getFiat());

        assertEquals(expected, BrandMocks.getFiat());
        verify(brandRepository, times(1)).findById(any());
        verify(brandRepository, times(1)).findByName(any());
        verify(brandRepository, times(1)).update(any(), any());
    }

    @Test
    @DisplayName("UPDATE BRAND BY WITHOUT EXISTING ID")
    void shouldThrowErrorWhenUpdateBrandWithoutExistingId() {
        Long id = 2L;
        Optional<Brand> audi = Optional.of(BrandMocks.getAudi());

        given(brandRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> brandService.updateBrand(id, audi.get()));
        verify(brandRepository, times(1)).findById(any());
        verify(brandRepository, never()).findByName(any());
        verify(brandRepository, never()).update(any(), any());
    }

    @Test
    @DisplayName("UPDATE BRAND BY EXISTING BRAND NAME")
    void shouldThrowErrorWhenUpdateBrandWithExistingName() {
        Long id = 2L;
        String audi = BrandMocks.getAudi().getName();

        given(brandRepository.findById(id)).willReturn(Optional.of(BrandMocks.getAudi()));
        given(brandRepository.findByName(audi)).willReturn(Optional.of(BrandMocks.getAudi()));

        assertThrows(BrandDuplicatedNameException.class, () -> brandService.updateBrand(id, BrandMocks.getAudi()));
        verify(brandRepository, times(1)).findById(any());
        verify(brandRepository, times(1)).findByName(any());
        verify(brandRepository, never()).update(any(), any());
    }

    @Test
    @DisplayName("DELETE BRAND")
    void shouldDeleteBrand() {
        Long id = 4L;

        given(brandRepository.findById(any())).willReturn(Optional.of(BrandMocks.getFord()));

        brandService.deleteBrand(id);

        verify(brandRepository, times(1)).findById(any());
        verify(brandRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("DELETE BRAND BY WITHOUT EXISTING ID")
    void shouldThrowErrorWhenDeleteBrandWithoutExistingId() {
        Long id = 2L;

        given(brandRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> brandService.deleteBrand(id));
        verify(brandRepository, times(1)).findById(any());
        verify(brandRepository, never()).delete(any());
    }
}
