package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carangobom/v1")
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("brand")
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @GetMapping("brand/{id}")
    public Brand findById(@PathVariable Long id) {
        return brandRepository.findById(id);
    }

    @PostMapping("brand")
    @ResponseStatus(HttpStatus.CREATED)
    public Brand create(@RequestBody Brand brand) {
        return brandRepository.create(brand);
    }

    @DeleteMapping("cookbook/{id}")
    public void delete(@PathVariable Long id) {
        brandRepository.delete(id);
    }

    @PutMapping("cookbook/{id}")
    public Brand update(@RequestBody String recipe) {
        return brandRepository.update(recipe);
    }

}
