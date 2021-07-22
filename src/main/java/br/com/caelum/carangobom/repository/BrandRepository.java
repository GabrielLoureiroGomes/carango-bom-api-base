package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {

    List<Brand> findAll();

    Optional<Brand> findById(Long id);

    Optional<Brand> findByName(String name);

    void delete(Long id);

    Optional<Brand> create(String brandName);

    Optional<Brand> update(Long id, String name);

}