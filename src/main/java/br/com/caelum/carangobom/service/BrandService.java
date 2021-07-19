package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exception.BrandDuplicatedNameException;
import br.com.caelum.carangobom.exception.BrandNotFoundException;
import br.com.caelum.carangobom.exception.BusinessException;
import br.com.caelum.carangobom.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    public Brand findBrandById(Long id) {
        Optional<Brand> result = brandRepository.findById(id);

        if (result.isEmpty()) throw new BrandNotFoundException(id.toString());

        return result.get();
    }

    public Brand findBrandByName(String name) {
        Optional<Brand> result = brandRepository.findByName(name);

        return result.orElseThrow(() -> new BrandNotFoundException(name));
    }

    public Brand createBrand(Brand brand) {
        Optional<Brand> result = brandRepository.findByName(brand.getName());

        if (result.isPresent()) throw new BrandDuplicatedNameException(brand.getName());

        return brandRepository.create(brand.getName()).orElseThrow(BusinessException::new);

    }

    public Brand updateBrand(Long id, Brand brand) {

        this.validateUpdate(id, brand.getName());

        return brandRepository.update(id, brand.getName()).orElseThrow(BusinessException::new);
    }

    private void validateUpdate(Long id, String name) {
        if (findBrandById(id) == null) throw new BrandNotFoundException(id.toString());

        if (brandRepository.findByName(name).isPresent()) throw new BrandDuplicatedNameException(name);

    }

    public void deleteBrand(Long id) {
        findBrandById(id);
        brandRepository.delete(id);
    }

}
