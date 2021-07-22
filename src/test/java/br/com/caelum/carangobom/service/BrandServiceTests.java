package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.config.security.SecurityConfiguration;
import br.com.caelum.carangobom.controller.AuthController;
import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exception.BrandDuplicatedNameException;
import br.com.caelum.carangobom.exception.BrandNotFoundException;
import br.com.caelum.carangobom.mocks.BrandMocks;
import br.com.caelum.carangobom.repository.BrandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class BrandServiceTests {

    @InjectMocks
    private BrandService brandService;

    @Mock
    private BrandRepository brandRepository;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private SecurityConfiguration securityConfiguration;

    @MockBean
    private AuthController authController;

    @InjectMocks
    private TokenService tokenService;


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
    @DisplayName("FIND BRAND BY NON EXISTING ID")
    void shouldThrowErrorWhenGetBrandByNonExistingId() {
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
    @DisplayName("FIND BRAND BY NON EXISTING NAME")
    void shouldThrowErrorWhenGetBrandByNonExistingName() {
        String name = "Jaguar";

        given(brandRepository.findByName(any())).willReturn(Optional.empty());
        assertThrows(BrandNotFoundException.class, () -> brandService.findBrandByName(name));
        verify(brandRepository, times(1)).findByName(any());
    }

    @Test
    @DisplayName("CREATE BRAND")
    void shouldCreateBrand() {
        Brand audi = BrandMocks.getAudi();
        String audiname = audi.getName();

        given(brandRepository.findByName(any())).willReturn(Optional.empty());
        given(brandRepository.create(any())).willReturn(Optional.of(BrandMocks.getAudi()));

        Brand expected = brandService.createBrand(audiname);

        assertEquals(expected, audi);
        verify(brandRepository, times(1)).findByName(any());
        verify(brandRepository, times(1)).create(any());
    }

    @Test
    @DisplayName("CREATE BRAND BY EXISTING BRAND NAME")
    void shouldThrowErrorWhenCreateBrandWithExistingName() {
        Optional<Brand> optionalAudi = Optional.of(BrandMocks.getAudi());
        String audi = optionalAudi.get().getName();

        given(brandRepository.findByName(any())).willReturn(Optional.of(BrandMocks.getAudi()));
        given(brandRepository.create(any())).willReturn(Optional.of(BrandMocks.getAudi()));

        assertThrows(BrandDuplicatedNameException.class, () -> brandService.createBrand(audi));
        verify(brandRepository, times(1)).findByName(any());
        verify(brandRepository, never()).create(any());
    }

    @Test
    @DisplayName("UPDATE BRAND")
    void shouldUpdateBrand() {
        Long id = 2L;
        String fiatName = BrandMocks.getFiat().getName();

        given(brandRepository.findById(id)).willReturn(Optional.of(BrandMocks.getAudi()));
        given(brandRepository.findByName(BrandMocks.getAudi().getName())).willReturn(Optional.empty());
        given(brandRepository.update(id, BrandMocks.getFiat().getName())).willReturn(Optional.of(BrandMocks.getFiat()));

        Brand expected = brandService.updateBrand(id, fiatName);

        assertEquals(expected, BrandMocks.getFiat());
        verify(brandRepository, times(1)).findById(any());
        verify(brandRepository, times(1)).findByName(any());
        verify(brandRepository, times(1)).update(any(), any());
    }

    @Test
    @DisplayName("UPDATE BRAND BY NON EXISTING ID")
    void shouldThrowErrorWhenUpdateBrandNonExistingId() {
        Long id = 2L;
        Optional<Brand> optionalBrand = Optional.of(BrandMocks.getAudi());
        String audi = optionalBrand.get().getName();

        given(brandRepository.findById(any())).willThrow(BrandNotFoundException.class);

        assertThrows(BrandNotFoundException.class, () -> brandService.updateBrand(id, audi));
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

        assertThrows(BrandDuplicatedNameException.class, () -> brandService.updateBrand(id, audi));
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
    @DisplayName("DELETE BRAND BY NON EXISTING ID")
    void shouldThrowErrorWhenDeleteBrandNonExistingId() {
        Long id = 2L;

        given(brandRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> brandService.deleteBrand(id));
        verify(brandRepository, times(1)).findById(any());
        verify(brandRepository, never()).delete(any());
    }
}
