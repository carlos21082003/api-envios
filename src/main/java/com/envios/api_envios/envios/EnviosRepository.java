package com.envios.api_envios.envios;

import com.envios.api_envios.reportes.ProvinciasConteoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnviosRepository extends JpaRepository<Envios,Long> {
    Optional<Envios> findByDniRemitente(String dniRemitente);

    Long countByEstadoEnvio(EstadoEnvio estadoEnvio);

    Page<Envios> findBySedeId(Long sedeId, Pageable pageable);

    Page<Envios> findAll(Pageable pageable);

    @Query("SELECT new com.envios.api_envios.reportes.ProvinciasConteoDTO(e.provincia, COUNT(e)) " +
            "FROM Envios e GROUP BY e.provincia ORDER BY COUNT(e) DESC")
    List<ProvinciasConteoDTO> countByProvincia();

    @Query("SELECT e FROM Envios e WHERE e.sede.id = :sedeId OR e.sedeDestino.id = :sedeId")
    Page<Envios> findBySedeOrigenOrSedeDestino(@Param("sedeId") Long sedeId, Pageable pageable);

    Optional<Envios> findByCodigoEnvio(String codigoEnvio);

    boolean existsByCodigoEnvio(String codigoEnvio);
}
