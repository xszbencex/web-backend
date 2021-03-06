package inf.unideb.webbackend.controller;

import inf.unideb.webbackend.dto.MonitorDTO;
import inf.unideb.webbackend.service.MonitorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/monitor")
@CrossOrigin(origins = "http://localhost:4200")
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping()
    public List<MonitorDTO> getAllMonitors() {
        return monitorService.getAllMonitors();
    }

    @GetMapping("/{id}")
    public MonitorDTO getMonitorById(@PathVariable final Long id) {
        return monitorService.getMonitorById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MonitorDTO saveMonitor(@RequestBody final MonitorDTO monitor) {
        return monitorService.saveMonitor(monitor);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MonitorDTO updateMonitor(@RequestBody final MonitorDTO monitor, @PathVariable final Long id) {
        return monitorService.updateMonitor(monitor, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteMonitor(@PathVariable final Long id) {
        monitorService.deleteMonitor(id);
    }
}
