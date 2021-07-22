package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exception.BrandNotFoundException;
import br.com.caelum.carangobom.exception.BrandDuplicatedNameException;
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

        return result.orElseThrow(() -> new BrandNotFoundException(id.toString()));
    }

    public Brand findBrandByName(String name) {
        Optional<Brand> result = brandRepository.findByName(name);

        return result.orElseThrow(() -> new BrandNotFoundException(name));
    }

    public Brand createBrand(String brandName) {
        Optional<Brand> result = brandRepository.findByName(brandName);

        if (result.isPresent()) throw new BrandDuplicatedNameException(brandName);

        return brandRepository.create(brandName).orElseThrow(BusinessException::new);

    }

    public Brand updateBrand(Long id, String brandName) {

        this.validateUpdate(id, brandName);

        return brandRepository.update(id, brandName).orElseThrow(BusinessException::new);
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
