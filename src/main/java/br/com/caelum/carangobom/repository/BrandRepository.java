package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.Brand;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository {

    List<Brand> findAll();

    Brand findById(Long id);

    void delete(Long id);

    Brand create(String brandName);

    Brand update(Long id, String name);

}