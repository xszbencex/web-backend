package inf.unideb.webbackend.configuration;

import inf.unideb.webbackend.dto.UserDTO;
import inf.unideb.webbackend.model.Brand;
import inf.unideb.webbackend.model.Monitor;
import inf.unideb.webbackend.model.Role;
import inf.unideb.webbackend.repository.BrandRepository;
import inf.unideb.webbackend.repository.MonitorRepository;
import inf.unideb.webbackend.repository.UserRepository;
import inf.unideb.webbackend.service.UserService;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class DatabaseInitializer {

    private final BrandRepository brandRepository;
    private final MonitorRepository monitorRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Environment environment;

    public DatabaseInitializer(BrandRepository brandRepository,
                               MonitorRepository monitorRepository,
                               UserRepository userRepository,
                               UserService userService,
                               Environment environment) {
        this.brandRepository = brandRepository;
        this.monitorRepository = monitorRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.environment = environment;
    }

    @PostConstruct
    public void initBrandsAndMonitors() {
        if (this.isProfileMemoryActive()) {
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

    @PostConstruct
    public void initAccount() {
        if (this.isProfileMemoryActive() || !userRepository.existsByUsername("admin")) {
            UserDTO admin = new UserDTO();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@email.com");
            admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

            userService.signUp(admin);
        }
        if (this.isProfileMemoryActive() || !userRepository.existsByUsername("client")) {
            UserDTO client = new UserDTO();
            client.setUsername("client");
            client.setPassword("client");
            client.setEmail("client@email.com");
            client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

            userService.signUp(client);
        }
    }

    private boolean isProfileMemoryActive() {
        return Arrays.asList(this.environment.getActiveProfiles()).contains("memory");
    }
}
