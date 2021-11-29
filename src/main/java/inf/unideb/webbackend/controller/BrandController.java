package inf.unideb.webbackend.controller;

import inf.unideb.webbackend.dto.BrandDTO;
import inf.unideb.webbackend.service.BrandService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping()
    public List<BrandDTO> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public BrandDTO getBrandById(@PathVariable final Long id) {
        return brandService.getBrandById(id);
    }

    @PostMapping
    public BrandDTO saveBrand(@RequestBody final BrandDTO brand) {
        return brandService.saveBrand(brand);
    }

    @PutMapping("/{id}")
    public BrandDTO updateBrand(@RequestBody final BrandDTO brand, @PathVariable final Long id) {
        return brandService.updateBrand(brand, id);
    }

    @DeleteMapping("/{id}")
    public String updateBrand(@PathVariable final Long id) {
        brandService.deleteBrand(id);
        return "Deleted";
    }
}
