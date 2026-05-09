package com.envios.api_envios.sede;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SedeRepository extends JpaRepository<Sede, Long> {
    Page<Sede> findByActivoTrue(Pageable pageable);
    Page<Sede> findAll(Pageable pageable);
    List<Sede> findByActivoTrue(); // para selects en formularios
    boolean existsByProvincia(String provincia);
}
