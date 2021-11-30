package inf.unideb.webbackend.service;

import inf.unideb.webbackend.dto.BrandDTO;
import inf.unideb.webbackend.exception.CustomException;
import inf.unideb.webbackend.model.Brand;
import inf.unideb.webbackend.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService extends BaseService<BrandDTO, Brand> {

    private final BrandRepository brandRepository;
    private final MonitorService monitorService;

    @Autowired
    public BrandService(BrandRepository brandRepository, MonitorService monitorService) {
        super(BrandDTO.class, Brand.class);
        this.brandRepository = brandRepository;
        this.monitorService = monitorService;
    }

    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public BrandDTO getBrandById(final Long id) {
        final Brand brand = brandRepository.findById(id).orElseThrow(() ->
                new CustomException("Id not found", HttpStatus.NOT_FOUND));
        return this.mapToDTO(brand);
    }

    public BrandDTO saveBrand(final BrandDTO brandDTO) {
        final Brand brand = mapFromDTO(brandDTO);
        brand.setLastModified(Instant.now());
        return mapToDTO(brandRepository.save(brand));
    }

    public BrandDTO updateBrand(final BrandDTO brandDTO, final Long id) {
        final Brand brand = mapFromDTO(brandDTO);
        return brandRepository.findById(id)
                .map(updateBrand -> {
                    updateBrand = brand;
                    updateBrand.setLastModified(Instant.now());
                    return mapToDTO(brandRepository.save(updateBrand));
                })
                .orElseThrow(() -> new CustomException("asd", HttpStatus.NOT_FOUND));
    }

    public void deleteBrand(final Long id) {
        brandRepository.findById(id)
                .map(deleteBrand -> {
                    monitorService.getMonitorsByBrandId(id)
                            .forEach(monitorDTO -> monitorService.deleteMonitor(monitorDTO.getId()));
                    brandRepository.deleteById(id);
                    return deleteBrand;
                })
                .orElseThrow(() -> new CustomException("asd", HttpStatus.NOT_FOUND));
    }
}
