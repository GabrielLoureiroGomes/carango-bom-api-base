package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.requests.CreateUpdateBrandRequest;
import br.com.caelum.carangobom.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carangobom/v1/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public List<Brand> findAll() {
        return brandService.findAllBrands();
    }

    @GetMapping("/{id}")
    public Brand findById(@PathVariable Long id) {
        return brandService.findBrandById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Brand create(@RequestBody CreateUpdateBrandRequest request) {
        return brandService.createBrand(request.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        brandService.deleteBrand(id);
    }

    @PatchMapping("/{id}")
    public Brand update(@PathVariable Long id, @RequestBody CreateUpdateBrandRequest request) {
        return brandService.updateBrand(id, request.getName());
    }

}
