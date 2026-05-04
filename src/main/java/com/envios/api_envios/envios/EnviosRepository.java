package com.envios.api_envios.envios;

import com.envios.api_envios.reportes.ProvinciasConteoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnviosRepository extends JpaRepository<Envios,Long> {
    Optional<Envios> findByDniRemitente(String dniRemitente);
    Long countByEstadoEnvio(EstadoEnvio estadoEnvio);

    @Query("SELECT new com.envios.api_envios.reportes.ProvinciasConteoDTO(e.provincia, COUNT(e)) " +
            "FROM Envios e GROUP BY e.provincia ORDER BY COUNT(e) DESC")
    List<ProvinciasConteoDTO> countByProvincia();
}
