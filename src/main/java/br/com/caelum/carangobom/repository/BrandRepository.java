package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.Brand;

import java.util.List;

public interface BrandRepository {

    List<Brand> findAll();

    Brand findById(Long id);

    void delete(Long id);

    Brand create(Brand brand);

    Brand update(Brand brand);

}
