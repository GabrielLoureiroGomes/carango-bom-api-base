package br.com.caelum.carangobom.mocks;

import br.com.caelum.carangobom.domain.Brand;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public final class BrandMocks {

    public static Brand getChevrolet() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Chevrolet");
        brand.setCreatedAt(LocalDate.now());
        brand.setUpdatedAt(null);
        return brand;
    }

    public static Brand getAudi() {
        Brand brand = new Brand();
        brand.setId(2L);
        brand.setName("Audi");
        brand.setCreatedAt(LocalDate.now().plusDays(1));
        brand.setUpdatedAt(null);
        return brand;
    }

    public static Brand getFiat() {
        Brand brand = new Brand();
        brand.setId(3L);
        brand.setName("Fiat");
        brand.setCreatedAt(LocalDate.now().plusDays(2));
        brand.setUpdatedAt(null);
        return brand;
    }

    public static Brand getFord() {
        Brand brand = new Brand();
        brand.setId(4L);
        brand.setName("Ford");
        brand.setCreatedAt(LocalDate.now());
        brand.setUpdatedAt(null);
        return brand;
    }

    public static Brand getBmw() {
        Brand brand = new Brand();
        brand.setId(5L);
        brand.setName("BMW");
        brand.setCreatedAt(LocalDate.now());
        brand.setUpdatedAt(null);
        return brand;
    }

    public static List<Brand> getListBrands() {
        return Arrays.asList(getChevrolet(), getAudi(), getFiat(), getFord(), getBmw());
    }
}
