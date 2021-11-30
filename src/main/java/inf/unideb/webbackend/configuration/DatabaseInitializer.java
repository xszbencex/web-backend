package inf.unideb.webbackend.configuration;

import inf.unideb.webbackend.controller.UserController;
import inf.unideb.webbackend.dto.UserDTO;
import inf.unideb.webbackend.model.Brand;
import inf.unideb.webbackend.model.Monitor;
import inf.unideb.webbackend.model.Role;
import inf.unideb.webbackend.model.User;
import inf.unideb.webbackend.repository.BrandRepository;
import inf.unideb.webbackend.repository.MonitorRepository;
import inf.unideb.webbackend.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class DatabaseInitializer {

    private final BrandRepository brandRepository;
    private final MonitorRepository monitorRepository;
    private final UserService userService;

    public DatabaseInitializer(BrandRepository brandRepository, MonitorRepository monitorRepository, UserService userService) {
        this.brandRepository = brandRepository;
        this.monitorRepository = monitorRepository;
        this.userService = userService;
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

    @PostConstruct
    public void accountInit() {
        UserDTO admin = new UserDTO();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@email.com");
        admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

        userService.signUp(admin);

        UserDTO client = new UserDTO();
        client.setUsername("client");
        client.setPassword("client");
        client.setEmail("client@email.com");
        client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

        userService.signUp(client);
    }

}
