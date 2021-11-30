package inf.unideb.webbackend.service;

import inf.unideb.webbackend.dto.MonitorDTO;
import inf.unideb.webbackend.exception.CustomException;
import inf.unideb.webbackend.model.Monitor;
import inf.unideb.webbackend.repository.MonitorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitorService extends BaseService<MonitorDTO, Monitor> {

    private final MonitorRepository monitorRepository;

    public MonitorService(MonitorRepository monitorRepository) {
        super(MonitorDTO.class, Monitor.class);
        this.monitorRepository = monitorRepository;
    }

    public List<MonitorDTO> getAllMonitors() {
        return monitorRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<MonitorDTO> getMonitorsByBrandId(final Long brandId) {
        return monitorRepository.findAllByBrand_Id(brandId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public MonitorDTO getMonitorById(final Long id) {
        final Monitor monitor = monitorRepository.findById(id).orElseThrow(() ->
                new CustomException("Id not found", HttpStatus.NOT_FOUND));
        return this.mapToDTO(monitor);
    }

    public MonitorDTO saveMonitor(final MonitorDTO monitorDTO) {
        final Monitor monitor = mapFromDTO(monitorDTO);
        monitor.setLastModified(Instant.now());
        return mapToDTO(monitorRepository.save(monitor));
    }

    public MonitorDTO updateMonitor(final MonitorDTO monitorDTO, final Long id) {
        final Monitor monitor = mapFromDTO(monitorDTO);
        return monitorRepository.findById(id)
                .map(updateMonitor -> {
                    updateMonitor = monitor;
                    updateMonitor.setLastModified(Instant.now());
                    return mapToDTO(monitorRepository.save(updateMonitor));
                })
                .orElseThrow(() -> new CustomException("asd", HttpStatus.NOT_FOUND));
    }

    public void deleteMonitor(final Long id) {
        monitorRepository.findById(id)
                .map(deleteMonitor -> {
                    monitorRepository.deleteById(id);
                    return deleteMonitor;
                })
                .orElseThrow(() -> new CustomException("asd", HttpStatus.NOT_FOUND));
    }
}
