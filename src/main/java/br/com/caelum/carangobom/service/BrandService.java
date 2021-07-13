package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exception.BrandNotFoundException;
import br.com.caelum.carangobom.exception.VehicleNotFoundException;
import br.com.caelum.carangobom.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand findById(Long id) {
        Optional<Brand> brand = Optional.of(this.brandRepository.findById(id));
        if (brand.isPresent()) {
            return brand.get();
        } else {
            throw new BrandNotFoundException("Marca não encontrada!");
        }
    }
}
