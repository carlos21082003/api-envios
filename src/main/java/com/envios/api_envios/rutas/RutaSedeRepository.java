package com.envios.api_envios.rutas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RutaSedeRepository extends JpaRepository<RutaSede, Long>{
    // verifica si existe una ruta activa entre dos sedes
    Optional<RutaSede> findBySedeOrigenIdAndSedeDestinoIdAndActivoTrue(
            Long sedeOrigenId, Long sedeDestinoId);

    // lista todas las rutas de una sede origen
    List<RutaSede> findBySedeOrigenIdAndActivoTrue(Long sedeOrigenId);

    Page<RutaSede> findAll(Pageable pageable);
}
