package inf.unideb.webbackend.repository;

import inf.unideb.webbackend.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitorRepository extends JpaRepository<Monitor, Long> {

    List<Monitor> findAllByBrand_Id(Long brandId);
}
