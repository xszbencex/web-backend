package inf.unideb.webbackend.configuration;

import inf.unideb.webbackend.model.Brand;
import inf.unideb.webbackend.model.Monitor;
import inf.unideb.webbackend.repository.BrandRepository;
import inf.unideb.webbackend.repository.MonitorRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

    private final BrandRepository brandRepository;
    private final MonitorRepository monitorRepository;

    public DatabaseInitializer(BrandRepository brandRepository, MonitorRepository monitorRepository) {
        this.brandRepository = brandRepository;
        this.monitorRepository = monitorRepository;
    }

    @PostConstruct
    public void initBrandsAndMonitors() {
        Brand brand = new Brand();
        brand.setName("MSI");
        brand.setCeo("Charles Chiang");
        brand.setHeadquarters("Tajvan");
        brandRepository.save(brand);

        Monitor monitor = new Monitor();
        monitor.setName("Optix MAG342CQR");
        monitor.setBrand(brand);
        monitor.setAspectRatio("21:9");
        monitor.setDisplaySize(32);
        monitor.setRefreshRate(144);
        monitor.setResolution("3440x1440");
        monitor.setPanelType("VA");
        monitorRepository.save(monitor);

        brand = new Brand();
        brand.setName("AOC");
        brand.setCeo("Ross Siragusa");
        brand.setHeadquarters("Tajvan");
        brandRepository.save(brand);

        monitor = new Monitor();
        monitor.setName("27G2U");
        monitor.setBrand(brand);
        monitor.setAspectRatio("16:9");
        monitor.setDisplaySize(27);
        monitor.setRefreshRate(144);
        monitor.setResolution("1920x1080");
        monitor.setPanelType("IPS");
        monitorRepository.save(monitor);
    }

}
